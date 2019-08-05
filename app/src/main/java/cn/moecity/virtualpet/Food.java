package cn.moecity.virtualpet;

public class Food {
    private String foodName = "NoNme";
    private int plevelStart = 0, plevelEnd = 0, fCoast = 0, fHung = 0, fExp = 0;

    public Food() {
    }

    public Food(String foodName, int plevelStart, int plevelEnd, int fCoast,
                int fHung, int fExp) {
        super();
        this.foodName = foodName;
        this.plevelStart = plevelStart;
        this.plevelEnd = plevelEnd;
        this.fCoast = fCoast;
        this.fHung = fHung;
        this.fExp = fExp;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPlevelStart() {
        return plevelStart;
    }

    public void setPlevelStart(int plevelStart) {
        this.plevelStart = plevelStart;
    }

    public int getPlevelEnd() {
        return plevelEnd;
    }

    public void setPlevelEnd(int plevelEnd) {
        this.plevelEnd = plevelEnd;
    }

    public int getfCoast() {
        return fCoast;
    }

    public void setfCoast(int fCoast) {
        this.fCoast = fCoast;
    }

    public int getfHung() {
        return fHung;
    }

    public void setfHung(int fHung) {
        this.fHung = fHung;
    }

    public int getfExp() {
        return fExp;
    }

    public void setfExp(int fExp) {
        this.fExp = fExp;
    }

}
