CREATE TABLE Person_T (
PersonID			Varchar(5) 
Constraint PerPersID_PK PRIMARY KEY,
PersonName			Varchar(35),
PersonStrAddress	Varchar(20),
PersonCity			Varchar(20),
PersonState		Char(2),
PersonZip			Varchar(10),
PersonHomePhone		Varchar(14),
PersonWorkPhone		Varchar(14),
PersonDOB			Date,
PersonEMail		Varchar(25),
IsPhysician		Char(1) check (IsPhysician in ('Y','N')),
IsEmployee			Char(1) check (IsEmployee in ('Y','N')),
IsVolunteer		Char(1) check (IsVolunteer in ('Y','N')),
IsPatient			Char(1) check (IsPatient in ('Y','N')));

CREATE TABLE Physician_T (
PhysicianID	Varchar(5) Constraint PhyPhysID_PK primary key 
	Constraint PhyPhysID_FK references Person_T(PersonID),
DEANo		Varchar(20),
PagerNo		Varchar(14),
Specialty		Varchar(20));

CREATE TABLE Patient_T (
PatientID		Varchar(5) Constraint PatPatID_PK primary key
	Constraint PatPatID_FK references Person_T(PersonID),
ContactDate	Date,
ECLastName	Varchar(20),
ECFirstName	Varchar(20),
ECRelationship	Varchar(15),
ECAddress		Varchar(55),
ECPhone		Varchar(14),
CompanyName	Varchar(25),
PolicyNo		Varchar(20),
GroupNo		Varchar(15),
CompanyPhone	Varchar(14),
SubLastName	Varchar(20),
SubFirstName	Varchar(20),
SubRelationship	Varchar(15),
SubAddress		Varchar(55),
SubPhone		Varchar(14),
IsOutpatient	Char(1) check (IsOutpatient in ('Y','N')),
IsResident		Char(1) check (IsResident in ('Y','N')),
AdmitPhys		Varchar(5) 
	Constraint PatAdPhys_FK references Physician_T(PhysicianID),
ReferPhys		Varchar(5) 
	Constraint PatRefPhys_FK references Physician_T(PhysicianID));

CREATE TABLE Employee_T (
EmpID		Varchar(5) Constraint EmpEmpID_PK primary key 
	Constraint EmpEmpID_FK references Person_T(PersonID),
DateHired		Date,
EmpType		Char(1) check (EmpType in ('N','S','T')));



CREATE TABLE Volunteer_T (
VolID			Varchar(5) Constraint VolVolID_PK primary key 
	Constraint Vol_VolID_FK references Person_T(PersonID),
HadFelony		Char(1) check (HadFelony in ('Y','N')),
FelonyExplanation Varchar(50),
VECLastName	Varchar(20),
VECFirstName	Varchar(20),
VECRelationship	Varchar(15),
VECAddress		Varchar(55),
VECPhone		Varchar(14),
VEmployer		Varchar(25),
VEmpoyerAddr	Varchar(55),
VEmployPosition	Varchar(20),
VEmployStartDate	Date,
VEmployEndDate		Date,
HadMVCHSvc	Char(1) check (HadMVCHSvc in ('Y','N')),
HadVolExp		Char(1) check (HadVolExp in ('Y','N')),
WhyVolunteer	Varchar(50));

CREATE TABLE Facility_T (
FacilityID		Varchar(10) Constraint FacFID_PK primary key,
FacilityName	Varchar(40));

CREATE TABLE Vendor_T (
VendorID		Varchar(5) Constraint VenVID_PK primary key,
VendorName		Varchar(40));

CREATE TABLE Item_T (
ItemNo			Varchar(5) Constraint ITMINO_PK primary key,
ItemDesc		Varchar(40),
UnitCost		Number(7,2));

CREATE TABLE Diagnosis_T (
DiagnosisCode	Varchar(5) Constraint DxDxCode_PK primary key,
DiagnosisName	Varchar(60));

CREATE TABLE Outpatient_T (
OPatientID	Varchar(5) Constraint OPOPID_PK primary key 
	Constraint OPOPID_FK references Patient_T(PatientID));

CREATE TABLE Visit_T (
VisitNo		Varchar(5) Constraint VISVNO_PK primary key,
OPatientID	Varchar(5) 
	Constraint VISOPID_FK references Outpatient_T(OPatientID),
VisitDate		Date,
VisitTime		Timestamp,
VisitReason	Varchar(50));

CREATE TABLE Nurse_T (
NurseID		Varchar(5) Constraint NURNID_PK primary key 
	Constraint NURNID_FK references Employee_T(EmpID),
CertDegree		Varchar(5),
StateLicenseNo	Varchar(15),
NurseSpecialty	Varchar(20),
NurseType		Char(1) check (NurseType in ('R','L')));


CREATE TABLE RN_T (
RNID		Varchar(5) Constraint RNRNID_PK primary key 
	Constraint RNRNID_FK references Nurse_T(NurseID));

