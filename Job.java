package materialtest.example.user.firebase;

class Job implements Comparable<Job>{

    private String JobAddedAt = "-";
    //private String JobAddedBy = "-";
    private String JobBenefits = "-";
    //private String JobClosingDate = "-";
    private String JobContactPerson = "-";
    private String JobContactNumber = "-";
    //private String JobContactEmail = "-";
    private String JobDate = "-";
    private String JobCompany = "-";
    private String JobGender = "-";
    private String JobIndustry = "-";
    //private String JobLat = "-";
    //private String JobLong = "-";
    private String JobPay = "-";
    private String JobRequirement = "-";
    private String JobResponsibilities = "-";
    private String JobTime = "-";
    //private String JobTimeBreak = "-";
    private String JobTitle = "-";
    private String JobType = "-";
    private String JobVenue = "-";
    private String JobZone = "-";
    //private String JobStatus = "-";
    private String isExperienced = "-";
    private String isYoungAdult = "-";
    private String isHighSchoolCollegeStudent = "-";

/*
    public String getJobAddedBy() {
        return JobAddedBy;
    }

    public void setJobAddedBy(String jobAddedBy) {
        JobAddedBy = jobAddedBy;
    }
*/
    public String getJobAddedAt() {
        return JobAddedAt;
    }

    public void setJobAddedAt(String jobAddedAt) {
        JobAddedAt = jobAddedAt;
    }

    public String getJobBenefits() {
        return JobBenefits;
    }

    public void setJobBenefits(String jobBenefits) {
        JobBenefits = jobBenefits;
    }
/*
    public String getJobClosingDate() {
        return JobClosingDate;
    }

    public void setJobClosingDate(String jobClosingDate) {
        JobClosingDate = jobClosingDate;
    }
*/
    public String getJobCompany() {
        return JobCompany;
    }

    public void setJobCompany(String jobCompany) {
        JobCompany = jobCompany;
    }
/*
    public String getJobContactEmail() {
        return JobContactEmail;
    }

    public void setJobContactEmail(String jobContactEmail) {
        JobContactEmail = jobContactEmail;
    }
*/
    public String getJobContactNumber() {
        return JobContactNumber;
    }

    public void setJobContactNumber(String jobContactNumber) {
        JobContactNumber = jobContactNumber;
    }

    public String getJobContactPerson() {
        return JobContactPerson;
    }

    public void setJobContactPerson(String jobContactPerson) {
        JobContactPerson = jobContactPerson;
    }

    public String getJobDate() {
        return JobDate;
    }

    public void setJobDate(String jobDate) {
        JobDate = jobDate;
    }

    public String getJobGender() {
        return JobGender;
    }

    public void setJobGender(String jobGender) {
        JobGender = jobGender;
    }

    public String getJobIndustry() {
        return JobIndustry;
    }

    public void setJobIndustry(String jobIndustry) {
        JobIndustry = jobIndustry;
    }
/*
    public String getJobLat() {
        return JobLat;
    }

    public void setJobLat(String jobLat) {
        JobLat = jobLat;
    }

    public String getJobLong() {
        return JobLong;
    }

    public void setJobLong(String jobLong) {
        JobLong = jobLong;
    }
*/
    public String getJobPay() {
        return JobPay;
    }

    public void setJobPay(String jobPay) {
        JobPay = jobPay;
    }

    public String getJobRequirement() {
        return JobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        JobRequirement = jobRequirement;
    }

    public String getJobResponsibilities() {
        return JobResponsibilities;
    }

    public void setJobResponsibilities(String jobResponsibilities) {
        JobResponsibilities = jobResponsibilities;
    }
/*
    public String getJobStatus() {
        return JobStatus;
    }

    public void setJobStatus(String jobStatus) {
        JobStatus = jobStatus;
    }
*/
    public String getJobTime() {
        return JobTime;
    }

    public void setJobTime(String jobTime) {
        JobTime = jobTime;
    }
/*
    public String getJobTimeBreak() {
        return JobTimeBreak;
    }

    public void setJobTimeBreak(String jobTimeBreak) {
        JobTimeBreak = jobTimeBreak;
    }
*/
    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getJobVenue() {
        return JobVenue;
    }

    public void setJobVenue(String jobVenue) {
        JobVenue = jobVenue;
    }

    public String getJobZone() {
        return JobZone;
    }

    public void setJobZone(String jobZone) {
        JobZone = jobZone;
    }

    public String getIsExperienced() {
        return isExperienced;
    }

    public void setIsExperienced(String isExperienced) {
        this.isExperienced = isExperienced;
    }

    public String getIsHighSchoolCollegeStudent() {
        return isHighSchoolCollegeStudent;
    }

    public void setIsHighSchoolCollegeStudent(String isHighSchoolCollegeStudent) {
        this.isHighSchoolCollegeStudent = isHighSchoolCollegeStudent;
    }

    public String getIsYoungAdult() {
        return isYoungAdult;
    }

    public void setIsYoungAdult(String isYoungAdult) {
        this.isYoungAdult = isYoungAdult;
    }

    @Override
    public int compareTo(Job o) {
        return this.JobAddedAt.compareTo(o.getJobAddedAt());
    }
}