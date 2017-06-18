package cn.cellcom.szba.common.fileUtil;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class ComparatorByName implements Comparator<File> {

	private boolean isAsc;
	public ComparatorByName(boolean isAsc){
		this.isAsc = isAsc;
	}
	@Override
	public int compare(File o1, File o2) {
		if(isAsc){
			return Collator.getInstance(Locale.ENGLISH).compare(o1.getName(), o2.getName());
		}else{
			return Collator.getInstance(Locale.ENGLISH).compare(o2.getName(), o1.getName());
		}
	}

}
