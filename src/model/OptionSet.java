/**
 * @author cvandera
 *
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

public class OptionSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Option> opt = new ArrayList<Option>();
	
	private Option optionChoice;

	protected OptionSet(String n) {
		name = n;
	}
	

	public Option getOptionChoice() {
		return optionChoice;
	}

	public void setOptionChoice(String option) {
		for (Option o : opt) {
			if (o.getName().equals(option)) {
				this.optionChoice = o;
			}
		}
	}

	protected Option getOptionByName(String name) {
		// look in the opt for an object with name = name
		for (int i = 0; i < opt.size(); i++) {
			Option option = this.opt.get(i);
			if (option.getName().equals(name))
				return option;
		}
		return null;
	}

	private int getOptionIndex(Option opt) {
		// Find the Option opt in the array
		int index = -1;
		for (int i = 0; i < this.opt.size(); i++) {
			Option o = this.opt.get(i);
			if (o == opt) {
				index = i;
				break;
			}
		}
		return index;
	}

	protected ArrayList<Option> getOptions() {
		return opt;
	}

	protected void setOptions(ArrayList<Option> opt) {
		this.opt = new ArrayList<Option>();
		// copy every element to the option list
		for (int i = 0; i < opt.size(); i++) {
			this.opt.add(opt.get(i));
		}
	}

	protected void updateOption(Option opt, String newName, float newPrice) {
		// find the index of the option in the set and update the values
		int index = getOptionIndex(opt);
		if (index > -1) {
			this.opt.get(index).setName(newName);
			this.opt.get(index).setPrice(newPrice);
		}
	}

	protected void updateOption(String oldName, String newName, float newPrice) {
		// find the option in the set and update the values
		Option opt = getOptionByName(oldName);
		updateOption(opt, newName, newPrice);
	}

	protected void addOption(String name, float price) {
		addOption(new Option(name, price));
	}

	protected void addOption(Option opt) {
		// add the object
		this.opt.add(opt);
	}

	protected void removeOption(Option opt) {
		int index = getOptionIndex(opt);

		if (index > -1) {
			this.opt.remove(index);
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
		for (int i = 0; i < opt.size(); i++) {
			sb.append(opt.get(i));
			if (i < (opt.size() - 1)) {
				sb.append(", ");
			}
		}
		sb.append("]");
		sb.append(", ");
		sb.append("optionChoice: ");
		sb.append(optionChoice);
		
		
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