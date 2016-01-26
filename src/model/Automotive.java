/**
 * @author cvandera
 *
 */

package model;

import java.io.Serializable;

import model.OptionSet.Option;

public class Automotive implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private float basePrice;
	private OptionSet opset[];
	private int currentSize = 0;

	public Automotive() {
	}
	
	public Automotive(String name, float basePrice, int size) {
		super();
		this.name = name;
		this.basePrice = basePrice;
		this.opset = new OptionSet[size];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OptionSet getOptionSet(int index) {
		return opset[index];
	}
	
	public float getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	
	public void setOptionSetSize(int size) {
		this.opset = new OptionSet[size];
	}

	public void setOpset(OptionSet[] opset) {
		this.opset = opset;
		// calculate the actual size of the array
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] != null) currentSize++;
			else break;
		}
	}
	
	public void updateOptionSet(OptionSet set, String newName) {
		int index = getOptionSetIndex(set);
		this.opset[index].setName(newName);
	}
	
	public void addOptionSet(OptionSet opset) throws Exception {
		// if the array has room for more objects
		if (currentSize < this.opset.length) {
			//add the object after the last non-null object
			this.opset[currentSize] = opset;
			currentSize++;
		} else {
			throw new Exception("You are trying to add elements to a full array.");
		}
	}
	
	public void addOptionSet(String name, int size) throws Exception {
		OptionSet set = new OptionSet(name, size);
		addOptionSet(set);
	}
	
	public Option getOptionInSet(String setName, String optName) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null) throw new Exception("No such OptionSet exists in this model.");
		
		return set.getOptionByName(optName);
	}
	
	public void updateOptionInSet(String setName, Option opt, String newName, float newPrice) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null) throw new Exception("No such OptionSet exists in this model.");
		
		set.updateOption(opt, newName, newPrice);
	}
	
	public void addOptionInSet(String setName, String name, float price) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null) throw new Exception("No such OptionSet exists in this model.");
		
		set.addOption(name, price);
	}
	
	public void removeOptionInSet(String setName, Option opt) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null) throw new Exception("No such OptionSet exists in this model.");
		
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
		// Find the OptionSet opt in the array
		int index = -1;
		for (int i = 0; i < this.opset.length; i++) {
			OptionSet o = this.opset[i];
			if (o == opset) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void removeOptionSet(OptionSet opset) {
		int index = getOptionSetIndex(opset);
		
		// if OptionSet opset was found in the array, remove it and move all the
		// remaining optionSets after that one row above
		if (index > -1) {
			for (int i = index; i < this.currentSize - 1; i++) {
				this.opset[i] = this.opset[i + 1];
			}
			
			if (index == (currentSize - 1)) {
				this.opset[index] = null;
			}

			// update the current actual size of the array
			currentSize--;
		}
	}

	public void removeOptionSetByName(String name) {
		OptionSet o = getOptionSetByName(name);
		if (o == null) return;
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
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] != null) {
				sb.append(opset[i]);
				if (i < (opset.length - 1)) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");

		sb.append("}");
		return sb.toString();
	}
	
}
