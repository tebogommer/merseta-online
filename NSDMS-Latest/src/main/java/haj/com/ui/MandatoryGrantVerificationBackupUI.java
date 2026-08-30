package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MgVerification;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.MgVerificationService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;

//TODO: Auto-generated Javadoc
/**
 * The Class MandatoryGrantVerificationUI.
 */
@ManagedBean(name = "mandatoryGrantVerificationbackupUI")
@ViewScoped
public class MandatoryGrantVerificationBackupUI extends AbstractUI {

	/** Entity Layer */
	private MandatoryGrant mandatoryGrant;
	private WspReportEnum wspReport;
	private Company company;
	private Wsp wsp;
	private MgVerification mgVerification;

	/** Service Layer */
	private EtqaService etqaService = new EtqaService();
	private YesNoLookupService yesNoService = new YesNoLookupService();
	private MandatoryGrantService mandatoryGrantService = new MandatoryGrantService();
	private WspService wspService = new WspService();
	private CompanyService companyService = new CompanyService();
	private MgVerificationService mgVerificationService = new MgVerificationService();

	/** Lists */
	private List<MandatoryGrant> mandatoryGrants;
	private LazyDataModel<MandatoryGrant> dataModel;
	private LazyDataModel<Company> companyDataModel;
	private LazyDataModel<Wsp> wspDataModel;
	private List<Wsp> wspList;

	private SignoffService signoffService = new SignoffService();

	/** Boolean */
	private Boolean disableEdit;
	private Boolean disableSignOffButton;

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
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.MG_VERIFICATION && super.getParameter("id", false) != null) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			mgVerification = mgVerificationService.findByKey(getSessionUI().getTask().getTargetKey());
			this.wsp = mgVerification.getWsp();
			selcteWsp();
		} else {
			disableEdit = false;
			wspReport = WspReportEnum.WSP;
			companyInfo();
			wspInfo();
		}
	}

	/**
	 * Lazy load for wsp
	 * 
	 * @throws Exception
	 */
	private void wspInfo() throws Exception {
		// array list
		wspList = wspService.allWsp();

		// lazy load array list
		wspDataModel = new LazyDataModel<Wsp>() {

			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();

			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					Calendar now = Calendar.getInstance();
					Long finYear = Long.valueOf(now.get(Calendar.YEAR) + 1);
					retorno = wspService.findCompaniesForVerification(first, pageSize, sortField, sortOrder, filters, finYear.intValue());
					wspDataModel.setRowCount(wspService.countCompaniesForVerification(filters, finYear.intValue()));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Wsp obj) {
				return obj.getId();
			}

			@Override
			public Wsp getRowData(String rowKey) {
				for (Wsp obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	private void companyInfo() {
		companyDataModel = new LazyDataModel<Company>() {

			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.allMandatoryGrantCompany(first, pageSize, sortField, sortOrder, filters, wspReport, HAJConstants.HOSTING_MERSETA_ETQA);
					companyDataModel.setRowCount(mandatoryGrantService.countCompany(filters, wspReport, HAJConstants.HOSTING_MERSETA_ETQA));
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	/**
	 * When a wsp is selected locates all mandatory grants by wsp company
	 */
	public void selcteWsp() {
		try {
			pivitolInfoWsp();
			locateWspMgVerification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * By wsp selection
	 */
	private void pivitolInfoWsp() throws Exception {
		company = companyService.findByKey(wsp.getCompany().getId());
		dataModel = new LazyDataModel<MandatoryGrant>() {

			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();

			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID);
					dataModel.setRowCount(mandatoryGrantService.count(MandatoryGrant.class, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID));
				} catch (Exception e) {
					logger.fatal(e);
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

	/**
	 * locates MG verification by WSP
	 * 
	 * @throws Exception
	 */
	private void locateWspMgVerification() throws Exception {
		mgVerification = mgVerificationService.findByWspId(wsp);
		if (mgVerification == null || mgVerification.getId() == null) {
			mgVerification = new MgVerification();
			mgVerification.setWsp(wsp);
		}
		this.mgVerification = signoffService.resolveMgSignOff(mgVerification);
		mgVerification.getSignOffs().forEach(sign -> {
			if (disableSignOffButton == null || !disableSignOffButton) {
				disableSignOffButton = sign.getUser().equals(getSessionUI().getUser()) && (sign.getCompleted() == null || !sign.getCompleted());
			}
		});
	}

	/**
	 * Create / Update MG Verification submit button and relocate MG Verification by
	 * WSP
	 */
	public void updateMgVerification() {
		try {
			updateMg();
			locateWspMgVerification();
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void signOffVerification() {
		try {
			if (mgVerification.getDateCaptured() == null) {
				mgVerification.setDateCaptured(new Date());
			}
			mgVerificationService.saveSignOff(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			locateWspMgVerification();
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create / Update MG Verification
	 * 
	 * @throws Exception
	 */
	private void updateMg() {
		try {
			if (mgVerification.getDateCaptured() == null) {
				mgVerification.setDateCaptured(new Date());
			}
			mgVerificationService.createWithSignOff(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * By company selection
	 */
	public void pivitolInfo() {

		dataModel = new LazyDataModel<MandatoryGrant>() {

			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();

			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID);
					dataModel.setRowCount(mandatoryGrantService.count(MandatoryGrant.class, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID));
				} catch (Exception e) {
					logger.fatal(e);
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

	public void generateEmployeesEmployed() {
		try {
			mandatoryGrantService.create(mandatoryGrant);

			pivitolInfo();
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMandatory() {
		try {
			mandatoryGrantService.delete(mandatoryGrant);

			pivitolInfo();
			addInfoMessage(getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applySaqaData(SelectEvent event) {
		try {
			mandatoryGrant.setNqfLevels(mandatoryGrant.getQualification().getNqflevel());
			mandatoryGrant.setInterventionLevel(mandatoryGrant.getNqfLevels().getInterventionLevel());
			mandatoryGrant.setEtqa(etqaService.findByCode(mandatoryGrant.getQualification().getEtqaid()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void applyInterventionData(SelectEvent event) {
		try {
			mandatoryGrant.setPivotNonPivot(mandatoryGrant.getInterventionType().getPivotNonPivot());
			if (mandatoryGrant.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.YES_ID));
			} else {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.NO_ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public List<MandatoryGrant> getMandatoryGrants() {
		return mandatoryGrants;
	}

	public void setMandatoryGrants(List<MandatoryGrant> mandatoryGrants) {
		this.mandatoryGrants = mandatoryGrants;
	}

	public LazyDataModel<MandatoryGrant> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrant> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LazyDataModel<Wsp> getWspDataModel() {
		return wspDataModel;
	}

	public void setWspDataModel(LazyDataModel<Wsp> wspDataModel) {
		this.wspDataModel = wspDataModel;
	}

	public List<Wsp> getWspList() {
		return wspList;
	}

	public void setWspList(List<Wsp> wspList) {
		this.wspList = wspList;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Boolean getDisableEdit() {
		return disableEdit;
	}

	public void setDisableEdit(Boolean disableEdit) {
		this.disableEdit = disableEdit;
	}

	public MgVerification getMgVerification() {
		return mgVerification;
	}

	public void setMgVerification(MgVerification mgVerification) {
		this.mgVerification = mgVerification;
	}

	public Boolean getDisableSignOffButton() {
		return disableSignOffButton;
	}

	public void setDisableSignOffButton(Boolean disableSignOffButton) {
		this.disableSignOffButton = disableSignOffButton;
	}

}
