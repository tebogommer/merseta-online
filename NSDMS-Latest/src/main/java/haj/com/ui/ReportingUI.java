package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.EmployeeReportBean;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.MailLog;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ReportingService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "reportingUI")
@ViewScoped
public class ReportingUI extends AbstractUI {

	/* Service Levels */
	private ReportingService reportingService = new ReportingService();

	/* VARS */
	private boolean displayReport = false;
	private boolean displayReport1 = false;
	private boolean displayReport2 = false;
	private boolean displayReport3 = false;
	private boolean displayReport4 = false;

	/* Lists */
	private List<EmployeeReportBean> employeeReportList = null;
	private List<EmployeeReportBean> companyReportList = null;
	private List<EmployeeReportBean> grantReportList = null;
	private List<EmployeeReportBean> cRMReportList = null;
	private List<EmployeeReportBean> cLOReportList = null;
	
	/* dataModels */
	private LazyDataModel<EmployeeReportBean> cLODataModel;
	private LazyDataModel<EmployeeReportBean> cRMDataModel;
	private LazyDataModel<Company> companiesInCloRegionDataModel;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
	}

	public void reportButton() {
		try {
			if (displayReport) {
				displayReport = false;
			} else {
				employeeReportList = reportingService.allEmployeeList();
				displayReport = true;
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
		
		try {
			if (displayReport1) {
				displayReport1 = false;
			} else {
				companyReportList = reportingService.allCompanyList();
				displayReport1 = true;
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
		try {
			if (displayReport2) {
				displayReport2 = false;
			} else {
				grantReportList = reportingService.allGrantList();
				displayReport2 = true;
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
		try {
			if (displayReport3) {
				displayReport3 = false;
			} else {		
				/* companiesInCloRegionDataModelInfo(); */
				displayReport3 = true;
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
		try {
			if (displayReport4) {
				displayReport4 = false;
			} else {
				cLOReportList = reportingService.allCLOList();
				displayReport4 = true;
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
		
	}
	
	/*
	 * public void cRMDataModelInfo(){ cRMDataModel = new
	 * LazyDataModel<EmployeeReportBean>() { private static final long
	 * serialVersionUID = 1L; private List<EmployeeReportBean> retorno = new
	 * ArrayList<EmployeeReportBean>();
	 * 
	 * @Override public List<EmployeeReportBean> load(int first, int pageSize,
	 * String sortField, SortOrder sortOrder, Map<String, Object> filters) { try {
	 * if (sortField == null || sortField.isEmpty()) {
	 * 
	 * sortField = "id"; sortOrder = SortOrder.DESCENDING; } retorno =
	 * reportingService.allCRMList(EmployeeReportBean.class, first, pageSize,
	 * sortField, sortOrder, filters);
	 * cRMDataModel.setRowCount(reportingService.countAllCRM(EmployeeReportBean.
	 * class, filters)); } catch (Exception e) { logger.fatal(e);
	 * e.printStackTrace(); } return retorno; }
	 * 
	 * }; }
	 * 
	 * public void companiesInCloRegionDataModelInfo(){
	 * companiesInCloRegionDataModel = new LazyDataModel<Company>() { private static
	 * final long serialVersionUID = 1L; private List<Company> retorno = new
	 * ArrayList<Company>();
	 * 
	 * @Override public List<Company> load(int first, int pageSize, String
	 * sortField, SortOrder sortOrder, Map<String, Object> filters) { try { if
	 * (sortField == null || sortField.isEmpty()) {
	 * 
	 * sortField = "id"; sortOrder = SortOrder.DESCENDING; } retorno =
	 * reportingService.companiesInCloRegion(Company.class, first, pageSize,
	 * sortField, sortOrder, filters);
	 * companiesInCloRegionDataModel.setRowCount(reportingService.
	 * countAllcompaniesInCloRegion(Company.class, filters)); } catch (Exception e)
	 * { logger.fatal(e); e.printStackTrace(); } return retorno; }
	 * 
	 * }; }
	 */


	/* getters and setters */
	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public List<EmployeeReportBean> getEmployeeReportList() {
		return employeeReportList;
	}

	public void setEmployeeReportList(List<EmployeeReportBean> employeeReportList) {
		this.employeeReportList = employeeReportList;
	}

	public boolean isDisplayReport1() {
		return displayReport1;
	}

	public void setDisplayReport1(boolean displayReport1) {
		this.displayReport1 = displayReport1;
	}

	public List<EmployeeReportBean> getCompanyReportList() {
		return companyReportList;
	}

	public void setCompanyReportList(List<EmployeeReportBean> companyReportList) {
		this.companyReportList = companyReportList;
	}

	public boolean isDisplayReport2() {
		return displayReport2;
	}

	public void setDisplayReport2(boolean displayReport2) {
		this.displayReport2 = displayReport2;
	}

	public List<EmployeeReportBean> getGrantReportList() {
		return grantReportList;
	}

	public void setGrantReportList(List<EmployeeReportBean> grantReportList) {
		this.grantReportList = grantReportList;
	}

	public boolean isDisplayReport3() {
		return displayReport3;
	}

	public void setDisplayReport3(boolean displayReport3) {
		this.displayReport3 = displayReport3;
	}

	public boolean isDisplayReport4() {
		return displayReport4;
	}

	public void setDisplayReport4(boolean displayReport4) {
		this.displayReport4 = displayReport4;
	}

	public List<EmployeeReportBean> getcRMReportList() {
		return cRMReportList;
	}

	public void setcRMReportList(List<EmployeeReportBean> cRMReportList) {
		this.cRMReportList = cRMReportList;
	}

	public List<EmployeeReportBean> getcLOReportList() {
		return cLOReportList;
	}

	public void setcLOReportList(List<EmployeeReportBean> cLOReportList) {
		this.cLOReportList = cLOReportList;
	}

	public LazyDataModel<EmployeeReportBean> getcLODataModel() {
		return cLODataModel;
	}

	public void setcLODataModel(LazyDataModel<EmployeeReportBean> cLODataModel) {
		this.cLODataModel = cLODataModel;
	}

	public LazyDataModel<EmployeeReportBean> getcRMDataModel() {
		return cRMDataModel;
	}

	public void setcRMDataModel(LazyDataModel<EmployeeReportBean> cRMDataModel) {
		this.cRMDataModel = cRMDataModel;
	}

	public LazyDataModel<Company> getCompaniesInCloRegionDataModel() {
		return companiesInCloRegionDataModel;
	}

	public void setCompaniesInCloRegionDataModel(LazyDataModel<Company> companiesInCloRegionDataModel) {
		this.companiesInCloRegionDataModel = companiesInCloRegionDataModel;
	}

}
