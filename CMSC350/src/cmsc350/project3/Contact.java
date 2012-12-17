package cmsc350.project3;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project:
 * Date: 12/1/12 11:44 PM
 * Requires: J2SE 7+
 */

// ND provided test class
public class Contact implements Comparable<Contact> {
    private String firstName, lastName, phone;

    public Contact(String first, String last, String phone) {
        firstName = first;
        lastName = last;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format ("%-16s%s", lastName + "," + firstName, phone); // ND
    }

    @Override
    public int compareTo(Contact o) {
        if(lastName.equals(o.lastName))
            return firstName.compareTo(o.firstName);

        return lastName.compareTo(o.lastName);
    }
}
