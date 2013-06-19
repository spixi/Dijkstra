package controller;

import gui.Mainwindow;

public class Controller {

	public Controller() {
		Mainwindow mwObj = new Mainwindow();
		mwObj.draw();
	}

	public static void main(String[] args) {
		new Controller();
	}

}
