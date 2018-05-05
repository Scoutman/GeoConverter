package at.mlakar.geoconverter.webapp;

import java.util.ArrayList;
import java.util.List;

public class JsonResponse
{
	private String status = "";
	private String content = "";
	private List<String> error = new ArrayList<>();
	
	public JsonResponse()
	{}
	
	public JsonResponse(String status, String content)
	{
		this.status = status;
		this.content = content;
	}
	
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}

	public List<String> getError()
	{
		return error;
	}

	public void setError(List<String> error)
	{
		this.error = error;
	}
	
}
