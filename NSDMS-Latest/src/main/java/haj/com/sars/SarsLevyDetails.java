package haj.com.sars;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.datatakeon.TS2;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLevyDetails.


select arrival_date_1, sum(mandatory_levy) as mandatoryLevyD
from sars_levy_detail
where ref_no in (select b.vendor_id from t_s2 b
										where b.posting_date_d between '2017-08-01' and '2017-08-31'
										)
and arrival_date_1 between '2017-06-01' and '2017-07-30'
group by arrival_date_1


 */
@NamedNativeQueries({

	@NamedNativeQuery(
			name = "NQ_NATIVE_levyForSchemeYear",
			query = "select scheme_year as schemeYear ,sum(mandatory_levy) as mandatoryLevyD from sars_levy_detail " +
					"where ref_no in (select b.levy_number from t_s1 b " +
					"					where b.scheme_year  = :setaSchemeYear " +
					"					and b.status_description in ('Paid','Approved')) " +
					"and scheme_year = :sarsSchemeYear",
			resultSetMapping	= "SchemeYearMLResult"
			) ,
	@NamedNativeQuery(
			name = "NQ_NATIVE_GP_levyForMonth",
			query = "select a.scheme_year as schemeYear , d.code as chamber,  sum(a.mandatory_levy) as mandatoryLevyD , sum(a.discretionary_levy) as discretionaryLevyD , " +
					"sum(a.admin_levy) as adminLevyD, sum(a.interest) as interestD , sum(a.penalty) as penaltyD , sum(a.total) as totalD " +
					"from sars_levy_detail a , sars_employer_detail b,  sic_code c, chamber d " +
					"where a.sars_filel_id = :sarsFileId " +
					"and a.sars_filel_id = b.sars_filel_id " +
					"and a.ref_no = b.ref_no " +
					"and b.sic_code_2 = c.code " +
					"and c.chamber_id = d.id " +
					"group by a.scheme_year, d.code ",
			resultSetMapping	= "GPLevyForMonthResult"
			)  ,

	@NamedNativeQuery(
			name = "NQ_NATIVE_GP_levyForSchemeYearByChamber",
			query = "select a.scheme_year as schemeYear , d.code as chamber,  sum(a.mandatory_levy) as mandatoryLevyD , sum(a.discretionary_levy) as discretionaryLevyD , " +
					"sum(a.admin_levy) as adminLevyD, sum(a.interest) as interestD , sum(a.penalty) as penaltyD , sum(a.total) as totalD " +
					"from sars_levy_detail a , sars_employer_detail b,  sic_code c, chamber d " +
					"where a.scheme_year = :schemeYear " +
					"and a.sars_filel_id = b.sars_filel_id " +
					"and a.ref_no = b.ref_no " +
					"and b.sic_code_2 = c.code " +
					"and c.chamber_id = d.id " +
					"group by a.scheme_year, d.code ",
			resultSetMapping	= "GPLevyForMonthResult"
			)  ,
	@NamedNativeQuery(
			name = "NQ_NATIVE_SARS_levyForPeriod",
			query = "select round(sum(mandatory_levy),2) as mandatoryLevyD  " +
					"from sars_levy_detail  " +
					"where ref_no in (select b.vendor_id from t_s2 b  " +
					"				   where b.posting_date_d between :fromDateInv and :toDateInv and b.scheme_year = :setaSchemeYear	) " +
					"and arrival_date_1 between :fromDate and :toDate " +
					" and scheme_year = :sarsSchemeYear ",
			resultSetMapping	= "SARSLevyForPeriodResult"
			)
})
/*
@SqlResultSetMapping(name="SchemeYearMLResult", classes = {
	    @ConstructorResult(targetClass = SarsLevyDetails.class,
	    columns = {@ColumnResult(name="schemeYear"), @ColumnResult(name="mandatoryLevyD")})
	})
*/
@SqlResultSetMappings({
	@SqlResultSetMapping(name="SchemeYearMLResult", classes = {
		    @ConstructorResult(targetClass = SarsLevyDetails.class,
		    columns = {@ColumnResult(name="schemeYear"), @ColumnResult(name="mandatoryLevyD")})
		}),
	@SqlResultSetMapping(name="GPLevyForMonthResult", classes = {
		    @ConstructorResult(targetClass = SarsLevyDetails.class,
		    columns = {@ColumnResult(name="schemeYear"), @ColumnResult(name="chamber"),
		    			   @ColumnResult(name="mandatoryLevyD"), @ColumnResult(name="discretionaryLevyD"),
		    			   @ColumnResult(name="adminLevyD"), @ColumnResult(name="interestD"),
		    			   @ColumnResult(name="penaltyD"), @ColumnResult(name="totalD")
		    })
		}),
	@SqlResultSetMapping(name="SARSLevyForPeriodResult", classes = {
		    @ConstructorResult(targetClass = SarsLevyDetails.class,
		    columns = {@ColumnResult(name="mandatoryLevyD")})
		})
    })

