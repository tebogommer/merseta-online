
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GLTransactionKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLTransactionKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="JournalId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLTransactionKey", propOrder = {
    "journalId",
    "date"
})
@XmlSeeAlso({
    GLTransactionPostedKey.class
})
public class GLTransactionKey
    extends TransactionKey
{

    @XmlElement(name = "JournalId")
    protected Integer journalId;
    @XmlElement(name = "Date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;

    
    
    public GLTransactionKey() {
		super();
		// TODO Auto-generated constructor stub
	}

    
	public GLTransactionKey(Integer journalId, XMLGregorianCalendar date) {
		super();
		this.journalId = journalId;
		this.date = date;
	}


	/**
     * Gets the value of the journalId property.
     * 
     */
    public Integer getJournalId() {
        return journalId;
    }

    /**
     * Sets the value of the journalId property.
     * 
     */
    public void setJournalId(Integer value) {
        this.journalId = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

}
