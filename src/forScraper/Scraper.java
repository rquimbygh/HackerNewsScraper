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
	final String getItem = "item/";
	//final String hackerSites = "...orderBy="$value"";
	//final String hackerUsers = "...orderBy="$value"";
	//final String hackerPosts = "score.json?orderBy="$value"";
	final String topStories = "topstories.json";
	final String maxItem = "maxitem.json";
	int maxItemID = -1;
	Pair[] urlItems;
	Pair[] byItems;
	PairWithInt[] scoreItems;

	//modified method from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	//and http://stackoverflow.com/questions/6159118/using-java-to-pull-data-from-a-webpage
	private void setMaxItemID() throws Exception {
		URL url = new URL(hackerAPI + maxItem);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();

		Gson gson = new Gson();
		int foo = gson.fromJson(response.toString(), int.class);
		maxItemID = foo;
	}

	public void discoverAllItems(){
		try {
			setMaxItemID();
		} catch (Exception e) {
			System.out.println("error: " + e);
			return;
		}
		if (maxItemID>0){
			urlItems = new Pair[maxItemID - 1];
			int u = 0;
			byItems = new Pair[maxItemID - 1];
			int b = 0;
			scoreItems = new PairWithInt[maxItemID - 1];
			int s = 0;
			for (int i = 0 ; i < maxItemID ; i++){
				try{
					//System.out.println("item id: " + i);
					Response item = getItem(i+"");
					//System.out.println("item: " + item);
					//filter out deleted posts
					if (item.deleted == false){
						if (item.url != null){
							urlItems[u++] = new Pair(item.id, item.url);
						}
						if (item.by != null){
							byItems[b++] = new Pair(item.id, item.by);
						}
						scoreItems[s++] = new PairWithInt(item.id, item.score);
					}
				} catch (Exception e){
					//dont stop for 401 error
					System.out.println("trying to get item: " + i + ". error: " + e);
				}
			}
		}
		else {
			System.out.println("maxItemID <= 0");
			return;
		}
	}

	private Response getItem(String itemID) throws Exception {
		URL url = new URL(hackerAPI + getItem + itemID + ".json");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();

		Gson gson = new Gson();
		Response item = gson.fromJson(response.toString(), Response.class);
		return item;
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
			scraper.setMaxItemID();
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		System.out.println("maxItemID is: " + scraper.maxItemID);
		scraper.discoverAllItems();
	}

}
