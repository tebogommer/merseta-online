
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectTimesheetLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectTimesheetLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectLineBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTimesheetLineKey" minOccurs="0"/&gt;
 *         &lt;element name="BeginDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EndDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Billing" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBillingFull" minOccurs="0"/&gt;
 *         &lt;element name="PayCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="SalaryPostingType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalaryPostingType"/&gt;
 *         &lt;element name="JobTitleCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}JobTitleKey" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayrollDepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectChangeOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectTimesheetLine", propOrder = {
    "key",
    "beginDateTime",
    "endDateTime",
    "billing",
    "payCodeKey",
    "salaryPostingType",
    "jobTitleCodeKey",
    "departmentCodeKey",
    "projectChangeOrderKey"
})
public class ProjectTimesheetLine
    extends ProjectLineBase
{

    @XmlElement(name = "Key")
    protected ProjectTimesheetLineKey key;
    @XmlElement(name = "BeginDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginDateTime;
    @XmlElement(name = "EndDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDateTime;
    @XmlElement(name = "Billing")
    protected ProjectBillingFull billing;
    @XmlElement(name = "PayCodeKey")
    protected PayCodeKey payCodeKey;
    @XmlElement(name = "SalaryPostingType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalaryPostingType salaryPostingType;
    @XmlElement(name = "JobTitleCodeKey")
    protected JobTitleKey jobTitleCodeKey;
    @XmlElement(name = "DepartmentCodeKey")
    protected PayrollDepartmentKey departmentCodeKey;
    @XmlElement(name = "ProjectChangeOrderKey")
    protected ProjectChangeOrderKey projectChangeOrderKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTimesheetLineKey }
     *     
     */
    public ProjectTimesheetLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTimesheetLineKey }
     *     
     */
    public void setKey(ProjectTimesheetLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the beginDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginDateTime() {
        return beginDateTime;
    }

    /**
     * Sets the value of the beginDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginDateTime(XMLGregorianCalendar value) {
        this.beginDateTime = value;
    }

    /**
     * Gets the value of the endDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets the value of the endDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDateTime(XMLGregorianCalendar value) {
        this.endDateTime = value;
    }

    /**
     * Gets the value of the billing property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBillingFull }
     *     
     */
    public ProjectBillingFull getBilling() {
        return billing;
    }

    /**
     * Sets the value of the billing property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBillingFull }
     *     
     */
    public void setBilling(ProjectBillingFull value) {
        this.billing = value;
    }

    /**
     * Gets the value of the payCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link PayCodeKey }
     *     
     */
    public PayCodeKey getPayCodeKey() {
        return payCodeKey;
    }

    /**
     * Sets the value of the payCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayCodeKey }
     *     
     */
    public void setPayCodeKey(PayCodeKey value) {
        this.payCodeKey = value;
    }

    /**
     * Gets the value of the salaryPostingType property.
     * 
     * @return
     *     possible object is
     *     {@link SalaryPostingType }
     *     
     */
    public SalaryPostingType getSalaryPostingType() {
        return salaryPostingType;
    }

    /**
     * Sets the value of the salaryPostingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalaryPostingType }
     *     
     */
    public void setSalaryPostingType(SalaryPostingType value) {
        this.salaryPostingType = value;
    }

    /**
     * Gets the value of the jobTitleCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link JobTitleKey }
     *     
     */
    public JobTitleKey getJobTitleCodeKey() {
        return jobTitleCodeKey;
    }

    /**
     * Sets the value of the jobTitleCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobTitleKey }
     *     
     */
    public void setJobTitleCodeKey(JobTitleKey value) {
        this.jobTitleCodeKey = value;
    }

    /**
     * Gets the value of the departmentCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link PayrollDepartmentKey }
     *     
     */
    public PayrollDepartmentKey getDepartmentCodeKey() {
        return departmentCodeKey;
    }

    /**
     * Sets the value of the departmentCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayrollDepartmentKey }
     *     
     */
    public void setDepartmentCodeKey(PayrollDepartmentKey value) {
        this.departmentCodeKey = value;
    }

    /**
     * Gets the value of the projectChangeOrderKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderKey }
     *     
     */
    public ProjectChangeOrderKey getProjectChangeOrderKey() {
        return projectChangeOrderKey;
    }

    /**
     * Sets the value of the projectChangeOrderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderKey }
     *     
     */
    public void setProjectChangeOrderKey(ProjectChangeOrderKey value) {
        this.projectChangeOrderKey = value;
    }

}
