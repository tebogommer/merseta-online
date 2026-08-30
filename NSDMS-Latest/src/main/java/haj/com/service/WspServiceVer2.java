package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import haj.com.constants.HAJConstants;
import haj.com.dao.WspDAO;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.SDFCompany;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspImpactOfStaffTraining;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.WspDocRequirements;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationErrorException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WspServiceVer2 extends AbstractService {

	private WspDAO dao = new WspDAO();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private WspSkillsRequirementsService wspSkillsRequirementsService = new WspSkillsRequirementsService();
	private WspImpactOfStaffTrainingService impactOfStaffTrainingService2 = new WspImpactOfStaffTrainingService();
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();
	private CompanyService companyService = new CompanyService();

	public WspServiceVer2(ResourceBundle resourceBundle) {
		super(resourceBundle);
		// TODO Auto-generated constructor stub
	}

	public void sendBackToSDFDocumentRejection(Wsp entity, Users users, List<RejectReasons> rejectReasonsSelected,
			String additionalInformation) throws Exception {
		validateWSP(entity, users, false, false, false, true, false, false, false);
		SDFCompany company = sdfCompanyService.locateFirstPrimarySDF(entity.getCompany());
		List<Users> sdf = new ArrayList<>();
		if (company != null)
			sdf.add(company.getSdf());
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
		TasksService.instance().findFirstInProcessAndCreateTask(null, users, entity.getId(), Wsp.class.getName(), true,
				ConfigDocProcessEnum.WSPDocumentUpload, null, sdf);
		setRejectedReasons(entity.getClass().getName(), entity.getId(), users, rejectReasonsSelected,
				ConfigDocProcessEnum.WSPDocumentUpload, additionalInformation);
	}

	private void validateWSP(Wsp wsp, Users users, boolean checkDocs, boolean checkImpactOfStaffTraining,
			boolean checkSkillsRequirments, boolean checkSignOff, boolean checkWSPData, boolean checkATRData,
			boolean checkGrantInfo) throws Exception {
		StringBuilder error = new StringBuilder("");
		List<String> exceptions = new ArrayList<>();
		if (checkDocs) {
			for (Doc doc : wsp.getDocs()) {

				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}

				if ((doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument())
						&& (doc.getData() == null || doc.getData().length == 0)) {
					exceptions.add("DONE-" + getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
				} else if (doc.isRequired() && (doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					exceptions.add("DONE-" + getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
				}

				// Validation check for training deviation upload
				if (wsp.getWspType() != WspTypeEnum.IntersetaTransferMandGrant) {
					if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.Deviation) {
						if (wsp.getDeviatedWorkplaceSkillsPlan() != null
								&& wsp.getDeviatedWorkplaceSkillsPlan().getId() == HAJConstants.YES_ID) {
							if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
								doc.getConfigDoc().setRequiredDocument(true);
								exceptions.add("DONE-"
										+ getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
							}
						} else {
							if (WspCalculationDataService.instance().calculateDeviationByWsp(wsp)) {
								if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
									doc.getConfigDoc().setRequiredDocument(true);
									exceptions.add("DONE-"
											+ getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
								}
							}
						}
					}
				}
			}
		}

		if (checkSkillsRequirments
				&& (wsp.getCompany().getNonLevyPaying() == null || !wsp.getCompany().getNonLevyPaying())) {
			if (wspSkillsRequirementsService.findByWspCount(wsp) == 0) {
				exceptions.add("skills.requirement.outstanding");
			}
		}

		if (checkImpactOfStaffTraining) {
			List<WspImpactOfStaffTraining> it = impactOfStaffTrainingService2.findByWsp(wsp.getId());
			if (it == null || it.size() > 0) {
				exceptions.add("impact.of.staff.training.outstanding");
			}
		}
		if (checkSignOff) {
			for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
				if (wspSignoff.getUser().equals(users) && (wspSignoff.getAccept() == null || !wspSignoff.getAccept())) {
					exceptions.add("accept.ts.cs");
				}
			}
		}
		if (checkWSPData) {
			if (mandatoryGrantDetailService.findByWSPCount(wsp, WspReportEnum.WSP) == 0) {
				exceptions.add("no.wsp.captured");
			}
		}
		if (checkATRData && wsp.getWspType() != WspTypeEnum.Discretionary) {
			if (mandatoryGrantDetailService.findByWSPCount(wsp, WspReportEnum.ATR) == 0
					&& (wsp.getCompletedTraining() == null
							|| wsp.getCompletedTraining().getId() == HAJConstants.YES_ID)) {
				exceptions.add("no.atr.captured");
			}
			if (wsp.getCompletedTraining() != null && wsp.getCompletedTraining().getId() == HAJConstants.YES_ID) {
				if (wsp.getTotalPayroll() == null) {
					// #{initiatewspUI.wsp.totalPayroll}
					exceptions.add("no.atr.payroll.captured");
				}
			}
		}

		// validation checks on the information that is entered on the screen
		if (checkGrantInfo) {
//			if (wsp.getWspType() != WspTypeEnum.Discretionary) {}
			boolean nonMersetaNonLevyPaying = (wsp.getCompany().getNonLevyPaying() != null
					&& wsp.getCompany().getNonLevyPaying() && wsp.getCompany().getSicCode() != null
					&& "Unknown".equals(wsp.getCompany().getSicCode().getChamber().getCode()));
			if (!nonMersetaNonLevyPaying) {
				/*
				 * Skills Gap Validation Start
				 * /MerSETA/src/main/webapp/pages/externalparty/wsp/skillsgap.xhtml
				 */
				// Do you keep track of skills gaps in your organization?
				if (wsp.getTrackSkillsGap() == null) {
					exceptions.add("no.skills.gap.indicated");
				} else if (wsp.getTrackSkillsGap().getId() == HAJConstants.YES_ID) {
					// How do you keep track of skills gap?
					if (wsp.getSkillsGapTrackSelection() == null) {
						exceptions.add("no.skills.gap.record.kept");
					}
				}
				/*
				 * Skills Gap Validation End
				 */
			}
			// #{not initiatewspUI.wsp.company.nonLevyPaying}
			if (!wsp.getCompany().getNonLevyPaying()) {
				/*
				 * ATR Validation Start
				 * /MerSETA/src/main/webapp/pages/externalparty/wsp/atr.xhtml
				 */
				// Have you completed any training in the current reporting period?
				if (wsp.getCompletedTraining() == null) {
					exceptions.add("atr.no.completed.training.provided");
				}
				// Have you deviated from the Workplace Skills Plan?
				if (wsp.getDeviatedWorkplaceSkillsPlan() == null) {
					exceptions.add("atr.training.deviation.no.provided");
				}
				/*
				 * ATR Validation End /MerSETA/src/main/webapp/pages/externalparty/wsp/atr.xhtml
				 */

				/*
				 * PIVITOL Training Report Validation Start
				 * /MerSETA/src/main/webapp/pages/externalparty/wsp/pivitoltraining.xhtml NEED
				 * MORE INFORMATION
				 */
				/*
				 * PIVITOL Training Report Validation End
				 * /MerSETA/src/main/webapp/pages/externalparty/wsp/pivitoltraining.xhtml
				 */
			}

			/*
			 * WSP FULL /MerSETA/src/main/webapp/pages/externalparty/wsp/wsp.xhtml
			 */
			// Indicate whether your planned training is in line with your Employment Equity
			// Plan?
			if (wsp.getEmploymentEquityPlanInline() == null) {
				exceptions.add("wsp.no.planned.training.provided");
			}
			// If applying for a Discretionary Grant and the application exceeds the 49.5%
			// value of the levy contribution, would your organisation consider a partial
			// funding partnership with merSETA? Please Refer to the merSETA Grant Policy if
			// unclear.
			if (wsp.getWspType() != WspTypeEnum.Mandatory) {
				if (wsp.getCoFundingPartnership() == null) {
					exceptions.add("wsp.no.co.funding.indicated");
				}
			}
			/*
			 * WSP FULL /MerSETA/src/main/webapp/pages/externalparty/wsp/wsp.xhtml
			 */
		}

		if (exceptions.size() > 0) {
			for (String string : exceptions) {
				if (string.indexOf("DONE-") == -1) {
					error.append("<li>" + getEntryLanguage(string) + "</li>");
				} else {
					error.append("<li>" + string.replaceAll("DONE-", "") + "</li>");
				}
			}
			throw new ValidationErrorException("<ul>" + error.toString() + "</ul>");
		}
	}

	public void setRejectedReasons(String className, Long targetkey, Users users,
			List<RejectReasons> rejectReasonsSelected, ConfigDocProcessEnum configProcess, String additionalInformation)
			throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		if (rejectReasonsSelected != null && rejectReasonsSelected.size() != 0) {
			entityList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetkey, className,
					rejectReasonsSelected, users, configProcess, additionalInformation));
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(targetkey, className, configProcess);
		}
		if (entityList.size() != 0) {
			dao.createBatch(entityList);
		}
	}

	public void rejectToSdfToAppealVersionTwo(Wsp entity, Users users, Roles role, Tasks tasks,
			List<RejectReasons> selectedRejectReasons, String additionalInformation) throws Exception {

		SDFCompany primarySDF = companyService.findPrimarySDF(entity.getCompany());
		if (primarySDF.getSdf() == null && primarySDF.getSdf().getId() == null) {
			throw new Exception("Unable to locate primary SDF for company: " + entity.getCompany().getLevyNumber());
		}
		List<Users> taskUsers = new ArrayList<>();
		taskUsers.add(primarySDF.getSdf());

		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWithSdf(true);
		entity.setSdfAppealedGrant(false);
		entity.setGrantRejected(true);
		entity.setGrantRejectedDate(getSynchronizedDate());
		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);

		if (role != null && role.getId() != null) {
			WspRejectionInformationService.instance().updateCreateLastestEntries(entity, selectedRejectReasons, users,
					tasks, role);
		} else {
			WspRejectionInformationService.instance().updateCreateLastestEntries(entity, selectedRejectReasons, users,
					tasks, null);
		}

		TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(),
				true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName,
						entity.getCompany().getCompanyName()),
				taskUsers);
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), users, selectedRejectReasons,
					ConfigDocProcessEnum.WSP, additionalInformation);
		}
	}

}
