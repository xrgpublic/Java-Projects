/*Xavier Garrido Assignment 3 6/26/2021
Creates BinaryTree constructor and all its functions
*/


public class BinaryTree {
//Instantiating variables
    Node root;
    int heightLeft=0;
    int heightRight=0;
    int nodeCount = 0;
    int rightCount = 0;
    int leftCount = 0;
    String treeMaker = "";

//Creates static nested class for the tree nodes
static class Node {
    String value;
    Node left;
    Node right;

    Node(String value) {
        this.value = value;
        right = null;
        left = null; 
    }
}

//Recurs for left side of tree
private Node addLeftRec(Node current, String value, int level, char leftRight) {
    //Runs for first node on left side
    if (current == null){
        nodeCount++;
        leftCount++;
	return new Node(value);
    }
    //used to control what level a node is added to
    level--;
    if (level>0){
        current.left = addLeftRec(current.left,value,level, leftRight);
    }
    //creates new node and adds it to left side of of the node that is one level above it
    if (level == 0 && leftRight == 'l'){
        nodeCount++;
        leftCount++;
        current.left = new Node(value);
    }
    //creates new node and adds it to right side of of the node that is one level above it
    else if(level == 0 && leftRight == 'r') {
        nodeCount++;
        leftCount++;
        current.right = new Node(value);
    }
    return current;
}

//Recurs for right side of tree
    private Node addRightRec(Node current, String value, int level, char leftRight) {
        //Runs for first node on right side
    if (current == null){
        nodeCount++;
        rightCount++;
	return new Node(value);
        
    }
    //used to control what level a node is added to
    level--;
    if (level>0){
        current.right = addRightRec(current.right,value,level, leftRight);
    }
    //creates new node and adds it to left side of of the node that is one level above it
    if (level == 0 && leftRight == 'l'){
        nodeCount++;
        rightCount++;
        current.left = new Node(value);
    }
    //creates new node and adds it to right side of of the node that is one level above it
    else if (level == 0 && leftRight =='r'){
        nodeCount++;
        rightCount++;
        current.right = new Node(value);
    }
    return current;
}

//Returns tree in 'in order'
    public String traverseInOrder(Node node) {
    if (node != null) {
        treeMaker = treeMaker+"(";
        traverseInOrder(node.left);
        treeMaker = treeMaker+node.value;
        traverseInOrder(node.right);
        treeMaker = treeMaker+")";
    }
    //returns tree in 'in order' 
    return treeMaker;
}
    
//Checks to see if tree is balanced    
    public String balanceCheck(){
        if (heightLeft == heightRight || heightLeft+1 == heightRight || heightLeft == heightRight+1){
            return "Tree is balanced.";
        }
        return "Tree is not balanced.";
    }
    
//Gets the number of nodes required for the tree to be full
    private double fullNumber(){
        double full=0;
        int height = 0;
        String strHeight = getHeight();
        height = Integer.parseInt(strHeight);
        for (int i=0; i<height+1;i++){
            full = full + Math.pow(2,i);
        }
        return full-1;
    }
    
//Checks to see if tree is full    
    public String fullCheck(){
        if (heightLeft == heightRight && nodeCount == fullNumber()){
            return "Tree is full.";
        }
        return "Tree is not full.";
    }

//Checks to see if tree is proper
    public String properCheck(){
        System.out.println("LC: "+leftCount+" RC: "+rightCount);
        if (leftCount%2 == 1 && rightCount%2 == 1){
            return "Tree is proper.";
        }
        return "Tree is not proper.";
    }
 
//Maintains height of left side of tree    
    private void heightKeeperLeft(int left){
        if (left>heightLeft){
            heightLeft = left;
        }
    }
    
//Maintains height of right side of tree   
    private void heightKeeperRight(int right){
        if (right>heightRight){
            heightRight = right;
        }
    }

//Recieves string and turns it into a tree    
public void stringReader(String str){
    char ch = ' ';
    String temp = "";
    int height = 0;
    int leftControl = 0;
    int leftRightSide = 0;
    char left = 'l';
    char right = 'r';
    //resets variables incase previous tree was already made
    heightLeft = 0;
    heightRight = 0;
    nodeCount = 0;
    rightCount = 0;
    leftCount = 0;
    ch = str.charAt(1);
    temp = String.valueOf(ch);
    root = new Node(temp);
    str = str.substring(2);
    
    //iterates through string
    for (int i=0;i<str.length();i++){
        //uses parentheses to know what level of the tree we are at
        if (str.charAt(i) == '('){
            height++;
        }
        else if (str.charAt(i) == ')'){
            height--;
            leftControl++;
        }
        //once height is '0' it moves to right side of tree
        if (height == 0){
            leftRightSide = 1;
        }
        //adds value to left side of tree
        if (str.charAt(i) != '(' && str.charAt(i) != ')' && str.charAt(i) != ' ' && leftRightSide == 0){
            heightKeeperLeft(height);
            //adds value to left side of node
            if (leftControl < 1){
                ch = str.charAt(i);
                temp = String.valueOf(ch);
                addLeftRec(root,temp,height,left);
            }
            //adds value to right side of node
            if (leftControl > 0){
                ch = str.charAt(i);
                temp = String.valueOf(ch);
                addLeftRec(root,temp,height,right);
                leftControl=0;
            }
        }
        //adds value to right side of tree
        if (str.charAt(i) != '(' && str.charAt(i) != ')' &&leftRightSide == 1){
            heightKeeperRight(height);
            //adds value to left side of node
            if (leftControl < 1){
                ch = str.charAt(i);
                temp = String.valueOf(ch);
                addRightRec(root,temp,height,left);
            }
            //adds value to right side of node
            if (leftControl > 0){
                ch = str.charAt(i);
                temp = String.valueOf(ch);
                addRightRec(root,temp,height,right);
                leftControl=0;
            }
        }
    }
}

//gets and returns height of tree
public String getHeight(){
    String x;
    if (heightLeft >= heightRight){
        x = String.valueOf(heightLeft);
        return x;
    }
    x = String.valueOf(heightRight);
    return x;
}

//gets and returns number of nodes in tree
public String getNodes(){
    String x;
    x = String.valueOf(nodeCount+1);
    return x;
}

//Makes sure string is in proper format
public void treeChecker(String tree) throws InvalidTreeSyntax{
    int parCounter = 0;
    int charCounter = 0;
    try{
        for (int i=0; i<tree.length();i++){
            
            if (tree.charAt(i) == '('){
                parCounter++;
            }
            else if(tree.charAt(i) == ')'){
                parCounter--;
                charCounter--;
            }
            else if (Character.isLetterOrDigit(tree.charAt(i))){
                charCounter++;
                if (charCounter > parCounter){
                    throw new InvalidTreeSyntax();
                }
                //charCounter--;
            }
            else if (!Character.isLetterOrDigit(tree.charAt(i)) && tree.charAt(i) != '(' && tree.charAt(i) != ')'){
                throw new InvalidTreeSyntax();
            }
            if (tree.charAt(i) == ' '){
                throw new InvalidTreeSyntax();
            }
        }
        //if string is not in proper format,it throws custom exception
        if (parCounter != 0){
            throw new InvalidTreeSyntax();
        }
    }
    catch(InvalidTreeSyntax g){
        throw g;
    }

}

public String problemFinder(String tree){
    int parCounter = 0;
    int charCounter = 0;
    for (int i=0; i<tree.length();i++){
        if (tree.charAt(i) == ' '){
            return "Error: Invalid Tree Syntax.  Please do not use spaces";
        }
        if (tree.charAt(i) == '('){
            parCounter++;
        }
        else if(tree.charAt(i) == ')'){
            parCounter--;
            charCounter--;
        }
        else if (Character.isLetterOrDigit(tree.charAt(i))){
            charCounter++;
            if (charCounter > parCounter){
                 return "Error: Invalid Tree Syntax.  Please make sure the values and parentheses match up.";
            }
            }
        else if (!Character.isLetterOrDigit(tree.charAt(i)) && tree.charAt(i) != '(' && tree.charAt(i) != ')'){
            return "Error: Invalid Tree Syntax.  Please only use alphanumeric values";
        }
    }
    if (parCounter != 0){
        return "Error: Invalid Tree Syntax.  Parentheses order does not match.";
        }
    return  "";
}
}