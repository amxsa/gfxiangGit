package cn.cellcom.common;

import java.util.Date;

import cn.cellcom.common.date.DateTool;

public class SequenceGenerator {

	private static int minNumber = 1000;

	private static int maxNumber = 9999;

	private static int currentNumber = minNumber;

	public static String generate() {

		return generateSuffix().toString();
	}

	private static StringBuffer generateSuffix() {
		StringBuffer buffer = new StringBuffer().append(
				DateTool.formateTime2String(new Date(), "yyyyMMddHHmmss")).append(
				String.valueOf(generateNumber()));

		return buffer;
	}

	private static synchronized int generateNumber() {
		if (currentNumber >= maxNumber) {
			currentNumber = minNumber;
		} else {
			currentNumber++;
		}

		return currentNumber;
	}
}
