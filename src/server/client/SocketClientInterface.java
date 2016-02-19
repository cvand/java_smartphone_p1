/**
 * @author cvandera
 *
 */

package server.client;

public interface SocketClientInterface {
	boolean openConnection();

	void handleSession();

	void closeSession();

}
