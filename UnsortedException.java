/*
Throws a Unsorted exception if an array comes out of the quicksort unsorted
 */
package project1cmsc451;

class UnsortedException extends Exception{
    //creates custom exception
    public UnsortedException(){
        //error message
        super("Error! Array is not sorted.");
    }
    public UnsortedException(String errorMessage){
        super(errorMessage);
    }
}
