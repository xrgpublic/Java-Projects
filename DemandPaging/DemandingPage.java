import java.util.ArrayList;
import java.util.Scanner;
import java.nio.CharBuffer;
import java.util.Random;


public class DemandingPage {
    String text = "";
    
    //Gets number Of Frames from user
    public int getFrames(){
        int x = 0;
        int numberOfFrames;
        String temp;
        //infinite loop that only accepts a number 1-7
        while (x == 0){
            System.out.println("Please enter the number of physical frames.");
            Scanner myObj = new Scanner(System.in); 
            temp = myObj.nextLine();
            try{
                //parses to see if input is number
                numberOfFrames = Integer.parseInt(temp);
                if (numberOfFrames<8 && numberOfFrames>0){
                    return numberOfFrames;
                }
            }
            catch(Exception e){
            }
        }
        return 0;
    }
    
    //Gets reference string from user
    public CharBuffer getReferenceString(){     
        char textChar;
        int x = 0;
        long parseCheck;
        //infinite loop that only stops when a numerical reference string is inputed
        while(x == 0){
            System.out.println("Please enter the Refernce String.");
            Scanner myObj = new Scanner(System.in);
            text = myObj.nextLine();
            try{
                parseCheck = Long.valueOf(text);
                x = 1;
            }
            catch(Exception e){  
                System.out.println("Please only use numbers 0-9 for the reference string.");
            }
        }  
        //Puts string in charBuffer
        CharBuffer buffer = CharBuffer.allocate(text.length());
        for (int i = 0; i < text.length(); i++) {
            textChar = text.charAt(i);
            buffer.put(textChar);
      }
        return buffer;
    }
    
    //Generates refernce String
    public CharBuffer generateReferenceString(){     
        char textChar;
        Random rand = new Random();
        int x = 0;
        int refStringLength =0;
        long parseCheck = 0;
        int rand_int1;
        //infinite loop that only stops when a numerical reference string is inputed
        while(x == 0){
            System.out.println("Please enter the length of the Refernce String.");
            Scanner myObj = new Scanner(System.in);
            text = myObj.nextLine();
            try{
                parseCheck = Long.valueOf(text);
                refStringLength = Integer.parseInt(text); 
                x = 1;
            }
            catch(Exception e){  
                System.out.println("Please only use numbers for the length of the reference string.");
            }
        }  
        //Puts string in charBuffer
        CharBuffer buffer1 = CharBuffer.allocate(refStringLength);
        text = "";
        for (int i = 0; i < refStringLength; i++) {
            rand_int1 = rand.nextInt(10);
            text = text + String.valueOf(rand_int1);
            textChar = text.charAt(i);
            buffer1.put(textChar); 
        }
        return buffer1;
    }
    
    //Prints Refernce String
    public void printRefString(){
        System.out.println("Your Refernce String is: "+text);   
    }
    
