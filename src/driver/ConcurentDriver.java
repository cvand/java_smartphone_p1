/**
 * @author cvandera
 *
 */

package driver;

import adapter.BuildAuto;
import exception.AutoException;
import scale.EditOptions;

public class ConcurentDriver {
	
	private static String customFileType = "custom";
	
	public static void main(String[] args) {
		// Input for testing
		String filename = "input.txt";
		String modelName = "Focus Wagon ZTW";
		
		BuildAuto ba = new BuildAuto();
		// build the automobile
		ba.buildAuto(filename, customFileType);
		
		EditOptions eo1 = new EditOptions(ba) {
			@Override
			public void run() {
				// override run() method to call specific functions for editing options
				super.run();
				
				try {
					System.out.println("EO1 - Option before changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatic"));
					this.setOptionName(modelName, "Transmission", "Automatic", "Automatics");
					System.out.println("EO1 - Option AFTER changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatics"));
					System.out.println("--------");
				} catch (AutoException e) {
					e.printStackTrace();
				}
				
			}
		};
		
		EditOptions eo2 = new EditOptions(ba) {
			@Override
			public void run() {
				// override run() method to call specific functions for editing options
				super.run();
				
				try {
					System.out.println("EO2 - Option before changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatic"));
					this.setOptionName(modelName, "Transmission", "Automatic", "Automatics2");
					System.out.println("EO2 - Option AFTER changing: " + ba.findAutomobile(modelName).getOptionInSet("Transmission", "Automatics2"));
					System.out.println("--------");
				} catch (AutoException e) {
					e.printStackTrace();
				}
			}
		};
		
		eo2.run();
		eo1.run();
		
	}

}
