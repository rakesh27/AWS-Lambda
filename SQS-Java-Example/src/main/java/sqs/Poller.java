package sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.sqs.model.Message;

public class Poller
{

    public void handleRequest(Object input, Context context)
    {
        context.getLogger().log("Cron Started input is  : ");
       
		
		
        AWS_SQS sqs = new AWS_SQS();
        String url = sqs.getQueueUrl("Twitter_Queue");
        
        for(Message m : sqs.getMessagesFromQueue(url))
        {
        	
        	context.getLogger().log("From SQS: "+sqs.deleteMessageFromQueue(url, m) );
        }    
        
        context.getLogger().log("Cron ended");
    }
}
