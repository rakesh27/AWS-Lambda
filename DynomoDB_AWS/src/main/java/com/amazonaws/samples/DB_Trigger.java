package com.amazonaws.samples;

import java.util.Arrays;

import com.amazonaws.services.lambda.runtime.Context;




public class DB_Trigger
{
	
	    public void handleRequest(Object input, Context context)
	    {
	        context.getLogger().log("DynomoDB Started ");	       
			AmazonDynamoDBSample ads  = new AmazonDynamoDBSample();
			context.getLogger().log(ads.DrescribeTable("SampleTable"));	        
	        context.getLogger().log("DynomoDB ended");
	    }
	    
	    public static void main(String[] args)
	    {
	    	AmazonDynamoDBSample ads  = new AmazonDynamoDBSample();
	    	System.out.println(ads.DrescribeTable("messages"));
	    	
	    	System.out.println(ads.getall().size());
	    	
	    	
	    	System.out.println(ads.getByMessage("ind").get(0).getMsg());
	    	
	    	System.out.println(ads.getByMessageID("919529622085050369").getCreated_at());
	    }
	    
	    
	

}