/**
 * ConnectionTableWindow
 * <p>
 * This is the main class starting all actions and instancing objects
 * 
 * @author Serjoscha Bassauer
 */
package de.bwv_aachen.dijkstra.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import de.bwv_aachen.dijkstra.gui.Mainwindow;
import de.bwv_aachen.dijkstra.gui.View;
import de.bwv_aachen.dijkstra.model.BadFileFormatException;
import de.bwv_aachen.dijkstra.model.ImplListDataModelFactory;
import de.bwv_aachen.dijkstra.model.ListDataModel;

public class Controller {

    private ListDataModel          model;
    private View                   view;

    public final static Controller INSTANCE = new Controller();

    /**
     * Controller
     * <p>
     * Simple constructor which sets the standard view. The view can be
     * exchanged later.
     */
    private Controller() {
        view = new Mainwindow(this);
    }

    /**
     * readFile
     * 
     * @param f
     *            The file to read
     *            <p>
     *            Reads a file for the data model factory
     */
    public void readFile(File f) throws FileNotFoundException, IOException,
            ParseException, BadFileFormatException {
        model = ImplListDataModelFactory.INSTANCE.factory(f);
    }

    /**
     * writeFile
     * 
     * @param f
     *            The file to read
     *            <p>
     *            Writes a file from the data model factory
     */
    public void writeFile(File f) throws FileNotFoundException, IOException,
            BadFileFormatException {
        FileWriter writer;

        if (!f.exists()) {
            f.createNewFile();
        }

        writer = new FileWriter(f);

        // f.setWritable(true);

        model.writeJSONString(writer);

        writer.close();
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

    public File getDefaultConnectionFile() {
        return new File("test/testconnection.json");
    }

}
