/**
 * @author cvandera
 *
 */

package client;

public interface SocketClientInterface {
	boolean openConnection();

	void handleSession();

	void closeSession();

}
