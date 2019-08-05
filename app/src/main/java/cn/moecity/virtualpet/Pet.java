package cn.moecity.virtualpet;


/**
 * Created by Tony on 2017/11/4.
 */

public class Pet {

    private int uID;
    private String pName;
    private int pLevel;
    private long cleanTime;
    private long feedTime;
    private long bathTime;
    private int experience;
    private int dirtyPoint;
    private int illPoint;
    private int hungPoint;
    private int pSkill;

    public Pet() {
        setuID(0);
        setpName("NoName");
        setpLevel(0);
        setFeedTime(0);
        setBathTime(0);
        setCleanTime(0);
        setExperience(0);
        setDirtyPoint(0);
        setIllPoint(0);
        setHungPoint(0);
        setpSkill(0);
    }

    public Pet(int uID, String pName,int pLevel, int cleanTime, int feedTime, int bathTime,
               int experience, int dirtyPoint, int illPoint, int hungPoint,
               int pSkill) {
        this.uID = uID;
        this.pName=pName;
        this.pLevel = pLevel;
        this.cleanTime = cleanTime;
        this.feedTime = feedTime;
        this.bathTime = bathTime;
        this.experience = experience;
        this.dirtyPoint = dirtyPoint;
        this.illPoint = illPoint;
        this.hungPoint = hungPoint;
        this.pSkill = pSkill;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public int getpLevel() {
        return pLevel;
    }

    public void setpLevel(int pLevel) {
        this.pLevel = pLevel;
    }

    public long getCleanTime() {
        return cleanTime;
    }

    public void setCleanTime(long cleanTime) {
        this.cleanTime = cleanTime;
    }

    public long getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(long feedTime) {
        this.feedTime = feedTime;
    }

    public long getBathTime() {
        return bathTime;
    }

    public void setBathTime(long bathTime) {
        this.bathTime = bathTime;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getDirtyPoint() {
        return dirtyPoint;
    }

    public void setDirtyPoint(int dirtyPoint) {
        this.dirtyPoint = dirtyPoint;
    }

    public int getIllPoint() {
        return illPoint;
    }

    public void setIllPoint(int illPoint) {
        this.illPoint = illPoint;
    }

    public int getpSkill() {
        return pSkill;
    }

    public void setpSkill(int pSkill) {
        this.pSkill = pSkill;
    }

    public int getHungPoint() {
        return hungPoint;
    }

    public void setHungPoint(int hungPoint) {
        this.hungPoint = hungPoint;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "uID=" + uID +
                ", pName='" + pName + '\'' +
                ", pLevel=" + pLevel +
                ", cleanTime=" + cleanTime +
                ", feedTime=" + feedTime +
                ", bathTime=" + bathTime +
                ", experience=" + experience +
                ", dirtyPoint=" + dirtyPoint +
                ", illPoint=" + illPoint +
                ", hungPoint=" + hungPoint +
                ", pSkill=" + pSkill +
                '}';
    }
}
