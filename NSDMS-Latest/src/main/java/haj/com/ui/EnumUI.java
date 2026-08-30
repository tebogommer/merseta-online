package haj.com.ui;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import haj.com.entity.Blank;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ActionPlanValidiationTypeEnum;
import haj.com.entity.enums.AgeGroupEnum;
import haj.com.entity.enums.AllocationChangeTypeEnum;
import haj.com.entity.enums.AnswerTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AprlProgressReportingEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.BugReportType;
import haj.com.entity.enums.BulkApprovalEnum;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.CompanyLearnersTransferRecommendation;
import haj.com.entity.enums.CompanyRegOrSDLEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.ContractStatusEnum;
import haj.com.entity.enums.DGWindowTypeEnum;
import haj.com.entity.enums.DesignatedTradeType;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.enums.DocumentRequiredCheckEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.entity.enums.GenerateAddEnum;
import haj.com.entity.enums.GrantTypeEnum;
import haj.com.entity.enums.HighestEducationEnum;
import haj.com.entity.enums.HoldingRoomStatusEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.LearnerCompletenceEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.LearnerTransferTypeEnum;
import haj.com.entity.enums.LostTimeReason;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.MailingListTypeEnum;
import haj.com.entity.enums.MeetingTypeEnum;
import haj.com.entity.enums.ModerationEnum;
import haj.com.entity.enums.NewExistingLearnershipEnum;
import haj.com.entity.enums.NewOrLegacyEnum;
import haj.com.entity.enums.NsdpReportRunTypeEnum;
import haj.com.entity.enums.OpenClosedEnum;
import haj.com.entity.enums.PastFutureEnum;
import haj.com.entity.enums.PercentageEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.PlannedImplementedEnum;
import haj.com.entity.enums.ProgressType;
import haj.com.entity.enums.ProviderNameRegisTrationNumberEnum;
import haj.com.entity.enums.ProviderSusActionsEnum;
import haj.com.entity.enums.PublicPrivateEnum;
import haj.com.entity.enums.QCDTemplateTypeEnum;
import haj.com.entity.enums.QCTOFileTypeEnum;
import haj.com.entity.enums.QmrEquityEnum;
import haj.com.entity.enums.QmrGenderEnum;
import haj.com.entity.enums.QmrTypeSelectionEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.enums.RatingEnum;
import haj.com.entity.enums.RelationTypeEnum;
import haj.com.entity.enums.ReportGenerationEnum;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.entity.enums.SDPApplicationType;
import haj.com.entity.enums.SetaIndustryFundedEnum;
import haj.com.entity.enums.SkillsTypeEnum;
import haj.com.entity.enums.SmeTypeEnum;
import haj.com.entity.enums.StatusEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.TemplateTypeEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.TradeTypeEnum;
import haj.com.entity.enums.TrainingProviderFilterEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.TrancheRuleEnum;
import haj.com.entity.enums.TransferRequestTypeEnum;
import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.entity.enums.UrbanRuralEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.enums.WSPSearchType;
import haj.com.entity.enums.WorkplaceApprovalTypeEnum;
import haj.com.entity.enums.WorkplaceSurveyType;
import haj.com.entity.enums.WspReopenLocationEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.gpservices.GPVendorClassEnum;
import haj.com.service.ConfigDocService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnumUI.
 */
@ManagedBean(name = "enumUI")
@ViewScoped
public class EnumUI extends AbstractUI {

	private ConfigDocService configDocService = new ConfigDocService();

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
	 * @author TechFinium
	 * @throws Exception
	 * the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {

	}

