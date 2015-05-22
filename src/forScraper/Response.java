package forScraper;

import java.sql.Time;

public class Response {
	public int id;
	public boolean deleted;
	public String type;
	public String by;
	public int time;
	public String text;
	public boolean dead;
	public int parent;
	public int[] kids;
	public String url;
	public int score;
	public String title;
	public Pair parts;
	public int descendants;

	Response() {
		// no-args constructor
	}
	
	public String toString() {
		return id + "\n" + deleted + "\n" + type + "\n" + by + "\n" + 
	time + "\n" + text + "\n" + dead + "\n" + parent + "\n" + kids + "\n" + url + "\n" +
	score + "\n" + title + "\n" + parts + "\n" + descendants;
	}
}
