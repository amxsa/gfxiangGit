
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}character_code"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}sub_product_code"/>
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
    "characterCode",
    "subProductCode"
})
@XmlRootElement(name = "sub_attr_relate")
public class SubAttrRelate {

    @XmlElement(name = "character_code", required = true)
    protected String characterCode;
    @XmlElement(name = "sub_product_code", required = true)
    protected String subProductCode;

    /**
     * Gets the value of the characterCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterCode() {
        return characterCode;
    }

    /**
     * Sets the value of the characterCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterCode(String value) {
        this.characterCode = value;
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

}
