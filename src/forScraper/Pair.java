package forScraper;

public class Pair {
	public int key;
	  public int val;
	  
	  Pair(int itemID, int itemVal) {
	    key = itemID;
	    val = itemVal;
	  }
	  
	  public String toString(){
		  return key + " " + val;
	  }
	  
		public static void main(String[] args) {
			
		}
}
