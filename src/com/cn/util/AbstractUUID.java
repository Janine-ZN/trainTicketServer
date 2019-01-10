package com.cn.util;

import java.net.InetAddress;

/** 
 * The base class for ID generators that use a UUID algorithm. This
 * class implements the algorithm, subclasses define the ID format.
 * 
 * @see UUIDHexUtil
 * @see UUIDStringGenerator
 */
public abstract class AbstractUUID {

	private static final int ip;
	static {
		int ipadd;
		try {
			ipadd = GenerateUtil.toInt( InetAddress.getLocalHost().getAddress() );
		}
		catch (Exception e) {
			ipadd = 0;
		}
		ip = ipadd;
	}
	private static short counter = (short) 0;
	private static final int jvm = (int) ( System.currentTimeMillis() >>> 8 );

	/** 
	 * Unique across JVMs on this machine (unless they load this class
	 * in the same quater second - very unlikely)
	 */
	protected int getJVM() {
		return jvm;
	}

	/** 
	 * Unique in a millisecond for this JVM instance (unless there
	 * are > Short.MAX_VALUE instances created in a millisecond)
	 */
	protected short getCount() {
		synchronized(AbstractUUID.class) {
			if (counter < 0) counter = 0;
			return counter++;
		}
	}

	/** 
	 * Unique in a local network
	 */
	protected int getIP() {
		return ip;
	}

	/** 
	 * Unique down to millisecond
	 */
	protected short getHiTime() {
		return (short) ( System.currentTimeMillis() >>> 32 );
	}
	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}
}