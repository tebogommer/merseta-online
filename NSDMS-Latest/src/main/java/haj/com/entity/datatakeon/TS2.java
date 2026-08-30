package haj.com.entity.datatakeon;
// Generated Oct 16, 2017 5:23:37 PM by Hibernate Tools 4.3.1


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.TechFiniumColumn;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Mandatory Grants.
 * 
select sum(b.document_amount_d) as documentAmountD from t_s2 b  
where b.posting_date_d between '2017-08-01' and '2017-08-31'
and b.vendor_id in 
   (select c.levy_number from t_s1 c where c.levy_number = b.vendor_id  and b.scheme_year = c.scheme_year and c.status_description in ('Paid','Approved'))
   
 */

@NamedNativeQueries({
	
	@NamedNativeQuery(
			name = "NQ_NATIVE_invoicesForSchemeYear",
			query = "select a.scheme_year as schemeYear , sum(a.document_amount_d) as documentAmountD from t_s2 a " + 
					"where a.scheme_year = :setaSchemeYear " + 
					"and a.vendor_id in (select b.levy_number from t_s1 b " + 
					"					where b.scheme_year  = :setaSchemeYear " + 
					"					and b.status_description in ('Paid','Approved'))",
			resultSetMapping	= "SchemeYearInvoiceResult"	
			)	,
	@NamedNativeQuery(
			name = "NQ_NATIVE_invoicesForPeriod",
			query = "select sum(b.document_amount_d) as documentAmountD from t_s2 b  " + 
					"where b.posting_date_d between :fromDate and :toDate "+
					"and b.scheme_year = :setaSchemeYear ",
			resultSetMapping	= "ForPeriodInvoiceResult"	
			)
})

//@SqlResultSetMapping(name="SchemeYearInvoiceResult", classes = {
//	    @ConstructorResult(targetClass = TS2.class, 
//	    columns = {@ColumnResult(name="schemeYear"), @ColumnResult(name="documentAmountD")})
//	})


@SqlResultSetMappings({
	@SqlResultSetMapping(name="SchemeYearInvoiceResult", classes = {
		    @ConstructorResult(targetClass = TS2.class, 
		    columns = {@ColumnResult(name="schemeYear"), @ColumnResult(name="documentAmountD")})
		}),
	@SqlResultSetMapping(name="ForPeriodInvoiceResult", classes = {
		    @ConstructorResult(targetClass = TS2.class, 
		    columns = {@ColumnResult(name="documentAmountD")
		    })
		})
    })


@Entity
@Table(name="t_s2"
    
)
public class TS2  implements IDataEntity {


     /** The id. */
	@TechFiniumColumn(suppress=true)
     private Long id;
     
     /** The posting date. */
	 @CSVAnnotation(name = "posting_date", className = String.class)
     private String postingDate;
     
     /** The vendor id. */
	 @CSVAnnotation(name = "vendor_id", className = String.class)
     private String vendorId;
     
     /** The vendor name. */
	 @CSVAnnotation(name = "vendor_name", className = String.class)
     private String vendorName;
     
     /** The voucher number. */
     @TechFiniumColumn(suppress=true)
     @CSVAnnotation(name = "voucher_number", className = String.class)
     private String voucherNumber;
     
     /** The document type. */
     @CSVAnnotation(name = "document_type", className = String.class)
     private String documentType;
     
     /** The document date. */
     @CSVAnnotation(name = "document_date", className = String.class)
     private String documentDate;
     
     /** The document number. */
     @CSVAnnotation(name = "document_number", className = String.class)
     private String documentNumber;
     
     /** The transaction description. */
     @CSVAnnotation(name = "transaction_description", className = String.class)
     private String transactionDescription;
     
     /** The grant type. */
     @TechFiniumColumn(suppress=true)
     @CSVAnnotation(name = "grant_type", className = String.class)
     private String grantType;
     
