package com.devefx.serverengine.connection;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.devefx.serverengine.connection.impl.ConnectionImpl;

public class ConnectionManager {
	
	private Map<Integer, Connection> connectionMap = new ConcurrentHashMap<>();
	
	public Connection getConnection(int connectionId) {
		return connectionMap.get(connectionId);
	}
	
	public void killConnection(int connectionId) {
		connectionMap.remove(connectionId);
	}
	
	public Collection<Connection> getConnections() {
		return connectionMap.values();
	}
	
	public Connection newConnection(ChannelHandlerContext ctx, SocketAddress address) {
		Connection connection = new ConnectionImpl(ctx, address);
		connectionMap.put(connection.getId(), connection);
		return connection;
	}
	
	public void surveillance() {
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Iterator<Integer> it = connectionMap.keySet().iterator();
				while (it.hasNext()) {
					Connection conn = connectionMap.get(it.next());
					if (!conn.isActive()) {
						it.remove();
					}
				}
			}
		}, 0, 5000);
	}
}
