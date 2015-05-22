package forScraper;

public class MergeSort {

	public PairStringInt[] a_array;
	public Pair[] b_array;
	public int maxID;

	public MergeSort(PairStringInt[] x, int numOfElements, int maxItemID){
		maxID = maxItemID;
		a_array = new PairStringInt[numOfElements];
		for (int i = 0 ; i < numOfElements ; i++){
			a_array[i] = x[i];
		}
		mergeSort(a_array, 0, a_array.length - 1);
	}

	public MergeSort(Pair[] x, int numOfElements, int maxItemID){
		maxID = maxItemID;
		b_array = new Pair[numOfElements];
		for (int i = 0 ; i < numOfElements ; i++){
			b_array[i] = x[i];
		}
		mergeSort(b_array, 0, b_array.length - 1);
	}

	public void mergeSort(PairStringInt[] a, int p, int r){
		if (p<r){
			int q = (int)Math.floor((double)(p+r)/2);
			mergeSort(a, p, q);
			mergeSort(a, q+1, r);
			merge(a, p, q, r);
		}
	}

	public void mergeSort(Pair[] b, int p, int r){
		if (p<r){
			int q = (int)Math.floor((double)(p+r)/2);
			mergeSort(b, p, q);
			mergeSort(b, q+1, r);
			merge(b, p, q, r);
		}
	}


	public void merge(PairStringInt[] a, int p, int q, int r){
		int ni = q-p+1;
		int nii = r-q;
		PairStringInt[] left = new PairStringInt[ni + 1];
		PairStringInt[] right = new PairStringInt[nii + 1];
		int i = 0;
		for ( ; i < left.length ; i++){
			left[i] = a[p + i];
		}
		int j = 0;
		for( ; j < right.length - 1 ; j++){
			right[j] = a[q + 1 + j];
		}
		//sentinel cards mark the end of each pile, NOT REALLY guaranteed to be larger than all other values
		left[left.length-1]= new PairStringInt("*", maxID+1);
		right[right.length-1] = new PairStringInt("*", maxID+1);

		i = 0;
		j = 0;
		for (int k = p ; k < r+1 ; k++){
			if ((left[i].val) <= (right[j].val) ){
				a[k] = left[i];
				i++;
			} else {
				a[k] = right[j];
				j++;
			}
		}
		
	}	


	public void merge(Pair[] a, int p, int q, int r){
		int ni = q-p+1;
		int nii = r-q;
		Pair[] left = new Pair[ni + 1];
		Pair[] right = new Pair[nii + 1];
		int i = 0;
		for ( ; i < left.length ; i++){
			left[i] = a[p + i];
		}
		int j = 0;
		for( ; j < right.length - 1 ; j++){
			right[j] = a[q + 1 + j];
		}
		//sentinel cards mark the end of each pile, NOT REALLY guaranteed to be larger than all other values
		left[left.length-1]= new Pair(-1, maxID+1);
		right[right.length-1] = new Pair(-1, maxID+1);
		
		i = 0;
		j = 0;
		for (int k = p ; k < r+1 ; k++){
			if ((left[i].val) <= (right[j].val)){
				a[k] = left[i];
				i++;
			} else {
				a[k] = right[j];
				j++;
			}
		}
		
	}
	
	public static void main(String[] args) {

	}
}