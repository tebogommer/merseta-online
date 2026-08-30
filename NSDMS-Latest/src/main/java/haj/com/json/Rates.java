
package haj.com.json;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Rates.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "AUD",
    "BGN",
    "BRL",
    "CAD",
    "CHF",
    "CNY",
    "CZK",
    "DKK",
    "HKD",
    "HRK",
    "HUF",
    "IDR",
    "ILS",
    "INR",
    "JPY",
    "KRW",
    "MXN",
    "MYR",
    "NOK",
    "NZD",
    "PHP",
    "PLN",
    "RON",
    "RUB",
    "SEK",
    "SGD",
    "THB",
    "TRY",
    "USD",
    "ZAR",
    "EUR",
    "GBP"
})
@Entity
@Table(name = "currenry_rates")
public class Rates implements IDataEntity {

	/** The id. */
	@JsonIgnore
	@GenericGenerator(name = "generator", strategy = "foreign",
	parameters = @Parameter(name = "property", value = "currencyBean"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/** The currency bean. */
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private CurrencyBean currencyBean;
	
    /** The aud. */
    @JsonProperty("AUD")
    private Double AUD;
    
    /** The bgn. */
    @JsonProperty("BGN")
    private Double BGN;
    
    /** The brl. */
    @JsonProperty("BRL")
    private Double BRL;
    
    /** The cad. */
    @JsonProperty("CAD")
    private Double CAD;
    
    /** The chf. */
    @JsonProperty("CHF")
    private Double CHF;
    
    /** The cny. */
    @JsonProperty("CNY")
    private Double CNY;
    
    /** The czk. */
    @JsonProperty("CZK")
    private Double CZK;
    
    /** The dkk. */
    @JsonProperty("DKK")
    private Double DKK;
    
    /** The hkd. */
    @JsonProperty("HKD")
    private Double HKD;
    
    /** The hrk. */
    @JsonProperty("HRK")
    private Double HRK;
    
    /** The huf. */
    @JsonProperty("HUF")
    private Double HUF;
    
    /** The idr. */
    @JsonProperty("IDR")
    private Double IDR;
    
    /** The ils. */
    @JsonProperty("ILS")
    private Double ILS;
    
    /** The inr. */
    @JsonProperty("INR")
    private Double INR;
    
    /** The jpy. */
    @JsonProperty("JPY")
    private Double JPY;
    
    /** The krw. */
    @JsonProperty("KRW")
    private Double KRW;
    
    /** The mxn. */
    @JsonProperty("MXN")
    private Double MXN;
    
    /** The myr. */
    @JsonProperty("MYR")
    private Double MYR;
    
    /** The nok. */
    @JsonProperty("NOK")
    private Double NOK;
    
    /** The nzd. */
    @JsonProperty("NZD")
    private Double NZD;
    
    /** The php. */
    @JsonProperty("PHP")
    private Double PHP;
    
    /** The pln. */
    @JsonProperty("PLN")
    private Double PLN;
    
    /** The ron. */
    @JsonProperty("RON")
    private Double RON;
    
    /** The rub. */
    @JsonProperty("RUB")
    private Double RUB;
    
    /** The sek. */
    @JsonProperty("SEK")
    private Double SEK;
    
    /** The sgd. */
    @JsonProperty("SGD")
    private Double SGD;
    
    /** The thb. */
    @JsonProperty("THB")
    private Double THB;
    
    /** The try. */
    @JsonProperty("TRY")
    private Double TRY;
    
    /** The usd. */
    @JsonProperty("USD")
    private Double USD;
    
    /** The zar. */
    @JsonProperty("ZAR")
    private Double ZAR;
    
    /** The eur. */
    @JsonProperty("EUR")
    private Double EUR;
    
    /** The gbp. */
    @JsonProperty("GBP")
    private Double GBP;   
    
    /** The additional properties. */
    @JsonIgnore
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the aud.
     *
     * @return the aud
     */
    @JsonProperty("AUD")
    public Double getAUD() {
        return AUD;
    }

    /**
     * Sets the aud.
     *
     * @param aUD the new aud
     */
    @JsonProperty("AUD")
    public void setAUD(Double aUD) {
        this.AUD = aUD;
    }

    /**
     * Gets the bgn.
     *
     * @return the bgn
     */
    @JsonProperty("BGN")
    public Double getBGN() {
        return BGN;
    }

    /**
     * Sets the bgn.
     *
     * @param bGN the new bgn
     */
    @JsonProperty("BGN")
    public void setBGN(Double bGN) {
        this.BGN = bGN;
    }

    /**
     * Gets the brl.
     *
     * @return the brl
     */
    @JsonProperty("BRL")
    public Double getBRL() {
        return BRL;
    }

    /**
     * Sets the brl.
     *
     * @param bRL the new brl
     */
    @JsonProperty("BRL")
    public void setBRL(Double bRL) {
        this.BRL = bRL;
    }

    /**
     * Gets the cad.
     *
     * @return the cad
     */
    @JsonProperty("CAD")
    public Double getCAD() {
        return CAD;
    }

    /**
     * Sets the cad.
     *
     * @param cAD the new cad
     */
    @JsonProperty("CAD")
    public void setCAD(Double cAD) {
        this.CAD = cAD;
    }

    /**
     * Gets the chf.
     *
     * @return the chf
     */
    @JsonProperty("CHF")
    public Double getCHF() {
        return CHF;
    }

    /**
     * Sets the chf.
     *
     * @param cHF the new chf
     */
    @JsonProperty("CHF")
    public void setCHF(Double cHF) {
        this.CHF = cHF;
    }

    /**
     * Gets the cny.
     *
     * @return the cny
     */
    @JsonProperty("CNY")
    public Double getCNY() {
        return CNY;
    }

    /**
     * Sets the cny.
     *
     * @param cNY the new cny
     */
    @JsonProperty("CNY")
    public void setCNY(Double cNY) {
        this.CNY = cNY;
    }

    /**
     * Gets the czk.
     *
     * @return the czk
     */
    @JsonProperty("CZK")
    public Double getCZK() {
        return CZK;
    }

    /**
     * Sets the czk.
     *
     * @param cZK the new czk
     */
    @JsonProperty("CZK")
    public void setCZK(Double cZK) {
        this.CZK = cZK;
    }

    /**
     * Gets the dkk.
     *
     * @return the dkk
     */
    @JsonProperty("DKK")
    public Double getDKK() {
        return DKK;
    }

    /**
     * Sets the dkk.
     *
     * @param dKK the new dkk
     */
    @JsonProperty("DKK")
    public void setDKK(Double dKK) {
        this.DKK = dKK;
    }

    /**
     * Gets the hkd.
     *
     * @return the hkd
     */
    @JsonProperty("HKD")
    public Double getHKD() {
        return HKD;
    }

    /**
     * Sets the hkd.
     *
     * @param hKD the new hkd
     */
    @JsonProperty("HKD")
    public void setHKD(Double hKD) {
        this.HKD = hKD;
    }

    /**
     * Gets the hrk.
     *
     * @return the hrk
     */
    @JsonProperty("HRK")
    public Double getHRK() {
        return HRK;
    }

    /**
     * Sets the hrk.
     *
     * @param hRK the new hrk
     */
    @JsonProperty("HRK")
    public void setHRK(Double hRK) {
        this.HRK = hRK;
    }

    /**
     * Gets the huf.
     *
     * @return the huf
     */
    @JsonProperty("HUF")
    public Double getHUF() {
        return HUF;
    }

    /**
     * Sets the huf.
     *
     * @param hUF the new huf
     */
    @JsonProperty("HUF")
    public void setHUF(Double hUF) {
        this.HUF = hUF;
    }

    /**
     * Gets the idr.
     *
     * @return the idr
     */
    @JsonProperty("IDR")
    public Double getIDR() {
        return IDR;
    }

    /**
     * Sets the idr.
     *
     * @param iDR the new idr
     */
    @JsonProperty("IDR")
    public void setIDR(Double iDR) {
        this.IDR = iDR;
    }

    /**
     * Gets the ils.
     *
     * @return the ils
     */
    @JsonProperty("ILS")
    public Double getILS() {
        return ILS;
    }

    /**
     * Sets the ils.
     *
     * @param iLS the new ils
     */
    @JsonProperty("ILS")
    public void setILS(Double iLS) {
        this.ILS = iLS;
    }

    /**
     * Gets the inr.
     *
     * @return the inr
     */
    @JsonProperty("INR")
    public Double getINR() {
        return INR;
    }

    /**
     * Sets the inr.
     *
     * @param iNR the new inr
     */
    @JsonProperty("INR")
    public void setINR(Double iNR) {
        this.INR = iNR;
    }

    /**
     * Gets the jpy.
     *
     * @return the jpy
     */
    @JsonProperty("JPY")
    public Double getJPY() {
        return JPY;
    }

    /**
     * Sets the jpy.
     *
     * @param jPY the new jpy
     */
    @JsonProperty("JPY")
    public void setJPY(Double jPY) {
        this.JPY = jPY;
    }

    /**
     * Gets the krw.
     *
     * @return the krw
     */
    @JsonProperty("KRW")
    public Double getKRW() {
        return KRW;
    }

    /**
     * Sets the krw.
     *
     * @param kRW the new krw
     */
    @JsonProperty("KRW")
    public void setKRW(Double kRW) {
        this.KRW = kRW;
    }

    /**
     * Gets the mxn.
     *
     * @return the mxn
     */
    @JsonProperty("MXN")
    public Double getMXN() {
        return MXN;
    }

    /**
     * Sets the mxn.
     *
     * @param mXN the new mxn
     */
    @JsonProperty("MXN")
    public void setMXN(Double mXN) {
        this.MXN = mXN;
    }

    /**
     * Gets the myr.
     *
     * @return the myr
     */
    @JsonProperty("MYR")
    public Double getMYR() {
        return MYR;
    }

    /**
     * Sets the myr.
     *
     * @param mYR the new myr
     */
    @JsonProperty("MYR")
    public void setMYR(Double mYR) {
        this.MYR = mYR;
    }

    /**
     * Gets the nok.
     *
     * @return the nok
     */
    @JsonProperty("NOK")
    public Double getNOK() {
        return NOK;
    }

    /**
     * Sets the nok.
     *
     * @param nOK the new nok
     */
    @JsonProperty("NOK")
    public void setNOK(Double nOK) {
        this.NOK = nOK;
    }

    /**
     * Gets the nzd.
     *
     * @return the nzd
     */
    @JsonProperty("NZD")
    public Double getNZD() {
        return NZD;
    }

    /**
     * Sets the nzd.
     *
     * @param nZD the new nzd
     */
    @JsonProperty("NZD")
    public void setNZD(Double nZD) {
        this.NZD = nZD;
    }

    /**
     * Gets the php.
     *
     * @return the php
     */
    @JsonProperty("PHP")
    public Double getPHP() {
        return PHP;
    }

    /**
     * Sets the php.
     *
     * @param pHP the new php
     */
    @JsonProperty("PHP")
    public void setPHP(Double pHP) {
        this.PHP = pHP;
    }

    /**
     * Gets the pln.
     *
     * @return the pln
     */
    @JsonProperty("PLN")
    public Double getPLN() {
        return PLN;
    }

    /**
     * Sets the pln.
     *
     * @param pLN the new pln
     */
    @JsonProperty("PLN")
    public void setPLN(Double pLN) {
        this.PLN = pLN;
    }

    /**
     * Gets the ron.
     *
     * @return the ron
     */
    @JsonProperty("RON")
    public Double getRON() {
        return RON;
    }

    /**
     * Sets the ron.
     *
     * @param rON the new ron
     */
    @JsonProperty("RON")
    public void setRON(Double rON) {
        this.RON = rON;
    }

    /**
     * Gets the rub.
     *
     * @return the rub
     */
    @JsonProperty("RUB")
    public Double getRUB() {
        return RUB;
    }

    /**
     * Sets the rub.
     *
     * @param rUB the new rub
     */
    @JsonProperty("RUB")
    public void setRUB(Double rUB) {
        this.RUB = rUB;
    }

    /**
     * Gets the sek.
     *
     * @return the sek
     */
    @JsonProperty("SEK")
    public Double getSEK() {
        return SEK;
    }

    /**
     * Sets the sek.
     *
     * @param sEK the new sek
     */
    @JsonProperty("SEK")
    public void setSEK(Double sEK) {
        this.SEK = sEK;
    }

    /**
     * Gets the sgd.
     *
     * @return the sgd
     */
    @JsonProperty("SGD")
    public Double getSGD() {
        return SGD;
    }

    /**
     * Sets the sgd.
     *
     * @param sGD the new sgd
     */
    @JsonProperty("SGD")
    public void setSGD(Double sGD) {
        this.SGD = sGD;
    }

    /**
     * Gets the thb.
     *
     * @return the thb
     */
    @JsonProperty("THB")
    public Double getTHB() {
        return THB;
    }

    /**
     * Sets the thb.
     *
     * @param tHB the new thb
     */
    @JsonProperty("THB")
    public void setTHB(Double tHB) {
        this.THB = tHB;
    }

    /**
     * Gets the try.
     *
     * @return the try
     */
    @JsonProperty("TRY")
    public Double getTRY() {
        return TRY;
    }

    /**
     * Sets the try.
     *
     * @param tRY the new try
     */
    @JsonProperty("TRY")
    public void setTRY(Double tRY) {
        this.TRY = tRY;
    }

    /**
     * Gets the usd.
     *
     * @return the usd
     */
    @JsonProperty("USD")
    public Double getUSD() {
        return USD;
    }

    /**
     * Sets the usd.
     *
     * @param uSD the new usd
     */
    @JsonProperty("USD")
    public void setUSD(Double uSD) {
        this.USD = uSD;
    }

    /**
     * Gets the zar.
     *
     * @return the zar
     */
    @JsonProperty("ZAR")
    public Double getZAR() {
        return ZAR;
    }

    /**
     * Sets the zar.
     *
     * @param zAR the new zar
     */
    @JsonProperty("ZAR")
    public void setZAR(Double zAR) {
        this.ZAR = zAR;
    }

    /**
     * Gets the eur.
     *
     * @return the eur
     */
    @JsonProperty("EUR")
    public Double getEUR() {
        return EUR;
    }

    /**
     * Sets the eur.
     *
     * @param eUR the new eur
     */
    @JsonProperty("EUR")
    public void setEUR(Double eUR) {
        this.EUR = eUR;
    }

    /**
     * Gets the additional properties.
     *
     * @return the additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Sets the additional property.
     *
     * @param name the name
     * @param value the value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the currency bean.
	 *
	 * @return the currency bean
	 */
	public CurrencyBean getCurrencyBean() {
		return currencyBean;
	}

	/**
	 * Sets the currency bean.
	 *
	 * @param currencyBean the new currency bean
	 */
	public void setCurrencyBean(CurrencyBean currencyBean) {
		this.currencyBean = currencyBean;
	}

	/**
	 * Sets the additional properties.
	 *
	 * @param additionalProperties the additional properties
	 */
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

    /**
     * Gets the gbp.
     *
     * @return the gbp
     */
    @JsonProperty("GBP")
	public Double getGBP() {
		return GBP;
	}

    /**
     * Sets the gbp.
     *
     * @param gBP the new gbp
     */
    @JsonProperty("GBP")
	public void setGBP(Double gBP) {
		GBP = gBP;
	}

}
