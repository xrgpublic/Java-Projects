/*
This File Holds the Main method and the JVM warmup starter
 */
package project1cmsc451;


public class BenchmarkSorts {
    
    //Warm up Starter
    protected static void start() {
        System.out.println("Started Warm up");
        for (int i = 0; i < 100000; i++) {
            Sorter warmupOBJ = new Sorter();
            warmupOBJ.warmUp();
        }
        System.out.println("Finished Warm up");
    }
    
    public static void main(String[] args) {
        //Warm Up
        BenchmarkSorts.start();
        //Create sorter and MainGui objects
        Sorter sorter = new Sorter();
        BenchmarkReport frame = new BenchmarkReport();
        //Call method that runs the data sets and creates averages
        sorter.dataCreator();
        
        //Gets files, then will create table
        frame.startBenchmark();
        
    }

}
