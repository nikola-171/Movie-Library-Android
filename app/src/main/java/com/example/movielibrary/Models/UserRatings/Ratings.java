package com.example.movielibrary.Models.UserRatings;

public class Ratings {

    private String rating;
    private String votes;

    public String getRating()
    {
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
}