CREATE TABLE LPN_T (
LPNID		Varchar(5) Constraint LPNLPNID_PK primary key 
	Constraint LPNLPNID_FK references Nurse_T(NurseID),
Supervisor	Varchar(5) Constraint LPNRNID_FK references RN_T(RNID));

CREATE TABLE WorkUnit_T (
UnitName	Varchar(20) Constraint WUUName_PK primary key,
Floor		Varchar(3),
FacilityID	Varchar(10) 
	Constraint WUFID_FK references Facility_T(FacilityID),
UnitType		Char(2) check (UnitType in ('CC','DU')));

CREATE TABLE CareCenter_T (
CCUnitName 	Varchar(20) constraint CCCCNAME_PK primary key
	Constraint CCCCNAME_FK references WorkUnit_T(UnitName),
DayInCharge	Varchar(5) Constraint CCAMID_FK references RN_T(RNID),
NightInCharge	Varchar(5) 
Constraint CCPMID_FK references RN_T(RNID));

CREATE TABLE Room_T (
RoomNo		Varchar(5) Constraint ROOMRNO_PK primary key,
CCUnitName Varchar(20) 
	Constraint ROOMCC_FK references CareCenter_T(CCUnitName));

CREATE TABLE Bed_T (
BedNo		Varchar(5),
RoomNo		Varchar(5) 
Constraint BEDRNO_FK references Room_T(RoomNo),
	Constraint BedPK primary key(RoomNo,BedNo));

CREATE TABLE Resident_T (
RPatientID	Varchar(5) Constraint RESRID_PK primary key 
	Constraint RESRID_FK references Patient_T(PatientID),
DateAdmitted	Date,
DateDischarged	Date,
BedNo			Varchar(3),
RoomNo			Varchar(5),
Constraint RESRoomBed_FK foreign key(RoomNo, BedNo) 
	references Bed_T(RoomNo, BedNo));

CREATE TABLE PhysicianDx_T (
PDID		Varchar(5) Constraint PDXPDID_PK primary key,
DiagnosisDate	Date,
DiagnosisTime	Timestamp,
DiagnosisCode	Varchar(5) 
	Constraint PDXDXCODE_FK references Diagnosis_T(DiagnosisCode),
PhysicianID	Varchar(5) 
	Constraint PDXPHYID_FK references Physician_T(PhysicianID),
PatientID		Varchar(5)
	Constraint PDXPATID_FK references Patient_T(PatientID));

 
CREATE TABLE CCAssignment_T (
CCAID		Varchar(5) Constraint CCACCAID_PK primary key,
AssignStart	Date,
AssignEnd		Date,
HrsWorked		Number (4,2),
NurseID	Varchar(5) 
Constraint CCANID_FK references Nurse_T(NurseID),
CCUnitName	Varchar(20) 
	Constraint CCACCNAME_FK references CareCenter_T(CCUnitName));

CREATE TABLE FieldCertification_T (
FCID	Varchar(5) Constraint FCFCID_PK primary key,
FCDescription	Varchar(30),
NurseID	Varchar(5) 
Constraint FCNID_FK references Nurse_T(NurseID));

CREATE TABLE Assessment_T (
AssessmentID	Varchar(5) Constraint ASASID_PK primary key,
AssessmentDate	Date,
AssessmentTime	Timestamp,
Comments		Varchar(50),
PatientWeight	Number(3),
PatientBP		Varchar(7),
PatientPulse	Number(4),
PatientTemperature	Number(3,2),
PatientID		Varchar(5) 
	Constraint ASPATID_FK references Patient_T(PatientID),
NurseID		Varchar(5)
	Constraint ASNID_FK references Nurse_T(NurseID));

CREATE TABLE Staff_T (
StaffID	Varchar(5) Constraint STFSTID_PK primary key
	Constraint STFSTID_FK references Employee_T(EmpID),
JobClass	Varchar(3),
UnitName	Varchar(20) 
	Constraint STFUName_FK references WorkUnit_T(UnitName));

CREATE TABLE Technician_T (
TechnicianID	Varchar(5) Constraint TTID_PK primary key
	Constraint TTID_FK references Employee_T(EmpID),
UnitName	Varchar(20)
	Constraint TUName_FK references WorkUnit_T(UnitName));

CREATE TABLE TechnicianSkill_T (
TSID	Varchar(5) Constraint TSTSID_PK primary key,
TechnicianID Varchar(5) 
	Constraint TSTID_FK references Technician_T(TechnicianID),
TSSkill	Varchar(20));

CREATE TABLE DiagnosticUnit_T (
DXUnitName	Varchar(20) Constraint DUDXName_PK primary key
	Constraint DUDXName_FK references WorkUnit_T(UnitName));


 
CREATE TABLE Treatment_T (
TrtCode	Varchar(5) Constraint TRTTCode_PK primary key,
TreatmentName	Varchar(30),
DXUnitName	Varchar(20) 
	Constraint TRTDXName_FK references DiagnosticUnit_T(DXUnitName));

