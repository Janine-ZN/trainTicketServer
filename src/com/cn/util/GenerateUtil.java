package com.cn.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GenerateUtil {

    /** Number of bytes in a kilobyte. */
	public static final long KILOBYTE = 1024;

	/** Number of bytes in a megabyte. */
	public static final long MEGABYTE = 1024 * 1024;

	/** Number of bytes in a gigabyte. */
	public static final long GIGABYTE = 1024 * 1024 * 1024;

	/** Number of bytes in a terabyte. */
	public static final long TERABYTE = 1024 * 1024 * 1024;

	public static final byte[] getBytes(URL imageUrl) throws IOException {
		InputStream is = imageUrl.openStream();
		byte[] b = GenerateUtil.loadBytes(is);
		is.close();
		return b;
	}

	/**
	 * reads all bytes from the given stream
	 * 
	 * @param is the stream to read from
	 */
	public static final byte[] loadBytes(InputStream is) throws IOException {
		int count = 0;
		byte[] buffer = new byte[0];
		byte[] chunk = new byte[4096];
		while ((count = is.read(chunk)) >= 0) {
			byte[] t = new byte[buffer.length + count];
			System.arraycopy(buffer, 0, t, 0, buffer.length);
			System.arraycopy(chunk, 0, t, buffer.length, count);
			buffer = t;
		}
		return buffer;
	}

	static public final String stripToSafeDatabaseString(String s) {
		if (s == null)
			return null;
		s = s.replace('\n', ' ');
		s = s.replace('|', ' ');
		return s;
	}

	static public final void download(String url, String newLocal)
			throws MalformedURLException, FileNotFoundException, IOException {
		download(new URL(url), newLocal);
	}

	static public final void download(URL url, String newLocal)
			throws FileNotFoundException, IOException {
		InputStream in = url.openStream();
		byte[] buffer = GenerateUtil.loadBytes(in);
		OutputStream out = new FileOutputStream(newLocal);
		out.write(buffer);
		out.flush();
		in.close();
		out.close();

	}

	/**
	 * Returns information about free, used and total memory.
	 * 
	 * @return
	 */
	public static String memory() {
		StringBuffer retorno = new StringBuffer();
		retorno.append("\nFree Memory: ");
		retorno.append(GenerateUtil.getFreeMemory());
		retorno.append("\nUsed Memory: ");
		retorno.append(GenerateUtil.getUsedMemory());
		retorno.append("\nTotal Memory: ");
		retorno.append(GenerateUtil.getTotalMemory());
		return retorno.toString();
	}

	static public String getFreeMemory() {
		return GenerateUtil.getUserFriendlySize(Runtime.getRuntime().freeMemory());
	}

	static public String getUsedMemory() {
		return GenerateUtil.getUserFriendlySize(Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory());
	}

	static public String getTotalMemory() {
		return GenerateUtil.getUserFriendlySize(Runtime.getRuntime().totalMemory());
	}

	/**
	 * Returns the file (or directory) size user-friendly formatted
	 * 
	 * @param size
	 * @return
	 */
	static public String getUserFriendlySize(long size) {
		String retorno = null;
		if ((size / GenerateUtil.KILOBYTE) >= 1) {
			if ((size / GenerateUtil.MEGABYTE) >= 1) {
				if ((size / GenerateUtil.TERABYTE) >= 1)
					retorno = (size / GenerateUtil.TERABYTE) + " Tb";
				else if ((size / GenerateUtil.GIGABYTE) >= 1)
					retorno = (size / GenerateUtil.GIGABYTE) + " Gb";
				else
					retorno = (size / GenerateUtil.MEGABYTE) + " Mb";
			} else {
				retorno = (size / GenerateUtil.KILOBYTE) + " Kb";
			}
		} else {
			retorno = size + " bytes";
		}
		return retorno;
	}

	static public void delete(File file) {
		while (!file.delete()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Convert from a URL to a File.
	 * 
	 * @param url
	 *            File URL.
	 * @return The equivalent File object, or null if the URL's protocol is not
	 *         file
	 */
	public static File toFile(URL url) {
		if ("file".equals(url.getProtocol()) == false) {
			return null;
		} else {
			String filename = url.getFile().replace('/', File.separatorChar);
			return new File(filename);
		}
	}

	/**
	 * Converte uma data para uma string no formato desejado. Formatos
	 * suportados: dd/MM/yyyy, dd/MM/yyyy HH:mm, MM/dd/yyyy HH:mm:ss, MM/dd/yyyy
	 * HH:mm, yyyyMMddHHmmss, yyyyMMdd, yyyy/MM/dd HH:mm, yyyy-MM-dd, HH:mm,
	 * MM/dd/yyyy HH:mm:ss
	 * 
	 * @param d
	 *            Data a ser convertida
	 * @param f
	 *            Formato da string de retorno
	 * @return String convertida
	 */
	public static String dateToString(Date d, String f) {
		String strDate = "";
		if (d != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(f);
			strDate = formatter.format(d);
		}
		return strDate;
	}

	static public String trim(String s, String c) {
		while (s.startsWith(c)) s = s.substring(1);
		while (s.endsWith(c)) s = s.substring(0, s.length() - 1);
		return s;
	}

	public static int toInt( byte[] bytes ) {
		int result = 0;
		for (int i=0; i<4; i++) {
			result = ( result << 8 ) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}
	
	public static byte[] toBytes(int value) {
		byte[] result = new byte[4];
		for (int i=3; i>=0; i--) {
			result[i] = (byte) ( ( 0xFFl & value ) + Byte.MIN_VALUE );
			value >>>= 8;
		}
		return result;
	}

	public static byte[] toBytes(short value) {
		byte[] result = new byte[2];
		for (int i=1; i>=0; i--) {
			result[i] = (byte) ( ( 0xFFl & value )  + Byte.MIN_VALUE );
			value >>>= 8;
		}
		return result;
	}
	
	static public byte[] serialize(Serializable s) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(s);
        if (out != null) out.close();
        return baos.toByteArray();
	}
	
	static public Object deserialize(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream in = new ObjectInputStream(bais);
        Object r = null;
        try {
        	r = in.readObject();
        } catch (ClassNotFoundException e) {
        	throw new IOException(e.getMessage());
        } finally {
            if (in != null) in.close();
        }
        return r;
	}

}