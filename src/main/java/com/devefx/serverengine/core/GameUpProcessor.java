package com.devefx.serverengine.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


import com.devefx.serverengine.event.GameEvent;
import com.devefx.serverengine.handler.GameHandler;
import com.devefx.serverengine.handler.GameHandlerManager;

public class GameUpProcessor implements Runnable {

	private Queue<GameEvent> msgQueue = new ConcurrentLinkedQueue<>();
	
	private GameHandlerManager handlerManager;
	
	public GameUpProcessor(GameHandlerManager handlerManager) {
		this.handlerManager = handlerManager;
	}
	
	public void push(GameEvent e) {
		synchronized (msgQueue) {
			msgQueue.add(e);
			msgQueue.notifyAll();
		}
	}
	
	public boolean isDone() {
		return msgQueue.isEmpty();
	}
	
	@Override
	public void run() {
		while (true) {
			synchronized (msgQueue) {
				if (msgQueue.isEmpty()) {
					try {
						msgQueue.wait();
					} catch (InterruptedException e) {
						
					}
				}
				GameEvent event = null;
				while ((event = msgQueue.poll()) != null) {
					try {
						GameHandler handler = handlerManager.getHandler(event.getType());
						if (handler != null) {
							handler.process(event);
						} else {
							
						}
					} catch (Exception e) {
						
					}
				}
			}
		}
	}
	
}
