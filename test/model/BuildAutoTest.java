/**
 * @author cvandera
 *
 */

package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import adapter.BuildAuto;

public class BuildAutoTest {

	BuildAuto buildAuto;

	@Before
	public void setUp() throws Exception {
		buildAuto = new BuildAuto();
	}

	@Test
	public void canBuildAuto() {
		String filename = "input.txt";
		String modelName = "Focus Wagon ZTW";
		buildAuto.buildAuto(filename);
		
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
		
	}
	
	@Test
	public void canBuildMultipleAutos() {
		String filename = "input.txt";
		buildAuto.buildAuto(filename);
		
		filename = "input_second_model.txt";
		buildAuto.buildAuto(filename);
		
		String modelName = "Focus Wagon ZTW";
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
		
	}

	@Test
	public void canNotBuildAutoWithoutFile() {
		String filename = "input2.txt";
		String modelName = "Focus Wagon ZTW";
		// When asked for a filename in the console, enter input.txt to pass the test
		buildAuto.buildAuto(filename);
		
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
		
	}
	
	@Test
	public void canBuildAutoWithoutPrice() {
		String filename = "input_no_price.txt";
		String modelName = "Focus Wagon ZTW";
		
		// When asked for a price in the console, enter 18000 to pass the test
		String price = "18000.0";
		
		buildAuto.buildAuto(filename);
		
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: " + price + ", optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
	}
	
	@Test
	public void canBuildAutoWithoutOptionName() {
		String filename = "input_no_option_name.txt";
		String modelName = "Focus Wagon ZTW";
		
		// When asked for a name in the console, enter lala to pass the test
		String name = "lala";
		
		buildAuto.buildAuto(filename);
		
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: " + name + ", price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
	}
	
	@Test
	public void canBuildAutoWithoutOptionPrice() {
		String filename = "input_no_option_price.txt";
		String modelName = "Focus Wagon ZTW";
		
		// When asked for a price in the console, enter 5 to pass the test
		String price = "5.0";
		
		buildAuto.buildAuto(filename);
		
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: " + price + "}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
	}

}
