/**
 * @author cvandera
 *
 */

package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.OptionSet.Option;

public class AutomotiveTest {

	Automobile auto;
	
	@Before
	public void setUp() throws Exception {
		auto = new Automobile("Focus Wagon ZTW", 18445);
	}

	@Test
	public void canAddOptionSet() {
		OptionSet set = new OptionSet("Transmission");
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
		OptionSet set = new OptionSet("Transmission");
		try {
			auto.addOptionSet(set);
			OptionSet set2 = new OptionSet("Color");
			auto.addOptionSet(set2);
			assertEquals(set, auto.getOptionSetByName("Transmission"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	
	@Test
	public void canRemoveOptionSetByName() {
		OptionSet set = new OptionSet("Transmission");
		try {
			auto.addOptionSet(set);
			OptionSet set2 = new OptionSet("Color");
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
		OptionSet set = new OptionSet("Transmission");
		try {
			auto.addOptionSet(set);
			
			auto.addOptionInSet("Transmission", "Automatic", 0);
			
			Option o = auto.getOptionInSet("Transmission", "Automatic");
			assertNotEquals(null, o);
			assertEquals("Automatic", o.getName());
			assertEquals(0, o.getPrice(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	
	@Test
	public void canGetOption() {
		OptionSet set = new OptionSet("Transmission");
		try {
			auto.addOptionSet(set);
			
			auto.addOptionInSet("Transmission", "Manual", -815);
			auto.addOptionInSet("Transmission", "Automatic", 0);
			
			Option o = auto.getOptionInSet("Transmission", "Automatic");
			assertNotEquals(null, o);
			assertEquals("Automatic", o.getName());
			assertEquals(0, o.getPrice(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	
	@Test
	public void canRemoveOption() {
		OptionSet set = new OptionSet("Transmission");
		try {
			auto.addOptionSet(set);
			
			auto.addOptionInSet("Transmission", "Automatic", 0);
			auto.addOptionInSet("Transmission", "Manual", -815);
			
			Option o = auto.getOptionInSet("Transmission", "Automatic");
			auto.removeOptionInSet("Transmission", o);
			
			assertEquals(null, auto.getOptionInSet("Transmission", "Automatic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

}
