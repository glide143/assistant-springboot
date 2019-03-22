package com.mel.assistant.util;

import java.util.Random;

public class MyUtils {
	private MyUtils() {

	}

	public static int genRanNumByListSize(int size) {
		Random random = new Random();
		return random.nextInt(size);
	}
}
