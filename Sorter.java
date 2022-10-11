/*
This holds the recursive and iterative version of the quick sort method 
Also holds the method that runs each method 50 times for 10 different values of n, 
then gets the Avg Count, Coef Count, Avg Time, and Coef Time and writes them to their files.
 */
package project1cmsc451;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;


public class Sorter implements SortInterface{
    
    private int level = 0;
    private int criticalOp = 0;
    private int[] numberHolderRec;
    private int[] numberHolderItr;
    private double[] countRec = new double[50];
    private double[] timeRec = new double[50];
    private double[] countItr = new double[50];
    private double[] timeItr = new double[50];
    private double[][] avgCountNumber = new double[2][50];
    private double[][] coefCountNumber = new double[2][50];
    private double[][] avgTimeNumber = new double[2][50];
    private double[][] coefTimeNumber = new double[2][50];
    public long startTime;
    private long endTime;

    //Warmup JVM 
    public void warmUp(){   
    }
    
    //Swap for iterative version
    public void swap(int arr[], int i, int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    //Finds partition for iterative version
    public int partitionItrative(int arr[], int l, int h){
        int x = arr[h];
        int i = (l - 1);
  
        for (int j = l; j <= h - 1; j++) {
            if (arr[j] <= x) {
                i++;
                // swap arr[i] and arr[j]
                swap(arr, i, j);
            }
        }
        // swap arr[i+1] and arr[h]
        swap(arr, i + 1, h);
        return (i + 1);
    }
  
    // Sorts arr[l..h] using iterative QuickSort
    @Override
    public void iterativeSort(int arr[], int l, int h){
        // create auxiliary stack
        int stack[] = new int[h - l + 1];
  
        // initialize top of stack
        int top = -1;
  
        // push initial values in the stack
        stack[++top] = l;
        stack[++top] = h;
  
        // keep popping elements until stack is not empty
        
        //START OF CRITICAL TIME COUNT
        startTime = System.nanoTime();
        while (top >= 0) {
            criticalOp++; //Incriments critical count for each run of the while loop
            // pop h and l
            h = stack[top--];
            l = stack[top--];
  
            // set pivot element at it's proper position
            int p = partitionItrative(arr, l, h); //Critical count is updated in this function
  
            // If there are elements on left side of pivot,
            // then push left side to stack
            if (p - 1 > l) {
                stack[++top] = l;
                stack[++top] = p - 1;
            }
  
            // If there are elements on right side of pivot,
            // then push right side to stack
            if (p + 1 < h) {
                stack[++top] = p + 1;
                stack[++top] = h;
            }
        }
        //END OF CRITICAL TIME COUNT
        endTime = System.nanoTime() - startTime;
    }


//END OF ITERATIVE VERSION
//------------------------------------------------------------------------------------------------------
   
    
    
    //PULLED FROM https://big-o.io/algorithms/comparison/quicksort/
    //Recursive Quick Sort Alg <-------DONE
    @Override
    public void recursiveSort(int[] array, int startIndex, int endIndex){
        criticalOp++;
    //START CRITICAL TIME COUNT
        //Make way of keeping track of how deep we are in reccursion 
        level++;
        //System.out.println(level);
        if (level == 1){
            //START CRITICAL TIME COUNT
            startTime = System.nanoTime();
        }
        
        // verify that the start and end index have not overlapped
        if (startIndex < endIndex)
        {
             //IDK IF THIS SHOULD GO AT THE BEGINING OF THE METHOD OR IN THE IF STATEMENT
            // calculate the pivotIndex
            int pivotIndex = partitionRecursive(array, startIndex, endIndex);
            // sort the left sub-array
             //For method Call
            recursiveSort(array, startIndex, pivotIndex);
            // sort the right sub-array
            recursiveSort(array, pivotIndex + 1, endIndex);
        }
        level--;
    //STOP CRITICAL TIME COUNT
        if(level == 0){
            //STOP CRITICAL TIME COUNT
            
            endTime = System.nanoTime() - startTime;
        }
    }

    //Finds partition for recursive version
    public int partitionRecursive(int[] array, int startIndex, int endIndex){
        int pivotIndex = (startIndex + endIndex) / 2;
        int pivotValue = array[pivotIndex];
        startIndex--;
        endIndex++;

        while (true)
        {
            // start at the FIRST index of the sub-array and increment
            // FORWARD until we find a value that is > pivotValue
            do {
                startIndex++;
            } 
            while (array[startIndex] < pivotValue);

            // start at the LAST index of the sub-array and increment
            // BACKWARD until we find a value that is < pivotValue
            do {
                endIndex--;
            } 
            while (array[endIndex] > pivotValue);

            if (startIndex >= endIndex){
                return endIndex;
            }

            // swap values at the startIndex and endIndex
            int temp = array[startIndex];
            array[startIndex] = array[endIndex];
            array[endIndex] = temp;
        }
    }
    

//END OF RECURSIVE VERSION
//--------------------------------------------------------------------------------------
    
    //Method that creates array for n elements
    private void arrayFiller(int n){
        Random rnd = new Random ();
        numberHolderRec = new int[n];
        numberHolderItr = new int[n];
        for(int i = 0; i < n; i++){
            //Fills both arrays with same number
            int x = rnd.nextInt();
            numberHolderRec[i] = x;
            numberHolderItr[i] = x;
        }
    }
    
    //Finds average of array 
    public double findAverage(double[] array){
        double average = 0;
        int count = 50;
        while(count > 0){
            average = average + array[count-1];
            count--;
        }
        average = average/50 ;
        return average;
    }
    
    //Finds coef of variance of array
    public double findCoef(double[] array){
        return standardDeviation(array) / findAverage(array);
    }
    
    //Finds standard deviation of array
    public double standardDeviation(double[] array){
        double sum = 0;
        int n = 50;
        for (int i = 0; i < n; i++){
            sum = sum + (array[i] - findAverage(array)) * (array[i] - findAverage(array));
        }
        return Math.sqrt(sum / (n - 1));
    }
 
    //if 0, then recursive
    //if 1, then iterative
    //run = run number (1-10)
    private void findInfo(int run){
        avgCountNumber[0][run] = findAverage(countRec);
        coefCountNumber[0][run] = findCoef(countRec);
        avgTimeNumber[0][run] = findAverage(timeRec);
        coefTimeNumber[0][run] = findCoef(timeRec);
        avgCountNumber[1][run] = findAverage(countItr);
        coefCountNumber[1][run] = findCoef(countItr);
        avgTimeNumber[1][run] = findAverage(timeItr);
        coefTimeNumber[1][run] = findCoef(timeItr);
        System.out.println("\nAVERAGES REC----------------");
        System.out.println(avgCountNumber[0][run]);
        System.out.println(coefCountNumber[0][run]);
        System.out.println(avgTimeNumber[0][run]);
        System.out.println(coefTimeNumber[0][run]);
        System.out.println("\nAVERAGES ITR----------------");
        System.out.println(avgCountNumber[1][run]);
        System.out.println(coefCountNumber[1][run]);
        System.out.println(avgTimeNumber[1][run]);
        System.out.println(coefTimeNumber[1][run]);
    }
    
    //int x controls if writing to recursive or iterative file
        //0 for recursive 1 for iterative
    private void writeToFile(){
        try{
            int n = 100;
            //Creating variables
            File recFile = new File("recFile.txt");
            File itrFile = new File("itrFile.txt");
            FileWriter fileWriter;
            BufferedWriter bufferedWriter;
            
            //Writing to recursive file
            fileWriter = new FileWriter(recFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            for(int i=0; i<10;i++){
                bufferedWriter.write(n+" "+avgCountNumber[0][i]+" "+coefCountNumber[0][i]+" "+avgTimeNumber[0][i]+" "+coefTimeNumber[0][i]+"\n");
                n += 100;
            }
            bufferedWriter.close();
            n = 100;
            
            //Writing to IterativeFile
            fileWriter = new FileWriter(itrFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            for(int i=0; i<10;i++){
                bufferedWriter.write(n+" "+avgCountNumber[1][i]+" "+coefCountNumber[1][i]+" "+avgTimeNumber[1][i]+" "+coefTimeNumber[1][i]+"\n");
                n += 100;
            }
            bufferedWriter.close();
        }catch(Exception e){
        } 
    }
    
    //Makes sure array is sorted
    public void arraySortCheck(int[] array, int n) throws UnsortedException{
        double prev = array[0];
        for(int i=0; i<n; i++){
            if(prev > array[i]){
                throw new UnsortedException();
            }
        }
    }
    
    //Gets critical operation count
    @Override
    public int getCount(){
        int x = criticalOp;
        criticalOp = 0;
        return x;
    }
    
    //Gets time in nanoseconds
    @Override
    public long getTime(){
        return endTime;
    }
    
    private void checkSort(int n, String message,int[] array){
            System.out.println(message);
            for(int k = 0; k<n;k++){
                System.out.println("Element "+k+ ": "+array[k]);
            }
    }
    
    //Method that runs each data set and gets averages for the 50 runs
    public void dataCreator(){
    try{
        //Runs 10 times with n=100 to n=1000
        for(int n = 100; n <= 1000; n = n+100){
            for (int i = 0; i<50; i++){
                arrayFiller(n); //Creats array of size n
                
                //Prints unsorted array for first run of each n
                if(i == 0){
                    checkSort(n,"\nUnsorted Array",numberHolderRec);
                }
                
                recursiveSort(numberHolderRec,0,n-1); //Runs recursive quicksort on array
                countRec[i] = getCount(); //adds count to array
                timeRec[i] = getTime(); //adds time to array
                arraySortCheck(numberHolderRec, n); //makes sure array is sorted
                
                //Prints sorted array for first recursive run of each n
                if(i == 0){
                    checkSort(n,"\nSorted Array Recursive",numberHolderRec);
                }

                
                iterativeSort(numberHolderItr,0,n-1); //Runs iterative quicksort on array
                countItr[i] = getCount(); //adds count to array
                timeItr[i] = getTime(); //adds time to array
                arraySortCheck(numberHolderItr, n); //makes sure array is sorted
                
                //Prints sorted array for first iterative run of each n
                if(i == 0){
                    checkSort(n,"\nSorted Array Iterative",numberHolderItr);
                }
            }
            //(n/100)-1 will give 0-9 and n will give data size
            findInfo((n/100)-1); //Does Calculations    
        }
        writeToFile(); //Writes benchmarks to file
    }
    catch(UnsortedException e){
        System.err.println(e);
    }
}
    
}
