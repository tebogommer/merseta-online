package haj.com.bean;

import haj.com.entity.OfoCodes;

public class EmployeeEquityProfileBean 
{
	private String  occupationCat;
	private Long totalBlackAfricanFemale;
	private Long totalColouredFemale;
	private Long totalIndianOrAsianFemale;
	private Long totalWhiteFemale;
	private Long totalFemaleOther;
	private Long totalBlackAfricanMale;
	private Long totalColouredMale;
	private Long totalIndianAsianMale;
	private Long totalWhiteMale;
	private Long totalMaleOther;
	private Long totalFemale;
	private Long totalMale;
	private Long ofoCode;
	
	
	
	public EmployeeEquityProfileBean() {
		super();
		// TODO Auto-generated constructor stub
	}



	public EmployeeEquityProfileBean(OfoCodes ofo, long totalBlackAfricanFemale, long totalColouredFemale,
			long totalIndianOrAsianFemale, long totalWhiteFemale, long totalFemaleOther,
			long totalBlackAfricanMale, long totalColouredMale, long totalIndianAsianMale,
			long totalWhiteMale, long totalMaleOther, long totalFemale, long totalMale) {
		super();
		if(ofo !=null)
		{
			this.occupationCat= ofo.getOfoDescription();
		}
		
		this.totalBlackAfricanFemale = totalBlackAfricanFemale;
		this.totalColouredFemale = totalColouredFemale;
		this.totalIndianOrAsianFemale = totalIndianOrAsianFemale;
		this.totalWhiteFemale = totalWhiteFemale;
		this.totalFemaleOther = totalFemaleOther;
		this.totalBlackAfricanMale = totalBlackAfricanMale;
		this.totalColouredMale = totalColouredMale;
		this.totalIndianAsianMale = totalIndianAsianMale;
		this.totalWhiteMale = totalWhiteMale;
		this.totalMaleOther = totalMaleOther;
		this.totalFemale = totalFemale;
		this.totalMale = totalMale;
	}



	public String getOccupationCat() {
		return occupationCat;
	}



	public void setOccupationCat(String occupationCat) {
		this.occupationCat = occupationCat;
	}



	public Long getTotalBlackAfricanFemale() {
		return totalBlackAfricanFemale;
	}



	public void setTotalBlackAfricanFemale(Long totalBlackAfricanFemale) {
		this.totalBlackAfricanFemale = totalBlackAfricanFemale;
	}



	public Long getTotalColouredFemale() {
		return totalColouredFemale;
	}



	public void setTotalColouredFemale(Long totalColouredFemale) {
		this.totalColouredFemale = totalColouredFemale;
	}



	public Long getTotalIndianOrAsianFemale() {
		return totalIndianOrAsianFemale;
	}



	public void setTotalIndianOrAsianFemale(Long totalIndianOrAsianFemale) {
		this.totalIndianOrAsianFemale = totalIndianOrAsianFemale;
	}



	public Long getTotalWhiteFemale() {
		return totalWhiteFemale;
	}



	public void setTotalWhiteFemale(Long totalWhiteFemale) {
		this.totalWhiteFemale = totalWhiteFemale;
	}



	public Long getTotalFemaleOther() {
		return totalFemaleOther;
	}



	public void setTotalFemaleOther(Long totalFemaleOther) {
		this.totalFemaleOther = totalFemaleOther;
	}



	public Long getTotalBlackAfricanMale() {
		return totalBlackAfricanMale;
	}



	public void setTotalBlackAfricanMale(Long totalBlackAfricanMale) {
		this.totalBlackAfricanMale = totalBlackAfricanMale;
	}



	public Long getTotalColouredMale() {
		return totalColouredMale;
	}



	public void setTotalColouredMale(Long totalColouredMale) {
		this.totalColouredMale = totalColouredMale;
	}



	public Long getTotalIndianAsianMale() {
		return totalIndianAsianMale;
	}



	public void setTotalIndianAsianMale(Long totalIndianAsianMale) {
		this.totalIndianAsianMale = totalIndianAsianMale;
	}



	public Long getTotalWhiteMale() {
		return totalWhiteMale;
	}



	public void setTotalWhiteMale(Long totalWhiteMale) {
		this.totalWhiteMale = totalWhiteMale;
	}



	public Long getTotalMaleOther() {
		return totalMaleOther;
	}



	public void setTotalMaleOther(Long totalMaleOther) {
		this.totalMaleOther = totalMaleOther;
	}



	public Long getTotalFemale() {
		return totalFemale;
	}



	public void setTotalFemale(Long totalFemale) {
		this.totalFemale = totalFemale;
	}



	public Long getTotalMale() {
		return totalMale;
	}



	public void setTotalMale(Long totalMale) {
		this.totalMale = totalMale;
	}



	public Long getOfoCode() {
		return ofoCode;
	}



	public void setOfoCode(Long ofoCode) {
		this.ofoCode = ofoCode;
	}
	
	
	
	
	

}
