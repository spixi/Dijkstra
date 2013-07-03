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
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import de.bwv_aachen.dijkstra.gui.ConnectionVisualization;
import de.bwv_aachen.dijkstra.gui.Mainwindow;
import de.bwv_aachen.dijkstra.gui.View;
import de.bwv_aachen.dijkstra.model.BadFileFormatException;
import de.bwv_aachen.dijkstra.model.ImplListDataModelFactory;
import de.bwv_aachen.dijkstra.model.ListDataModel;

public class Controller {

    private ListDataModel          model;
    private HashMap<String, View>  views;

    public final static Controller INSTANCE = new Controller();

    /**
     * Controller
     * <p>
     * Simple constructor which sets the standard view. The view can be
     * exchanged later.
     */
    private Controller() {
        views = new HashMap<String, View>();
        
        new ConnectionVisualization(this);
        new Mainwindow(this);
    }
    
    public void register(String name, View view) {
        views.put(name, view);
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
    
    public View getView(String name) {
        return views.get(name);
    }
    
    public void drawView(String name) {
        getView(name).draw();
    }


    /**
     * main
     * <p>
     * The main method, starts the program
     */
    public static void main(String[] args) {
        INSTANCE.drawView("MainWindow");
    }

    public File getDefaultConnectionFile() {
        return new File("test/testconnection.json");
    }

    public void command(String command) {
        switch(command) {
        case "AirportListChanged" : {
            for(Map.Entry<String, View> view : views.entrySet()) {
                switch(view.getKey()) {
                    case("MainWindow") :
                    case("EditorWindow") :
                    {
                        //View neu zeichnen
                        view.getValue().draw();
                    }
                }
            }
            break;
        }
    }
        
    }

}
