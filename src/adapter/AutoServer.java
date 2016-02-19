/**
 * @author cvandera
 *
 */

package adapter;

import java.util.List;
import java.util.Properties;

import model.Automobile;

public interface AutoServer {

	public Automobile createAutomobileFromProperties(Properties properties);
	
	public List<String> getAvailableModels();
	
	public Automobile getAutomobile(String modelName);
	
	public void saveAutomobile(Automobile auto);
}
