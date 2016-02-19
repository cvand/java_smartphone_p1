/**
 * @author cvandera
 *
 */

package scale;

import adapter.BuildAuto;
import exception.AutoException;
import model.Automobile;

public class EditOptions extends Thread implements IEditOptions {

	BuildAuto auto;

	public EditOptions(BuildAuto auto) {
		super();
		this.auto = auto;
	}
	
	@Override
	public void run() {
		super.run();
		// wait for a random amount of time before executing any calls
		randomWait();
	}

	@Override
	public synchronized void setOptionName(String modelName, String optionSetName, String optionOldName, String optionNewName) {
		Float price = (float) 0;

		// make sure that the modelName matches one of the name of the
		// automobiles
		Automobile a = auto.findAutomobile(modelName);
		if (a != null) {
			boolean noError = false;
			do {
				try {
					if (price == null) {
						throw new AutoException(AutoException.ExceptionType.INVALID_INPUT, "The input you entered is invalid.");
					}

					a.updateOptionInSet(optionSetName, optionOldName, optionNewName, null);
					noError = true;
				} catch (AutoException e) {
					// exception handling in case there is no option set with
					// that name in auto
					if (e.getType() == AutoException.ExceptionType.MISSING_OPTION_SET) {
						e.fix(e.getType().getErrorNumber());
						optionSetName = (String) e.getFix("setName");
					} else if (e.getType() == AutoException.ExceptionType.MISSING_OPTION) {
						// exception handling in case there is no option with
						// that name in optionset in auto
						e.fix(e.getType().getErrorNumber());
						String option = (String) e.getFix("name");

						// deal with user entered an input that can't be
						// converted to float
						try {
							price = Float.parseFloat((String) e.getFix("price"));
							a.addOptionInSet(optionSetName, option, price);
						} catch (Exception e1) {
							price = null;
						}
					}
				}
			} while (!noError);
		}
	}

	@Override
	public synchronized void setOptionPrice(String modelName, String optionSetName, String optionName, Float optionPrice) {
		Float price = (float) 0;

		// make sure that the modelName matches one of the name of the
		// automobiles
		Automobile a = auto.findAutomobile(modelName);
		if (a != null) {
			boolean noError = false;
			do {
				try {
					if (price == null) {
						throw new AutoException(AutoException.ExceptionType.INVALID_INPUT, "The input you entered is invalid.");
					}

					a.updateOptionInSet(optionSetName, optionName, null, optionPrice);
				} catch (AutoException e) {
					// exception handling in case there is no option set with
					// that name in auto
					if (e.getType() == AutoException.ExceptionType.MISSING_OPTION_SET) {
						e.fix(e.getType().getErrorNumber());
						optionSetName = (String) e.getFix("setName");
					} else if (e.getType() == AutoException.ExceptionType.MISSING_OPTION) {
						// exception handling in case there is no option with
						// that name in optionset in auto
						e.fix(e.getType().getErrorNumber());
						String option = (String) e.getFix("name");

						// deal with user entered an input that can't be
						// converted to float
						try {
							price = Float.parseFloat((String) e.getFix("price"));
							a.addOptionInSet(optionSetName, option, price);
						} catch (Exception e1) {
							price = null;
						}
					}
				}
			} while (!noError);
		}
	}

	@Override
	public synchronized void setOptionChoice(String modelName, String optionSetName, String choice) {
		// make sure that the modelName matches one of the name of the
		// automobiles
		Automobile a = auto.findAutomobile(modelName);
		if (a != null) {
			a.setOptionChoice(optionSetName, choice);
		}
	}

	void randomWait() {
		try {
			double wait = 3000 * Math.random();
			System.out.println("wait for " + wait);
			Thread.sleep((long) wait);
		} catch (InterruptedException e) {
			System.out.println("Interrupted!");
		}
	}

}
