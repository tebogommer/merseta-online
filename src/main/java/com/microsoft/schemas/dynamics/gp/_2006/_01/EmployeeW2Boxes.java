
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeW2Boxes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeW2Boxes"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="W2BoxNumber_1" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="W2BoxNumber_2" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="W2BoxNumber_3" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="W2BoxNumber_4" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="W2BoxLabel_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="W2BoxLabel_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="W2BoxLabel_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="W2BoxLabel_4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeW2Boxes", propOrder = {
    "w2BoxNumber1",
    "w2BoxNumber2",
    "w2BoxNumber3",
    "w2BoxNumber4",
    "w2BoxLabel1",
    "w2BoxLabel2",
    "w2BoxLabel3",
    "w2BoxLabel4"
})
public class EmployeeW2Boxes {

    @XmlElement(name = "W2BoxNumber_1", required = true, type = Integer.class, nillable = true)
    protected Integer w2BoxNumber1;
    @XmlElement(name = "W2BoxNumber_2", required = true, type = Integer.class, nillable = true)
    protected Integer w2BoxNumber2;
    @XmlElement(name = "W2BoxNumber_3", required = true, type = Integer.class, nillable = true)
    protected Integer w2BoxNumber3;
    @XmlElement(name = "W2BoxNumber_4", required = true, type = Integer.class, nillable = true)
    protected Integer w2BoxNumber4;
    @XmlElement(name = "W2BoxLabel_1")
    protected String w2BoxLabel1;
    @XmlElement(name = "W2BoxLabel_2")
    protected String w2BoxLabel2;
    @XmlElement(name = "W2BoxLabel_3")
    protected String w2BoxLabel3;
    @XmlElement(name = "W2BoxLabel_4")
    protected String w2BoxLabel4;

    /**
     * Gets the value of the w2BoxNumber1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getW2BoxNumber1() {
        return w2BoxNumber1;
    }

    /**
     * Sets the value of the w2BoxNumber1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setW2BoxNumber1(Integer value) {
        this.w2BoxNumber1 = value;
    }

    /**
     * Gets the value of the w2BoxNumber2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getW2BoxNumber2() {
        return w2BoxNumber2;
    }

    /**
     * Sets the value of the w2BoxNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setW2BoxNumber2(Integer value) {
        this.w2BoxNumber2 = value;
    }

    /**
     * Gets the value of the w2BoxNumber3 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getW2BoxNumber3() {
        return w2BoxNumber3;
    }

    /**
     * Sets the value of the w2BoxNumber3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setW2BoxNumber3(Integer value) {
        this.w2BoxNumber3 = value;
    }

    /**
     * Gets the value of the w2BoxNumber4 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getW2BoxNumber4() {
        return w2BoxNumber4;
    }

    /**
     * Sets the value of the w2BoxNumber4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setW2BoxNumber4(Integer value) {
        this.w2BoxNumber4 = value;
    }

    /**
     * Gets the value of the w2BoxLabel1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getW2BoxLabel1() {
        return w2BoxLabel1;
    }

    /**
     * Sets the value of the w2BoxLabel1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setW2BoxLabel1(String value) {
        this.w2BoxLabel1 = value;
    }

    /**
     * Gets the value of the w2BoxLabel2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getW2BoxLabel2() {
        return w2BoxLabel2;
    }

    /**
     * Sets the value of the w2BoxLabel2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setW2BoxLabel2(String value) {
        this.w2BoxLabel2 = value;
    }

    /**
     * Gets the value of the w2BoxLabel3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getW2BoxLabel3() {
        return w2BoxLabel3;
    }

    /**
     * Sets the value of the w2BoxLabel3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setW2BoxLabel3(String value) {
        this.w2BoxLabel3 = value;
    }

    /**
     * Gets the value of the w2BoxLabel4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getW2BoxLabel4() {
        return w2BoxLabel4;
    }

    /**
     * Sets the value of the w2BoxLabel4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setW2BoxLabel4(String value) {
        this.w2BoxLabel4 = value;
    }

}
