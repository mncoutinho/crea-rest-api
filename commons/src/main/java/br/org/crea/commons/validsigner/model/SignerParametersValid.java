package br.org.crea.commons.validsigner.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for signerParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="signerParameters">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.web.pad.cd.valid.com.br/}basicParameters">
 *       &lt;sequence>
 *         &lt;element name="clientPrivateKeyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cmsPackageType" type="{http://ws.web.pad.cd.valid.com.br/}cmsPackageType" minOccurs="0"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="dataAlreadySigned" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="hashData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="mdAlg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signedAttributes" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="signedData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
@XmlType(name = "signerParameters", propOrder = {
    "clientPrivateKeyCode",
    "cmsPackageType",
    "data",
    "dataAlreadySigned",
    "hashData",
    "mdAlg",
    "signedAttributes",
    "signedData",
    "x509SigningCertificate"
})
public class SignerParametersValid  extends BasicParametersValid {

	protected String clientPrivateKeyCode;
    protected CmsPackageTypeValidEnum cmsPackageType;
    protected byte[] data;
    protected byte[] dataAlreadySigned;
    protected byte[] hashData;
    protected String mdAlg;
    protected byte[] signedAttributes;
    protected byte[] signedData;
    protected byte[] x509SigningCertificate;

    /**
     * Gets the value of the clientPrivateKeyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientPrivateKeyCode() {
        return clientPrivateKeyCode;
    }

    /**
     * Sets the value of the clientPrivateKeyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientPrivateKeyCode(String value) {
        this.clientPrivateKeyCode = value;
    }

    /**
     * Gets the value of the cmsPackageType property.
     * 
     * @return
     *     possible object is
     *     {@link CmsPackageType }
     *     
     */
    public CmsPackageTypeValidEnum getCmsPackageType() {
        return cmsPackageType;
    }

    /**
     * Sets the value of the cmsPackageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmsPackageType }
     *     
     */
    public void setCmsPackageType(CmsPackageTypeValidEnum value) {
        this.cmsPackageType = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setData(byte[] value) {
        this.data = value;
    }

    /**
     * Gets the value of the dataAlreadySigned property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDataAlreadySigned() {
        return dataAlreadySigned;
    }

    /**
     * Sets the value of the dataAlreadySigned property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDataAlreadySigned(byte[] value) {
        this.dataAlreadySigned = value;
    }

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
     * Gets the value of the mdAlg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMdAlg() {
        return mdAlg;
    }

    /**
     * Sets the value of the mdAlg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMdAlg(String value) {
        this.mdAlg = value;
    }

    /**
     * Gets the value of the signedAttributes property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignedAttributes() {
        return signedAttributes;
    }

    /**
     * Sets the value of the signedAttributes property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignedAttributes(byte[] value) {
        this.signedAttributes = value;
    }

    /**
     * Gets the value of the signedData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignedData() {
        return signedData;
    }

    /**
     * Sets the value of the signedData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignedData(byte[] value) {
        this.signedData = value;
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
