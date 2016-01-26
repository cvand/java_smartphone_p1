/**
 * @author cvandera
 *
 */

package driver;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Automotive;
import util.FileIO;

public class Driver {

	public static void main(String[] args) {
		FileIO fileIO = new FileIO();
		//Build Automobile Object from a file.
		Automotive FordZTW = fileIO.buildAutomotiveObject("input.txt");
		
		//Print attributes before serialization
		System.out.println(FordZTW);
		
		//Serialize the object
		try {
			fileIO.serializeAutomotive(FordZTW, "auto.ser");
			
			//Deserialize the object and read it into memory.
			Automotive newFordZTW = fileIO.deserializeAutomotive("auto.ser");
			
			//Print new attributes.
			System.out.println(newFordZTW);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
