/**
 * @author cvandera
 *
 */

package util;

import org.junit.Before;
import org.junit.Test;

import exception.AutoException;

import static org.junit.Assert.*;

import model.Automobile;

public class FileIOTest {

	FileIO fileIO;
	
	@Before
	public void setUp() throws Exception {
		fileIO = new FileIO();
	}

	@Test
	public void canBuildAutomotive() {
		String filename = "input.txt";
		boolean noError = false;
		do {
			try {

				// Build Automobile Object from a file.
				Automobile FordZTW = fileIO.buildAutomobileObject(filename);
				noError = true;
				String expected = "Automotive :: {make: Focus, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}";
				
				assertEquals(expected, FordZTW.toString());
			} catch (AutoException e) {
				// Figure out what type of exception was thrown and handle it by
				// calling fix and getting any information needed from the fix

				if (e.getType() == AutoException.ExceptionType.INVALID_FILENAME || e.getType() == AutoException.ExceptionType.MISSING_FILE
						|| e.getType() == AutoException.ExceptionType.IO_EXCEPTION) {
					// if the exception is invalid filename, missing file or any
					// io
					// exception something is wrong with the file so handle it
					// by
					// asking the user for the filename again

					e.fix(e.getType().getErrorNumber());
					filename = (String) e.getFix("filename");

				}
			}

		} while (!noError);
		
		
		
		
	}

}
