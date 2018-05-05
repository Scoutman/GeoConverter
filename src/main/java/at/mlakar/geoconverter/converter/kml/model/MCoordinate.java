package at.mlakar.geoconverter.converter.kml.model;

public class MCoordinate
{
	private Double lat;
	private Double lon;
	private int altitude;

	public MCoordinate()
	{
	}

	public MCoordinate(Double lat, Double lon, int altitude)
	{
		this.lat = lat;
		this.lon = lon;
		this.altitude = altitude;
	}

	public Double getLat()
	{
		return lat;
	}

	public void setLat(Double lat)
	{
		this.lat = lat;
	}

	public Double getLon()
	{
		return lon;
	}

	public void setLon(Double lon)
	{
		this.lon = lon;
	}

	public int getAltitude()
	{
		return altitude;
	}

	public void setAltitude(int altitude)
	{
		this.altitude = altitude;
	}

	@Override
	public String toString()
	{
		return "Lat: " + this.lat + ", Lon: " + this.lon + ", Altitude: " + this.altitude;
	}
}
