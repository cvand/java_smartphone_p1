/**
 * @author cvandera
 *
 */

package model;

import java.io.Serializable;

public class Automotive implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String name;
	OptionSet opset[];
	private int currentSize = 0;

	Automotive(int size, String n) {
		opset = new OptionSet[size];
		name = n;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OptionSet[] getOpset() {
		return opset;
	}

	public void setOpset(OptionSet[] opset) {
		this.opset = opset;
		// calculate the actual size of the array
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] != null) currentSize++;
			else break;
		}
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
	
	public Option getOptionInSet(String setName, String optName) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null) throw new Exception("No such OptionSet exists in this model.");
		
		return set.getOptionByName(optName);
	}
	
	public void addOptionInSet(String setName, Option opt) throws Exception {
		OptionSet set = getOptionSetByName(setName);
		if (set == null) throw new Exception("No such OptionSet exists in this model.");
		
		set.addOption(opt);
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
