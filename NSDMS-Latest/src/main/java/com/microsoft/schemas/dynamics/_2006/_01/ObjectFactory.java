
package com.microsoft.schemas.dynamics._2006._01;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.microsoft.schemas.dynamics._2006._01 package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.microsoft.schemas.dynamics._2006._01
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Extension }
     * 
     */
    public Extension createExtension() {
        return new Extension();
    }

    /**
     * Create an instance of {@link CompanyKey }
     * 
     */
    public CompanyKey createCompanyKey() {
        return new CompanyKey();
    }

    /**
     * Create an instance of {@link Context }
     * 
     */
    public Context createContext() {
        return new Context();
    }

    /**
     * Create an instance of {@link AXCompanyKey }
     * 
     */
    public AXCompanyKey createAXCompanyKey() {
        return new AXCompanyKey();
    }

    /**
     * Create an instance of {@link NAVCompanyKey }
     * 
     */
    public NAVCompanyKey createNAVCompanyKey() {
        return new NAVCompanyKey();
    }

    /**
     * Create an instance of {@link Extension.DocExtension }
     * 
     */
    public Extension.DocExtension createExtensionDocExtension() {
        return new Extension.DocExtension();
    }

}
