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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import exception.AutoException;
import model.Automobile;

public class FileIO {

	private final String START_OF_AUTOMOTIVE_OBJECT = "-----";
	private final String MAKE_LABEL = "Make:";
	private final String MODEL_LABEL = "Model:";
	private final String PRICE_LABEL = "Price:";
	private final String OPTIONS_LABEL = "OPTIONS";

	private final String PROP_CAR_MAKE = "CarMake";
	private final String PROP_CAR_MODEL = "CarModel";
	private final String PROP_OPTION = "Option";
	private final String PROP_OPTION_VALUE = "OptionValue";

	// builAutomobileObject method only forwards any exceptions caused because
	// of problems with the configuration file. The remaining exceptions are
	// caught internally.
	public Automobile buildAutomobileObject(String filename, String fileType) throws AutoException {
		if (filename == null || filename.equals(""))
			throw new AutoException(AutoException.ExceptionType.INVALID_FILENAME, "The filename is invalid.").set("filename", filename);

		Automobile auto = null;

		// The accepted fileTypes are: properties and custom
		// In any other case, the application throws an exception
		if ("properties".equals(fileType)) {
			Properties props = readPropertiesFile(filename);
			if (props != null) {
				auto = buildAutomobileObject(props);
			}
		} else if ("custom".equals(fileType)) {
			auto = readCustomFile(filename);
		} else {
			throw new AutoException(AutoException.ExceptionType.INVALID_FILETYPE, "The filetype is invalid.").set("filename", filename)
					.set("filetype", fileType);
		}

		return auto;
	}

	private void readAutomotiveAttributes(Automobile auto, String key, String value) {
		switch (key) {
		case MAKE_LABEL:
			auto.setMake(value);
			break;
		case MODEL_LABEL:
			auto.setModel(value);
			break;
		case PRICE_LABEL:
			float price = Float.parseFloat(value);
			auto.setBasePrice(price);
			break;
		}
	}

