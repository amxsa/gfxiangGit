package cn.cellcom.wechat.webservice.service;

public interface InternalService {
	/**
	 * 内部请求
	 * @param seqid
	 * @param regNo
	 * @param cfNumber
	 * @param characterCode
	 * @return 
	 */
	public String InternalCallforwardAPI(String seqid,String regNo,String cfNumber,String characterCode);
}
