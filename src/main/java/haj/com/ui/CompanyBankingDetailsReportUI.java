package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;

/**
 * The Class CompanyBankingDetailsReportUI.
 */
@ManagedBean(name = "companyBankingDetailsReportUI")
@ViewScoped
public class CompanyBankingDetailsReportUI extends AbstractUI {

	/* Service Levels */
	private CompanyService companyService = new CompanyService();
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	
	/* Lazy Data Models */
	private LazyDataModel<Company> dataModelBankingDetails;
	
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
		companyBankingDetilasInfo();
	}
	
	public void companyBankingDetilasInfo() {
		dataModelBankingDetails = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					filters.put("companyStatus", CompanyStatusEnum.Active);
					companyList = companyService.allCompanyBankingDetails(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModelBankingDetails.setRowCount(companyService.count(Company.class, filters));

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

	public LazyDataModel<Company> getDataModelBankingDetails() {
		return dataModelBankingDetails;
	}
	
	public void downloadCompanyBankingDetailsReport(){
		try {
			bankingDetailsService.downloadCompanyBankingDetailsReport();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public void setDataModelBankingDetails(LazyDataModel<Company> dataModelBankingDetails) {
		this.dataModelBankingDetails = dataModelBankingDetails;
	}

	
}