	/**
	 * Gets the config doc process enum DD.
	 * @return the config doc process enum DD
	 */
	public List<SelectItem> getConfigDocProcessEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ConfigDocProcessEnum selectItem : ConfigDocProcessEnum.values()) {
			l.add(new SelectItem(selectItem, getEntryLanguage(selectItem.getFriendlyName())));
		}
		return l;
	}

	public List<SelectItem> getApprovalEnumApproveRejectedDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(ApprovalEnum.Approved, ApprovalEnum.Approved.getFriendlyName()));
		l.add(new SelectItem(ApprovalEnum.Rejected, ApprovalEnum.Rejected.getFriendlyName()));
		// for (ApprovalEnum val : YesNoEnum.values()) {
		// l.add(new SelectItem(val, val.getFriendlyName()));
		// }
		return l;
	}

	public List<SelectItem> getApprovalEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(ApprovalEnum.DeAccredited, ApprovalEnum.DeAccredited.getFriendlyName()));
		l.add(new SelectItem(ApprovalEnum.Rejected, ApprovalEnum.Rejected.getFriendlyName()));
		l.add(new SelectItem(ApprovalEnum.Approved, ApprovalEnum.Approved.getFriendlyName()));
		/*
		 * for (ApprovalEnum val : ApprovalEnum.values()) { l.add(new SelectItem(val, val.getFriendlyName())); }
		 */
		return l;
	}

	public List<SelectItem> getUsedConfigDocProcessEnumDDInTasks() {
		List<SelectItem> l = new ArrayList<>();
		try {
			List<ConfigDocProcessEnum> usedList = configDocService.usedConfigDocProcessEnumInTasks();
			for (ConfigDocProcessEnum selectItem : usedList) {
				l.add(new SelectItem(selectItem, getEntryLanguage(selectItem.getFriendlyName())));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		return l;
	}

	/**
	 * Gets the process enum DD.
	 * @return the process enum DD
	 */
	public List<SelectItem> getProcessEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ConfigDocProcessEnum selectItem : ConfigDocProcessEnum.values()) {
			l.add(new SelectItem(selectItem, getEntryLanguage(selectItem.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Gets the employed un employed enum DD.
	 * @return the employed un employed enum DD
	 */
	public List<SelectItem> getEmployedUnEmployedEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (EmployedUnEmployedEnum selectItem : EmployedUnEmployedEnum.values()) {
			l.add(new SelectItem(selectItem, getEntryLanguage(selectItem.getFriendlyName())));
		}
		return l;
	}

	/**
	 * Gets the company types enum DD.
	 * @return the company types enum DD
	 */
	public List<SelectItem> getCompanyTypesEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ConfigDocProcessEnum selectItem : ConfigDocProcessEnum.values()) {
			l.add(new SelectItem(selectItem, selectItem.getType()));
		}
		return l;
	}

	/**
	 * Populates enum list for IdPassportEnum.
	 * @return List<IdPassportEnum>
	 */
	public List<SelectItem> getIdPassportEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (IdPassportEnum val : IdPassportEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	public List<SelectItem> getNewOrLegacyEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (NewOrLegacyEnum val : NewOrLegacyEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for IdPassportEnum.
	 * @return List<IdPassportEnum>
	 */
	public List<SelectItem> getWorkplaceApprovalTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (WorkplaceApprovalTypeEnum val : WorkplaceApprovalTypeEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for CompanyStatusEnum.
	 * @return List<CompanyStatusEnum>
	 */
	public List<SelectItem> getCompanyStatusEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CompanyStatusEnum val : CompanyStatusEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for CompanyTypeEnum.
	 * @return List<CompanyTypeEnum>
	 */
	public List<SelectItem> getCompanyTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CompanyTypeEnum val : CompanyTypeEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for DocumentTrackerEnum.
	 * @return List<DocumentTrackerEnum>
	 */
	public List<SelectItem> getDocumentTrackerEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (DocumentTrackerEnum val : DocumentTrackerEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for TaskStatusEnum.
	 * @return List<TaskStatusEnum>
	 */
	public List<SelectItem> getTaskStatusEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (TaskStatusEnum val : TaskStatusEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Populates enum list for TaskStatusEnum.
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getUserPermissionEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (UserPermissionEnum val : UserPermissionEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for TaskStatusEnum.
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getUserPermissionApprovalEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(UserPermissionEnum.View, super.getEntryLanguage((UserPermissionEnum.View.getRegistrationName()))));
		l.add(new SelectItem(UserPermissionEnum.Approve, super.getEntryLanguage(UserPermissionEnum.Approve.getRegistrationName())));
		l.add(new SelectItem(UserPermissionEnum.FinalApproval, super.getEntryLanguage((UserPermissionEnum.FinalApproval.getRegistrationName()))));
		return l;
	}

	/**
	 * Populates enum list for UsersStatusEnum.
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getUsersStatusEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (UsersStatusEnum val : UsersStatusEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	public List<SelectItem> getYesNoEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (YesNoEnum val : YesNoEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getHighestEducationDD() {
		List<SelectItem> l = new ArrayList<>();
		// l.add(new SelectItem(HighestEducationEnum.BelowMetric, HighestEducationEnum.BelowMetric.getFriendlyName()));
		l.add(new SelectItem(HighestEducationEnum.AboveMetric, HighestEducationEnum.AboveMetric.getFriendlyName()));
		l.add(new SelectItem(HighestEducationEnum.Grade12to10, HighestEducationEnum.Grade12to10.getFriendlyName()));
		l.add(new SelectItem(HighestEducationEnum.Grade11toStd9, HighestEducationEnum.Grade11toStd9.getFriendlyName()));
		l.add(new SelectItem(HighestEducationEnum.Grade10toStd8, HighestEducationEnum.Grade10toStd8.getFriendlyName()));
		l.add(new SelectItem(HighestEducationEnum.Grade9toStd7, HighestEducationEnum.Grade9toStd7.getFriendlyName()));
		l.add(new SelectItem(HighestEducationEnum.Grade8toStd6, HighestEducationEnum.Grade8toStd6.getFriendlyName()));
		l.add(new SelectItem(HighestEducationEnum.BelowGrade7Std5, HighestEducationEnum.BelowGrade7Std5.getFriendlyName()));
		// l.add(new SelectItem(HighestEducationEnum.S1, HighestEducationEnum.S1.getFriendlyName()));
		// l.add(new SelectItem(HighestEducationEnum.S2, HighestEducationEnum.S2.getFriendlyName()));
		// l.add(new SelectItem(HighestEducationEnum.S3, HighestEducationEnum.S3.getFriendlyName()));
		/*
		 * for (HighestEducationEnum val : HighestEducationEnum.values()) { l.add(new SelectItem(val, val.getFriendlyName())); }
		 */
		return l;
	}

	public List<SelectItem> getLearnerCompletenceEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (LearnerCompletenceEnum val : LearnerCompletenceEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Populates enum list for CompanyRegOrSDLEnum.
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getCompanyRegOrSDLEnumDD() {
		/*
		 * REMOVED SINCE NO LONGER NEEDED TO SEARCH BY COMPANY REG List<SelectItem> l = new ArrayList<SelectItem>(); for (CompanyRegOrSDLEnum val : CompanyRegOrSDLEnum.values()) { l.add(new SelectItem(val, val.getFriendlyName())); } return l;
		 */
		return getSDLEnumDD();
	}

	public List<SelectItem> getSDLEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		// for (CompanyRegOrSDLEnum val : CompanyRegOrSDLEnum.values()) {
		l.add(new SelectItem(CompanyRegOrSDLEnum.SDLNumber, CompanyRegOrSDLEnum.SDLNumber.getFriendlyName()));
		// }
		return l;
	}

	public List<SelectItem> getEntityIdEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		// for (CompanyRegOrSDLEnum val : CompanyRegOrSDLEnum.values()) {
		l.add(new SelectItem(CompanyRegOrSDLEnum.SDLNumber, "Entity ID"));
		// }
		return l;
	}

	public List<SelectItem> getProviderNameRegisTrationNumberEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		// for (ProviderNameRegisTrationNumberEnum val :
		// ProviderNameRegisTrationNumberEnum.values()) {
		l.add(new SelectItem(ProviderNameRegisTrationNumberEnum.ProviderNumber, ProviderNameRegisTrationNumberEnum.ProviderNumber.getFriendlyName()));
		// }
		return l;
	}

	/**
	 * Populates enum list for CompanyUserTypeEnum.
	 * @return List<CompanyUserTypeEnum>
	 */
	public List<SelectItem> getCompanyUserEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CompanyUserTypeEnum val : CompanyUserTypeEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Gets the past future enum DD.
	 * @return the past future enum DD
	 */
	public List<SelectItem> getPastFutureEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (PastFutureEnum val : PastFutureEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for EmploymentStatusEnum.
	 * @return List<EmploymentStatusEnum>
	 */
	public List<SelectItem> getEmploymentStatusEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (EmploymentStatusEnum val : EmploymentStatusEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Populates enum list for EmploymentStatusEnum.
	 * @return List<EmploymentStatusEnum>
	 */
	public List<SelectItem> getEmploymentStatusFilteredEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (EmploymentStatusEnum val : EmploymentStatusEnum.values()) {
			if (val != EmploymentStatusEnum.NoLongerAtCompany) {
				l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
			}
		}
		return l;
	}

	/**
	 * Gets the pivotal non pivotal DD.
	 * @return the pivotal non pivotal DD
	 */
	public List<SelectItem> getPivotalNonPivotalDD() {
		List<SelectItem> l = new ArrayList<>();
		for (PivotNonPivotEnum val : PivotNonPivotEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Gets the completed plannedl DD.
	 * @return the completed plannedl DD
	 */
	public List<SelectItem> getCompletedPlannedlDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CompletedPlannedEnum val : CompletedPlannedEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Gets the rating enum DD.
	 * @return the rating enum DD
	 */
	public List<SelectItem> getRatingEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (RatingEnum val : RatingEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	public List<SelectItem> getRatingEnumSelectOneDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(null, "--- Select ----"));
		for (RatingEnum val : RatingEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	/**
	 * Gets the age group enum DD.
	 * @return the age group enum DD
	 */
	public List<SelectItem> getAgeGroupEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (AgeGroupEnum val : AgeGroupEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Gets the wsp report enum DD.
	 * @return the wsp report enum DD
	 */
	public List<SelectItem> getWspReportEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (WspReportEnum val : WspReportEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Gets the wsp type enum DD.
	 * @return the wsp type enum DD
	 */
	public List<SelectItem> getWspTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (WspTypeEnum val : WspTypeEnum.values()) {
			if (val != WspTypeEnum.IntersetaTransferMandGrant) {
				l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
			}
		}
		return l;
	}

	/**
	 * Gets the template type enum DD.
	 * @return the template type enum DD
	 */
	public List<SelectItem> getTemplateTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (TemplateTypeEnum val : TemplateTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getMailEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (MailEnum val : MailEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getMailTagsEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (MailTagsEnum val : MailTagsEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<String> getMailTags() {
		List<String> l = new ArrayList<String>();
		for (MailTagsEnum val : MailTagsEnum.values()) {
			l.add(val.getTagName());
		}
		return l;
	}

	public List<SelectItem> getPlannedImplementedEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (PlannedImplementedEnum val : PlannedImplementedEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getGrantTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (GrantTypeEnum val : GrantTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Gets the Document Required Check Enum DD.
	 * @return the Document Required Check Enum DD
	 */
	public List<SelectItem> getDocumentRequiredCheckEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (DocumentRequiredCheckEnum selectItem : DocumentRequiredCheckEnum.values()) {
			l.add(new SelectItem(selectItem, getEntryLanguage(selectItem.getFriendlyName())));
		}
		return l;
	}

	public List<SelectItem> getGPVendorClassEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (GPVendorClassEnum selectItem : GPVendorClassEnum.values()) {
			l.add(new SelectItem(selectItem, selectItem.getGPName()));
		}
		return l;
	}

	public List<SelectItem> getInterventionTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (InterventionTypeEnum selectItem : InterventionTypeEnum.values()) {
			l.add(new SelectItem(selectItem, selectItem.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Populates enum list for MeetingTypeEnum.
	 * @return List<AssessorModeratorAppTypeEnum>
	 */
	public List<SelectItem> getMeetingTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (MeetingTypeEnum val : MeetingTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getBugReportType() {
		List<SelectItem> l = new ArrayList<>();
		for (BugReportType selectItem : BugReportType.values()) {
			l.add(new SelectItem(selectItem, selectItem.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getMonths() {
		List<SelectItem> l = new ArrayList<>();
		for (Month selectItem : Month.values()) {
			l.add(new SelectItem(selectItem, selectItem.getDisplayName(TextStyle.FULL, Locale.UK)));
		}
		return l;
	}

	/**
	 * Populates enum list for AssessorModeratorAppTypeEnum.
	 * @return List<AssessorModeratorAppTypeEnum>
	 */
	public List<SelectItem> getAssessorModeratorAppTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (AssessorModeratorAppTypeEnum val : AssessorModeratorAppTypeEnum.values()) {
			if (val != AssessorModeratorAppTypeEnum.AssessorExtensionOfScope && val != AssessorModeratorAppTypeEnum.AssessorReRegistration && val != AssessorModeratorAppTypeEnum.ModeratorExtensionOfScope && val != AssessorModeratorAppTypeEnum.ModeratorReRegistration) {
				l.add(new SelectItem(val, val.getRegistrationName()));
			}
		}
		return l;
	}

	public List<SelectItem> getSkillsTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (SkillsTypeEnum val : SkillsTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getQCDTemplateTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (QCDTemplateTypeEnum val : QCDTemplateTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getNewExistingLearnershipEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (NewExistingLearnershipEnum val : NewExistingLearnershipEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getAccreditationApplicationTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (AccreditationApplicationTypeEnum val : AccreditationApplicationTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getAccreditationApplicationTypeEnumDDForFirstApp() {
		List<SelectItem> l = new ArrayList<>();
		for (AccreditationApplicationTypeEnum val : AccreditationApplicationTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getAccreditationApplicationTypeEnumDDForExistingApp() {
		List<SelectItem> l = new ArrayList<>();
		for (AccreditationApplicationTypeEnum val : AccreditationApplicationTypeEnum.values()) {
			if (val != AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL) {
				l.add(new SelectItem(val, val.getFriendlyName()));
			}

		}
		return l;
	}

	public List<SelectItem> getLearnerStatusEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (LearnerStatusEnum val : LearnerStatusEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getDiscretionalWithdrawalEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.EconomicDecline, DiscretionalWithdrawalAppealEnum.EconomicDecline.getFriendlyName()));
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.CompanyClosingDown, DiscretionalWithdrawalAppealEnum.CompanyClosingDown.getFriendlyName()));
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.ChangeSkillsPlanningRequirements, DiscretionalWithdrawalAppealEnum.ChangeSkillsPlanningRequirements.getFriendlyName()));
		return l;
	}

	public List<SelectItem> getDiscretionalAppealEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.RequestForNumbersAppliedFor, DiscretionalWithdrawalAppealEnum.RequestForNumbersAppliedFor.getFriendlyName()));
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.ReduceTheAllocatedNumber, DiscretionalWithdrawalAppealEnum.ReduceTheAllocatedNumber.getFriendlyName()));
		return l;
	}

	public List<SelectItem> getDiscretionalWithdrawalEmployeeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.EconomicDecline, DiscretionalWithdrawalAppealEnum.EconomicDecline.getFriendlyName()));
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.CompanyClosingDown, DiscretionalWithdrawalAppealEnum.CompanyClosingDown.getFriendlyName()));
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.ChangeSkillsPlanningRequirements, DiscretionalWithdrawalAppealEnum.ChangeSkillsPlanningRequirements.getFriendlyName()));
		l.add(new SelectItem(DiscretionalWithdrawalAppealEnum.MoaThirtyDayRule, DiscretionalWithdrawalAppealEnum.MoaThirtyDayRule.getFriendlyName()));
		return l;
	}

	public List<SelectItem> getTransferRequestTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (TransferRequestTypeEnum val : TransferRequestTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getLearnerTransferTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (LearnerTransferTypeEnum val : LearnerTransferTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getLostTimeReasonDD() {
		List<SelectItem> l = new ArrayList<>();
		for (LostTimeReason val : LostTimeReason.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getTerminationTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (TerminationTypeEnum val : TerminationTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getTerminationTypeEnumNotSystemDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(TerminationTypeEnum.MutualSidedTermination, TerminationTypeEnum.MutualSidedTermination.getFriendlyName()));
		l.add(new SelectItem(TerminationTypeEnum.OneSidedTermination, TerminationTypeEnum.OneSidedTermination.getFriendlyName()));
		l.add(new SelectItem(TerminationTypeEnum.DeceasedLearnerTermination, TerminationTypeEnum.DeceasedLearnerTermination.getFriendlyName()));
		return l;
	}

	public List<SelectItem> getTerminationTypeEnumForLearner() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(TerminationTypeEnum.OneSidedTermination, TerminationTypeEnum.OneSidedTermination.getFriendlyName()));
		return l;
	}

	public List<SelectItem> getLearnerChangeTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (LearnerChangeTypeEnum val : LearnerChangeTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getCompetenceEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CompetenceEnum val : CompetenceEnum.values()) {
			if (val != CompetenceEnum.AbsentCancelled) {
				l.add(new SelectItem(val, val.getFriendlyName()));
			}
		}
		return l;
	}

	public List<SelectItem> getAllCompetenceEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CompetenceEnum val : CompetenceEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getCollectionEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CollectionEnum val : CollectionEnum.values()) {
			l.add(new SelectItem(val, val.getRegistrationName()));
		}
		return l;
	}

	public List<SelectItem> getAnswerTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (AnswerTypeEnum val : AnswerTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getUrbanRuralEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (UrbanRuralEnum val : UrbanRuralEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getWorkplaceSurveyTypeDD() {
		List<SelectItem> l = new ArrayList<>();
		for (WorkplaceSurveyType val : WorkplaceSurveyType.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getProgressTypeDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ProgressType val : ProgressType.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getTradeTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (TradeTypeEnum val : TradeTypeEnum.values()) {
			if (val != TradeTypeEnum.LEARNERSHIP) l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getStatusEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (StatusEnum val : StatusEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getQCTOFileTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (QCTOFileTypeEnum val : QCTOFileTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getContractStatusEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ContractStatusEnum val : ContractStatusEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getRelationTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (RelationTypeEnum val : RelationTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getSmeTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (SmeTypeEnum val : SmeTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getAssessorModTypeDD() {
		List<SelectItem> l = new ArrayList<>();
		for (AssessorModType val : AssessorModType.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Populates enum list for EmploymentStatusEnum and remove No Longer at company.
	 * @return List<EmploymentStatusEnum>
	 */
	public List<SelectItem> getEmployedUnemployedOnly() {
		List<SelectItem> l = new ArrayList<>();
		for (EmploymentStatusEnum val : EmploymentStatusEnum.values()) {
			if (val != EmploymentStatusEnum.NoLongerAtCompany) {
				l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
			}

		}
		return l;
	}

	public List<SelectItem> getAllocationChangeTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (AllocationChangeTypeEnum val : AllocationChangeTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getWspSearchTypeDD() {
		List<SelectItem> l = new ArrayList<>();
		for (WSPSearchType val : WSPSearchType.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getSdpApplicationTypeDD() {
		List<SelectItem> l = new ArrayList<>();
		for (SDPApplicationType val : SDPApplicationType.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getDesignatedTradeType() {
		List<SelectItem> l = new ArrayList<>();
		for (DesignatedTradeType val : DesignatedTradeType.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getTrainingProviderFilterEnum() {
		List<SelectItem> l = new ArrayList<>();
		for (TrainingProviderFilterEnum val : TrainingProviderFilterEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getWspReopenLocationEnum() {
		List<SelectItem> l = new ArrayList<>();
		for (WspReopenLocationEnum val : WspReopenLocationEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getAccreditationApplicationTypeRegEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL, AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL.getFriendlyName()));
		l.add(new SelectItem(AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL, AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL.getFriendlyName()));
		l.add(new SelectItem(AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider, AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider.getFriendlyName()));
		l.add(new SelectItem(AccreditationApplicationTypeEnum.QCTOTradeTestCentre, AccreditationApplicationTypeEnum.QCTOTradeTestCentre.getFriendlyName()));
		l.add(new SelectItem(AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider, AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider.getFriendlyName()));
		return l;
	}

	public List<SelectItem> getTrancheEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (TrancheEnum val : TrancheEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getCompanyLearnersTransferRecommendationDD() {
		List<SelectItem> l = new ArrayList<>();
		for (CompanyLearnersTransferRecommendation val : CompanyLearnersTransferRecommendation.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getReportGenerationEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ReportGenerationEnum selectItem : ReportGenerationEnum.values()) {
			l.add(new SelectItem(selectItem, selectItem.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getBulkApprovalEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (BulkApprovalEnum selectItem : BulkApprovalEnum.values()) {
			l.add(new SelectItem(selectItem, selectItem.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getMailingListTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (MailingListTypeEnum val : MailingListTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getAprlProgressReportingEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (AprlProgressReportingEnum val : AprlProgressReportingEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<AprlProgressReportingEnum> getAprlProgressReportingEnumSelection() {
		List<AprlProgressReportingEnum> l = new ArrayList<AprlProgressReportingEnum>();
		for (AprlProgressReportingEnum val : AprlProgressReportingEnum.values()) {
			l.add(val);
		}
		return l;
	}

	public List<SelectItem> getUnitStandardTypeEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (UnitStandardTypeEnum val : UnitStandardTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getGenerateAddEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (GenerateAddEnum val : GenerateAddEnum.values()) {
			l.add(new SelectItem(val, super.getEntryLanguage(val.getRegistrationName())));
		}
		return l;
	}

	public List<SelectItem> getPercentageEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (PercentageEnum val : PercentageEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getModerationEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ModerationEnum val : ModerationEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getQualificationTypeSelectionEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (QualificationTypeSelectionEnum val : QualificationTypeSelectionEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getQualificationTypeSelectionLegacyAppEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (QualificationTypeSelectionEnum val : QualificationTypeSelectionEnum.values()) {
			if (val != QualificationTypeSelectionEnum.NonCreditBearingIntervationTitle && val != QualificationTypeSelectionEnum.Learnership) {
				l.add(new SelectItem(val, val.getFriendlyName()));
			}
		}
		return l;
	}

	public List<SelectItem> getActionPlanValidiationTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ActionPlanValidiationTypeEnum val : ActionPlanValidiationTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getOpenClosedEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (OpenClosedEnum val : OpenClosedEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getQmrTypeSelectionEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (QmrTypeSelectionEnum val : QmrTypeSelectionEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getSetaIndustryFundedEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (SetaIndustryFundedEnum val : SetaIndustryFundedEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getPublicPrivateEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (PublicPrivateEnum val : PublicPrivateEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getFinYearQuartersEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (FinYearQuartersEnum val : FinYearQuartersEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getQmrEquityEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (QmrEquityEnum val : QmrEquityEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getQmrGenderEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (QmrGenderEnum val : QmrGenderEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getReportPropertiesEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ReportPropertiesEnum val : ReportPropertiesEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getAssessorModeratorAppTypeEnumTccDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(AssessorModeratorAppTypeEnum.NewAssessorRegistration, AssessorModeratorAppTypeEnum.NewAssessorRegistration.getDisplayName()));
		l.add(new SelectItem(AssessorModeratorAppTypeEnum.NewModeratorRegistration, AssessorModeratorAppTypeEnum.NewModeratorRegistration.getDisplayName()));
		return l;
	}

	public List<SelectItem> getTrancheRuleEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (TrancheRuleEnum val : TrancheRuleEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getNsdpReportRunTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (NsdpReportRunTypeEnum val : NsdpReportRunTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getProviderSuspensionDeAcredListEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ApprovalEnum val : ApprovalEnum.getStatusListForProviderSuspensionPage()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getProviderSusActionsEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		for (ProviderSusActionsEnum val : ProviderSusActionsEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	public List<SelectItem> getDGWindowTypeEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (DGWindowTypeEnum val : DGWindowTypeEnum.values()) {
			if (getSessionUI().isAdmin() && val == DGWindowTypeEnum.Standard) {
				l.add(new SelectItem(val, val.getRegistrationName()));
			} else if (val != DGWindowTypeEnum.Standard){
				l.add(new SelectItem(val, val.getRegistrationName()));
			}
		}
		return l;
	}

	public List<SelectItem> getHoldingRoomStatusEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (HoldingRoomStatusEnum val : HoldingRoomStatusEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

}