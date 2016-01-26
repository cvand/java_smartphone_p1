/**
 * @author cvandera
 *
 */

package model;

import java.io.Serializable;

public class OptionSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private Option opt[];
	// currentSize holds the actual size of the opt array (the number of
	// elements in the array that are not null)
	private int currentSize = 0;

	protected OptionSet(String n, int size) {
		opt = new Option[size];
		name = n;
	}

	protected Option getOptionByName(String name) {
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

	protected Option[] getOptions() {
		return opt;
	}

	protected void setOptions(Option[] opt) {
		this.opt = opt;
		// calculate the actual size of the array
		for (int i = 0; i < opt.length; i++) {
			if (opt[i] != null)
				currentSize++;
			else
				break;
		}
	}

	protected void updateOption(Option opt, String newName, float newPrice) {
		// find the index of the option in the set and update the values
		int index = getOptionIndex(opt);
		if (index > -1) {
			this.opt[index].setName(newName);
			this.opt[index].setPrice(newPrice);
		}
	}

	protected void updateOption(String oldName, String newName, float newPrice) {
		// find the option in the set and update the values
		Option opt = getOptionByName(oldName);
		updateOption(opt, newName, newPrice);
	}
	

	protected void addOption(String name, float price) throws Exception {
		addOption(new Option(name, price));
	}

	protected void addOption(Option opt) throws Exception {
		// if the array has room for more objects
		if (currentSize < this.opt.length) {
			// add the object after the last non-null object
			this.opt[currentSize] = opt;
			currentSize++;
		} else {
			throw new Exception("You are trying to add elements to a full array.");
		}
	}

	protected void removeOption(Option opt) {
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

	protected void removeOptionByName(String name) {
		Option o = getOptionByName(name);
		if (o == null)
			return;
		removeOption(o);
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
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

	class Option implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		private float price;

		protected Option(String name, float price) {
			this.name = name;
			this.price = price;
		}

		protected String getName() {
			return name;
		}

		protected void setName(String name) {
			this.name = name;
		}

		protected float getPrice() {
			return price;
		}

		protected void setPrice(float price) {
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

			if (!getName().equals(o.getName()))
				return false;
			if (getPrice() != o.getPrice())
				return false;

			return true;
		}
	}

}