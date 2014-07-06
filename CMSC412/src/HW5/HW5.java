package HW5;

/**
 * Created by Justin Smith
 * 7/5/14
 * CMSC 412
 * Requires Java JDK 7+
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class HW5 {

  public static void main(String[] args) {

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    Path dir = null;
    int response;
    boolean quit = false;

    while (!quit) {
      System.out.println("\nSelect Option:\n" +
          "\t(0) Exit\n" +
          "\t(1) Select Directory\n" +
          "\t(2) List Directory Content\n" +
          "\t(3) List Directory Content (Recursive)\n" +
          "\t(4) Delete File\n" +
          "\t(5) Display File\n" +
          "\t(6) Encrypt File\n" +
          "\t(7) Decrypt File");
      System.out.print("Choose > ");

      response = getNextInt(input);

      switch (response) {
        case 0:
          quit = true;
          break;

        case 1:
          System.out.print("Please enter absolute path to directory > ");
          dir = Paths.get(getNextString(input));
          if (Files.isDirectory(dir)) {
            System.out.println("The working directory is now: " + dir.toString());
          } else {
            System.out.println("Error: " + dir.toString() + " is not a directory!");
            dir = null;
          }
          break;

        case 2:
          if (dir != null) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
              for (Path entry : stream)
                System.out.println(entry.getFileName());
            } catch (IOException e) {
              System.out.println(e.getMessage());
            }
          } else {
            System.out.println("You must select a directory first!");
          }
          break;

        case 3:
          if (dir != null) {
            try {
              Files.walkFileTree(dir, new RecursiveDirWalk());
            } catch (IOException e) {
              System.out.println(e.getMessage());
            }
          } else {
            System.out.println("You must select a directory first!");
          }
          break;

        case 4:
          if (dir != null) {
            try {
              System.out.print("Please enter a target file to delete > ");
              Files.delete(dir.resolve(getNextString(input)));
              System.out.println("File deleted!");
            } catch (IOException e) {
              System.out.println("The file: " + e.getMessage() + " does not exist!");
            }
          } else {
            System.out.println("You must select a directory first!");
          }
          break;

        case 5:
          if (dir != null) {
            System.out.print("Please enter a target file to display > ");
            try (BufferedReader reader =
                     Files.newBufferedReader(dir.resolve(getNextString(input)), StandardCharsets.UTF_8)) {
              String line;
              while ((line = reader.readLine()) != null)
                System.out.println(line);
            } catch (IOException e) {
              System.out.println("The file: " + e.getMessage() + " does not exist!");
            }
          } else {
            System.out.println("You must select a directory first!");
          }
          break;

        case 6:
          if (dir != null) {

            Path file, dest;
            byte[] ptxt = null;
            byte[] password;

            System.out.print("Please enter a target file to encrypt > ");
            file = dir.resolve(getNextString(input));
            if (Files.isRegularFile(file)) {
              try {
                System.out.println("Reading file into memory...");
                ptxt = Files.readAllBytes(file);
              } catch (IOException e) {
                System.out.println("Error occurred reading all bytes: " + e.getMessage());
              }

              System.out.print("Please enter a destination file > ");
              dest = dir.resolve(getNextString(input));
              Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-r--r--");
              FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
              try {
                Files.createFile(dest, attr);
              } catch (IOException e) {
                System.out.println("Error occurred creating destination file: " + e.getMessage());
              }

              System.out.print("Please enter a password to use > ");
              password = getNextString(input).getBytes();

              if (ptxt != null) {
                System.out.println("Encrypting...");
                byte[] ctxt = new byte[ptxt.length];

                for(int i = 0; i < ptxt.length; ++i)
                  ctxt[i] = (byte) (ptxt[i] ^ password[i % password.length]);

                try {
                  Files.write(dest, ctxt, StandardOpenOption.WRITE);
                } catch (IOException e) {
                  System.out.println("There was a problem writing the CTXT to the file: " + dest.toString());
                }

                System.out.println("Finished Encrypting: " + file.toString() + " -> into -> " + dest.toString());
              } else {
                System.out.println("There was an unknown error accessing the file: " + file.toString());
              }
            } else {
              System.out.println("File: " + file.toString() + " does not exist!");
            }
          } else {
            System.out.println("You must select a directory first!");
          }
          break;

        case 7:
          if(dir!= null) {

            Path file, dest;
            byte[] ctxt = null;
            byte[] password;

            System.out.print("Please enter a cyphertext file > ");
            file = dir.resolve(getNextString(input));
            if(Files.isRegularFile(file)) {
              try {
                System.out.println("Reading file into memory...");
                ctxt = Files.readAllBytes(file);
              } catch (IOException e) {
                System.out.println("Error occurred reading all bytes: " + e.getMessage());
              }

              System.out.print("Please enter a destination file > ");
              dest = dir.resolve(getNextString(input));
              Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-r--r--");
              FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
              try {
                Files.createFile(dest, attr);
              } catch (IOException e) {
                System.out.println("Error occurred creating destination file: " + e.getMessage());
              }

              System.out.print("Please enter a password to use > ");
              password = getNextString(input).getBytes();

              if(ctxt != null) {
                System.out.println("Decrypting...");
                byte[] ptxt = new byte[ctxt.length];

                for(int i = 0; i < ctxt.length; ++i)
                  ptxt[i] = (byte) (ctxt[i] ^ password[i % password.length]);

                try {
                  Files.write(dest, ptxt, StandardOpenOption.WRITE);
                } catch (IOException e) {
                  System.out.println("There was a problem writing the PTXT to the file: " + dest.toString());
                }

              } else {
                System.out.println("There was an unknown error accessing the file: " + file.toString());
              }
            } else {
              System.out.println("File: " + file.toString() + " does not exist!");
            }
          } else {
            System.out.println("You must select a directory first!");
          }
          break;

        default:
          System.out.println("Invalid option, try again");
      }
    }


  }

  private static class RecursiveDirWalk extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
      System.out.println(file.getFileName());
      return FileVisitResult.CONTINUE;
    }
  }

  private static int getNextInt(BufferedReader r) {
    do {
      try {
        return Integer.parseInt(r.readLine());
      } catch (IOException e) {
        System.out.println("Error thrown from getNextInt input");
        e.printStackTrace();
      } catch (NumberFormatException e) {
        System.out.print("Enter numbers only, please try again > ");
      }
    } while (true);
  }

  private static String getNextString(BufferedReader r) {
    String s = null;
    try {
      s = r.readLine();
      if (s.length() == 0) return null;
    } catch (IOException e) {
      System.out.println("Thrown from readLine() on user input");
      e.printStackTrace();
    }
    return s;
  }

}