	private void readOptionsForSet(Automobile auto, String setName, String optionsString) throws AutoException {
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
				throw new AutoException(AutoException.ExceptionType.MISSING_ATTRIBUTE, "Option is missing required attributes in configuration file.")
						.set("option", option);

			// remove the quotes from the option name
			String name = splits[0].substring(1, splits[0].length() - 1);
			boolean noError = false;
			float price = 0;
			do {
				try {
					// convert the price to float
					price = Float.parseFloat(splits[1]);

					noError = false;
					if (name == null || name.equals("")) {
						throw new AutoException(AutoException.ExceptionType.MISSING_OPTION_NAME, "Option name is missing from configuration file.")
								.set("name", name);
					}

					// add the option to the optionSet in the automotive
					// object
					auto.addOptionInSet(setName, name, price);
					noError = true;

				} catch (Exception e) {
					// if an exception is thrown it's gonna be either from
					// Float.parseFloat() (which will be an exception of type
					// other than AutoException, or from throwing an exception
					// if the name is not valid, or from auto.addOptionInSet()
					// which throws an AutoException.

					// Deal with the exception based on type
					if (e instanceof AutoException) {
						AutoException e1 = (AutoException) e;
						if (e1.getType() == AutoException.ExceptionType.MISSING_OPTION_NAME) {
							e1.fix(e1.getType().getErrorNumber());
							name = (String) e1.getFix("name");
						} else if (e1.getType() == AutoException.ExceptionType.MISSING_OPTION_SET) {
							e1.fix(e1.getType().getErrorNumber());
							setName = (String) e1.getFix("setName");
						}
					} else {
						AutoException e1 = new AutoException(AutoException.ExceptionType.MISSING_OPTION_PRICE,
								"Option price is invalid or missing from configuration file.").set("price", splits[1]);
						e1.fix(e1.getType().getErrorNumber());
						splits[1] = (String) e1.getFix("price");

					}

				}
			} while (!noError);
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

	private Automobile readCustomFile(String filename) throws AutoException {
		Automobile auto = null;
		boolean scanningOptions = false;
		FileReader file;
		try {
			file = new FileReader(filename);
		} catch (FileNotFoundException e) {
			throw new AutoException(AutoException.ExceptionType.MISSING_FILE, "The file is missing.").set("filename", filename);
		}
		BufferedReader buff = new BufferedReader(file);
		boolean eof = false;
		try {
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

						boolean noError = false;
						do {
							try {
								if (optionSetName.equals("")) {
									throw new AutoException(AutoException.ExceptionType.MISSING_OPTION_SET_NAME,
											"The option set name is missing from configuration file.").set("name", optionSetName);
								}
								noError = true;
							} catch (AutoException e) {
								if (e.getType() == AutoException.ExceptionType.MISSING_OPTION_SET_NAME) {
									e.fix(e.getType().getErrorNumber());
									optionSetName = (String) e.getFix("setName");
								}
							}
						} while (!noError);

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
		} catch (IOException e) {
			throw new AutoException(AutoException.ExceptionType.IO_EXCEPTION, "The was an error reading the configuration file.").set("filename",
					filename);
		}

		boolean noError = false;
		do {
			try {
				if (auto.getBasePrice() == 0) {
					throw new AutoException(AutoException.ExceptionType.MISSING_AUTO_PRICE,
							"The price of this automobile is missing from configuration file.").set("set", auto);
				}
				noError = true;
			} catch (AutoException e) {
				if (e.getType() == AutoException.ExceptionType.MISSING_AUTO_PRICE) {
					e.fix(e.getType().getErrorNumber());
					try {
						float price = Float.parseFloat((String) e.getFix("price"));
						auto.setBasePrice(price);
					} catch (Exception ex) {
						// wrong input parsed
						auto.setBasePrice((float) 0);
					}
				}
			}
		} while (!noError);

		return auto;
	}

	private Properties readPropertiesFile(String filename) throws AutoException {
		Properties props = new Properties();
		try {

			FileInputStream in = new FileInputStream(filename);
			props.load(in);
			return props;

		} catch (FileNotFoundException e) {
			throw new AutoException(AutoException.ExceptionType.MISSING_FILE, "The file is missing.").set("filename", filename);
		} catch (IOException e) {
			throw new AutoException(AutoException.ExceptionType.IO_EXCEPTION, "The was an error reading the configuration file.").set("filename",
					filename);
		}
	}

	public Automobile buildAutomobileObject(Properties props) {
		Automobile auto = new Automobile();
		parseProperties(props, auto);

		return auto;
	}

	private void parseProperties(Properties props, Automobile auto) {
		// parse through all the properties of the file
		Enumeration<?> en = props.propertyNames();
		// Every time we find an Option# key we save it and the following
		// OptionValue# properties are going to be added to that optionSet

		Map<String, String> optionSetMap = new HashMap<String, String>();

		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();

			// check what the current property key is
			if (PROP_CAR_MAKE.equals(key)) {
				auto.setMake(props.getProperty(key));
			} else if (PROP_CAR_MODEL.equals(key)) {
				auto.setModel(props.getProperty(key));
			} else if (key.contains(PROP_OPTION_VALUE)) {
				// skip those properties for the next iteration
			}else if (key.contains(PROP_OPTION)) {
				String setName = props.getProperty(key);
				auto.addOptionSet(setName);

				// remove from the key all non numeric characters and make that
				// number the key for the optionSetMap
				optionSetMap.put(key.replaceAll("[^\\d.]", ""), setName);
			}
		}

		// reset the enumerator and add the option values now that the
		// optionSetMap is filled with all the options
		en = props.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			if (key.contains(PROP_OPTION_VALUE)) {
				boolean noError = false;
				do {
					// remove from the key all non numeric characters and find
					// the corrsponding optionSet name in the optionSetMap
					String k = key.replaceAll("[^\\d.]", "");
					String setName = optionSetMap.get(k);
					try {

						auto.addOptionInSet(setName, props.getProperty(key), null);
						noError = true;
					} catch (AutoException e) {
						// if an exception is thrown it's gonna be from
						// auto.addOptionInSet()
						// which throws an AutoException.

						// Deal with the exception based on type
						if (e instanceof AutoException) {
							AutoException e1 = (AutoException) e;
							if (e1.getType() == AutoException.ExceptionType.MISSING_OPTION_SET) {
								e1.fix(e1.getType().getErrorNumber());
								setName = (String) e1.getFix("setName");
							}
						}
					}
				} while (!noError);
			}
		}
	}
}