    //uses FIFO algorithm
    public void fifoDisplay(CharBuffer refString, int numberOfFrames){
        try{
        String logFrame;
        char c;
        int refStringLength = refString.position();
        int physFrameCounter=0;
        String temp;
        char tempC;
        String nextOut;
        String[][] physFrames = new String[refStringLength][numberOfFrames];
        String[] pageFault = new String[refStringLength];
        String[] victimFrame = new String[refStringLength];
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<String> frameKeeper = new ArrayList<>();
        refString.flip();
        
        //adds all characters from text string into a string array
        for (int i = 0; i < text.length(); i++) {
            tempC = text.charAt(i);
            temp = String.valueOf(tempC);  
            textArray.add(temp);
        }
        
        //iterates through each char in buffer        
        for (int i=0;i<refStringLength;i++){
            c = refString.get();
            logFrame = String.valueOf(c);
            //handles list with frame fault
                if (frameKeeper.contains(logFrame)){
                    for (int j=0;j<frameKeeper.size();j++ ){
                    }
                    //Handles not full list
                    if (physFrameCounter != numberOfFrames){
                         for (int z=numberOfFrames-1;z>-1;z--){
                            if (physFrames [i-1][z] != null){
                                physFrames[i][z] = physFrames [i-1][z];    
                            }
                            
                        }
                    }
                    //handles if all physical frames are full
                    else{
                    for (int k=0;k<numberOfFrames;k++){
                        if((physFrames[i-1][k] != null) && physFrames[i-1][k].equals(logFrame)){
                            physFrames[i][k] = physFrames[i-1][k];
                            for (int z=numberOfFrames-1;z>-1;z--){
                                if((physFrames[i-1][z]!= null) && !(physFrames[i-1][z].equals(logFrame))){
                                    physFrames[i][z] = physFrames [i-1][z];
                                }
                                }
                            }
                        }
                    }
                }
                //handles if all physical frames are full
                else if (physFrameCounter == numberOfFrames){
                    nextOut = frameKeeper.get(0);
                    frameKeeper.remove(0);
                    for (int k=0;k<numberOfFrames;k++){
                        //pops top frame off list and finds corisponding frame
                        if (physFrames[i-1][k].equals(nextOut)){
                            physFrames[i][k] = logFrame;
                            victimFrame[i] = physFrames [i-1][k];
                            for (int z=numberOfFrames-1;z>-1;z--){
                                if(!(physFrames[i-1][z].equals(nextOut))){  
                                    physFrames[i][z] = physFrames [i-1][z];
                                }
                            }
                            pageFault[i] = "Y";
                        }
                    }
                    frameKeeper.add(logFrame);
                }
                //Handles not full lsit
                else{
                    //for first entry
                    if (i == 0){
                    physFrames[i][i] = logFrame;
                    }
                    //for other entrys
                    else{
                        for (int z=numberOfFrames-1;z>-1;z--){
                            System.out.println("I: "+i+" z: "+z+" PFC: "+physFrameCounter);
                            if (physFrames[i-1][z] != null){
                            //System.out.println("I: "+i+" z: "+z+" PFC: "+physFrameCounter);
                            physFrames[i][physFrameCounter] = logFrame;
                            physFrames[i][z] = physFrames [i-1][z]; 
                            }
                        }
                    }
                    frameKeeper.add(logFrame);
                    pageFault[i] = "Y";
                    physFrameCounter++;
            }
        }
        
    
    for (int i=0;i<refStringLength+1;i++){
        System.out.print("Reference String|  ");
        for (int k=0;k<refStringLength;k++){
            System.out.print(textArray.get(k)+"  ");
        }
        System.out.println();
        System.out.print("------------------");
        for (int k=0;k<refStringLength;k++){
            System.out.print("---");
        }
        System.out.println();
        for (int j=0;j<numberOfFrames;j++){
            System.out.print("Physical Frame "+j+"| ");
            
                for (int k=0;k<i;k++){
                    if (physFrames[k][j] == null){
                        System.out.print(" "+"x"+" ");
                    }
                    else{
                        System.out.print(" "+physFrames[k][j]+" ");
                    }
            
                }
                System.out.println();
        }
       System.out.print("Page Fault      | ");
        for (int k=0;k<refStringLength;k++){
            if(k>=i){
            }
            else if (pageFault[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+pageFault[k]+" ");
            }
        }
        System.out.println();
        System.out.print("Victim Frame    | ");
        for (int k=0;k<refStringLength;k++){
            if(k>=i){
            }
            else if (victimFrame[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+victimFrame[k]+" ");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
   
    }
        }
        catch(Exception e){
            System.out.println("Please enter or create a reference String");
        }
    }   
    
    //uses LRU algorithm
    public void lruDisplay(CharBuffer refString, int numberOfFrames){
        try{
        String logFrame = "";
        String prevLogFrame;
        String temp;
        char tempC;
        char c;
        int refStringLength = refString.position();
        int physFrameCounter=0;
        String nextOut;
        String[][] physFrames = new String[refStringLength][numberOfFrames];
        String[] pageFault = new String[refStringLength];
        String[] victimFrame = new String[refStringLength];
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<String> controlArray = new ArrayList<>();
        ArrayList<String> frameKeeper = new ArrayList<>();
        int farAwayX = 0;
        int farAwayY = 0;
        int controlArrayCounter = 0;
        int textArrayCounter;
        int control = 0;
        int leastUsed = -1;
        String leastUsedString;
        controlArray.add("-1");
        refString.flip();
        //adds all characters from text string into a string array
        for (int i = 0; i < text.length(); i++) {
            tempC = text.charAt(i);
            temp = String.valueOf(tempC);  
            textArray.add(temp);
        }
        
        //iterates through each char in buffer  
        for (int i=0;i<refStringLength;i++){
            textArrayCounter = i;
            c = refString.get();
            prevLogFrame = logFrame;
            logFrame = String.valueOf(c);
            System.out.println("LF: "+logFrame);
   
                //Handles if all frames are full
                    if (physFrameCounter == numberOfFrames){
                    System.out.println("Still ran this");
                        //finds frame to be replaced
                        for (int z=0;z<numberOfFrames;z++){
                            //Scan Reference string starting from 'i' and move backwards
                            while (control < numberOfFrames){
                                System.out.println("IM cool " +i);
                                for (int j=i;j>0;j--){
                                    for (int l=numberOfFrames-1;l>-1;l--){
                                        System.out.println("l "+l+" Past "+physFrames[i-1][l]+" new: "+logFrame);
                                        //if a hit is found
                                        if(physFrames[i-1][l].equals(logFrame)){
                                            for (int k=numberOfFrames-1;k>-1;k--){
                                                if(physFrames[i][k] == null){
                                                    System.out.println("I: "+i+" j: "+k);
                                                    physFrames[i][k] = physFrames [i-1][k];
                                                }
                                            }
                                            control = numberOfFrames+1;
                                        }  
                                    }
                                    //if hit is not found, increase control variable
                                    if (!(controlArray.contains(textArray.get(j-1))) && control != numberOfFrames){
                                        controlArray.add(textArray.get(j-1));
                                         System.out.println("ControlArray: "+controlArray);
                                        control++;
                                    }
                                }
                                //if no hits are found in physical frames
                                if(control == numberOfFrames){
                                    //remove least recently used frame
                                    leastUsedString = controlArray.remove(numberOfFrames);
                                    for (int y=0;y<numberOfFrames;y++){
                                        //if logical frame equals the least used frame, set y equal to the location of the logical frame
                                        if (physFrames[i-1][y].equals(leastUsedString)){
                                            leastUsed = y;
                                        }
                                    }
                                    control++;
                                }
                                else{ 
                                    System.out.println("IM cool3");
                                    textArrayCounter--;
                                }
                                
                            }
                            //find least recently used logical frame
                        }
                            //Set frame that will be used last as victim
                            if(leastUsed != -1){
                                System.out.println("LU made");
                                //set new logical frame as least used frame
                                physFrames[i][leastUsed] = logFrame;
                                victimFrame[i] = physFrames[i-1][leastUsed];
                                //fill in the empty frames
                                for (int j=numberOfFrames-1;j>-1;j--){
                                    if(physFrames[i][j] == null){
                                        System.out.println("I: "+i+" j: "+j);
                                        physFrames[i][j] = physFrames [i-1][j];
                                    }
                                
                                pageFault[i] = "Y";
                                leastUsed = -1;
                                }
                            }
                    //reset all control variables
                    controlArray.clear();
                    controlArray.add("-1");
                    control = 0;
                    frameKeeper.add(logFrame);

                }
                //Handles not full lsit
                else{
                    //for first entry
   
                    if (i == 0){
                    physFrames[i][i] = logFrame;
                    System.out.println("FC Add");
                    physFrameCounter++;
                    frameKeeper.add(logFrame);
                    pageFault[i] = "Y"; 
                    }

                    //for other entrys
                    else{
                        for (int z=numberOfFrames-1;z>-1;z--){
                            if (physFrames[i-1][z] != null){
                                System.out.println(physFrameCounter);
                            physFrames[i][physFrameCounter] = logFrame;
                            physFrames[i][z] = physFrames [i-1][z]; 
                            }
                        }
                    }
                    if (!frameKeeper.contains(logFrame) && i !=0 ){
                        if(i != refStringLength-1){
                            tempC = text.charAt(i+1);
                            temp = String.valueOf(tempC); 
                        }
                        else{
                            temp = prevLogFrame; 
                        }
                        System.out.println("Temp "+temp+" logFrame "+logFrame);
                        if(!temp.equals(logFrame)){
                            System.out.println("FC Add");
                            physFrameCounter++;
                            frameKeeper.add(logFrame);
                            pageFault[i] = "Y"; 
                        }
                    }
                    
                     
            }
        }
        
        //Makes animated output
       for (int i=0;i<refStringLength+1;i++){
        System.out.print("Reference String|  ");
        for (int k=0;k<refStringLength;k++){
            System.out.print(textArray.get(k)+"  ");
        }
        System.out.println();
        System.out.print("------------------");
        for (int k=0;k<refStringLength;k++){
            System.out.print("---");
        }
        System.out.println();
        for (int j=0;j<numberOfFrames;j++){
            System.out.print("Physical Frame "+j+"| ");
            
                for (int k=0;k<i;k++){
                    if (physFrames[k][j] == null){
                        System.out.print(" "+"x"+" ");
                    }
                    else{
                        System.out.print(" "+physFrames[k][j]+" ");
                    }
            
                }
                System.out.println();
        }
       System.out.print("Page Fault      | ");
        for (int k=0;k<refStringLength;k++){
            if(k>=i){
            }
            else if (pageFault[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+pageFault[k]+" ");
            }
        }
        System.out.println();
        System.out.print("Victim Frame    | ");
        for (int k=0;k<refStringLength;k++){
            if(k>=i){
            }
            else if (victimFrame[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+victimFrame[k]+" ");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
   
    }
    }
            catch(Exception e){
            System.out.println("Please enter or create a reference String");
        }
    }
    
    //uses OPT algorithm
    public void optDisplay(CharBuffer refString, int numberOfFrames){
    try{
        String logFrame = "";
        String prevLogFrame;
        String temp;
        char tempC;
        char c;
        int refStringLength = refString.position();
        int physFrameCounter=0;
        String nextOut;
        String[][] physFrames = new String[refStringLength][numberOfFrames];
        String[] pageFault = new String[refStringLength];
        String[] victimFrame = new String[refStringLength];
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<String> controlArray = new ArrayList<>();
        ArrayList<String> frameKeeper = new ArrayList<>();
        int[] frameTracker = new int[numberOfFrames];
        int controlArrayCounter = 0;
        int textArrayCounter;
        int replaceFrame = 0;
        int farAway = 0;
        int control = 0;
        controlArray.add("-1");
        refString.flip();
        
        //adds all characters from text string into a string array
        for (int i = 0; i < text.length(); i++) {
            tempC = text.charAt(i);
            temp = String.valueOf(tempC);  
            textArray.add(temp);
        }
        
        //iterates through each char in buffer  
        for (int i=0;i<refStringLength;i++){
            textArrayCounter = i;
            c = refString.get();
            prevLogFrame = logFrame;
            logFrame = String.valueOf(c);
            System.out.println("LF: "+logFrame);
   
                //Handles full list
                    if (physFrameCounter == numberOfFrames){
                        //if hit reprint last colum
                        for (int j=0;j<numberOfFrames;j++){
                            System.out.println("Trouble! "+physFrames[i-1][j]+" I "+i+" J "+j);
                            if(physFrames[i-1][j].equals(logFrame)){
                                System.out.println("Replacing LF "+j);
                                control = 1;
                                physFrames[i][j] = logFrame;
                                for (int z=0;z<numberOfFrames;z++){
                                    if(physFrames[i][z] == null){
                                        System.out.println("Here "+z);
                                    physFrames[i][z] = physFrames[i-1][z];
                                    }
                                }
                            }
                        }

                        //if miss, iterate through remaining string and find furthest away
                        if (control == 0){
                            for (int j=i;j<refStringLength;j++){
                                for(int k=0;k<numberOfFrames;k++){
                                    if (textArray.get(j).equals(physFrames[i-1][k]) && frameTracker[k]==0){
                                        frameTracker[k] = j;
                                    }
                                }
                            }
                            //iterates through the physical frames
                            for (int j=0;j<numberOfFrames;j++){
                                //if frame has not been found yet, set 'replaceFrame' equal to distance 
                                if(frameTracker[j] == 0){
                                    replaceFrame = j;
                                    j=numberOfFrames;
                                }
                                //if frame was already found and if it is the furthest frame away, set this frame to new furthest away frame
                                else if(farAway<frameTracker[j]){
                                    farAway = frameTracker[j];
                                    replaceFrame = j;
                                }
                            }
                            //replace furthest away frame with new logical frame
                            physFrames[i][replaceFrame] = logFrame;
                            pageFault[i] = "Y";
                            victimFrame[i]=physFrames[i-1][replaceFrame];
                            //fill in the rest of the frames
                            for (int z=0;z<numberOfFrames;z++){
                                    if(physFrames[i][z] == null){
                                    physFrames[i][z] = physFrames[i-1][z];
                                    }
                                }
                            for (int z=0;z<numberOfFrames;z++){
                                frameTracker[z]=0;
                            }
                        }
                        //reset control variables
                        farAway = 0;
                        control = 0;
                        replaceFrame = 0;
                    frameKeeper.add(logFrame);

                }
                //Handles not full lsit
                else{
                    //for first entry
   
                    if (i == 0){
                    physFrames[i][i] = logFrame;
                    System.out.println("FC Add");
                    physFrameCounter++;
                    frameKeeper.add(logFrame);
                    pageFault[i] = "Y"; 
                    }

                    //for other entrys
                    else{
                        for (int z=numberOfFrames-1;z>-1;z--){
                            if (physFrames[i-1][z] != null){
                                System.out.println(physFrameCounter);
                            physFrames[i][physFrameCounter] = logFrame;
                            physFrames[i][z] = physFrames [i-1][z]; 
                            }
                        }
                    }
                    if (!frameKeeper.contains(logFrame) && i !=0 ){
                        if(i != refStringLength-1){
                            tempC = text.charAt(i+1);
                            temp = String.valueOf(tempC); 
                        }
                        else{
                            temp = prevLogFrame; 
                        }
                        System.out.println("Temp "+temp+" logFrame "+logFrame);
                        if(!temp.equals(logFrame)){
                            System.out.println("FC Add");
                            physFrameCounter++;
                            frameKeeper.add(logFrame);
                            pageFault[i] = "Y"; 
                        }
                    }
                    
                     
            }
        }

        //prints animated output
       for (int i=0;i<refStringLength+1;i++){
        System.out.print("Reference String|  ");
        for (int k=0;k<refStringLength;k++){
            System.out.print(textArray.get(k)+"  ");
        }
        System.out.println();
        System.out.print("------------------");
        for (int k=0;k<refStringLength;k++){
            System.out.print("---");
        }
        System.out.println();
        for (int j=0;j<numberOfFrames;j++){
            System.out.print("Physical Frame "+j+"| ");
            
                for (int k=0;k<refStringLength;k++){
                    if(k>=i){
                    }
                    else if (physFrames[k][j] == null){
                        System.out.print(" "+"x"+" ");
                    }
                    else{
                        System.out.print(" "+physFrames[k][j]+" ");
                    }
            
                }
                System.out.println();
        }
       System.out.print("Page Fault      | ");
        for (int k=0;k<refStringLength;k++){
            if(k>=i){
            }
            else if (pageFault[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+pageFault[k]+" ");
            }
        }
        System.out.println();
        System.out.print("Victim Frame    | ");
        for (int k=0;k<i;k++){
            if (victimFrame[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+victimFrame[k]+" ");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
   
    }
           }
            catch(Exception e){
            System.out.println("Please enter or create a reference String");
        }
    }
    
    public void lfuDisplay(CharBuffer refString, int numberOfFrames){
    try{
        String logFrame = "";
        String prevLogFrame;
        int nextOutFinder = 0;
        int nextFrameOut = 0;
        String temp;
        char tempC;
        char c;
        int refStringLength = refString.position();
        int physFrameCounter=0;
        String[][] physFrames = new String[refStringLength][numberOfFrames];
        String[] pageFault = new String[refStringLength];
        String[] victimFrame = new String[refStringLength];
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<String> frameKeeper = new ArrayList<>();
        int[] frameTally = new int[numberOfFrames];
        int[] ageTally = new int[numberOfFrames];
        int ageTallyControl = 0;
        int control = 0;
        refString.flip();
        
        //iterates through each char in buffer  
        for (int i=0;i<refStringLength;i++){
            c = refString.get();
            prevLogFrame = logFrame;
            logFrame = String.valueOf(c);
            //keeps how long each logical frame has been in the physical frame spot
            for (int z=0;z<numberOfFrames;z++){
                ageTally[z]++;
            }
   
                //Handles full list
                if (physFrameCounter == numberOfFrames){
                    System.out.println("Still ran this");
                    for (int z=0;z<numberOfFrames;z++){
                        //if there is a hit, set control equal to one
                        if(physFrames[i-1][z].equals(logFrame)){
                            System.out.println("TallyAdd1 "+z);
                            frameTally[z]++;
                            System.out.println(frameTally[z]);
                            control = 1;
                        }
                    }
                    // if there is a hit, copy last colum of physical frames
                    if (control == 1){
                        for (int z=0;z<numberOfFrames;z++){
                            physFrames[i][z] = physFrames[i-1][z];
                        }
                        control = 0;
                    }
                    //if there is a page fault: 
                    else{
                    //for each frame count frequency of each frame in use
                    nextOutFinder = frameTally[0];
                    ageTallyControl = 0;
                    //finds next victim frame
                    for (int z=0;z<numberOfFrames;z++){
                        if (nextOutFinder > frameTally[z]){
                            nextOutFinder = frameTally[z];
                            nextFrameOut = z;
                        }
                        else if(nextOutFinder == frameTally[z]){
                            for (int j=0;j<numberOfFrames;j++){
                                if (ageTally[j]> ageTallyControl && nextOutFinder == frameTally[j]){
                                    ageTallyControl = ageTally[j];
                                    nextFrameOut = j;
                                }
                            }
                            
                        } 
                    }
                    //replaces victim frame with new logical frame
                    physFrames[i][nextFrameOut] = logFrame;
                    pageFault[i] = "Y"; 
                    victimFrame[i]=physFrames[i-1][nextFrameOut];
                    //resets control variables
                    ageTally[nextFrameOut]=0;
                    frameTally[nextFrameOut] = 1;
                    nextFrameOut = 0; 
                    textArray.clear();
                    //fills in rest of colum of physical frames
                    for (int z=0;z<numberOfFrames;z++){
                        if (physFrames[i][z]==null){
                            physFrames[i][z]= physFrames[i-1][z];
                        }
                    }
                    for (int z=0;z<numberOfFrames;z++){
                        textArray.add(physFrames[i][z]);
                    }
                    frameKeeper.add(logFrame);
                    }
                }
                //Handles not full lsit
                else{
                    //for first entry
                    if (i == 0){
                        textArray.add(logFrame);
                        physFrames[i][i] = logFrame;
                        System.out.println("FC Add");
                        physFrameCounter++;
                        System.out.println("TallyAdd2");
                        frameTally[0]++;
                        frameKeeper.add(logFrame);
                        pageFault[i] = "Y"; 
                    }

                    //for other entrys
                    else{
                        for (int z=numberOfFrames-1;z>-1;z--){
                            if (physFrames[i-1][z] != null){
                                System.out.println(physFrameCounter);
                                if (control == 0){
                                    textArray.add(logFrame);
                                    control = 1;
                                }
                            physFrames[i][physFrameCounter] = logFrame;
                            physFrames[i][z] = physFrames [i-1][z]; 
                            }
                        }
                        control = 0;
                    }
                    if (!frameKeeper.contains(logFrame) && i !=0){
                         tempC = text.charAt(i+1);
                        temp = String.valueOf(tempC); 
                        System.out.println("Temp "+temp+" logFrame "+logFrame);
                        if(!temp.equals(logFrame)){
                            System.out.println("FC Add");
                            physFrameCounter++;
                            System.out.println("TallyAdd3 "+i);
                            frameTally[i]++;
                            frameKeeper.add(logFrame);
                            pageFault[i] = "Y"; 

                            
                        }
                    }
                    
                     
            }
        }
        //clears text array
        textArray.clear();
        //adds all characters from text string into a string array
        for (int i = 0; i < text.length(); i++) {
            tempC = text.charAt(i);
            temp = String.valueOf(tempC);  
            textArray.add(temp);
        }
        
        //prints animated output
        for (int i=0;i<refStringLength+1;i++){
        System.out.print("Reference String|  ");
        for (int k=0;k<refStringLength;k++){
            System.out.print(textArray.get(k)+"  ");
        }
        System.out.println();
        System.out.print("------------------");
        for (int k=0;k<refStringLength;k++){
            System.out.print("---");
        }
        System.out.println();
        for (int j=0;j<numberOfFrames;j++){
            System.out.print("Physical Frame "+j+"| ");
            
                for (int k=0;k<refStringLength;k++){
                    if(k>=i){
                    }
                    else if (physFrames[k][j] == null){
                        System.out.print(" "+"x"+" ");
                    }
                    else{
                        System.out.print(" "+physFrames[k][j]+" ");
                    }
            
                }
                System.out.println();
        }
       System.out.print("Page Fault      | ");
        for (int k=0;k<refStringLength;k++){
            if(k>=i){
            }
            else if (pageFault[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+pageFault[k]+" ");
            }
        }
        System.out.println();
        System.out.print("Victim Frame    | ");
        for (int k=0;k<i;k++){
            if(k>=i){
            }
            else if (victimFrame[k] == null){
                System.out.print(" "+"N"+" ");
            }
            else{
                System.out.print(" "+victimFrame[k]+" ");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
   
    }
            }
            catch(Exception e){
            System.out.println("Please enter or create a reference String");
        }
    }
    
    public boolean intCheck(String userString) {
        try{
            int userInput = Integer.parseInt(userString); 
            return true;
        }
        catch (Exception e){
            System.out.println("Please enter a number 0-7.");
        return false;
        }
     }
    
    
    public static void main (String[] args){
        int  x=1;
        CharBuffer buffer = null;
        int numberOfFrames = 0;
        boolean controller;
        Scanner myObj = new Scanner(System.in);
        DemandingPage dp = new DemandingPage();
        while (x == 1){
            controller = false;
            System.out.println("");
            System.out.println("Please choose an option: ");
            System.out.println("0-Exit");
            System.out.println("1-Read refernce String");
            System.out.println("2-Generate Reference String");
            System.out.println("3-Display Current Refernce String");
            System.out.println("4-Simulate FIFO");
            System.out.println("5-Simulate OPT");
            System.out.println("6-Simulate LRU");
            System.out.println("7-Simulate LFU");
            String userString = myObj.nextLine();
            while (controller == false){
                controller = dp.intCheck(userString); 
                if (controller == false){
                    userString = myObj.nextLine();
                }
            }
            int userInput = Integer.parseInt(userString); 
            switch (userInput) {
                case 0:
                    System.exit(0);
                case 1:
                    numberOfFrames = dp.getFrames();
                    buffer = dp.getReferenceString();
                    break;
                case 2:
                    numberOfFrames = dp.getFrames();
                    buffer = dp.generateReferenceString();
                    break;
                case 3:
                    dp.printRefString();
                    break;
                case 4:
                    dp.fifoDisplay(buffer,numberOfFrames);
                    break;
                case 5:
                    dp.optDisplay(buffer,numberOfFrames);
                    break;
                case 6:
                    dp.lruDisplay(buffer,numberOfFrames);
                    break;
                case 7:
                    dp.lfuDisplay(buffer,numberOfFrames);
                    break;
            }
        }
    }
}
