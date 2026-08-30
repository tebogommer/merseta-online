package haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.Funding;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.GrantOfoSelection;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.JasperService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.UploadTrackerAtrWspService;
import haj.com.service.lookup.GrantOfoSelectionService;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "atrUI")
@ViewScoped
public class AtrUI extends AbstractUI {

	private MandatoryGrantDetail mandatoryGrant;
	private List<MandatoryGrantDetail> mandatoryGrants;
	private MandatoryGrantDetailService mandatoryGrantService = new MandatoryGrantDetailService();
	private WspReportEnum wspReport;
	private Date endDatLimit;
	private Date startDateLimit;
	private List<MandatoryGrantDetail> mandatoryGrantsCSV;

	private boolean skillsSet;
	private boolean skillsProgram;
	private boolean shortCreditBearing;

	/** MandatoryGrantDetail lazy data model */
	private LazyDataModel<MandatoryGrantDetail> dataModel;

	/** MandatoryGrantDetail CSV lazy data model */
	private LazyDataModel<MandatoryGrantDetail> dataModelCsv;
	public CSVUtil csvUtil = new CSVUtil();
	
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
		wspReport = WspReportEnum.ATR;
		mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
		today = GenericUtility.getStartOfDay(getNow());
		if (getSessionUI().getWspSession() != null && getSessionUI().getWspSession().getFinYear() != null) {
			grantOfoSelection = GrantOfoSelectionService.instance().findByGrantYearListReturnFirst(getSessionUI().getWspSession().getFinYear());
		}
		
