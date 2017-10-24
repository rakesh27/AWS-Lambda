package com.amazonaws.samples;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class AmazonDynamoDBSample
{
	
	

	
	
	
	DynamoDB db;
	
	DynamoDBMapper dbm;
	
	public AmazonDynamoDBSample()
	{
		
		 AWSCredentials credentials = null;
	        try
	        {
	            credentials = new ProfileCredentialsProvider("rakesh").getCredentials();
	        } 
	        catch (Exception e)
	        {
	           System.out.println(e.getMessage());
	        }

		 AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
		 Region mumbai = Region.getRegion(Regions.AP_SOUTH_1);
	        client.setRegion(mumbai);
		
	        db = new DynamoDB(client);
		dbm = new DynamoDBMapper(client);
		
	}

	public List<Tweet_msg> getall()
	{
	
		
		return dbm.scan(Tweet_msg.class,new DynamoDBScanExpression());
		
		
	}
	
	public Tweet_msg getByMessageID(String msgid)
	{
		
		Tweet_msg exp = dbm.load(Tweet_msg.class, msgid);
		return exp;
	}
	
	/*public List<Tweet_msg> getByUserorScreenName(String username)
	{
		
		final String filterExpression1 = "contains(user.name, :name) or contains(user.screenName, :name)";
		final String filterExpression2= "user.screenName conttains :name";
		final String filterExpression3="contains(user.screenName, :nam)";
		
		final Map<String, AttributeValue> expressionAttributeValues = Collections.singletonMap(":nam", new AttributeValue().withS(username));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
		    .withFilterExpression(filterExpression3)
		    .withExpressionAttributeValues(expressionAttributeValues);
		return dbm.scan(Tweet_msg.class, scanExpression);
		
		DynamoDBScanExpression de = new DynamoDBScanExpression();
		
		de.addFilterCondition("user.id", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(Username)));
		
		return dbm.scan(Tweet_msg.class, de);
	}*/
	
	public List<Tweet_msg> getByMessage(String msg)
	{
		
		
		DynamoDBScanExpression de = new DynamoDBScanExpression();
		
		de.addFilterCondition("msg", new Condition().withComparisonOperator(ComparisonOperator.CONTAINS)
				.withAttributeValueList(new AttributeValue().withS(msg)));
		
		return dbm.scan(Tweet_msg.class, de);
	}
	
	
	public String DrescribeTable(String tableName)
	{
			
		TableDescription td = db.getTable(tableName).describe();
		return td.toString();
		
		
	}
	

    
}
