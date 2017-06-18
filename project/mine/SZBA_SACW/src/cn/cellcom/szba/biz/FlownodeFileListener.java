package cn.cellcom.szba.biz;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import cn.cellcom.szba.service.impl.FlownodeEngine;

public class FlownodeFileListener implements FileAlterationListener{

	@Override
	public void onDirectoryChange(File arg0) {
		
	}

	@Override
	public void onDirectoryCreate(File arg0) {
		
	}

	@Override
	public void onDirectoryDelete(File arg0) {
		
	}

	@Override
	public void onFileChange(File file) {
		FlownodeEngine.refresh(file);
		FlownodeEngine.printFlownodes();
	}

	@Override
	public void onFileCreate(File file) {
		FlownodeEngine.refresh(file);
		FlownodeEngine.printFlownodes();
	}

	@Override
	public void onFileDelete(File file) {
		FlownodeEngine.refresh(file);
		FlownodeEngine.printFlownodes();
	}

	@Override
	public void onStart(FileAlterationObserver arg0) {
		
	}

	@Override
	public void onStop(FileAlterationObserver arg0) {
		
	}

}
