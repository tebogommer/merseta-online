
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.microsoft.schemas.dynamics._2006._01.Context;


/**
 * <p>Java class for LoggedExceptionData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoggedExceptionData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LogId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="ExceptionType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessExceptionType"/&gt;
 *         &lt;element name="ExceptionDetail" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ExceptionInformation" minOccurs="0"/&gt;
 *         &lt;element name="ValidationResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ValidationResult" minOccurs="0"/&gt;
 *         &lt;element name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BusinessObjectType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="User" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Context" type="{http://schemas.microsoft.com/dynamics/2006/01}Context" minOccurs="0"/&gt;
 *         &lt;element name="RequestXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "LoggedExceptionData", propOrder = {
    "logId",
    "exceptionType",
    "exceptionDetail",
    "validationResult",
    "serviceType",
    "businessObjectType",
    "user",
    "context",
    "requestXml",
    "loggedDate"
})
public class LoggedExceptionData {

    @XmlElement(name = "LogId", required = true)
    protected String logId;
    @XmlElement(name = "ExceptionType", required = true)
    @XmlSchemaType(name = "string")
    protected BusinessExceptionType exceptionType;
    @XmlElement(name = "ExceptionDetail")
    protected ExceptionInformation exceptionDetail;
    @XmlElement(name = "ValidationResult")
    protected ValidationResult validationResult;
    @XmlElement(name = "ServiceType")
    protected String serviceType;
    @XmlElement(name = "BusinessObjectType")
    protected String businessObjectType;
    @XmlElement(name = "User")
    protected String user;
    @XmlElement(name = "Context")
    protected Context context;
    @XmlElement(name = "RequestXml")
    protected String requestXml;
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
     * Gets the value of the exceptionType property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessExceptionType }
     *     
     */
    public BusinessExceptionType getExceptionType() {
        return exceptionType;
    }

    /**
     * Sets the value of the exceptionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessExceptionType }
     *     
     */
    public void setExceptionType(BusinessExceptionType value) {
        this.exceptionType = value;
    }

    /**
     * Gets the value of the exceptionDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ExceptionInformation }
     *     
     */
    public ExceptionInformation getExceptionDetail() {
        return exceptionDetail;
    }

    /**
     * Sets the value of the exceptionDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExceptionInformation }
     *     
     */
    public void setExceptionDetail(ExceptionInformation value) {
        this.exceptionDetail = value;
    }

    /**
     * Gets the value of the validationResult property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationResult }
     *     
     */
    public ValidationResult getValidationResult() {
        return validationResult;
    }

    /**
     * Sets the value of the validationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationResult }
     *     
     */
    public void setValidationResult(ValidationResult value) {
        this.validationResult = value;
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
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link Context }
     *     
     */
    public Context getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link Context }
     *     
     */
    public void setContext(Context value) {
        this.context = value;
    }

    /**
     * Gets the value of the requestXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestXml() {
        return requestXml;
    }

    /**
     * Sets the value of the requestXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestXml(String value) {
        this.requestXml = value;
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
