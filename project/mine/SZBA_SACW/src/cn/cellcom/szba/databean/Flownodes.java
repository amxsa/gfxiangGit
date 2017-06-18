package cn.cellcom.szba.databean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement  
@XmlType(name = "flownodes")
public class Flownodes {

	@XmlElement(name = "flownode")
	private List<Flownode> flownodes;

	public List<Flownode> getFlownodes() {
		return flownodes;
	}

	public void setFlownodes(List<Flownode> flownodes) {
		this.flownodes = flownodes;
	}
	
}
