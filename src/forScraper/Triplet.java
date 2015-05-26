package forScraper;

public class Triplet {
	public int score;
	public String title;
	public String url;

	Triplet(int s, String t, String u) {
		score = s;
		title = t;
		url = u;
	}

	public String toString(){
		return score + "	" + title + "	" + url;
	}

	public static void main(String[] args) {

	}
}
