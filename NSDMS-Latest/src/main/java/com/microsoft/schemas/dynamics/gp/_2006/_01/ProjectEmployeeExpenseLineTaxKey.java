
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for ProjectEmployeeExpenseLineTaxKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectEmployeeExpenseLineTaxKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectEmployeeExpenseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectEmployeeExpenseKey" minOccurs="0"/&gt;
 *         &lt;element name="TransactionType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTransactionType"/&gt;
 *         &lt;element name="LineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TaxDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectEmployeeExpenseLineTaxKey", propOrder = {
    "projectEmployeeExpenseKey",
    "transactionType",
    "lineSequenceNumber",
    "taxDetailKey"
})
public class ProjectEmployeeExpenseLineTaxKey
    extends Key
{

    @XmlElement(name = "ProjectEmployeeExpenseKey")
    protected ProjectEmployeeExpenseKey projectEmployeeExpenseKey;
    @XmlElement(name = "TransactionType", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectTransactionType transactionType;
    @XmlElement(name = "LineSequenceNumber")
    protected int lineSequenceNumber;
    @XmlElement(name = "TaxDetailKey")
    protected TaxDetailKey taxDetailKey;

    /**
     * Gets the value of the projectEmployeeExpenseKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectEmployeeExpenseKey }
     *     
     */
    public ProjectEmployeeExpenseKey getProjectEmployeeExpenseKey() {
        return projectEmployeeExpenseKey;
    }

    /**
     * Sets the value of the projectEmployeeExpenseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectEmployeeExpenseKey }
     *     
     */
    public void setProjectEmployeeExpenseKey(ProjectEmployeeExpenseKey value) {
        this.projectEmployeeExpenseKey = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTransactionType }
     *     
     */
    public ProjectTransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTransactionType }
     *     
     */
    public void setTransactionType(ProjectTransactionType value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the lineSequenceNumber property.
     * 
     */
    public int getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    /**
     * Sets the value of the lineSequenceNumber property.
     * 
     */
    public void setLineSequenceNumber(int value) {
        this.lineSequenceNumber = value;
    }

    /**
     * Gets the value of the taxDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailKey }
     *     
     */
    public TaxDetailKey getTaxDetailKey() {
        return taxDetailKey;
    }

    /**
     * Sets the value of the taxDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailKey }
     *     
     */
    public void setTaxDetailKey(TaxDetailKey value) {
        this.taxDetailKey = value;
    }

}
