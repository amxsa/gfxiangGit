package hcho.nsfw.reserve.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Item entity. @author MyEclipse Persistence Tools
 */

public class Item implements java.io.Serializable {

	// Fields

	private String itemId;
	private String itemName;
	private String itemDealDept;
	private String itemDealName;
	private String itemState;
	private String reserveReplyId;
	private String itemNo;
	private Set reserves = new HashSet(0);

	public String ITEM_STATE_VALID="1";//有效
	public String ITEM_STATE_INVALID="0";//无效
	// Constructors

	public static  List<String> itemIdList;
	static{
		itemIdList=new ArrayList<String>();
		for (int i = 1; i <11; i++) {
			itemIdList.add("编号"+i);
		}
	}
	
	/** default constructor */
	public Item() {
	}

	/** full constructor */
	public Item(String itemName, String itemDealDept, String itemDealName,
			String itemState, String reserveReplyId, String itemNo, Set reserves) {
		this.itemName = itemName;
		this.itemDealDept = itemDealDept;
		this.itemDealName = itemDealName;
		this.itemState = itemState;
		this.reserveReplyId = reserveReplyId;
		this.itemNo = itemNo;
		this.reserves = reserves;
	}

	// Property accessors

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDealDept() {
		return this.itemDealDept;
	}

	public void setItemDealDept(String itemDealDept) {
		this.itemDealDept = itemDealDept;
	}

	public String getItemDealName() {
		return this.itemDealName;
	}

	public void setItemDealName(String itemDealName) {
		this.itemDealName = itemDealName;
	}

	public String getItemState() {
		return this.itemState;
	}

	public void setItemState(String itemState) {
		this.itemState = itemState;
	}

	public String getReserveReplyId() {
		return this.reserveReplyId;
	}

	public void setReserveReplyId(String reserveReplyId) {
		this.reserveReplyId = reserveReplyId;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Set getReserves() {
		return this.reserves;
	}

	public void setReserves(Set reserves) {
		this.reserves = reserves;
	}

}