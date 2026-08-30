package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnersAchievementStatusEISA;
import haj.com.entity.CompanyLearnersQualificationAchievementStatus;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.Users;
import haj.com.entity.datamodel.CompanyLearnersAchievementStatusEISADatamodel;
import haj.com.entity.datamodel.CompanyLearnersQualificationAchievementStatusDatamodel;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.QCTOFileTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AdministrationOfAQPService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.JasperService;
import haj.com.service.TasksService;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "administrationofaqpUI")
@ViewScoped
public class AdministrationOfAQPUI extends AbstractUI {

	private AdministrationOfAQPService service = new AdministrationOfAQPService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private List<AdministrationOfAQP> administrationofaqpList = null;
	private List<AdministrationOfAQP> administrationofaqpfilteredList = null;
	private AdministrationOfAQP administrationofaqp = null;
	private Doc doc;
	private LazyDataModel<AdministrationOfAQP> dataModel;
	private Date maxDate;
	private QCTOFileTypeEnum qctoFileTypeEnum;
	private CSVUtil csvUtil = new CSVUtil();
	private LazyDataModel<CompanyLearnersAchievementStatusEISA> dataModelAchievementStatusEISA;
	private LazyDataModel<CompanyLearnersQualificationAchievementStatus> dataModelQualificationAchievementStatus;

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
		if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ApplicationAdministrationOfAQP || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.AdministrationOfAQP)) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.administrationofaqp = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), administrationofaqp, getSessionUI().getTask().getProcessRole());
			// populateExistingLearner();
			datamodelInfo();
		} else {
			prepareNew();
			administrationofaqpInfo();
		}
	}
	
	private void datamodelInfo() {
		dataModelQualificationAchievementStatus = new CompanyLearnersQualificationAchievementStatusDatamodel(administrationofaqp);
		dataModelAchievementStatusEISA = new CompanyLearnersAchievementStatusEISADatamodel(administrationofaqp);
	}

	public void dateSelect() {
		this.maxDate = GenericUtility.getEndOfDay(administrationofaqp.getEisaDate());
		this.administrationofaqp.setEndTime(null);
	}

	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(administrationofaqp.getId());
				doc.setTargetClass(AdministrationOfAQP.class.getName());

				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event) {
		try {
			if (qctoFileTypeEnum == QCTOFileTypeEnum.File1) {
				List<CompanyLearnersQualificationAchievementStatus> companyLearnersQualificationAchievementStatus = (List<CompanyLearnersQualificationAchievementStatus>) (List<?>) csvUtil.readFixedFileLength(CompanyLearnersQualificationAchievementStatus.class, event.getFile().getInputstream());
				service.processFile(companyLearnersQualificationAchievementStatus);
			} else {
				List<CompanyLearnersAchievementStatusEISA> companyLearnersQualificationAchievementStatus = (List<CompanyLearnersAchievementStatusEISA>) (List<?>) csvUtil.readFixedFileLength(CompanyLearnersAchievementStatusEISA.class, event.getFile().getInputstream());
				service.processFileEISA(companyLearnersQualificationAchievementStatus);
			}
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

	public void administrationofaqpInfo() {

		dataModel = new LazyDataModel<AdministrationOfAQP>() {

			private static final long serialVersionUID = 1L;
			private List<AdministrationOfAQP> retorno = new ArrayList<AdministrationOfAQP>();

			@Override
			public List<AdministrationOfAQP> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allAdministrationOfAQP(AdministrationOfAQP.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(AdministrationOfAQP.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AdministrationOfAQP obj) {
				return obj.getId();
			}

			@Override
			public AdministrationOfAQP getRowData(String rowKey) {
				for (AdministrationOfAQP obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void downloadLearnerJasper() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			String fileName = "LearnerRegistrationFormForEISA.pdf";
			// INSERT JASPER HERE
			// MM-282

			Users learner = null;// To be Fixed
			Integer learnerAge = 24;// To be fixed
			String specialAssessmentNeeds = "";// To be Fixed
			Company summativeAssessment = null;// To be fixed
			Users sdp = null;// To be Fixed
			boolean statementOfResultsAttached = true;// To be fixed
			Qualification qualification = null;// To be fixed
			Company assessmentCentre = null;// To be fixed
			String dateEISA = "";// To be fixed
			String timeEISA = "";// To be fixed

			params.put("learner", learner);
			params.put("learnerAge", learnerAge);
			params.put("specialAssessmentNeeds", specialAssessmentNeeds);
			params.put("summativeAssessment", summativeAssessment);
			params.put("sdp", sdp);
			params.put("statementOfResultsAttached", statementOfResultsAttached);
			params.put("qualification", qualification);
			params.put("assessmentCentre", assessmentCentre);
			params.put("dateEISA", dateEISA);
			params.put("timeEISA", timeEISA);

			JasperService.instance().createReportFromDBtoServletOutputStream("LearnerRegistrationFormForEISA.jasper", params, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadConfidentialityAgreement() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			String fileName = "";
			// INSERT JASPER HERE
			if (getSessionUI().getActiveUser().equals(administrationofaqp.getAssessor())) {
				// MM-306
				fileName = "Notification_of_EISA.pdf";
				params.put("assessmentCentre", administrationofaqp.getAssessmentCenter());
				params.put("contactPerson", administrationofaqp.getContactPerson());
				params.put("qualification", administrationofaqp.getQualification());
				params.put("regStartDate", HAJConstants.sdfDDMMYYYY.format(administrationofaqp.getEisaDate()));
				params.put("regEndDate", HAJConstants.sdfDDMMYYYY.format(administrationofaqp.getEndTime()));
				params.put("enrolmentLastDate", "");// To be Fixed
				params.put("archiementLastDate", "");// To be Fixed
				JasperService.instance().createReportFromDBtoServletOutputStream("ConfidentialityAgreement.jasper", params, fileName);

			} else {
				// MM-307
				fileName = "Confidentiality_Agreement.pdf";
				// ASSESSOR/ EXAMINER/MARKER/ MODERATOR
				Users user = new Users();
				if (administrationofaqp.getModerator() != null) {
					user = administrationofaqp.getModerator();
				} else if (administrationofaqp.getAssessor() != null) {
					user = administrationofaqp.getAssessor();
				}

				params.put("user", user);
				params.put("qualification", administrationofaqp.getQualification());
				JasperService.instance().createReportFromDBtoServletOutputStream("ConfidentialityAgreementExaminerModeratorMarker.jasper", params, fileName);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadManagerJasper() {
		try {
			service.downloadManagerJasper(administrationofaqp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadAdministrationJasper() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			String fileName = "";
			// INSERT JASPER HERE

			if (getSessionUI().getActiveUser().equals(administrationofaqp.getAssessor())) {
				// MM-309
				Users examiner = administrationofaqp.getAssessor();// To be fixed
				Users modarator = administrationofaqp.getModerator();// To be fixed
				String examinoeDateSentToAQP = "";// To be fixed
				String modaratorDateSentToAQP = "";// To be fixed
				String modaratorDateReceivedToAQP = "";// To be fixed
				Qualification qualification = administrationofaqp.getQualification();// To be fixed
				String dateOfEISA = HAJConstants.sdf3.format(administrationofaqp.getEisaDate());// To be fixed

				long diffInMillies = Math.abs(administrationofaqp.getEndTime().getTime() - administrationofaqp.getEisaDate().getTime());
				long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

				String duration = diff + " hours";// To be fixed

				String totalMarks = "";// To be fixed
				String passMark = "";// To be fixed

				params.put("examiner", examiner);
				params.put("modarator", modarator);
				params.put("examinoeDateSentToAQP", examinoeDateSentToAQP);
				params.put("modaratorDateSentToAQP", modaratorDateSentToAQP);
				params.put("modaratorDateReceivedToAQP", modaratorDateReceivedToAQP);
				params.put("qualification", qualification);
				params.put("dateOfEISA", dateOfEISA);
				params.put("duration", duration);
				params.put("totalMarks", totalMarks);
				params.put("passMark", passMark);

				fileName = "ExaminerReport.pdf";
				JasperService.instance().createReportFromDBtoServletOutputStream("ExaminerReport.jasper", params, fileName);
			} else {

				// MM-310
				Users examiner = administrationofaqp.getAssessor();// To be fixed
				Users modarator = administrationofaqp.getModerator();// To be fixed
				String examinoeDateSentToAQP = "";// To be fixed
				String modaratorDateSentToAQP = "";// To be fixed
				String modaratorDateReceivedToAQP = "";// To be fixed
				Qualification qualification = null;// To be fixed
				String dateOfEISA = HAJConstants.sdf3.format(administrationofaqp.getEisaDate());// To be fixed

				long diffInMillies = Math.abs(administrationofaqp.getEndTime().getTime() - administrationofaqp.getEisaDate().getTime());
				long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

				String duration = diff + " hours";// To be fixed
				String totalMarks = "";// To be fixed
				String passMark = "";// To be fixed

				params.put("examiner", examiner);
				params.put("modarator", modarator);
				params.put("examinoeDateSentToAQP", examinoeDateSentToAQP);
				params.put("modaratorDateSentToAQP", modaratorDateSentToAQP);
				params.put("modaratorDateReceivedToAQP", modaratorDateReceivedToAQP);
				params.put("qualification", qualification);
				params.put("dateOfEISA", dateOfEISA);
				params.put("duration", duration);
				params.put("totalMarks", totalMarks);
				params.put("passMark", passMark);

				fileName = "ModeratorReport.pdf";
				JasperService.instance().createReportFromDBtoServletOutputStream("ModeratorReport.jasper", params, fileName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findProviderContact() {
		try {
			List<CompanyUsers> cu = companyUsersService.findByCompanyType(administrationofaqp.getAssessmentCenter().getId(), ConfigDocProcessEnum.TP);
			Users providerContactPerson = cu.get(0).getUser();
			administrationofaqp.setContactPerson(providerContactPerson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void administrationofaqpInsert() {
		try {
			service.create(this.administrationofaqp);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			administrationofaqpInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void administrationofaqpUpdate() {
		try {
			service.update(this.administrationofaqp);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			administrationofaqpInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void administrationofaqpDelete() {
		try {
			service.delete(this.administrationofaqp);
			prepareNew();
			administrationofaqpInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNew() {
		administrationofaqp = new AdministrationOfAQP();
	}

	public void requestAdministrationOfAQP() {
		try {
			service.requestAdministrationOfAQP(administrationofaqp, getSessionUI().getActiveUser());
			prepareNew();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void completeWorkflow() {
		try {
			service.completeWorkflow(administrationofaqp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(administrationofaqp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			service.rejectWorkflow(administrationofaqp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(administrationofaqp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			service.finalRejectWorkflow(administrationofaqp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<AdministrationOfAQP> complete(String desc) {
		List<AdministrationOfAQP> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AdministrationOfAQP> getAdministrationOfAQPList() {
		return administrationofaqpList;
	}

	public void setAdministrationOfAQPList(List<AdministrationOfAQP> administrationofaqpList) {
		this.administrationofaqpList = administrationofaqpList;
	}

	public AdministrationOfAQP getAdministrationofaqp() {
		return administrationofaqp;
	}

	public void setAdministrationofaqp(AdministrationOfAQP administrationofaqp) {
		this.administrationofaqp = administrationofaqp;
	}

	public List<AdministrationOfAQP> getAdministrationOfAQPfilteredList() {
		return administrationofaqpfilteredList;
	}

	public void setAdministrationOfAQPfilteredList(List<AdministrationOfAQP> administrationofaqpfilteredList) {
		this.administrationofaqpfilteredList = administrationofaqpfilteredList;
	}

	public LazyDataModel<AdministrationOfAQP> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AdministrationOfAQP> dataModel) {
		this.dataModel = dataModel;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public QCTOFileTypeEnum getQctoFileTypeEnum() {
		return qctoFileTypeEnum;
	}

	public void setQctoFileTypeEnum(QCTOFileTypeEnum qctoFileTypeEnum) {
		this.qctoFileTypeEnum = qctoFileTypeEnum;
	}

	/**
	 * @return the dataModelAchievementStatusEISA
	 */
	public LazyDataModel<CompanyLearnersAchievementStatusEISA> getDataModelAchievementStatusEISA() {
		return dataModelAchievementStatusEISA;
	}

	/**
	 * @param dataModelAchievementStatusEISA
	 *            the dataModelAchievementStatusEISA to set
	 */
	public void setDataModelAchievementStatusEISA(LazyDataModel<CompanyLearnersAchievementStatusEISA> dataModelAchievementStatusEISA) {
		this.dataModelAchievementStatusEISA = dataModelAchievementStatusEISA;
	}

	/**
	 * @return the dataModelQualificationAchievementStatus
	 */
	public LazyDataModel<CompanyLearnersQualificationAchievementStatus> getDataModelQualificationAchievementStatus() {
		return dataModelQualificationAchievementStatus;
	}

	/**
	 * @param dataModelQualificationAchievementStatus
	 *            the dataModelQualificationAchievementStatus to set
	 */
	public void setDataModelQualificationAchievementStatus(LazyDataModel<CompanyLearnersQualificationAchievementStatus> dataModelQualificationAchievementStatus) {
		this.dataModelQualificationAchievementStatus = dataModelQualificationAchievementStatus;
	}

}
