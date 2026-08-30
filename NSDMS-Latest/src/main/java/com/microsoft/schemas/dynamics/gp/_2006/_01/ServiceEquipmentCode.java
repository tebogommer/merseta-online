
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEquipmentCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEquipmentCode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="ProblemCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallLineProblemCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="CauseCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallLineCauseCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="RepairCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallLineRepairCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="EquipmentLineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEquipmentCode", propOrder = {
    "serialNumber",
    "itemKey",
    "problemCodeKey",
    "causeCodeKey",
    "repairCodeKey",
    "key",
    "equipmentLineSequenceNumber"
})
@XmlSeeAlso({
    ServiceCallEquipmentCode.class
})
public class ServiceEquipmentCode
    extends BusinessObject
{

    @XmlElement(name = "SerialNumber")
    protected String serialNumber;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "ProblemCodeKey")
    protected ServiceCallLineProblemCodeKey problemCodeKey;
    @XmlElement(name = "CauseCodeKey")
    protected ServiceCallLineCauseCodeKey causeCodeKey;
    @XmlElement(name = "RepairCodeKey")
    protected ServiceCallLineRepairCodeKey repairCodeKey;
    @XmlElement(name = "Key")
    protected ServiceLineKey key;
    @XmlElement(name = "EquipmentLineSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer equipmentLineSequenceNumber;

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setItemKey(ItemKey value) {
        this.itemKey = value;
    }

    /**
     * Gets the value of the problemCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallLineProblemCodeKey }
     *     
     */
    public ServiceCallLineProblemCodeKey getProblemCodeKey() {
        return problemCodeKey;
    }

    /**
     * Sets the value of the problemCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallLineProblemCodeKey }
     *     
     */
    public void setProblemCodeKey(ServiceCallLineProblemCodeKey value) {
        this.problemCodeKey = value;
    }

    /**
     * Gets the value of the causeCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallLineCauseCodeKey }
     *     
     */
    public ServiceCallLineCauseCodeKey getCauseCodeKey() {
        return causeCodeKey;
    }

    /**
     * Sets the value of the causeCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallLineCauseCodeKey }
     *     
     */
    public void setCauseCodeKey(ServiceCallLineCauseCodeKey value) {
        this.causeCodeKey = value;
    }

    /**
     * Gets the value of the repairCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallLineRepairCodeKey }
     *     
     */
    public ServiceCallLineRepairCodeKey getRepairCodeKey() {
        return repairCodeKey;
    }

    /**
     * Sets the value of the repairCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallLineRepairCodeKey }
     *     
     */
    public void setRepairCodeKey(ServiceCallLineRepairCodeKey value) {
        this.repairCodeKey = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setKey(ServiceLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the equipmentLineSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEquipmentLineSequenceNumber() {
        return equipmentLineSequenceNumber;
    }

    /**
     * Sets the value of the equipmentLineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEquipmentLineSequenceNumber(Integer value) {
        this.equipmentLineSequenceNumber = value;
    }

}
