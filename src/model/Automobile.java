/**
 * @author cvandera
 *
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.OptionSet.Option;

public class Automobile implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private float basePrice;
	private ArrayList<OptionSet> opset = new ArrayList<OptionSet>();

	public Automobile() {
	}

	public Automobile(String name, float basePrice) {
		super();
		this.name = name;
		this.basePrice = basePrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OptionSet getOptionSet(int index) {
		return opset.get(index);
	}

	public float getBasePrice() {
		return basePrice;
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

	public void updateOptionSet(String oldName, String newName) {
		OptionSet set = getOptionSetByName(oldName);
		if (set == null)
			return;
		int index = getOptionSetIndex(set);
		this.opset.get(index).setName(newName);
	}

	public void addOptionSet(OptionSet opset) throws Exception {
		// add the object
		this.opset.add(opset);
	}

	public void addOptionSet(String name) throws Exception {
		OptionSet set = new OptionSet(name);
		addOptionSet(set);
	}

	public Option getOptionInSet(String setName, String optName) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new Exception("No such OptionSet exists in this model.");

		return set.getOptionByName(optName);
	}

	public void updateOptionInSet(String setName, String optName, String newName, float newPrice) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new Exception("No such OptionSet exists in this model.");

		Option opt = set.getOptionByName(optName);
		if (opt == null)
			throw new Exception("No such Option exists in this OptionSet in this model.");

		set.updateOption(opt, newName, newPrice);
	}

	public void addOptionInSet(String setName, String name, float price) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new Exception("No such OptionSet exists in this model.");

		set.addOption(name, price);
	}

	public void removeOptionInSet(String setName, Option opt) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null)
			throw new Exception("No such OptionSet exists in this model.");

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Automotive :: {");
		sb.append("name: ");
		sb.append(name);
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

		sb.append("}");
		return sb.toString();
	}

}
