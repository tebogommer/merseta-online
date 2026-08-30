
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExceptionInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExceptionInformation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ExceptionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HelpLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InnerException" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ExceptionInformation" minOccurs="0"/&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StackTrace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TargetSite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExceptionInformation", propOrder = {
    "exceptionType",
    "helpLink",
    "innerException",
    "message",
    "source",
    "stackTrace",
    "targetSite"
})
public class ExceptionInformation {

    @XmlElement(name = "ExceptionType")
    protected String exceptionType;
    @XmlElement(name = "HelpLink")
    protected String helpLink;
    @XmlElement(name = "InnerException")
    protected ExceptionInformation innerException;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "Source")
    protected String source;
    @XmlElement(name = "StackTrace")
    protected String stackTrace;
    @XmlElement(name = "TargetSite")
    protected String targetSite;

    /**
     * Gets the value of the exceptionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExceptionType() {
        return exceptionType;
    }

    /**
     * Sets the value of the exceptionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExceptionType(String value) {
        this.exceptionType = value;
    }

    /**
     * Gets the value of the helpLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHelpLink() {
        return helpLink;
    }

    /**
     * Sets the value of the helpLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHelpLink(String value) {
        this.helpLink = value;
    }

    /**
     * Gets the value of the innerException property.
     * 
     * @return
     *     possible object is
     *     {@link ExceptionInformation }
     *     
     */
    public ExceptionInformation getInnerException() {
        return innerException;
    }

    /**
     * Sets the value of the innerException property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExceptionInformation }
     *     
     */
    public void setInnerException(ExceptionInformation value) {
        this.innerException = value;
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
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the stackTrace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * Sets the value of the stackTrace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStackTrace(String value) {
        this.stackTrace = value;
    }

    /**
     * Gets the value of the targetSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetSite() {
        return targetSite;
    }

    /**
     * Sets the value of the targetSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetSite(String value) {
        this.targetSite = value;
    }

}
