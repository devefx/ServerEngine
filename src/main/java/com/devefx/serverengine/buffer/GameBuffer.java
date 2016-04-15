package com.devefx.serverengine.buffer;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;

public class GameBuffer {
	
	private final ChannelBuffer buffer;
	private Charset charset;
	
	public GameBuffer(ChannelBuffer buffer) {
		this(buffer, "UTF-8");
	}
	public GameBuffer(ChannelBuffer buffer, String charsetName) {
		this.buffer = buffer;
		this.charset = Charset.forName(charsetName);
	}
	public Charset getCharset() {
		return charset;
	}
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	public void setCharset(String charsetName) {
		this.charset = Charset.forName(charsetName);
	}
	public byte[] array() {
		return buffer.array();
	}
	public byte readByte() {
		return buffer.readByte();
	}
	public void readBytes(byte[] dst) {
		buffer.readBytes(dst);
	}
	public void readBytes(byte[] dst, int dstIndex, int length) {
		buffer.readBytes(dst, dstIndex, length);
	}
	public short readShort() {
		return buffer.readShort();
	}
	public int readInt() {
		return buffer.readInt();
	}
	public long readLong() {
		return buffer.readLong();
	}
	/**
	 * 
	 * +--------+--------------+
	 * | Length |   Content    |
	 * | 0x000C | HELLO, WORLD |
	 * +--------+--------------+
	 * @author：youqian.yue
	 * @return String
	 */
	public String readString() {
		int len = buffer.readShort();
		if (len <= 0 || len > 1024)
			throw new IllegalArgumentException("string length cannot less than zero or len is more than 1024, value is " + len);
		byte[] strData = new byte[len];
		if (buffer.readableBytes() < len)
			throw new IllegalArgumentException("readableBytes less than " + len);
		buffer.readBytes(strData);
		return new String(strData, charset);
	}
	public boolean readable() {
		return buffer.readable();
	}
	public int readableBytes() {
		return buffer.readableBytes();
	}
}
