/**
Xavier Garrido 10/12/21
This Class is in charge creating Stop watch object
 */
package project3;

import java.util.Timer;
import java.util.TimerTask;

public class StopWatch implements Runnable {
    private int currentTime = 0;
    private boolean stop = false;
    
    @Override
    public void run(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
           @Override public void run() {
               if(!stop){
                currentTime++;
               }
            }
        }, 0L, 10);
    }
    
    protected String getTime(){
        String time = currentTime/100+"";
        return time;
    }
    
    protected void timeGo(){
        stop = false;
    }
    
    protected void timeStop(){
        stop = true;
    }
    
    protected void timeReset(){
        currentTime = 2;
        stop = false;
    }
}
