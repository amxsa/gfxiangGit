package hcho.nsfw.info.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hcho.core.service.impl.BaseServiceImpl;
import hcho.nsfw.info.dao.InfoDao;
import hcho.nsfw.info.entity.Info;
import hcho.nsfw.info.service.InfoService;

@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService{
	
	private InfoDao infoDao;
	
	@Resource
	public void setInfoDao(InfoDao infoDao){
		super.setBaseDao(infoDao);
		this.infoDao=infoDao;
	}
}
