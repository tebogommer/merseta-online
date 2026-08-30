
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidationResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Errors" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfValidationError" minOccurs="0"/&gt;
 *         &lt;element name="Warnings" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfValidationWarning" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationResult", propOrder = {
    "errors",
    "warnings"
})
public class ValidationResult {

    @XmlElement(name = "Errors")
    protected ArrayOfValidationError errors;
    @XmlElement(name = "Warnings")
    protected ArrayOfValidationWarning warnings;

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfValidationError }
     *     
     */
    public ArrayOfValidationError getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfValidationError }
     *     
     */
    public void setErrors(ArrayOfValidationError value) {
        this.errors = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfValidationWarning }
     *     
     */
    public ArrayOfValidationWarning getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfValidationWarning }
     *     
     */
    public void setWarnings(ArrayOfValidationWarning value) {
        this.warnings = value;
    }

}
