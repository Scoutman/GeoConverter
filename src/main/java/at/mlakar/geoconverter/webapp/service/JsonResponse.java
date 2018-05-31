package at.mlakar.geoconverter.webapp.service;

public class JsonResponse
{
	private Integer geodataId;
	private String type = "";
	private String geodata = "[]";
	private String error = "";
	private Integer errorCode = 0;

	public JsonResponse()
	{
	}

	public JsonResponse(Integer geodataID, String type, String geodata, String error, Integer errorCode)
	{
		this.geodataId = geodataID;
		this.type = type;
		this.geodata = geodata;
		this.error = error;
		this.errorCode = errorCode;
	}

	public Integer getGeodataId()
	{
		return geodataId;
	}

	public void setGeodataId(Integer geodataId)
	{
		this.geodataId = geodataId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getGeodata()
	{
		return geodata;
	}

	public void setGeodata(String geodata)
	{
		this.geodata = geodata;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public Integer getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(Integer errorCode)
	{
		this.errorCode = errorCode;
	}

}
