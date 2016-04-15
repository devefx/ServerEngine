package com.devefx.serverengine.connection;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

public interface Connection {
	
	int getId();
	
	ChannelFuture write(Object message);
	
	Channel getChannel();
	
	Object getAttachment();
	
	void setAttachment(Object attachment);
	
	boolean isActive();
	
	void active();
	
	boolean isClose();
	
	void close();
}
