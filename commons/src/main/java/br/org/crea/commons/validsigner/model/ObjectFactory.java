package br.org.crea.commons.validsigner.model;

import javax.xml.bind.annotation.XmlRegistry;



/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.valid.cd.pad.web.ws package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.valid.cd.pad.web.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public ResultValid createResult() {
        return new ResultValid();
    }

    /**
     * Create an instance of {@link SignerResult }
     * 
     */
    public SignerResultValid createSignerResult() {
        return new SignerResultValid();
    }

    /**
     * Create an instance of {@link BillingInfoReq }
     * 
     */
    public BillingValid createBillingInfoReq() {
        return new BillingValid();
    }

    /**
     * Create an instance of {@link SignerParameters }
     * 
     */
    public SignerParametersValid createSignerParameters() {
        return new SignerParametersValid();
    }

    /**
     * Create an instance of {@link BasicParameters }
     * 
     */
    public BasicParametersValid createBasicParameters() {
        return new BasicParametersValid();
    }

    /**
     * Create an instance of {@link Credential }
     * 
     */
    public CredentialValid createCredential() {
        return new CredentialValid();
    }

    /**
     * Create an instance of {@link ServiceError }
     * 
     */
    public ServiceErrorValid createServiceError() {
        return new ServiceErrorValid();
    }

}
