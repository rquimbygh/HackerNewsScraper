package forScraper;

import com.google.gson.Gson;

//looked up these imports from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 

public class Scraper {

	final String hackerAPI = "https://hacker-news.firebaseio.com/v0/";
	final String getItem = "item/";
	final String maxItem = "maxitem.json";
	int maxItemID = -1;
	Pair[] urlItems;
	int uItems = 0;
	Pair[] byItems;
	int bItems = 0;
	Triplet[] scoreItems;
	int sItems = 0;

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
			/*
			urlItems = new Pair[maxItemID - 1];
			byItems = new Pair[maxItemID - 1];	
			scoreItems = new Pair[maxItemID - 1];	
			 */

			//size limits for memory's sake
			urlItems = new Pair[1000];
			byItems = new Pair[1000];	
			scoreItems = new Triplet[1000];

			//size limits for memory's sake
			// for (int i = 0 ; i <=maxItemID ; i++) {
			for (int i = maxItemID - 1000 ; i <= maxItemID ; i++){
				try {
					Response item = getItem(""+i);
					if ((item.deleted == false) && !(item.type.equals("comment"))){
						if (item.url != null){
							addUrl(urlItems, justDomain(item.url));
						}
						if (item.by != null){
							addBy(byItems, item.by);
						}
						scoreItems[sItems++] = new Triplet(item.score, item.title, item.url);
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

	public void addUrl(Pair[] items, String key){
		if (!isDuplicateUrl(items, key)){
			urlItems[uItems++] = new Pair(key, 1);
		}
	}

	public void addBy(Pair[] items, String key){
		if (!isDuplicateBy(items, key)){
			byItems[bItems++] = new Pair(key, 1);
		}
	}

	public boolean isDuplicateUrl(Pair[] items, String key){
		for (int i = 0 ; i < uItems ; i++){
			items[i].key = items[i].key.trim();
			items[i].key = items[i].key.toLowerCase();
			if (items[i].key.equals(key)){
				items[i].val++;
				return true;
			}
		}
		return false;
	}

	public boolean isDuplicateBy(Pair[] items, String key){
		for (int i = 0 ; i < bItems ; i++){
			items[i].key = items[i].key.trim();
			items[i].key = items[i].key.toLowerCase();
			if (items[i].key.equals(key)){
				items[i].val++;
				return true;
			}
		}
		return false;
	}

	public String justDomain (String url){
		// we're going to say blank url's are hosted at news.ycombinator.com
		if (url.equals("")){
			url = "news.ycombinator.com";
		} else {
			int https = url.indexOf("https://");
			if (https > -1){
				url = url.substring("https://".length(), url.length());
			}

			int http = url.indexOf("http://");
			if (http > -1){
				url = url.substring("http://".length(), url.length());
			}

			int www = url.indexOf("www.");
			if (www > -1){
				url = url.substring("www.".length(), url.length());
			}

			int mobile = url.indexOf("mobile.");
			if (mobile > -1){
				url = url.substring("mobile.".length(), url.length());
			}

			int com = url.indexOf(".com");
			int net = url.indexOf(".net");
			int edu = url.indexOf(".edu");
			int org = url.indexOf(".org");
			int io = url.indexOf(".io");
			int co = url.indexOf(".co");
			int it = url.indexOf(".it");
			int us = url.indexOf(".us");

			if (com > -1){
				url = url.substring(0, com+ ".com".length());
			}	
			else if (net > -1){
				url = url.substring(0, net+ ".net".length());
			}
			else if (edu > -1){
				url = url.substring(0, edu+ ".edu".length());
			}
			else if (org > -1 ){
				url = url.substring(0, org+ ".org".length());
			}
			else if (io > -1 ){
				url = url.substring(0, io+ ".io".length());
			}
			else if (co > -1 ){
				url = url.substring(0, co+ ".co".length());
			}
			else if (it > -1 ){
				url = url.substring(0, it+ ".it".length());
			}
			else if (us > -1 ){
				url = url.substring(0, us+ ".us".length());
			}
		}
		return url;
	}

	//modified method from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	//and http://stackoverflow.com/questions/6159118/using-java-to-pull-data-from-a-webpage
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

	public void topTenSites(){
		MergeSort ms = new MergeSort(urlItems, uItems, maxItemID);

		System.out.println("\nTop 10 Most Popular Domains\nCount	Domain\n****************************************************************************************************");
		for (int i = ms.p_array.length - 1 ; i > ms.p_array.length - 11 && i > -1; i--){
			System.out.println(ms.p_array[i].val + "	" + ms.p_array[i].key);
		}
	}

	public void topTenUsers(){
		MergeSort ms = new MergeSort(byItems, bItems, maxItemID);
		System.out.println("\nTop 10 Most Popular Submitters\nCount	Submitter\n****************************************************************************************************");
		for (int i = ms.p_array.length - 1 ; i > ms.p_array.length - 11 && i > -1; i--){
			System.out.println(ms.p_array[i].val + "	" + ms.p_array[i].key);
		}
	}

	public void topTenPosts(){
		MergeSort ms = new MergeSort(scoreItems, sItems, maxItemID);
		System.out.println("\nTop 10 Highest Scoring Posts\nScore	Title	Url\n****************************************************************************************************");
		for (int i = ms.t_array.length - 1 ; i > ms.t_array.length - 11 && i > -1; i--){
			System.out.println(ms.t_array[i]);
		}
	}

	public static void main(String[] args) {
		Scraper scraper = new Scraper();
		try {
			scraper.setMaxItemID();
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		scraper.discoverAllItems();
		scraper.topTenSites();
		scraper.topTenUsers();
		scraper.topTenPosts();
	}
}
