package cn.cellcom.common.DB;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import cn.cellcom.common.data.DataTool;

public class MemoryTool {
	private static MemoryMXBean aMemoryMXBean = ManagementFactory
			.getMemoryMXBean();

	public static String getUsedString() {
		MemoryUsage mu = aMemoryMXBean.getHeapMemoryUsage();
		return DataTool.getStringSize(mu.getUsed());
	}

	public static String getJvmFree() {
		return DataTool.getStringSize(Runtime.getRuntime().freeMemory());
	}

	public static String getMemString() {
		return "[mem]used=" + getUsedString() + ",free=" + getJvmFree()
				+ ",threadCount=" + Thread.activeCount();
	}

	public static void main(String[] args) {
		System.out.println(getMemString());
	}
}
