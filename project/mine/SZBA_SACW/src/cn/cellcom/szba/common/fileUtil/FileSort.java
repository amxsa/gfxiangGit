package cn.cellcom.szba.common.fileUtil;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileSort {
	private static Log log = LogFactory.getLog(FileSort.class);
	public static void sortListByTime(List<File> list , boolean isAsc) {  
        // 对文件列表进行排序
        ComparatorByName comparator = new ComparatorByName(isAsc);  
        if (!list.isEmpty()) {  
            synchronized (list) {  
                Collections.sort(list, comparator);  
            }  
  
        }  
    }  
}
