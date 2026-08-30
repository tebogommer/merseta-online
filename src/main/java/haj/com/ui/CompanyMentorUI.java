package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.LearnersService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SitesSmeService;
import haj.com.service.SmeQualificationsService;

@ManagedBean(name = "companyMentorUI")
@ViewScoped
public class CompanyMentorUI extends AbstractUI {
	
	private List<Company> companies = null;
	private Company selectedCompany;
	/** The Sites SME */
	private SitesSme sitesSme = null;
	private SitesSmeService sitesSmeService = new SitesSmeService();
	private SmeQualificationsService smeQualificationsService = new SmeQualificationsService();
	private LazyDataModel<SitesSme> allSmeDataModel = null;

	private Boolean primaryOrSecondarySDF;
	/* booleans */
	private boolean displayLastInfo = false;
	List<Doc> docs = new ArrayList<>();
	private List<SmeQualifications> smeQualificationsList = null;
	
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
		if (getSessionUI().getTask() != null ) {
			getSessionUI().setTask(null);
		}
		prepareNew();
		//siteSmeDataInfo();
		
	}
	
	public void checkLearnerInfo() {
		if (getSessionUI().isExternalParty()) {
			try {
				siteSmeDataInfo();				
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}			
		}		
	}
	
	/*public void checkLearnerInfo() {
		if (getSessionUI().isExternalParty()) {
			try {
				primaryOrSecondarySDF = sDFCompanyService.checkIfPrimarOrSecondaryCanRegisterLearners(getSessionUI().getActiveUser(), selectedCompany);
				
				if(!primaryOrSecondarySDF) {
					companyContactRegisterLearner = companyUsersService.checkIfCompanyContactCanRegisterLearner(getSessionUI().getActiveUser(), selectedCompany, ConfigDocProcessEnum.Learner);
					if(companyContactRegisterLearner) {
						siteSmeDataInfo();
					} else {
						addErrorMessage("Kindly be advised that you require the relevant authorisation to access this information");
					}
				}else {
					siteSmeDataInfo();
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}			
		}		
	}*/
	
	
	public void siteSmeDataInfo() {
		allSmeDataModel = new LazyDataModel<SitesSme>() {
			private static final long serialVersionUID = 1L;
			private List<SitesSme> retorno = new ArrayList<SitesSme>();

			@Override
			public List<SitesSme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = sitesSmeService.allSitesSmeByCompany(first, pageSize, sortField, sortOrder, filters, selectedCompany, null, null);
					allSmeDataModel.setRowCount(sitesSmeService.countAllSitesSmeByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SitesSme obj) {
				return obj.getId();
			}

			@Override
			public SitesSme getRowData(String rowKey) {
				for (SitesSme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}	


	public void prepareNew() {

	}
	
	public void listenerMethod(SitesSme sitesSme) {
		try {
			this.docs = sitesSme.getDocs();
			smeQualificationsList = smeQualificationsService.findBySme(sitesSme);
			for (SmeQualifications smequalifications : smeQualificationsList) {
				if (smequalifications.getDocs().size() > 0) docs.addAll(smequalifications.getDocs());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}
	
	public Boolean getPrimaryOrSecondarySDF() {
		return primaryOrSecondarySDF;
	}

	public void setPrimaryOrSecondarySDF(Boolean primaryOrSecondarySDF) {
		this.primaryOrSecondarySDF = primaryOrSecondarySDF;
	}


	public boolean isDisplayLastInfo() {
		return displayLastInfo;
	}

	public void setDisplayLastInfo(boolean displayLastInfo) {
		this.displayLastInfo = displayLastInfo;
	}

	public SitesSme getSitesSme() {
		return sitesSme;
	}

	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	public LazyDataModel<SitesSme> getAllSmeDataModel() {
		return allSmeDataModel;
	}

	public void setAllSmeDataModel(LazyDataModel<SitesSme> allSmeDataModel) {
		this.allSmeDataModel = allSmeDataModel;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public List<SmeQualifications> getSmeQualificationsList() {
		return smeQualificationsList;
	}

	public void setSmeQualificationsList(List<SmeQualifications> smeQualificationsList) {
		this.smeQualificationsList = smeQualificationsList;
	}
}
