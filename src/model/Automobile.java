/**
 * @author cvandera
 *
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

import exception.AutoException;
import model.OptionSet.Option;

public class Automobile implements Serializable {
	private static final long serialVersionUID = 1L;

	private String make;
	private String model;
	private float basePrice;
	private ArrayList<OptionSet> opset = new ArrayList<OptionSet>();

	private ArrayList<OptionSet> userChoice = new ArrayList<OptionSet>();
	
	public Automobile() {
	}

	public Automobile(String make, String model, float basePrice) {
		super();
		this.make = make;
		this.model = model;
		this.basePrice = basePrice;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public OptionSet getOptionSet(int index) {
		return opset.get(index);
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public float getBasePrice() {
		return basePrice;
	}

	public float getTotalPrice() {
		float totalPrice = basePrice;
		for (OptionSet set : userChoice) {
			Option option = set.getOptionChoice();
			if (option == null) continue;
			float optionPrice = option.getPrice();
			totalPrice += optionPrice;
		}
		
		return totalPrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	public void setOpset(ArrayList<OptionSet> opset) {
		this.opset = new ArrayList<OptionSet>();
		// calculate the actual size of the array
		for (int i = 0; i < opset.size(); i++) {
			this.opset.add(opset.get(i));
		}
	}

	public void updateOptionSet(String oldName, String newName) throws AutoException {
		OptionSet set = getOptionSetByName(oldName);
		if (set == null)
			throw new AutoException(AutoException.ExceptionType.MISSING_OPTION_SET, "This option set doesn't exist in this automobile configuration.").set("name", oldName);
		int index = getOptionSetIndex(set);
		this.opset.get(index).setName(newName);
	}

	public void addOptionSet(OptionSet opset)  {
		// add the object
		this.opset.add(opset);
	}

	public void addOptionSet(String name) {
		OptionSet set = new OptionSet(name);
		addOptionSet(set);
	}

	public Option getOptionInSet(String setName, String optName) throws AutoException {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new AutoException(AutoException.ExceptionType.MISSING_OPTION_SET, "This option set doesn't exist in this automobile configuration.").set("name", setName);

		return set.getOptionByName(optName);
	}

	public void updateOptionInSet(String setName, String optName, String newName, float newPrice) throws AutoException {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new AutoException(AutoException.ExceptionType.MISSING_OPTION_SET, "This option set doesn't exist in this automobile configuration.").set("name", setName);

		Option opt = set.getOptionByName(optName);
		if (opt == null)
			throw new AutoException(AutoException.ExceptionType.MISSING_OPTION, "This option doesn't exist in this option set in this automobile configuration.").set("name", optName);

		set.updateOption(opt, newName, newPrice);
	}

	public void addOptionInSet(String setName, String name, float price) throws AutoException {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new AutoException(AutoException.ExceptionType.MISSING_OPTION_SET, "This option set doesn't exist in this automobile configuration.").set("name", setName);

		set.addOption(name, price);
	}

	public void removeOptionInSet(String setName, Option opt) throws AutoException {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new AutoException(AutoException.ExceptionType.MISSING_OPTION_SET, "This option set doesn't exist in this automobile configuration.").set("name", setName);

		set.removeOption(opt);
	}

	public OptionSet getOptionSetByName(String name) {
		// look in the optset for an object with name = name
		for (OptionSet optionSet : opset) {
			if (optionSet.getName().equals(name))
				return optionSet;
		}
		return null;
	}

	private int getOptionSetIndex(OptionSet opset) {
		// Find the OptionSet opt in the list
		int index = -1;
		for (int i = 0; i < this.opset.size(); i++) {
			OptionSet o = this.opset.get(i);
			if (o == opset) {
				index = i;
				break;
			}
		}
		return index;
	}

	public void removeOptionSet(OptionSet opset) {
		int index = getOptionSetIndex(opset);

		// if OptionSet opset was found in the list, remove it
		if (index > -1) {
			this.opset.remove(index);
		}
	}

	public void removeOptionSetByName(String name) {
		OptionSet o = getOptionSetByName(name);
		if (o == null)
			return;
		removeOptionSet(o);
	}
	
	public String getOptionChoice(String setName) {
		for (OptionSet set : userChoice) {
			if (set.getName().equals(setName)) {
				return set.getOptionChoice().getName();
			}
		}
		return null;
	}
	
	public float getOptionChoicePrice(String setName) {
		for (OptionSet set : userChoice) {
			if (set.getName().equals(setName)) {
				return set.getOptionChoice().getPrice();
			}
		}
		return 0;
	}
	
	public void setOptionChoice(String setName, String optionName) {
		for (OptionSet set : userChoice) {
			if (set.getName().equals(setName)) {
				set.setOptionChoice(optionName);
				return;
			}
		}
		
		// if optionSet not in the userChoise, find the set and add it
		OptionSet set = getOptionSetByName(optionName);
		if (set == null) return;
		set.setOptionChoice(optionName);
		userChoice.add(set);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Automotive :: {");
		sb.append("make: ");
		sb.append(make);
		sb.append(", ");
		sb.append("model: ");
		sb.append(model);
		sb.append(", ");
		sb.append("basePrice: ");
		sb.append(basePrice);
		sb.append(", ");

		// printing all the options of the optionSet
		sb.append("optionSets: [");
		for (int i = 0; i < opset.size(); i++) {
			sb.append(opset.get(i));
			if (i < (opset.size() - 1)) {
				sb.append(", ");
			}
		}
		sb.append("]");

		sb.append(", ");
		sb.append("userChoice: [");
		for (int i = 0; i < userChoice.size(); i++) {
			sb.append(userChoice.get(i));
			if (i < (userChoice.size() - 1)) {
				sb.append(", ");
			}
		}
		sb.append("]");
		
		sb.append("}");
		return sb.toString();
	}

}
