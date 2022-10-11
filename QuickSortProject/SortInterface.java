/*
Interface for Sorting algorithms
 */


public interface SortInterface {
    void iterativeSort(int arr[], int l, int h);
    void recursiveSort(int[] array, int startIndex, int endIndex);
    int getCount();
    long getTime();
}
