
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}work_order_id"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}work_type"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}rela_info_collection" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}area_code"/>
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
    "workOrderId",
    "workType",
    "relaInfoCollection",
    "areaCode"
})
@XmlRootElement(name = "public")
public class Public {

    @XmlElement(name = "work_order_id", required = true)
    protected String workOrderId;
    @XmlElement(name = "work_type", required = true)
    protected String workType;
    @XmlElement(name = "rela_info_collection")
    protected RelaInfoCollection relaInfoCollection;
    @XmlElement(name = "area_code", required = true)
    protected String areaCode;

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
     * Gets the value of the workType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkType() {
        return workType;
    }

    /**
     * Sets the value of the workType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkType(String value) {
        this.workType = value;
    }

    /**
     * Gets the value of the relaInfoCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RelaInfoCollection }
     *     
     */
    public RelaInfoCollection getRelaInfoCollection() {
        return relaInfoCollection;
    }

    /**
     * Sets the value of the relaInfoCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelaInfoCollection }
     *     
     */
    public void setRelaInfoCollection(RelaInfoCollection value) {
        this.relaInfoCollection = value;
    }

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

}
