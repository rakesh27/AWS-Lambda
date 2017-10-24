package sqsPoller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.services.sqs.model.Message;
import com.sqs.utils.AWS_SQS;

import sqsProducer.Producer;
import twitter4j.TwitterException;

public class Poller
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


		for(Message m : aws_sqs.getMessagesFromQueue(url))
		{
			System.out.println(m.getBody());
			System.out.println("From SQS: "+aws_sqs.deleteMessageFromQueue(url, m) );
		}    


	}
}
