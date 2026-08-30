
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalespersonCommissionsKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalespersonCommissionsKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DocumentType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonCommissionsDocumentType"/&gt;
 *         &lt;element name="DocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalespersonCommissionsKey", propOrder = {
    "documentType",
    "documentNumber",
    "sequenceNumber"
})
public class SalespersonCommissionsKey
    extends TransactionKey
{

    @XmlElement(name = "DocumentType", required = true)
    @XmlSchemaType(name = "string")
    protected SalespersonCommissionsDocumentType documentType;
    @XmlElement(name = "DocumentNumber")
    protected String documentNumber;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the documentType property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonCommissionsDocumentType }
     *     
     */
    public SalespersonCommissionsDocumentType getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonCommissionsDocumentType }
     *     
     */
    public void setDocumentType(SalespersonCommissionsDocumentType value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     */
    public void setSequenceNumber(int value) {
        this.sequenceNumber = value;
    }

}
