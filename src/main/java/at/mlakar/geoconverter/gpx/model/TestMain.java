package at.mlakar.geoconverter.gpx.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
		MWaypoint wp1 = new MWaypoint(new Double(47.32923199357016), new Double(15.362652534697418));
		wp1.setName("Mixnitz");
		
		MWaypoint wp2 = new MWaypoint(new Double(47.36261372836141), new Double(15.425823921416168));
		wp2.setName("Hochlantsch");

		MRoute route = new MRoute();
		route.addWaypoint(wp1);
		route.addWaypoint(wp2);
		route.setName("Tolle erste Route");

		MTrack track = new MTrack();
		MTrackSegment trackSegment = new MTrackSegment();
		
		trackSegment.addWaypoint(wp1);
		trackSegment.addWaypoint(wp2);
		
		track.setName("Ein aufgezeichneter Track");
		track.addSegment(trackSegment);
		track.addSegment(trackSegment);
		
		// create GPX
		MGpx gpx = new MGpx();
		gpx.setVersion("1.1");
		gpx.addWaypoint(wp1);
		gpx.addWaypoint(wp2);
		gpx.addRoute(route);
		gpx.addTrack(track);

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

