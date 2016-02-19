/**
 * @author cvandera
 *
 */

package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Automobile;

public class SelectCarOption {
	
	private Automobile a;
	
	public SelectCarOption(Automobile a) {
		super();
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
	
	public void printOptions() {
		System.out.println(a.choicesToString());
	}
	
}
