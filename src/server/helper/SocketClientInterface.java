/**
 * @author cvandera
 *
 */

package server.helper;

public interface SocketClientInterface {
	boolean openConnection();

	void handleSession();

	void closeSession();

}
