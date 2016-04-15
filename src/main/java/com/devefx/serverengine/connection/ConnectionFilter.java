package com.devefx.serverengine.connection;

public interface ConnectionFilter {
	
	public boolean accept(Connection connection);
}
