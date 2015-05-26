package forScraper;

public class Pair {
	  public String key;
	  public int val;
	  
	  Pair(String itemKey, int itemVal) {
	    key = itemKey;
	    val = itemVal;
	  }

	  public String toString(){
		  return key.toString() + " " + val;
	  }
}
