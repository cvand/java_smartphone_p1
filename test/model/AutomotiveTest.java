/**
 * @author cvandera
 *
 */

package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AutomotiveTest {

	Automotive auto;
	
	@Before
	public void setUp() throws Exception {
		auto = new Automotive(2, "Focus Wagon ZTW");
	}

	@Test
	public void canAddOptionSet() {
		OptionSet set = new OptionSet("Transmission", 2);
		try {
			auto.addOptionSet(set);
			assertEquals(set, auto.getOptionSetByName("Transmission"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	@Test
	public void canGetOptionSetByName() {
		OptionSet set = new OptionSet("Transmission", 2);
		try {
			auto.addOptionSet(set);
			OptionSet set2 = new OptionSet("Color", 10);
			auto.addOptionSet(set2);
			assertEquals(set, auto.getOptionSetByName("Transmission"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	
	@Test
	public void canRemoveOptionSetByName() {
		OptionSet set = new OptionSet("Transmission", 2);
		try {
			auto.addOptionSet(set);
			OptionSet set2 = new OptionSet("Color", 10);
			auto.addOptionSet(set2);
			auto.removeOptionSetByName("Transmission");
			assertEquals(null, auto.getOptionSetByName("Transmission"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	
	@Test
	public void canAddOption() {
		OptionSet set = new OptionSet("Transmission", 2);
		try {
			auto.addOptionSet(set);
			
			Option opt = new Option("Automatic", 0);
			auto.addOptionInSet("Transmission", opt);
			
			assertEquals(opt, auto.getOptionInSet("Transmission", "Automatic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	
	@Test
	public void canGetOption() {
		OptionSet set = new OptionSet("Transmission", 2);
		try {
			auto.addOptionSet(set);
			
			Option opt = new Option("Automatic", 0);
			Option opt2 = new Option("Manual", -815);
			auto.addOptionInSet("Transmission", opt2);
			auto.addOptionInSet("Transmission", opt);
			
			assertEquals(opt, auto.getOptionInSet("Transmission", "Automatic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	
	@Test
	public void canRemoveOption() {
		OptionSet set = new OptionSet("Transmission", 2);
		try {
			auto.addOptionSet(set);
			
			Option opt = new Option("Automatic", 0);
			Option opt2 = new Option("Manual", -815);
			auto.addOptionInSet("Transmission", opt2);
			auto.addOptionInSet("Transmission", opt);
			auto.removeOptionInSet("Transmission", opt);
			
			assertEquals(null, auto.getOptionInSet("Transmission", "Automatic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

}
