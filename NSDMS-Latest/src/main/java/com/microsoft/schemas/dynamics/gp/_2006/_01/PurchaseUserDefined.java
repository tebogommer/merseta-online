
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PurchaseUserDefined complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseUserDefined"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseUserDefinedKey" minOccurs="0"/&gt;
 *         &lt;element name="List01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="List02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="List03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="List04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="List05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text06" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text07" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text08" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text09" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Text10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Date01" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date02" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date03" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date04" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date05" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date06" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date07" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date08" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date09" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date10" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date11" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date12" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date13" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date14" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date15" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date16" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date17" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date18" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date19" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Date20" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseUserDefined", propOrder = {
    "key",
    "list01",
    "list02",
    "list03",
    "list04",
    "list05",
    "text01",
    "text02",
    "text03",
    "text04",
    "text05",
    "text06",
    "text07",
    "text08",
    "text09",
    "text10",
    "date01",
    "date02",
    "date03",
    "date04",
    "date05",
    "date06",
    "date07",
    "date08",
    "date09",
    "date10",
    "date11",
    "date12",
    "date13",
    "date14",
    "date15",
    "date16",
    "date17",
    "date18",
    "date19",
    "date20"
})
public class PurchaseUserDefined
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PurchaseUserDefinedKey key;
    @XmlElement(name = "List01")
    protected String list01;
    @XmlElement(name = "List02")
    protected String list02;
    @XmlElement(name = "List03")
    protected String list03;
    @XmlElement(name = "List04")
    protected String list04;
    @XmlElement(name = "List05")
    protected String list05;
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
    @XmlElement(name = "Text06")
    protected String text06;
    @XmlElement(name = "Text07")
    protected String text07;
    @XmlElement(name = "Text08")
    protected String text08;
    @XmlElement(name = "Text09")
    protected String text09;
    @XmlElement(name = "Text10")
    protected String text10;
    @XmlElement(name = "Date01", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date01;
    @XmlElement(name = "Date02", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date02;
    @XmlElement(name = "Date03", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date03;
    @XmlElement(name = "Date04", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date04;
    @XmlElement(name = "Date05", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date05;
    @XmlElement(name = "Date06", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date06;
    @XmlElement(name = "Date07", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date07;
    @XmlElement(name = "Date08", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date08;
    @XmlElement(name = "Date09", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date09;
    @XmlElement(name = "Date10", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date10;
    @XmlElement(name = "Date11", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date11;
    @XmlElement(name = "Date12", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date12;
    @XmlElement(name = "Date13", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date13;
    @XmlElement(name = "Date14", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date14;
    @XmlElement(name = "Date15", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date15;
    @XmlElement(name = "Date16", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date16;
    @XmlElement(name = "Date17", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date17;
    @XmlElement(name = "Date18", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date18;
    @XmlElement(name = "Date19", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date19;
    @XmlElement(name = "Date20", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date20;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseUserDefinedKey }
     *     
     */
    public PurchaseUserDefinedKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseUserDefinedKey }
     *     
     */
    public void setKey(PurchaseUserDefinedKey value) {
        this.key = value;
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
     * Gets the value of the list04 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getList04() {
        return list04;
    }

    /**
     * Sets the value of the list04 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setList04(String value) {
        this.list04 = value;
    }

    /**
     * Gets the value of the list05 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getList05() {
        return list05;
    }

    /**
     * Sets the value of the list05 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setList05(String value) {
        this.list05 = value;
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

    /**
     * Gets the value of the text06 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText06() {
        return text06;
    }

    /**
     * Sets the value of the text06 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText06(String value) {
        this.text06 = value;
    }

    /**
     * Gets the value of the text07 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText07() {
        return text07;
    }

    /**
     * Sets the value of the text07 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText07(String value) {
        this.text07 = value;
    }

    /**
     * Gets the value of the text08 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText08() {
        return text08;
    }

    /**
     * Sets the value of the text08 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText08(String value) {
        this.text08 = value;
    }

    /**
     * Gets the value of the text09 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText09() {
        return text09;
    }

    /**
     * Sets the value of the text09 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText09(String value) {
        this.text09 = value;
    }

    /**
     * Gets the value of the text10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText10() {
        return text10;
    }

    /**
     * Sets the value of the text10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText10(String value) {
        this.text10 = value;
    }

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
     * Gets the value of the date03 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate03() {
        return date03;
    }

    /**
     * Sets the value of the date03 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate03(XMLGregorianCalendar value) {
        this.date03 = value;
    }

    /**
     * Gets the value of the date04 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate04() {
        return date04;
    }

    /**
     * Sets the value of the date04 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate04(XMLGregorianCalendar value) {
        this.date04 = value;
    }

    /**
     * Gets the value of the date05 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate05() {
        return date05;
    }

    /**
     * Sets the value of the date05 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate05(XMLGregorianCalendar value) {
        this.date05 = value;
    }

    /**
     * Gets the value of the date06 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate06() {
        return date06;
    }

    /**
     * Sets the value of the date06 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate06(XMLGregorianCalendar value) {
        this.date06 = value;
    }

    /**
     * Gets the value of the date07 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate07() {
        return date07;
    }

    /**
     * Sets the value of the date07 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate07(XMLGregorianCalendar value) {
        this.date07 = value;
    }

    /**
     * Gets the value of the date08 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate08() {
        return date08;
    }

    /**
     * Sets the value of the date08 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate08(XMLGregorianCalendar value) {
        this.date08 = value;
    }

    /**
     * Gets the value of the date09 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate09() {
        return date09;
    }

    /**
     * Sets the value of the date09 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate09(XMLGregorianCalendar value) {
        this.date09 = value;
    }

    /**
     * Gets the value of the date10 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate10() {
        return date10;
    }

    /**
     * Sets the value of the date10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate10(XMLGregorianCalendar value) {
        this.date10 = value;
    }

    /**
     * Gets the value of the date11 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate11() {
        return date11;
    }

    /**
     * Sets the value of the date11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate11(XMLGregorianCalendar value) {
        this.date11 = value;
    }

    /**
     * Gets the value of the date12 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate12() {
        return date12;
    }

    /**
     * Sets the value of the date12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate12(XMLGregorianCalendar value) {
        this.date12 = value;
    }

    /**
     * Gets the value of the date13 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate13() {
        return date13;
    }

    /**
     * Sets the value of the date13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate13(XMLGregorianCalendar value) {
        this.date13 = value;
    }

    /**
     * Gets the value of the date14 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate14() {
        return date14;
    }

    /**
     * Sets the value of the date14 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate14(XMLGregorianCalendar value) {
        this.date14 = value;
    }

    /**
     * Gets the value of the date15 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate15() {
        return date15;
    }

    /**
     * Sets the value of the date15 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate15(XMLGregorianCalendar value) {
        this.date15 = value;
    }

    /**
     * Gets the value of the date16 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate16() {
        return date16;
    }

    /**
     * Sets the value of the date16 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate16(XMLGregorianCalendar value) {
        this.date16 = value;
    }

    /**
     * Gets the value of the date17 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate17() {
        return date17;
    }

    /**
     * Sets the value of the date17 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate17(XMLGregorianCalendar value) {
        this.date17 = value;
    }

    /**
     * Gets the value of the date18 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate18() {
        return date18;
    }

    /**
     * Sets the value of the date18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate18(XMLGregorianCalendar value) {
        this.date18 = value;
    }

    /**
     * Gets the value of the date19 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate19() {
        return date19;
    }

    /**
     * Sets the value of the date19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate19(XMLGregorianCalendar value) {
        this.date19 = value;
    }

    /**
     * Gets the value of the date20 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate20() {
        return date20;
    }

    /**
     * Sets the value of the date20 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate20(XMLGregorianCalendar value) {
        this.date20 = value;
    }

}
