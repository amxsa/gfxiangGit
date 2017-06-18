
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}sub_attr_relate" maxOccurs="unbounded"/>
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
    "subAttrRelate"
})
@XmlRootElement(name = "sub_attr_relates")
public class SubAttrRelates {

    @XmlElement(name = "sub_attr_relate", required = true)
    protected List<SubAttrRelate> subAttrRelate;

    /**
     * Gets the value of the subAttrRelate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subAttrRelate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubAttrRelate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubAttrRelate }
     * 
     * 
     */
    public List<SubAttrRelate> getSubAttrRelate() {
        if (subAttrRelate == null) {
            subAttrRelate = new ArrayList<SubAttrRelate>();
        }
        return this.subAttrRelate;
    }

}
