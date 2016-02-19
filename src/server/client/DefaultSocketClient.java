/**
 * @author cvandera
 *
 */

package server.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {

	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket sock;
	private String strHost;
	private int iPort;

	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}// constructor

	public DefaultSocketClient(Socket socket) {
		sock = socket;
	}

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}// run

	public boolean openConnection() {

		try {
			if (sock == null && strHost != null && iPort > 0) {
				sock = new Socket(strHost, iPort);
			}
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + strHost);
			return false;
		}
		try {
			writer = new ObjectOutputStream(sock.getOutputStream());
			reader = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream to/from " + strHost);
			return false;
		}
		return true;
	}

	public void handleSession() {
		Object obj = null;
		while ((obj = deserializeObject()) != null) {
			handleInput(obj);
			
			if ((obj instanceof String) && ((String) obj).equals("Bye")) {
				break;
			}
		}
	}

	public void sendOutput(Object object) {
		try {
			writer.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object deserializeObject() {
		try {
			return reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void handleInput(Object input) {
		System.out.println(input);
	}

	public void closeSession() {
		try {
			writer.close();
			reader.close();
			writer = null;
			reader = null;
			sock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}
	}

	public void setHost(String strHost) {
		this.strHost = strHost;
	}

	public void setPort(int iPort) {
		this.iPort = iPort;
	}

	public static void main(String arg[]) {
		/* debug main; does daytime on local host */
		String strLocalHost = "";
		try {
			strLocalHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			System.err.println("Unable to find local host");
		}
		DefaultSocketClient d = new DefaultSocketClient(strLocalHost, iDAYTIME_PORT);
		d.start();
	}

}// class DefaultSocketClient
