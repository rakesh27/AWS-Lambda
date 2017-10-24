package sqs_poller;

import java.util.List;
import java.util.Properties;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class AWS_SQS
{

	//private BasicAWSCredentials credentials;
	private AmazonSQS sqs;



	public  AWS_SQS( Properties properties)
	{
		/*this.credentials = new   BasicAWSCredentials(properties.getProperty("accessKey"),
				properties.getProperty("secretKey"));

		this.sqs = new AmazonSQSClient(this.credentials);

		this.sqs.setEndpoint(properties.getProperty("endPoint"));    */
	
		this.sqs = AmazonSQSClientBuilder.defaultClient();

	}

	public String getQueueUrl(String queueName)
	{
		GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName);
		return this.sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl();
	}


	public ListQueuesResult listQueues()
	{
		return this.sqs.listQueues();
	}


	public String sendMessageToQueue(String queueUrl, String message)
	{
		SendMessageResult messageResult =  this.sqs.sendMessage(new SendMessageRequest(queueUrl, message));
		return messageResult.toString();
	}

	public List<Message> getMessagesFromQueue(String queueUrl)
	{
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
		List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
		return messages;
	}

	public String deleteMessageFromQueue(String queueUrl, Message message)
	{
		String messageRecieptHandle = message.getReceiptHandle();	        
		sqs.deleteMessage(new DeleteMessageRequest(queueUrl, messageRecieptHandle));
		return ("message deleted : " + message.getBody() + "." + message.getReceiptHandle());
	}


}
