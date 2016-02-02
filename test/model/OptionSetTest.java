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

public class OptionSetTest {

	OptionSet set;

	@Before
	public void setUp() throws Exception {
		set = new OptionSet("Color");
	}

	@Test
	public void canAddOption() {
		try {
			set.addOption("Fort Knox Gold Clearcoat Metallic", 0);
			
			Option o = set.getOptionByName("Fort Knox Gold Clearcoat Metallic");
			assertNotEquals(null, o);
			assertEquals("Fort Knox Gold Clearcoat Metallic", o.getName());
			assertEquals(0, o.getPrice(), 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	@Test
	public void canDeleteOption() {
		try {
			set.addOption("Fort Knox Gold Clearcoat Metallic", 0);
			set.removeOptionByName("Fort Knox Gold Clearcoat Metallic");
			
			assertEquals(null, set.getOptionByName("Fort Knox Gold Clearcoat Metallic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}


	@Test
	public void canFindOptionByName() {
		try {
			set.addOption("Fort Knox Gold Clearcoat Metallic", 0);
			Option o = set.getOptionByName("Fort Knox Gold Clearcoat Metallic");
			
			assertNotEquals(null, o);
			assertEquals("Fort Knox Gold Clearcoat Metallic", o.getName());
			assertEquals(0, o.getPrice(), 0);

			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

}
