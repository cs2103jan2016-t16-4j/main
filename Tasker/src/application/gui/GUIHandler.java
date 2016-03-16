package application.gui;

import application.gui.GUI;

/**
 * 
 * @author Shawn
 *
 */

public class GUIHandler {
	
	public GUIHandler(){
		launchGUI();
	}
	
	private void launchGUI() {
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(GUI.class);
			}
		}.start();
	}
	
}
