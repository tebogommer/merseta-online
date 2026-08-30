
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PricingDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PricingDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PricingDetailKey" minOccurs="0"/&gt;
 *         &lt;element name="UofMPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PricingDetailPrice" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedyBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PricingDetail", propOrder = {
    "key",
    "uofMPrice",
    "lastModifiedDate",
    "lastModifiedyBy"
})
public class PricingDetail
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PricingDetailKey key;
    @XmlElement(name = "UofMPrice")
    protected PricingDetailPrice uofMPrice;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "LastModifiedyBy")
    protected String lastModifiedyBy;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PricingDetailKey }
     *     
     */
    public PricingDetailKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PricingDetailKey }
     *     
     */
    public void setKey(PricingDetailKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the uofMPrice property.
     * 
     * @return
     *     possible object is
     *     {@link PricingDetailPrice }
     *     
     */
    public PricingDetailPrice getUofMPrice() {
        return uofMPrice;
    }

    /**
     * Sets the value of the uofMPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link PricingDetailPrice }
     *     
     */
    public void setUofMPrice(PricingDetailPrice value) {
        this.uofMPrice = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the lastModifiedyBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifiedyBy() {
        return lastModifiedyBy;
    }

    /**
     * Sets the value of the lastModifiedyBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifiedyBy(String value) {
        this.lastModifiedyBy = value;
    }

}
