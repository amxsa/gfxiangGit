package com.gf.ims.service;

import java.util.List;

import com.gf.imsCommon.ims.module.Menu;

public interface MenuService {
	List<Menu> queryMenuTree(String type);

	Menu saveMenu(Menu menu);
}
