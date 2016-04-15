package com.devefx.serverengine.channel;

import com.devefx.serverengine.connection.Connection;
import com.devefx.serverengine.connection.ConnectionManager;

public class GameChannelFactory {
	
	private ConnectionManager connectionManager;
	
	public GameChannelFactory(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}
	
	public GameChannel build(Connection remoteConnection) {
		return new GameChannel(remoteConnection, connectionManager);
	}
}
