package kinesisPorducer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twitter4j.FilterQuery;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;

public class KinesisPorducer
{
	static   AmazonKinesisRecordProducerSample ask = null ;

	public static void main(String[] args) throws InterruptedException
	{
				
			ask= new AmazonKinesisRecordProducerSample();
		 
		
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();


		StatusListener listner = new StatusListener() 
		{



			public void onStatus(Status status)
			{
				// TODO Auto-generated method stub
				JSONObject obj ;
				try {
					obj= new JSONObject();
					
					
					User u  = status.getUser();
					Gson gson = new GsonBuilder().create();
					String Userjson = gson.toJson(u);
					JSONObject jsonObj = new JSONObject(Userjson);
					obj.append("Username",jsonObj);
					obj.append("msg_id", status.getId());
					obj.append("msg", status.getText());
					obj.append("Created_at", status.getCreatedAt());
					obj.append("isRetweet", status.isRetweet());
					obj.append("isRetweeted", status.isRetweeted());				
					
					//System.out.println(obj.toString());
					//System.out.println(aws_sqs.sendMessageToQueue(url,obj.toString()));
					
						try {
							ask.PutMsgToKinesis(obj.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					

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
