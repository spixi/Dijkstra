
/**
* ConnectionTableWindow
* <p>
* This is the main class starting all actions and instancing objects
* @author Serjoscha Bassauer
*/
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import model.BadFileFormatException;
import model.ImplListDataModelFactory;
import model.ListDataModel;
import gui.Mainwindow;
import gui.View;

public class Controller {

	private ListDataModel model;
	private View          view;
	
	public final static Controller INSTANCE = new Controller();
	
	/**
	 * Controller
	 * <p>
	 * Simple constructor which sets the standard view.
	 * The view can be exchanged later.
	 */
	private Controller() {
		view = new Mainwindow();
	}
	
	/**
	 * readFile
	 * @param f: The file to read
	 * <p>
	 * Reads a file for the data model factory
	 */
	public void readFile(File f) throws FileNotFoundException, IOException, ParseException, BadFileFormatException {
		model = ImplListDataModelFactory.INSTANCE.factory(f);
	}
	
	public ListDataModel getModel() {
		return model;
	}
	
	/**
	 * Controller
	 * <p>
	 * Displays the view
	 */
	private void callMainwindow() {
		view.draw();
	}

	/**
	 * main
	 * <p>
	 * The main method, starts the program
	 */
	public static void main(String[] args) {
		INSTANCE.callMainwindow();
	}
	
	public static File getDefaultConnectionFile(){
		return new File("test/testconnection.json");
	}

}
