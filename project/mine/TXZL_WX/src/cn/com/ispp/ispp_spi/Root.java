
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
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}msg_head"/>
 *         &lt;element ref="{http://ispp.com.cn/ispp_spi/}interface_msg"/>
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
    "msgHead",
    "interfaceMsg"
})
@XmlRootElement(name = "root")
public class Root {

    @XmlElement(name = "msg_head", required = true)
    protected MsgHead msgHead;
    @XmlElement(name = "interface_msg", required = true)
    protected InterfaceMsg interfaceMsg;

    /**
     * Gets the value of the msgHead property.
     * 
     * @return
     *     possible object is
     *     {@link MsgHead }
     *     
     */
    public MsgHead getMsgHead() {
        return msgHead;
    }

    /**
     * Sets the value of the msgHead property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgHead }
     *     
     */
    public void setMsgHead(MsgHead value) {
        this.msgHead = value;
    }

    /**
     * Gets the value of the interfaceMsg property.
     * 
     * @return
     *     possible object is
     *     {@link InterfaceMsg }
     *     
     */
    public InterfaceMsg getInterfaceMsg() {
        return interfaceMsg;
    }

    /**
     * Sets the value of the interfaceMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterfaceMsg }
     *     
     */
    public void setInterfaceMsg(InterfaceMsg value) {
        this.interfaceMsg = value;
    }

}