/*
@SqlResultSetMappings({
    @SqlResultSetMapping(name="SchemeYearMLResult",
        entities={@EntityResult(entityClass=SarsLevyDetails.class)}, columns={@ColumnResult(name="schemeYear"), @ColumnResult(name="mandatoryLevyD")}),
    @SqlResultSetMapping(name="SDLNrForSchemeYearResult",
        columns={@ColumnResult(name="FIRSTNAME"), @ColumnResult(name="LASTNAME")})
    })
*/

@Entity
@Table(name = "sars_levy_detail")
public class SarsLevyDetails  implements IDataEntity,Cloneable{

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The sars employer detail. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_employer_detail_id", nullable = true)
	private SarsEmployerDetail sarsEmployerDetail;

	/** The sars files. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_filel_id", nullable = true)
	private SarsFiles sarsFiles;

	/** The arrival date 1. */
	@CSVAnnotation(name = "ARRIVAL_DATE_1", className = Date.class, datePattern="yyyyMMdd")
	@Column(name = "arrival_date_1")
	private Date arrivalDate1;

	/** The seta code. */
	@CSVAnnotation(name = "SETA_CODE", className = String.class)
	@Column(name = "seta_code")
	private String setaCode;

	/** The ref no. */
	@CSVAnnotation(name = "REF_NO", className = String.class)
	@Column(name = "ref_no")
	private String refNo;

	/** The arrival date 2. */
	@CSVAnnotation(name = "ARRIVAL_DATE_2", className = Date.class, datePattern="yyyyMMdd")
	@Column(name = "arrival_date_2")
	private Date arrivalDate2;

	/** The mandatory levy. */
	@CSVAnnotation(name = "MANDATORY_LEVY", className = BigDecimal.class)
	@Column(name = "mandatory_levy", columnDefinition = "DOUBLE")
	private BigDecimal mandatoryLevy;

	/** The discretionary levy. */
	@CSVAnnotation(name = "DISCRETIONARY_LEVY", className = BigDecimal.class)
	@Column(name = "discretionary_levy", columnDefinition = "DOUBLE")
	private BigDecimal discretionaryLevy;

	/** The admin levy. */
	@CSVAnnotation(name = "ADMIN_LEVY", className = BigDecimal.class)
	@Column(name = "admin_levy", columnDefinition = "DOUBLE")
	private BigDecimal adminLevy;

	/** The interest. */
	@CSVAnnotation(name = "INTEREST", className = BigDecimal.class)
	@Column(name = "interest", columnDefinition = "DOUBLE")
	private BigDecimal interest;

	/** The penalty. */
	@CSVAnnotation(name = "PENALTY", className = BigDecimal.class)
	@Column(name = "penalty", columnDefinition = "DOUBLE")
	private BigDecimal penalty;

	/** The total. */
	@CSVAnnotation(name = "TOTAL", className = BigDecimal.class)
	@Column(name = "total", columnDefinition = "DOUBLE")
	private BigDecimal total;

