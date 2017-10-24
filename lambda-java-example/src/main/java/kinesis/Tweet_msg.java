package kinesis;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "messages")
public class Tweet_msg  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 844253516432120601L;


	/**
	 * 
	 */
	public static final String USER_NAME = "User-Index";
	
	
	@DynamoDBHashKey
	private String id;
	
	@DynamoDBAttribute
	private String msg;
	
	@DynamoDBAttribute
	private Tweet_user user;
	
	@DynamoDBAttribute
	private String Created_at;
	@DynamoDBAttribute
	private boolean isRetweet;
	@DynamoDBAttribute
	private boolean isRetweeted;
	
	
	
	
	
	public Tweet_user getUser() {
		return user;
	}
	public void setUser(Tweet_user user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getCreated_at() {
		return Created_at;
	}
	public void setCreated_at(String created_at) {
		Created_at = created_at;
	}
	public boolean isRetweet() {
		return isRetweet;
	}
	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}
	public boolean isRetweeted() {
		return isRetweeted;
	}
	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}
	
	
	

}
