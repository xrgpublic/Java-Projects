/*Xavier Garrido Assignment 3 6/26/2021
Creates custom Exception for invalid tree syntax
*/
class InvalidTreeSyntax extends Exception{
    //creates custom exception
    public InvalidTreeSyntax(){
        //error message
        super("Cannot Be Done!");
    }
    public InvalidTreeSyntax(String errorMessage){
        super(errorMessage);
    }
}