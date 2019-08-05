package cn.moecity.virtualpet;

public class DataMethods {

    public static Pet bathMethod(Pet myPet) {
        switch (myPet.getpLevel()) {
            case 1:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 1);
                myPet.setExperience(myPet.getExperience() + 10);
                break;

            case 2:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 2);
                myPet.setExperience(myPet.getExperience() + 10);
                break;

            case 3:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 3);
                myPet.setExperience(myPet.getExperience() + 10);
                break;
            case 4:
            case 5:
            case 6:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 4);
                myPet.setExperience(myPet.getExperience() + 10);
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 3);
                myPet.setExperience(myPet.getExperience() + 15);
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 4);
                myPet.setExperience(myPet.getExperience() + 20);
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 5);
                myPet.setExperience(myPet.getExperience() + 20);
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 8);
                myPet.setExperience(myPet.getExperience() + 25);
                break;
            case 26:
            case 27:
            case 28:
            case 29:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 10);
                myPet.setExperience(myPet.getExperience() + 30);
                break;
            case 30:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 20);
                myPet.setExperience(myPet.getExperience() + 40);
                break;
        }
        if (myPet.getDirtyPoint() < 0)
            myPet.setDirtyPoint(0);
        levelCheck(myPet);
        return myPet;
    }

    public static Pet cleanMethod(Pet myPet) {
        switch (myPet.getpLevel()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 1);
                myPet.setExperience(myPet.getExperience() + 10);
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 2);
                myPet.setExperience(myPet.getExperience() + 20);
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 3);
                myPet.setExperience(myPet.getExperience() + 30);
                break;
            case 21:
            case 22:
            case 23:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 4);
                myPet.setExperience(myPet.getExperience() + 40);
                break;
            case 24:
            case 25:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 5);
                myPet.setExperience(myPet.getExperience() + 50);
                break;
            case 26:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 6);
                myPet.setExperience(myPet.getExperience() + 60);
                break;
            case 27:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 7);
                myPet.setExperience(myPet.getExperience() + 70);
                break;
            case 28:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 8);
                myPet.setExperience(myPet.getExperience() + 80);
                break;
            case 29:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 9);
                myPet.setExperience(myPet.getExperience() + 90);
                break;
            case 30:
                myPet.setDirtyPoint(myPet.getDirtyPoint() - 10);
                myPet.setExperience(myPet.getExperience() + 100);
                break;
        }
        if (myPet.getDirtyPoint() < 0)
            myPet.setDirtyPoint(0);
        levelCheck(myPet);
        return myPet;
    }

    public static Pet levelCheck(Pet myPet) {
        int exp = myPet.getExperience();
        int levelTemp = myPet.getpLevel();
        if (exp >= 80000)
            myPet.setpLevel(30);
        else if (exp >= 50000)
            myPet.setpLevel(29);
        else if (exp >= 40000)
            myPet.setpLevel(28);
        else if (exp >= 34000)
            myPet.setpLevel(27);
        else if (exp >= 30000)
            myPet.setpLevel(26);
        else if (exp >= 28000)
            myPet.setpLevel(25);
        else if (exp >= 26000)
            myPet.setpLevel(24);
        else if (exp >= 24000)
            myPet.setpLevel(23);
        else if (exp >= 22000)
            myPet.setpLevel(22);
        else if (exp >= 20000)
            myPet.setpLevel(21);
        else if (exp >= 13400)
            myPet.setpLevel(20);
        else if (exp >= 12400)
            myPet.setpLevel(19);
        else if (exp >= 11400)
            myPet.setpLevel(18);
        else if (exp >= 10400)
            myPet.setpLevel(17);
        else if (exp >= 9400)
            myPet.setpLevel(16);
        else if (exp >= 8400)
            myPet.setpLevel(15);
        else if (exp >= 7400)
            myPet.setpLevel(14);
        else if (exp >= 6400)
            myPet.setpLevel(13);
        else if (exp >= 5400)
            myPet.setpLevel(12);
        else if (exp >= 4400)
            myPet.setpLevel(11);
        else if (exp >= 3400)
            myPet.setpLevel(10);
        else if (exp >= 2800)
            myPet.setpLevel(9);
        else if (exp >= 2200)
            myPet.setpLevel(8);
        else if (exp >= 1600)
            myPet.setpLevel(7);
        else if (exp >= 1000)
            myPet.setpLevel(6);
        else if (exp >= 500)
            myPet.setpLevel(5);
        else if (exp >= 400)
            myPet.setpLevel(4);
        else if (exp >= 300)
            myPet.setpLevel(3);
        else if (exp >= 200)
            myPet.setpLevel(2);
        if (myPet.getpLevel() != levelTemp) {
            myPet.setDirtyPoint(1);
            myPet.setIllPoint(0);
            myPet.setHungPoint(1);
            updateInfo(myPet);
        }
        return myPet;
    }

    private static Pet updateInfo(Pet myPet) {
        if (myPet.getpLevel() >= 24)
            myPet.setpSkill(8);
        else if (myPet.getpLevel() >= 22)
            myPet.setpSkill(7);
        else if (myPet.getpLevel() >= 16)
            myPet.setpSkill(6);
        else if (myPet.getpLevel() >= 14)
            myPet.setpSkill(5);
        else if (myPet.getpLevel() >= 12)
            myPet.setpSkill(4);
        else if (myPet.getpLevel() >= 9)
            myPet.setpSkill(3);
        else if (myPet.getpLevel() >= 8)
            myPet.setpSkill(2);
        else if (myPet.getpLevel() >= 5)
            myPet.setpSkill(1);
        return myPet;
    }
}
