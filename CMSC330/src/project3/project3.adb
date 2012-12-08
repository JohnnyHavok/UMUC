-- Justin Smith
-- CMSC330 6980
-- Project 3
-- File: project3.adb
-- Development Enviroment: Apple Mac OS X 10.6.3
--                         gcc 4.2.0 
-- Program that merges two sorted files using concurrent tasks

with Ada.Text_IO; use Ada.Text_IO;
procedure Project3 is
  package Int_IO is new Integer_IO(Integer);
  use Int_IO;

  -- Task Specifications
  task Merge_Task is
    entry Submit_First(Value: in Integer);
    entry Submit_Second(Value: in Integer);
  end Merge_Task;
  task Input_Task_1;
  task Input_Task_2;
  task Output_Task is
    entry Smaller(Smaller_Value: in Integer);
  end Output_Task;
  
  -- Merge_Task has two entry points.  As it recieves values it checks
  -- to see if the other value is present, if so it compares the values.
  -- 
  task body Merge_Task is
    First_Present, Second_Present: Boolean := False;
    First_Value, Second_Value: Integer := 0;	  
  begin
    while First_Value = Integer'Last and Second_Value = Integer'Last
    loop
      select
        when not First_Present =>
          accept Submit_First(Value: in Integer) do
            First_Value := Value;
          end Submit_First;
          First_Present := true;
          if Second_Present then
            if First_Value < Second_Value then
              Output_Task.Smaller(First_Value);
              First_Present := false;
            else
              Output_Task.Smaller(Second_Value);
              Second_Present := false;
            end if;
          end if;
      or
        when not Second_Present =>
          accept Submit_Second(Value: in Integer) do
            Second_Value := Value;
          end Submit_Second;
          Second_Present := true;
          if First_Present then
            if Second_Value < First_Value then
              Output_Task.Smaller(Second_Value);
              Second_Present := false;
            else
              Output_Task.Smaller(First_Value);
              First_Present := false;
            end if;
          end if;
      end select;
    end loop;    
  end Merge_Task;

  -- Input_Task_1 reads values from the first input file and sends
  -- them to Merge_Task.

  task body Input_Task_1 is
    Value: Integer;
    File_1: File_Type;
  begin
    Open(File_1, In_File, "file1.txt");
    while not End_Of_File(File_1) loop
      Get(File_1, Value);
      Merge_Task.Submit_First(Value);
    end loop;
    Merge_Task.Submit_First(Integer'Last);
    Close(File_1);
  end Input_Task_1;

  -- Input_Task_2 reads values from the second input file and sends
  -- them to Merge_Task.

  task body Input_Task_2 is
    Value: Integer;
    File_2: File_Type;
  begin
    Open(File_2, In_File, "file2.txt");
    while not End_Of_File(File_2) loop
      Get(File_2, Value);
      Merge_Task.Submit_Second(Value);
    end loop;
    Merge_Task.Submit_Second(Integer'Last);
    Close(File_2);
  end Input_Task_2;

  -- Output_Task has one entry and waits to be called by Merge_Task above.

  task body Output_Task is
    File: File_Type;
    Value: Integer;
  begin
    Create(File, Out_File, "file3.txt");
    loop
      accept Smaller(Smaller_Value: in Integer) do
        Value := Smaller_Value;
      end Smaller; 
      Put(File, Value);
      New_Line(File);
    end loop;
    Close(File);
  end Output_Task;
begin
  null;
end Project3;