CREATE TABLE Order_T (
OrderID		Varchar(5) Constraint ORORDID_PK primary key,
PatientID		Varchar(5) 
	Constraint ORPATID_FK references Patient_T(PatientID),
PhysicianID	Varchar(5) 
	Constraint ORPHYID_FK references Physician_T(PhysicianID),
ItemNo			Varchar(5)
	Constraint ORITNO_FK references Item_T(ItemNo),
OrderDate		Date,
OrderTime		Timestamp);

CREATE TABLE TreatmentOrder_T (
TOID		Varchar(5) Constraint TOTOID_PK primary key,
TrtCode	Varchar(5) 
	Constraint TOTCODE_FK references Treatment_T(TrtCode),
Results		Varchar(50),
TrtDate	Date,
TrtTime	Timestamp,
OrderID	Varchar(5) 
	Constraint TOORID_FK references Order_T(OrderID));

CREATE TABLE ItemConsumption_T (
ICID	Varchar(5) Constraint ICICID_PK primary key,
ConsumeDate	Date,
ConsumeTime	Timestamp,
ConsumeQty		Number(3),
OrderID		Varchar(5) 
Constraint ICORNO_FK references Order_T(OrderID),
ItemNo		Varchar(5) 
Constraint ICITNO_FK references Item_T(ItemNo),
PatientID	Varchar(5) 
	Constraint ICPATID_FK references Patient_T(PatientID));

CREATE TABLE ItemBilling_T (
IBID		Varchar(5) Constraint IBIBID_PK primary key,
StartDate	Date,
EndDate	Date,
Cost		Number (9,2),
ItemNo		Varchar(5) 
Constraint IBITNO_FK references Item_T(ItemNo),
RoomNo		Varchar(5) 
Constraint IBRNO_FK references Room_T(RoomNo),
PatientID	Varchar(5) 
	Constraint IBPATID_FK references Patient_T(PatientID));

CREATE TABLE Inventory_T (
InvID		Varchar (5) Constraint INVINVID_PK primary key,
ItemNo		Varchar(5) 
Constraint INVITNO_FK references Item_T(ItemNo),
VendorID	Varchar(5) 
Constraint INVVID_FK references Vendor_T(VendorID));
 
CREATE TABLE Schedule_T (
ScheduleID		Varchar(5) Constraint SCHSCHID_PK primary key,
SchedBegin		Date,
SchedEnd		Date,
PhysicianID	Varchar(5) 
Constraint SCHPHYID_FK references Physician_T(PhysicianID),
FacilityID		Varchar(10)
	Constraint SCHFID_FK references Facility_T(FacilityID));

CREATE TABLE VolMVCHService (
VMSID		Varchar(5) Constraint VMSVMSID_PK primary key,
MVCHServiceInfo	Varchar(25),
VolID		Varchar (5)
	Constraint VMSVID_FK references Volunteer_T(VolID));

CREATE TABLE VolRefInfo (
VRIID		Varchar(5) Constraint VRIVRID_PK primary key,
VRILastName	Varchar(20),
VRIFirstName	Varchar(20),
VRIRelationship	Varchar(15),
VRIPhone		Varchar(14),
VRIAddress		Varchar(20),
VRICity		Varchar(20),
VRIState		Varchar(2),
VRIZip		Varchar(10),
VolID		Varchar (5)
	Constraint VRIVID_FK references Volunteer_T(VolID));

CREATE TABLE VolExperience_T (
VEID		Varchar(5) Constraint VEVEID_PK primary key,
VolunteerExpInfo	Varchar(25),
VolID		Varchar (5)
	Constraint VEVID_FK references Volunteer_T(VolID));

CREATE TABLE VolLanguage_T (
VLID		Varchar(5) Constraint VLVLID_PK primary key,
Language	Varchar(15),
VolID		Varchar (5)
	Constraint VLVID_FK references Volunteer_T(VolID));

CREATE TABLE VolSkill_T (
VSID		Varchar(5) Constraint VSVSID_PK primary key,
Skill		Varchar(25),
VolID		Varchar (5)
	Constraint VSVID_FK references Volunteer_T(VolID));

CREATE TABLE VolInterest_T (
VIID		Varchar(5) Constraint VIVID_PK primary key,
Interest	Varchar(25),
VolID		Varchar (5)
	Constraint VIVID_FK references Volunteer_T(VolID));

 
CREATE TABLE VolAvailability_T (
VAID		Varchar(5) Constraint VAVAID_PK primary key,
DayOfWeek	Varchar(8),
PortionOfDay	Varchar(15),
VolID		Varchar (5)
	Constraint VAVID_FK references Volunteer_T(VolID));

CREATE TABLE VolServHistory_T (
VSHID	Varchar(5) Constraint VSHVSHID_PK primary key,
ServiceBeginDate	Date,
ServiceEndDate		Date,
ServiceHrsWorked	Varchar(3),
VolID	Varchar(5) Constraint VSH_VID_FK references Volunteer_T(VolID),
UnitName	Varchar(15) 
Constraint VSHUName_FK references WorkUnit_T(UnitName),
PhysicianID Varchar(5)
	Constraint VSHPhyID_FK references Physician_T(PhysicianID),
EmpID	Varchar(5)
	Constraint VSHEmpID_FK references Employee_T(EmpID));