import java.util.*;

public class Sorting {

	public static void main(String args[]) {
		Scanner reader = new Scanner(System.in);
		// read size of input
		int n = reader.nextInt();
		int[] numbers = new int[n];
		// read numbers
		for(int i = 0; i < n; i++){
			numbers[i] = reader.nextInt();
        }
		AbstractSort sorting;
		// run sorting algorithms
		sorting = new InsertionSort();
		run(sorting, numbers);
		
		sorting = new HeapSort();
		run(sorting, numbers);
		
		sorting = new QuickSort();
		run(sorting, numbers);
		
		sorting = new QuickSortImproved();
		run(sorting, numbers);
	}
	
	private static void run(AbstractSort sorting, int[] numbers) {
		int[] temp = Arrays.copyOf(numbers, numbers.length);
		long startTime = System.currentTimeMillis();
		sorting.sort(temp);
		long endTime = System.currentTimeMillis();
		System.err.println("Duration: " + (endTime-startTime) + " ms");
		System.out.println(Arrays.toString(temp));
		int[] temp2 = Arrays.copyOf(numbers, numbers.length);
		Arrays.sort(temp2);
		if(Arrays.toString(temp).equals(Arrays.toString(temp2)))
			System.err.println("Correct");
		else
			System.err.println("Wrong");
	}
}

interface AbstractSort {
	public void sort(int[] numbers);
}
class InsertionSort implements AbstractSort {
	public void sort(int[] numbers) {
		// Implements insertionSort
        int n = numbers.length;
        for(int i = 1; i < n; i++)
        {
            //iterates over length of loop
            int j = i;
            while((j>0) && (numbers[j] < numbers[j-1]))
            {
                //swaps j and j-1 entries 
                int tmp = numbers[j];
                numbers[j] = numbers[j-1];
                numbers[j-1] = tmp;
                --j;
            }
        }
	}
}

class HeapSort implements AbstractSort {
    //Very close to working. Seemingly random swaps with two consecutive elements occur with Inc Input.
    public int size;
	public void sort(int[] numbers) {
		// Implements HeapSort
        size = 1;
        int i = 1;
        //Inserts the contents of numbers into the heap
        while(i < numbers.length)
        {
            insert(numbers, numbers[i]); //insert call
            ++i;
        }
        i = numbers.length - 1; // resets i to scan from back to front of heap
        while(i > 0)
        {
            int largest = deleteMax(numbers);
            numbers[i] = largest;   //takes largest heap elements and re adds them to array sorted
            --i;
        }

	}
    public void insert(int[] numbers, int key) {
        if(size < numbers.length)
        {
            numbers[size] = key;    //adds the element to the end of the array
            ++size;
            //restore the max heap property
            int j = size - 1;
            while((j > 0) && (numbers[j] > numbers[GetParent(j)]))  //Moves element up to correct position in heap
            {
                int tmp = numbers[j];
                numbers[j] = numbers[GetParent(j)];
                numbers[GetParent(j)] = tmp;
                j = GetParent(j);   
            }
        }
        else
        {
            //throw new FullHeapException();
        }
    }
    public int deleteMax(int[] numbers) {
        if(size > 0)        
        {
            int max = numbers[0];   //Takes last element from heap and puts it back at front of array
            numbers[0] = numbers[size-1];   
            --size;
            //restore max heap property
            int j = 0;
            while( j < size) 
            {       
                int l = 2*j; // left 
                int r = 2*j + 1;    //right
                int largest = j;
                if((l < size) && (numbers[l] > numbers[largest]))   //Checking for new largest
                {
                    largest = l;
                }
                if((r < size) && (numbers[r] > numbers[largest]))   //Checking for new largest
                {       
                    largest = r;
                }
                if(largest != j)    //Switches numbers[j] with numbers[largest] if j isnt largest
                {
                    int tmp = numbers[j];
                    numbers[j] = numbers[largest];
                    numbers[largest] = tmp;
                    j = largest;
                }
                else    
                {                   
                    j = size;   //Exits loop 
                }
            }
            return max;
        }
        else
        {
            //throw new EmptyHeapException("Full");
            return 0;
        }
    }
    public int GetParent(int i) {
        double element = i;
        int index = (int)Math.floor((element - 1)  / 2);    //Calculates parent of i
        return index;
    }
}

