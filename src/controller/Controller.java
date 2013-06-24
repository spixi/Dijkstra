package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import model.BadFileFormatException;
import model.ImplListDataModelFactory;
import model.ListDataModel;
import gui.Mainwindow;

/**
 * This is the mainclass starting all actions and instancing objects
 */
public class Controller {

	private ListDataModel model;
	private Mainwindow    view;
	
	public final static Controller INSTANCE = new Controller();
	private Controller() {
		view = new Mainwindow();
	};
	
	public void readFile(File f) throws FileNotFoundException, IOException, ParseException, BadFileFormatException {
		model = ImplListDataModelFactory.INSTANCE.factory(f);
	}
	
	public ListDataModel getModel() {
		return model;
	}
	
	private void callMainwindow() {
		view.draw();
	}

	public static void main(String[] args) {
		INSTANCE.callMainwindow();
	}

}
