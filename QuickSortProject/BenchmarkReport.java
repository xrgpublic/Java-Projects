/*
This File Gets the files Via JFileChooser and writes the contents into a JTable
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class BenchmarkReport{
    
    File fileRec;
    File fileItr;

    //Starts creating Benchmark JTable 
    public void startBenchmark(){
        getFile();
        createTable("Recursive Benchmark",0);
        createTable("Iterative Benchmark",1);
    }
    
    //Gets the file via JFileChooser
    private void getFile(){
        JButton open = new JButton();
        JFileChooser jFC = new JFileChooser(System.getProperty("user.dir"));
        jFC.setDialogTitle("Please Choose Recursive File");
        jFC.showOpenDialog(open);     
        fileRec = jFC.getSelectedFile();
        jFC.setDialogTitle("Please Choose Iterative File");
        jFC.showOpenDialog(open);     
        fileItr = jFC.getSelectedFile();
    }
    

    //Creating the JTable
    public void createTable(String header, int sortType){
        String[][] data = new String [10][5]; //Creates Matrix for JTable
        int currentLine = 0; //Tracks current line in file
        File currentFile; //Will hold file we are reading
        String[] column = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"}; //Holds Headers for colums
        
        //Creating JFrame
        JFrame window; 
        window = new JFrame(header);
        window.setSize(800,400); // set size of panel
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        window.setResizable(false); // Don't let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( // Center window on screen.
            (screen.width - window.getWidth()) / 2,
            (screen.height - window.getHeight()) / 2);
         

        
        //Open File
        try {
            if(sortType == 0){
                currentFile = new File(fileRec.getAbsolutePath()); 
            }
            else{
                currentFile = new File(fileItr.getAbsolutePath());
            }
            
            Scanner sc = new Scanner(currentFile); //Scanner is set to read file

            //Read each line of file
            while(currentLine < 10){
                //Reads each line and puts it in array
                String str = sc.nextLine();
                String[] parts = str.split(" ");
                
                //Puts each line into matrix for JTable
                for(int i = 0; i<5; i++){
                    data[currentLine][i] = parts[i];
                }
                currentLine++; //Increment currentLine
            }
            
            //Creating JTable
            JTable jt=new JTable(data,column);            
            JScrollPane sp=new JScrollPane(jt);    
            window.add(sp);
            window.setVisible(true);  
        } catch (Exception  e) {
        }
    }
}
