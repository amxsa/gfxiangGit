
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}time_value" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}time_unit" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}excute_time" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}sla_grade" minOccurs="0"/>
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
    "timeValue",
    "timeUnit",
    "excuteTime",
    "slaGrade"
})
@XmlRootElement(name = "sla_info")
public class SlaInfo {

    @XmlElement(name = "time_value")
    protected String timeValue;
    @XmlElement(name = "time_unit")
    protected String timeUnit;
    @XmlElement(name = "excute_time")
    protected String excuteTime;
    @XmlElement(name = "sla_grade")
    protected Integer slaGrade;

    /**
     * Gets the value of the timeValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeValue() {
        return timeValue;
    }

    /**
     * Sets the value of the timeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeValue(String value) {
        this.timeValue = value;
    }

    /**
     * Gets the value of the timeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeUnit() {
        return timeUnit;
    }

    /**
     * Sets the value of the timeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeUnit(String value) {
        this.timeUnit = value;
    }

    /**
     * Gets the value of the excuteTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcuteTime() {
        return excuteTime;
    }

    /**
     * Sets the value of the excuteTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcuteTime(String value) {
        this.excuteTime = value;
    }

    /**
     * Gets the value of the slaGrade property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSlaGrade() {
        return slaGrade;
    }

    /**
     * Sets the value of the slaGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSlaGrade(Integer value) {
        this.slaGrade = value;
    }

}
