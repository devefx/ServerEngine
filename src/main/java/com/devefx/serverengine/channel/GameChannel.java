package com.devefx.serverengine.channel;

import org.jboss.netty.buffer.ChannelBuffers;

import com.devefx.serverengine.connection.Connection;
import com.devefx.serverengine.connection.ConnectionFilter;
import com.devefx.serverengine.connection.ConnectionManager;

public class GameChannel {

	private final Connection remoteConnection;
	private final ConnectionManager manager;
	
	protected GameChannel(Connection remoteConnection, ConnectionManager manager) {
		this.remoteConnection = remoteConnection;
		this.manager = manager;
	}
	public int getId() {
		return remoteConnection.getId();
	}
	public void write(byte[] bytes) {
		remoteConnection.write(ChannelBuffers.wrappedBuffer(bytes));
	}
	public void writeAll(byte[] bytes, ConnectionFilter filter) {
		for (Connection conn : manager.getConnections()) {
			if (filter.accept(conn)) {
				conn.write(ChannelBuffers.wrappedBuffer(bytes));
			}
		}
	}
}