	/** The sars control digit 1. */
	@CSVAnnotation(name = "SARS_CONTROL_DIGIT_1", className = String.class)
	@Transient
	private String sarsControlDigit1;

	/** The sars control digit 2. */
	@CSVAnnotation(name = "SARS_CONTROL_DIGIT_2", className = String.class)
	@Transient
	private String sarsControlDigit2;

	/** The scheme year. */
	@CSVAnnotation(name = "SCHEME_YEAR", className = Integer.class)
	@Column(name = "scheme_year")
	private Integer schemeYear;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** Indicator if levy detail entry can appear on MG payments */
	@Column(name = "can_appear_on_mg_payments", columnDefinition = "BIT default true")
	private Boolean canAppearOnMgPayments;

	/** The ts 2. */
	@Transient
	private TS2 ts2;

	@Transient
	private List<SarsEmployerDetail> employerSummary;

	@Transient
	private Long numberEmployersInSARSLevyFiles;

	@Transient
	private Long numberEmployersInSARSEmployerFiles;

	@Transient
	private Long numberEmployersInSETAInvoiceFile;

	@Transient
	private Double interSetaTransfer;

	/*
select a.scheme_year, d.description as chamber,  sum(a.mandatory_levy) as mandatoryLevyD , sum(a.discretionary_levy) as discretionaryLevyD ,
sum(a.admin_levy) as adminLevyD, sum(a.interest) as interestD , sum(a.penalty) as penaltyD , sum(a.total) as totalD
	 */
	@Transient
	private String chamber;
	@Transient
	private Integer chamberCode;
	@Transient
	private Double mandatoryLevyD;
	@Transient
	private Double discretionaryLevyD;
	@Transient
	private Double adminLevyD;
	@Transient
	private Double interestD;
	@Transient
	private Double penaltyD;
	@Transient
	private Double totalD;

	@Transient
	private Double levyAmountD;



	public SarsLevyDetails(Integer schemeYear, String chamber, Double mandatoryLevyD, Double discretionaryLevyD,
			Double adminLevyD, Double interestD, Double penaltyD, Double totalD) {
		super();
		this.schemeYear = schemeYear;
		this.chamber = chamber;
		this.mandatoryLevyD = mandatoryLevyD;
		this.discretionaryLevyD = discretionaryLevyD;
		this.adminLevyD = adminLevyD;
		this.interestD = interestD;
		this.penaltyD = penaltyD;
		this.totalD = totalD;

		try {this.mandatoryLevy = BigDecimal.valueOf(mandatoryLevyD);} catch (Exception e) {}
		try {this.discretionaryLevy = BigDecimal.valueOf(discretionaryLevyD);} catch (Exception e) {}
		try {this.adminLevy = BigDecimal.valueOf(adminLevyD);} catch (Exception e) {}
		try {this.interest = BigDecimal.valueOf(interestD);} catch (Exception e) {}
		try {this.penalty = BigDecimal.valueOf(penaltyD);} catch (Exception e) {}
		try {this.total = BigDecimal.valueOf(totalD);} catch (Exception e) {}

	}

	public SarsLevyDetails(Integer schemeYear, Double mandatoryLevyD) {
		super();
		this.schemeYear = schemeYear;
		if (mandatoryLevyD!=null) {
		  this.mandatoryLevyD =	mandatoryLevyD;
		  this.mandatoryLevy = GenericUtility.roundToPrecision(BigDecimal.valueOf(mandatoryLevyD),2);
		}
	}

	/**
	 * Instantiates a new sars levy details.
	 *
	 * @param schemeYear the scheme year
	 * @param mandatoryLevy the mandatory levy
	 */
	public SarsLevyDetails(Integer schemeYear,BigDecimal mandatoryLevy) {
		super();
		this.mandatoryLevy = mandatoryLevy;
		this.schemeYear = schemeYear;
	}

