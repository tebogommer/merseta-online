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
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SitesSmeService;
import haj.com.service.SmeQualificationsService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyInfoUI.
 */
@ManagedBean(name = "mentorManagementUI")
@ViewScoped
public class MentorManagementUI extends AbstractUI {
	
	/** Entity Level */
	private SitesSme sitesSme = null;
	
	/** Service Level */
	private SitesSmeService sitesSmeService = new SitesSmeService();
	private SmeQualificationsService smeQualificationsService = new SmeQualificationsService();
	
	/** Lazy Data Models */
	private LazyDataModel<SitesSme> allSmeDataModel;
	private LazyDataModel<SitesSme> allSmeDataModelLimited;
	private LazyDataModel<SmeQualifications> allSmeQualificationsDataModel;
	
	/** Array List */
	private List<SmeQualifications> smeQualificationsList = null;
	private List<Doc> docs = new ArrayList<>();

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
	 * Initialize method to get all Company and prepare a for a create of a new
	 * Company.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	private void runInit() throws Exception {
		siteSmeDataInfo();
		runSmeQualificationInfo();
	}

	public void siteSmeDataInfo() {
		if (sitesSmeService == null) {
			sitesSmeService = new SitesSmeService();
		}

		allSmeDataModel = new LazyDataModel<SitesSme>() {
			private static final long serialVersionUID = 1L;
			private List<SitesSme> retorno = new ArrayList<>();

			@Override
			public List<SitesSme> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = sitesSmeService.allSitesSme(first, pageSize, sortField, sortOrder, filters);
					allSmeDataModel.setRowCount(sitesSmeService.count(SitesSme.class, filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};

		allSmeDataModelLimited = new LazyDataModel<SitesSme>() {
			private static final long serialVersionUID = 1L;
			private List<SitesSme> retorno = new ArrayList<>();

			@Override
			public List<SitesSme> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = sitesSmeService.allSitesSmeNoAdditionalPopulation(first, pageSize, sortField, sortOrder, filters);
					allSmeDataModelLimited.setRowCount(sitesSmeService.countAllSitesSmeNoAdditionalPopulation(filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void runSmeQualificationInfo() throws Exception {
		if (smeQualificationsService == null) {
			smeQualificationsService = new SmeQualificationsService();
		}
		
		allSmeQualificationsDataModel = new LazyDataModel<SmeQualifications>() {
			private static final long serialVersionUID = 1L;
			private List<SmeQualifications> retorno = new ArrayList<>();

			@Override
			public List<SmeQualifications> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = smeQualificationsService.allSmeQualificationsNoAdditionalInformation(first, pageSize, sortField, sortOrder, filters);
					allSmeQualificationsDataModel.setRowCount(smeQualificationsService.countAllSmeQualificationsNoAdditionalInformation(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SmeQualifications obj) {
				return obj.getId();
			}

			@Override
			public SmeQualifications getRowData(String rowKey) {
				for (SmeQualifications obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void prepareAllInfor() {
		try {
			docs = new ArrayList<>();
			smeQualificationsList = smeQualificationsService.findBySme(sitesSme);
			if (sitesSme.getDocs() != null) {
				if (!sitesSme.getDocs().isEmpty()) {
					docs.addAll(sitesSme.getDocs());
				}
				for (SmeQualifications smequalifications : smeQualificationsList) {
					if (!smequalifications.getDocs().isEmpty())
						docs.addAll(smequalifications.getDocs());
				}
			} else {
				for (SmeQualifications smequalifications : smeQualificationsList) {
					if (!smequalifications.getDocs().isEmpty())
						docs.addAll(smequalifications.getDocs());
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public void listenerMethod(SitesSme sitesSme) {
		try {
			sitesSme = sitesSmeService.findByKey(sitesSme.getId());
			sitesSmeService.populateAdditionalInformationBySitesSme(sitesSme);
			this.docs = sitesSme.getDocs();
			smeQualificationsList = smeQualificationsService.findBySme(sitesSme);
			for (SmeQualifications smequalifications : smeQualificationsList) {
				if (!smequalifications.getDocs().isEmpty())
					docs.addAll(smequalifications.getDocs());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectSiteSme(){
		try {
			sitesSme = sitesSmeService.findByKey(sitesSme.getId());
			sitesSmeService.populateAdditionalInformationNoQuals(sitesSme);
			this.docs = sitesSme.getDocs();
			smeQualificationsList = smeQualificationsService.findBySme(sitesSme); 
			for (SmeQualifications smequalifications : smeQualificationsList) {
				if (!smequalifications.getDocs().isEmpty())
					docs.addAll(smequalifications.getDocs());
			}
			// double check rejection reasons on SmeQualifications level (Check: haj.com.service.SitesSmeService.populateAdditionalInformation(SitesSme, ConfigDocProcessEnum, CompanyUserTypeEnum, Company))
			addInfoMessage("Selection Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void deselectSme(){
		try {
			sitesSme = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadSmeQualReport(){
		try {
			smeQualificationsService.downloadSmeQualReport();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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

	public List<SmeQualifications> getSmeQualificationsList() {
		return smeQualificationsList;
	}

	public void setSmeQualificationsList(List<SmeQualifications> smeQualificationsList) {
		this.smeQualificationsList = smeQualificationsList;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public LazyDataModel<SitesSme> getAllSmeDataModelLimited() {
		return allSmeDataModelLimited;
	}

	public void setAllSmeDataModelLimited(LazyDataModel<SitesSme> allSmeDataModelLimited) {
		this.allSmeDataModelLimited = allSmeDataModelLimited;
	}

	public LazyDataModel<SmeQualifications> getAllSmeQualificationsDataModel() {
		return allSmeQualificationsDataModel;
	}

	public void setAllSmeQualificationsDataModel(LazyDataModel<SmeQualifications> allSmeQualificationsDataModel) {
		this.allSmeQualificationsDataModel = allSmeQualificationsDataModel;
	}

}
