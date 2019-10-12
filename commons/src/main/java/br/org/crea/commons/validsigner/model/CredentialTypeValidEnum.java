package br.org.crea.commons.validsigner.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for credentialType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="credentialType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TOKEN"/>
 *     &lt;enumeration value="CERTIFICATE"/>
 *     &lt;enumeration value="CMS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "credentialType")
@XmlEnum
public enum CredentialTypeValidEnum {
	
	TOKEN,
    CERTIFICATE,
    CMS;

    public String value() {
        return name();
    }

    public static CredentialTypeValidEnum fromValue(String v) {
        return valueOf(v);
    }

}
