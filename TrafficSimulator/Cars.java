/*
Xavier Garrido 10/12/21
This Class is in charge creating traffic light objects
 */
package project3;

import java.util.Timer;
import java.util.TimerTask;

public class Cars implements Runnable {
    final private Timer timer;
    private int timerControl;
    private int speed;
    private boolean stop;
    private Thread parentThread;

    public Cars() {
        this.stop = false;
        this.timerControl = 0;
        this.timer = new Timer();
    }
    
    protected void resetCar(){
        timerControl = 0;
        createSpeed();
        parentThread.setName("0");
    }
    
    protected int getVelocity(){
        return speed;
    }
    
    @Override
    public void run() {
        parentThread = Thread.currentThread();
        //Generate speed of car.
        createSpeed();
        //Timer to update of car position every second
        timer.schedule(new TimerTask() {
           @Override public void run() {
               //MAKE THREAD NAME THE X POSITION
                if (!stop){
                    timerControl++;
                    getLocation(timerControl);
                    parentThread.setName(getLocation(timerControl));
                }
            }
        }, 0L, 10L);
    }
    
    protected void carStop(){
        stop = true;
    }
    
    protected void carGo(){
        stop = false;
    }
    
    protected void createSpeed(){
        //Creates a random speed for the car between 45 and 60 Miles per Hour
        speed = (int) Math.floor(Math.random()*(60-45+1)+45);
        
    }
    
    private String getLocation(int time){
        //Distance formula.
        int location = (speed*time)/330;
        //Resets position back to 0 when car reaches end of road
        if (location > 1280){
            timerControl = 0;
            location = location-1400;
        }
        String locationString = location+"";
        return locationString;
    }
}
