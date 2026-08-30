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
 *         &lt;element name="RecentTransactionsResult" type="{http://Finance.merseta.org.za/}ArrayOfRecentPayments" minOccurs="0"/&gt;
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
    "recentTransactionsResult"
})
@XmlRootElement(name = "RecentTransactionsResponse")
public class RecentTransactionsResponse {

    @XmlElement(name = "RecentTransactionsResult")
    protected ArrayOfRecentPayments recentTransactionsResult;

    /**
     * Gets the value of the recentTransactionsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecentPayments }
     *     
     */
    public ArrayOfRecentPayments getRecentTransactionsResult() {
        return recentTransactionsResult;
    }

    /**
     * Sets the value of the recentTransactionsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecentPayments }
     *     
     */
    public void setRecentTransactionsResult(ArrayOfRecentPayments value) {
        this.recentTransactionsResult = value;
    }

}
