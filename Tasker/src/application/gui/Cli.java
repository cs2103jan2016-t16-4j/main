package application.gui;

import java.util.ArrayList;
import java.util.Scanner;
import application.storage.Task;

public class Cli implements Ui{
    private static final int FIRST_INDEX_FOR_USER = 1;
            
    private static final String MESSAGE_WELCOME = "Welcome to Tasker. "
            + "We'll make your life easy and organised!";
    private static final String MESSAGE_ASK_FOR_INPUT = "What would you like to do?: ";

    private Scanner inChannel;

    public Cli() {
        inChannel = new Scanner(System.in);
    }

    public void printWelcomeMessage(ArrayList<Task> tasks) {
        showToUser(MESSAGE_WELCOME, tasks);
    }

    public void showToUser(String message,ArrayList<Task> tasks){
        showLine(message);
        showList(tasks);
    }
    private void showLine(String message) {
        System.out.println(message);
    }
    
    private void showList(ArrayList<Task> tasks){
        int i = FIRST_INDEX_FOR_USER;
        for(Task task: tasks){
            showLine(i + ". " + task.toString());
            i++;
        }
    }

    public String getCommand() {
        askForCommand();
        String input = takeCommand();
        return input;
    }

    private String takeCommand() {
        String command = inChannel.nextLine();
        return command;
    }

    private void askForCommand() {
        System.out.print(MESSAGE_ASK_FOR_INPUT);
    }

    @Override
    public void showError(String message) {
        showLine(message);
    }


}
