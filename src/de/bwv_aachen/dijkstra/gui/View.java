/**
 * View
 * <p>
 * Simple interface for the view.
 * 
 * @author Marius Spix
 */

package de.bwv_aachen.dijkstra.gui;

import javax.swing.JFrame;

import de.bwv_aachen.dijkstra.controller.Controller;

public abstract class View extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = -6945535097653654834L;
    protected Controller controller;
    
    /**
     * draw
     * <p>
     * Draws the view.
     */
    public abstract void draw();
    public View(Controller c) {
        this.controller = c;
    }
}
