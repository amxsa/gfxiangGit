
package cn.com.ispp.ispp_spi;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}cust_name" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}cust_type" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}cust_grade" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}cust_info" maxOccurs="unbounded" minOccurs="0"/>
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
    "custName",
    "custType",
    "custGrade",
    "custInfo"
})
@XmlRootElement(name = "cust_infos")
public class CustInfos {

    @XmlElement(name = "cust_name")
    protected String custName;
    @XmlElement(name = "cust_type")
    protected String custType;
    @XmlElement(name = "cust_grade")
    protected String custGrade;
    @XmlElement(name = "cust_info", required = true)
    protected List<CustInfo> custInfo;

    /**
     * Gets the value of the custName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustName() {
        return custName;
    }

    /**
     * Sets the value of the custName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustName(String value) {
        this.custName = value;
    }

    /**
     * Gets the value of the custType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustType() {
        return custType;
    }

    /**
     * Sets the value of the custType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustType(String value) {
        this.custType = value;
    }

    /**
     * Gets the value of the custGrade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustGrade() {
        return custGrade;
    }

    /**
     * Sets the value of the custGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustGrade(String value) {
        this.custGrade = value;
    }

    /**
     * Gets the value of the custInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the custInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustInfo }
     * 
     * 
     */
    public List<CustInfo> getCustInfo() {
        if (custInfo == null) {
            custInfo = new ArrayList<CustInfo>();
        }
        return this.custInfo;
    }

}