     /** The grnt type gp code. */
     @TechFiniumColumn(suppress=true)
     @CSVAnnotation(name = "grnt_type_gp_code", className = Integer.class)
     private Integer grntTypeGpCode;
     
     /** The scheme year. */
     @CSVAnnotation(name = "scheme_year", className = Integer.class)
     private Integer schemeYear;
     
     /** The chamber code. */
     @CSVAnnotation(name = "chamber_code", className = Integer.class)
     private Integer chamberCode;
     
     /** The gp code scheme year. */
     @TechFiniumColumn(suppress=true)
     @CSVAnnotation(name = "gp_code_scheme_year", className = String.class)
     private String gpCodeSchemeYear;
     
     /** The account code. */
     @CSVAnnotation(name = "account_code", className = String.class)
     private String accountCode;
     
     /** The grant allocation. */
     @CSVAnnotation(name = "grant_allocation", className = String.class)
     private String grantAllocation;
     
     /** The document amount. */
 	@TechFiniumColumn(suppress=true)
 	@CSVAnnotation(name = "document_amount", className = String.class)
    private String documentAmount;
     
     /** The current trx amount. */
 	@TechFiniumColumn(suppress=true)
 	@CSVAnnotation(name = "current_trx_amount", className = String.class)
     private String currentTrxAmount;
     
     /** The document status. */
     private String documentStatus;
     
     /** The trx source. */
     @CSVAnnotation(name = "trx_Source", className = String.class)
     private String trxSource;
     
     /** The voided. */
     private String voided;
     
     /** The posted date. */
 	@TechFiniumColumn(suppress=true)
     private String postedDate;
     
     /** The document status 2. */
 	@TechFiniumColumn(suppress=true)
     private String documentStatus2;
     
     /** The chamber. */
 	@CSVAnnotation(name = "chamber", className = String.class)
     private String chamber;
     
     /** The tx. */
 	@TechFiniumColumn(suppress=true)
     private String tx;
     
     /** The ty. */
 	@TechFiniumColumn(suppress=true)
     private String ty;
     
     /** The ts 1. */
	@TechFiniumColumn(suppress=true)
     private TS1 ts1;
     
     /** The document date D. */
 	 @TechFiniumColumn(suppress=true)
     private Date documentDateD;
     
     /** The posted date D. */
 	 @TechFiniumColumn(suppress=true)
     private Date postedDateD;
     
     /** The document amount D. */
 	@TechFiniumColumn(heading = "Document Amount", formula = "sum")
     private Double documentAmountD;
     
     /** The current trx amount D. */
 	@TechFiniumColumn(suppress=true)
     private Double currentTrxAmountD;
     
     /** The newfile. */
     @TechFiniumColumn(suppress=true)
     private Boolean newfile;

     @TechFiniumColumn(suppress=true)
     private Integer year;
     @TechFiniumColumn(suppress=true)
     private Integer month;
    /**
     * Instantiates a new ts2.
     */
     
     /** The posted date D. */
 	 @TechFiniumColumn(suppress=true)
 	 @CSVAnnotation(name = "posting_date", className = Date.class, datePattern="dd-MMM-yy")
     private Date postingDateD;    
 	 
 	 @TechFiniumColumn(suppress=true)
 	 @CSVAnnotation(name = "financial_year", className = Integer.class)
 	 private Integer financialYear;
     
    public TS2() {
    }


	/**
      * Instantiates a new ts2.
      *
      * @param schemeYear the scheme year
      * @param vendorId the vendor id
      * @param documentAmountD the document amount D
      */
     public TS2( Integer schemeYear,String vendorId, Double documentAmountD) {
		super();
		this.vendorId = vendorId;
		this.schemeYear = schemeYear;
		this.documentAmountD = documentAmountD;
		
	}



	public TS2(Date documentDateD, Double documentAmountD) {
		super();
		this.documentDateD = documentDateD;
		this.documentAmountD = documentAmountD;
	}



	public TS2(Integer year, Integer month, Double documentAmountD) {
		super();
		this.year = year;
		this.month = month;
		this.documentAmountD = documentAmountD;
	}

