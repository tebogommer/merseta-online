
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesUserDefined complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesUserDefined"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Date01" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date02" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="List01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="List02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="List03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesUserDefined", propOrder = {
    "date01",
    "date02",
    "list01",
    "list02",
    "list03",
    "text01",
    "text02",
    "text03",
    "text04",
    "text05"
})
public class SalesUserDefined {

    @XmlElement(name = "Date01", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date01;
    @XmlElement(name = "Date02", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date02;
    @XmlElement(name = "List01")
    protected String list01;
    @XmlElement(name = "List02")
    protected String list02;
    @XmlElement(name = "List03")
    protected String list03;
    @XmlElement(name = "Text01")
    protected String text01;
    @XmlElement(name = "Text02")
    protected String text02;
    @XmlElement(name = "Text03")
    protected String text03;
    @XmlElement(name = "Text04")
    protected String text04;
    @XmlElement(name = "Text05")
    protected String text05;

    /**
     * Gets the value of the date01 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate01() {
        return date01;
    }

    /**
     * Sets the value of the date01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate01(XMLGregorianCalendar value) {
        this.date01 = value;
    }

    /**
     * Gets the value of the date02 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate02() {
        return date02;
    }

    /**
     * Sets the value of the date02 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate02(XMLGregorianCalendar value) {
        this.date02 = value;
    }

    /**
     * Gets the value of the list01 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getList01() {
        return list01;
    }

    /**
     * Sets the value of the list01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setList01(String value) {
        this.list01 = value;
    }

    /**
     * Gets the value of the list02 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getList02() {
        return list02;
    }

    /**
     * Sets the value of the list02 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setList02(String value) {
        this.list02 = value;
    }

    /**
     * Gets the value of the list03 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getList03() {
        return list03;
    }

    /**
     * Sets the value of the list03 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setList03(String value) {
        this.list03 = value;
    }

    /**
     * Gets the value of the text01 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText01() {
        return text01;
    }

    /**
     * Sets the value of the text01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText01(String value) {
        this.text01 = value;
    }

    /**
     * Gets the value of the text02 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText02() {
        return text02;
    }

    /**
     * Sets the value of the text02 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText02(String value) {
        this.text02 = value;
    }

    /**
     * Gets the value of the text03 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText03() {
        return text03;
    }

    /**
     * Sets the value of the text03 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText03(String value) {
        this.text03 = value;
    }

    /**
     * Gets the value of the text04 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText04() {
        return text04;
    }

    /**
     * Sets the value of the text04 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText04(String value) {
        this.text04 = value;
    }

    /**
     * Gets the value of the text05 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText05() {
        return text05;
    }

    /**
     * Sets the value of the text05 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText05(String value) {
        this.text05 = value;
    }

}
