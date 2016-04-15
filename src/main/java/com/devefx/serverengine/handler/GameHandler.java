package com.devefx.serverengine.handler;

import com.devefx.serverengine.event.GameEvent;

/**
 * 消息处理器接口
 * @author： youqian.yue
 */
public interface GameHandler {
	
	short getId();
	
	public abstract void process(GameEvent e);
}