	public TS2(Double documentAmountD) {
		super();
		this.documentAmountD = documentAmountD;
	}


	/**
	 * Instantiates a new ts2.
	 *
	 * @param schemeYear the scheme year
	 * @param documentAmountD the document amount D
	 */
	public TS2(Integer schemeYear, Double documentAmountD) {
		super();
		this.schemeYear = schemeYear;
		this.documentAmountD = documentAmountD;
		
	}




	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
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
     * Gets the posting date.
     *
     * @return the posting date
     */
    @Column(name="posting_date")
    public String getPostingDate() {
        return this.postingDate;
    }
    
    /**
     * Sets the posting date.
     *
     * @param postingDate the new posting date
     */
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    
    /**
     * Gets the vendor id.
     *
     * @return the vendor id
     */
    @Column(name="vendor_id")
    public String getVendorId() {
        return this.vendorId;
    }
    
    /**
     * Sets the vendor id.
     *
     * @param vendorId the new vendor id
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    
    /**
     * Gets the vendor name.
     *
     * @return the vendor name
     */
    @Column(name="vendor_name")
    public String getVendorName() {
        return this.vendorName;
    }
    
    /**
     * Sets the vendor name.
     *
     * @param vendorName the new vendor name
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    
    /**
     * Gets the voucher number.
     *
     * @return the voucher number
     */
    @Column(name="voucher_number")
    public String getVoucherNumber() {
        return this.voucherNumber;
    }
    
    /**
     * Sets the voucher number.
     *
     * @param voucherNumber the new voucher number
     */
    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    
    /**
     * Gets the document type.
     *
     * @return the document type
     */
    @Column(name="document_type")
    public String getDocumentType() {
        return this.documentType;
    }
    
    /**
     * Sets the document type.
     *
     * @param documentType the new document type
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    
    /**
     * Gets the document date.
     *
     * @return the document date
     */
    @Column(name="document_date")
    public String getDocumentDate() {
        return this.documentDate;
    }
    
    /**
     * Sets the document date.
     *
     * @param documentDate the new document date
     */
    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    
    /**
     * Gets the document number.
     *
     * @return the document number
     */
    @Column(name="document_number")
    public String getDocumentNumber() {
        return this.documentNumber;
    }
    
    /**
     * Sets the document number.
     *
     * @param documentNumber the new document number
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    
    /**
     * Gets the transaction description.
     *
     * @return the transaction description
     */
    @Column(name="transaction_description")
    public String getTransactionDescription() {
        return this.transactionDescription;
    }
    
    /**
     * Sets the transaction description.
     *
     * @param transactionDescription the new transaction description
     */
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    
    /**
     * Gets the grant type.
     *
     * @return the grant type
     */
    @Column(name="grant_type")
    public String getGrantType() {
        return this.grantType;
    }
    
    /**
     * Sets the grant type.
     *
     * @param grantType the new grant type
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    
    /**
     * Gets the grnt type gp code.
     *
     * @return the grnt type gp code
     */
    @Column(name="grnt_type_gp_code")
    public Integer getGrntTypeGpCode() {
        return this.grntTypeGpCode;
    }
    
    /**
     * Sets the grnt type gp code.
     *
     * @param grntTypeGpCode the new grnt type gp code
     */
    public void setGrntTypeGpCode(Integer grntTypeGpCode) {
        this.grntTypeGpCode = grntTypeGpCode;
    }

    
    /**
     * Gets the scheme year.
     *
     * @return the scheme year
     */
    @Column(name="scheme_year")
    public Integer getSchemeYear() {
        return this.schemeYear;
    }
    
    /**
     * Sets the scheme year.
     *
     * @param schemeYear the new scheme year
     */
    public void setSchemeYear(Integer schemeYear) {
        this.schemeYear = schemeYear;
    }

    
    /**
     * Gets the chamber code.
     *
     * @return the chamber code
     */
    @Column(name="chamber_code")
    public Integer getChamberCode() {
        return this.chamberCode;
    }
    
