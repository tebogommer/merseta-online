
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceEquipment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEquipment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentKey" minOccurs="0"/&gt;
 *         &lt;element name="Reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StatusKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceStatusKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="AddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="Address" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentAddress" minOccurs="0"/&gt;
 *         &lt;element name="InstallDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ShippedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RegisterDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastPreventiveMaintenanceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastServiceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TechnicianKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTechnicianKey" minOccurs="0"/&gt;
 *         &lt;element name="OfficeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}OfficeKey" minOccurs="0"/&gt;
 *         &lt;element name="ServiceAreaKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceAreaKey" minOccurs="0"/&gt;
 *         &lt;element name="TimeZoneKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTimeZoneKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorWarrantyCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceWarrantyCode" minOccurs="0"/&gt;
 *         &lt;element name="SellerWarrantyCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceWarrantyCode" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PreventiveMaintenanceMonth" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ISO8061Month"/&gt;
 *         &lt;element name="PreventiveMaintenanceDay" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LastCalculatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="UserDefined01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="AssetTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Readings" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceEquipmentReading" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEquipment", propOrder = {
    "key",
    "reference",
    "version",
    "statusKey",
    "customerKey",
    "addressKey",
    "address",
    "installDate",
    "shippedDate",
    "registerDate",
    "lastPreventiveMaintenanceDate",
    "lastServiceDate",
    "technicianKey",
    "officeKey",
    "serviceAreaKey",
    "timeZoneKey",
    "vendorKey",
    "vendorWarrantyCode",
    "sellerWarrantyCode",
    "note",
    "preventiveMaintenanceMonth",
    "preventiveMaintenanceDay",
    "lastCalculatedDate",
    "userDefined01",
    "userDefined02",
    "userDefined03",
    "userDefined04",
    "userDefined05",
    "serialNumber",
    "quantity",
    "assetTag",
    "readings"
})
public class ServiceEquipment
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ServiceEquipmentKey key;
    @XmlElement(name = "Reference")
    protected String reference;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "StatusKey")
    protected ServiceStatusKey statusKey;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "AddressKey")
    protected AddressKey addressKey;
    @XmlElement(name = "Address")
    protected ServiceEquipmentAddress address;
    @XmlElement(name = "InstallDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar installDate;
    @XmlElement(name = "ShippedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar shippedDate;
    @XmlElement(name = "RegisterDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar registerDate;
    @XmlElement(name = "LastPreventiveMaintenanceDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastPreventiveMaintenanceDate;
    @XmlElement(name = "LastServiceDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastServiceDate;
    @XmlElement(name = "TechnicianKey")
    protected ServiceTechnicianKey technicianKey;
    @XmlElement(name = "OfficeKey")
    protected OfficeKey officeKey;
    @XmlElement(name = "ServiceAreaKey")
    protected ServiceAreaKey serviceAreaKey;
    @XmlElement(name = "TimeZoneKey")
    protected ServiceTimeZoneKey timeZoneKey;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;
    @XmlElement(name = "VendorWarrantyCode")
    protected ServiceWarrantyCode vendorWarrantyCode;
    @XmlElement(name = "SellerWarrantyCode")
    protected ServiceWarrantyCode sellerWarrantyCode;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "PreventiveMaintenanceMonth", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ISO8061Month preventiveMaintenanceMonth;
    @XmlElement(name = "PreventiveMaintenanceDay", required = true, type = Integer.class, nillable = true)
    protected Integer preventiveMaintenanceDay;
    @XmlElement(name = "LastCalculatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastCalculatedDate;
    @XmlElement(name = "UserDefined01")
    protected String userDefined01;
    @XmlElement(name = "UserDefined02")
    protected String userDefined02;
    @XmlElement(name = "UserDefined03")
    protected String userDefined03;
    @XmlElement(name = "UserDefined04")
    protected String userDefined04;
    @XmlElement(name = "UserDefined05")
    protected String userDefined05;
    @XmlElement(name = "SerialNumber")
    protected String serialNumber;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "AssetTag")
    protected String assetTag;
    @XmlElement(name = "Readings")
    protected ArrayOfServiceEquipmentReading readings;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public ServiceEquipmentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public void setKey(ServiceEquipmentKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the statusKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStatusKey }
     *     
     */
    public ServiceStatusKey getStatusKey() {
        return statusKey;
    }

    /**
     * Sets the value of the statusKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStatusKey }
     *     
     */
    public void setStatusKey(ServiceStatusKey value) {
        this.statusKey = value;
    }

    /**
     * Gets the value of the customerKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCustomerKey() {
        return customerKey;
    }

    /**
     * Sets the value of the customerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCustomerKey(CustomerKey value) {
        this.customerKey = value;
    }

    /**
     * Gets the value of the addressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getAddressKey() {
        return addressKey;
    }

    /**
     * Sets the value of the addressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setAddressKey(AddressKey value) {
        this.addressKey = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentAddress }
     *     
     */
    public ServiceEquipmentAddress getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentAddress }
     *     
     */
    public void setAddress(ServiceEquipmentAddress value) {
        this.address = value;
    }

    /**
     * Gets the value of the installDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInstallDate() {
        return installDate;
    }

    /**
     * Sets the value of the installDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInstallDate(XMLGregorianCalendar value) {
        this.installDate = value;
    }

    /**
     * Gets the value of the shippedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getShippedDate() {
        return shippedDate;
    }

    /**
     * Sets the value of the shippedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setShippedDate(XMLGregorianCalendar value) {
        this.shippedDate = value;
    }

    /**
     * Gets the value of the registerDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRegisterDate() {
        return registerDate;
    }

    /**
     * Sets the value of the registerDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRegisterDate(XMLGregorianCalendar value) {
        this.registerDate = value;
    }

    /**
     * Gets the value of the lastPreventiveMaintenanceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPreventiveMaintenanceDate() {
        return lastPreventiveMaintenanceDate;
    }

    /**
     * Sets the value of the lastPreventiveMaintenanceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPreventiveMaintenanceDate(XMLGregorianCalendar value) {
        this.lastPreventiveMaintenanceDate = value;
    }

    /**
     * Gets the value of the lastServiceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastServiceDate() {
        return lastServiceDate;
    }

    /**
     * Sets the value of the lastServiceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastServiceDate(XMLGregorianCalendar value) {
        this.lastServiceDate = value;
    }

    /**
     * Gets the value of the technicianKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTechnicianKey }
     *     
     */
    public ServiceTechnicianKey getTechnicianKey() {
        return technicianKey;
    }

    /**
     * Sets the value of the technicianKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTechnicianKey }
     *     
     */
    public void setTechnicianKey(ServiceTechnicianKey value) {
        this.technicianKey = value;
    }

    /**
     * Gets the value of the officeKey property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeKey }
     *     
     */
    public OfficeKey getOfficeKey() {
        return officeKey;
    }

    /**
     * Sets the value of the officeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeKey }
     *     
     */
    public void setOfficeKey(OfficeKey value) {
        this.officeKey = value;
    }

    /**
     * Gets the value of the serviceAreaKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceAreaKey }
     *     
     */
    public ServiceAreaKey getServiceAreaKey() {
        return serviceAreaKey;
    }

    /**
     * Sets the value of the serviceAreaKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceAreaKey }
     *     
     */
    public void setServiceAreaKey(ServiceAreaKey value) {
        this.serviceAreaKey = value;
    }

    /**
     * Gets the value of the timeZoneKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTimeZoneKey }
     *     
     */
    public ServiceTimeZoneKey getTimeZoneKey() {
        return timeZoneKey;
    }

    /**
     * Sets the value of the timeZoneKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTimeZoneKey }
     *     
     */
    public void setTimeZoneKey(ServiceTimeZoneKey value) {
        this.timeZoneKey = value;
    }

    /**
     * Gets the value of the vendorKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getVendorKey() {
        return vendorKey;
    }

    /**
     * Sets the value of the vendorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setVendorKey(VendorKey value) {
        this.vendorKey = value;
    }

    /**
     * Gets the value of the vendorWarrantyCode property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceWarrantyCode }
     *     
     */
    public ServiceWarrantyCode getVendorWarrantyCode() {
        return vendorWarrantyCode;
    }

    /**
     * Sets the value of the vendorWarrantyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceWarrantyCode }
     *     
     */
    public void setVendorWarrantyCode(ServiceWarrantyCode value) {
        this.vendorWarrantyCode = value;
    }

    /**
     * Gets the value of the sellerWarrantyCode property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceWarrantyCode }
     *     
     */
    public ServiceWarrantyCode getSellerWarrantyCode() {
        return sellerWarrantyCode;
    }

    /**
     * Sets the value of the sellerWarrantyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceWarrantyCode }
     *     
     */
    public void setSellerWarrantyCode(ServiceWarrantyCode value) {
        this.sellerWarrantyCode = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the preventiveMaintenanceMonth property.
     * 
     * @return
     *     possible object is
     *     {@link ISO8061Month }
     *     
     */
    public ISO8061Month getPreventiveMaintenanceMonth() {
        return preventiveMaintenanceMonth;
    }

    /**
     * Sets the value of the preventiveMaintenanceMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link ISO8061Month }
     *     
     */
    public void setPreventiveMaintenanceMonth(ISO8061Month value) {
        this.preventiveMaintenanceMonth = value;
    }

    /**
     * Gets the value of the preventiveMaintenanceDay property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPreventiveMaintenanceDay() {
        return preventiveMaintenanceDay;
    }

    /**
     * Sets the value of the preventiveMaintenanceDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPreventiveMaintenanceDay(Integer value) {
        this.preventiveMaintenanceDay = value;
    }

    /**
     * Gets the value of the lastCalculatedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastCalculatedDate() {
        return lastCalculatedDate;
    }

    /**
     * Sets the value of the lastCalculatedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastCalculatedDate(XMLGregorianCalendar value) {
        this.lastCalculatedDate = value;
    }

    /**
     * Gets the value of the userDefined01 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined01() {
        return userDefined01;
    }

    /**
     * Sets the value of the userDefined01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined01(String value) {
        this.userDefined01 = value;
    }

    /**
     * Gets the value of the userDefined02 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined02() {
        return userDefined02;
    }

    /**
     * Sets the value of the userDefined02 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined02(String value) {
        this.userDefined02 = value;
    }

    /**
     * Gets the value of the userDefined03 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined03() {
        return userDefined03;
    }

    /**
     * Sets the value of the userDefined03 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined03(String value) {
        this.userDefined03 = value;
    }

    /**
     * Gets the value of the userDefined04 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined04() {
        return userDefined04;
    }

    /**
     * Sets the value of the userDefined04 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined04(String value) {
        this.userDefined04 = value;
    }

    /**
     * Gets the value of the userDefined05 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined05() {
        return userDefined05;
    }

    /**
     * Sets the value of the userDefined05 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined05(String value) {
        this.userDefined05 = value;
    }

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
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the assetTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetTag() {
        return assetTag;
    }

    /**
     * Sets the value of the assetTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetTag(String value) {
        this.assetTag = value;
    }

    /**
     * Gets the value of the readings property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceEquipmentReading }
     *     
     */
    public ArrayOfServiceEquipmentReading getReadings() {
        return readings;
    }

    /**
     * Sets the value of the readings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceEquipmentReading }
     *     
     */
    public void setReadings(ArrayOfServiceEquipmentReading value) {
        this.readings = value;
    }

}
