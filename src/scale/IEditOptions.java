/**
 * @author cvandera
 *
 */

package scale;

// Interface to expose methods for editing an Option through BuildAuto
public interface IEditOptions {

	public void setOptionName(String modelName, String optionSetName, String optionOldName, String optionNewName);
	public void setOptionPrice(String modelName, String optionSetName, String optionName, Float optionPrice);
	public void setOptionChoice(String modelName, String optionSetName, String choice);
}
