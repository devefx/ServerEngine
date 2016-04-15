package com.devefx.serverengine.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

/**
 * 消息解码器
 * @author： youqian.yue
 * @date： 2016-4-13 上午11:41:58
 */
public class GameDecoder extends LengthFieldBasedFrameDecoder {

	/**
	 * 
	 * @param maxFrameLength 消息最大长度
	 * @param lengthFieldOffset length字段偏移量
	 * @param lengthFieldLength length字段的长度
	 * @param lengthAdjustment length字段调节量
	 * @param initialBytesToStrip 丢弃的字节长度
	 */
	public GameDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,
				initialBytesToStrip, true);
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		return (ChannelBuffer) super.decode(ctx, channel, buffer);
	}
}
