package hcho.nsfw.reserve.entity;

import java.sql.Timestamp;

/**
 * Reserve entity. @author MyEclipse Persistence Tools
 */

public class Reserve implements java.io.Serializable {

	// Fields

	private String reserveId;
	private Item item;
	private String reserveNo;
	private String reserveItemId;
	private Timestamp reserveTime;
	private String reserveAddress;
	private String reserveExplain;
	private String reserveName;
	private String reserveMobile;
	private Timestamp reserveReplyTime;
	private String reserveState;
	private String reserveReplyContent;
	private String reserveReplyId;

	// Constructors

	/** default constructor */
	public Reserve() {
	}

	/** minimal constructor */
	public Reserve(Item item) {
		this.item = item;
	}

	/** full constructor */
	public Reserve(Item item, String reserveNo, String reserveItemId,
			Timestamp reserveTime, String reserveAddress,
			String reserveExplain, String reserveName, String reserveMobile,
			Timestamp reserveReplyTime, String reserveState,
			String reserveReplyContent, String reserveReplyId) {
		this.item = item;
		this.reserveNo = reserveNo;
		this.reserveItemId = reserveItemId;
		this.reserveTime = reserveTime;
		this.reserveAddress = reserveAddress;
		this.reserveExplain = reserveExplain;
		this.reserveName = reserveName;
		this.reserveMobile = reserveMobile;
		this.reserveReplyTime = reserveReplyTime;
		this.reserveState = reserveState;
		this.reserveReplyContent = reserveReplyContent;
		this.reserveReplyId = reserveReplyId;
	}

	// Property accessors

	public String getReserveId() {
		return this.reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getReserveNo() {
		return this.reserveNo;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public String getReserveItemId() {
		return this.reserveItemId;
	}

	public void setReserveItemId(String reserveItemId) {
		this.reserveItemId = reserveItemId;
	}

	public Timestamp getReserveTime() {
		return this.reserveTime;
	}

	public void setReserveTime(Timestamp reserveTime) {
		this.reserveTime = reserveTime;
	}

	public String getReserveAddress() {
		return this.reserveAddress;
	}

	public void setReserveAddress(String reserveAddress) {
		this.reserveAddress = reserveAddress;
	}

	public String getReserveExplain() {
		return this.reserveExplain;
	}

	public void setReserveExplain(String reserveExplain) {
		this.reserveExplain = reserveExplain;
	}

	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	public String getReserveMobile() {
		return this.reserveMobile;
	}

	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}

	public Timestamp getReserveReplyTime() {
		return this.reserveReplyTime;
	}

	public void setReserveReplyTime(Timestamp reserveReplyTime) {
		this.reserveReplyTime = reserveReplyTime;
	}

	public String getReserveState() {
		return this.reserveState;
	}

	public void setReserveState(String reserveState) {
		this.reserveState = reserveState;
	}

	public String getReserveReplyContent() {
		return this.reserveReplyContent;
	}

	public void setReserveReplyContent(String reserveReplyContent) {
		this.reserveReplyContent = reserveReplyContent;
	}

	public String getReserveReplyId() {
		return this.reserveReplyId;
	}

	public void setReserveReplyId(String reserveReplyId) {
		this.reserveReplyId = reserveReplyId;
	}

}