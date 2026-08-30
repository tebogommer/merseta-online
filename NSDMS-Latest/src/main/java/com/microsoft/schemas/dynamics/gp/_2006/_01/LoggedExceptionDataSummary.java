
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for LoggedExceptionDataSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoggedExceptionDataSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LogId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="User" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BusinessExceptionType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessExceptionType"/&gt;
 *         &lt;element name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BusinessObjectType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LoggedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoggedExceptionDataSummary", propOrder = {
    "logId",
    "message",
    "user",
    "companyName",
    "businessExceptionType",
    "serviceType",
    "businessObjectType",
    "loggedDate"
})
public class LoggedExceptionDataSummary {

    @XmlElement(name = "LogId", required = true)
    protected String logId;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "User")
    protected String user;
    @XmlElement(name = "CompanyName")
    protected String companyName;
    @XmlElement(name = "BusinessExceptionType", required = true)
    @XmlSchemaType(name = "string")
    protected BusinessExceptionType businessExceptionType;
    @XmlElement(name = "ServiceType")
    protected String serviceType;
    @XmlElement(name = "BusinessObjectType")
    protected String businessObjectType;
    @XmlElement(name = "LoggedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar loggedDate;

    /**
     * Gets the value of the logId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogId() {
        return logId;
    }

    /**
     * Sets the value of the logId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogId(String value) {
        this.logId = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the businessExceptionType property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessExceptionType }
     *     
     */
    public BusinessExceptionType getBusinessExceptionType() {
        return businessExceptionType;
    }

    /**
     * Sets the value of the businessExceptionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessExceptionType }
     *     
     */
    public void setBusinessExceptionType(BusinessExceptionType value) {
        this.businessExceptionType = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the businessObjectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessObjectType() {
        return businessObjectType;
    }

    /**
     * Sets the value of the businessObjectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessObjectType(String value) {
        this.businessObjectType = value;
    }

    /**
     * Gets the value of the loggedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoggedDate() {
        return loggedDate;
    }

    /**
     * Sets the value of the loggedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoggedDate(XMLGregorianCalendar value) {
        this.loggedDate = value;
    }

}
