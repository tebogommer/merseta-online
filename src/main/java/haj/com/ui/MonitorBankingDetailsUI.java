package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;


@ManagedBean(name = "monitorBankingDetailsUI")
@ViewScoped
public class MonitorBankingDetailsUI extends AbstractUI {

	/* Entity Level */
	private Company selectedCompany = null;
	private BankingDetails bankingDetails = null;
	private BankingDetails selectedBankingDetails = null;
	private Vendor vendor;
	
	/* Service Level */
	private CompanyService companyService = new CompanyService();
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	
	/* Lazy Data Models */
	private LazyDataModel<Company> dataModelComapny;
	
	/* Array lists */
	private List<BankingDetails> companyApprovedBankingDetails;
	private List<BankingDetails> bankingDetailsList;
	
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
		populateServiceLevels();
		populateCompanies();
	}

	private void populateServiceLevels() throws Exception{
	}
	
	private void populateCompanies() throws Exception{
		companyInfo();
	}
	
	public void companyInfo() {
		dataModelComapny = new LazyDataModel<Company>() {
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
					dataModelComapny.setRowCount(companyService.count(Company.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
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
	
	public void selectCompany(){
		try {
			bankingDetailsList = bankingDetailsService.findByCompanyLatestApproved(selectedCompany);
			populateVendorFromGP();
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateVendorFromGP() {
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(selectedCompany);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}
			companyApprovedBankingDetails = new ArrayList<>();
			if (bankingDetailsService.findByCompanyCount(selectedCompany) > 1) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
				companyApprovedBankingDetails.remove(bankingDetails);
			} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(selectedCompany) > 0) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
			}
			this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
			
//			this.vendor = bankingDetailsService.getGPDetailsForLNumberAndCreateIfApp(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
//			if (this.vendor == null) {
//				this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addCompanyToGP(){
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(selectedCompany);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}
			companyApprovedBankingDetails = new ArrayList<>();
			if (bankingDetailsService.findByCompanyCount(selectedCompany) > 1) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
				companyApprovedBankingDetails.remove(bankingDetails);
			} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(selectedCompany) > 0) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
			}
			this.vendor = bankingDetailsService.getGPDetailsForLNumberAndCreateIfApp(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
			if (this.vendor == null) {
				this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
			}
			bankingDetailsList = bankingDetailsService.findByCompanyLatestApproved(selectedCompany);
			addInfoMessage("Action Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateDetailsOnGP(){
		try {
			if (selectedBankingDetails != null) {
				bankingDetailsService.updateBankningDetailsOnGP(bankingDetails, getSessionUI().getActiveUser(), vendor);
				selectCompany();
				addInfoMessage("Action Complete");
			} else {
				addErrorMessage("Unable to locate selected Banking Details, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateDetailsOnGPHoldStatusTrue(){
		try {
			bankingDetailsService.updateBankningDetailsOnGPHoldStatus(getSessionUI().getActiveUser(), vendor, true);
			selectCompany();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateDetailsOnGPHoldStatusfalse(){
		try {
			bankingDetailsService.updateBankningDetailsOnGPHoldStatus(getSessionUI().getActiveUser(), vendor, false);
			selectCompany();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	/* getters and setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public LazyDataModel<Company> getDataModelComapny() {
		return dataModelComapny;
	}

	public void setDataModelComapny(LazyDataModel<Company> dataModelComapny) {
		this.dataModelComapny = dataModelComapny;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<BankingDetails> getCompanyApprovedBankingDetails() {
		return companyApprovedBankingDetails;
	}

	public void setCompanyApprovedBankingDetails(List<BankingDetails> companyApprovedBankingDetails) {
		this.companyApprovedBankingDetails = companyApprovedBankingDetails;
	}

	public BankingDetails getSelectedBankingDetails() {
		return selectedBankingDetails;
	}

	public void setSelectedBankingDetails(BankingDetails selectedBankingDetails) {
		this.selectedBankingDetails = selectedBankingDetails;
	}

	public List<BankingDetails> getBankingDetailsList() {
		return bankingDetailsList;
	}

	public void setBankingDetailsList(List<BankingDetails> bankingDetailsList) {
		this.bankingDetailsList = bankingDetailsList;
	}
	
}