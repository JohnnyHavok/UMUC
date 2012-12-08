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

  -- Protected object and tasks specifications

  protected Merge_Protected is
    entry Submit_First(Value: in Integer);
    entry Submit_Second(Value: in Integer);
    entry Extract_Smaller(Value: out Integer);
  private
    First_Present, Second_Present: Boolean := False;
    First_Value, Second_Value: Integer;
  end Merge_Protected;
  task Input_Task_1;
  task Input_Task_2;
  task Output_Task;

  -- Merge_Protected is a protected object that provides
  -- the interface between the input tasks and the output task.

  protected body Merge_Protected is
    entry Submit_First(Value: in Integer)
      when not First_Present is
    begin
      First_Value := Value;
      First_Present := True;
    end Submit_First;
    entry Submit_Second(Value: in Integer)
      when not Second_Present is
    begin
      Second_Value := Value;
      Second_Present := True;
    end Submit_Second;
    entry Extract_Smaller(Value: out Integer)
      when First_Present and Second_Present is
    begin
      if First_Value < Second_Value then
        Value := First_Value;
        First_Present := False;
      else
        Value := Second_Value;
        Second_Present := False;
      end if;
    end Extract_Smaller;
  end Merge_Protected;

  -- Input_Task_1 reads values from the first input file and sends
  -- them to Merge_Protected.

  task body Input_Task_1 is
    Value: Integer;
    File_1: File_Type;
  begin
    Open(File_1, In_File, "file1.txt");
    while not End_Of_File(File_1) loop
      Get(File_1, Value);
      Merge_Protected.Submit_First(Value);
    end loop;
    Merge_Protected.Submit_First(Integer'Last);
    Close(File_1);
  end Input_Task_1;

  -- Input_Task_2 reads values from the second input file and sends
  -- them to Merge_Protected.

  task body Input_Task_2 is
    Value: Integer;
    File_2: File_Type;
  begin
    Open(File_2, In_File, "file2.txt");
    while not End_Of_File(File_2) loop
      Get(File_2, Value);
      Merge_Protected.Submit_Second(Value);
    end loop;
    Merge_Protected.Submit_Second(Integer'Last);
    Close(File_2);
  end Input_Task_2;

  -- Output_Task obtains values from Merge_Protected and writes
  -- them to the output file.

  task body Output_Task is
    File: File_Type;
    Value: Integer;
  begin
    Create(File, Out_File, "file3.txt");
    loop
      Merge_Protected.Extract_Smaller(Value);
      exit when Value = Integer'Last;
      Put(File, Value);
      New_Line(File);
    end loop;
    Close(File);
  end Output_Task;
begin
  null;
end Project3;
