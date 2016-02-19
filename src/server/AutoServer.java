/**
 * @author cvandera
 *
 */

package server;

import java.util.List;
import java.util.Properties;

import model.Automobile;

public interface AutoServer {

	public Automobile createAutomobileFromProperties(Properties properties);
	
	public List<String> getAvailableModels();
	
	public String getSerializedAutomobile(String modelName);
	
	public void saveAutomobile(Automobile auto);
}
