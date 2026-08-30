
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesSerialLotBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesSerialLotBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Bin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateReceived" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DateSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="IsPosted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesSerialLotBase", propOrder = {
    "bin",
    "dateReceived",
    "dateSequenceNumber",
    "unitCost",
    "isPosted"
})
@XmlSeeAlso({
    SalesSerialLot.class,
    ServiceSerialLot.class
})
public abstract class SalesSerialLotBase
    extends BusinessObject
{

    @XmlElement(name = "Bin")
    protected String bin;
    @XmlElement(name = "DateReceived", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateReceived;
    @XmlElement(name = "DateSequenceNumber", required = true, nillable = true)
    protected BigDecimal dateSequenceNumber;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "IsPosted", required = true, type = Boolean.class, nillable = true)
    protected Boolean isPosted;

    /**
     * Gets the value of the bin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBin() {
        return bin;
    }

    /**
     * Sets the value of the bin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBin(String value) {
        this.bin = value;
    }

    /**
     * Gets the value of the dateReceived property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateReceived() {
        return dateReceived;
    }

    /**
     * Sets the value of the dateReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateReceived(XMLGregorianCalendar value) {
        this.dateReceived = value;
    }

    /**
     * Gets the value of the dateSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDateSequenceNumber() {
        return dateSequenceNumber;
    }

    /**
     * Sets the value of the dateSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDateSequenceNumber(BigDecimal value) {
        this.dateSequenceNumber = value;
    }

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitCost(MoneyAmount value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the isPosted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPosted() {
        return isPosted;
    }

    /**
     * Sets the value of the isPosted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPosted(Boolean value) {
        this.isPosted = value;
    }

}
