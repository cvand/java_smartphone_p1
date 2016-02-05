/**
 * @author cvandera
 *
 */

package adapter;

import exception.AutoException;
import model.Automobile;
import util.FileIO;

public abstract class ProxyAutomobile {

	private static Automobile auto;

	public void updateOptionSetName(String modelName, String optionSetName, String newName) {
		// make sure that the modelName matches the name of the automobile
		if (auto.getModel().equals(modelName)) {
			boolean noError = false;
			do {
				try {
					// rename the optionSet
					auto.updateOptionSet(optionSetName, newName);
				} catch (AutoException e) {
					// exception handling in case there is no option set with that name in auto
					if (e.getType() == AutoException.ExceptionType.MISSING_OPTION_SET) {
						e.fix(e.getType().getErrorNumber());
						optionSetName = (String) e.getFix("setName");
					}
				}
			} while (!noError);
		}
	}

	public void updateOptionPrice(String modelName, String optionSetName, String option, Float newPrice) {
		// make sure that the modelName matches the name of the automobile
		if (auto.getModel().equals(modelName)) {
			boolean noError = false;
			do {
				try {
					if (newPrice == null) {
						throw new AutoException(AutoException.ExceptionType.INVALID_INPUT, "The input you entered is invalid.");
					}
					auto.updateOptionInSet(optionSetName, option, option, newPrice);
					noError = true;
				} catch (AutoException e) {
					if (e.getType() == AutoException.ExceptionType.MISSING_OPTION_SET) {
						// exception handling in case there is no option set with that name in auto
						e.fix(e.getType().getErrorNumber());
						optionSetName = (String) e.getFix("setName");
					} else if (e.getType() == AutoException.ExceptionType.MISSING_OPTION) {
						// exception handling in case there is no option with that name in auto
						e.fix(e.getType().getErrorNumber());
						option = (String) e.getFix("name");
						
						// deal with user entered an input that can't be converted to float
						try {
							newPrice = Float.parseFloat((String) e.getFix("price"));
							auto.addOptionInSet(optionSetName, option, newPrice);
						} catch (Exception e1) {
							newPrice = null;
						}
					} else if (e.getType() == AutoException.ExceptionType.INVALID_INPUT) {
						// deal with user entered an input that can't be converted to float
						try {
							newPrice = Float.parseFloat((String) e.getFix("price"));
						} catch (Exception e1) {
							newPrice = null;
						}
					}
				}
			} while (!noError);

		}
	}

	public void buildAuto(String filename) {
		FileIO fileIO = new FileIO();
		boolean noError = false;
		do {
			try {

				// Build Automobile Object from a file.
				auto = fileIO.buildAutomobileObject(filename);
				noError = true;
			} catch (AutoException e) {
				// Figure out what type of exception was thrown and handle it by
				// calling fix and getting any information needed from the fix

				if (e.getType() == AutoException.ExceptionType.INVALID_FILENAME || e.getType() == AutoException.ExceptionType.MISSING_FILE
						|| e.getType() == AutoException.ExceptionType.IO_EXCEPTION) {
					// if the exception is invalid filename, missing file or any
					// io
					// exception something is wrong with the file so handle it
					// by
					// asking the user for the filename again

					e.fix(e.getType().getErrorNumber());
					filename = (String) e.getFix("filename");

				}
			}

		} while (!noError);
	}

	public void printAuto(String modelName) {
		if (auto.getModel().equals(modelName)) {
			System.out.println(auto);
		}
	}
	
	public String autoToString(String modelName) {
		if (auto.getModel().equals(modelName)) {
			return auto.toString();
		}
		return null;
	}
	
}
