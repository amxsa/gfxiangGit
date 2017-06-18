
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}character_code" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}character_name" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}character_value" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}old_character_value" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}act_type" minOccurs="0"/>
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
    "characterName",
    "characterValue",
    "oldCharacterValue",
    "actType"
})
@XmlRootElement(name = "prod_character")
public class ProdCharacter {

    @XmlElement(name = "character_code")
    protected String characterCode;
    @XmlElement(name = "character_name")
    protected String characterName;
    @XmlElement(name = "character_value")
    protected String characterValue;
    @XmlElement(name = "old_character_value")
    protected String oldCharacterValue;
    @XmlElement(name = "act_type")
    protected String actType;

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
     * Gets the value of the characterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * Sets the value of the characterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterName(String value) {
        this.characterName = value;
    }

    /**
     * Gets the value of the characterValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterValue() {
        return characterValue;
    }

    /**
     * Sets the value of the characterValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterValue(String value) {
        this.characterValue = value;
    }

    /**
     * Gets the value of the oldCharacterValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldCharacterValue() {
        return oldCharacterValue;
    }

    /**
     * Sets the value of the oldCharacterValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldCharacterValue(String value) {
        this.oldCharacterValue = value;
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
