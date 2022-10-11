/*
Interface for Sorting algorithms
 */
package project1cmsc451;


public interface SortInterface {
    void iterativeSort(int arr[], int l, int h);
    void recursiveSort(int[] array, int startIndex, int endIndex);
    int getCount();
    long getTime();
}
