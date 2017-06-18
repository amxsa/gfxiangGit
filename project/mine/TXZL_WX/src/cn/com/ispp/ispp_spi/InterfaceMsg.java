
package cn.com.ispp.ispp_spi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence minOccurs="0">
 *           &lt;choice minOccurs="0">
 *             &lt;sequence minOccurs="0">
 *               &lt;element ref="{http://ispp.com.cn/ispp_spi/}bat_serial" minOccurs="0"/>
 *               &lt;element ref="{http://ispp.com.cn/ispp_spi/}file_name" minOccurs="0"/>
 *             &lt;/sequence>
 *             &lt;sequence minOccurs="0">
 *               &lt;element ref="{http://ispp.com.cn/ispp_spi/}public" minOccurs="0"/>
 *               &lt;element ref="{http://ispp.com.cn/ispp_spi/}prod_info" minOccurs="0"/>
 *               &lt;element ref="{http://ispp.com.cn/ispp_spi/}res_infos" minOccurs="0"/>
 *               &lt;element ref="{http://ispp.com.cn/ispp_spi/}cust_infos" minOccurs="0"/>
 *               &lt;element ref="{http://ispp.com.cn/ispp_spi/}sla_info" minOccurs="0"/>
 *             &lt;/sequence>
 *           &lt;/choice>
 *         &lt;/sequence>
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}work_order_id" minOccurs="0"/>
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}is_success" minOccurs="0"/>
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}error_desc" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}work_num" minOccurs="0"/>
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}dealed_num" minOccurs="0"/>
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}dealing_num" minOccurs="0"/>
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}error_num" minOccurs="0"/>
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}uncreated_num" minOccurs="0"/>
 *           &lt;element ref="{http://ispp.com.cn/ispp_spi/}work_order_desc" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "batSerial",
    "fileName",
    "_public",
    "prodInfo",
    "resInfos",
    "custInfos",
    "slaInfo",
    "workOrderId",
    "isSuccess",
    "errorDesc",
    "workNum",
    "dealedNum",
    "dealingNum",
    "errorNum",
    "uncreatedNum",
    "workOrderDesc"
})
@XmlRootElement(name = "interface_msg")
public class InterfaceMsg {

    @XmlElement(name = "bat_serial")
    protected String batSerial;
    @XmlElement(name = "file_name")
    protected String fileName;
    @XmlElement(name = "public")
    protected Public _public;
    @XmlElement(name = "prod_info")
    protected ProdInfo prodInfo;
    @XmlElement(name = "res_infos")
    protected ResInfos resInfos;
    @XmlElement(name = "cust_infos")
    protected CustInfos custInfos;
    @XmlElement(name = "sla_info")
    protected SlaInfo slaInfo;
    @XmlElement(name = "work_order_id")
    protected String workOrderId;
    @XmlElement(name = "is_success")
    protected String isSuccess;
    @XmlElement(name = "error_desc")
    protected String errorDesc;
    @XmlElement(name = "work_num")
    protected Integer workNum;
    @XmlElement(name = "dealed_num")
    protected Integer dealedNum;
    @XmlElement(name = "dealing_num")
    protected Integer dealingNum;
    @XmlElement(name = "error_num")
    protected Integer errorNum;
    @XmlElement(name = "uncreated_num")
    protected Integer uncreatedNum;
    @XmlElement(name = "work_order_desc")
    protected String workOrderDesc;

    /**
     * Gets the value of the batSerial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatSerial() {
        return batSerial;
    }

    /**
     * Sets the value of the batSerial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatSerial(String value) {
        this.batSerial = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the public property.
     * 
     * @return
     *     possible object is
     *     {@link Public }
     *     
     */
    public Public getPublic() {
        return _public;
    }

    /**
     * Sets the value of the public property.
     * 
     * @param value
     *     allowed object is
     *     {@link Public }
     *     
     */
    public void setPublic(Public value) {
        this._public = value;
    }

    /**
     * Gets the value of the prodInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ProdInfo }
     *     
     */
    public ProdInfo getProdInfo() {
        return prodInfo;
    }

    /**
     * Sets the value of the prodInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProdInfo }
     *     
     */
    public void setProdInfo(ProdInfo value) {
        this.prodInfo = value;
    }

    /**
     * Gets the value of the resInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ResInfos }
     *     
     */
    public ResInfos getResInfos() {
        return resInfos;
    }

    /**
     * Sets the value of the resInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResInfos }
     *     
     */
    public void setResInfos(ResInfos value) {
        this.resInfos = value;
    }

    /**
     * Gets the value of the custInfos property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfos }
     *     
     */
    public CustInfos getCustInfos() {
        return custInfos;
    }

    /**
     * Sets the value of the custInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfos }
     *     
     */
    public void setCustInfos(CustInfos value) {
        this.custInfos = value;
    }

    /**
     * Gets the value of the slaInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SlaInfo }
     *     
     */
    public SlaInfo getSlaInfo() {
        return slaInfo;
    }

    /**
     * Sets the value of the slaInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SlaInfo }
     *     
     */
    public void setSlaInfo(SlaInfo value) {
        this.slaInfo = value;
    }

    /**
     * Gets the value of the workOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkOrderId() {
        return workOrderId;
    }

    /**
     * Sets the value of the workOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkOrderId(String value) {
        this.workOrderId = value;
    }

    /**
     * Gets the value of the isSuccess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSuccess() {
        return isSuccess;
    }

    /**
     * Sets the value of the isSuccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSuccess(String value) {
        this.isSuccess = value;
    }

    /**
     * Gets the value of the errorDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * Sets the value of the errorDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDesc(String value) {
        this.errorDesc = value;
    }

    /**
     * Gets the value of the workNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWorkNum() {
        return workNum;
    }

    /**
     * Sets the value of the workNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWorkNum(Integer value) {
        this.workNum = value;
    }

    /**
     * Gets the value of the dealedNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDealedNum() {
        return dealedNum;
    }

    /**
     * Sets the value of the dealedNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDealedNum(Integer value) {
        this.dealedNum = value;
    }

    /**
     * Gets the value of the dealingNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDealingNum() {
        return dealingNum;
    }

    /**
     * Sets the value of the dealingNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDealingNum(Integer value) {
        this.dealingNum = value;
    }

    /**
     * Gets the value of the errorNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getErrorNum() {
        return errorNum;
    }

    /**
     * Sets the value of the errorNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setErrorNum(Integer value) {
        this.errorNum = value;
    }

    /**
     * Gets the value of the uncreatedNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUncreatedNum() {
        return uncreatedNum;
    }

    /**
     * Sets the value of the uncreatedNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUncreatedNum(Integer value) {
        this.uncreatedNum = value;
    }

    /**
     * Gets the value of the workOrderDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkOrderDesc() {
        return workOrderDesc;
    }

    /**
     * Sets the value of the workOrderDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkOrderDesc(String value) {
        this.workOrderDesc = value;
    }

}
