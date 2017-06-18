
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}rela_work_order"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}rela_type"/>
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
    "relaWorkOrder",
    "relaType"
})
@XmlRootElement(name = "rela_work_orders")
public class RelaWorkOrders {

    @XmlElement(name = "rela_work_order", required = true)
    protected String relaWorkOrder;
    @XmlElement(name = "rela_type", required = true)
    protected String relaType;

    /**
     * Gets the value of the relaWorkOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelaWorkOrder() {
        return relaWorkOrder;
    }

    /**
     * Sets the value of the relaWorkOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelaWorkOrder(String value) {
        this.relaWorkOrder = value;
    }

    /**
     * Gets the value of the relaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelaType() {
        return relaType;
    }

    /**
     * Sets the value of the relaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelaType(String value) {
        this.relaType = value;
    }

}
