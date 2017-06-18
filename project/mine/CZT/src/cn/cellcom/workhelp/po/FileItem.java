package cn.cellcom.workhelp.po;

import java.io.File;
import java.util.Date;

import cn.cellcom.workhelp.bus.WorkHelpCommon;

public class FileItem {
	private String fileName;
	private boolean isDirectory;
	private File file;
	private String parent;
	private Date createTime;
	private long size;
	private String extName;

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public String getExtName() {
		String ext = "";
		int index = this.fileName.lastIndexOf(".");
		if (index >= 0) {
			ext = this.fileName.substring(index);
		}
		return ext;
	}

	public String getParent() {
		return this.parent;
	}

	public String getParentC() {
		return WorkHelpCommon.escapeSecond(this.parent);
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileNameC() {
		return WorkHelpCommon.escapeSecond(this.fileName);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;

	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return "";
	}

	public long getSize() {
		return size;
	}

	public String getStringSize() {
		return "";
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFullPath() {
		return this.parent.endsWith("/") ? this.parent + this.fileName
				: this.parent + "/" + this.fileName;
	}

	public String getFullPathC() {
		return WorkHelpCommon.escapeSecond(getFullPath());
	}

}
