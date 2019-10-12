package br.org.crea.commons.validsigner.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for cmsPackageType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="cmsPackageType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CMS"/>
 *     &lt;enumeration value="CAdES_AD_RT"/>
 *     &lt;enumeration value="CAdES_AD_RB"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "cmsPackageType")
@XmlEnum
public enum CmsPackageTypeValidEnum {

	CMS("CMS"),
    @XmlEnumValue("CAdES_AD_RT")
    C_AD_ES_AD_RT("CAdES_AD_RT"),
    @XmlEnumValue("CAdES_AD_RB")
    C_AD_ES_AD_RB("CAdES_AD_RB"),
	@XmlEnumValue("PAdES_AD_RB")
    P_AD_ES_AD_RB("PAdES_AD_RB");
    private final String value;

    CmsPackageTypeValidEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CmsPackageTypeValidEnum fromValue(String v) {
        for (CmsPackageTypeValidEnum c: CmsPackageTypeValidEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
