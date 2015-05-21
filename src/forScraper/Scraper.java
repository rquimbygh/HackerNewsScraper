package forScraper;

import com.google.gson.Gson;

//looked up these imports from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import javax.net.ssl.HttpsURLConnection;

public class Scraper {
	
	final String hackerAPI = "https://hacker-news.firebaseio.com/v0/";
	//final String hackerSites = "...orderBy="$value"";
	//final String hackerUsers = "...orderBy="$value"";
	//final String hackerPosts = "score.json?orderBy="$value"";
	final String topStories = "topstories.json";
	final String maxItem = "maxitem.json?print=pretty";
	int maxItemID = -1;
	
	//modified method from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	private void getMaxItemID() throws Exception {
		 
		String restURL = hackerAPI + maxItem;
 
		URL url = new URL(restURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
	
	public void discoverAllItems(){
		
	}
	

/*	public String getRequest(String url, String item){
		
	}
	
	public String getTopTen(String url){
		
	}
	
	public String topTen(String item){
		String fromGet = getRequest(hackerAPI, item);
		return getTopTen(fromGet);
	}
*/	
	//API call to get IDs of top sites
	//API call to get IDs of top users
	//API call to get IDs of top posts
	
	//internal URLs may need fixing
	//external URLs from the same dns(?) should count towards the same site
	public static void main(String[] args) {
		//print top 10 sites posted to Hacker News
		//print top 10 users who submit stories
		//print top 10 highest scoring posts
		Scraper scraper = new Scraper();
		try {
			scraper.getMaxItemID();
		} catch (Exception e) {
			System.out.println("error:" + e);
		}
	}

}
