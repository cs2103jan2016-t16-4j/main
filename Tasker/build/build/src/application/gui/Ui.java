package application.gui;

import java.util.ArrayList;

import application.storage.Task;

public interface Ui {
    void showError(String message);
    void showToUser(String feedback, ArrayList<Task> tasks);
    void printWelcomeMessage(ArrayList<Task> tasks);
    String getCommand();
    
}
