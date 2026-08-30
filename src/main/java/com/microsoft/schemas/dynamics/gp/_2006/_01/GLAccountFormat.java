
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLAccountFormat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLAccountFormat"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountFormatKey" minOccurs="0"/&gt;
 *         &lt;element name="SegmentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MaximumSegmentLength" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SegmentLength" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsMainSegment" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="UserDefinedSegment" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLAccountFormat", propOrder = {
    "key",
    "segmentName",
    "maximumSegmentLength",
    "segmentLength",
    "isMainSegment",
    "userDefinedSegment"
})
public class GLAccountFormat
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected GLAccountFormatKey key;
    @XmlElement(name = "SegmentName")
    protected String segmentName;
    @XmlElement(name = "MaximumSegmentLength", required = true, type = Integer.class, nillable = true)
    protected Integer maximumSegmentLength;
    @XmlElement(name = "SegmentLength", required = true, type = Integer.class, nillable = true)
    protected Integer segmentLength;
    @XmlElement(name = "IsMainSegment", required = true, type = Boolean.class, nillable = true)
    protected Boolean isMainSegment;
    @XmlElement(name = "UserDefinedSegment", required = true, type = Integer.class, nillable = true)
    protected Integer userDefinedSegment;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountFormatKey }
     *     
     */
    public GLAccountFormatKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountFormatKey }
     *     
     */
    public void setKey(GLAccountFormatKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the segmentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentName() {
        return segmentName;
    }

    /**
     * Sets the value of the segmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentName(String value) {
        this.segmentName = value;
    }

    /**
     * Gets the value of the maximumSegmentLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaximumSegmentLength() {
        return maximumSegmentLength;
    }

    /**
     * Sets the value of the maximumSegmentLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaximumSegmentLength(Integer value) {
        this.maximumSegmentLength = value;
    }

    /**
     * Gets the value of the segmentLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSegmentLength() {
        return segmentLength;
    }

    /**
     * Sets the value of the segmentLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSegmentLength(Integer value) {
        this.segmentLength = value;
    }

    /**
     * Gets the value of the isMainSegment property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMainSegment() {
        return isMainSegment;
    }

    /**
     * Sets the value of the isMainSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMainSegment(Boolean value) {
        this.isMainSegment = value;
    }

    /**
     * Gets the value of the userDefinedSegment property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUserDefinedSegment() {
        return userDefinedSegment;
    }

    /**
     * Sets the value of the userDefinedSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserDefinedSegment(Integer value) {
        this.userDefinedSegment = value;
    }

}
