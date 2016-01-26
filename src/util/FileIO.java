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

import model.Automotive;

public class FileIO {

	private final String START_OF_AUTOMOBILE_OBJECT = "-----";
	private final String CAR_LABEL = "Car:";
	private final String PRICE_LABEL = "Price:";
	private final String OPTIONS_ATTR_LABEL = "Options:";
	private final String OPTIONS_LABEL = "OPTIONS";

	public Automotive buildAutomotiveObject(String filename) {
		Automotive auto = null;
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
					if (line.equals(START_OF_AUTOMOBILE_OBJECT)) {
						auto = new Automotive();
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
						int n = calculateNumberOfOptions(value);
						String optionSetName = key.replace(":", "");
						if (n > 0) {
							// add the optionSet to the automotive and then
							// parse the line and gradually add options as they
							// are found in the value string
							auto.addOptionSet(optionSetName, n);
							readOptionsForSet(auto, optionSetName, value);
						}

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

	private void readAutomotiveAttributes(Automotive auto, String key, String value) {
		switch (key) {
		case CAR_LABEL:
			auto.setName(value);
			break;
		case PRICE_LABEL:
			float price = Float.parseFloat(value);
			auto.setBasePrice(price);
			break;
		case OPTIONS_ATTR_LABEL:
			int size = Integer.parseInt(value);
			auto.setOptionSetSize(size);
			break;
		}
	}

	private int calculateNumberOfOptions(String line) {
		int count = countOccurrencesInString(line, "},{");

		if (count == 0) {
			// make sure that there is at least one option
			int c = countOccurrencesInString(line, "{\"");
			if (c == 0)
				return 0;
		}
		count++;

		return count;
	}

	private void readOptionsForSet(Automotive auto, String setName, String optionsString) {
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

	private int countOccurrencesInString(String str, String findStr) {
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

			lastIndex = str.indexOf(findStr, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}

		return count;
	}

	public void serializeAutomotive(Automotive auto, String filename) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(auto);
		out.close();
	}

	public Automotive deserializeAutomotive(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
		Automotive newStaff = (Automotive) in.readObject();
		in.close();
		return newStaff;
	}
}
