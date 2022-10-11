/**
Xavier Garrido 10/12/21
This Class is in charge creating a error pane that pops up when an error occurs
 */
package project3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author xrgpu
 */
public class ErrorPopUp {
        public ErrorPopUp(String error){
        //Creating JFrame
        JFrame guiPopupFrame = new JFrame("Error");
        guiPopupFrame.setSize(400,200);
        //Creating label and button
        JLabel errorMessage = new JLabel(error);
        JButton errorButton = new JButton("OK");
        //Creating panels
        JPanel panelOne = new JPanel();
        JPanel panelTwo = new JPanel();
        //Adding to panels
        panelOne.add(errorMessage);
        panelTwo.add(errorButton);
        //sets location of each panel
        guiPopupFrame.getContentPane().add(BorderLayout.NORTH, panelOne);
        guiPopupFrame.getContentPane().add(BorderLayout.CENTER, panelTwo);
        guiPopupFrame.setVisible(true);
        //Action listener for button.  Closes when clicked.
        errorButton.addActionListener((ActionEvent e) -> {
            guiPopupFrame.dispose();
        });
    }
}
