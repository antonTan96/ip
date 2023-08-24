import Exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Cracker {

    private TodoList list = new TodoList();
    private Reply reply = new Reply();


    public void startService(){
        boolean talking = true;
        Scanner sc = new Scanner(System.in);
        reply.echo("What can I do for you?");

        while(talking){

            String input = sc.nextLine();
            ArrayList<Object> inLine = new ArrayList<>();
            try {


                if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.replace("mark", "").trim()) - 1;
                    list.markDone(index);
                    inLine.add(list.getTask(index));
                    reply.echo("Nice! I've marked this task as done: \n  " + list.getTask(index));
                } else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.replace("unmark", "").trim()) - 1;
                    list.markUndone(index);
                    inLine.add(list.getTask(index));
                    reply.echo("Ok, I've marked this task as not done yet: \n  " + list.getTask(index));
                } else if (input.startsWith("deadline")) {
                    list.store(new Deadline(input.replace("deadline", "").trim()));
                    reply.add("Got it. I've added this task:");
                    reply.add(list.getTask(list.size() - 1));
                    reply.add("Now you have " + list.size() + " task(s) in the list.");
                    reply.echo();
                } else if (input.startsWith("event")) {
                    list.store(new Event(input.replace("event", "").trim()));
                    reply.add("Got it. I've added this task:");
                    reply.add(list.getTask(list.size() - 1));
                    reply.add("Now you have " + list.size() + " task(s) in the list.");
                    reply.echo();
                } else if (input.startsWith("todo")) {
                    list.store(new Todo(input.replace("todo", "").trim()));
                    reply.add("Got it. I've added this task:");
                    reply.add(list.getTask(list.size() - 1));
                    reply.add("Now you have " + list.size() + " task(s) in the list.");
                    reply.echo();
                } else {

                    switch (input) {
                        case "bye":
                            sc.close();
                            talking = false;
                            break;
                        case "list":
                            reply.iterate(list);
                            break;

                        default:
                            try {
                                throw new UnknownCommandException();
                            } catch (UnknownCommandException e) {
                                reply.echo(e.toString());
                            }

                    }
                }
            } catch (EmptyDescriptionException e){
                reply.echo(e.toString());
            } catch (ArrayIndexOutOfBoundsException e){
                reply.echo("The index you provided does not exist");
            }



        }
        reply.echo("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        Cracker bot = new Cracker();
        bot.startService();


    }

}