class QuickSort implements AbstractSort {
	public void sort(int[] numbers) {
        quicksort(numbers, 0, numbers.length - 1);  //Sets correct initial low and high values
	}
    public void quicksort(int[] numbers, int low, int high) {
        if(low < high)  
        {
            int q = partition(numbers, low, high);      //Partitions the array into two subArrays of size/2
            this.quicksort(numbers, low, q-1);          //Recursively sorts higher part of subArray
            this.quicksort(numbers, q +1, high);        //Recursively sorts lower part of subArray
        }
    }   
    public int partition(int[] numbers, int low, int high){
        int p = numbers[high];
        int i = low;
        int j = high - 1;
        while(i <= j)
        {
            //sweeps rightward to find an element larger than the pivot
            while((i<=j) && (numbers[i] <=p))
            {
                ++i;
            }
            //sweeps leftward to find and element smaller than the pivot
            while((j>=i) && (numbers[j] >=p))
            {
                --j;
            }
            if(i<j)
            {
                //swaps i and j entries
                int tmp = numbers[j];
                numbers[j] = numbers[i];
                numbers[i] = tmp;
            }
        }
        //swaps high and i entries
        int tmp1 = numbers[i];
        numbers[i] = numbers[high];
        numbers[high] = tmp1;     
        return i; 
    }
}

class QuickSortImproved implements AbstractSort {
	public void sort(int[] numbers) {
        
		// TODO implement improved version of quick sort
        quicksort(numbers, 0, numbers.length - 1);
	}
    public void quicksort(int[] numbers, int low, int high) {
        if((low < high) && ((high - low) > 10))
        {
            int q = partition(numbers, low, high);
            this.quicksort(numbers, low, q-1);
            this.quicksort(numbers, q +1, high);
        }
        else
        {
            insertSort(numbers, low, high + 1);
        }
        
    }   
    public int partition(int[] numbers, int low, int high){
        int p = numbers[high];
        int i = low;
        int j = high - 1;
        int k = (int)Math.floor(numbers.length /2); // middle element address
        int a = numbers[low];
        int b = numbers[k];
        int c = numbers[high];
        int min, max, med;
        if( a > b ) //Determines the order of numbers at low medium and high and sets pivot to the middle
        {
            if( a > c )
            {
                max = a;
                if( b > c )
                {
                    med = b;
                    min = c;
                }
                else
                {
                    med = c;
                    min = b;
                }
            }
            else
            {
                med = a;
                if( b > c )
                {
                    max = b;
                    min = c;
                }
                else
                {
                    max = c;
                    min = b;
                }
            }
        }
        else
        {
            if( b > c )
            {
                max = b;
                if( a > c )
                {
                    med = a;
                    min = c;
                }
                else
                {   
                    med = c;
                    min = a;
                }
            }
            else
            {
                med = b;
                max = c;
                min = a;
            }
        }    

        while(i <= j)
        {
            //sweeps rightward to find an element larger than the pivot
            while((i<=j) && (numbers[i] <=p))
            {
                ++i;
            }
            //sweeps leftard to find and element smaller than the pivot
            while((j>=i) && (numbers[j] >=p))
            {
                --j;
            }
            if(i<j)
            {
                //swaps i and j entries
                int tmp = numbers[j];
                numbers[j] = numbers[i];
                numbers[i] = tmp;
            }
        }
        //swaps high and i entries
        int tmp1 = numbers[i];
        numbers[i] = numbers[high];
        numbers[high] = tmp1;     
        return i; 
    }
	public void insertSort(int[] subArray, int start, int end) {
        //Local insertSort for more efficient access
        int n = subArray.length;
        for(int i = start + 1; i < end; i++)
        {
            //iterates over length of loop
            int j = i;
            while((j>0) && (subArray[j] < subArray[j-1]))
            {
                //swaps j and j-1 entries 
                int tmp = subArray[j];
                subArray[j] = subArray[j-1];
                subArray[j-1] = tmp;
                --j;
            }
        }
	}    
}































