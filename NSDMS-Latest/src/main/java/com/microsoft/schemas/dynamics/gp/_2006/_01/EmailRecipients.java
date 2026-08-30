
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmailRecipients complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmailRecipients"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="To" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfString" minOccurs="0"/&gt;
 *         &lt;element name="CC" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfString" minOccurs="0"/&gt;
 *         &lt;element name="BCC" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailRecipients", propOrder = {
    "to",
    "cc",
    "bcc"
})
public class EmailRecipients {

    @XmlElement(name = "To")
    protected ArrayOfString to;
    @XmlElement(name = "CC")
    protected ArrayOfString cc;
    @XmlElement(name = "BCC")
    protected ArrayOfString bcc;

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setTo(ArrayOfString value) {
        this.to = value;
    }

    /**
     * Gets the value of the cc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getCC() {
        return cc;
    }

    /**
     * Sets the value of the cc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setCC(ArrayOfString value) {
        this.cc = value;
    }

    /**
     * Gets the value of the bcc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getBCC() {
        return bcc;
    }

    /**
     * Sets the value of the bcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setBCC(ArrayOfString value) {
        this.bcc = value;
    }

}
