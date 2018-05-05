package at.mlakar.geoconverter.webapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Geodata
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String type;
	@Column(columnDefinition = "TEXT")
	private String geodata;
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
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
	
}
