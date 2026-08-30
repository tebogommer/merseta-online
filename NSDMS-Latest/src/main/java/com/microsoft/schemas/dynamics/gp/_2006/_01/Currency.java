
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Currency complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Currency"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Symbol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SymbolLocation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RelativeLocation"/&gt;
 *         &lt;element name="IncludeSpace" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="NegativeSymbol" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}NegativeSymbol"/&gt;
 *         &lt;element name="NegativeSymbolLocation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RelativeLocation"/&gt;
 *         &lt;element name="NegativeSymbolCurrencySymbolLocation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}NegativeSymbolCurrencySymbolLocation"/&gt;
 *         &lt;element name="DecimalSymbol" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DecimalSymbol"/&gt;
 *         &lt;element name="DecimalPlaces" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DecimalPlaces"/&gt;
 *         &lt;element name="ThousandsSymbol" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ThousandsSymbol"/&gt;
 *         &lt;element name="UnitText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UnitSubunitConnectorText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SubunitText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Currency", propOrder = {
    "key",
    "description",
    "index",
    "id",
    "symbol",
    "symbolLocation",
    "includeSpace",
    "negativeSymbol",
    "negativeSymbolLocation",
    "negativeSymbolCurrencySymbolLocation",
    "decimalSymbol",
    "decimalPlaces",
    "thousandsSymbol",
    "unitText",
    "unitSubunitConnectorText",
    "subunitText",
    "lastModifiedDate",
    "lastModifiedBy"
})
public class Currency
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected CurrencyKey key;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Index", required = true, type = Integer.class, nillable = true)
    protected Integer index;
    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "Symbol")
    protected String symbol;
    @XmlElement(name = "SymbolLocation", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected RelativeLocation symbolLocation;
    @XmlElement(name = "IncludeSpace", required = true, type = Boolean.class, nillable = true)
    protected Boolean includeSpace;
    @XmlElement(name = "NegativeSymbol", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected NegativeSymbol negativeSymbol;
    @XmlElement(name = "NegativeSymbolLocation", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected RelativeLocation negativeSymbolLocation;
    @XmlElement(name = "NegativeSymbolCurrencySymbolLocation", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected NegativeSymbolCurrencySymbolLocation negativeSymbolCurrencySymbolLocation;
    @XmlElement(name = "DecimalSymbol", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected DecimalSymbol decimalSymbol;
    @XmlElement(name = "DecimalPlaces", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected DecimalPlaces decimalPlaces;
    @XmlElement(name = "ThousandsSymbol", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ThousandsSymbol thousandsSymbol;
    @XmlElement(name = "UnitText")
    protected String unitText;
    @XmlElement(name = "UnitSubunitConnectorText")
    protected String unitSubunitConnectorText;
    @XmlElement(name = "SubunitText")
    protected String subunitText;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "LastModifiedBy")
    protected String lastModifiedBy;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setKey(CurrencyKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(Integer value) {
        this.index = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the symbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the value of the symbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSymbol(String value) {
        this.symbol = value;
    }

    /**
     * Gets the value of the symbolLocation property.
     * 
     * @return
     *     possible object is
     *     {@link RelativeLocation }
     *     
     */
    public RelativeLocation getSymbolLocation() {
        return symbolLocation;
    }

    /**
     * Sets the value of the symbolLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelativeLocation }
     *     
     */
    public void setSymbolLocation(RelativeLocation value) {
        this.symbolLocation = value;
    }

    /**
     * Gets the value of the includeSpace property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeSpace() {
        return includeSpace;
    }

    /**
     * Sets the value of the includeSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeSpace(Boolean value) {
        this.includeSpace = value;
    }

    /**
     * Gets the value of the negativeSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link NegativeSymbol }
     *     
     */
    public NegativeSymbol getNegativeSymbol() {
        return negativeSymbol;
    }

    /**
     * Sets the value of the negativeSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link NegativeSymbol }
     *     
     */
    public void setNegativeSymbol(NegativeSymbol value) {
        this.negativeSymbol = value;
    }

    /**
     * Gets the value of the negativeSymbolLocation property.
     * 
     * @return
     *     possible object is
     *     {@link RelativeLocation }
     *     
     */
    public RelativeLocation getNegativeSymbolLocation() {
        return negativeSymbolLocation;
    }

    /**
     * Sets the value of the negativeSymbolLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelativeLocation }
     *     
     */
    public void setNegativeSymbolLocation(RelativeLocation value) {
        this.negativeSymbolLocation = value;
    }

    /**
     * Gets the value of the negativeSymbolCurrencySymbolLocation property.
     * 
     * @return
     *     possible object is
     *     {@link NegativeSymbolCurrencySymbolLocation }
     *     
     */
    public NegativeSymbolCurrencySymbolLocation getNegativeSymbolCurrencySymbolLocation() {
        return negativeSymbolCurrencySymbolLocation;
    }

    /**
     * Sets the value of the negativeSymbolCurrencySymbolLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link NegativeSymbolCurrencySymbolLocation }
     *     
     */
    public void setNegativeSymbolCurrencySymbolLocation(NegativeSymbolCurrencySymbolLocation value) {
        this.negativeSymbolCurrencySymbolLocation = value;
    }

    /**
     * Gets the value of the decimalSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link DecimalSymbol }
     *     
     */
    public DecimalSymbol getDecimalSymbol() {
        return decimalSymbol;
    }

    /**
     * Sets the value of the decimalSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link DecimalSymbol }
     *     
     */
    public void setDecimalSymbol(DecimalSymbol value) {
        this.decimalSymbol = value;
    }

    /**
     * Gets the value of the decimalPlaces property.
     * 
     * @return
     *     possible object is
     *     {@link DecimalPlaces }
     *     
     */
    public DecimalPlaces getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * Sets the value of the decimalPlaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link DecimalPlaces }
     *     
     */
    public void setDecimalPlaces(DecimalPlaces value) {
        this.decimalPlaces = value;
    }

    /**
     * Gets the value of the thousandsSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link ThousandsSymbol }
     *     
     */
    public ThousandsSymbol getThousandsSymbol() {
        return thousandsSymbol;
    }

    /**
     * Sets the value of the thousandsSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThousandsSymbol }
     *     
     */
    public void setThousandsSymbol(ThousandsSymbol value) {
        this.thousandsSymbol = value;
    }

    /**
     * Gets the value of the unitText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitText() {
        return unitText;
    }

    /**
     * Sets the value of the unitText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitText(String value) {
        this.unitText = value;
    }

    /**
     * Gets the value of the unitSubunitConnectorText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitSubunitConnectorText() {
        return unitSubunitConnectorText;
    }

    /**
     * Sets the value of the unitSubunitConnectorText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitSubunitConnectorText(String value) {
        this.unitSubunitConnectorText = value;
    }

    /**
     * Gets the value of the subunitText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubunitText() {
        return subunitText;
    }

    /**
     * Sets the value of the subunitText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubunitText(String value) {
        this.subunitText = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the lastModifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the value of the lastModifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifiedBy(String value) {
        this.lastModifiedBy = value;
    }

}
