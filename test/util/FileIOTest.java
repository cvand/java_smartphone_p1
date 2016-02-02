/**
 * @author cvandera
 *
 */

package util;

import org.junit.Before;
import org.junit.Test;
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
		Automobile FordZTW = fileIO.buildAutomobileObject("input.txt");
		String expected = "Automotive :: {name: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}]}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}]}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}]}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}]}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}]}]}";

		assertEquals(expected, FordZTW.toString());
	}

}
