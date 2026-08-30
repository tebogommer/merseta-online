
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectChangeOrderFee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectChangeOrderFee"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderFeeKey" minOccurs="0"/&gt;
 *         &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TotalProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalRetainerFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalRetentionFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="VarianceProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceRetainerFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceRetentionFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedRetainerFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedRetentionFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectChangeOrderFeeLine" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectChangeOrderFee", propOrder = {
    "key",
    "contractBeginDate",
    "contractEndDate",
    "totalProjectFeeAmount",
    "totalRetainerFeeAmount",
    "totalRetentionFeeAmount",
    "totalServiceFeeAmount",
    "varianceFeeAmount",
    "sequenceNumber",
    "varianceProjectFeeAmount",
    "varianceRetainerFeeAmount",
    "varianceRetentionFeeAmount",
    "varianceServiceFeeAmount",
    "postedProjectFeeAmount",
    "postedRetainerFeeAmount",
    "postedRetentionFeeAmount",
    "postedServiceFeeAmount",
    "lines"
})
public class ProjectChangeOrderFee
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectChangeOrderFeeKey key;
    @XmlElement(name = "ContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractBeginDate;
    @XmlElement(name = "ContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "TotalProjectFeeAmount")
    protected MoneyAmount totalProjectFeeAmount;
    @XmlElement(name = "TotalRetainerFeeAmount")
    protected MoneyAmount totalRetainerFeeAmount;
    @XmlElement(name = "TotalRetentionFeeAmount")
    protected MoneyAmount totalRetentionFeeAmount;
    @XmlElement(name = "TotalServiceFeeAmount")
    protected MoneyAmount totalServiceFeeAmount;
    @XmlElement(name = "VarianceFeeAmount")
    protected MoneyAmount varianceFeeAmount;
    @XmlElement(name = "SequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer sequenceNumber;
    @XmlElement(name = "VarianceProjectFeeAmount")
    protected MoneyAmount varianceProjectFeeAmount;
    @XmlElement(name = "VarianceRetainerFeeAmount")
    protected MoneyAmount varianceRetainerFeeAmount;
    @XmlElement(name = "VarianceRetentionFeeAmount")
    protected MoneyAmount varianceRetentionFeeAmount;
    @XmlElement(name = "VarianceServiceFeeAmount")
    protected MoneyAmount varianceServiceFeeAmount;
    @XmlElement(name = "PostedProjectFeeAmount")
    protected MoneyAmount postedProjectFeeAmount;
    @XmlElement(name = "PostedRetainerFeeAmount")
    protected MoneyAmount postedRetainerFeeAmount;
    @XmlElement(name = "PostedRetentionFeeAmount")
    protected MoneyAmount postedRetentionFeeAmount;
    @XmlElement(name = "PostedServiceFeeAmount")
    protected MoneyAmount postedServiceFeeAmount;
    @XmlElement(name = "Lines")
    protected ArrayOfProjectChangeOrderFeeLine lines;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderFeeKey }
     *     
     */
    public ProjectChangeOrderFeeKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderFeeKey }
     *     
     */
    public void setKey(ProjectChangeOrderFeeKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the contractBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractBeginDate() {
        return contractBeginDate;
    }

    /**
     * Sets the value of the contractBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractBeginDate(XMLGregorianCalendar value) {
        this.contractBeginDate = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the totalProjectFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalProjectFeeAmount() {
        return totalProjectFeeAmount;
    }

    /**
     * Sets the value of the totalProjectFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalProjectFeeAmount(MoneyAmount value) {
        this.totalProjectFeeAmount = value;
    }

    /**
     * Gets the value of the totalRetainerFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalRetainerFeeAmount() {
        return totalRetainerFeeAmount;
    }

    /**
     * Sets the value of the totalRetainerFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalRetainerFeeAmount(MoneyAmount value) {
        this.totalRetainerFeeAmount = value;
    }

    /**
     * Gets the value of the totalRetentionFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalRetentionFeeAmount() {
        return totalRetentionFeeAmount;
    }

    /**
     * Sets the value of the totalRetentionFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalRetentionFeeAmount(MoneyAmount value) {
        this.totalRetentionFeeAmount = value;
    }

    /**
     * Gets the value of the totalServiceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalServiceFeeAmount() {
        return totalServiceFeeAmount;
    }

    /**
     * Sets the value of the totalServiceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalServiceFeeAmount(MoneyAmount value) {
        this.totalServiceFeeAmount = value;
    }

    /**
     * Gets the value of the varianceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceFeeAmount() {
        return varianceFeeAmount;
    }

    /**
     * Sets the value of the varianceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceFeeAmount(MoneyAmount value) {
        this.varianceFeeAmount = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequenceNumber(Integer value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the varianceProjectFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceProjectFeeAmount() {
        return varianceProjectFeeAmount;
    }

    /**
     * Sets the value of the varianceProjectFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceProjectFeeAmount(MoneyAmount value) {
        this.varianceProjectFeeAmount = value;
    }

    /**
     * Gets the value of the varianceRetainerFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceRetainerFeeAmount() {
        return varianceRetainerFeeAmount;
    }

    /**
     * Sets the value of the varianceRetainerFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceRetainerFeeAmount(MoneyAmount value) {
        this.varianceRetainerFeeAmount = value;
    }

    /**
     * Gets the value of the varianceRetentionFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceRetentionFeeAmount() {
        return varianceRetentionFeeAmount;
    }

    /**
     * Sets the value of the varianceRetentionFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceRetentionFeeAmount(MoneyAmount value) {
        this.varianceRetentionFeeAmount = value;
    }

    /**
     * Gets the value of the varianceServiceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceServiceFeeAmount() {
        return varianceServiceFeeAmount;
    }

    /**
     * Sets the value of the varianceServiceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceServiceFeeAmount(MoneyAmount value) {
        this.varianceServiceFeeAmount = value;
    }

    /**
     * Gets the value of the postedProjectFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedProjectFeeAmount() {
        return postedProjectFeeAmount;
    }

    /**
     * Sets the value of the postedProjectFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedProjectFeeAmount(MoneyAmount value) {
        this.postedProjectFeeAmount = value;
    }

    /**
     * Gets the value of the postedRetainerFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedRetainerFeeAmount() {
        return postedRetainerFeeAmount;
    }

    /**
     * Sets the value of the postedRetainerFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedRetainerFeeAmount(MoneyAmount value) {
        this.postedRetainerFeeAmount = value;
    }

    /**
     * Gets the value of the postedRetentionFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedRetentionFeeAmount() {
        return postedRetentionFeeAmount;
    }

    /**
     * Sets the value of the postedRetentionFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedRetentionFeeAmount(MoneyAmount value) {
        this.postedRetentionFeeAmount = value;
    }

    /**
     * Gets the value of the postedServiceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedServiceFeeAmount() {
        return postedServiceFeeAmount;
    }

    /**
     * Sets the value of the postedServiceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedServiceFeeAmount(MoneyAmount value) {
        this.postedServiceFeeAmount = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectChangeOrderFeeLine }
     *     
     */
    public ArrayOfProjectChangeOrderFeeLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectChangeOrderFeeLine }
     *     
     */
    public void setLines(ArrayOfProjectChangeOrderFeeLine value) {
        this.lines = value;
    }

}
