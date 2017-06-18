package com.gf.ims.common.db.jdbc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
public class MemoryTool {
	private static MemoryMXBean aMemoryMXBean = ManagementFactory
			.getMemoryMXBean();

	public static String getUsedString() {
		MemoryUsage mu = aMemoryMXBean.getHeapMemoryUsage();
		return getStringSize(mu.getUsed());
	}

	public static String getJvmFree() {
		return getStringSize(Runtime.getRuntime().freeMemory());
	}

	public static String getMemString() {
		return "[mem]used=" + getUsedString() + ",free=" + getJvmFree()
				+ ",threadCount=" + Thread.activeCount();
	}

	private static String getStringSize(long size) {
		if (size == 0L)
			return "0";
		long b = size % 1024L;
		long k = size % 1048576L / 1024L;
		long m = size % 1073741824L / 1048576L;
		StringBuffer sb = new StringBuffer();
		if (m > 0L)
			sb.append(m).append("M");
		if (k > 0L)
			sb.append(k).append("K");
		if (b > 0L)
			sb.append(b).append("B");
		return sb.toString();
	}

	
	public static void main(String[] args) {
		System.out.println(getMemString());
	}
}
