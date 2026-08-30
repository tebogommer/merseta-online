package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.DgVerification;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.Wsp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DgVerificationService;
import haj.com.service.MandatoryGrantRecommendationService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.WspService;

@ManagedBean(name = "monitorDgVerificationUI")
@ViewScoped
public class MonitorDgVerificationUI extends AbstractUI {

	/* Entity Level */
	private Company selectedCompany = null;
	private Wsp selectedWsp = null;
	private DgVerification selectedDgVerification = null;
	
	/* Service Level */
	private CompanyService companyService = null;
	private WspService wspService = null;
	private DgVerificationService dgVerificationService = null;
	private MandatoryGrantService mandatoryGrantService = null;
	private MandatoryGrantRecommendationService mandatoryGrantRecommendationService = null;
	
	/* Lazy Data Models */
	private LazyDataModel<Company> companyDataModel;
	private LazyDataModel<DgVerification> dgVerificationDataModel;
	private LazyDataModel<MandatoryGrant> mandatoryGrantDataModel;
	
	/* Array Lists */
	private List<MandatoryGrant> mandatoryGrantDataList = new ArrayList<>();
	
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
		populateServiceLevels();
		companyInfo();
	}
	
	private void populateServiceLevels() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (wspService == null) {
			wspService = new WspService();
		}
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}
		if (mandatoryGrantService == null) {
			mandatoryGrantService = new MandatoryGrantService();
		}
		if (mandatoryGrantRecommendationService == null) {
			mandatoryGrantRecommendationService = new MandatoryGrantRecommendationService();
		}
	}

	public void companyInfo() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = companyService.allCompanysLevyNotNull(Company.class, first, pageSize, sortField, sortOrder, filters);
					companyDataModel.setRowCount(companyService.countAllCompanysLevyNotNull(Company.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
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
	
	public void selectCompany() {
		try {
			dgVerificationDataModelInfo();
			selectedDgVerification = null;
			mandatoryGrantDataList.clear();
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeViewSelectCompany(){
		try {
			selectedCompany = null;
			selectedDgVerification = null;
			mandatoryGrantDataList.clear();
			addWarningMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void dgVerificationDataModelInfo() throws Exception {
		dgVerificationDataModel = new LazyDataModel<DgVerification>() {
			private static final long serialVersionUID = 1L;
			private List<DgVerification> retorno = new ArrayList<DgVerification>();
			@Override
			public List<DgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = dgVerificationService.allDgVerificationByCompany(first, pageSize, sortField, sortOrder, filters, selectedCompany);
					dgVerificationDataModel.setRowCount(dgVerificationService.countAllDgVerificationByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DgVerification obj) {
				return obj.getId();
			}

			@Override
			public DgVerification getRowData(String rowKey) {
				for (DgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void viewVerificationData(){
		try {
			mandatoryGrantDataModelInfo();
			mandatoryGrantDataList.clear();
			addInfoMessage("Action complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeViewVerificationData(){
		try {
			selectedDgVerification = null;
			mandatoryGrantDataList.clear();
			addWarningMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void mandatoryGrantDataModelInfo() throws Exception {
		mandatoryGrantDataModel = new LazyDataModel<MandatoryGrant>() {
			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();
			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, selectedDgVerification.getWsp());
					retorno.forEach(mg -> {
						try {
							mg.setGrantRecommendations(mandatoryGrantRecommendationService.findByMG(mg));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					mandatoryGrantDataModel.setRowCount(mandatoryGrantService.count(MandatoryGrant.class, filters, selectedDgVerification.getWsp()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MandatoryGrant obj) {
				return obj.getId();
			}

			@Override
			public MandatoryGrant getRowData(String rowKey) {
				for (MandatoryGrant obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void generateVerificationData() {
		try {
			if (mandatoryGrantService.countMandatoryGrantByWspId(selectedDgVerification.getWsp().getId()) == 0) {
				dgVerificationService.generateMissingDataVerficiation(wspService.findByKey(selectedDgVerification.getWsp().getId()));
				addInfoMessage("Action Complete");
			} else {
				addErrorMessage("Data Already Generated.");
			}
			mandatoryGrantDataList.clear();
			selectedDgVerification = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void viewDataGenerationForVerification(){
		try {
			mandatoryGrantDataList = dgVerificationService.viewDataGeneration(wspService.findByKey(selectedDgVerification.getWsp().getId()));
			if (mandatoryGrantDataList.isEmpty()) {
				addWarningMessage("No Data To View");
			} else {
				addInfoMessage("Action Complete");
			}
			selectedDgVerification = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void reopenToStartVerification(){
		try {
			// check for allocation against wsp 
			dgVerificationService.reopenDgVerification(selectedDgVerification, getSessionUI().getActiveUser());
			mandatoryGrantDataList.clear();
			selectedDgVerification = null;
			dgVerificationDataModelInfo();
			addWarningMessage("DG Verification Re-opened.");
		} catch (Exception e) {
			mandatoryGrantDataList.clear();
			selectedDgVerification = null;
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* getters and Setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Wsp getSelectedWsp() {
		return selectedWsp;
	}

	public void setSelectedWsp(Wsp selectedWsp) {
		this.selectedWsp = selectedWsp;
	}

	public DgVerification getSelectedDgVerification() {
		return selectedDgVerification;
	}

	public void setSelectedDgVerification(DgVerification selectedDgVerification) {
		this.selectedDgVerification = selectedDgVerification;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public LazyDataModel<DgVerification> getDgVerificationDataModel() {
		return dgVerificationDataModel;
	}

	public void setDgVerificationDataModel(LazyDataModel<DgVerification> dgVerificationDataModel) {
		this.dgVerificationDataModel = dgVerificationDataModel;
	}

	public LazyDataModel<MandatoryGrant> getMandatoryGrantDataModel() {
		return mandatoryGrantDataModel;
	}

	public void setMandatoryGrantDataModel(LazyDataModel<MandatoryGrant> mandatoryGrantDataModel) {
		this.mandatoryGrantDataModel = mandatoryGrantDataModel;
	}

	public List<MandatoryGrant> getMandatoryGrantDataList() {
		return mandatoryGrantDataList;
	}

	public void setMandatoryGrantDataList(List<MandatoryGrant> mandatoryGrantDataList) {
		this.mandatoryGrantDataList = mandatoryGrantDataList;
	}
	
}