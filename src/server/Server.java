/**
 * @author cvandera
 *
 */

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import adapter.BuildAuto;
import model.Automobile;
import server.helper.DefaultSocketClient;

public class Server {

	public Server() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(1);
		}

		BuildAuto ba = new BuildAuto();
		BuildCarModelOptions buildCarModel = new BuildCarModelOptions(ba);

		DefaultSocketClient clientSocket = null;
		try {
			Socket s = serverSocket.accept();
			clientSocket = new DefaultSocketClient(s) {

				@Override
				public void handleInput(Object obj) {
					if (obj instanceof String) {
						String strInput = (String) obj;

						if ("1".equals(strInput)) {
							// Option 1 selected: Upload properties file
							sendOutput("option1:Please enter the name of properties file:");
						} else if ("2".equals(strInput)) {
							// Option 2 selected: Configure an automobile
							// send the list of available automobiles
							sendOutput(buildCarModel.getAvailableModels());
						} else if (strInput.contains("modelName:")) {
							String modelName = strInput.replace("modelName:", "");
							Automobile auto = buildCarModel.getAutomobile(modelName);
							sendOutput(auto);
						}
					}

					if (obj instanceof Properties) {
						// the client sent a properties file
						Properties props = (Properties) obj;
						Automobile auto = buildCarModel.createAutomobileFromProperties(props);
						buildCarModel.saveAutomobile(auto);
						sendOutput("autoSaved");
					}

				}
			};

		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		clientSocket.start();
	}
}
