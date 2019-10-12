package br.org.crea.commons.validsigner.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for signerResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="signerResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.web.pad.cd.valid.com.br/}result">
 *       &lt;sequence>
 *         &lt;element name="hashData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="signaturePackage" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="x509SigningCertificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "signerResult", propOrder = {
    "hashData",
    "signaturePackage",
    "x509SigningCertificate"
})
public class SignerResultValid extends ResultValid {
	
	protected byte[] hashData;
    protected byte[] signaturePackage;
    protected byte[] x509SigningCertificate;

    /**
     * Gets the value of the hashData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHashData() {
        return hashData;
    }

    /**
     * Sets the value of the hashData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHashData(byte[] value) {
        this.hashData = value;
    }

    /**
     * Gets the value of the signaturePackage property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignaturePackage() {
        return signaturePackage;
    }

    /**
     * Sets the value of the signaturePackage property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignaturePackage(byte[] value) {
        this.signaturePackage = value;
    }

    /**
     * Gets the value of the x509SigningCertificate property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509SigningCertificate() {
        return x509SigningCertificate;
    }

    /**
     * Sets the value of the x509SigningCertificate property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509SigningCertificate(byte[] value) {
        this.x509SigningCertificate = value;
    }

}
