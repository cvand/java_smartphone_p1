/**
 * @author cvandera
 *
 */

package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import adapter.BuildAuto;
import exception.AutoException;
import model.Automobile;
import scale.EditOptions;
import server.BuildCarModelOptions;
import util.FileIO;

public class Driver {
	
	private static String customFileType = "custom";

	public static void main(String[] args) {

		testServerSocket();
//		testSerialEditOptions();
//		testFileIOAndSerialization();
	}
	
	public static void testServerSocket() {
		
	}
	
	private static String readInputFromUser(String message) {
		System.out.println(message);

		// read user input for a new filename
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void testSerialEditOptions() {
		// Input for testing
		String filename = "input.txt";
		String modelName = "Focus Wagon ZTW";

		BuildAuto ba = new BuildAuto();
		// build the automobile
		ba.buildAuto(filename, customFileType);

		EditOptions eo1 = new EditOptions(ba);
		try {
			System.out.println("EO1 - Option before changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatic"));
			eo1.setOptionName(modelName, "Transmission", "Automatic", "Automatics");
			System.out.println("EO1 - Option AFTER changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatics"));
			System.out.println("--------");

			EditOptions eo2 = new EditOptions(ba);

			System.out.println("EO2 - Option before changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatic"));
			eo2.setOptionName(modelName, "Transmission", "Automatic", "Automatics2");
			System.out.println("EO2 - Option AFTER changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatics2"));
			System.out.println("--------");
		} catch (AutoException e) {
			e.printStackTrace();
		}
	}

	public static void testFileIOAndSerialization() {
		FileIO fileIO = new FileIO();
		// Build Automobile Object from a file.
		Automobile FordZTW;
		try {
			FordZTW = fileIO.buildAutomobileObject("input.txt", customFileType);

			// Print attributes before serialization
			System.out.println(FordZTW);

			// Serialize the object
			fileIO.serializeAutomotive(FordZTW, "auto.ser");

			// Deserialize the object and read it into memory.
			Automobile newFordZTW = fileIO.deserializeAutomotive("auto.ser");

			// Print new attributes.
			System.out.println(newFordZTW);
		} catch (AutoException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
