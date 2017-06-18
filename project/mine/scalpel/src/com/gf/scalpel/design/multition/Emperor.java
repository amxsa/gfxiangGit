package com.gf.scalpel.design.multition;

import java.util.ArrayList;

public class Emperor {
	
	private static int maxNumberOfEmperor=2;
	
	private static ArrayList emperorInfoList=new ArrayList(maxNumberOfEmperor);
	
	private static ArrayList emperorList=new ArrayList(maxNumberOfEmperor);
	
	private static int countNumberOfEmperor=2;
	
	static{
		for (int i = 0; i < maxNumberOfEmperor; i++) {
			emperorList.add(new Emperor("»ÊµÛ"+i));
		}
	}

	public Emperor() {
		
	}
	
	public Emperor(String info) {
		
	}
	
	
}
