/**
 * @author cvandera
 *
 */

package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OptionSetTest {

	OptionSet set;

	@Before
	public void setUp() throws Exception {
		set = new OptionSet("Color", 10);
	}

	@Test
	public void canAddOption() {
		Option o = new Option("Fort Knox Gold Clearcoat Metallic", 0);
		try {
			set.addOption(o);
			assertEquals(o, set.getOptionByName("Fort Knox Gold Clearcoat Metallic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	@Test(expected = Exception.class)
	public void cannotAddOptionOverLimit() throws Exception {
		for (int i = 0; i < 12; i++) {
			Option o = new Option("Fort Knox Gold Clearcoat Metallic " + i, 0);
			set.addOption(o);
		}
	}

	@Test
	public void canDeleteOption() {
		Option o = new Option("Fort Knox Gold Clearcoat Metallic", 0);
		try {
			set.addOption(o);
			set.removeOption(o);
			
			assertEquals(null, set.getOptionByName("Fort Knox Gold Clearcoat Metallic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	@Test
	public void canDeleteOptionByName() {
		Option o = new Option("Fort Knox Gold Clearcoat Metallic", 0);
		try {
			set.addOption(o);
			set.removeOptionByName("Fort Knox Gold Clearcoat Metallic");
			
			assertEquals(null, set.getOptionByName("Fort Knox Gold Clearcoat Metallic"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	@Test
	public void canFindOptionByName() {
		Option o = new Option("Fort Knox Gold Clearcoat Metallic", 0);
		try {
			set.addOption(o);
			Option result = set.getOptionByName("Fort Knox Gold Clearcoat Metallic");
			
			assertEquals(o, result);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

}