	public SarsLevyDetails(Integer schemeYear,BigDecimal mandatoryLevy, BigDecimal discretionaryLevy , BigDecimal adminLevy, BigDecimal interest ,BigDecimal penalty, BigDecimal total) {
		super();
		this.mandatoryLevy = mandatoryLevy;
		this.schemeYear = schemeYear;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.penalty = penalty;
		this.total = total;
		this.interest = interest;
	}



	public SarsLevyDetails(Double mandatoryLevyD) {
		super();
		this.mandatoryLevyD = mandatoryLevyD;
	}

	public SarsLevyDetails(BigDecimal mandatoryLevy) {
		super();
		this.mandatoryLevy = mandatoryLevy;
	}

	/**
	 * Instantiates a new sars levy details.
	 *
	 * @param schemeYear the scheme year
	 * @param refNo the ref no
	 * @param mandatoryLevy the mandatory levy
	 */
	public SarsLevyDetails(Integer schemeYear, String refNo,BigDecimal mandatoryLevy) {
		super();
		this.mandatoryLevy = mandatoryLevy;
		this.schemeYear = schemeYear;
		this.refNo = refNo;
	}

	/**
	 * Instantiates a new sars levy details.
	 *
	 * @param id the id
	 * @param discretionaryLevy the discretionary levy
	 * @param mandatoryLevy the mandatory levy
	 * @param adminLevy the admin levy
	 * @param penalty the penalty
	 * @param total the total
	 * @param interest the interest
	 */
	public SarsLevyDetails(Long id, BigDecimal mandatoryLevy, BigDecimal discretionaryLevy, BigDecimal adminLevy, BigDecimal penalty, BigDecimal total, BigDecimal interest) {
		super();
		this.id = id;
		this.mandatoryLevy = mandatoryLevy;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.penalty = penalty;
		this.total = total;
		this.interest = interest;
	}


	/**
	 * Instantiates a new sars levy details.
	 *
	 * @param arrivalDate1 the arrival date 1
	 * @param discretionaryLevy the discretionary levy
	 * @param mandatoryLevy the mandatory levy
	 * @param adminLevy the admin levy
	 * @param penalty the penalty
	 * @param total the total
	 * @param interest the interest
	 */
	//select new  haj.com.sars.SarsLevyDetails(  o.arrivalDate1, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total) )
	public SarsLevyDetails(Date arrivalDate1, BigDecimal mandatoryLevy,BigDecimal discretionaryLevy,  BigDecimal adminLevy,BigDecimal interest,BigDecimal penalty, BigDecimal total) {
		super();
		this.arrivalDate1 = arrivalDate1;
		this.mandatoryLevy = mandatoryLevy;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.penalty = penalty;
		this.total = total;
		this.interest = interest;
	}
	
	/**
	 * Instantiates a new sars levy details.
	 *
	 * @param reftNo the refNo
	 * @param arrivalDate1 the arrival date 1
	 * @param schemeYear the scheme year
	 * @param discretionaryLevy the discretionary levy
	 * @param mandatoryLevy the mandatory levy
	 * @param adminLevy the admin levy
	 * @param penalty the penalty
	 * @param total the total
	 * @param interest the interest
	 */
	//select new  haj.com.sars.SarsLevyDetails( refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total, o.sarsEmployerDetail)
	public SarsLevyDetails(String refNo, Date arrivalDate1, Integer schemeYear, BigDecimal mandatoryLevy,BigDecimal discretionaryLevy,  BigDecimal adminLevy,BigDecimal interest,BigDecimal penalty, BigDecimal total) {
		super();
		this.refNo = refNo;
		this.arrivalDate1 = arrivalDate1;
		this.schemeYear = schemeYear;
		this.mandatoryLevy = mandatoryLevy;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.penalty = penalty;
		this.total = total;
		this.interest = interest;
	}
	
