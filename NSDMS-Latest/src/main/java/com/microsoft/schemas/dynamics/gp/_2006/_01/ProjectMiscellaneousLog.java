
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectMiscellaneousLog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectMiscellaneousLog"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectHeaderBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectMiscellaneousLogKey" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MiscellaneousKey" minOccurs="0"/&gt;
 *         &lt;element name="ReportingPeriod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReportingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PeriodEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectMiscellaneousLogLine" minOccurs="0"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectMiscellaneousLogDistribution" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectMiscellaneousLog", propOrder = {
    "key",
    "miscellaneousKey",
    "reportingPeriod",
    "reportingDate",
    "periodEndDate",
    "lines",
    "distributions"
})
public class ProjectMiscellaneousLog
    extends ProjectHeaderBase
{

    @XmlElement(name = "Key")
    protected ProjectMiscellaneousLogKey key;
    @XmlElement(name = "MiscellaneousKey")
    protected MiscellaneousKey miscellaneousKey;
    @XmlElement(name = "ReportingPeriod", required = true, type = Integer.class, nillable = true)
    protected Integer reportingPeriod;
    @XmlElement(name = "ReportingDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reportingDate;
    @XmlElement(name = "PeriodEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodEndDate;
    @XmlElement(name = "Lines")
    protected ArrayOfProjectMiscellaneousLogLine lines;
    @XmlElement(name = "Distributions")
    protected ArrayOfProjectMiscellaneousLogDistribution distributions;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectMiscellaneousLogKey }
     *     
     */
    public ProjectMiscellaneousLogKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectMiscellaneousLogKey }
     *     
     */
    public void setKey(ProjectMiscellaneousLogKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the miscellaneousKey property.
     * 
     * @return
     *     possible object is
     *     {@link MiscellaneousKey }
     *     
     */
    public MiscellaneousKey getMiscellaneousKey() {
        return miscellaneousKey;
    }

    /**
     * Sets the value of the miscellaneousKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link MiscellaneousKey }
     *     
     */
    public void setMiscellaneousKey(MiscellaneousKey value) {
        this.miscellaneousKey = value;
    }

    /**
     * Gets the value of the reportingPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReportingPeriod() {
        return reportingPeriod;
    }

    /**
     * Sets the value of the reportingPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReportingPeriod(Integer value) {
        this.reportingPeriod = value;
    }

    /**
     * Gets the value of the reportingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReportingDate() {
        return reportingDate;
    }

    /**
     * Sets the value of the reportingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReportingDate(XMLGregorianCalendar value) {
        this.reportingDate = value;
    }

    /**
     * Gets the value of the periodEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPeriodEndDate() {
        return periodEndDate;
    }

    /**
     * Sets the value of the periodEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPeriodEndDate(XMLGregorianCalendar value) {
        this.periodEndDate = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectMiscellaneousLogLine }
     *     
     */
    public ArrayOfProjectMiscellaneousLogLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectMiscellaneousLogLine }
     *     
     */
    public void setLines(ArrayOfProjectMiscellaneousLogLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectMiscellaneousLogDistribution }
     *     
     */
    public ArrayOfProjectMiscellaneousLogDistribution getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectMiscellaneousLogDistribution }
     *     
     */
    public void setDistributions(ArrayOfProjectMiscellaneousLogDistribution value) {
        this.distributions = value;
    }

}
