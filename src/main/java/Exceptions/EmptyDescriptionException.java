package Exceptions;

public class EmptyDescriptionException extends Exception{
    public String toString(){
        return "Description cannot be empty";
    }
}