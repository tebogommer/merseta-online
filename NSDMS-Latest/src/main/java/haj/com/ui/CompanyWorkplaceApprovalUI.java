package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.OfoCodes;
import haj.com.entity.Sites;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ConfigDocService;
import haj.com.service.DocService;
import haj.com.service.SitesService;
import haj.com.service.WorkPlaceApprovalService;

@ManagedBean(name = "companyWorkplaceApprovalUI")
@ViewScoped
public class CompanyWorkplaceApprovalUI extends AbstractUI {

	private List<Company> companies = null;
	private Company selectedCompany;

	/** The WorkPlaceApproval  */
	private WorkPlaceApproval workPlaceApproval = null;
	/** The  service. */
	private SitesService sitesService = new SitesService();
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	
	private LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel;

	private Boolean primaryOrSecondarySDF;
	private Doc doc ;

	/* booleans */
	private boolean displayLastInfo = false;
	private Qualification qualification = null;
	private OfoCodes ofoCodes = null;
	private boolean byQualification = true;
	private List<Sites> sitesList = null;
	private Sites selectedSite = null;
	private boolean useCompanyAsSite = false;
	
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
	}
	
	public void prepareNew() {

	}
	
	public void workplaceApprovalDataInfo() {
		allWorkPlaceApprovalDataModel = new LazyDataModel<WorkPlaceApproval>() {
			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApproval> retorno = new ArrayList<WorkPlaceApproval>();

			@Override
			public List<WorkPlaceApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workPlaceApprovalService.allWorkPlaceApprovalByCompanyAndSkillProgran(first, pageSize, sortField, sortOrder, filters, selectedCompany);
					allWorkPlaceApprovalDataModel.setRowCount(workPlaceApprovalService.countAllWorkPlaceApprovalByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkPlaceApproval obj) {
				return obj.getId();
			}

			@Override
			public WorkPlaceApproval getRowData(String rowKey) {
				for (WorkPlaceApproval obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	
	public void prepWorkplaceApprovalRequest() {
		try {
			sitesList = sitesService.findByCompany(selectedCompany);
			useCompanyAsSite = false;
			qualification = null;
			ofoCodes = null;
			byQualification = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<Sites> completeSites(String desc) {
		List<Sites> l = null;
		try {
			l = sitesService.findByNameAndCompany(desc, selectedCompany);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Creates workplace approval
	 */
	public void createNewWorkPlaceApproval() {
		try {
			if (workPlaceApprovalService == null) {
				workPlaceApprovalService = new WorkPlaceApprovalService();
			}
			if (sitesList.size() != 0 && !useCompanyAsSite) {
				if (selectedSite == null) {
					throw new Exception("Select A Site Before Proceeding");
				}
			}
			if (byQualification) {
				ofoCodes = null;
			}else { 
				ofoCodes = null;
				//qualification = null; 
			}
			
			if (qualification == null && ofoCodes == null) {
				addWarningMessage("Provide either: Trade or Qualification Before Proceeding.");
			} else {
				if (workPlaceApprovalService.checkForExistingRecords(selectedCompany, qualification, ofoCodes, selectedSite) == 0) {
					workPlaceApprovalService.createWorkplaceApproval(selectedCompany, qualification, ofoCodes, selectedSite, getSessionUI().getActiveUser(), byQualification);
					addInfoMessage("Workplace Approval application initiated. Review application");
					runClientSideExecute("PF('dlgAddWpa').hide()");
					workplaceApprovalInfo();
				} else {
					addWarningMessage("A Workplace Approval has been created for that qualification/trade, please apply for a different qualification/trade");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeWithdrawalFile(FileUploadEvent event) {
		try {
			prepWithdrawDoc();
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			doc.setTargetClass(workPlaceApproval.getClass().getName());
			doc.setTargetKey(workPlaceApproval.getId());
			witdrawWorkPlaceApproval();
			//changeReason.setDoc(doc);
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void witdrawWorkPlaceApproval() {
		try {
			if(doc == null) {
				throw new Exception("Please Upload Required Document");
			}
			if(doc != null && doc.getTargetKey() == null) {
				throw new Exception("Please Upload Required Document");
			}
			workPlaceApprovalService.witdrawWorkPlaceApproval(workPlaceApproval, getSessionUI().getActiveUser(), null, doc);
			addInfoMessage("Your Application Has Been Submitted For Review");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepWithdrawDoc() {
		try {
			ConfigDocService configDocService = new ConfigDocService();
			List<Doc>list = new ArrayList<Doc>();
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.WITHDRAW_WORKPLACE_APPROVAL, CompanyUserTypeEnum.Company);
			l.forEach(a -> {
				list.add(new Doc(a));
			});
			if(list.size() > 0) {
				doc = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void workplaceApprovalInfo() {
		try {
			if (workPlaceApprovalService == null) {
				workPlaceApprovalService = new WorkPlaceApprovalService();
			}
			workplaceApprovalDataInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearQualification() {
		this.qualification = null;
	}
	
	public void downloadWorkPlaceApproval() {
		try {
			workPlaceApprovalService.downloadVTwo(workPlaceApproval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepWithdrawal() {
		try {
			doc = new Doc();
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

	public LazyDataModel<WorkPlaceApproval> getAllWorkPlaceApprovalDataModel() {
		return allWorkPlaceApprovalDataModel;
	}

	public void setAllWorkPlaceApprovalDataModel(LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel) {
		this.allWorkPlaceApprovalDataModel = allWorkPlaceApprovalDataModel;
	}

	public WorkPlaceApproval getWorkPlaceApproval() {
		return workPlaceApproval;
	}

	public void setWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) {
		this.workPlaceApproval = workPlaceApproval;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public boolean isByQualification() {
		return byQualification;
	}

	public void setByQualification(boolean byQualification) {
		this.byQualification = byQualification;
	}

	public List<Sites> getSitesList() {
		return sitesList;
	}

	public void setSitesList(List<Sites> sitesList) {
		this.sitesList = sitesList;
	}

	public Sites getSelectedSite() {
		return selectedSite;
	}

	public void setSelectedSite(Sites selectedSite) {
		this.selectedSite = selectedSite;
	}

	public boolean isUseCompanyAsSite() {
		return useCompanyAsSite;
	}

	public void setUseCompanyAsSite(boolean useCompanyAsSite) {
		this.useCompanyAsSite = useCompanyAsSite;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
