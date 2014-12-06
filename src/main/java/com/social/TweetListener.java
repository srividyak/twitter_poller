package com.social;

import com.social.documents.Tweet;
import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

/**
 * Created by srividyak on 06/12/14.
 */
@Component
public class TweetListener implements StatusStreamHandler {

    @Autowired
    private MongoOperations operations;

    @Override
    public void onDisconnectMessage(DisconnectMessage message) {

    }

    @Override
    public void onStallWarningMessage(StallWarningMessage warning) {

    }

    @Override
    public void onUnknownMessageType(String msg) {

    }

    @Override
    public void onStatus(Status status) {
        Tweet tweet = new Tweet();
        tweet.setClient("flipkart");
        tweet.setTweet(status);
        operations.save(tweet);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {

    }

    @Override
    public void onStallWarning(StallWarning warning) {

    }

    @Override
    public void onException(Exception ex) {

    }
}
