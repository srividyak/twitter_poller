package com.social;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by srividyak on 06/12/14.
 */
@Component
public class TriggerApp {

    public static String client = "flipkart";

    @Autowired
    private TweetListener tweetListener;

    public void startTweetListener(String consumerKey, String consumerSecret, String token, String secret) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        List<String> trackingTerms = new ArrayList<String>();

        // to track tweets having client name in text
        trackingTerms.add(client);

        endpoint.trackTerms(trackingTerms);
        endpoint.stallWarnings(false);

        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        BasicClient client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        int numProcessingThreads = 5;
        ExecutorService service = Executors.newFixedThreadPool(numProcessingThreads);

        Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(client, queue, Lists.newArrayList(tweetListener), service);

        t4jClient.connect();

        for (int i = 0; i < numProcessingThreads; i++) {
            t4jClient.process();
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        TriggerApp app = context.getBean(TriggerApp.class);
        String consumerKey = "yt5Tr4D7EG1tPBiBy5cTQ";
        String consumerSecret = "cHnPaX1hre0Iq1PBKn68Za6tmD1P5FOenytW578La8";
        String token = "45552901-FMaYcvNtOPoB5MrqhVwoVj6PtmmzJVvHfIH2tdpnl";
        String secret = "vNu2u7JFlF2pCpXlRRisMoHQTem7me5OYkFKg82PtWgPT";
        app.startTweetListener(consumerKey, consumerSecret, token, secret);
    }
}
