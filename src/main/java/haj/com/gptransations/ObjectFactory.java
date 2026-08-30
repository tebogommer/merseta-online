package haj.com.gptransations;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.vendor.transiction package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.vendor.transiction
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OutStandingTransactions }
     * 
     */
    public OutStandingTransactions createOutStandingTransactions() {
        return new OutStandingTransactions();
    }

    /**
     * Create an instance of {@link OutStandingTransactionsResponse }
     * 
     */
    public OutStandingTransactionsResponse createOutStandingTransactionsResponse() {
        return new OutStandingTransactionsResponse();
    }

    /**
     * Create an instance of {@link ArrayOfInfo }
     * 
     */
    public ArrayOfInfo createArrayOfInfo() {
        return new ArrayOfInfo();
    }

    /**
     * Create an instance of {@link RecentTransactions }
     * 
     */
    public RecentTransactions createRecentTransactions() {
        return new RecentTransactions();
    }

    /**
     * Create an instance of {@link RecentTransactionsResponse }
     * 
     */
    public RecentTransactionsResponse createRecentTransactionsResponse() {
        return new RecentTransactionsResponse();
    }

    /**
     * Create an instance of {@link ArrayOfRecentPayments }
     * 
     */
    public ArrayOfRecentPayments createArrayOfRecentPayments() {
        return new ArrayOfRecentPayments();
    }

    /**
     * Create an instance of {@link Info }
     * 
     */
    public Info createInfo() {
        return new Info();
    }

    /**
     * Create an instance of {@link RecentPayments }
     * 
     */
    public RecentPayments createRecentPayments() {
        return new RecentPayments();
    }

}
