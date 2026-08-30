package haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import haj.com.entity.lookup.Funding;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.Company;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.SDFCompany;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.GrantOfoSelection;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationErrorException;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DgVerificationService;
import haj.com.service.JasperService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.UploadTrackerAtrWspService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.GrantOfoSelectionService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "wspUI")
@ViewScoped
public class WspUI extends AbstractUI {

	private MandatoryGrantDetail mandatoryGrant;
	private List<MandatoryGrantDetail> mandatoryGrants;
	private MandatoryGrantDetailService mandatoryGrantService = new MandatoryGrantDetailService();
	private WspReportEnum wspReport;
	private EtqaService etqaService = new EtqaService();
	private YesNoLookupService yesNoService = new YesNoLookupService();
	private Date endDatLimit;
	private Date startDateLimit;
	private Date startDateEndLimit;
	private Integer maxLearners;
	private boolean editLearners;
	private List<MandatoryGrantDetail> mandatoryGrantsCSV;
	private boolean skillsSet;
	private boolean skillsProgram;
	private boolean shortCreditBearing;
	/** MandatoryGrantDetail lazy data model */
	private LazyDataModel<MandatoryGrantDetail> dataModel;

	/** MandatoryGrantDetail CSV lazy data model */
	private LazyDataModel<MandatoryGrantDetail> dataModelCsv;
	public CSVUtil csvUtil = new CSVUtil();

