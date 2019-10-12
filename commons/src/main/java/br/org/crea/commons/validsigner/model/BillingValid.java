package br.org.crea.commons.validsigner.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for billingInfoReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="billingInfoReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationInstanceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idClient" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="productSku" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secretCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sessionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vectorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billingInfoReq", propOrder = {
    "applicationInstanceCode",
    "contractUuid",
    "idClient",
    "productSku",
    "secretCode",
    "sessionKey",
    "userCode",
    "vectorCode"
})
public class BillingValid {
	
	protected String applicationInstanceCode;
    protected String contractUuid;
    protected long idClient;
    protected String productSku;
    protected String secretCode;
    protected String sessionKey;
    protected String userCode;
    protected String vectorCode;

    /**
     * Gets the value of the applicationInstanceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationInstanceCode() {
        return applicationInstanceCode;
    }

    /**
     * Sets the value of the applicationInstanceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationInstanceCode(String value) {
        this.applicationInstanceCode = value;
    }

    /**
     * Gets the value of the contractUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractUuid() {
        return contractUuid;
    }

    /**
     * Sets the value of the contractUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractUuid(String value) {
        this.contractUuid = value;
    }

    /**
     * Gets the value of the idClient property.
     * 
     */
    public long getIdClient() {
        return idClient;
    }

    /**
     * Sets the value of the idClient property.
     * 
     */
    public void setIdClient(long value) {
        this.idClient = value;
    }

    /**
     * Gets the value of the productSku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductSku() {
        return productSku;
    }

    /**
     * Sets the value of the productSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductSku(String value) {
        this.productSku = value;
    }

    /**
     * Gets the value of the secretCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecretCode() {
        return secretCode;
    }

    /**
     * Sets the value of the secretCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecretCode(String value) {
        this.secretCode = value;
    }

    /**
     * Gets the value of the sessionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * Sets the value of the sessionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionKey(String value) {
        this.sessionKey = value;
    }

    /**
     * Gets the value of the userCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * Sets the value of the userCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCode(String value) {
        this.userCode = value;
    }

    /**
     * Gets the value of the vectorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVectorCode() {
        return vectorCode;
    }

    /**
     * Sets the value of the vectorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVectorCode(String value) {
        this.vectorCode = value;
    }

}