	/**
	 * Instantiates a new sars levy details.
	 *
	 * @param reftNo the refNo
	 * @param arrivalDate1 the arrival date 1
	 * @param schemeYear the scheme year
	 * @param discretionaryLevy the discretionary levy
	 * @param mandatoryLevy the mandatory levy
	 * @param adminLevy the admin levy
	 * @param penalty the penalty
	 * @param total the total
	 * @param interest the interest
	 */
	//select new  haj.com.sars.SarsLevyDetails( o.id, o.refNo,  o.arrivalDate1, o.schemeYear, o.mandatoryLevy , o.discretionaryLevy, o.adminLevy, o.interest, o.penalty, o.total, o.sarsEmployerDetail)
	public SarsLevyDetails(Long id, String refNo, Date arrivalDate1, Integer schemeYear, BigDecimal mandatoryLevy,BigDecimal discretionaryLevy,  BigDecimal adminLevy,BigDecimal interest,BigDecimal penalty, BigDecimal total) {
		super();
		this.id = id;
		this.refNo = refNo;
		this.arrivalDate1 = arrivalDate1;
		this.schemeYear = schemeYear;
		this.mandatoryLevy = mandatoryLevy;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.penalty = penalty;
		this.total = total;
		this.interest = interest;
	}
	
	//select new  haj.com.sars.SarsLevyDetails(o.refNo, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total) )
	public SarsLevyDetails(String refNo, BigDecimal mandatoryLevy,BigDecimal discretionaryLevy,  BigDecimal adminLevy,BigDecimal interest,BigDecimal penalty, BigDecimal total) {
		super();
		this.refNo = refNo;
		this.mandatoryLevy = mandatoryLevy;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.penalty = penalty;
		this.total = total;
		this.interest = interest;
	}
	
	//select new  haj.com.sars.SarsLevyDetails(o.refNo, Integer schemeYear, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total))
	public SarsLevyDetails(String refNo, Integer schemeYear,BigDecimal mandatoryLevy,BigDecimal discretionaryLevy,  BigDecimal adminLevy,BigDecimal interest,BigDecimal penalty, BigDecimal total) {
		super();
		this.refNo = refNo;
		this.schemeYear = schemeYear;
		this.mandatoryLevy = mandatoryLevy;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.penalty = penalty;
		this.total = total;
		this.interest = interest;
	}

	/**
	 * Instantiates a new sars levy details.
	 */
	public SarsLevyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the seta code.
	 *
	 * @return the seta code
	 */
	public String getSetaCode() {
		return setaCode;
	}

	/**
	 * Sets the seta code.
	 *
	 * @param setaCode the new seta code
	 */
	public void setSetaCode(String setaCode) {
		this.setaCode = setaCode;
	}

	/**
	 * Gets the ref no.
	 *
	 * @return the ref no
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * Sets the ref no.
	 *
	 * @param refNo the new ref no
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * Gets the arrival date 2.
	 *
	 * @return the arrival date 2
	 */
	public Date getArrivalDate2() {
		return arrivalDate2;
	}

	/**
	 * Sets the arrival date 2.
	 *
	 * @param arrivalDate2 the new arrival date 2
	 */
	public void setArrivalDate2(Date arrivalDate2) {
		this.arrivalDate2 = arrivalDate2;
	}



	/**
	 * Gets the sars control digit 1.
	 *
	 * @return the sars control digit 1
	 */
	public String getSarsControlDigit1() {
		return sarsControlDigit1;
	}

	/**
	 * Sets the sars control digit 1.
	 *
	 * @param sarsControlDigit1 the new sars control digit 1
	 */
	public void setSarsControlDigit1(String sarsControlDigit1) {
		this.sarsControlDigit1 = sarsControlDigit1;
	}

	/**
	 * Gets the sars control digit 2.
	 *
	 * @return the sars control digit 2
	 */
	public String getSarsControlDigit2() {
		return sarsControlDigit2;
	}

	/**
	 * Sets the sars control digit 2.
	 *
	 * @param sarsControlDigit2 the new sars control digit 2
	 */
	public void setSarsControlDigit2(String sarsControlDigit2) {
		this.sarsControlDigit2 = sarsControlDigit2;
	}

