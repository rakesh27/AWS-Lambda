



import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

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

public class SearchTweets
{
   
	public static void main(String[] args) throws TwitterException
	{
		
		
		Properties properties=new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		@SuppressWarnings("resource")
		final KafkaProducer<String,String> myProducer= new KafkaProducer<String,String>(properties);
		
		
		
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        
       
        StatusListener listner = new StatusListener() 
        {
			
			
			
			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				JSONObject obj ;
				try {
					obj= new JSONObject();
					obj.append("Username", status.getUser());
					obj.append("msg", status.getText());
					obj.append("Created_at", status.getCreatedAt());
					obj.append("isRetweet", status.isRetweet());
					obj.append("isRetweeted", status.isRetweeted());				
					
					System.out.println(obj.toString());
					ProducerRecord<String, String> record  = new ProducerRecord<String, String>("tcs-poc", obj.toString());
					myProducer.send(record);

					
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
