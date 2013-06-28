package helpers;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class JSONFileChooser extends JFileChooser {
	public JSONFileChooser() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Dateien (*.json)", "json");
		setFileFilter(filter);
	}
}