    /**
     * Sets the chamber code.
     *
     * @param chamberCode the new chamber code
     */
    public void setChamberCode(Integer chamberCode) {
        this.chamberCode = chamberCode;
    }

    
    /**
     * Gets the gp code scheme year.
     *
     * @return the gp code scheme year
     */
    @Column(name="gp_code_scheme_year")
    public String getGpCodeSchemeYear() {
        return this.gpCodeSchemeYear;
    }
    
    /**
     * Sets the gp code scheme year.
     *
     * @param gpCodeSchemeYear the new gp code scheme year
     */
    public void setGpCodeSchemeYear(String gpCodeSchemeYear) {
        this.gpCodeSchemeYear = gpCodeSchemeYear;
    }

    
    /**
     * Gets the account code.
     *
     * @return the account code
     */
    @Column(name="account_code")
    public String getAccountCode() {
        return this.accountCode;
    }
    
    /**
     * Sets the account code.
     *
     * @param accountCode the new account code
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    
    /**
     * Gets the grant allocation.
     *
     * @return the grant allocation
     */
    @Column(name="grant_allocation")
    public String getGrantAllocation() {
        return this.grantAllocation;
    }
    
    /**
     * Sets the grant allocation.
     *
     * @param grantAllocation the new grant allocation
     */
    public void setGrantAllocation(String grantAllocation) {
        this.grantAllocation = grantAllocation;
    }

    
    /**
     * Gets the document amount.
     *
     * @return the document amount
     */
    @Column(name="document_amount")
    public String getDocumentAmount() {
        return this.documentAmount;
    }
    
    /**
     * Sets the document amount.
     *
     * @param documentAmount the new document amount
     */
    public void setDocumentAmount(String documentAmount) {
        this.documentAmount = documentAmount;
    }

    
    /**
     * Gets the current trx amount.
     *
     * @return the current trx amount
     */
    @Column(name="current_trx_amount")
    public String getCurrentTrxAmount() {
        return this.currentTrxAmount;
    }
    
    /**
     * Sets the current trx amount.
     *
     * @param currentTrxAmount the new current trx amount
     */
    public void setCurrentTrxAmount(String currentTrxAmount) {
        this.currentTrxAmount = currentTrxAmount;
    }

    
    /**
     * Gets the document status.
     *
     * @return the document status
     */
    @Column(name="document_status")
    public String getDocumentStatus() {
        return this.documentStatus;
    }
    
    /**
     * Sets the document status.
     *
     * @param documentStatus the new document status
     */
    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    
    /**
     * Gets the trx source.
     *
     * @return the trx source
     */
    @Column(name="trx_Source")
    public String getTrxSource() {
        return this.trxSource;
    }
    
    /**
     * Sets the trx source.
     *
     * @param trxSource the new trx source
     */
    public void setTrxSource(String trxSource) {
        this.trxSource = trxSource;
    }

    
    /**
     * Gets the voided.
     *
     * @return the voided
     */
    @Column(name="voided")
    public String getVoided() {
        return this.voided;
    }
    
    /**
     * Sets the voided.
     *
     * @param voided the new voided
     */
    public void setVoided(String voided) {
        this.voided = voided;
    }

    
    /**
     * Gets the posted date.
     *
     * @return the posted date
     */
    @Column(name="posted_date")
    public String getPostedDate() {
        return this.postedDate;
    }
    
    /**
     * Sets the posted date.
     *
     * @param postedDate the new posted date
     */
    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    
    /**
     * Gets the document status 2.
     *
     * @return the document status 2
     */
    @Column(name="document_status_2")
    public String getDocumentStatus2() {
        return this.documentStatus2;
    }
    
