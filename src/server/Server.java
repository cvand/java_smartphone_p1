/**
 * @author cvandera
 *
 */

package server;

import java.io.IOException;
import java.net.ServerSocket;

import adapter.BuildAuto;
import client.DefaultSocketClient;

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
			clientSocket = new DefaultSocketClient(serverSocket.accept()) {
				@Override
				public void handleInput(String strInput) {
					// TODO Auto-generated method stub
					super.handleInput(strInput);
				}
			};
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
	}
}
