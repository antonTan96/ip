package crackerpackage;

import java.io.IOException;

import crackerpackage.tasks.Task;
import exceptions.EmptyDescriptionException;
import exceptions.IllegalFormatException;
import exceptions.UnknownCommandException;
import javafx.scene.image.Image;
import uicomponents.Parser;
import uicomponents.Reply;


/**
 * A chatbot that manages tasks.
 *
 * @author Anton Tan Hong Zhi
 */
public class Cracker {

    private TodoList list = null;
    private Reply reply = new Reply();
    private Storage storage = new Storage("./data/list.txt");
    private Image user = new Image(this.getClass().getResourceAsStream("/images/chuck2.jpg"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/chuck1.jpg"));

    /**
     * The types of operations supported by the chatbot.
     */
    public enum Type {
        MARK,
        UNMARK,
        TASK,
        DELETE,
        UNKNOWN,
        LIST,
        QUIT,
        FIND,
        CLEAR
    }

    public String getResponse(String input) {
        try {
            Type command = Parser.parseCommand(input);
            assert command != null;
            switch (command) {
            case MARK:
                list.markDone(Parser.parseIndex(input));
                return reply.modifyTaskReply(list.getTask(Parser.parseIndex(input)));
            case UNMARK:
                list.markUndone(Parser.parseIndex(input));
                return reply.modifyTaskReply(list.getTask(Parser.parseIndex(input)));
            case DELETE:
                String cachedReply = reply.deleteTaskReply(list.getTask(Parser.parseIndex(input)), list.size() - 1);
                storage.archive(list.getTask(Parser.parseIndex(input)).toString());
                list.deleteTask(Parser.parseIndex(input));
                return cachedReply;
            case TASK:
                Task newTask = Parser.parseTask(input);
                list.store(newTask);
                return reply.storeTaskReply(newTask, list.size());
            case UNKNOWN:
                try {
                    throw new UnknownCommandException();
                } catch (UnknownCommandException e) {
                    return e.toString();
                }
            case LIST:
                return reply.iterate(list);
            case FIND:
                return reply.findTaskReply(list.filter(Parser.parseKeyword(input)));
            case QUIT:
                try {
                    storage.save(list);
                    return "Bye! Your tasks have been saved!";
                } catch (IOException e) {
                    return "Something wrong happened when saving your tasks";
                }
            case CLEAR:
                try {
                    while(true){
                        storage.archive(list.getTask(0).toString());
                        list.deleteTask(0);
                    }
                } catch (IndexOutOfBoundsException e) {
                    return "All tasks have been cleared!";
                }
            default:
                return "";

            }

        } catch (EmptyDescriptionException e) {
            return e.toString();
        } catch (IndexOutOfBoundsException e) {
            return "The index you provided does not exist";
        } catch (IllegalFormatException e) {
            return e.toString();
        }
    }

    /**
     * Starts up the bot.
     */
    public void startService() {
        list = storage.load();
    }

    public static void main(String[] args) {
        Cracker bot = new Cracker();


        bot.startService();


    }

}
