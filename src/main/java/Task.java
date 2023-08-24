import Exceptions.EmptyDescriptionException;

public class Task{
    private boolean isDone;
    private String description;

    Task(String s) throws EmptyDescriptionException {
        if(s.isBlank()){
            throw new EmptyDescriptionException();
        }
        this.description = s;
        this.isDone = false;
    }
    public void markDone(){
        this.isDone = true;
    }
    public void markUndone(){
        this.isDone = false;
    }
    public String toString(){
        return "[" + (isDone?"X":" ") + "] " + description;
    }
}