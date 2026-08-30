
package haj.com.json;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrencyBean.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "base",
    "date",
    "rates"
})

@Entity

@Table(name = "currency_bean", 
uniqueConstraints = @UniqueConstraint(columnNames = {"base", "date"}))
public class CurrencyBean implements IDataEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
    /** The base. */
    @JsonProperty("base")
    private String base;
    
    /** The date. */
    @JsonProperty("date")
    private String date;
    
    /** The rates. */
    @JsonProperty("rates")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "currencyBean", cascade = CascadeType.ALL)
    private Rates rates;
    
    /** The created. */
    @JsonIgnore
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = true)
    private Date created;

    
    
    /** The additional properties. */
    @JsonIgnore
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the base.
     *
     * @return the base
     */
    @JsonProperty("base")
    public String getBase() {
        return base;
    }

    /**
     * Sets the base.
     *
     * @param base the new base
     */
    @JsonProperty("base")
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the rates.
     *
     * @return the rates
     */
    @JsonProperty("rates")
    public Rates getRates() {
        return rates;
    }

    /**
     * Sets the rates.
     *
     * @param rates the new rates
     */
    @JsonProperty("rates")
    public void setRates(Rates rates) {
        this.rates = rates;
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
	 * Sets the additional properties.
	 *
	 * @param additionalProperties the additional properties
	 */
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Sets the created.
	 *
	 * @param created the new created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}



}
