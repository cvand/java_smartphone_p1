/**
 * @author cvandera
 *
 */

package adapter;

import model.Automobile;
import util.FileIO;

public abstract class ProxyAutomobile {

	private Automobile auto;
	
	public void updateOptionSetName(String modelName, String optionSetName, String newName) {
		// make sure that the modelName matches the name of the automobile
		if (auto.getName().equals(modelName)) {
			// rename the optionSet
			auto.updateOptionSet(optionSetName, newName);
		}
	}
	
	public void updateOptionPrice(String modelName, String optionSetName, String option, float newPrice) {
		// make sure that the modelName matches the name of the automobile
		if (auto.getName().equals(modelName)) {
			try {
				auto.updateOptionInSet(optionSetName, option, option, newPrice);
			} catch (Exception e) {
				// TODO Custom exception handling
				e.printStackTrace();
			}
		}
	}
	
	public void buildAuto(String filename) {
		FileIO fileIO = new FileIO();
		//Build Automobile Object from a file.
		auto = fileIO.buildAutomobileObject(filename);
	}

	public void printAuto(String modelName) {
		if (auto.getName().equals(modelName)) {
			System.out.println(auto);
		}
	}
}
