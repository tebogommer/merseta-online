
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for GLTransactionLineKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLTransactionLineKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TransactionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransactionKey" minOccurs="0"/&gt;
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
@XmlType(name = "GLTransactionLineKey", propOrder = {
    "transactionKey",
    "sequenceNumber"
})
public class GLTransactionLineKey
    extends Key
{

    @XmlElement(name = "TransactionKey")
    protected GLTransactionKey transactionKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    
    
    
    public GLTransactionLineKey(GLTransactionKey transactionKey, int sequenceNumber) {
		super();
		this.transactionKey = transactionKey;
		this.sequenceNumber = sequenceNumber;
	}

	public GLTransactionLineKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * Gets the value of the transactionKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLTransactionKey }
     *     
     */
    public GLTransactionKey getTransactionKey() {
        return transactionKey;
    }

    /**
     * Sets the value of the transactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLTransactionKey }
     *     
     */
    public void setTransactionKey(GLTransactionKey value) {
        this.transactionKey = value;
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
