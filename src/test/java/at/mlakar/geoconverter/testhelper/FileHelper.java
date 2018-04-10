package at.mlakar.geoconverter.testhelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper
{
	public static String readFile(String file)
	{
		String json = "";

		try
		{
			json = new String(Files.readAllBytes(Paths.get(file)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public static String cleanString(String str)
	{
		str = str.trim();
		str = str.replace(" ", "");
		str = str.replace("\r\n", "");
		str = str.replace("\n", "");
		str = str.replace("\t", "");

		return str;
	}
}
