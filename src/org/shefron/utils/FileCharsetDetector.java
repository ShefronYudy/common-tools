package org.shefron.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Arrays;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

public final class FileCharsetDetector {

	/**
	 * 
	 * @param filePath
	 *            the local filepath
	 * @param langFlag
	 *            nsPSMDetector.CHINESE, nsPSMDetector.SIMPLIFIED_CHINESE,
	 *            nsPSMDetector.TRADITIONAL_CHINESE, nsPSMDetector.ALL ...
	 * @return
	 * @throws Exception
	 */
	public final static CharsetObj getPriorityCharset(String filePath,
			int langFlag) throws Exception {
		if (langFlag < 1 || langFlag > 6) {
			langFlag = nsPSMDetector.ALL;
		}

		final CharsetObj charsetObj = new CharsetObj();

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);

			nsDetector det = new nsDetector(langFlag);
			// Set an observer...
			// The Notify() will be called when a matching charset is found.

			det.Init(new nsICharsetDetectionObserver() {
				public void Notify(String charset) {
					charsetObj.setFound(true);
					charsetObj.setCharset(charset);
				}
			});

			BufferedInputStream imp = new BufferedInputStream(fis);

			byte[] buf = new byte[1024];
			int len;
			boolean done = false;
			boolean isAscii = true;

			while ((len = imp.read(buf, 0, buf.length)) != -1) {

				// Check if the stream is only ascii.
				if (isAscii)
					isAscii = det.isAscii(buf, len);

				// DoIt if non-ascii and not done yet.
				if (!isAscii && !done)
					done = det.DoIt(buf, len, false);
			}
			det.DataEnd();

			if (isAscii) {
				charsetObj.setAscii(true);
			}

			charsetObj.setProbableCharsets(det.getProbableCharsets());

		} catch (Exception e) {
			System.out.println("File handler Error!");
			throw new Exception("File handler Error:" + e.getMessage());
		}

		return charsetObj;

	}

	/**
	 * 
	 * @param filePath
	 *            the local filepath
	 * @return
	 * @throws Exception
	 */
	public final static CharsetObj getPriorityCharset(String filePath)
			throws Exception {
		return getPriorityCharset(filePath, nsPSMDetector.ALL);

	}

	/**
	 * 
	 * @param url
	 *            the www url
	 * @return
	 * @throws Exception
	 */
	public final static CharsetObj getPriorityCharset(URL url) throws Exception {
		return getPriorityCharset(url, nsPSMDetector.ALL);

	}

	/**
	 * 
	 * @param url
	 *            the www url
	 * @param langFlag
	 * @return
	 * @throws Exception
	 */
	public static CharsetObj getPriorityCharset(URL url, int langFlag)
			throws Exception {
		if (langFlag < 1 || langFlag > 6) {
			langFlag = nsPSMDetector.ALL;
		}

		final CharsetObj charsetObj = new CharsetObj();

		try {
			nsDetector det = new nsDetector(langFlag);
			// Set an observer...
			// The Notify() will be called when a matching charset is found.

			det.Init(new nsICharsetDetectionObserver() {
				public void Notify(String charset) {
					charsetObj.setFound(true);
					charsetObj.setCharset(charset);
				}
			});

			BufferedInputStream imp = new BufferedInputStream(url.openStream());

			byte[] buf = new byte[1024];
			int len;
			boolean done = false;
			boolean isAscii = true;

			while ((len = imp.read(buf, 0, buf.length)) != -1) {

				// Check if the stream is only ascii.
				if (isAscii)
					isAscii = det.isAscii(buf, len);

				// DoIt if non-ascii and not done yet.
				if (!isAscii && !done)
					done = det.DoIt(buf, len, false);
			}
			det.DataEnd();

			if (isAscii) {
				charsetObj.setAscii(true);
			}

			charsetObj.setProbableCharsets(det.getProbableCharsets());

		} catch (Exception e) {
			System.out.println("File handler Error!");
			throw new Exception("File handler Error:" + e.getMessage());
		}

		return charsetObj;
	}

	public final static class CharsetObj {
		private boolean found = false;
		private String[] probableCharsets = null;
		private String charset = null;

		public String getCharset() {
			return charset;
		}

		@Override
		public String toString() {
			return "CharsetObj [charset=" + this.getCharset() + ", found="
					+ this.isFound() + ", isAscii=" + this.isAscii()
					+ ", probableCharsets="
					+ Arrays.toString(this.getProbableCharsets()) + "]";
		}

		public void setCharset(String charset) {
			this.charset = charset;
		}

		private boolean isAscii = true;

		public boolean isFound() {
			return found;
		}

		public String[] getProbableCharsets() {
			return probableCharsets;
		}

		public boolean isAscii() {
			return isAscii;
		}

		public void setFound(boolean found) {
			this.found = found;
		}

		public void setProbableCharsets(String[] probableCharsets) {
			this.probableCharsets = probableCharsets;
		}

		public void setAscii(boolean isAscii) {
			this.isAscii = isAscii;
		}

	}

}
