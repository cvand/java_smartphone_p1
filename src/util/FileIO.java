/**
 * @author cvandera
 *
 */

package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

import model.Automobile;

public class FileIO {

	private final String START_OF_AUTOMOTIVE_OBJECT = "-----";
	private final String CAR_LABEL = "Car:";
	private final String PRICE_LABEL = "Price:";
	private final String OPTIONS_LABEL = "OPTIONS";

	public Automobile buildAutomobileObject(String filename) {
		Automobile auto = null;
		boolean scanningOptions = false;
		try {
			FileReader file = new FileReader(filename);
			BufferedReader buff = new BufferedReader(file);
			boolean eof = false;
			while (!eof) {
				String line = buff.readLine();
				if (line == null)
					eof = true;
				else {
					if (line.equals(START_OF_AUTOMOTIVE_OBJECT)) {
						auto = new Automobile();
						scanningOptions = false;
						continue;
					}
					if (line.equals(OPTIONS_LABEL)) {
						scanningOptions = true;
						continue;
					}

					// tokenize the line
					StringTokenizer st = new StringTokenizer(line, "\t");
					if (st.countTokens() != 2)
						continue;
					
					// first token will be the title
					String key = st.nextToken();
					// second token will be the value containing either the
					// value of an attribute or the string with the options of
					// an optionSet
					String value = st.nextToken();

					if (scanningOptions) {
						// scanning options from the file
						String optionSetName = key.replace(":", "");
						// add the optionSet to the automotive and then
						// parse the line and gradually add options as they
						// are found in the value string
						auto.addOptionSet(optionSetName);
						readOptionsForSet(auto, optionSetName, value);

					} else {
						// scanning attributes of the automotive
						readAutomotiveAttributes(auto, key, value);
					}
				}
			}
			buff.close();
		} catch (Exception e) {
			System.out.println("Error " + e.toString());
		}

		return auto;
	}

	private void readAutomotiveAttributes(Automobile auto, String key, String value) {
		switch (key) {
		case CAR_LABEL:
			auto.setName(value);
			break;
		case PRICE_LABEL:
			float price = Float.parseFloat(value);
			auto.setBasePrice(price);
			break;
		}
	}

	private void readOptionsForSet(Automobile auto, String setName, String optionsString) {
		StringTokenizer st = new StringTokenizer(optionsString);
		while (st.hasMoreTokens()) {
			String option = st.nextToken("{");
			// the option variable should now contain smth like: "Present",
			// 595},

			// clean the input from redundant characters
			option = option.replace("},", "");
			option = option.replace("}", "");
			// end up with a string like: "Present", 595

			// split in the attribute values of the option
			// should get an array of 2 elements: name and price
			String[] splits = option.split(", ");
			if (splits.length != 2)
				continue;

			// remove the quotes from the option name
			String name = splits[0].substring(1, splits[0].length() - 1);
			// convert the price to float
			float price = Float.parseFloat(splits[1]);

			// add the option to the optionSet in the automotive object
			try {
				auto.addOptionInSet(setName, name, price);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

	}

	public void serializeAutomotive(Automobile auto, String filename) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(auto);
		out.close();
	}

	public Automobile deserializeAutomotive(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
		Automobile newStaff = (Automobile) in.readObject();
		in.close();
		return newStaff;
	}
}
