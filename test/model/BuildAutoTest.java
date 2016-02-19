/**
 * @author cvandera
 *
 */

package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import adapter.BuildAuto;
import exception.AutoException;

public class BuildAutoTest {

	BuildAuto buildAuto;
	Automobile expectedBuildAuto;
	private static String customFileType = "custom";
	private static String propertiesFileType = "properties";

	@Before
	public void setUp() throws Exception {
		buildAuto = new BuildAuto();
		generateExpectedValue();
	}
	
	private void generateExpectedValue() throws AutoException {
		expectedBuildAuto = new Automobile();
		expectedBuildAuto.setMake("Ford");
		expectedBuildAuto.setModel("Focus Wagon ZTW");
		expectedBuildAuto.setBasePrice(null);
		expectedBuildAuto.addOptionSet("Color");
		expectedBuildAuto.addOptionSet("Transmission");
		expectedBuildAuto.addOptionSet("Brakes/Traction Control");
		expectedBuildAuto.addOptionSet("Side Impact Air Bags");
		expectedBuildAuto.addOptionSet("Power Moonroof");
		expectedBuildAuto.addOptionInSet("Color", "Fort Knox Gold Clearcoat Metallic", null);
		expectedBuildAuto.addOptionInSet("Color", "Liquid Grey Clearcoat Metallic", null);
		expectedBuildAuto.addOptionInSet("Color", "Infra-Red Clearcoat", null);
		expectedBuildAuto.addOptionInSet("Color", "Grabber Green Clearcoat Metallic", null);
		expectedBuildAuto.addOptionInSet("Color", "Sangria Red Clearcoat Metallic", null);
		expectedBuildAuto.addOptionInSet("Color", "French Blue Clearcoat Metallic", null);
		expectedBuildAuto.addOptionInSet("Color", "Twilight Blue Clearcoat Metallic", null);
		expectedBuildAuto.addOptionInSet("Color", "CD Silver Clearcoat Metallic", null);
		expectedBuildAuto.addOptionInSet("Color", "Pitch Black Clearcoat", null);
		expectedBuildAuto.addOptionInSet("Color", "Cloud 9 White Clearcoat", null);
		
		expectedBuildAuto.addOptionInSet("Transmission", "Automatic", null);
		expectedBuildAuto.addOptionInSet("Transmission", "Standard", null);
		
		expectedBuildAuto.addOptionInSet("Brakes/Traction Control", "Standard", null);
		expectedBuildAuto.addOptionInSet("Brakes/Traction Control", "ABS", null);
		expectedBuildAuto.addOptionInSet("Brakes/Traction Control", "ABS with Advance Trac", null);
		
		expectedBuildAuto.addOptionInSet("Side Impact Air Bags", "Present", null);
		expectedBuildAuto.addOptionInSet("Side Impact Air Bags", "Not present", null);

		expectedBuildAuto.addOptionInSet("Power Moonroof", "Present", null);
		expectedBuildAuto.addOptionInSet("Power Moonroof", "Not present", null);
		
	}

	@Test
	public void canBuildAuto() {
		String filename = "input.txt";
		String modelName = "Focus Wagon ZTW";
		buildAuto.buildAuto(filename, customFileType);
		
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
		
	}
	
	@Test
	public void canBuildAutoFromPropertiesFile() {
		String filename = "ford.properties";
		String modelName = "Focus Wagon ZTW";
		buildAuto.buildAuto(filename, propertiesFileType);
		
		assertEquals(expectedBuildAuto, buildAuto.findAutomobile(modelName));
		
	}
	
	@Test
	public void canBuildMultipleAutos() {
		String filename = "input.txt";
		buildAuto.buildAuto(filename, customFileType);
		
		filename = "input_second_model.txt";
		buildAuto.buildAuto(filename, customFileType);
		
		String modelName = "Focus Wagon ZTW";
		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
		
	}

//	@Test
//	public void canNotBuildAutoWithoutFile() {
//		String filename = "input2.txt";
//		String modelName = "Focus Wagon ZTW";
//		// When asked for a filename in the console, enter input.txt to pass the test
//		buildAuto.buildAuto(filename, customFileType);
//		
//		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
//		
//	}
//	
//	@Test
//	public void canBuildAutoWithoutPrice() {
//		String filename = "input_no_price.txt";
//		String modelName = "Focus Wagon ZTW";
//		
//		// When asked for a price in the console, enter 18000 to pass the test
//		String price = "18000.0";
//		
//		buildAuto.buildAuto(filename, customFileType);
//		
//		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: " + price + ", optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
//	}
//	
//	@Test
//	public void canBuildAutoWithoutOptionName() {
//		String filename = "input_no_option_name.txt";
//		String modelName = "Focus Wagon ZTW";
//		
//		// When asked for a name in the console, enter lala to pass the test
//		String name = "lala";
//		
//		buildAuto.buildAuto(filename, customFileType);
//		
//		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: " + name + ", price: 0.0}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
//	}
//	
//	@Test
//	public void canBuildAutoWithoutOptionPrice() {
//		String filename = "input_no_option_price.txt";
//		String modelName = "Focus Wagon ZTW";
//		
//		// When asked for a price in the console, enter 5 to pass the test
//		String price = "5.0";
//		
//		buildAuto.buildAuto(filename, customFileType);
//		
//		assertEquals("Automotive :: {make: Ford, model: Focus Wagon ZTW, basePrice: 18445.0, optionSets: [OptionSet :: {name: Color, options: [Option :: {name: Fort Knox Gold Clearcoat Metallic, price: " + price + "}, Option :: {name: Liquid Grey Clearcoat Metallic, price: 0.0}, Option :: {name: Infra-Red Clearcoat, price: 0.0}, Option :: {name: Grabber Green Clearcoat Metallic, price: 0.0}, Option :: {name: Sangria Red Clearcoat Metallic, price: 0.0}, Option :: {name: French Blue Clearcoat Metallic, price: 0.0}, Option :: {name: Twilight Blue Clearcoat Metallic, price: 0.0}, Option :: {name: CD Silver Clearcoat Metallic, price: 0.0}, Option :: {name: Pitch Black Clearcoat, price: 0.0}, Option :: {name: Cloud 9 White Clearcoat, price: 0.0}], optionChoice: null}, OptionSet :: {name: Transmission, options: [Option :: {name: Automatic, price: 0.0}, Option :: {name: Standard, price: -815.0}], optionChoice: null}, OptionSet :: {name: Brakes/Traction Control, options: [Option :: {name: Standard, price: 0.0}, Option :: {name: ABS, price: 400.0}, Option :: {name: ABS with Advance Trac, price: 1625.0}], optionChoice: null}, OptionSet :: {name: Side Impact Air Bags, options: [Option :: {name: Present, price: 350.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}, OptionSet :: {name: Power Moonroof, options: [Option :: {name: Present, price: 595.0}, Option :: {name: Not present, price: 0.0}], optionChoice: null}], userChoice: []}", buildAuto.autoToString(modelName));
//	}

}
