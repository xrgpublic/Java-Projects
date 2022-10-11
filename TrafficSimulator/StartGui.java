/**
Xavier Garrido 10/12/21
This Class is in charge creating the first gui that gets the number of lights and number of cars
 */
package project3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartGui {
    private int numberOfCars;
    private int numberOfLights;
    boolean startPainting = false;
        public StartGui(){
        //Creating JFrame
        JFrame guiPopupFrame = new JFrame("Error");
        guiPopupFrame.setSize(800,300);
        guiPopupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creating label and button
        JLabel mainMessage = new JLabel("<html>Please choose the number of cars and the number of lights you would <br>like to start the simmulation with. "
                + "Then, click\"Start\" to begin the simulation.</html>");
        
        String[] selectCars = {"1","2","3"};
        String[] selectLights = {"1","2","3"};
        
        JLabel carLabel = new JLabel("Number Of Cars");
        JLabel lightLabel = new JLabel("Number Of Lights");
        JComboBox<String> carCombo = new JComboBox<>(selectCars);
        JComboBox<String> lightCombo = new JComboBox<>(selectLights);
        
        JButton doneButton = new JButton("Start");
        //Creating panels
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        
        centerPanel.setLayout(new GridLayout(2,2,80,0));
        
        centerPanel.add(carLabel);
        centerPanel.add(lightLabel);
        centerPanel.add(carCombo);
        centerPanel.add(lightCombo);
        
        
        //Adding to panels
        northPanel.add(mainMessage);
        southPanel.add(doneButton);
        //sets location of each panel
        guiPopupFrame.getContentPane().add(BorderLayout.NORTH, northPanel);
        guiPopupFrame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        guiPopupFrame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        guiPopupFrame.setVisible(true);
        //Action listener for button.  Closes when clicked.
        doneButton.addActionListener((ActionEvent e) -> {
            setLightAndCar(lightCombo.getSelectedItem().toString(),carCombo.getSelectedItem().toString());
            startPainting = true;
            guiPopupFrame.dispose();
        });
    }
    
    private void setLightAndCar(String light,String car){
        numberOfLights = Integer.parseInt(light);
        numberOfCars = Integer.parseInt(car); 
    }
    protected int getNumberOfLights(){
        return numberOfLights;
    }
    protected int getNumberOfCars(){
        return numberOfCars;
    }
}
