package haj.com.bean;
import java.io.Serializable;
import java.math.BigDecimal;



// TODO: Auto-generated Javadoc
/**
 * The Class AttachmentBean.
 */
public class HistoricalGrantsBean implements Serializable {

	private Integer finYear;
	private BigDecimal mandatoryGrants;
	private BigDecimal descretionaryGrants;
	private BigDecimal total ;
	


	public HistoricalGrantsBean() {
		super();
		
		
	}



	/**
	 * Instantiates a new attachment bean.
	 *
	 * @param filename the filename
	 * @param file the file
	 */
	public HistoricalGrantsBean(Integer finYear, BigDecimal mandatoryGrants, BigDecimal descretionaryGrants) {
		super();
		this.finYear = finYear;
		this.mandatoryGrants= mandatoryGrants;
		this.descretionaryGrants =descretionaryGrants;
		this.total = new BigDecimal( mandatoryGrants.doubleValue() +  descretionaryGrants.doubleValue());
	}



	public Integer getFinYear() {
		return finYear;
	}



	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}



	public BigDecimal getMandatoryGrants() {
		return mandatoryGrants;
	}



	public void setMandatoryGrants(BigDecimal mandatoryGrants) {
		this.mandatoryGrants = mandatoryGrants;
	}



	public BigDecimal getDescretionaryGrants() {
		return descretionaryGrants;
	}



	public void setDescretionaryGrants(BigDecimal descretionaryGrants) {
		this.descretionaryGrants = descretionaryGrants;
	}



	public BigDecimal getTotal() {
		return total;
	}



	public void setTotal(BigDecimal total) {
		this.total = total;
	}



}