	/**
	 * Gets the scheme year.
	 *
	 * @return the scheme year
	 */
	public Integer getSchemeYear() {
		return schemeYear;
	}

	/**
	 * Sets the scheme year.
	 *
	 * @param schemeYear the new scheme year
	 */
	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LevyDetails [arrivalDate1=" + arrivalDate1 + ", setaCode=" + setaCode + ", refNo=" + refNo
				+ ", arrivalDate2=" + arrivalDate2 + ", mandatoryLevy=" + mandatoryLevy + ", discretionaryLevy="
				+ discretionaryLevy + ", adminLevy=" + adminLevy + ", interest=" + interest + ", penalty=" + penalty
				+ ", total=" + total + ", sarsControlDigit1=" + sarsControlDigit1 + ", sarsControlDigit2="
				+ sarsControlDigit2 + ", schemeYear=" + schemeYear + "]";
	}

	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	/**
	 * Gets the arrival date 1.
	 *
	 * @return the arrival date 1
	 */
	public Date getArrivalDate1() {
		return arrivalDate1;
	}

	/**
	 * Sets the arrival date 1.
	 *
	 * @param arrivalDate1 the new arrival date 1
	 */
	public void setArrivalDate1(Date arrivalDate1) {
		this.arrivalDate1 = arrivalDate1;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SarsLevyDetails other = (SarsLevyDetails) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	 * Gets the mandatory levy.
	 *
	 * @return the mandatory levy
	 */
	public BigDecimal getMandatoryLevy() {
		return mandatoryLevy;
	}

	/**
	 * Sets the mandatory levy.
	 *
	 * @param mandatoryLevy the new mandatory levy
	 */
	public void setMandatoryLevy(BigDecimal mandatoryLevy) {
		this.mandatoryLevy = mandatoryLevy;
	}

	/**
	 * Gets the discretionary levy.
	 *
	 * @return the discretionary levy
	 */
	public BigDecimal getDiscretionaryLevy() {
		return discretionaryLevy;
	}

	/**
	 * Sets the discretionary levy.
	 *
	 * @param discretionaryLevy the new discretionary levy
	 */
	public void setDiscretionaryLevy(BigDecimal discretionaryLevy) {
		this.discretionaryLevy = discretionaryLevy;
	}

	/**
	 * Gets the admin levy.
	 *
	 * @return the admin levy
	 */
	public BigDecimal getAdminLevy() {
		return adminLevy;
	}

	/**
	 * Sets the admin levy.
	 *
	 * @param adminLevy the new admin levy
	 */
	public void setAdminLevy(BigDecimal adminLevy) {
		this.adminLevy = adminLevy;
	}

	/**
	 * Gets the interest.
	 *
	 * @return the interest
	 */
	public BigDecimal getInterest() {
		return interest;
	}

	/**
	 * Sets the interest.
	 *
	 * @param interest the new interest
	 */
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	/**
	 * Gets the penalty.
	 *
	 * @return the penalty
	 */
	public BigDecimal getPenalty() {
		return penalty;
	}

	/**
	 * Sets the penalty.
	 *
	 * @param penalty the new penalty
	 */
	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public BigDecimal getTotal() {
	/*	if(total!=null && penalty!=null) {
			 double t = total.doubleValue()<0?(total.doubleValue()*-1):total.doubleValue();
			 double p = penalty.doubleValue()<0?(penalty.doubleValue()*-1):penalty.doubleValue();
			 if (t<p) {
			   double tot = penalty.doubleValue();
			   double pen = total.doubleValue();
		       total = BigDecimal.valueOf(tot);
		       penalty = BigDecimal.valueOf(pen);
			 }
		 }
	*/
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the sars files.
	 *
	 * @return the sars files
	 */
	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	/**
	 * Sets the sars files.
	 *
	 * @param sarsFiles the new sars files
	 */
	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	/**
	 * Gets the sars employer detail.
	 *
	 * @return the sars employer detail
	 */
	public SarsEmployerDetail getSarsEmployerDetail() {
		return sarsEmployerDetail;
	}

	/**
	 * Sets the sars employer detail.
	 *
	 * @param sarsEmployerDetail the new sars employer detail
	 */
	public void setSarsEmployerDetail(SarsEmployerDetail sarsEmployerDetail) {
		this.sarsEmployerDetail = sarsEmployerDetail;
	}


	/**
	 * Gets the ts 2.
	 *
	 * @return the ts 2
	 */
	public TS2 getTs2() {
		return ts2;
	}


	/**
	 * Sets the ts 2.
	 *
	 * @param ts2 the new ts 2
	 */
	public void setTs2(TS2 ts2) {
		this.ts2 = ts2;
	}

	public Long getNumberEmployersInSARSLevyFiles() {
		return numberEmployersInSARSLevyFiles;
	}

	public void setNumberEmployersInSARSLevyFiles(Long numberEmployersInSARSLevyFiles) {
		this.numberEmployersInSARSLevyFiles = numberEmployersInSARSLevyFiles;
	}

	public Long getNumberEmployersInSARSEmployerFiles() {
		return numberEmployersInSARSEmployerFiles;
	}

	public void setNumberEmployersInSARSEmployerFiles(Long numberEmployersInSARSEmployerFiles) {
		this.numberEmployersInSARSEmployerFiles = numberEmployersInSARSEmployerFiles;
	}

	public Long getNumberEmployersInSETAInvoiceFile() {
		return numberEmployersInSETAInvoiceFile;
	}

	public void setNumberEmployersInSETAInvoiceFile(Long numberEmployersInSETAInvoiceFile) {
		this.numberEmployersInSETAInvoiceFile = numberEmployersInSETAInvoiceFile;
	}

	public List<SarsEmployerDetail> getEmployerSummary() {
		return employerSummary;
	}

	public void setEmployerSummary(List<SarsEmployerDetail> employerSummary) {
		this.employerSummary = employerSummary;
	}

	public Double getMandatoryLevyD() {
		return mandatoryLevyD==null?0.0:mandatoryLevyD;
	}

	public void setMandatoryLevyD(Double mandatoryLevyD) {
		this.mandatoryLevyD = mandatoryLevyD;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public Double getDiscretionaryLevyD() {
		return discretionaryLevyD==null?0.0:discretionaryLevyD;
	}

	public void setDiscretionaryLevyD(Double discretionaryLevyD) {
		this.discretionaryLevyD = discretionaryLevyD;
	}

	public Double getAdminLevyD() {
		return adminLevyD==null?0.0:adminLevyD;
	}

	public void setAdminLevyD(Double adminLevyD) {
		this.adminLevyD = adminLevyD;
	}

	public Double getInterestD() {
		return interestD==null?0.0:interestD;
	}

	public void setInterestD(Double interestD) {
		this.interestD = interestD;
	}

	public Double getPenaltyD() {
		return penaltyD==null?0.0:penaltyD;
	}

	public void setPenaltyD(Double penaltyD) {
		this.penaltyD = penaltyD;
	}

	public Double getTotalD() {
		return totalD==null?0.0:totalD;
	}

	public void setTotalD(Double totalD) {
		this.totalD = totalD;
	}

	public Integer getChamberCode() {
		return chamberCode;
	}

	public void setChamberCode(Integer chamberCode) {
		this.chamberCode = chamberCode;
	}

	public Double getLevyAmountD() {
		return levyAmountD;
	}

	public void setLevyAmountD(Double levyAmountD) {
		this.levyAmountD = levyAmountD;
	}

	public Double getInterSetaTransfer() {
		return interSetaTransfer;
	}

	public void setInterSetaTransfer(Double interSetaTransfer) {
		this.interSetaTransfer = interSetaTransfer;
	}
	
	public Boolean getCanAppearOnMgPayments() {
		return canAppearOnMgPayments;
	}

	public void setCanAppearOnMgPayments(Boolean canAppearOnMgPayments) {
		this.canAppearOnMgPayments = canAppearOnMgPayments;
	}

}