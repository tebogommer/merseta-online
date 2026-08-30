package haj.com.bean;

import java.io.Serializable;

import haj.com.entity.Company;
import haj.com.entity.lookup.SICCode;
import haj.com.sars.SarsEmployerDetail;

public class SarsEmployerCompanyBean implements Serializable {

	private Company company;
	private SarsEmployerDetail sarsEmployerDetail;
	private SICCode sicCode;
	
	public SarsEmployerCompanyBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SarsEmployerCompanyBean(Company company, SarsEmployerDetail sarsEmployerDetail) {
		super();
		this.company = company;
		this.sarsEmployerDetail = sarsEmployerDetail;
	}

	
	
	public SarsEmployerCompanyBean(Company company, SarsEmployerDetail sarsEmployerDetail, SICCode sicCode) {
		super();
		this.company = company;
		this.sarsEmployerDetail = sarsEmployerDetail;
		this.sicCode = sicCode;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SarsEmployerDetail getSarsEmployerDetail() {
		return sarsEmployerDetail;
	}

	public void setSarsEmployerDetail(SarsEmployerDetail sarsEmployerDetail) {
		this.sarsEmployerDetail = sarsEmployerDetail;
	}

	public SICCode getSicCode() {
		return sicCode;
	}

	public void setSicCode(SICCode sicCode) {
		this.sicCode = sicCode;
	}
	
	
}
