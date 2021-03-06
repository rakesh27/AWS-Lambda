package kinesis;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent.KinesisEventRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Handler implements RequestHandler<KinesisEvent, Integer> 
{
	
    public Integer handleRequest(KinesisEvent event, Context context)
    {
    	context.getLogger().log("Input: " + event);
    	AmazonDynamoDBSample ads  = new AmazonDynamoDBSample();
    	context.getLogger().log("Table Description : "+ads.DrescribeTable("messages"));
    	
    	
    	JsonParser jsonparser = new JsonParser();
    	
    	
    	
        for (KinesisEventRecord record : event.getRecords())
        {
            String payload = new String(record.getKinesis().getData().array());
            context.getLogger().log("Payload: " + payload);
            
            JsonObject jsonObj = jsonparser.parse(payload).getAsJsonObject();
            
            Tweet_msg msg = new Tweet_msg();
           msg.setId(jsonObj.get("msg_id").getAsString());
           msg.setMsg(jsonObj.get("msg").getAsString());
       
           msg.setCreated_at(jsonObj.get("Created_at").getAsString());
           msg.setRetweet(jsonObj.get("isRetweet").getAsBoolean());
           msg.setRetweeted(jsonObj.get("isRetweeted").getAsBoolean());           
           context.getLogger().log("Username: " + jsonObj.get("Username").toString());           
           Tweet_user user = new Tweet_user(); 
           JsonElement username = jsonObj.get("Username");
           if(username.isJsonArray())
           {
        	   context.getLogger().log("Username: is array ");    
           }
           if(username.isJsonObject())
           {
        	   context.getLogger().log("Username: is object " );    
           }
           JsonArray userarray =  (JsonArray) jsonparser.parse(jsonObj.get("Username").toString());
           JsonObject userobj = userarray.get(0).getAsJsonObject();
           user.setId(userobj.get("id").getAsString());
           user.setName(userobj.get("name").getAsString());
           user.setScreenName(userobj.get("screenName").getAsString());           
           user.setFollowersCount(userobj.get("followersCount").getAsInt());
           user.setFriendsCount(userobj.get("friendsCount").getAsInt());
           user.setVerified(userobj.get("isVerified").getAsBoolean());         
           
           
          
           msg.setUser(user);
           
           ads.saveOrUpdateEvent(msg);
        }
        return event.getRecords().size();
    }
}