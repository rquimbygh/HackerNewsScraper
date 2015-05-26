package forScraper;

public class MergeSort {

	public Pair[] p_array;
	public Triplet[] t_array;
	public int maxID;

	public MergeSort(Pair[] x, int numOfElements, int maxItemID){
		maxID = maxItemID;
		p_array = new Pair[numOfElements];
		for (int i = 0 ; i < numOfElements ; i++){
			p_array[i] = x[i];
		}
		mergeSort(p_array, 0, p_array.length - 1);
	}

	public MergeSort(Triplet[] x, int numOfElements, int maxItemID){
		maxID = maxItemID;
		t_array = new Triplet[numOfElements];
		for (int i = 0 ; i < numOfElements ; i++){
			t_array[i] = x[i];
		}
		mergeSort(t_array, 0, t_array.length - 1);
	}

	public void mergeSort(Pair[] a, int p, int r){
		if (p<r){
			int q = (int)Math.floor((double)(p+r)/2);
			mergeSort(a, p, q);
			mergeSort(a, q+1, r);
			merge(a, p, q, r);
		}
	}

	public void mergeSort(Triplet[] b, int p, int r){
		if (p<r){
			int q = (int)Math.floor((double)(p+r)/2);
			mergeSort(b, p, q);
			mergeSort(b, q+1, r);
			merge(b, p, q, r);
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
		left[left.length-1]= new Pair("*", maxID+1);
		right[right.length-1] = new Pair("*", maxID+1);

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


	public void merge(Triplet[] a, int p, int q, int r){
		int ni = q-p+1;
		int nii = r-q;
		Triplet[] left = new Triplet[ni + 1];
		Triplet[] right = new Triplet[nii + 1];
		int i = 0;
		for ( ; i < left.length ; i++){
			left[i] = a[p + i];
		}
		int j = 0;
		for( ; j < right.length - 1 ; j++){
			right[j] = a[q + 1 + j];
		}
		//sentinel cards mark the end of each pile, NOT REALLY guaranteed to be larger than all other values
		left[left.length-1]= new Triplet(maxID+1, "", "");
		right[right.length-1] = new Triplet(maxID+1, "", "");
		
		i = 0;
		j = 0;
		for (int k = p ; k < r+1 ; k++){
			if ((left[i].score) <= (right[j].score)){
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