package haj.com.bean;

import haj.com.entity.OfoCodes;

public class EmpEmploymentStatusBean {
	private String occupationCat;
	private Long PermanentEmployment;
	private Long ContractEmployment;
	private Long Unemployed;
	private Long FormerlyEmployedAtCompany;
	private Long total;

	public EmpEmploymentStatusBean(OfoCodes ofo, long permanentEmployment, long contractEmployment, long unemployed, long formerlyEmployedAtCompany) {
		super();
		if (ofo != null) {
			this.occupationCat = ofo.getOfoDescription();
		}
		PermanentEmployment = permanentEmployment;
		ContractEmployment = contractEmployment;
		Unemployed = unemployed;
		FormerlyEmployedAtCompany = formerlyEmployedAtCompany;
		total = calculateTotal();
	}

	public String getOccupationCat() {
		return occupationCat;
	}

	public void setOccupationCat(String occupationCat) {
		this.occupationCat = occupationCat;
	}

	public Long getPermanentEmployment() {
		return PermanentEmployment;
	}

	public void setPermanentEmployment(Long permanentEmployment) {
		PermanentEmployment = permanentEmployment;
	}

	public Long getContractEmployment() {
		return ContractEmployment;
	}

	public void setContractEmployment(Long contractEmployment) {
		ContractEmployment = contractEmployment;
	}

	public Long getUnemployed() {
		return Unemployed;
	}

	public void setUnemployed(Long unemployed) {
		Unemployed = unemployed;
	}

	public Long getFormerlyEmployedAtCompany() {
		return FormerlyEmployedAtCompany;
	}

	public void setFormerlyEmployedAtCompany(Long formerlyEmployedAtCompany) {
		FormerlyEmployedAtCompany = formerlyEmployedAtCompany;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public long calculateTotal() {

		return PermanentEmployment + ContractEmployment + Unemployed + FormerlyEmployedAtCompany;

	}

}
