package com.example.newsapplication;

import java.io.Serializable;

public class Company implements Serializable {

    private String twitterUsername;

    public Company() {
    }

    public Company(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

}
