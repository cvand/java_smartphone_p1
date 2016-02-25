/**
 * @author cvandera
 *
 */

package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import model.Automobile;

public class SelectCarOption {
	
	private Automobile a;
	
	public SelectCarOption() {
	}
	
	public SelectCarOption(Automobile a) {
		this.a = a;
	}

	public void setAutoOption(String optionSet, String optionChoice) {
		a.setOptionChoice(optionSet, optionChoice);
		System.out.println("Changed Option " + optionSet);
	}
	
	public static String readModelfromInput() {
		System.out.println("Please select one automobile by entering its name:");

		// read user input for a new filename
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String selectAutomobileFromList(List<String> autos) {
		System.out.println("The list of available automobiles is:");
		for (String a : autos) {
			System.out.println(a);
		}
		String modelName = readModelfromInput();
		String m = "modelName:" + modelName;
		return m;
	}
	
	public void printOptions() {
		if (a == null) {
			System.out.println("No configuration found.");
			return;
		}
		System.out.println(a.choicesToString());
	}
	
	public void setAutomobile(Automobile a) {
		this.a = a;
	}
	
}
