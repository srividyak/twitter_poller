package com.social.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import twitter4j.Status;

/**
 * Created by srividyak on 06/12/14.
 */
@Document(collection = "tweet")
public class Tweet {

    @Id
    private String id;

    private Status tweet;
    private String client;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getTweet() {
        return tweet;
    }

    public void setTweet(Status tweet) {
        this.tweet = tweet;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
