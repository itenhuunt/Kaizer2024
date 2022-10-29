package team.diamond.kaizer.job;


public class jobModel {

    String myname,job,descriptionjob, time, nameboss, reward;

    public jobModel() {
        //empty
    }

    public jobModel(String myname, String job, String descriptionjob, String time, String nameboss, String reward) {
        this.myname = myname;
        this.job = job;
        this.descriptionjob = descriptionjob;
        this.time = time;
        this.nameboss = nameboss;
        this.reward = reward;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDescriptionjob() {
        return descriptionjob;
    }

    public void setDescriptionjob(String descriptionjob) {
        this.descriptionjob = descriptionjob;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNameboss() {
        return nameboss;
    }

    public void setNameboss(String nameboss) {
        this.nameboss = nameboss;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
