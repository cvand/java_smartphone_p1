/**
 * @author cvandera
 *
 */

package adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import exception.AutoException;
import model.Automobile;
import util.FileIO;

public abstract class ProxyAutomobile {

	private static LinkedHashMap<String, Automobile> autos = new LinkedHashMap<String, Automobile>();

	public void updateOptionSetName(String modelName, String optionSetName, String newName) {
		// make sure that the modelName matches one of the name of the
		// automobiles
		Automobile auto = findAutomobile(modelName);
		if (auto != null) {
			boolean noError = false;
			do {
				try {
					// rename the optionSet
					auto.updateOptionSet(optionSetName, newName);
				} catch (AutoException e) {
					// exception handling in case there is no option set with
					// that name in auto
					if (e.getType() == AutoException.ExceptionType.MISSING_OPTION_SET) {
						e.fix(e.getType().getErrorNumber());
						optionSetName = (String) e.getFix("setName");
					}
				}
			} while (!noError);
		}
	}

	public Automobile findAutomobile(String modelName) {
		// fetch the automobile with key = modelName, if the map doesn't have
		// one it'll return null
		return autos.get(modelName);
	}

	public void updateOptionPrice(String modelName, String optionSetName, String option, Float newPrice) {
		// make sure that the modelName matches the name of the automobile
		Automobile auto = findAutomobile(modelName);
		if (auto != null) {
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
						// exception handling in case there is no option set
						// with that name in auto
						e.fix(e.getType().getErrorNumber());
						optionSetName = (String) e.getFix("setName");
					} else if (e.getType() == AutoException.ExceptionType.MISSING_OPTION) {
						// exception handling in case there is no option with
						// that name in auto
						e.fix(e.getType().getErrorNumber());
						option = (String) e.getFix("name");

						// deal with user entered an input that can't be
						// converted to float
						try {
							newPrice = Float.parseFloat((String) e.getFix("price"));
							auto.addOptionInSet(optionSetName, option, newPrice);
						} catch (Exception e1) {
							newPrice = null;
						}
					} else if (e.getType() == AutoException.ExceptionType.INVALID_INPUT) {
						// deal with user entered an input that can't be
						// converted to float
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

	public synchronized void buildAuto(String filename, String fileType) {
		FileIO fileIO = new FileIO();
		boolean noError = false;
		do {
			try {

				// Build Automobile Object from a file.
				Automobile auto = fileIO.buildAutomobileObject(filename, fileType);
				noError = true;

				// add the newly created model to the map
				autos.put(auto.getModel(), auto);
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

				} else if (e.getType() == AutoException.ExceptionType.INVALID_FILETYPE) {
					// if the exception is invalid filetype something is wrong
					// with the type of file so handle it by asking the user for
					// the filename and the filetype again

					e.fix(e.getType().getErrorNumber());
					filename = (String) e.getFix("filename");
					fileType = (String) e.getFix("filetype");

				}
			}

		} while (!noError);
	}

	public void printAuto(String modelName) {
		Automobile auto = findAutomobile(modelName);
		if (auto != null) {
			System.out.println(auto);
		}
	}

	public String autoToString(String modelName) {
		Automobile auto = findAutomobile(modelName);
		if (auto != null) {
			return auto.toString();
		}
		return null;
	}

	public Automobile createAutomobileFromProperties(Properties properties) {
		FileIO fileIO = new FileIO();
		// Build Automobile Object from a properties object.
		Automobile auto = fileIO.buildAutomobileObject(properties);
		return auto;
	}

	public void saveAutomobile(Automobile auto) {
		autos.put(auto.getModel(), auto);
	}

	public List<String> getAvailableModels() {
		List<String> models = new ArrayList<String>();
		models.addAll(autos.keySet());

		return models;
	}

	public String getSerializedAutomobile(String modelName) {
		Automobile auto = autos.get(modelName);
		if (auto == null) return null;
		return auto.toString();
	}

}
