package forScraper;

public class PairStringInt {
	  public String key;
	  public int val;
	  
	  PairStringInt(String itemKey, int itemVal) {
	    key = itemKey;
	    val = itemVal;
	  }

	  public String toString(){
		  return key.toString() + " " + val;
	  }
}
