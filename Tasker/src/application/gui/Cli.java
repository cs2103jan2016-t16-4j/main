package application.gui;

import java.util.Scanner;

public class Cli implements Ui{
    private static final String MESSAGE_WELCOME = "Welcome to Tasker. "
            + "We'll make your life easy and organised!";
    private static final String MESSAGE_ASK_FOR_INPUT = "\nWhat would you like to do?: ";

    private Scanner inChannel;

    public Cli() {
        inChannel = new Scanner(System.in);
    }

    public void printWelcomeMessage() {
        showToUser(MESSAGE_WELCOME);
    }

    public void showToUser(String message) {
        System.out.println("\n" + message);
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


}
