
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}prod_code"/>
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
    "prodCode"
})
@XmlRootElement(name = "prod_attr_relate")
public class ProdAttrRelate {

    @XmlElement(name = "character_code", required = true)
    protected String characterCode;
    @XmlElement(name = "prod_code", required = true)
    protected String prodCode;

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
     * Gets the value of the prodCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdCode() {
        return prodCode;
    }

    /**
     * Sets the value of the prodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdCode(String value) {
        this.prodCode = value;
    }

}
