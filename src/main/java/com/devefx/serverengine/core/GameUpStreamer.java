package com.devefx.serverengine.core;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.timeout.ReadTimeoutException;

import com.devefx.serverengine.buffer.GameBuffer;
import com.devefx.serverengine.channel.GameChannelFactory;
import com.devefx.serverengine.connection.Connection;
import com.devefx.serverengine.connection.ConnectionManager;
import com.devefx.serverengine.event.GameEvent;
import com.devefx.serverengine.handler.GameHandlerManager;

public class GameUpStreamer extends SimpleChannelUpstreamHandler {

	private GameUpProcessor processor;
	private ConnectionManager connectionManager;
	private GameChannelFactory channelFactory;
	
	public GameUpStreamer(GameHandlerManager handlerManager) {
		processor = new GameUpProcessor(handlerManager);
		new Thread(processor).start();
		
		connectionManager = new ConnectionManager();
		connectionManager.surveillance();
		
		channelFactory = new GameChannelFactory(connectionManager);
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		
		SocketAddress remoteAddress = e.getRemoteAddress();
		int connectionId = remoteAddress.hashCode();
		
		Connection connection = connectionManager.getConnection(connectionId);
		if (connection == null) {
			connection = connectionManager.newConnection(ctx, remoteAddress);
		} else {
			connection.active();
		}
		
		GameEvent event = new GameEvent(new GameBuffer((ChannelBuffer) e.getMessage()),
				channelFactory.build(connection));

		if (event.getType() < 1)
			return;
		
		processor.push(event);
		
		super.messageReceived(ctx, e);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		Throwable throwable = e.getCause();
		if (throwable instanceof ReadTimeoutException) {
			
		} else if (throwable instanceof TooLongFrameException) {
			ctx.getChannel().close();
		} else if (throwable instanceof ClosedChannelException) {
			
		} else if (throwable instanceof IOException) {
			ctx.getChannel().close();
		} else {
			super.exceptionCaught(ctx, e);
		}
	}
}
