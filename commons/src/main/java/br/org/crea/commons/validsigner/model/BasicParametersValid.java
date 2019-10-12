package br.org.crea.commons.validsigner.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for basicParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="basicParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingInfoReq" type="{http://ws.web.pad.cd.valid.com.br/}billingInfoReq" minOccurs="0"/>
 *         &lt;element name="credential" type="{http://ws.web.pad.cd.valid.com.br/}credential" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basicParameters", propOrder = {
    "billingInfoReq",
    "credential"
})
@XmlSeeAlso({
    SignerParametersValid.class
})
public class BasicParametersValid {
	
	protected BillingValid billingInfoReq;
    protected CredentialValid credential;

    /**
     * Gets the value of the billingInfoReq property.
     * 
     * @return
     *     possible object is
     *     {@link BillingInfoReq }
     *     
     */
    public BillingValid getBillingInfoReq() {
        return billingInfoReq;
    }

    /**
     * Sets the value of the billingInfoReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingInfoReq }
     *     
     */
    public void setBillingInfoReq(BillingValid value) {
        this.billingInfoReq = value;
    }

    /**
     * Gets the value of the credential property.
     * 
     * @return
     *     possible object is
     *     {@link Credential }
     *     
     */
    public CredentialValid getCredential() {
        return credential;
    }

    /**
     * Sets the value of the credential property.
     * 
     * @param value
     *     allowed object is
     *     {@link Credential }
     *     
     */
    public void setCredential(CredentialValid value) {
        this.credential = value;
    }

}
