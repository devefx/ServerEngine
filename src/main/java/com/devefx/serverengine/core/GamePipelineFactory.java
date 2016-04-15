package com.devefx.serverengine.core;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;

import com.devefx.serverengine.codec.GameDecoder;
import com.devefx.serverengine.codec.GameEncoder;
import com.devefx.serverengine.handler.GameHandlerManager;

public class GamePipelineFactory implements ChannelPipelineFactory {

	private int maxFrameLength		= 1024;
	private int lengthFieldLength	= 2;
    private int timeOut				= 0;
    
    private GameHandlerManager handlerManager;
    
    public void setHandlerManager(GameHandlerManager handlerManager) {
		this.handlerManager = handlerManager;
	}
    /**
     * 
     * @param maxFrameLength
     */
    public void setMaxFrameLength(int maxFrameLength) {
		this.maxFrameLength = maxFrameLength;
	}
    /**
     * 
     * @param lengthFieldLength
     */
    public void setLengthFieldLength(int lengthFieldLength) {
		this.lengthFieldLength = lengthFieldLength;
	}
    /**
     * 
     * @param timeOut
     */
    public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
    /**
     * create a buffer
     * @param capacity
     * @return ChannelBuffer
     */
    public ChannelBuffer getBuffer(int capacity) {
		return ChannelBuffers.buffer(capacity + lengthFieldLength);
	}
    
    
	public ChannelPipeline getPipeline() throws Exception {
		if (timeOut > 0) {
			return Channels.pipeline(
					new ReadTimeoutHandler(new HashedWheelTimer(), timeOut),
					new GameDecoder(maxFrameLength, 0, lengthFieldLength, 0, lengthFieldLength),
					new GameEncoder(), new GameUpStreamer(handlerManager));
		}
		return Channels.pipeline(
				new GameDecoder(maxFrameLength, 0, lengthFieldLength, 0, lengthFieldLength),
				new GameEncoder(), new GameUpStreamer(handlerManager));
	}
}
