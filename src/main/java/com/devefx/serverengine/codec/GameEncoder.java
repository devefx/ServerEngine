package com.devefx.serverengine.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 消息编码器
 * @author： youqian.yue
 * @date： 2016-4-13 上午11:43:05
 */
public class GameEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object object) throws Exception {
		return (ChannelBuffer) object;
	}
}
