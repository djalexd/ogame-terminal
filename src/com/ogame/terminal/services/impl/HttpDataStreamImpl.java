package com.ogame.terminal.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.htmlparser.lexer.Stream;


public class HttpDataStreamImpl extends BaseDataStreamImpl {
	
	public HttpDataStreamImpl () {
		super ();
	}

	public HttpDataStreamImpl (HttpResponse response) {
		super ();
		this.setHttpResponse(response);
	}
	
	public final void setHttpResponse (HttpResponse response) {
		HttpEntity entity = response.getEntity();		
		if (entity == null)
			throw new IllegalStateException();

		Header encoding = response.getFirstHeader("Content-Encoding");
		if (encoding == null) {
			// Let's try to find another header, Transfer-Encoding
			encoding = response.getFirstHeader("Transfer-Encoding");
		}
		
		Header type = response.getFirstHeader("Content-Type");

		try {
			// We cannot determine in advance how we'll instantiate
			// buffer data.
			byte[] contents = null;
			
			if (entity.getContentLength() > 0) {
				// We'll store the contents into a byte[] array
				int length = (int) entity.getContentLength();
				contents = new byte [length];
			
				// Do the actual read
				readToContents(entity, length, contents);
				
				// This is the place to set 'wireBytes'
				this.setWireBytes(entity.getContentLength());
			} else {
				// This is for chunked encoding (or any other 
				// protocol that cannot send 'Content-Length'
				// in advance.
				contents = readChunkedToContents(entity);
			}

			// Determine stream type: gzip, deflate. chunked
			Stream s = instantiateStream(encoding, contents);

			// Determine charset
			String typeStr = determineCharset(type);
			
			// Set values here
			this.setStream(s);
			this.setCharset(typeStr);
			
			 // Unknown number of uncompressed bytes
			this.setUncompressedBytes(-1);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String determineCharset(Header type) {
		String typeStr = null;
		if (type.getValue().contains("charset")) {
			String charset = type.getValue();
			typeStr = charset.substring(charset.indexOf("charset") + "charset".length() + 1);
		}
		return typeStr;
	}

	private Stream instantiateStream(Header encoding, byte[] contents)
			throws IOException {
		Stream s = null;
		if (encoding == null)
			throw new IOException("No encoding specified");
		if (contents == null)
			throw new IOException("No contents specified");
		
		if (encoding.getValue().contains("gzip")) {
			s = new Stream (new GZIPInputStream(new ByteArrayInputStream(contents)));
		} else if (encoding.getValue().contains("deflate")) {
			s = new Stream (new InflaterInputStream(new ByteArrayInputStream(contents)));
		} else if (encoding.getValue().contains("chunked")) {
			s = new Stream (new ByteArrayInputStream(contents));
		} else {
			throw new RuntimeException("Cannot determine encoding");
		}
		return s;
	}

	private void readToContents(HttpEntity entity, int length, byte[] contents)
			throws IOException {
		InputStream in = entity.getContent();
		int offset = 0;
		while (offset < length) {
			offset += in.read(contents, offset, length);
		}
	}
	
	private byte[] readChunkedToContents (HttpEntity entity) 
			throws IOException {
		
		if (!entity.isChunked())
			throw new IllegalStateException("You should not use this method, the entity is not chunked");
		
		// Do not read more that 1Mb of data...
		int MAX_DATA_LEN = 1024 * 1024;
		int offset = 0;
		byte[] buffer = new byte [MAX_DATA_LEN];
		
		InputStream in = entity.getContent();
		int read;
		
		do {
			read = in.read(buffer, offset, MAX_DATA_LEN);
			offset += read;
			
			if (offset > MAX_DATA_LEN)
				throw new IOException("Cannot read more than " + MAX_DATA_LEN + " bytes. Increase this limit or use other encoding");
		} while (read != -1);
		
		byte[] contents = Arrays.copyOfRange(buffer, 0, offset);
		buffer = null;
		
		return contents;
	}

	@Override
	public String toString() {
		return "HttpDataStreamImpl [wireBytes=" + wireBytes
				+ ", uncompressedBytes=" + uncompressedBytes + ", stream="
				+ stream + ", charset=" + charset + "]";
	}
}
