package controller;

import gui.Mainwindow;

/**
 * This is the mainclass starting all actions and instancing objects
 */
public class Controller {

	private final static Controller INSTANCE = new Controller();
	private Controller() { };
	
	private void callMainwindow() {
		// Render the userinterface
		Mainwindow mwObj = new Mainwindow();
		mwObj.draw();
	}

	public static void main(String[] args) {
		INSTANCE.callMainwindow();
	}

}
