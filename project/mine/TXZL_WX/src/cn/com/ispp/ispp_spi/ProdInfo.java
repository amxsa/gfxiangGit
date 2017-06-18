
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}prod_code"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}prod_name" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}so_type_code"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}so_type_name" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}old_prod_code" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}old_prod_name" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}prod_characters" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}sub_products" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}sub_attr_relates" minOccurs="0"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}prod_attr_relates" minOccurs="0"/>
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
    "prodCode",
    "prodName",
    "soTypeCode",
    "soTypeName",
    "oldProdCode",
    "oldProdName",
    "prodCharacters",
    "subProducts",
    "subAttrRelates",
    "prodAttrRelates"
})
@XmlRootElement(name = "prod_info")
public class ProdInfo {

    @XmlElement(name = "prod_code", required = true)
    protected String prodCode;
    @XmlElement(name = "prod_name")
    protected String prodName;
    @XmlElement(name = "so_type_code", required = true)
    protected String soTypeCode;
    @XmlElement(name = "so_type_name")
    protected String soTypeName;
    @XmlElement(name = "old_prod_code")
    protected String oldProdCode;
    @XmlElement(name = "old_prod_name")
    protected String oldProdName;
    @XmlElement(name = "prod_characters")
    protected ProdCharacters prodCharacters;
    @XmlElement(name = "sub_products")
    protected SubProducts subProducts;
    @XmlElement(name = "sub_attr_relates")
    protected SubAttrRelates subAttrRelates;
    @XmlElement(name = "prod_attr_relates")
    protected ProdAttrRelates prodAttrRelates;

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

    /**
     * Gets the value of the prodName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * Sets the value of the prodName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdName(String value) {
        this.prodName = value;
    }

    /**
     * Gets the value of the soTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoTypeCode() {
        return soTypeCode;
    }

    /**
     * Sets the value of the soTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoTypeCode(String value) {
        this.soTypeCode = value;
    }

    /**
     * Gets the value of the soTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoTypeName() {
        return soTypeName;
    }

    /**
     * Sets the value of the soTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoTypeName(String value) {
        this.soTypeName = value;
    }

    /**
     * Gets the value of the oldProdCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldProdCode() {
        return oldProdCode;
    }

    /**
     * Sets the value of the oldProdCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldProdCode(String value) {
        this.oldProdCode = value;
    }

    /**
     * Gets the value of the oldProdName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldProdName() {
        return oldProdName;
    }

    /**
     * Sets the value of the oldProdName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldProdName(String value) {
        this.oldProdName = value;
    }

    /**
     * Gets the value of the prodCharacters property.
     * 
     * @return
     *     possible object is
     *     {@link ProdCharacters }
     *     
     */
    public ProdCharacters getProdCharacters() {
        return prodCharacters;
    }

    /**
     * Sets the value of the prodCharacters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProdCharacters }
     *     
     */
    public void setProdCharacters(ProdCharacters value) {
        this.prodCharacters = value;
    }

    /**
     * Gets the value of the subProducts property.
     * 
     * @return
     *     possible object is
     *     {@link SubProducts }
     *     
     */
    public SubProducts getSubProducts() {
        return subProducts;
    }

    /**
     * Sets the value of the subProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubProducts }
     *     
     */
    public void setSubProducts(SubProducts value) {
        this.subProducts = value;
    }

    /**
     * Gets the value of the subAttrRelates property.
     * 
     * @return
     *     possible object is
     *     {@link SubAttrRelates }
     *     
     */
    public SubAttrRelates getSubAttrRelates() {
        return subAttrRelates;
    }

    /**
     * Sets the value of the subAttrRelates property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubAttrRelates }
     *     
     */
    public void setSubAttrRelates(SubAttrRelates value) {
        this.subAttrRelates = value;
    }

    /**
     * Gets the value of the prodAttrRelates property.
     * 
     * @return
     *     possible object is
     *     {@link ProdAttrRelates }
     *     
     */
    public ProdAttrRelates getProdAttrRelates() {
        return prodAttrRelates;
    }

    /**
     * Sets the value of the prodAttrRelates property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProdAttrRelates }
     *     
     */
    public void setProdAttrRelates(ProdAttrRelates value) {
        this.prodAttrRelates = value;
    }

}
