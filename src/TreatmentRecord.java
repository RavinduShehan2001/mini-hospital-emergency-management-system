public class TreatmentRecord {
    private int recordId;
    private int patientId;
    private String patientName;
    private String treatmentDescription;
    private String treatmentDate;

    public TreatmentRecord(int recordId, int patientId, String patientName,
                           String treatmentDescription, String treatmentDate) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.treatmentDescription = treatmentDescription;
        this.treatmentDate = treatmentDate;
    }

    public int getRecordId() {
        return recordId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    @Override
    public String toString() {
        return "Record ID: " + recordId
                + "\nPatient ID: " + patientId
                + "\nPatient Name: " + patientName
                + "\nTreatment Description: " + treatmentDescription
                + "\nTreatment Date: " + treatmentDate;
    }
}
