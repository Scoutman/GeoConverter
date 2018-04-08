package at.mlakar.geoconverter.gpx.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TestMain
{

	private static final String BOOKSTORE_XML = "./bookstore-jaxb.xml";

	public static void main(String[] args) throws JAXBException, IOException
	{

		ArrayList<MWaypoint> waypointList = new ArrayList<MWaypoint>();

		// create books
		MWaypoint wp1 = new MWaypoint();

		wp1.setName("Graz");
		wp1.setLat(new Double(47.123));
		wp1.setLon(new Double(15.789));
		waypointList.add(wp1);

		MWaypoint wp2 = new MWaypoint();
		wp2.setName("Mixnitz");
		waypointList.add(wp2);
		
		//List<MRoute> routeList = new ArrayList<>();
		MRoute route = new MRoute();
		route.setWaypointList(waypointList);
		//routeList.add(route);
		
		
		// create bookstore, assigning book
		MGpx gpx = new MGpx();
		gpx.setVersion("1.1");
		gpx.setWaypointList(waypointList);
		gpx.addRouteList(route);

		// create JAXB context and instantiate marshaller

		JAXBContext context = JAXBContext.newInstance(MGpx.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(gpx, System.out);

		// Write to File
		m.marshal(gpx, new File(BOOKSTORE_XML));

		// get variables from our xml file, created before
		System.out.println();
		System.out.println("Output from our XML File: ");
		Unmarshaller um = context.createUnmarshaller();
		MGpx bookstore2 = (MGpx) um.unmarshal(new FileReader(BOOKSTORE_XML));
		
		List<MWaypoint> list = bookstore2.getWaypointsList();
		
		for (MWaypoint waypoint : list)
		{
			System.out.println("Waypoint: " + waypoint.getName());
		}
	}
}
