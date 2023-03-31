package com.example.movielibrary.Models.UserRatings;

public class DemographicRatings {

    private Ratings allAges;
    private Ratings agesUnder18;
    private Ratings ages18To29;
    private Ratings ages30To44;
    private Ratings agesOver45;

    public Ratings getAllAges() {
        if(allAges == null)
            return new Ratings();

        return allAges;
    }

    public void setAllAges(Ratings allAges) {
        this.allAges = allAges;
    }

    public Ratings getAgesUnder18() {
        if(allAges == null)
            return new Ratings();

        return agesUnder18;
    }

    public void setAgesUnder18(Ratings agesUnder18) {
        this.agesUnder18 = agesUnder18;
    }

    public Ratings getAges18To29() {
        if(allAges == null)
            return new Ratings();

        return ages18To29;
    }

    public void setAges18To29(Ratings ages18To29) {
        this.ages18To29 = ages18To29;
    }

    public Ratings getAges30To44() {
        if(allAges == null)
            return new Ratings();

        return ages30To44;
    }

    public void setAges30To44(Ratings ages30To44) {
        this.ages30To44 = ages30To44;
    }

    public Ratings getAgesOver45() {
        if(allAges == null)
            return new Ratings();

        return agesOver45;
    }

    public void setAgesOver45(Ratings agesOver45) {
        this.agesOver45 = agesOver45;
    }
}
