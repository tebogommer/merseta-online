
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesQuote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesQuote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsRepeating" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DocumentFrequency" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Frequency"/&gt;
 *         &lt;element name="TimesToRepeat" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DaysToIncrement" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="WorkflowPriority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkflowPriority"/&gt;
 *         &lt;element name="IsProspect" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesQuoteLine" minOccurs="0"/&gt;
 *         &lt;element name="Workflows" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfWorkflow" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesQuote", propOrder = {
    "expirationDate",
    "isRepeating",
    "documentFrequency",
    "timesToRepeat",
    "daysToIncrement",
    "workflowPriority",
    "isProspect",
    "lines",
    "workflows"
})
public class SalesQuote
    extends SalesDocument
{

    @XmlElement(name = "ExpirationDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(name = "IsRepeating", required = true, type = Boolean.class, nillable = true)
    protected Boolean isRepeating;
    @XmlElement(name = "DocumentFrequency", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected Frequency documentFrequency;
    @XmlElement(name = "TimesToRepeat", required = true, type = Integer.class, nillable = true)
    protected Integer timesToRepeat;
    @XmlElement(name = "DaysToIncrement", required = true, type = Integer.class, nillable = true)
    protected Integer daysToIncrement;
    @XmlElement(name = "WorkflowPriority", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected WorkflowPriority workflowPriority;
    @XmlElement(name = "IsProspect", required = true, type = Boolean.class, nillable = true)
    protected Boolean isProspect;
    @XmlElement(name = "Lines")
    protected ArrayOfSalesQuoteLine lines;
    @XmlElement(name = "Workflows")
    protected ArrayOfWorkflow workflows;

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the isRepeating property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRepeating() {
        return isRepeating;
    }

    /**
     * Sets the value of the isRepeating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRepeating(Boolean value) {
        this.isRepeating = value;
    }

    /**
     * Gets the value of the documentFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link Frequency }
     *     
     */
    public Frequency getDocumentFrequency() {
        return documentFrequency;
    }

    /**
     * Sets the value of the documentFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Frequency }
     *     
     */
    public void setDocumentFrequency(Frequency value) {
        this.documentFrequency = value;
    }

    /**
     * Gets the value of the timesToRepeat property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimesToRepeat() {
        return timesToRepeat;
    }

    /**
     * Sets the value of the timesToRepeat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimesToRepeat(Integer value) {
        this.timesToRepeat = value;
    }

    /**
     * Gets the value of the daysToIncrement property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDaysToIncrement() {
        return daysToIncrement;
    }

    /**
     * Sets the value of the daysToIncrement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDaysToIncrement(Integer value) {
        this.daysToIncrement = value;
    }

    /**
     * Gets the value of the workflowPriority property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowPriority }
     *     
     */
    public WorkflowPriority getWorkflowPriority() {
        return workflowPriority;
    }

    /**
     * Sets the value of the workflowPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowPriority }
     *     
     */
    public void setWorkflowPriority(WorkflowPriority value) {
        this.workflowPriority = value;
    }

    /**
     * Gets the value of the isProspect property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsProspect() {
        return isProspect;
    }

    /**
     * Sets the value of the isProspect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsProspect(Boolean value) {
        this.isProspect = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesQuoteLine }
     *     
     */
    public ArrayOfSalesQuoteLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesQuoteLine }
     *     
     */
    public void setLines(ArrayOfSalesQuoteLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the workflows property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWorkflow }
     *     
     */
    public ArrayOfWorkflow getWorkflows() {
        return workflows;
    }

    /**
     * Sets the value of the workflows property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWorkflow }
     *     
     */
    public void setWorkflows(ArrayOfWorkflow value) {
        this.workflows = value;
    }

}
