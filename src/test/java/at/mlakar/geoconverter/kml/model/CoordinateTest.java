package at.mlakar.geoconverter.kml.model;

import org.junit.Assert;
import org.junit.Test;

public class CoordinateTest
{
	private String coordinatesTest = "	    		15.36823720884101,47.32049307205572,0 15.39491461657788,47.32533378450854,365 15.42652123606157,47.34382570030789   ";
	
	
	@Test
	public void addCoordinatesStringTest()
	{	
		CoordinatesList coordinatesList = new CoordinatesList();
		coordinatesList.addCoordinatesString(coordinatesTest);
		
		Assert.assertEquals(new Double(15.36823720884101), coordinatesList.getCoordinateList().get(0).getLon());
		Assert.assertEquals(new Double(47.32049307205572),coordinatesList.getCoordinateList().get(0).getLat());
		Assert.assertEquals(0, coordinatesList.getCoordinateList().get(0).getAltitude());
		
		Assert.assertEquals(new Double(15.39491461657788), coordinatesList.getCoordinateList().get(1).getLon());
		Assert.assertEquals(new Double(47.32533378450854),coordinatesList.getCoordinateList().get(1).getLat());
		Assert.assertEquals(365, coordinatesList.getCoordinateList().get(1).getAltitude());

		Assert.assertEquals(new Double(15.42652123606157), coordinatesList.getCoordinateList().get(2).getLon());
		Assert.assertEquals(new Double(47.34382570030789),coordinatesList.getCoordinateList().get(2).getLat());
		Assert.assertEquals(0, coordinatesList.getCoordinateList().get(2).getAltitude());
	}

	@Test
	public void toStringTest()
	{
		CoordinatesList coordinatesList = new CoordinatesList();
		coordinatesList.addCoordinatesString(coordinatesTest);
		
		Assert.assertEquals("Lat: 47.32049307205572, Lon: 15.36823720884101, Altitude: 0", coordinatesList.getCoordinateList().get(0).toString());
	}	
	
	@Test
	public void getCoordinateListStringTest()
	{
		String expected = "15.36823720884101,47.32049307205572,0 15.39491461657788,47.32533378450854,365 15.42652123606157,47.34382570030789,0 ";
		
		CoordinatesList coordinatesList = new CoordinatesList();
		coordinatesList.addCoordinatesString(coordinatesTest);
		
		Assert.assertEquals(expected, coordinatesList.getCoordinatesListString());
	}


}
