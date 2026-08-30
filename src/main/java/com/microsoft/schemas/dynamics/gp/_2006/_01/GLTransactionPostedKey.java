
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLTransactionPostedKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLTransactionPostedKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RecurringTransactionSequence" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="FiscalYear" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLTransactionPostedKey", propOrder = {
    "recurringTransactionSequence",
    "fiscalYear"
})
public class GLTransactionPostedKey
    extends GLTransactionKey
{

    @XmlElement(name = "RecurringTransactionSequence")
    protected long recurringTransactionSequence;
    @XmlElement(name = "FiscalYear")
    protected int fiscalYear;

    /**
     * Gets the value of the recurringTransactionSequence property.
     * 
     */
    public long getRecurringTransactionSequence() {
        return recurringTransactionSequence;
    }

    /**
     * Sets the value of the recurringTransactionSequence property.
     * 
     */
    public void setRecurringTransactionSequence(long value) {
        this.recurringTransactionSequence = value;
    }

    /**
     * Gets the value of the fiscalYear property.
     * 
     */
    public int getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Sets the value of the fiscalYear property.
     * 
     */
    public void setFiscalYear(int value) {
        this.fiscalYear = value;
    }

}
