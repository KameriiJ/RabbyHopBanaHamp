package serverAndClient;

import java.io.IOException;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {
	
	private int playerNumber;
	private int clientNumber;
	private int rank;
	
	public Server(int port, int playerNumber) {
		super(port);
		this.playerNumber = playerNumber;
		this.clientNumber = playerNumber;
		this.rank = playerNumber;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if(msg.toString().equals("end")) {
			try {
				client.sendToClient(String.format("%d", this.rank));
				rank--;
			} catch (IOException e) {}
		}
	}
	
	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		playerNumber -= 1;
		if(playerNumber <= 0) {
			sendToAllClients(String.format("ready"));
		}
	}

	@Override
	protected void serverStarted() {
		super.serverStarted();
	}
	
	public void resetRank() {
		this.rank = clientNumber;
	}
}
