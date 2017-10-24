package kinesis;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class AmazonDynamoDBSample
{
	
	

	
	DynamoDB db;
	
	DynamoDBMapper dbMapper;
	
	
	public AmazonDynamoDBSample()
	{
		 AmazonDynamoDBClient client = new AmazonDynamoDBClient();
	        client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
		
	        db = new DynamoDB(client);
	        dbMapper = new DynamoDBMapper(client);
		
	}

	public void getall(String tableName)
	{
		
	}
	
	
	public String DrescribeTable(String tableName)
	{
		
		
		
		TableDescription td = db.getTable(tableName).describe();
		return td.toString();
		
		
	}
	
	public void saveOrUpdateEvent(Tweet_msg event) {

        dbMapper.save(event);
    }



    
}
