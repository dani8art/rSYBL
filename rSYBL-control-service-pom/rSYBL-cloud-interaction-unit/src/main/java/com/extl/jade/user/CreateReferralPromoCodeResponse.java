
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Output result for method createReferralPromoCode
 * 
 * <p>Java class for createReferralPromoCodeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createReferralPromoCodeResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referralPromoCode" type="{http://extility.flexiant.net}referralPromoCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createReferralPromoCodeResponse", propOrder = {
    "referralPromoCode"
})
public class CreateReferralPromoCodeResponse {

    protected ReferralPromoCode referralPromoCode;

    /**
     * Gets the value of the referralPromoCode property.
     * 
     * @return
     *     possible object is
     *     {@link ReferralPromoCode }
     *     
     */
    public ReferralPromoCode getReferralPromoCode() {
        return referralPromoCode;
    }

    /**
     * Sets the value of the referralPromoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferralPromoCode }
     *     
     */
    public void setReferralPromoCode(ReferralPromoCode value) {
        this.referralPromoCode = value;
    }

}