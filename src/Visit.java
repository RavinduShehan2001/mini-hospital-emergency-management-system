public class Visit {
    private String visitId;
    private String visitDate;
    private String doctorName;
    private String diagnosis;
    private String treatment;

    public Visit(String visitId, String visitDate, String doctorName,
                 String diagnosis, String treatment) {
        this.visitId = visitId;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public String getVisitId() {
        return visitId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    @Override
    public String toString() {
        return "Visit ID: " + visitId
                + "\nVisit Date: " + visitDate
                + "\nDoctor Name: " + doctorName
                + "\nDiagnosis: " + diagnosis
                + "\nTreatment: " + treatment;
    }
}
