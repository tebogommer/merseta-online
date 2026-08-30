package haj.com.sars;

import java.math.BigDecimal;
import java.util.Date;

import haj.com.annotations.TechFiniumColumn;
import haj.com.framework.IDataEntity;


public class SarsMandatoryLevyDetails  implements IDataEntity,Cloneable{
	
	@TechFiniumColumn(heading = "Arrival Date")
	private Date arrivalDate1;

	@TechFiniumColumn(heading = "Levy Number")
	private String refNo;
 	
	@TechFiniumColumn(heading = "Mandatory Levy", formula = "sum")
	private BigDecimal mandatoryLevy;

	@TechFiniumColumn(heading = "Discretionary Levy", formula = "sum")
	private BigDecimal discretionaryLevy;

	@TechFiniumColumn(heading = "Admin Levy", formula = "sum")
	private BigDecimal adminLevy;

	@TechFiniumColumn(heading = "Interest", formula = "sum")
	private BigDecimal interest;

	@TechFiniumColumn(heading = "Penalty", formula = "sum")
	private BigDecimal penalty;

	@TechFiniumColumn(heading = "Total", formula = "sum")
	private BigDecimal total;

	@TechFiniumColumn(heading = "Scheme Year")
	private Integer schemeYear;

	public SarsMandatoryLevyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
//
	public SarsMandatoryLevyDetails(Date arrivalDate1, String refNo, BigDecimal mandatoryLevy,
			BigDecimal discretionaryLevy, BigDecimal adminLevy, BigDecimal interest, BigDecimal penalty,
			BigDecimal total, Integer schemeYear) {
		super();
		this.arrivalDate1 = arrivalDate1;
		this.refNo = refNo;
		this.mandatoryLevy = mandatoryLevy;
		this.discretionaryLevy = discretionaryLevy;
		this.adminLevy = adminLevy;
		this.interest = interest;
		this.penalty = penalty;
		this.total = total;
		this.schemeYear = schemeYear;
	}

	public Date getArrivalDate1() {
		return arrivalDate1;
	}

	public void setArrivalDate1(Date arrivalDate1) {
		this.arrivalDate1 = arrivalDate1;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public BigDecimal getMandatoryLevy() {
		return mandatoryLevy;
	}

	public void setMandatoryLevy(BigDecimal mandatoryLevy) {
		this.mandatoryLevy = mandatoryLevy;
	}

	public BigDecimal getDiscretionaryLevy() {
		return discretionaryLevy;
	}

	public void setDiscretionaryLevy(BigDecimal discretionaryLevy) {
		this.discretionaryLevy = discretionaryLevy;
	}

	public BigDecimal getAdminLevy() {
		return adminLevy;
	}

	public void setAdminLevy(BigDecimal adminLevy) {
		this.adminLevy = adminLevy;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

	

	
}
