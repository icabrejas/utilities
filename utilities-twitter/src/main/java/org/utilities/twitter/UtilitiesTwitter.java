package org.utilities.twitter;

import java.io.IOException;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class UtilitiesTwitter {

	public static void gettingTimeline() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = twitter.getHomeTimeline();
		System.out.println("Showing home timeline.");
		for (Status status : statuses) {
			System.out.println(status.getUser()
					.getName() + ":" + status.getText());
		}
	}

	public static void searchForTweets() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query("source:twitter4j yusukey");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser()
					.getScreenName() + ":" + status.getText());
		}
	}

	public static void main(String[] args) throws TwitterException, IOException {

		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				System.out.println(status.getUser()
						.getName() + " : " + status.getText());
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		// sample() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
		twitterStream.sample();
	}

}
