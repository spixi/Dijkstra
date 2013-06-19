package controller;

import gui.Mainwindow;

/**
 * This is the mainclass starting all actions and instancing objects
 */
public class Controller {

	public Controller() {
		// Render the userinterface
		Mainwindow mwObj = new Mainwindow();
		mwObj.draw();
	}

	public static void main(String[] args) {
		new Controller();
	}

}
