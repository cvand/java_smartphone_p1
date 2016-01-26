/**
 * @author cvandera
 *
 */

package model;

import java.io.Serializable;

public class OptionSet implements Serializable {
	private static final long serialVersionUID = 1L;
	String name;
	Option opt[];
	// currentSize holds the actual size of the opt array (the number of
	// elements in the array that are not null)
	private int currentSize = 0;

	OptionSet(String n, int size) {
		opt = new Option[size];
		name = n;
	}

	public Option getOptionByName(String name) {
		// look in the opt for an object with name = name
		for (int i = 0; i < currentSize; i++) {
			Option option = this.opt[i];
			if (option.getName().equals(name))
				return option;
		}
		return null;
	}

	private int getOptionIndex(Option opt) {
		// Find the Option opt in the array
		int index = -1;
		for (int i = 0; i < this.opt.length; i++) {
			Option o = this.opt[i];
			if (o == opt) {
				index = i;
				break;
			}
		}
		return index;
	}

	public Option[] getOptions() {
		return opt;
	}

	public void setOptions(Option[] opt) {
		this.opt = opt;
		// calculate the actual size of the array
		for (int i = 0; i < opt.length; i++) {
			if (opt[i] != null)
				currentSize++;
			else
				break;
		}
	}

	public void addOption(Option opt) throws Exception {
		// if the array has room for more objects
		if (currentSize < this.opt.length) {
			// add the object after the last non-null object
			this.opt[currentSize] = opt;
			currentSize++;
		} else {
			throw new Exception("You are trying to add elements to a full array.");
		}
	}
	
	public void removeOption(Option opt) {
		int index = getOptionIndex(opt);
		
		// if Option opt was found in the array, remove it and move all the
		// remaining options after that one row above
		if (index > -1) {
			for (int i = index; i < this.currentSize - 1; i++) {
				this.opt[i] = this.opt[i + 1];
			}
			
			if (index == (currentSize - 1)) {
				this.opt[index] = null;
			}
			
			// update the current actual size of the array
			currentSize--;
		}
		
	}

	public void removeOptionByName(String name) {
		Option o = getOptionByName(name);
		if (o == null) return;
		removeOption(o);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("OptionSet :: {");
		sb.append("name: ");
		sb.append(name);
		sb.append(", ");

		// printing all the options of the optionSet
		sb.append("options: [");
		for (int i = 0; i < opt.length; i++) {
			if (opt[i] != null) {
				sb.append(opt[i]);
				if (i < (opt.length - 1)) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");

		sb.append("}");
		return sb.toString();
	}

}

class Option implements Serializable {
	private static final long serialVersionUID = 1L;
	String name;
	float price;

	public Option(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Option :: {");
		sb.append("name: ");
		sb.append(name);
		sb.append(", ");
		sb.append("price: ");
		sb.append(price);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Option))
			return false;
		Option o = (Option) obj;

		if (!name.equals(o.name))
			return false;
		if (price != o.price)
			return false;

		return true;
	}
}