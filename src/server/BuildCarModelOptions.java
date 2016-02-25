/**
 * @author cvandera
 *
 */

package server;

import java.util.List;
import java.util.Properties;

import adapter.AutoServer;
import adapter.BuildAuto;
import model.Automobile;

public class BuildCarModelOptions implements AutoServer{
	
	private BuildAuto ba;
	
	public BuildCarModelOptions(BuildAuto ba) {
		this.ba = ba;
	}

	@Override
	public Automobile createAutomobileFromProperties(Properties properties) {
		return ba.createAutomobileFromProperties(properties);
	}

	@Override
	public List<String> getAvailableModels() {
		return ba.getAvailableModels();
	}

	@Override
	public Automobile getAutomobile(String modelName) {
		return ba.getAutomobile(modelName);
	}

	@Override
	public void saveAutomobile(Automobile auto) {
		ba.saveAutomobile(auto);
	}
	
}
