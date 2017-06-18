package cn.cellcom.szba.domain;

import java.util.ArrayList;
import java.util.List;

import cn.cellcom.szba.domain.base.BaseRecord;

public class TExtractRecord extends BaseRecord{

	private String extractID;
	private List<TProperty> properties;
	private List<TCivilian> owners; // 持有人
	private List<TCivilian> keepers; // 保管人

	public TExtractRecord() {
		super();
		properties = new ArrayList<TProperty>();
		owners = new ArrayList<TCivilian>();
		keepers = new ArrayList<TCivilian>();
	}

	public String getExtractID() {
		return extractID;
	}

	public void setExtractID(String extractID) {
		this.extractID = extractID;
	}

	public List<TProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<TProperty> properties) {
		this.properties = properties;
	}

	public List<TCivilian> getOwners() {
		return owners;
	}

	public void setOwners(List<TCivilian> owners) {
		this.owners = owners;
	}

	public List<TCivilian> getKeepers() {
		return keepers;
	}

	public void setKeepers(List<TCivilian> keepers) {
		this.keepers = keepers;
	}



}
