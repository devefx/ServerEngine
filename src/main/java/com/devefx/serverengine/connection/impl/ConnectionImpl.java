package com.devefx.serverengine.connection.impl;

import java.net.SocketAddress;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.devefx.serverengine.connection.Connection;

public class ConnectionImpl implements Connection {
	
	private int						id;
	private long 					lastActiveTime;
	private boolean					isClose;
	private Object					attachment;
	private ChannelHandlerContext	ctx;
	private SocketAddress			remoteAddress;

	public ConnectionImpl(ChannelHandlerContext ctx, SocketAddress remoteAddress) {
		this.ctx = ctx;
		this.remoteAddress = remoteAddress;
		id = remoteAddress.hashCode();
		lastActiveTime = System.currentTimeMillis();
		isClose = false;
	}

	@Override
	public int getId() {
		return id;
	}
	@Override
	public ChannelFuture write(Object message) {
		return getChannel().write(message, remoteAddress);
	}
	@Override
	public Channel getChannel() {
		return ctx.getChannel();
	}
	@Override
	public boolean isActive() {
		return System.currentTimeMillis() - lastActiveTime < 5000;
	}
	@Override
	public void active() {
		lastActiveTime = System.currentTimeMillis();
	}
	@Override
	public boolean isClose() {
		return isClose;
	}
	@Override
	public void close() {
		isClose = true;
	}
	@Override
	public Object getAttachment() {
		return attachment;
	}
	@Override
	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}
}
