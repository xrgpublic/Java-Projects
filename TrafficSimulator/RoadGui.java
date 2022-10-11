/**
Xavier Garrido 10/12/21
This Class is in charge of manipulating and creating the simulation 
 */


package project3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class RoadGui extends JPanel {
    
    private Thread trafficLight1;
    private Thread trafficLight2;
    private Thread trafficLight3;
    private int lightCreatorControl = 0;
    private int numberOfLights = 0;    
    private int carCreatorControl = 0;
    private int numberOfCars = 0;
    private boolean stopStartControl = true;
    private boolean pauseResume = true;   
    final private JLabel car1 = new JLabel();
    final private JLabel car2 = new JLabel();
    final private JLabel car3 = new JLabel();
    final private JLabel car1Pos = new JLabel();
    final private JLabel car2Pos = new JLabel();
    final private JLabel car3Pos = new JLabel();
    final private JLabel car1Vel = new JLabel();
    final private JLabel car2Vel = new JLabel();
    final private JLabel car3Vel = new JLabel();
    final private JLabel stopWatchLabel = new JLabel();
    final private Thread[] threadHolder = new Thread[3];
    final private Cars[] carHolder = new Cars[3];
    final private Thread[] lightHolder = new Thread[3];
    final private TrafficLight[] lightObjHolder = new TrafficLight[3];
    final private StopWatch stopWatch = new StopWatch();
    private int light1Control = 800;
    private int light3Control = 1100;
    
    //RoadGui Constructor
    //Calls the methods that start the cars, lights, and stopwatch.  
    public RoadGui(int numberOfLights, int numberOfCars){
        this.numberOfLights = numberOfLights;
        this.numberOfCars = numberOfCars;
        //Starts cars and timer
        createThreads();
        carStart();
        stopWatchStart();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
           @Override public void run() {
               //starts the lights at different time intervals
               lightCreatorControl++;
               carCreatorControl++;
               lightCreator();      
            }
        }, 0L, 10L);
    }
    
    //Creats the stopwatch thread and starts it
    private void stopWatchStart(){
        Thread stopWatchThread = new Thread(stopWatch);
        stopWatchThread.start();
    }
    
    //Creates the threads for the cars
    private void createThreads(){
        createCars();
        for (int i=0; i<numberOfCars;i++){
            threadHolder[i] = new Thread(carHolder[i]);
        }
    }
    
    //Creats the cars
    private void createCars(){
        for (int i=0; i<numberOfCars;i++){
            carHolder[i] = new Cars();
        }
    }
    
    //Starts the car threads
    private void carStart(){
        if (numberOfCars >= 1){
            threadHolder[0].start();
        }
        if (numberOfCars >= 2){
            threadHolder[1].start();
        }
        if (numberOfCars == 3){
            threadHolder[2].start();
        }  
    }
    
    //Creates x number of lights and starts the light threads
    private void lightCreator(){
        //Starts middle light
        if (lightCreatorControl == 1){
            if(lightHolder[1] == null){
                lightObjHolder[1] = new TrafficLight();
                trafficLight2 = new Thread(lightObjHolder[1]);
                lightHolder[1] = trafficLight2;
                lightHolder[1].start();
            }
        }
        //Starts first light
        if (lightCreatorControl == 299 && numberOfLights >= 2){
            if(lightHolder[0] == null){
                trafficLight1 = new Thread(lightObjHolder[0] = new TrafficLight());
                lightHolder[0] = trafficLight1;
                lightHolder[0].start();
            }
        }
        //Starts last light
        if (lightCreatorControl == 599 && numberOfLights == 3){
            if(lightHolder[2] == null){
                trafficLight3 = new Thread(lightObjHolder[2] = new TrafficLight());
                lightHolder[2] = trafficLight3;
                lightHolder[2].start();
            }
        }
    }
    
    //Changes the light of each traffic light
    private Color changeColor(String color){
        //Creates light colors
        Color greenLight = new Color(0,102,0);
        Color yellowLight = new Color(255,255,0);
        Color redLight = new Color(204,0,0);
        //Switch for setting light color
        switch (color) {
            case "GREEN" -> {
                return greenLight;
            }
            case "YELLOW" -> {
                return yellowLight;
            }
            case "RED" -> {
                return redLight;
            }
        }
            return null;
    }
     
    //Makes cars stop on red lights
    private void stopGo(int carNumber, int xDistance){
        //For first light
        if(xDistance == 400 && trafficLight1 != null && trafficLight1.getName().equals("RED")){
            carHolder[carNumber].carStop();
        }
        else if (xDistance == 400 && trafficLight1 != null && trafficLight1.getName().equals("GREEN")){
            carHolder[carNumber].carGo();
        }
        //For middle light
        if(xDistance == 700 && trafficLight2.getName().equals("RED")){
            carHolder[carNumber].carStop();
        }
        else if (xDistance == 700 && trafficLight2.getName().equals("GREEN")){
            carHolder[carNumber].carGo();
        }
        //For last light
        if(xDistance == 1000 && trafficLight3 != null && trafficLight3.getName().equals("RED")){
            carHolder[carNumber].carStop();
        }
        else if (xDistance == 1000 && trafficLight3 != null && trafficLight3.getName().equals("GREEN")){
            carHolder[carNumber].carGo();
        }
    }

    @Override
    public void paintComponent(Graphics shape){
        super.paintComponent(shape);
        Color currentColor;
        Color defaultGreen = new Color(0,102,0);
        //x1 x2 x3 are the x positions for car 1,2,3
        int x1 = 100;
        int x2 = 100;
        int x3 = 100;

         
        //Handling cars
        if (threadHolder[0] != null){
            String carName = threadHolder[0].getName();
            x1 = Integer.valueOf(carName) + 100;
            stopGo(0,x1);
        }
        if (threadHolder[1] != null){
            String carName = threadHolder[1].getName();
            x2 = Integer.valueOf(carName) + 100;
            stopGo(1,x2);
        }
        if (threadHolder[2] != null){
            String carName = threadHolder[2].getName();
            x3 = Integer.valueOf(carName) + 100;
            stopGo(2,x3);
        }
        
        //Creating road
            shape.setColor(Color.BLUE);
            shape.fillRect(100,100,1300,300);
            
            
            
        //If statement handles drawing traffic lights
        //Else statement turns instantiated traffic lights to green while they wait to be started
        if (trafficLight1 != null){
            shape.setColor(Color.WHITE);
            if (lightObjHolder[0] != null){
                shape.drawString(lightObjHolder[0].getTime(), 420, 190);
            }
            currentColor = changeColor(trafficLight1.getName());
            shape.setColor(currentColor);
            shape.fillRect(420,200,5,100);
        }
        else if (numberOfLights >= 2){
            shape.setColor(Color.WHITE);
            int lightControl = light1Control/100;
            String lightTime = lightControl  + "";
            shape.drawString(lightTime, 420, 190);
            shape.setColor(defaultGreen);
            shape.fillRect(420,200,5,100);
        }
            
        if (trafficLight2.getName() != null){
            shape.setColor(Color.WHITE);
            shape.drawString(lightObjHolder[1].getTime(), 720, 190); 
            currentColor = changeColor(trafficLight2.getName());
            shape.setColor(currentColor);
            shape.fillRect(720,200,5,100);
        }
            
        if (trafficLight3 != null){
            shape.setColor(Color.WHITE);
            if (lightObjHolder[0] != null){
                shape.drawString(lightObjHolder[2].getTime(), 1020, 190);
            }
            currentColor = changeColor(trafficLight3.getName());
            shape.setColor(currentColor);
            shape.fillRect(1020,200,5,100);
        }
        else if (numberOfLights == 3){
            shape.setColor(Color.WHITE);
            int lightControl = light3Control/100;
            String lightTime = lightControl  + "";
            shape.drawString(lightTime, 1020, 190);
            shape.setColor(defaultGreen);
            shape.fillRect(1020,200,5,100);
        }
        //Creates the cars
        //Car 1
        shape.setColor(Color.BLACK);
        shape.fillRect(x1,185,20,20);
        //Car 2
        if (carHolder[1] != null){
            shape.setColor(Color.GRAY);
            shape.fillRect(x2,235,20,20);
        }
        //Car 3
        if (carHolder[2] != null){
            shape.setColor(Color.WHITE);
            shape.fillRect(x3,285,20,20);
        }
        light1Control--;
        light3Control--;
    }

    //Makes all lights stop
    private void lightStopSwitch (){
        //Starts x lights depending on how many were started prior
        switch (numberOfLights){
            //1 light was started
            case 1 -> lightObjHolder[1].lightStop();
            //2 lights were started
            case 2 -> {
                lightObjHolder[1].lightStop();
                if (lightObjHolder[0] != null){
                    lightObjHolder[0].lightStop();
                }
            }
            //3 lights were started
            case 3 -> {
                lightObjHolder[1].lightStop();
                if (lightObjHolder[0] != null){
                    lightObjHolder[0].lightStop();
                }
                if (lightObjHolder[2] != null){
                    lightObjHolder[2].lightStop();
                }
            }
        }
    }
    
    //Resets the timer on each light
    private void lightRestartSwitch(){
        //Resets all active lights
        switch (numberOfLights){
            //Resets middle light
            case 1 -> lightObjHolder[1].lightRestart();
            //resets first and middle light
            case 2 -> {
                lightObjHolder[1].lightRestart();
                if (lightObjHolder[0] != null){
                    lightObjHolder[0].lightRestart();
                }
            }
            //resets all 3 lights
            case 3 -> {
                lightObjHolder[1].lightRestart();
                if (lightObjHolder[0] != null){
                    lightObjHolder[0].lightRestart();
                }
                if (lightObjHolder[2] != null){
                    lightObjHolder[2].lightRestart();
                }
            }
        }
    }
    
    //Makes all lights go
    private void lightGoSwitch(){
        switch (numberOfLights){
            //Starts middle light
            case 1 -> lightObjHolder[1].lightGo();
            //starts first and middle light
            case 2 -> {
                //Since lights are started at different times, error checking is used to see if light was started prior to this method call
                lightObjHolder[1].lightStop();
                if (lightObjHolder[0] != null){
                    lightObjHolder[0].lightGo();
                }
                if (lightObjHolder[1] != null){
                    lightObjHolder[1].lightGo();
                }
            }
            //Starts all 3 lights
            case 3 -> {
                lightObjHolder[1].lightStop();
                if (lightObjHolder[0] != null){
                    lightObjHolder[0].lightGo();
                }
                if (lightObjHolder[1] != null){
                    lightObjHolder[1].lightGo();
                }
                if (lightObjHolder[2] != null){
                    lightObjHolder[2].lightGo();
                }
            }
        }
    }
    
    //Resets position of each car
    private void carResetSwitch(){
        switch (numberOfCars){
            //Resets car 1
            case 1 -> {
                carHolder[0].resetCar();
            }
            //Resets car 1 and 2
            case 2 -> {
                carHolder[0].resetCar();
                carHolder[1].resetCar();
            }
            //Resets all cars
            case 3 -> {
                carHolder[0].resetCar();
                carHolder[1].resetCar();
                carHolder[2].resetCar();
                
            }
        }
    }
    
    //Makes all cars stop
    private void carStopSwitch(){
        switch (numberOfCars){
            //Stops car 1
            case 1 -> {
                carHolder[0].carStop();
            }
            //Stops car 2
            case 2 -> {
                carHolder[0].carStop();
                carHolder[1].carStop();
            }
            //Stops all 3 cars
            case 3 -> {
                carHolder[0].carStop();
                carHolder[1].carStop();
                carHolder[2].carStop();
            }
        }
    }
    
    //Makes all cars go
    private void carGoSwitch(){
        switch (numberOfCars){
            //Starts car 1
            case 1 -> carHolder[0].carGo();
            //Starts car 1 and 2
            case 2 -> {
                carHolder[0].carGo();
                carHolder[1].carGo();
            }
            //Starts all 3 cars
            case 3 -> {
                carHolder[0].carGo();
                carHolder[1].carGo();
                carHolder[2].carGo();
            }
        }
    }
   
    //Updates JLabel corresponding to the car and the car's velocity and position
    private void getCarInfo(){
        double position;
        switch (numberOfCars){
            //Sets info for car 1
            case 1 -> {
                position = Integer.valueOf(threadHolder[0].getName())*3.33339;
                car1.setText("Car 1(Black):                               \n");
                car1Pos.setText("Position: "+(int)position+" meters");
                car1Vel.setText("Velocity: "+carHolder[0].getVelocity()+" km/h");
            }
            //Sets info for car 1 and 2
            case 2 -> {
                position = Integer.valueOf(threadHolder[0].getName())*3.33339;
                car1.setText("Car 1(Black):                               \n");
                car1Pos.setText("Position: "+(int)position+" meters");
                car1Vel.setText("Velocity: "+carHolder[0].getVelocity()+" km/h");
                position = Integer.valueOf(threadHolder[1].getName())*3.33339;
                car2.setText("Car 2(Grey): \n");
                car2Pos.setText("Position: "+(int)position+" meters");
                car2Vel.setText("Velocity: "+carHolder[1].getVelocity()+" km/h");
            }
            //Sets info for all cars
            case 3 -> {
                position = Integer.valueOf(threadHolder[0].getName())*3.33339;
                car1.setText("Car 1(Black):                               \n");
                car1Pos.setText("Position: "+(int)position+" meters");
                car1Vel.setText("Velocity: "+carHolder[0].getVelocity()+" km/h");
                position = Integer.valueOf(threadHolder[1].getName())*3.33339;
                car2.setText("Car 2(Grey): \n");
                car2Pos.setText("Position: "+(int)position+" meters");
                car2Vel.setText("Velocity: "+carHolder[1].getVelocity()+" km/h");
                position = Integer.valueOf(threadHolder[2].getName())*3.33339;
                car3.setText("Car 3(White): \n");
                car3Pos.setText("Position: "+(int)position+" meters");
                car3Vel.setText("Velocity: "+carHolder[2].getVelocity()+" km/h");
            }
        }
    }
    
    //Returns the time of stop watch
    private void getStopWatchInfo(){
        stopWatchLabel.setText("Current Time: "+stopWatch.getTime()+" Seconds");
    }
    
    public static void main(String args[]){
        
        StartGui gui = new StartGui();
        
        //Waits for first JFrame to close before starting the simmulation
        while (!gui.startPainting){
            System.out.println();
        }
        //Object that starts simmulation
        RoadGui road = new RoadGui(gui.getNumberOfLights(),gui.getNumberOfCars());
        
        //Setting Up JFRame
        JFrame mainGuiFrame = new JFrame("Expression Converter");
        mainGuiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGuiFrame.setSize(1650,600);
        //Creating JLables
        JLabel emptySpace1 = new JLabel();
        JLabel emptySpace2 = new JLabel();
        JLabel emptySpace3 = new JLabel();
        
        //Creating JPanels
        JPanel bottomPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel westGridPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        
        //Setting layouts for panels
        westGridPanel.setLayout(new GridLayout(11,1));
        eastPanel.setLayout(new GridLayout(4,1,0,50));
        
        //Creating Buttons
        JButton addCar = new JButton("Add Car");
        JButton addLight = new JButton("Add Light");
        JButton pauseGo = new JButton("Pause/Resume");
        JButton stopStart = new JButton("Stop/Restart");
        
        //---------------------------- SETTING UP ACTION LISTENERS FOR BUTTONS ---------------------------- 
        addCar.addActionListener((ActionEvent e) -> {
            //if simmulation was not stopped
            if(road.stopStartControl && road.pauseResume){
                //Adds one Car to simmulation
                if (road.numberOfCars != 3){
                    if (road.numberOfCars == 2){
                        road.threadHolder[2] = new Thread(road.carHolder[2] = new Cars());
                        road.threadHolder[2].start();
                        road.numberOfCars++;
                    }else{
                        road.threadHolder[1] = new Thread(road.carHolder[1] = new Cars());
                        road.threadHolder[1].start();
                        road.numberOfCars++;
                    }
                //If 3 cars are on screen, notify user no more cars can be added.
                }else{
                    ErrorPopUp errorGui = new ErrorPopUp("You cannot have more than 3 cars");
                }
            }
        });
       
        addLight.addActionListener((ActionEvent e) -> {
            //if simmulation was not stopped
            if(road.stopStartControl && road.pauseResume){
                //Adds one light to simmulation
                if (road.numberOfLights != 3){
                    if (road.numberOfLights == 2){
                        road.trafficLight3 = new Thread(road.lightObjHolder[2] = new TrafficLight());
                        road.lightHolder[2] = road.trafficLight3;
                        road.lightHolder[2].start();
                        road.numberOfLights++;
                    }else{
                        road.trafficLight1 = new Thread(road.lightObjHolder[0] = new TrafficLight());
                        road.lightHolder[0] = road.trafficLight1;
                        road.lightHolder[0].start();
                        road.numberOfLights++;
                    }
                //If 3 lights are on screen, notify user no more lights can be added.
                }else{
                    ErrorPopUp errorGui = new ErrorPopUp("You cannot have more than 3 lights");
                }
            }
        });
        
	pauseGo.addActionListener((ActionEvent e) -> {
            //If simmulation was not stopped
            if(road.stopStartControl){
                //if simulation is paused
                if(road.pauseResume){
                    road.lightStopSwitch();
                    road.carStopSwitch();
                    road.stopWatch.timeStop();
                    road.pauseResume = false;
                }
                //if simmulation is resumed
                else{
                    road.lightGoSwitch();
                    road.carGoSwitch();
                    road.stopWatch.timeGo();
                    road.pauseResume = true;
                }
            }
        });
        
        stopStart.addActionListener((ActionEvent e) -> {
            //If simmulation was not paused
            if(road.pauseResume){
                //If simmulation was stopped
                if(road.stopStartControl){
                    road.lightStopSwitch();
                    road.carStopSwitch();
                    road.stopWatch.timeStop();
                    road.stopStartControl = false;
                }
                //If simmulation is reset
                else{
                    road.carResetSwitch();
                    road.carGoSwitch();
                    road.lightRestartSwitch();
                    road.stopWatch.timeReset();
                    road.stopWatch.timeGo();
                    road.stopStartControl = true;
                }
            }
        });
        
        //Resizing buttons at bottom
        pauseGo.setPreferredSize(new Dimension(150, 60));
        stopStart.setPreferredSize(new Dimension(150, 60));
        
        //Adding contents to panels
        //North Panel
        topPanel.add(road.stopWatchLabel);
        //South Panel
        bottomPanel.add(pauseGo);
        bottomPanel.add(stopStart);
        //West Panel
        westGridPanel.add(road.car1);
        westGridPanel.add(road.car1Pos);
        westGridPanel.add(road.car1Vel);
        westGridPanel.add(emptySpace1);
        westGridPanel.add(road.car2);
        westGridPanel.add(road.car2Pos);
        westGridPanel.add(road.car2Vel);
        westGridPanel.add(emptySpace2);
        westGridPanel.add(road.car3);
        westGridPanel.add(road.car3Pos);
        westGridPanel.add(road.car3Vel);
        westPanel.add(westGridPanel);
        //EastPanel
        eastPanel.add(emptySpace3);
        eastPanel.add(addCar);
        eastPanel.add(addLight);
        
        //Adding and setting location of Panles to JFrame
        mainGuiFrame.getContentPane().add(BorderLayout.NORTH, topPanel);
        mainGuiFrame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        mainGuiFrame.getContentPane().add(BorderLayout.WEST, westGridPanel);
        mainGuiFrame.getContentPane().add(BorderLayout.CENTER, westPanel);
        mainGuiFrame.getContentPane().add(BorderLayout.EAST, eastPanel);
        
        //Adding painting mechanic to JFrame
        mainGuiFrame.add(road);
        Timer timer = new Timer();
        mainGuiFrame.setVisible(true);
        timer.schedule(new TimerTask() {
            @Override public void run() {
                //Controls updating the graphics ever 10 miliseconds
                if(road.stopStartControl){
                   road.repaint(); 
                   road.getCarInfo();
                   road.getStopWatchInfo();
                }
            }
        }, 0L, 10L);
    }

}