    /**
     * Sets the document status 2.
     *
     * @param documentStatus2 the new document status 2
     */
    public void setDocumentStatus2(String documentStatus2) {
        this.documentStatus2 = documentStatus2;
    }

    
    /**
     * Gets the chamber.
     *
     * @return the chamber
     */
    @Column(name="chamber")
    public String getChamber() {
        return this.chamber;
    }
    
    /**
     * Sets the chamber.
     *
     * @param chamber the new chamber
     */
    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    
    /**
     * Gets the tx.
     *
     * @return the tx
     */
    @Column(name="tx")
    public String getTx() {
        return this.tx;
    }
    
    /**
     * Sets the tx.
     *
     * @param tx the new tx
     */
    public void setTx(String tx) {
        this.tx = tx;
    }

    
    /**
     * Gets the ty.
     *
     * @return the ty
     */
    @Column(name="ty")
    public String getTy() {
        return this.ty;
    }
    
    /**
     * Sets the ty.
     *
     * @param ty the new ty
     */
    public void setTy(String ty) {
        this.ty = ty;
    }


	/**
	 * Gets the ts 1.
	 *
	 * @return the ts 1
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ts1_id", nullable = true)

	public TS1 getTs1() {
		return ts1;
	}



	/**
	 * Sets the ts 1.
	 *
	 * @param ts1 the new ts 1
	 */
	public void setTs1(TS1 ts1) {
		this.ts1 = ts1;
	}


	/**
	 * Gets the document date D.
	 *
	 * @return the document date D
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "document_date_d", length = 19)
	public Date getDocumentDateD() {
		return documentDateD;
	}



	/**
	 * Sets the document date D.
	 *
	 * @param documentDateD the new document date D
	 */
	public void setDocumentDateD(Date documentDateD) {
		this.documentDateD = documentDateD;
	}


	/**
	 * Gets the posted date D.
	 *
	 * @return the posted date D
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posted_date_d", length = 19)
	public Date getPostedDateD() {
		return postedDateD;
	}



	/**
	 * Sets the posted date D.
	 *
	 * @param postedDateD the new posted date D
	 */
	public void setPostedDateD(Date postedDateD) {
		this.postedDateD = postedDateD;
	}


    /**
     * Gets the document amount D.
     *
     * @return the document amount D
     */
    @Column(name="document_amount_d")
	public Double getDocumentAmountD() {
		return documentAmountD;
	}



	/**
	 * Sets the document amount D.
	 *
	 * @param documentAmountD the new document amount D
	 */
	public void setDocumentAmountD(Double documentAmountD) {
		this.documentAmountD = documentAmountD;
	}


    /**
     * Gets the current trx amount D.
     *
     * @return the current trx amount D
     */
    @Column(name="current_trx_amount_d")
	public Double getCurrentTrxAmountD() {
		return currentTrxAmountD;
	}



	/**
	 * Sets the current trx amount D.
	 *
	 * @param currentTrxAmountD the new current trx amount D
	 */
	public void setCurrentTrxAmountD(Double currentTrxAmountD) {
		this.currentTrxAmountD = currentTrxAmountD;
	}



	/**
	 * Gets the newfile.
	 *
	 * @return the newfile
	 */
	public Boolean getNewfile() {
		return newfile;
	}



	/**
	 * Sets the newfile.
	 *
	 * @param newfile the new newfile
	 */
	public void setNewfile(Boolean newfile) {
		this.newfile = newfile;
	}


	@Transient
	public Integer getYear() {
		return year;
	}



	public void setYear(Integer year) {
		this.year = year;
	}


	@Transient
	public Integer getMonth() {
		return month;
	}



	public void setMonth(Integer month) {
		this.month = month;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posting_date_d", length = 19)
	public Date getPostingDateD() {
		return postingDateD;
	}


	public void setPostingDateD(Date postingDateD) {
		this.postingDateD = postingDateD;
	}


	@Column(name="financial_year")
	public Integer getFinancialYear() {
		return financialYear;
	}


	public void setFinancialYear(Integer financialYear) {
		this.financialYear = financialYear;
	}




}


