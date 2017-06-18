
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
 *       &lt;sequence>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}sub_product_code"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}sub_product_name" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}act_type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "subProductCode",
    "subProductName",
    "actType",
    "isSuccess",
    "returnDesc"
})
@XmlRootElement(name = "sub_product")
public class SubProduct {

    @XmlElement(name = "sub_product_code", required = true)
    protected String subProductCode;
    @XmlElement(name = "sub_product_name")
    protected String subProductName;
    @XmlElement(name = "act_type", required = true)
    protected String actType;
    @XmlElement(name = "is_success", required = true)
    protected String isSuccess;
    
    @XmlElement(name = "return_desc", required = true)
    protected String returnDesc;

    public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReturnDesc() {
		return returnDesc;
	}

	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}

	/**
     * Gets the value of the subProductCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubProductCode() {
        return subProductCode;
    }

    /**
     * Sets the value of the subProductCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubProductCode(String value) {
        this.subProductCode = value;
    }

    /**
     * Gets the value of the subProductName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubProductName() {
        return subProductName;
    }

    /**
     * Sets the value of the subProductName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubProductName(String value) {
        this.subProductName = value;
    }

    /**
     * Gets the value of the actType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActType() {
        return actType;
    }

    /**
     * Sets the value of the actType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActType(String value) {
        this.actType = value;
    }

}
