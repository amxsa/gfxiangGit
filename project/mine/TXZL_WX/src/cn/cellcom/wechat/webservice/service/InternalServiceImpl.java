package cn.cellcom.wechat.webservice.service;


import java.util.Date;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.date.DateTool;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.webservice.client.SPISyn;
import cn.cellcom.wechat.webservice.client.iSPPSPIServicesClient;


import cn.com.ispp.ispp_spi.ConfirmMsg;
import cn.com.ispp.ispp_spi.InterfaceMsg;
import cn.com.ispp.ispp_spi.MsgHead;
import cn.com.ispp.ispp_spi.ObjectFactory;
import cn.com.ispp.ispp_spi.ProdAttrRelate;
import cn.com.ispp.ispp_spi.ProdAttrRelates;
import cn.com.ispp.ispp_spi.ProdCharacter;
import cn.com.ispp.ispp_spi.ProdCharacters;
import cn.com.ispp.ispp_spi.ProdInfo;
import cn.com.ispp.ispp_spi.Public;
import cn.com.ispp.ispp_spi.ResInfos;
import cn.com.ispp.ispp_spi.Root;
import cn.com.ispp.ispp_spi.WorkOrderMsgASY;





public class InternalServiceImpl implements InternalService {
	
	private static final Log log = LogFactory.getLog(InternalServiceImpl.class);

	public String InternalCallforwardAPI(String seqid, String regNo, String cfNumber, String characterCode) {
		
		if(Env.FLAG_U.equals(characterCode)){
			characterCode = Env.CALLFORWARD_UNCOND;
		}else if(Env.FLAG_B.equals(characterCode)){
			characterCode = Env.CALLFORWARD_BUSY;
		}else if(Env.FLAG_G.equals(characterCode)){
			characterCode = Env.CALLFORWARD_NOGET;
		}else{
			characterCode = Env.CALLFORWARD_NORESPONSE;
		}
		
		
		iSPPSPIServicesClient client = new iSPPSPIServicesClient();
		// create a default service endpoint
		SPISyn service = client.getSPIServicesPort();

		ObjectFactory factory = new ObjectFactory();

		WorkOrderMsgASY woma = factory.createWorkOrderMsgASY();
		Root root = factory.createRoot();
		
		/*
		 * 消息头
		 */
		MsgHead head = factory.createMsgHead();
		
		head.setMsgType(Env.MESSAGEREQUEST);
		head.setTime(DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		head.setFrom("MDS001");
		head.setTo("ISPP");
		head.setSerial(String.valueOf(System.currentTimeMillis()));
		root.setMsgHead(head);
		
		/*
		 * 包体
		 */
		InterfaceMsg body = factory.createInterfaceMsg();
		
		/* 公共信息 */
		Public p = factory.createPublic();
		// 业务系统的请求工单标识
		p.setWorkOrderId(seqid);
		// 工单类型标识工单是正常工单还是方向工单，如为反向工单则需要进行反向操作
		p.setWorkType(Env.WORKTYPE_FORWARD);
		// 关联工单信息集合，当工单类型为追单时候需要带关联工单标识。包括关联工单标识，关联类型
//		p.setRelaInfoCollection(factory.createRelaInfoCollection());
		// 标识工单来源区域
		p.setAreaCode("020");
		body.setPublic(p);
		
		/* 产品信息 */
		ProdInfo prod = factory.createProdInfo();
		// 主体产品编码，与业务系统统一编码
		prod.setProdCode(Env.PRODCODE);
		// 主体产品名称
//		prod.setProdName("");
		// 业务类型编码，业务系统中业务动作标识。如：开户，销户，停机，复机等
		prod.setSoTypeCode("operation_modify_product_info");
//		prod.setSoTypeName("");
//		prod.setOldProdCode("");
//		prod.setOldProdName("");
		ProdCharacter charactor = factory.createProdCharacter();
		charactor.setCharacterCode(characterCode);
		//前转号码
		charactor.setCharacterValue(cfNumber);
//		charactor.setOldCharacterValue("");
		charactor.setActType(Env.ACT_TYPE);
		
		ProdCharacters charactors = factory.createProdCharacters();
		charactors.getProdCharacter().add(charactor);
		prod.setProdCharacters(charactors);
		
//		prod.setSubProducts(factory.createSubProducts());
		
		ProdAttrRelate proRelate = factory.createProdAttrRelate();
		proRelate.setCharacterCode(characterCode);
		proRelate.setProdCode(Env.PRODCODE);
		ProdAttrRelates prodRelates = factory.createProdAttrRelates();
		prodRelates.getProdAttrRelate().add(proRelate);
		prod.setProdAttrRelates(prodRelates);
		
		
//		prod.setSubAttrRelates(factory.createSubAttrRelates());

		body.setProdInfo(prod);
		
		/* 资源信息 */
		ResInfos resinfos = factory.createResInfos();
//		resinfos.setImsi("460059031201112");
//		resinfos.setOldImsi("");
		//注册号码
		resinfos.setMsisdn(regNo);
//		resinfos.setOldMsisdn("");
//		resinfos.getResInfo().add(factory.createResInfo());
		body.setResInfos(resinfos);
		
		/* 客户信息 */
//		CustInfos cusinfos = factory.createCustInfos();
//		cusinfos.setCustName("张三");
//		cusinfos.setCustType("01");
//		cusinfos.setCustGrade("01");
//		cusinfos.getCustInfo().add(factory.createCustInfo());

//		body.setCustInfos(cusinfos);
		
		/* sla信息 */
//		SlaInfo slainfo = factory.createSlaInfo();
//		slainfo.setTimeValue("10");
//		slainfo.setTimeUnit("second");
//		slainfo.setExcuteTime("");
//		slainfo.setSlaGrade(1);
//
//		body.setSlaInfo(slainfo);
		
		root.setInterfaceMsg(body);

		woma.setRoot(root);
		
		log.info("此次向ISPP发送请求参数："+"注册号-"+regNo+"呼转号-"+cfNumber+"工单标识-"+seqid+"业务类型-"+characterCode);
		
		try {
			ConfirmMsg result = service
					.aSYSPIAPI(
							woma,
							"mds001",
							"mds001",
							seqid,
							"http://132.108.116.28:8080/axis2/services/iSPPBOSSServices",
							"");
			log.info("返回确定包参数：" + "结果-"
					+ result.getRoot().getInterfaceMsg().getIsSuccess()
					+ "错误描述-"
					+ result.getRoot().getInterfaceMsg().getErrorDesc());
			return result.getRoot().getInterfaceMsg().getIsSuccess();
		} catch (Exception e) {
			log.info("请求出现异常，检查网络状况............................................."+e);
		}	
		
		return "-1";

	}


}
