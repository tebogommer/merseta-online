
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesDocumentCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesDocumentCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfSalesDocumentType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesDocumentCriteria", propOrder = {
    "type"
})
public class SalesDocumentCriteria
    extends SalesDocumentCriteriaBase
{

    @XmlElement(name = "Type")
    protected ListRestrictionOfNullableOfSalesDocumentType type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfSalesDocumentType }
     *     
     */
    public ListRestrictionOfNullableOfSalesDocumentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfSalesDocumentType }
     *     
     */
    public void setType(ListRestrictionOfNullableOfSalesDocumentType value) {
        this.type = value;
    }

}
