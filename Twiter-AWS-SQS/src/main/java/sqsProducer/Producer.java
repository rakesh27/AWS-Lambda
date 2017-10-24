package sqsProducer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sqs.utils.AWS_SQS;

import twitter4j.FilterQuery;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Producer
{
	
	
	
	public static void main(String[] args) throws TwitterException
	{
		
		InputStream stream  = Producer.class.getClassLoader().getResourceAsStream("AwsCredentials.properties");
		Properties properties  = new Properties();
        try 
        {
			properties.load(stream);
		} catch (FileNotFoundException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final AWS_SQS aws_sqs = new AWS_SQS(properties);
		final String url = aws_sqs.getQueueUrl("Twitter_Queue");
		System.out.println("SQS URL is  : "+url);

		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();


		StatusListener listner = new StatusListener() 
		{



			public void onStatus(Status status)
			{
				// TODO Auto-generated method stub
				JSONObject obj ;
				try {
					obj= new JSONObject();
					obj.append("Username", status.getUser());
					obj.append("msg", status.getText());
					obj.append("Created_at", status.getCreatedAt());
					obj.append("isRetweet", status.isRetweet());
					obj.append("isRetweeted", status.isRetweeted());				

					//System.out.println(obj.toString());
					System.out.println(aws_sqs.sendMessageToQueue(url,obj.toString()));


				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(status.getText());

			}

			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub

			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}


		};

		twitterStream.addListener(listner);			
		twitterStream.sample();
		FilterQuery fq = new FilterQuery();
		fq.track(new String[]{"BCCI","IND","AUS","Ind","Aus"});
		fq.language(new String[]{"en"});
		twitterStream.filter(fq);
	}


}
