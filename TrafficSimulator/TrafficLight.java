/**
Xavier Garrido 10/12/21
This Class is in charge creating traffic light objects
 */
package project3;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficLight implements Runnable{
    private final Timer trafficLight;
    private int timeControl;
    private boolean pause;
    Thread parentThread;

    public TrafficLight() {
        this.pause = false;
        this.trafficLight = new Timer();
        this.timeControl = -1;
    }
    
    
    @Override
    public void run(){
        parentThread = Thread.currentThread();
        trafficLight.schedule(new TimerTask() {
           @Override public void run() {
                if (timeControl == 1100){
                    timeControl = 0;
                }
                if (!pause){
                    timeControl++;
                    parentThread.setName(changeColor(timeControl));
                }
            }
        }, 0L, 10L);
    }
    
    protected void lightRestart(){
        int newStartColor = (int) (Math.random()*(1000-0+1)+0);   
        timeControl = newStartColor;
        pause = false;
    }
    
    protected void lightStop(){
        pause = true;
    }
    
    protected void lightGo(){
        pause = false;
    }
    
    protected String getTime(){
        String time = "";
        if(parentThread != null){
            if(parentThread.getName().equals("GREEN")){
                time = "" + (4 - timeControl/100);
            }
            else if(parentThread.getName().equals("YELLOW")){
                time = "" + (6 - timeControl/100);
            }
            else if(parentThread.getName().equals("RED")){
                time = "" + (10 - timeControl/100);
            }
        }
            return time;
    }
    
    private String changeColor(int time){
            if (time >= 0 && time < 500){
                return "GREEN";
        }
        else if(time>=500 && time <700){
                return "YELLOW";
                }
        else if (time >= 700){
                return "RED";
        }
            return "";
    }
}
