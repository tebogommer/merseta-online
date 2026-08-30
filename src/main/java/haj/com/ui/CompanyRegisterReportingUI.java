package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;

import haj.com.bean.CompanyStatusVsSarsBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsFiles;
import haj.com.service.CompanyService;
import haj.com.service.SarsFilesService;

/**
 * The Class CompanyRegisterReportingUI.
 */
@ManagedBean(name = "companyRegisterReportingUI")
@ViewScoped
public class CompanyRegisterReportingUI extends AbstractUI {
	
	/** Entity */
	private SarsFiles lastestSarsFile;

	/** Service */
	private CompanyService companyService = null;
	private SarsFilesService sarsFilesService = null;

	/** Array Lists */
	private List<CompanyStatusVsSarsBean> companyStatusVsSarsStatusList = new ArrayList<>();
	private List<ByChamberReportBean> companiesByChamberSizeList = new ArrayList<>();

	/** Display Booleans */
	private boolean displayCompanyStatusReport = false;
	private boolean displayMersetaCompaniesByChamberSize = false;

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

	/**
	 * runInit()
	 * 
	 * @throws Exception
	 */
	private void runInit() throws Exception {
		populateDefaultInformation();
	}
	
	/**
	 * Populates the default paramters for selection
	 * 
	 * @throws Exception
	 */
	private void populateDefaultInformation() throws Exception {
		populateServiceLayers();
		clearInformation();
		populateLastestSarsFile();
	}

	private void populateLastestSarsFile() throws Exception{
		lastestSarsFile = sarsFilesService.latestSarsLevyFile();
	}

	/**
	 * Populates 
	 */
	private void populateServiceLayers() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (sarsFilesService == null) {
			sarsFilesService = new SarsFilesService();
		}
	}

	/**
	 * Clears array Lists and reporting beans used
	 */
	private void clearInformation() throws Exception {
		companyStatusVsSarsStatusList = new ArrayList<>();
		companiesByChamberSizeList = new ArrayList<>();
		displayCompanyStatusReport = false;
		displayMersetaCompaniesByChamberSize = false;
	}
	
	/**
	 * On Tab Change will populate the required information for reports to
	 * download
	 */
	public void onTabChange(TabChangeEvent event) {
		try {
			clearInformation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateComapnyStatusReport(){
		try {
			clearInformation();
			companyStatusVsSarsStatusList = companyService.populateDistinctHostingCompanyProcessByUserId(lastestSarsFile.getId());
			displayCompanyStatusReport = true;
			addInfoMessage("Generation Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateCompanyChamberSizeReport(){
		try {
			clearInformation();
			companiesByChamberSizeList = companyService.generateReportActiveMersetaCompaniesReport();
			displayMersetaCompaniesByChamberSize = true;
			addInfoMessage("Generation Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public SarsFiles getLastestSarsFile() {
		return lastestSarsFile;
	}

	public void setLastestSarsFile(SarsFiles lastestSarsFile) {
		this.lastestSarsFile = lastestSarsFile;
	}

	public List<CompanyStatusVsSarsBean> getCompanyStatusVsSarsStatusList() {
		return companyStatusVsSarsStatusList;
	}

	public void setCompanyStatusVsSarsStatusList(List<CompanyStatusVsSarsBean> companyStatusVsSarsStatusList) {
		this.companyStatusVsSarsStatusList = companyStatusVsSarsStatusList;
	}

	public boolean isDisplayCompanyStatusReport() {
		return displayCompanyStatusReport;
	}

	public void setDisplayCompanyStatusReport(boolean displayCompanyStatusReport) {
		this.displayCompanyStatusReport = displayCompanyStatusReport;
	}

	public boolean isDisplayMersetaCompaniesByChamberSize() {
		return displayMersetaCompaniesByChamberSize;
	}

	public void setDisplayMersetaCompaniesByChamberSize(boolean displayMersetaCompaniesByChamberSize) {
		this.displayMersetaCompaniesByChamberSize = displayMersetaCompaniesByChamberSize;
	}

	public List<ByChamberReportBean> getCompaniesByChamberSizeList() {
		return companiesByChamberSizeList;
	}

	public void setCompaniesByChamberSizeList(List<ByChamberReportBean> companiesByChamberSizeList) {
		this.companiesByChamberSizeList = companiesByChamberSizeList;
	}

}
