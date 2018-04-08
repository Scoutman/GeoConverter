package at.mlakar.geoconverter.geojson.model;

import java.util.ArrayList;
import java.util.List;

public class MGeojson
{
	private List<MFeature> featuresList = new ArrayList<>();

	public List<MFeature> getFeaturesList()
	{
		return featuresList;
	}

	public void setFeatureList(List<MFeature> featureList)
	{
		featuresList = featureList;
	}
}
