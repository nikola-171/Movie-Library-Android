package com.example.movielibrary.Models.UserRatings;

public class AllRatingsItem {
    private String rating;
    private String votes;
    private String percent;

    public String getRating() {
        if(rating == null)
            return "/";

        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVotes() {
        if(votes == null)
            return "/";

        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getPercent() {
        if(percent == null)
            return "/";

        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
