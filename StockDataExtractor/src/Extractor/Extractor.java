package Extractor;

import java.io.*;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

// Found this on github by GeorgeShao, it reads html page and extracts the historical info... use the url and play
// with yahoo, you can see how to change period etc... then can update the url below if want to incorporate that
// into the program
public class Extractor {
	public static void main(String[] args) throws IOException {

		Day[] StockData = new Day[1]; // Important object array that holds all stock data
		DecimalFormat formatter = new DecimalFormat("#.####");

		// Establish connection to Yahoo Finance
		URL url = new URL("https://finance.yahoo.com/quote/AAPL/history?p=AAPL");
		URLConnection urlConn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return;
		}

		// Get stock data from Yahoo Finance as a string
		String entirePage = "";
		String line = br.readLine();
		while (line != null) {
			entirePage = entirePage + line;
			line = br.readLine();
		}
		String trimPage = entirePage.substring(entirePage.indexOf("HistoricalPriceStore") + 32);
		trimPage = trimPage.substring(0, trimPage.indexOf("]") + 1);
		System.out.println("Extracted Page Data:");
		System.out.println(trimPage);

		// Format the string into JSON & get stock info from JSON & store stock info in an array
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(trimPage);
			JSONArray array = (JSONArray) obj;

			StockData = new Day[array.size()];

			System.out.println("");
			System.out.println("Formatted & Stored Stock Data:");
			System.out.println("");

			for (int i = 0; i < array.size(); i++) {
				System.out.println("Daily Stock Information: ");
				System.out.println(array.get(i));

				JSONObject obj2 = (JSONObject) array.get(i);

				System.out.println("date=" + obj2.get("date"));
				System.out.println("open=" + obj2.get("open"));
				System.out.println("high=" + obj2.get("high"));
				System.out.println("low=" + obj2.get("low"));
				System.out.println("close=" + obj2.get("close"));
				System.out.println("adjclose=" + obj2.get("adjclose"));
				System.out.println("volume=" + obj2.get("volume"));
				System.out.println("");

				StockData[i] = new Day(Long.parseLong(obj2.get("date").toString()),
						Double.parseDouble(obj2.get("open").toString()),
						Double.parseDouble(obj2.get("high").toString()),
						Double.parseDouble(obj2.get("low").toString()),
						Double.parseDouble(obj2.get("close").toString()),
						Double.parseDouble(obj2.get("adjclose").toString()),
						Double.parseDouble(obj2.get("volume").toString()));
			}

		} catch (ParseException pe) {
			System.out.println("ERROR: " + pe);
			return;
		}

	}
}
