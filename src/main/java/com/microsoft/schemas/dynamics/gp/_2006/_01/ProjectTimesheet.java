
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectTimesheet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectTimesheet"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectHeaderBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTimesheetKey" minOccurs="0"/&gt;
 *         &lt;element name="ReportingPeriod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReportingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PeriodEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PersonalDataKeeperProxyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="EmployeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="PersonalDataKeeperTimesheetNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectTimesheetLine" minOccurs="0"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectTimesheetDistribution" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectTimesheet", propOrder = {
    "key",
    "reportingPeriod",
    "reportingDate",
    "periodEndDate",
    "personalDataKeeperProxyKey",
    "employeeKey",
    "personalDataKeeperTimesheetNumber",
    "lines",
    "distributions"
})
public class ProjectTimesheet
    extends ProjectHeaderBase
{

    @XmlElement(name = "Key")
    protected ProjectTimesheetKey key;
    @XmlElement(name = "ReportingPeriod", required = true, type = Integer.class, nillable = true)
    protected Integer reportingPeriod;
    @XmlElement(name = "ReportingDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reportingDate;
    @XmlElement(name = "PeriodEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodEndDate;
    @XmlElement(name = "PersonalDataKeeperProxyKey")
    protected EmployeeKey personalDataKeeperProxyKey;
    @XmlElement(name = "EmployeeKey")
    protected EmployeeKey employeeKey;
    @XmlElement(name = "PersonalDataKeeperTimesheetNumber")
    protected String personalDataKeeperTimesheetNumber;
    @XmlElement(name = "Lines")
    protected ArrayOfProjectTimesheetLine lines;
    @XmlElement(name = "Distributions")
    protected ArrayOfProjectTimesheetDistribution distributions;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTimesheetKey }
     *     
     */
    public ProjectTimesheetKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTimesheetKey }
     *     
     */
    public void setKey(ProjectTimesheetKey value) {
        this.key = value;
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
     * Gets the value of the personalDataKeeperProxyKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getPersonalDataKeeperProxyKey() {
        return personalDataKeeperProxyKey;
    }

    /**
     * Sets the value of the personalDataKeeperProxyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setPersonalDataKeeperProxyKey(EmployeeKey value) {
        this.personalDataKeeperProxyKey = value;
    }

    /**
     * Gets the value of the employeeKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getEmployeeKey() {
        return employeeKey;
    }

    /**
     * Sets the value of the employeeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setEmployeeKey(EmployeeKey value) {
        this.employeeKey = value;
    }

    /**
     * Gets the value of the personalDataKeeperTimesheetNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalDataKeeperTimesheetNumber() {
        return personalDataKeeperTimesheetNumber;
    }

    /**
     * Sets the value of the personalDataKeeperTimesheetNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalDataKeeperTimesheetNumber(String value) {
        this.personalDataKeeperTimesheetNumber = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectTimesheetLine }
     *     
     */
    public ArrayOfProjectTimesheetLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectTimesheetLine }
     *     
     */
    public void setLines(ArrayOfProjectTimesheetLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectTimesheetDistribution }
     *     
     */
    public ArrayOfProjectTimesheetDistribution getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectTimesheetDistribution }
     *     
     */
    public void setDistributions(ArrayOfProjectTimesheetDistribution value) {
        this.distributions = value;
    }

}
