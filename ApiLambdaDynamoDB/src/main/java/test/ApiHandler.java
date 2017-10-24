package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ApiHandler implements RequestHandler<Map<String, String>, List<Tweet_msg>>
{

	@Override
	public List<Tweet_msg> handleRequest(Map<String, String> input, Context context)
	{
		context.getLogger().log("Call Recieved "+input);
		
		/*ServerlessOutput output  = new ServerlessOutput();

		String out = "";
		out += "http method :"+input.getHttpMethod()+"\n";
		out += "input body :"+input.getBody()+"\n";
		out += "path : "+input.getPath()+"\n";
		out += "resource  :"+input.getResource()+"\n";
		if(input.getHeaders()!= null)
		{
			out += "headers : "+input.getHeaders().toString()+"\n";
		}
		if(input.getPathParameters()!= null)
		{
			out += "path params:"+input.getPathParameters().toString()+"\n";
		}
		if(input.getQueryStringParameters()!= null)
		{
			out += "query params:"+input.getQueryStringParameters().toString()+"\n";
		}

		if(input.getRequestContext()!=null)
		{
			out += "R account id : "+input.getRequestContext().getAccountId()+"\n";
			out += "R api id :"+input.getRequestContext().getApiId()+"\n";
			out += "R http method :"+input.getRequestContext().getHttpMethod()+"\n";
			out += "R resource path : "+input.getRequestContext().getResourcePath()+"\n";
			out += "R resource id : "+input.getRequestContext().getResourceId()+"\n";
			out += "R request id :"+input.getRequestContext().getRequestId()+"\n";
			if(input.getRequestContext().getIdentity()!= null)
			{
				out += "R identity id :"+input.getRequestContext().getIdentity().toString()+"\n";

			}
		}
		context.getLogger().log("Out :  "+out);*/
		
		DBSample db = new DBSample();
		
		if(input.containsKey("id"))
		{
			List<Tweet_msg> ts = new ArrayList<>();
			if(db.getByMessageID(input.get("id"))!=null)
				ts.add(db.getByMessageID(input.get("id")));
			return ts;
		}

		return db.getall();
	}

}
