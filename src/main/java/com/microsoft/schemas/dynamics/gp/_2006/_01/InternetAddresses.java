
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InternetAddresses complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternetAddresses"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MessengerAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EmailToAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EmailCcAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EmailBccAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetField8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AdditionalInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternetAddresses", propOrder = {
    "messengerAddress",
    "emailToAddress",
    "emailCcAddress",
    "emailBccAddress",
    "internetField1",
    "internetField2",
    "internetField3",
    "internetField4",
    "internetField5",
    "internetField6",
    "internetField7",
    "internetField8",
    "additionalInformation"
})
public class InternetAddresses {

    @XmlElement(name = "MessengerAddress")
    protected String messengerAddress;
    @XmlElement(name = "EmailToAddress")
    protected String emailToAddress;
    @XmlElement(name = "EmailCcAddress")
    protected String emailCcAddress;
    @XmlElement(name = "EmailBccAddress")
    protected String emailBccAddress;
    @XmlElement(name = "InternetField1")
    protected String internetField1;
    @XmlElement(name = "InternetField2")
    protected String internetField2;
    @XmlElement(name = "InternetField3")
    protected String internetField3;
    @XmlElement(name = "InternetField4")
    protected String internetField4;
    @XmlElement(name = "InternetField5")
    protected String internetField5;
    @XmlElement(name = "InternetField6")
    protected String internetField6;
    @XmlElement(name = "InternetField7")
    protected String internetField7;
    @XmlElement(name = "InternetField8")
    protected String internetField8;
    @XmlElement(name = "AdditionalInformation")
    protected String additionalInformation;

    /**
     * Gets the value of the messengerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessengerAddress() {
        return messengerAddress;
    }

    /**
     * Sets the value of the messengerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessengerAddress(String value) {
        this.messengerAddress = value;
    }

    /**
     * Gets the value of the emailToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailToAddress() {
        return emailToAddress;
    }

    /**
     * Sets the value of the emailToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailToAddress(String value) {
        this.emailToAddress = value;
    }

    /**
     * Gets the value of the emailCcAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailCcAddress() {
        return emailCcAddress;
    }

    /**
     * Sets the value of the emailCcAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailCcAddress(String value) {
        this.emailCcAddress = value;
    }

    /**
     * Gets the value of the emailBccAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailBccAddress() {
        return emailBccAddress;
    }

    /**
     * Sets the value of the emailBccAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailBccAddress(String value) {
        this.emailBccAddress = value;
    }

    /**
     * Gets the value of the internetField1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField1() {
        return internetField1;
    }

    /**
     * Sets the value of the internetField1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField1(String value) {
        this.internetField1 = value;
    }

    /**
     * Gets the value of the internetField2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField2() {
        return internetField2;
    }

    /**
     * Sets the value of the internetField2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField2(String value) {
        this.internetField2 = value;
    }

    /**
     * Gets the value of the internetField3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField3() {
        return internetField3;
    }

    /**
     * Sets the value of the internetField3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField3(String value) {
        this.internetField3 = value;
    }

    /**
     * Gets the value of the internetField4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField4() {
        return internetField4;
    }

    /**
     * Sets the value of the internetField4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField4(String value) {
        this.internetField4 = value;
    }

    /**
     * Gets the value of the internetField5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField5() {
        return internetField5;
    }

    /**
     * Sets the value of the internetField5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField5(String value) {
        this.internetField5 = value;
    }

    /**
     * Gets the value of the internetField6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField6() {
        return internetField6;
    }

    /**
     * Sets the value of the internetField6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField6(String value) {
        this.internetField6 = value;
    }

    /**
     * Gets the value of the internetField7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField7() {
        return internetField7;
    }

    /**
     * Sets the value of the internetField7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField7(String value) {
        this.internetField7 = value;
    }

    /**
     * Gets the value of the internetField8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetField8() {
        return internetField8;
    }

    /**
     * Sets the value of the internetField8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetField8(String value) {
        this.internetField8 = value;
    }

    /**
     * Gets the value of the additionalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the value of the additionalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInformation(String value) {
        this.additionalInformation = value;
    }

}
