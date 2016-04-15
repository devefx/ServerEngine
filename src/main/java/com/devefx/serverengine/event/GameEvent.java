package com.devefx.serverengine.event;


import com.devefx.serverengine.buffer.GameBuffer;
import com.devefx.serverengine.channel.GameChannel;

public class GameEvent {

	private short type;
	private GameChannel channel;
	private GameBuffer buffer;
	
	public GameEvent(GameBuffer buffer, GameChannel channel) {
		this.buffer = buffer;
		this.channel = channel;
		type = buffer.readShort();
	}
	public short getType() {
		return type;
	}
	public GameChannel getChannel() {
		return channel;
	}
	public GameBuffer getMessage() {
		return buffer;
	}
}
