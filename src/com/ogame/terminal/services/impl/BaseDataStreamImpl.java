package com.ogame.terminal.services.impl;

import java.io.InputStream;

import com.ogame.terminal.services.DataStream;


public class BaseDataStreamImpl implements DataStream {
	
	long wireBytes;
	long uncompressedBytes;
	InputStream stream;
	String charset;

	public BaseDataStreamImpl(long wireBytes, long uncompressedBytes,
			InputStream stream, String charset) {
		this.wireBytes = wireBytes;
		this.uncompressedBytes = uncompressedBytes;
		this.stream = stream;
		this.charset = charset;
	}
	
	public BaseDataStreamImpl() {
		this (-1, -1, null, null);
	}

	@Override
	public InputStream stream() {
		return this.stream;
	}

	@Override
	public long wireBytesCount() {
		return this.wireBytes;
	}

	@Override
	public long uncompressedBytesCount() {
		return this.uncompressedBytes;
	}

	@Override
	public String charset() {
		return this.charset;
	}

	public long getWireBytes() {
		return wireBytes;
	}

	public void setWireBytes(long wireBytes) {
		this.wireBytes = wireBytes;
	}

	public long getUncompressedBytes() {
		return uncompressedBytes;
	}

	public void setUncompressedBytes(long uncompressedBytes) {
		this.uncompressedBytes = uncompressedBytes;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}	
}
