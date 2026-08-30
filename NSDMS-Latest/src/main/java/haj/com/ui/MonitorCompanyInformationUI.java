package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.TrainingProviderApplicationService;


@ManagedBean(name = "monitorCompanyInformationUI")
@ViewScoped
public class MonitorCompanyInformationUI extends AbstractUI {

	/* Entity Level */
	private Company selectedCompany = null;
	
	/* Service Level */
	private CompanyService companyService = new CompanyService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService(); 
	
	/* Lazy Data Models */
	private LazyDataModel<Company> dataModelComapny;
	
	/* VARS */
	private Long companyID = null;
	private boolean useComapnyID = false;
	private boolean trainingProvider = false;
	
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
		getSessionUI().setValidationErrors("");
		populateCompanies();
	}
	
	private void populateCompanies() throws Exception{
		companyInfo();
	}
	
	public void applyFilter(){
		useComapnyID = true;
		companyInfo();
	}
	
	public void clearFilter(){
		useComapnyID = false;
		companyID = null;
		companyInfo();
	}
	
	public void companyInfo() {
		dataModelComapny = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (useComapnyID && companyID != null) {
						companyList = companyService.allCompaniesByID(first, pageSize, sortField, sortOrder, filters, companyID);
						dataModelComapny.setRowCount(companyService.countAllCompaniesByID(filters));
					} else {
						companyList = companyService.allCompanyNoDataResolved(Company.class, first, pageSize, sortField, sortOrder, filters);
						dataModelComapny.setRowCount(companyService.count(Company.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(Company object) {
				return object.getId();
			}
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void deselectComapny(){
		try {
			getSessionUI().setValidationErrors("");
			selectedCompany = null;
			addInfoMessage("Company De-selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectCompany(){
		try {
			selectedCompany = companyService.findByKeyNoResolveData(selectedCompany.getId());
			companyService.resolveCompanyAddresses(selectedCompany);
			
			trainingProvider = false;
			if (trainingProviderApplicationService.countTrainingProviderApplicationByComapnyID(selectedCompany.getId()) > 0) {
				trainingProvider = true;
			}
			if (companyLearnersService.countCompanyLearnersByEmployerId(selectedCompany.getId()) > 0) {
				trainingProvider = true;
			}
			getSessionUI().setValidationErrors("");
			
			// check if company approved training provider
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		} 
	}
	
	public void runValidiationChecks(){
		try {
			getSessionUI().setValidationErrors("");
			Company compForValidiation = companyService.findByKey(selectedCompany.getId());
			companyService.resolveCompanyAddresses(compForValidiation);
			compForValidiation.setValidiationRanDate(getNow());
			companyService.updateCompanyBatchInformation(compForValidiation);
			compForValidiation = null;
			addInfoMessage("Check Complete No Validiation Errors on Company and/or address level");
		} catch (javax.validation.ConstraintViolationException e) {
			addErrorMessage("Validiation Expection. Review Errors");
			extractValidationErrors(e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateCompanyInformation(){
		try {
			if (selectedCompany == null && selectedCompany.getId() == null) {
				throw new Exception("Unable to locate comapny information, contact support!");
			} else {
				companyService.updateCompanyBatchInformation(selectedCompany);
			}
			getSessionUI().setValidationErrors("");
			selectedCompany = null;
			addInfoMessage("Update Complete");
		} catch (javax.validation.ConstraintViolationException e) {
			addErrorMessage("Validiation Expection. Review Errors");
			extractValidationErrors(e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} 
	}
	
	/** getters and setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public LazyDataModel<Company> getDataModelComapny() {
		return dataModelComapny;
	}

	public void setDataModelComapny(LazyDataModel<Company> dataModelComapny) {
		this.dataModelComapny = dataModelComapny;
	}

	public boolean isUseComapnyID() {
		return useComapnyID;
	}

	public void setUseComapnyID(boolean useComapnyID) {
		this.useComapnyID = useComapnyID;
	}

	public boolean isTrainingProvider() {
		return trainingProvider;
	}

	public void setTrainingProvider(boolean trainingProvider) {
		this.trainingProvider = trainingProvider;
	}
	
		
}