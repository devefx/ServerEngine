package com.devefx.serverengine.handler;

/**
 * 消息处理器管理中心
 * @author： youqian.yue
 */
public class GameHandlerManager {
	
	private GameHandler[] handlers;
	
	public void setInitCapacity(int capacity) {
		if (capacity < 0) {
			new IllegalArgumentException("capacity cannot less than zero");
		}
		handlers = new GameHandler[capacity];
	}
	
	public void register(short id, GameHandler handler) throws Exception {
		assert (id > 0 && id < handlers.length);
		if (handlers[id] != null)
			throw new Exception("Handler " + id + " has been existed! please check your code!");
		handlers[id] = handler;
	}
	
	public boolean replace(short id, GameHandler handler) {
		assert (id > 0 && id < handlers.length);
		if (handlers[id] != null) {
			handlers[id] = handler;
			return true;
		}
		return false;
	}
	
	public boolean unregister(int id) {
		GameHandler handler = handlers[id];
		if (handler != null) {
			handlers[id] = null;
			return true;
		}
		return false;
	}
	
	public GameHandler getHandler(int id) {
		if (id > 0 && id < handlers.length)
			return handlers[id];
		return null;
	}
}