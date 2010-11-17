package com.ogame.terminal.services.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.htmlparser.lexer.Stream;


public class FileDataStreamImpl extends BaseDataStreamImpl {
	
	public FileDataStreamImpl () {
		super ();
	}

	public FileDataStreamImpl (String filename) {
		super();
		this.setFilename(filename);
	}
	
	public final void setFilename (String filename) {
		if (filename == null)
			throw new NullPointerException();
		
		try {
			final File f = new File (filename);
			
			long byteCount = f.length();
			byte[] contents = new byte [(int) byteCount];
			
			FileInputStream in = new FileInputStream(f);			
			readToContents(byteCount, contents, in);
			in.close();
			
			this.setCharset("utf-8");
			this.setStream(new Stream (new ByteArrayInputStream(contents)));
			this.setUncompressedBytes(byteCount);
			
			// No transfer on wire, just set this to -1
			this.setWireBytes(-1);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}

	private void readToContents(long byteCount, byte[] contents,
			FileInputStream in) throws IOException {
		long offset = 0;
		while (offset < byteCount) {
			offset += in.read(contents, (int) offset, (int) byteCount);
		}
	}	
}
