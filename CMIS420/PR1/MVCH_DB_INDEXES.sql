CREATE INDEX DrDx_IDX on PhysicianDx_T(PhysicianID);
CREATE INDEX DxCode_IDX on PhysicianDx_T(DiagnosisCode);
CREATE INDEX PatientDx_IDX on PhysicianDx_T (PatientID);
CREATE INDEX ResDateAdm_IDX on Resident_T(DateAdmitted);
CREATE INDEX ResDateDischg_IDX on Resident_T(DateDischarged);
CREATE INDEX TrtOrderDate_IDX on TreatmentOrder_T(TrtDate);
CREATE INDEX ODate_IDX on Order_T(OrderDate);
CREATE INDEX ItemCost_IDX on Item_T(unitCost);
CREATE INDEX ConsumeQty_IDX on ItemConsumption_T(ConsumeQty);
