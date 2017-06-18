package hcho.nsfw.reserve.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hcho.core.service.impl.BaseServiceImpl;
import hcho.nsfw.reserve.dao.ItemDao;
import hcho.nsfw.reserve.entity.Item;
import hcho.nsfw.reserve.service.ItemService;

@Service("itemService")
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{
	
	private ItemDao itemDao;
	@Resource
	public void setItemDao(ItemDao itemDao) {
		super.setBaseDao(itemDao);
		this.itemDao = itemDao;
	}
}