		// pivitolInfo();
		checkDateLimits();
		pivitolInfo();
	}

	public void pivitolInfo() {
		try {
			mandatoryGrantsDetailInfo();
			mandatoryGrantsDetailInfoCsv();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void deleteAll() {
		try {
			
			try {
				UploadTrackerAtrWspService.instance().createEntry("ATR", 0, "Delete All Entries Start", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			
			this.mandatoryGrantService.deleteAll(getSessionUI().getWspSession(), WspReportEnum.ATR);
			
			try {
				UploadTrackerAtrWspService.instance().createEntry("ATR", 0, "Delete All Entries End", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			
			pivitolInfo();
			super.runClientSideExecute("updatePivotalTraining()");
			super.runClientSideExecute("updateNonSetaTraining()");
			super.runClientSideExecute("calCosts()");
			super.runClientSideExecute("updateDocs()");
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteAllCSV() {
		try {
			
			try {
				UploadTrackerAtrWspService.instance().createEntry("ATR", 0, "Delete All CSV Entries Start", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			this.mandatoryGrantService.deleteAllCSV(getSessionUI().getWspSession(), WspReportEnum.ATR);
			try {
				UploadTrackerAtrWspService.instance().createEntry("ATR", 0, "Delete All CSV Entries End", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			
			pivitolInfo();
			
			super.runClientSideExecute("updatePivotalTraining()");
			super.runClientSideExecute("updateNonSetaTraining()");
			super.runClientSideExecute("calCosts()");
			super.runClientSideExecute("updateDocs()");
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadCSV() {
		try {
			List<MandatoryGrantDetail> mgd = mandatoryGrantService.findByWSPCsvAll(getSessionUI().getWspSession(),
					wspReport);
			if (mgd.size() > 0) {
				String csv = CSVUtil.writeCSV(mgd, ",", false);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "ATR_ERROR_CSV.csv", "text/csv");
			} else {
				addWarningMessage("No WSP data to export");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadCSV1() {
		try {
			List<MandatoryGrantDetail> mgd = mandatoryGrantService.findByWSPCsvAll(getSessionUI().getWspSession(),
					wspReport);
			if (mgd.size() > 0) {
				String csv = CSVUtil.writeCSV(mgd, ",", false);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "ATR_CSV.csv", "text/csv");
			} else {
				addWarningMessage("No WSP data to export");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			pivitolInfo();
			super.runClientSideExecute("updatePivotalTraining()");
			super.runClientSideExecute("updateNonSetaTraining()");
			super.runClientSideExecute("calCosts()");
			super.runClientSideExecute("updateDocs()");
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
			pivitolInfo();
			super.runClientSideExecute("updatePivotalTraining()");
			super.runClientSideExecute("updateNonSetaTraining()");
			super.runClientSideExecute("calCosts()");
			super.runClientSideExecute("updateDocs()");
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMandatory() {
		try {
			mandatoryGrantService.delete(mandatoryGrant);
			mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
			super.runClientSideExecute("updatePivotalTraining()");
			super.runClientSideExecute("updateNonSetaTraining()");
			super.runClientSideExecute("calCosts()");
			super.runClientSideExecute("updateDocs()");
			pivitolInfo();
			addInfoMessage(getEntryLanguage("row.deleted"));
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

	public void applyNQFData() {
		try {
			mandatoryGrantService.applyNQFData(mandatoryGrant);
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

	public void clearInfo() {
		try {
			mandatoryGrant.setIdNumber(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void setDOBGenderNationality(AjaxBehaviorEvent even) {
		try {
			if(GenericUtility.checkRsaId(mandatoryGrant.getIdNumber())) {
				GenericUtility.calcIDData(mandatoryGrant);
			}
			else {
				clearInfo();
				throw new Exception("ID Number not valid");
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void checkDateLimits() {
		try {
			if (mandatoryGrant.getFunding() != null
					&& mandatoryGrant.getFunding().getId() == HAJConstants.DISC_FUNDING_ID) {
				if (GenericUtility.checkBeforeApril(getNow())) {
					startDateLimit = GenericUtility.deductMonthsFromDate(GenericUtility.startDateOfCurrentYear(), 12);
					endDatLimit = GenericUtility.getMarch(getNow());
				} else {
					startDateLimit = GenericUtility.startDateOfCurrentYear();
					endDatLimit = GenericUtility.getMarch(GenericUtility.addYearsToDate(getNow(), 1));
				}
			} else {
				if (GenericUtility.checkBeforeApril(getNow())) {
					startDateLimit = GenericUtility.deductMonthsFromDate(GenericUtility.startDateOfCurrentYear(), 12);
					endDatLimit = GenericUtility.deductMonthsFromDate(GenericUtility.endDateOfCurrentYear(), 12);
				} else {
					startDateLimit = GenericUtility.startDateOfCurrentYear();
					endDatLimit = GenericUtility.endDateOfCurrentYear();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Date getEndDatLimit() {
		return endDatLimit;
	}

	public void setEndDatLimit(Date endDatLimit) {
		this.endDatLimit = endDatLimit;
	}

	public Date getStartDateLimit() {
		return startDateLimit;
	}

	public void setStartDateLimit(Date startDateLimit) {
		this.startDateLimit = startDateLimit;
	}

	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event) {
		try {

			logger.info("Starting file upload for " + getSessionUI().getWspSession().getCompany().getCompanyName());
			List<MandatoryGrantDetail> mandatoryGrantCSVImports = (List<MandatoryGrantDetail>) (List<?>) csvUtil.getObjects(MandatoryGrantDetail.class, event.getFile().getInputstream(), ",");
			try {
				UploadTrackerAtrWspService.instance().createEntry("ATR", mandatoryGrantCSVImports.size(), "CSV Upload Start", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
			} catch (Exception e) {
				logger.fatal(e);
			}
			
			if (mandatoryGrantCSVImports.size() <= 20000) {
				logger.info("Finished parsing upload for " + getSessionUI().getWspSession().getCompany().getCompanyName() + " number of entries: " + mandatoryGrantCSVImports.size());
				// version one
//				mandatoryGrantService.save(mandatoryGrantCSVImports, getSessionUI().getWspSession(), wspReport);
				// version two
				mandatoryGrantService.saveVersionTwo(mandatoryGrantCSVImports, getSessionUI().getWspSession(), wspReport, grantOfoSelection);
				logger.info("Finished persisting upload for " + getSessionUI().getWspSession().getCompany().getCompanyName());
				// 	trying to remove out of memory after successful upload
				try {
					UploadTrackerAtrWspService.instance().createEntry("ATR", mandatoryGrantCSVImports.size(), "CSV Upload End", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
				} catch (Exception e) {
					logger.fatal(e);
				}
				super.addInfoMessage("Upload Succesful");
			} else { 
				mandatoryGrantCSVImports = null;
				throw new Exception("Exceeded Batch Size Limit. Please Upload a CSV containing No More Than 20 000 Entries.");
			}
			mandatoryGrantCSVImports = null;
			pivitolInfo();
			super.runClientSideExecute("updatePivotalTraining()");
			super.runClientSideExecute("updateNonSetaTraining()");
			super.runClientSideExecute("calCosts()");
			super.runClientSideExecute("updateDocs()");
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
			UploadTrackerAtrWspService.instance().createEntry("ATR", 0, "ERROR: CSV Upload End: ID number exists for the specified OFO and Intervention Type", getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
			UploadTrackerAtrWspService.instance().createEntry("ATR", 0, "ERROR: CSV Upload End: " + e.getMessage(), getSessionUI().getWspSession().getId(), getSessionUI().getActiveUser().getId());
		} finally {
			csvUtil = new CSVUtil();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void handleFileUploadAtr(FileUploadEvent event) {
		try {

			logger.info("Starting file upload for " + getSessionUI().getWspSession().getCompany().getCompanyName());
			List<MandatoryGrantDetail> mandatoryGrantCSVImports = (List<MandatoryGrantDetail>) (List<?>) csvUtil
					.getObjects(MandatoryGrantDetail.class, event.getFile().getInputstream(), ",");
			logger.info("Finished parsing upload for " + getSessionUI().getWspSession().getCompany().getCompanyName()
					+ " number of entries: " + mandatoryGrantCSVImports.size());
			mandatoryGrantService.save(mandatoryGrantCSVImports, getSessionUI().getWspSession(), wspReport);
			logger.info(
					"Finished persisting upload for " + getSessionUI().getWspSession().getCompany().getCompanyName());
			pivitolInfo();

			super.runClientSideExecute("updatePivotalTraining()");
			super.runClientSideExecute("updateNonSetaTraining()");
			super.runClientSideExecute("calCosts()");
			super.runClientSideExecute("updateDocs()");
			super.addInfoMessage("Upload Succesful");
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
}
