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
import haj.com.entity.HostingCompanyEmployees;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.lookup.RegionTownService;

@ManagedBean(name = "companyRegionReportUI")
@ViewScoped
public class CompanyRegionReportUI extends AbstractUI {
	
	/* Service Levels */
	private HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
	private CompanyService companyService = new CompanyService();
	
	/* Lazy Data Models */
	private LazyDataModel<Company> companyDataModel;
	
	/* Vars */
	private boolean cloCrmUser = false;
	private boolean displayNormalDownload = false;

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
		populateInformation();
		companyDataModelInfo();
	}
	
	private void populateInformation() throws Exception {
		if (getSessionUI().getRole() == null || getSessionUI().getRole().getCloCrmReporting() == null || !getSessionUI().getRole().getCloCrmReporting()) {
			super.redirectToDashboard();
		} else {
			HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
			if (hce == null) {
				super.redirectToDashboard();
			} else {
				cloCrmUser = RegionTownService.instance().checkIfCrmCloUser(hce);
				if (cloCrmUser) {
					if (companyService.countCompaniesLinkedByCloCrmUser(getSessionUI().getActiveUser().getId()) > 65000) {
						displayNormalDownload = false;
					} else {
						displayNormalDownload = true;
					}
				}
			}
		}
	}

	private void companyDataModelInfo() {
		companyDataModel = new LazyDataModel<Company>() {
			
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (cloCrmUser) {
						retorno = companyService.cloCrmCompanyRegionReport(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId());
						companyDataModel.setRowCount(companyService.countCloCrmCompanyRegionReport( filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			
			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}
			
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/* getters and Seters */
	public boolean isDisplayNormalDownload() {
		return displayNormalDownload;
	}

	public void setDisplayNormalDownload(boolean displayNormalDownload) {
		this.displayNormalDownload = displayNormalDownload;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public boolean isCloCrmUser() {
		return cloCrmUser;
	}

	public void setCloCrmUser(boolean cloCrmUser) {
		this.cloCrmUser = cloCrmUser;
	}

	
}
