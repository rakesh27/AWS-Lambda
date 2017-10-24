package com.sqs.utils;

import java.util.List;
import java.util.Properties;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class AWS_SQS
{
	   private BasicAWSCredentials credentials;
	   private AmazonSQS sqs;
	   

	  
	    public  AWS_SQS( Properties properties)
	    {
	    		
	         
	            this.credentials = new   BasicAWSCredentials(properties.getProperty("accessKey"),
	                                                         properties.getProperty("secretKey"));
	          
	            this.sqs = new AmazonSQSClient(this.credentials);
	         
	            this.sqs.setEndpoint(properties.getProperty("endPoint"));            
	           

	       
	    }

	    
	 

	    /**
	     * returns the queueurl for for sqs queue if you pass in a name
	     * @param queueName
	     * @return
	     */
	    public String getQueueUrl(String queueName)
	    {
	        GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName);
	        return this.sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl();
	    }

	    /**
	     * lists all your queue.
	     * @return
	     */
	    public ListQueuesResult listQueues()
	    {
	       return this.sqs.listQueues();
	    }

	    /**
	     * send a single message to your sqs queue
	     * @param queueUrl
	     * @param message
	     */
	    public String sendMessageToQueue(String queueUrl, String message)
	    {
	        SendMessageResult messageResult =  this.sqs.sendMessage(new SendMessageRequest(queueUrl, message));
	       return messageResult.toString();
	    }

	    /**
	     * gets messages from your queue
	     * @param queueUrl
	     * @return
	     */
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
