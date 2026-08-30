package haj.com.gptransations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OutStandingTransactionsResult" type="{http://Finance.merseta.org.za/}ArrayOfInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "outStandingTransactionsResult"
})
@XmlRootElement(name = "OutStandingTransactionsResponse")
public class OutStandingTransactionsResponse {

    @XmlElement(name = "OutStandingTransactionsResult")
    protected ArrayOfInfo outStandingTransactionsResult;

    /**
     * Gets the value of the outStandingTransactionsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInfo }
     *     
     */
    public ArrayOfInfo getOutStandingTransactionsResult() {
        return outStandingTransactionsResult;
    }

    /**
     * Sets the value of the outStandingTransactionsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInfo }
     *     
     */
    public void setOutStandingTransactionsResult(ArrayOfInfo value) {
        this.outStandingTransactionsResult = value;
    }

}
