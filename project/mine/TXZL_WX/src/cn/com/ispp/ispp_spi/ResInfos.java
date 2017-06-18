
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}imsi" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}old_imsi" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}msisdn" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}old_msisdn" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}res_info" maxOccurs="unbounded" minOccurs="0"/>
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
    "imsi",
    "oldImsi",
    "msisdn",
    "oldMsisdn",
    "resInfo"
})
@XmlRootElement(name = "res_infos")
public class ResInfos {

    protected String imsi;
    @XmlElement(name = "old_imsi")
    protected String oldImsi;
    protected String msisdn;
    @XmlElement(name = "old_msisdn")
    protected String oldMsisdn;
    @XmlElement(name = "res_info", required = true)
    protected List<ResInfo> resInfo;

    /**
     * Gets the value of the imsi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * Sets the value of the imsi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsi(String value) {
        this.imsi = value;
    }

    /**
     * Gets the value of the oldImsi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldImsi() {
        return oldImsi;
    }

    /**
     * Sets the value of the oldImsi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldImsi(String value) {
        this.oldImsi = value;
    }

    /**
     * Gets the value of the msisdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Sets the value of the msisdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsisdn(String value) {
        this.msisdn = value;
    }

    /**
     * Gets the value of the oldMsisdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldMsisdn() {
        return oldMsisdn;
    }

    /**
     * Sets the value of the oldMsisdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldMsisdn(String value) {
        this.oldMsisdn = value;
    }

    /**
     * Gets the value of the resInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResInfo }
     * 
     * 
     */
    public List<ResInfo> getResInfo() {
        if (resInfo == null) {
            resInfo = new ArrayList<ResInfo>();
        }
        return this.resInfo;
    }

}
