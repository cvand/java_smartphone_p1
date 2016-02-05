/**
 * @author cvandera
 *
 */

package exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FixHelper {

	public static String readInputFromUser(String field) {
		String value = (field == null || field.equals("")) ? "value" : field;
		System.out.println("Please enter the " + value + " or type exit to stop the program:");

		// read user input for a new filename
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String s = br.readLine();

			// if the user typed "exit" then exit the program
			if ("exit".equals(s)) {
				System.exit(1);
			}
			
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