	private Wsp wsp = null;
	private LazyDataModel<Wsp> wspDataModel;
	private LazyDataModel<Wsp> wspAwaitingMancoDataModel;
	private WspService wspService = new WspService();
	private CompanyService companyService = new CompanyService();
	private DgVerificationService dgVerificationService = new DgVerificationService();
	private GrantOfoSelection grantOfoSelection = null;
	private Date today;

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
		wspReport = WspReportEnum.WSP;
		mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
		if (getSessionUI().getWspSession() != null && getSessionUI().getWspSession().getFinYear() != null) {
			grantOfoSelection = GrantOfoSelectionService.instance().findByGrantYearListReturnFirst(getSessionUI().getWspSession().getFinYear());
		}
		today = GenericUtility.getStartOfDay(getNow());
		// mandatoryGrants =
		// mandatoryGrantService.findByWSP(getSessionUI().getWspSession(),
		// wspReport);
		// findByWSP();
		checkDateLimits();
		mandatoryGrantsDetailInfo();
		mandatoryGrantsDetailInfoCsv();
		wspInfo();
		wspAwaitingMancoInfo();
	}

	private void findByWSP() throws Exception {
		mandatoryGrants = mandatoryGrantService.findByWSP(getSessionUI().getWspSession(), wspReport);
		mandatoryGrantsCSV = mandatoryGrantService.findByWSPCsv(getSessionUI().getWspSession(), wspReport);
	}
	
	public void clearInfoPassportRsaIdSelection(){
		try {
			mandatoryGrant.setRsaCitizenTypeEnum(null);
			mandatoryGrant.setIdNumber(null);
			mandatoryGrant.setNationality(null);
			mandatoryGrant.setGender(null);
			mandatoryGrant.setDateOfBirth(null);
//			if (mandatoryGrant.getIdType() != null && mandatoryGrant.getIdType() == IdPassportEnum.Passport) {
//				mandatoryGrant.setRsaCitizenTypeEnum(null);
//				mandatoryGrant.setIdNumber(null);
//				mandatoryGrant.setNationality(null);
//				mandatoryGrant.setGender(null);
//			} else if (mandatoryGrant.getIdType() != null && mandatoryGrant.getIdType() == IdPassportEnum.RsaId) {
//				mandatoryGrant.setRsaCitizenTypeEnum(null);
//				mandatoryGrant.setIdNumber(null);
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void mandatoryGrantsDetailInfo() {

		dataModel = new LazyDataModel<MandatoryGrantDetail>() {
			private static final long serialVersionUID = 1L;
			private List<MandatoryGrantDetail> list = new ArrayList<>();

			@Override
			public List<MandatoryGrantDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {

					list = mandatoryGrantService.findByWsp(MandatoryGrantDetail.class, first, pageSize, sortField,
							sortOrder, filters, getSessionUI().getWspSession().getId(), wspReport);
					dataModel.setRowCount((int) mandatoryGrantService.allMandatoryGrantDetailCount(filters,
							getSessionUI().getWspSession().getId(), wspReport));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(MandatoryGrantDetail object) {
				// TODO Auto-generated method stub
				return object.getId();
			}

			@Override
			public MandatoryGrantDetail getRowData(String rowKey) {
				for (MandatoryGrantDetail obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void mandatoryGrantsDetailInfoCsv() {

		dataModelCsv = new LazyDataModel<MandatoryGrantDetail>() {
			private static final long serialVersionUID = 1L;
			private List<MandatoryGrantDetail> list = new ArrayList<>();

			@Override
			public List<MandatoryGrantDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {

					list = mandatoryGrantService.findByWspCsv(MandatoryGrantDetail.class, first, pageSize, sortField,
							sortOrder, filters, getSessionUI().getWspSession().getId(), wspReport);
					dataModelCsv.setRowCount((int) mandatoryGrantService.allMandatoryGrantDetailCountCsv(filters,
							getSessionUI().getWspSession().getId(), wspReport));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(MandatoryGrantDetail object) {
				// TODO Auto-generated method stub
				return object.getId();
			}

			@Override
			public MandatoryGrantDetail getRowData(String rowKey) {
				for (MandatoryGrantDetail obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void generateEmployeesEmployed() {
		try {
			System.out.println("mandatoryGrant.getFunding().getId():" + mandatoryGrant.getFunding().getId());
			Funding funding = new ResolveByCodeLookupDAO().findFundingById(mandatoryGrant.getFunding().getId());
			mandatoryGrant.setFundingCode(funding.getCode());
			mandatoryGrant.setFunding(funding);
			mandatoryGrantService.create(mandatoryGrant);
			mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
			// mandatoryGrants =
			// mandatoryGrantService.findByWSP(getSessionUI().getWspSession(),
			// wspReport);
			findByWSP();
			super.runClientSideExecute("updatePivotal()");
			super.runClientSideExecute("updateDiscGrant()");
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		try {
			mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
			findByWSP();
			super.runClientSideExecute("updatePivotal()");
			super.runClientSideExecute("updateDiscGrant()");
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAll() {
		try {
			try {
				UploadTrackerAtrWspService.instance().createEntry("WSP", 0, "Delete All Entries Start", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			this.mandatoryGrantService.deleteAll(getSessionUI().getWspSession(), WspReportEnum.WSP);
			try {
				UploadTrackerAtrWspService.instance().createEntry("WSP", 0, "Delete All Entries End", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			findByWSP();
			super.runClientSideExecute("updatePivotal()");
			super.runClientSideExecute("updateDiscGrant()");
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteAllCSV() {
		try {
			try {
				UploadTrackerAtrWspService.instance().createEntry("WSP", 0, "Delete All CSV Entries Start", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}

			this.mandatoryGrantService.deleteAllCSV(getSessionUI().getWspSession(), WspReportEnum.WSP);
			findByWSP();

			try {
				UploadTrackerAtrWspService.instance().createEntry("WSP", 0, "Delete All CSV Entries END", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			super.runClientSideExecute("updatePivotal()");
			super.runClientSideExecute("updateDiscGrant()");
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applySaqaData() {
		try {
			if (SKILLS_PROGRAM_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
				mandatoryGrantService.applySkillsProgram(mandatoryGrant);
			} else if (SKILLS_SET_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
				mandatoryGrantService.applySkillsSet(mandatoryGrant);
			} else {
				mandatoryGrantService.applySaqaData(mandatoryGrant);
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyNQFUnitStandardData() {
		try {
			mandatoryGrantService.applyUnitStandardData(mandatoryGrant);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void applyInterventionData() {
		try {
			if (mandatoryGrant.getInterventionType() != null) {
				if (SKILLS_PROGRAM_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
					this.skillsProgram = true;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					mandatoryGrant.setSkillsSet(null);
					mandatoryGrant.setUnitStandard(null);
				} else if (SKILLS_SET_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
					this.skillsProgram = false;
					this.skillsSet = true;
					this.shortCreditBearing = false;
					mandatoryGrant.setSkillsProgram(null);
					mandatoryGrant.setUnitStandard(null);
				} else if (mandatoryGrant.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					mandatoryGrant.setSkillsProgram(null);
					mandatoryGrant.setSkillsSet(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = true;
				} else {
					mandatoryGrant.setSkillsProgram(null);
					mandatoryGrant.setSkillsSet(null);
					mandatoryGrant.setUnitStandard(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
				}
				mandatoryGrantService.applyInterventionData(mandatoryGrant);
				checkDateLimits();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMandatory() {
		try {
			mandatoryGrantService.delete(mandatoryGrant);
			mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
			// mandatoryGrants =
			// mandatoryGrantService.findByWSP(getSessionUI().getWspSession(),
			// wspReport);
			findByWSP();
			super.runClientSideExecute("updatePivotal()");
			super.runClientSideExecute("updateDiscGrant()");
			addInfoMessage(getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calcCost() {
	}

	public void calcEmploymentData() {
		if (mandatoryGrant.getEmploymentStatus() == EmploymentStatusEnum.Employed) {
			maxLearners = mandatoryGrant.getWsp().getCompany().getNumberOfEmployees();
		}
		editLearners = true;
	}

	public void applyNQFData() {
		try {

			mandatoryGrantService.applyNQFData(mandatoryGrant);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearInfo() {
		try {
			mandatoryGrant.setIdNumber(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void checkDateLimits() {
		try {

			if (GenericUtility.checkBeforeApril(getNow())) {
				startDateLimit = GenericUtility.startDateOfCurrentYear();
				endDatLimit = GenericUtility.endDateOfCurrentYear();
				startDateEndLimit = GenericUtility.endDateOfCurrentYear();
			} else {
				startDateLimit = GenericUtility.addYearsToDate(GenericUtility.startDateOfCurrentYear(), 1);
				endDatLimit = GenericUtility.addYearsToDate(GenericUtility.endDateOfCurrentYear(), 1);
				startDateEndLimit = GenericUtility.addYearsToDate(GenericUtility.endDateOfCurrentYear(), 1);
			}

			if (mandatoryGrant.getFunding() != null
					&& mandatoryGrant.getFunding().getId() == HAJConstants.DISC_FUNDING_ID) {
				endDatLimit = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		calcCost();
	}

	public void downloadCSV() {
		try {
			List<MandatoryGrantDetail> mgd = mandatoryGrantService.findByWSPCsvAll(getSessionUI().getWspSession(), wspReport);
			if (mgd.size() > 0) {
				String csv = CSVUtil.writeCSV(mgd, ",", false);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "WSP_ERROR_CSV.csv", "text/csv");
			} else {
				addWarningMessage("No WSP data to export");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadCSV1() {
		try {
			List<MandatoryGrantDetail> mgd = mandatoryGrantService.findByWSPCsvAll(getSessionUI().getWspSession(), wspReport);
			if (mgd.size() > 0) {
				String csv = CSVUtil.writeCSV(mgd, ",", false);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "WSP_CSV.csv", "text/csv");
			} else {
				addWarningMessage("No WSP data to export");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void wspInfo() {

		wspDataModel = new LazyDataModel<Wsp>() {

			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();

			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = wspService.allWsp(Wsp.class, first, pageSize, sortField, sortOrder, filters);
					wspDataModel.setRowCount(wspService.count(Wsp.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void setDOBGenderNationality(AjaxBehaviorEvent even) {
		try {
			if (GenericUtility.checkRsaId(mandatoryGrant.getIdNumber())) {
				GenericUtility.calcIDData(mandatoryGrant);
			} else {
				clearInfo();
				throw new Exception("ID Number not valid");
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/*
	 * Manco stuff
	 */
	
	public void wspAwaitingMancoInfo() {
		wspAwaitingMancoDataModel = new LazyDataModel<Wsp>() {
			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();
			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = wspService.allWspAwaitingMancoApproval(first, pageSize, sortField, sortOrder, filters);
					wspAwaitingMancoDataModel.setRowCount(wspService.countAllWspAwaitingMancoApproval(filters));
				} catch (Exception e) {
					logger.fatal(e);
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	/**
	 * The Final Reject of the grant application
	 */
	public void finalRejectGrantApplication() {
		try {
			validateSDF(wsp.getCompany());
			wspService.finalApproveRejectGrantApplication(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(), WspStatusEnum.Rejected);
			if (wsp.getGrantRejected() && wsp.getSdfAppealedGrant()) {
				wspService.sendReleventNotificationToUsers(wsp, 4, getAdminRejectionReasons());
			}
			// generate DG Verification
			// version two
			boolean generateVerification = false;
			if (wsp.getFinYear() != null && wsp.getFinYear() > 2020) {
				generateVerification = true;
			} else {
				generateVerification = wspService.determainDgVerificationGenerationRejectedGrant(wsp);
			}
			if (generateVerification) {
				dgVerificationService.generateForRejectedWspSmallCompany(wsp);
			}
			
			
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wspAwaitingMancoInfo();
			wsp = null;
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getAdminRejectionReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(), ConfigDocProcessEnum.WSP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * The Final Approve of the grant application
	 */
	public void finalApproveGrantApplication() {
		try {
			validateSDF(wsp.getCompany());
			wspService.finalApproveRejectGrantApplication(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(), WspStatusEnum.Approved);
			if (wsp.getSdfAppealedGrant() != null && wsp.getSdfAppealedGrant() == true) {
				wspService.sendReleventNotificationToUsers(wsp, 2, null);
			} else {
				wspService.sendReleventNotificationToUsers(wsp, 1, null);
			}
			dgVerificationService.generateForWSP(wsp, getSessionUI().getActiveUser());
			wspAwaitingMancoInfo();
			wsp = null;
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Validation check for primary SDF assigned
	 * 
	 * @param company
	 * @throws Exception
	 */
	private void validateSDF(Company company) throws Exception {
		SDFCompany primarySDF = companyService.findPrimarySDF(company);
		if (primarySDF == null) {
			throw new Exception("Unable to locate Primary SDF for: " + company.getLevyNumber());
		}
	}

	public Date getStartDateLimit() {
		return startDateLimit;
	}

	public void setStartDateLimit(Date startDateLimit) {
		this.startDateLimit = startDateLimit;
	}

	public Date getEndDatLimit() {
		return endDatLimit;
	}

	public void setEndDatLimit(Date endDatLimit) {
		this.endDatLimit = endDatLimit;
	}

	public Integer getMaxLearners() {
		return maxLearners;
	}

	public void setMaxLearners(Integer maxLearners) {
		this.maxLearners = maxLearners;
	}

	public boolean isEditLearners() {
		return editLearners;
	}

	public void setEditLearners(boolean editLearners) {
		this.editLearners = editLearners;
	}

	public Date getStartDateEndLimit() {
		return startDateEndLimit;
	}

	public void setStartDateEndLimit(Date startDateEndLimit) {
		this.startDateEndLimit = startDateEndLimit;
	}

	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event) {
		try {
			logger.info("Starting file upload for " + getSessionUI().getWspSession().getCompany().getCompanyName());
			List<MandatoryGrantDetail> mandatoryGrantCSVImports = (List<MandatoryGrantDetail>) (List<?>) csvUtil.getObjects(MandatoryGrantDetail.class, event.getFile().getInputstream(), ",");
			try {
				UploadTrackerAtrWspService.instance().createEntry("WSP", mandatoryGrantCSVImports.size(),"CSV Upload Start", getSessionUI().getWspSession().getId(),getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			if (mandatoryGrantCSVImports.size() <= 20000) {
				logger.info("Finished parsing upload for " + getSessionUI().getWspSession().getCompany().getCompanyName() + " number of entries: " + mandatoryGrantCSVImports.size());
				// version One
//				mandatoryGrantService.save(mandatoryGrantCSVImports, getSessionUI().getWspSession(), wspReport);
				// version two
				mandatoryGrantService.saveVersionTwo(mandatoryGrantCSVImports, getSessionUI().getWspSession(), wspReport, grantOfoSelection);
				logger.info("Finished persisting upload for " + getSessionUI().getWspSession().getCompany().getCompanyName());
				try {
					UploadTrackerAtrWspService.instance().createEntry("WSP", mandatoryGrantCSVImports.size(), "CSV Upload End", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
				} catch (Exception e) {
					logger.fatal(e);
				}
			} else {
				mandatoryGrantCSVImports = null;
				throw new Exception("Exceeded Batch Size Limit. Please Upload a CSV containing No More Than 20 000 Entries.");
			}
			mandatoryGrantCSVImports = null;
			findByWSP();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
			UploadTrackerAtrWspService.instance().createEntry("WSP", 0, "ERROR: CSV Upload End: ID number exists for the specified OFO and Intervention Type", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
			UploadTrackerAtrWspService.instance().createEntry("WSP", 0, "ERROR: CSV Upload End: " + e.getMessage(), getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
		} finally {
			csvUtil = new CSVUtil();
		}

	}

	public boolean isSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(boolean skillsSet) {
		this.skillsSet = skillsSet;
	}

	public boolean isSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(boolean skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public MandatoryGrantDetail getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrantDetail mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public List<MandatoryGrantDetail> getMandatoryGrants() {
		return mandatoryGrants;
	}

	public void setMandatoryGrants(List<MandatoryGrantDetail> mandatoryGrants) {
		this.mandatoryGrants = mandatoryGrants;
	}

	public List<MandatoryGrantDetail> getMandatoryGrantsCSV() {
		return mandatoryGrantsCSV;
	}

	public void setMandatoryGrantsCSV(List<MandatoryGrantDetail> mandatoryGrantsCSV) {
		this.mandatoryGrantsCSV = mandatoryGrantsCSV;
	}

	public LazyDataModel<MandatoryGrantDetail> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrantDetail> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<MandatoryGrantDetail> getDataModelCsv() {
		return dataModelCsv;
	}

	public void setDataModelCsv(LazyDataModel<MandatoryGrantDetail> dataModelCsv) {
		this.dataModelCsv = dataModelCsv;
	}

	public boolean isShortCreditBearing() {
		return shortCreditBearing;
	}

	public void setShortCreditBearing(boolean shortCreditBearing) {
		this.shortCreditBearing = shortCreditBearing;
	}

	/**
	 * @return the wspDataModel
	 */
	public LazyDataModel<Wsp> getWspDataModel() {
		return wspDataModel;
	}

	/**
	 * @param wspDataModel
	 *            the wspDataModel to set
	 */
	public void setWspDataModel(LazyDataModel<Wsp> wspDataModel) {
		this.wspDataModel = wspDataModel;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public LazyDataModel<Wsp> getWspAwaitingMancoDataModel() {
		return wspAwaitingMancoDataModel;
	}

	public void setWspAwaitingMancoDataModel(LazyDataModel<Wsp> wspAwaitingMancoDataModel) {
		this.wspAwaitingMancoDataModel = wspAwaitingMancoDataModel;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
}
