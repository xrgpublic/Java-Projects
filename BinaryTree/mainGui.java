/*Xavier Garrido Assignment 3 6/26/2021
Holds main function and creates GUI
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class mainGui {
    
    void errorPane(String str){
    JFrame errorFrame = new JFrame(); 
    //displays error message on new JOptionPane
    JOptionPane.showMessageDialog(errorFrame,str);  
    }
    
    public static void main(String[] args){
    //creates new binary tree and new gui
    BinaryTree bt = new BinaryTree();
    mainGui gui = new mainGui();
    //creating gui frame
    JFrame frame = new JFrame("Expression Converter");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,200);
    JTextField tf = new JTextField(20);
    JPanel panelOne = new JPanel();
    JPanel panelTwo = new JPanel();
    JPanel panelThree = new JPanel();
    //creating gui buttons and labels
    //north textbox and label
    JLabel label = new JLabel("Enter Tree");
    JLabel output = new JLabel("");
    //center buttons
    JButton makeTree = new JButton("Make Tree");
    JButton isBalanced = new JButton("Is Balanced?");
    JButton isFull = new JButton("is Full?");
    JButton isProper = new JButton("Is Proper?");
    JButton printHeight = new JButton("hieght");
    JButton printNodes = new JButton("Nodes");
    JButton printInorder = new JButton("Inorder");
    //south textbox
    JLabel printOutput = new JLabel("Output: ");
    //creating actions for each button
    //makes tree
    makeTree.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
        try{
		    String tree = tf.getText();
            bt.treeChecker(tree);
            bt.stringReader(tree);
            output.setText("Tree has been created");
        }
        catch(InvalidTreeSyntax g){
            String tree = tf.getText();
            gui.errorPane(bt.problemFinder(tree));
        }

        catch(Exception g){
            System.out.println(g.getMessage());
        }
	}
    });
    //checks to see if tree is balanced
    isBalanced.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
            try{
                output.setText(bt.balanceCheck());
            }
            catch(Exception f){
            }
	}
    });
    //checks to see if tree is full
    isFull.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
            try{
                output.setText(bt.fullCheck());
            }
            catch(Exception f){
            }
	}
    });
    //checks to see if tree is proper
    isProper.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
            try{
                output.setText(bt.properCheck());
            }
            catch(Exception f){
            }
	}
    });
    //gets height of tree
    printHeight.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
            try{
                output.setText(bt.getHeight());
            }
            catch(Exception f){
            }
	}
    });
    //gets number of nodes in tree
    printNodes.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
            try{
                output.setText(bt.getNodes());
            }
            catch(Exception f){
            }
	}
    });
    //prints tree in 'in order'
    printInorder.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
            try{
                output.setText(bt.traverseInOrder(bt.root));
                bt.treeMaker = "";
            }
            catch(Exception f){
            }
	}
    });
    //adds buttons and lables to panels
    panelOne.add(label);
    panelOne.add(tf);
    panelTwo.add(makeTree);
    panelTwo.add(isBalanced);
    panelTwo.add(isFull);
    panelTwo.add(isProper);
    panelTwo.add(printHeight);
    panelTwo.add(printNodes);
    panelTwo.add(printInorder);
    panelThree.add(printOutput);
    panelThree.add(output);
    //sets location of each panel
    frame.getContentPane().add(BorderLayout.NORTH, panelOne);
    frame.getContentPane().add(BorderLayout.CENTER, panelTwo);
    frame.getContentPane().add(BorderLayout.SOUTH, panelThree);
    frame.setVisible(true);
}
}
