package cn.cellcom.szba.domain;

import java.util.ArrayList;
import java.util.List;

public class TCaseDisposal {

	private long disposalId;
	private String disposal;
	private String disposalPerson;
	private String instruction;
	private String proId;
	private long attachId;
	private List<TAttachment> attachment; // 附件列表

	public TCaseDisposal() {
		attachment = new ArrayList<TAttachment>();
	}

	public List<TAttachment> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<TAttachment> attachment) {
		this.attachment = attachment;
	}

	public String getDisposal() {
		return disposal;
	}

	public void setDisposal(String disposal) {
		this.disposal = disposal;
	}

	public long getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(long disposalId) {
		this.disposalId = disposalId;
	}

	public String getDisposalPerson() {
		return disposalPerson;
	}

	public void setDisposalPerson(String disposalPerson) {
		this.disposalPerson = disposalPerson;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public long getAttachId() {
		return attachId;
	}

	public void setAttachId(long attachId) {
		this.attachId = attachId;
	}
}
