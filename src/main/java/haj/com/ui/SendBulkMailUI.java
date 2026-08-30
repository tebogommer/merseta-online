package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.SDFCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.SDFCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class SendBulkMailUI.
 */
@ManagedBean(name = "sendBulkMailUI")
@ViewScoped
public class SendBulkMailUI extends AbstractUI { 
	
	/** Entity Level */
	private Company selecetdCompany = null;
	
	/** Service Level */
	private CompanyService companyService = new CompanyService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	
	/** Vars */
	private String generalMessage;
	
	/** Lazy data models */
	private LazyDataModel<Company> dataModel;
	
	/** Array Lists */
	private List<Company> companiesSelected = new ArrayList<>();

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		generalMessage = "";
		dataModelCompanyInfo();
		companiesSelected = new ArrayList<>();
	}
	
	private void dataModelCompanyInfo() throws Exception{
		dataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompany2(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(companyService.count(Company.class, filters));

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

	public void addCompanyToList(){
		try {
			
			SDFCompany primarySDF = companyService.findPrimarySDF(selecetdCompany);
			if (primarySDF == null) {
				selecetdCompany = null;
				throw new Exception("No Primary SDFs found!");
			}
			
			if (companiesSelected == null) {
				companiesSelected = new ArrayList<>();
			}
			boolean addToList = true;
			for (Company company : companiesSelected) {
				if (company.getId().equals(selecetdCompany.getId())) {
					addToList = false;
				}
			}
			if (addToList) {
				companiesSelected.add(selecetdCompany);
				addWarningMessage("Company Added");
			} else {
				addWarningMessage("Company Already Added To List");
			}
			selecetdCompany = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeCompanyFromList() {
		try {
			companiesSelected.remove(selecetdCompany);
			addInfoMessage("Company Removed");
			selecetdCompany = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sendMailToCompanies(){
		try {
			validationCheck();
			companyService.sendBulkMailToSelectedCompanies(companiesSelected, generalMessage);
			addInfoMessage("Mails Being Sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void validationCheck() throws Exception{
		if (generalMessage == null || generalMessage.isEmpty() || generalMessage == "") {
			throw new Exception("Provide email contents");
		}
		if (companiesSelected == null || companiesSelected.size() == 0) {
			throw new Exception("Select Atleast One Company Before Proceeding");
		}
	}

	public Company getSelecetdCompany() {
		return selecetdCompany;
	}

	public void setSelecetdCompany(Company selecetdCompany) {
		this.selecetdCompany = selecetdCompany;
	}

	public String getGeneralMessage() {
		return generalMessage;
	}

	public void setGeneralMessage(String generalMessage) {
		this.generalMessage = generalMessage;
	}

	public LazyDataModel<Company> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Company> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Company> getCompaniesSelected() {
		return companiesSelected;
	}

	public void setCompaniesSelected(List<Company> companiesSelected) {
		this.companiesSelected = companiesSelected;
	}

	
}
