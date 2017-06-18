package com.jlit.xms.util;

import java.text.CollationKey;
import java.text.Collator;


public  class CollatorComparator extends Collator {

	private Collator collator = Collator.getInstance();  
	private int sort=1;  
	@Override
	public int compare(String arg0, String arg1) {
		
		CollationKey key1 = collator.getCollationKey(arg0);
		CollationKey key2 = collator.getCollationKey(arg0);
		return sort*key1.compareTo(key2);
	}

	/** 
  　　* 设定排序的方向 
  　　* @param i 排序方向（正数：正序；负数：倒序） 
  　　*/  
	
	public void setSort(int i){
		if(i>0){
			sort=1;
		}else if(i<0){
			sort=-1;
		}
	} 
	@Override
	public CollationKey getCollationKey(String source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
