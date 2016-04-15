package com.devefx.serverengine;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.devefx.serverengine.handler.GameHandler;
import com.devefx.serverengine.handler.GameHandlerManager;

public class EchoServer {
	
	private String					host;
	private int						port;
	private ChannelPipelineFactory	pipelineFactory;
	
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setPipelineFactory(ChannelPipelineFactory pipelineFactory) {
		this.pipelineFactory = pipelineFactory;
	}
	public void run() {
		ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(
				new NioDatagramChannelFactory(Executors.newCachedThreadPool()));
		
		bootstrap.setPipelineFactory(pipelineFactory);
		
		SocketAddress socketAddress = host != null ? new InetSocketAddress(host, port) : 
			new InetSocketAddress(port);
		 
		Channel channel = bootstrap.bind(socketAddress);
		
		System.out.println("Server已经启动，监听端口: " + channel.getLocalAddress() + "， 等待客户端连接...");
	}
	
	public static void main(String[] args) throws Exception {
		// initialize applicationContext
		ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
		// get handler manager
		GameHandlerManager handlerManager = (GameHandlerManager) context.getBean("handlerManager");
		// register handler
		String[] handlerNames = context.getBeanNamesForType(GameHandler.class);
		for (String handlerName : handlerNames) {
			GameHandler handler = (GameHandler) context.getBean(handlerName);
			handlerManager.register(handler.getId(), handler);
		}
		// run server
		((EchoServer) context.getBean("echoServer")).run();
	}
}
