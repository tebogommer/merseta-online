package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ConfigDocProcessEnum.
 */
public enum ConfigDocProcessEnum {

	/** The sdf. 0 */
	SDF("sdf.config.doc") {
		@Override
		public String getType() {
			return "skills.development.facilitator";
		}

		@Override
		public String getRegistrationName() {
			return "sdf.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/sdf/sdfDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdf.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},

	/** The Learner. 1 */
	Learner("learner.config.doc") {
		@Override
		public String getType() {
			return "learner";
		}

		@Override
		public String getRegistrationName() {
			return "learner.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerRegistrationApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The company. 2 */
	COMPANY("comnpany.config.doc") {
		@Override
		public String getType() {
			return "company";
		}

		@Override
		public String getRegistrationName() {
			return "company.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "companyregistration.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "comnpany.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},

	/** The tp. 3 */
	TP("tp.config.doc") {
		@Override
		public String getType() {
			return "training.provider";
		}

		@Override
		public String getRegistrationName() {
			return "tp.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingProviderDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "tp.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},

	/** The am. 4 */
	AM("am.config.doc") {
		@Override
		public String getType() {
			return "assessor.and.moderator";
		}

		@Override
		public String getRegistrationName() {
			return "am.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/assessorModaratorDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "am.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The wsp. 5 */
	WSP("wsp.config.doc") {
		@Override
		public String getType() {
			return "wsp.atr";
		}

		@Override
		public String getRegistrationName() {
			return "wsp.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/externalparty/wsp/reviewapplication.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "wsp.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #DEVIATION_STATUS#";
		}
	},
	/**
	 * 6
	 */
	INTER_SETA_TRANSFER("inter.seta.transfer") {
		@Override
		public String getType() {
			return "inter.seta.transfer";
		}

		@Override
		public String getRegistrationName() {
			return "inter.seta.transfer.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/intersetatransfer/transferrequestsdetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "inter.seta.transfer.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 7
	 */
	DG_VERIFICATION("dg.verification.full") {
		@Override
		public String getType() {
			return "dg.verification.full";
		}

		@Override
		public String getRegistrationName() {
			return "dg.verification.full";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgverification/dgverification.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "inter.seta.transfer.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/**
	 * 8
	 */
	BANKING_DETAIL_MANAGEMENT("banking.detail.management") {
		@Override
		public String getType() {
			return "banking.detail.management";
		}

		@Override
		public String getRegistrationName() {
			return "banking.detail.management";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/bankingdetails/bankdetails.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "banking.detail.management";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 9
	 */
	EXTENSION_REQUEST("extension.request") {
		@Override
		public String getType() {
			return "extension.request";
		}

		@Override
		public String getRegistrationName() {
			return "extension.request";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/extensionrequest/extensionprocessrequest.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "extension.request";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 10
	 */
	COMPANY_CHANGE_APPROVAL("company.change.approval") {
		@Override
		public String getType() {
			return "company.change.approval";
		}

		@Override
		public String getRegistrationName() {
			return "company.change.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "company.change.approval";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 11
	 */
	MG_VERIFICATION("mg.verification") {
		@Override
		public String getType() {
			return "mg.verification";
		}

		@Override
		public String getRegistrationName() {
			return "mg.verification";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/mgverification/mgverification.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "mg.verification";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #CLO_FIRST_NAME# #CLO_LAST_NAME# #CLO_EMAIL#";
		}
	},
	/**
	 * 12
	 */
	RESEND_VERIFICATION_EMAIL_NOT_MATCH("resend.verification.email_not_match") {
		@Override
		public String getType() {
			return "resend.verification.email_not_match";
		}

		@Override
		public String getRegistrationName() {
			return "resend.verification.email_not_match";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/updateprofileEmail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "resend.verification.email_not_match.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 13
	 */
	CONTACT_PERSONS_CHANGE("company.contact.change") {
		@Override
		public String getType() {
			return "company.contact.change";
		}

		@Override
		public String getRegistrationName() {
			return "company.contact.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "company.contact.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 14
	 */
	NEW_CONTACT_PERSON("new.company.contact.person") {
		@Override
		public String getType() {
			return "new.company.contact.person";
		}

		@Override
		public String getRegistrationName() {
			return "new.company.contact.person";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.company.contact.person";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 15
	 */
	SDF_CHANGE("sdf.change") {
		@Override
		public String getType() {
			return "sdf.change";
		}

		@Override
		public String getRegistrationName() {
			return "sdf.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdf.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 16
	 */
	DOC_CHANGE("doc.change") {
		@Override
		public String getType() {
			return "doc.change";
		}

		@Override
		public String getRegistrationName() {
			return "doc.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "doc.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 17
	 */
	LINKED_COMPANY_CHANGE("linked.company.change") {
		@Override
		public String getType() {
			return "linked.company.change";
		}

		@Override
		public String getRegistrationName() {
			return "linked.company.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "linked.company.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 18
	 */
	NEW_LINKED_COMPANY("new.linked.company") {
		@Override
		public String getType() {
			return "new.linked.company";
		}

		@Override
		public String getRegistrationName() {
			return "new.linked.company";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.linked.company";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 19
	 */
	REMOVE_LINKED_COMPANY("remove.linked.company.change") {
		@Override
		public String getType() {
			return "remove.linked.company.change";
		}

		@Override
		public String getRegistrationName() {
			return "remove.linked.company.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "remove.linked.company.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 20
	 */
	TRAINING_COMMITTEE_CHANGE("training.committee.change") {
		@Override
		public String getType() {
			return "training.committee.change";
		}

		@Override
		public String getRegistrationName() {
			return "training.committee.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "training.committee.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 21
	 */
	NEW_TRAINING_COMMITTEE("new.training.committee") {
		@Override
		public String getType() {
			return "new.training.committee";
		}

		@Override
		public String getRegistrationName() {
			return "new.training.committee";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.training.committee";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 22
	 */
	SITES_CHANGE("sites.change") {
		@Override
		public String getType() {
			return "sites.change";
		}

		@Override
		public String getRegistrationName() {
			return "sites.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sites.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 23
	 */
	NEW_SITES("new.site") {
		@Override
		public String getType() {
			return "new.site";
		}

		@Override
		public String getRegistrationName() {
			return "new.site";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.site";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 24
	 */
	SARS("SARS.levy.reports") {
		@Override
		public String getType() {
			return "SARS.levy.reports";
		}

		@Override
		public String getRegistrationName() {
			return "SARS.levy.reports";
		}

		@Override
		public String getRedirectPage() {
			return "";
		}

		@Override
		public String getTaskDescription() {
			return "SARS.levy.reports";
		}

		@Override
		public String getTaskTags() {
			return "";
		}
	},

	/** T25 */
	NEW_SDF("new.sdf") {
		@Override
		public String getType() {
			return "new.sdf";
		}

		@Override
		public String getRegistrationName() {
			return "new.sdf";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.sdf";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},
	/** 26 */
	NEW_COMPANY_USER("new.company.user") {
		@Override
		public String getType() {
			return "new.company.user";
		}

		@Override
		public String getRegistrationName() {
			return "new.company.user";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.company.user";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},
	/** 27 */
	NEW_EMPLOYEE("new.employee") {
		@Override
		public String getType() {
			return "new.employee";
		}

		@Override
		public String getRegistrationName() {
			return "new.employee";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.employee";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},

	/** 28 */
	EMPLOYEE_CHANGE("employee.training.change") {
		@Override
		public String getType() {
			return "employee.change";
		}

		@Override
		public String getRegistrationName() {
			return "employee.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "employee.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},
	/** 29 */
	DELETE_EMPLOYEE("delete.employee") {
		@Override
		public String getType() {
			return "delete.employee";
		}

		@Override
		public String getRegistrationName() {
			return "delete.employee";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "delete.employee";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},
	/** 30 */
	DELETE_CONTACT_PERSON("delete.contact.person") {
		@Override
		public String getType() {
			return "delete.contact.person";
		}

		@Override
		public String getRegistrationName() {
			return "delete.contact.person";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "delete.contact.person";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},
	/** 31 */
	DELETE_SDF("delete.sdf") {
		@Override
		public String getType() {
			return "delete.sdf";
		}

		@Override
		public String getRegistrationName() {
			return "delete.sdf";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "delete.sdf";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}

	},
	/** 32 */
	QUALIFICATION_REGISTRATION("qualification.reg") {
		@Override
		public String getType() {
			return "qualification.reg";
		}

		@Override
		public String getRegistrationName() {
			return "qualification.reg";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "qualification.reg";
		}

		@Override
		public String getTaskTags() {
			return "";
		}
	},
	/** 33 */
	SP_DEVELOPMENT("sp.development") {
		@Override
		public String getType() {
			return "sp.development";
		}

		@Override
		public String getRegistrationName() {
			return "sp.development";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/skillsregistrationapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sp.development";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/** 34 */
	LEARNERSHIP_DEVELOPMENT("learnership.development") {
		@Override
		public String getType() {
			return "learnership.development";
		}

		@Override
		public String getRegistrationName() {
			return "learnership.development";
		}

		@Override
		public String getRedirectPage() {
			return "";
		}

		@Override
		public String getTaskDescription() {
			return "learnership.development";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/** 35 */
	SKILLS_SET_DEVELOPMENT("sk.development") {
		@Override
		public String getType() {
			return "sk.development";
		}

		@Override
		public String getRegistrationName() {
			return "sk.development";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/skillsregistrationapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sk.development";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/**
	 * 36
	 */
	DELETE_TRAINING_COMMITTEE("delete.training.committee") {
		@Override
		public String getType() {
			return "delete.training.committee";
		}

		@Override
		public String getRegistrationName() {
			return "delete.training.committee";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "delete.training.committee";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 37
	 */
	DELETE_SISTE("delete.sites") {
		@Override
		public String getType() {
			return "delete.sites";
		}

		@Override
		public String getRegistrationName() {
			return "delete.sites";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/companychanges/companychanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "delete.sites";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 38
	 */
	QUALIFICATIONS_CURRICULUM_DEVELOPMENT("qualifications.curriculum.development") {
		@Override
		public String getType() {
			return "qualifications.curriculum.development";
		}

		@Override
		public String getRegistrationName() {
			return "qualifications.curriculum.development";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/qualificationscurriculumdevelopmentregistrationapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "qualifications.curriculum.development";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 39
	 */
	SITE_VISIT("site.visit") {
		@Override
		public String getType() {
			return "site.visit";
		}

		@Override
		public String getRegistrationName() {
			return "site.visit";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/sitevisit.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "site.visit";
		}

		@Override
		public String getTaskTags() {
			return "";
		}
	},
	/**
	 * 40 Workflow 16A Learnership Development and Registration ordinal value: 40
	 */
	LEARNERSHIP_DEVELOPMENT_REGISTRATION("learnership.development.registration") {
		@Override
		public String getType() {
			return "learnership.development.registration";
		}

		@Override
		public String getRegistrationName() {
			return "learnership.development.registration";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnershipregistrationapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learnership.development.registration";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 41 COURSEWARE_DISTRIBUTION ordinal value: 14
	 */
	COURSEWARE_DISTRIBUTION("courseware.distribution") {
		@Override
		public String getType() {
			return "courseware.distribution";
		}

		@Override
		public String getRegistrationName() {
			return "courseware.distribution";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/coursewareDistibutionApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "courseware.distribution";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 42
	 */
	WORKPLACE_APPROVAL("workplace.approval") {
		@Override
		public String getType() {
			return "workplace.approval";
		}

		@Override
		public String getRegistrationName() {
			return "workplace.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/workplaceapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "workplace.approval";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/**
	 * 43
	 */
	QDF_Registration("qualification.development.qdf") {
		@Override
		public String getType() {
			return "qualification.development.qdf";
		}

		@Override
		public String getRegistrationName() {
			return "qualification.development.qdf";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/qdf/qdfRegistration.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "qdf.registration";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},

	/** The LearnerTransfer. 44 */
	LearnerTransfer("learner.transfer") {
		@Override
		public String getType() {
			return "learner.transfer";
		}

		@Override
		public String getRegistrationName() {
			return "learner.transfer";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertransfer.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.transfer";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_FIRST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #INITIATOR_IDENTITY_NUMBER# #INITIATOR_FIRST_NAME# #INITIATOR_LAST_NAME# #INITIATOR_EMAIL# #TRANSFER_REQUESTED_BY# #TRANSFER_TYPE# #ORGIGINAL_COMPANY_NAME# #PROPOSSED_COMPANY_NAME#";
		}
	},

	/** The LearnerLostTime. 45 */
	LearnerLostTime("learner.lost.time") {
		@Override
		public String getType() {
			return "learner.lost.time";
		}

		@Override
		public String getRegistrationName() {
			return "learner.lost.time";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/companylearnerslosttime.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.lost.time";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The MutualLearnerTermination. 46 */
	MutualLearnerTermination("mutual.learner.termination") {
		@Override
		public String getType() {
			return "mutual.learner.termination";
		}

		@Override
		public String getRegistrationName() {
			return "mutual.learner.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "mutual.learner.termination";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The OneLearnerTermination. 47 */
	OneLearnerTermination("one.learner.termination") {
		@Override
		public String getType() {
			return "one.learner.termination";
		}

		@Override
		public String getRegistrationName() {
			return "one.learner.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "one.learner.termination";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The LearnerChange. 48 */
	LearnerChange("learner.change") {
		@Override
		public String getType() {
			return "learner.change";
		}

		@Override
		public String getRegistrationName() {
			return "learner.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerchange.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.change";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The LearnerCompletion. 49 */
	LearnerCompletion("learner.completion") {
		@Override
		public String getType() {
			return "learner.completion";
		}

		@Override
		public String getRegistrationName() {
			return "learner.completion";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerchange.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.completion";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The ProviderMonitoring. 50 */
	ProviderMonitoring("provider.monitoring") {
		@Override
		public String getType() {
			return "provider.monitoring";
		}

		@Override
		public String getRegistrationName() {
			return "provider.monitoring";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/providermnitoringapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "provider.monitoring";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #PROVIDER_ACCREDITATION_NUMBER# #PROVIDER_ACCREDITATION_START_DATE# #PROVIDER_ACCREDITATION_END_DATE#";
		}
	},
	/** The TradeTestApplication. 51 */
	TradeTestApplication("trade.test.application") {
		@Override
		public String getType() {
			return "trade.test.application";
		}

		@Override
		public String getRegistrationName() {
			return "trade.test.application";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/companylearnerstradetestworkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "trade.test.application";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_FIRST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #INITIATOR_IDENTITY_NUMBER# #INITIATOR_FIRST_NAME# #INITIATOR_LAST_NAME# #INITIATOR_EMAIL#";
		}
	},
	/** The ProviderVerification. 52 */
	ProviderVerification("provider.verification") {
		@Override
		public String getType() {
			return "provider.verification";
		}

		@Override
		public String getRegistrationName() {
			return "provider.verification";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingproviderverfication.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "provider.verification";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},

	/** The ARPLTradeTestApplication 53 */
	ARPLTradeTestApplication("arpl.trade.test.application") {
		@Override
		public String getType() {
			return "arpl.trade.test.application";
		}

		@Override
		public String getRegistrationName() {
			return "arpl.trade.test.application";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/arpl/arpltradetest.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "arpl.trade.test.application";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_FIRST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #INITIATOR_IDENTITY_NUMBER# #INITIATOR_FIRST_NAME# #INITIATOR_LAST_NAME# #INITIATOR_EMAIL# #DATE_SUBMISSION_ORIGINAL_DOC#";
		}
	},

	/**
	 * 54
	 */
	SITE_VISIT_REPORT("site.visit.report") {
		@Override
		public String getType() {
			return "site.visit.report";
		}

		@Override
		public String getRegistrationName() {
			return "site.visit.report";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/sitevisitreportview.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "site.visit.report";
		}

		@Override
		public String getTaskTags() {
			return "";
		}
	},

	/**
	 * 55
	 */
	RegisterNonSetaQualification("register.non.seta.qualification") {
		@Override
		public String getType() {
			return "company";
		}

		@Override
		public String getRegistrationName() {
			return "register.non.seta.qualification";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/nonsetaqualificationscompletion.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "register.non.seta.qualification";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The WorkplaceMonitoring. 56 */
	WorkplaceMonitoring("workplace.monitoring") {
		@Override
		public String getType() {
			return "workplace.monitoring";
		}

		@Override
		public String getRegistrationName() {
			return "workplace.monitoring";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/workplacemonitoring.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "workplace.monitoring";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The WSPDocumentUpload. 57 */
	WSPDocumentUpload("wsp.config.doc.reupload") {
		@Override
		public String getType() {
			return "wsp.atr.document.upload";
		}

		@Override
		public String getRegistrationName() {
			return "wsp.process.detail.document.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/externalparty/wsp/wspDocumentReUpload.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "wsp.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The AdministrationOfAQP. 58 */
	AdministrationOfAQP("administration.of.aqp") {
		@Override
		public String getType() {
			return "administration.of.aqp";
		}

		@Override
		public String getRegistrationName() {
			return "administration.of.aqp";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/administrationofaqpapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "administration.of.aqp";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The ApplicationAdministrationOfAQP. 59 */
	ApplicationAdministrationOfAQP("application.administration.of.aqp") {
		@Override
		public String getType() {
			return "application.administration.of.aqp";
		}

		@Override
		public String getRegistrationName() {
			return "application.administration.of.aqp";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/administrationofaqpapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "application.administration.of.aqp";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The LearnerRegistrationOfAQP. 60 */
	LearnerRegistrationOfAQP("learnerregistrationofaqp") {
		@Override
		public String getType() {
			return "learnerregistrationofaqp";
		}

		@Override
		public String getRegistrationName() {
			return "learnerregistrationofaqp";
		}

		@Override
		public String getRedirectPage() {
			return "";
		}

		@Override
		public String getTaskDescription() {
			return "learnerregistrationofaqp";
		}

		@Override
		public String getTaskTags() {
			return "";
		}
	},
	/** The DG_ALLOCATION. 61 */
	DG_ALLOCATION("dg.allocation") {
		@Override
		public String getType() {
			return "dg.allocation";
		}

		@Override
		public String getRegistrationName() {
			return "dg.allocation";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/dgallocationApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.allocation";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/** The DG_CONTRACT. 62 */
	DG_CONTRACT("dg.contract") {
		@Override
		public String getType() {
			return "dg.contract";
		}

		@Override
		public String getRegistrationName() {
			return "dg.contract";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/dgcontracts.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.contract";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/** The CONTRACT_DETAIL. 63 */
	CONTRACT_DETAIL("contract.detail") {
		@Override
		public String getType() {
			return "contract.detail";
		}

		@Override
		public String getRegistrationName() {
			return "contract.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/activecontracts.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "contract.detail";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/** The AM_ETQA_APPROVAL. 64 */
	AM_ETQA_APPROVAL("am.etqa.config.doc") {
		@Override
		public String getType() {
			return "assessor.and.moderator.etqa.approval";
		}

		@Override
		public String getRegistrationName() {
			return "am.process.detail.etqa.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/assessorModaratorDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "am.etqa.approval.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL. 65 */
	NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL("non.primary.focus.accreditation.approval") {
		@Override
		public String getType() {
			return "non.primary.focus.accreditation.approval";
		}

		@Override
		public String getRegistrationName() {
			return "non.primary.focus.accreditation.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingProviderDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "non.primary.focus.accreditation.approval";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID#";
		}
	},
	/** The SITE_SME_REGISTRATION. 66 */
	SITE_SME_REGISTRATION("site.sme.registration") {
		@Override
		public String getType() {
			return "site.sme.registration";
		}

		@Override
		public String getRegistrationName() {
			return "site.sme.registration";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/sitesmeapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "site.sme.registration";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #SITE_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The TP_ETQA_APPROVAL. 67 */
	TP_ETQA_APPROVAL("tp.etqa.approval") {
		@Override
		public String getType() {
			return "tp.etqa.approval";
		}

		@Override
		public String getRegistrationName() {
			return "tp.etqa.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingProviderDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "tp.etqa.approval.task.desc";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The DG_ALLOCATION_APPEAL. 68 */
	DG_ALLOCATION_APPEAL("dg.allocation.appeal") {
		@Override
		public String getType() {
			return "dg.allocation.appeal";
		}

		@Override
		public String getRegistrationName() {
			return "dg.allocation.appeal";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/dgallocationAppeal.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.allocation.appeal";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The LearnerReview. 69 */
	LearnerReview("learner.config.doc") {
		@Override
		public String getType() {
			return "learner";
		}

		@Override
		public String getRegistrationName() {
			return "learner.process.review";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerrevireapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #ENTITY_ID#";
		}
	},
	/** The SDPExtensionOfScope. 70 */
	SDPExtensionOfScope("sdp.extension.of.scope") {
		@Override
		public String getType() {
			return "SDPExtensionOfScope";
		}

		@Override
		public String getRegistrationName() {
			return "sdp.extension.of.scope";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/SDPExtensionOfScopeDetails.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.extension.of.scope";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/** The USER_DETAILS_CHANGE. 71 */
	USER_DETAILS_CHANGE("user.details.change") {
		@Override
		public String getType() {
			return "user.details.change";
		}

		@Override
		public String getRegistrationName() {
			return "user.details.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/userDetailsChange.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "user.details.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/** The NON_SETA_PROVIDERS. 72 */
	NON_SETA_PROVIDERS("non.seta.tp.config.doc") {
		@Override
		public String getType() {
			return "non.seta.training.provider";
		}

		@Override
		public String getRegistrationName() {
			return "non.seta.tp.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/nonSetatrainingProviderDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "non.seta.tp.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The AM_EXTENSION_OF_SCOPE. 73 */
	AM_EXTENSION_OF_SCOPE("am.ex.of.scope.config.doc") {
		@Override
		public String getType() {
			return "am.ex.of.scope";
		}

		@Override
		public String getRegistrationName() {
			return "am.ex.of.scope.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/assessorModaratorExtensionOfScopeDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "am.ex.of.scope.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The AM_EXTENSION_OF_SCOPE_ETQA_APPROVAL. 74 */
	AM_EXTENSION_OF_SCOPE_ETQA_APPROVAL("am.config.doc") {
		@Override
		public String getType() {
			return "am.ext.of.scope.etqa.approval";
		}

		@Override
		public String getRegistrationName() {
			return "etqa.am.ex.of.scope.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/assessorModaratorExtensionOfScopeDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "am.ex.of.scope.task.description";
		}
	},
	/** The SITE_SME_UPDATE. 75 */
	SITE_SME_UPDATE("site.sme.update") {
		@Override
		public String getType() {
			return "site.sme.update";
		}

		@Override
		public String getRegistrationName() {
			return "site.sme.update";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/sitesmeupdateapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "site.sme.update";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #SITE_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The SITE_SME_DELETE. 76 */
	SITE_SME_DELETE("site.sme.delete") {
		@Override
		public String getType() {
			return "site.sme.delete";
		}

		@Override
		public String getRegistrationName() {
			return "site.sme.delete";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/sitesmedeleteapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "site.sme.delete";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #SITE_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The DG Project Termination. 77 */
	DG_PROJECT_TERMINATION("dg.project.termination") {
		@Override
		public String getType() {
			return "dg.project.termination";
		}

		@Override
		public String getRegistrationName() {
			return "dg.project.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgprojecttermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.project.termination";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #SITE_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The PIP Update. 78 */
	PIP_UPDATE("pip.update") {
		@Override
		public String getType() {
			return "pip.update";
		}

		@Override
		public String getRegistrationName() {
			return "pip.update";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/pipupdate.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "pip.update";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #SITE_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** MG_DG_TRANSACTIONS 79 */
	MG_DG_TRANSACTIONS("mg.transactions") {
		@Override
		public String getType() {
			return "mg.transactions";
		}

		@Override
		public String getRegistrationName() {
			return "mg.transactions";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/mgdgtransactions/mgtransactionsWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "mg.transactions";
		}

		@Override
		public String getTaskTags() {
			return "#BATCH_NUMBER# #BATCH_DESCRIPTION# #BATCH_TYPE# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** DG_TRANSACTIONS 80 */
	DG_TRANSACTIONS("dg.transactions") {
		@Override
		public String getType() {
			return "dg.transactions";
		}

		@Override
		public String getRegistrationName() {
			return "dg.transactions";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/mgdgtransactions/mgdgtransactions.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.transactions";
		}

		@Override
		public String getTaskTags() {
			return "#BATCH_NUMBER# #BATCH_DESCRIPTION# #BATCH_TYPE# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The DG_CONTRACT. 81 */
	SPECIAL_PROJECTS("special.projects") {
		@Override
		public String getType() {
			return "special.projects";
		}

		@Override
		public String getRegistrationName() {
			return "special.projects";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/specialprojects.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "special.projects";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/** The ProviderVerificationModeration. 82 */
	ProviderVerificationModeration("provider.verification.moderation") {
		@Override
		public String getType() {
			return "provider.verification.moderation";
		}

		@Override
		public String getRegistrationName() {
			return "provider.verification.moderation";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingprovidermoderation.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "provider.verification.moderation";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The SDPReAccreditation. 83 */
	SDPReAccreditation("sdp.reaccreditation") {
		@Override
		public String getType() {
			return "SDPReAccreditation";
		}

		@Override
		public String getRegistrationName() {
			return "sdp.reaccreditation";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/SDPReAccreditationDetails.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.reaccreditation.task.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/** The TP_ETQA_APPROVAL. 84 */
	SDP_RE_ACC_ETQA_APPROVAL("tp.re.acc.etqa.approval") {
		@Override
		public String getType() {
			return "tp.re.acc.etqa.approval";
		}

		@Override
		public String getRegistrationName() {
			return "tp.re.acc.etqa.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/SDPReAccreditationDetails.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.reaccreditation.task.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The LearnerTransferSiteVisit. 85 */
	LearnerTransferSiteVisit("learner.transfer.site.visit") {
		@Override
		public String getType() {
			return "learner.transfer.site.visit";
		}

		@Override
		public String getRegistrationName() {
			return "learner.transfer.site.visit";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertransfer.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.transfer.site.visit";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The SystemLearnerTermination. 86 */
	SystemLearnerTermination("system.learner.termination") {
		@Override
		public String getType() {
			return "system.learner.termination";
		}

		@Override
		public String getRegistrationName() {
			return "system.learner.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/systemlearnertermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "system.learner.termination";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The DG_CONTRACT_EC. 87 */
	DG_CONTRACT_EC("dg.contract.ec") {
		@Override
		public String getType() {
			return "dg.contract.ec";
		}

		@Override
		public String getRegistrationName() {
			return "dg.contract.ec";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/dgcontractsec.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.contract.ec";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/** The DG_CONTRACT_EC. 88 */
	DG_CONTRACT_SIGNOFF("dg.contract.signoff") {
		@Override
		public String getType() {
			return "dg.contract.signoff";
		}

		@Override
		public String getRegistrationName() {
			return "dg.contract.signoff";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/dgContractBulkApprovalWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.contract.signoff";
		}

		@Override
		public String getTaskTags() {
			return "#CREATE_USER_FIRST_NAME# #CREATE_USER_LAST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #BATCH_NUMBER#";
		}
	},
	/** The Audit Monitoring. 89 */
	AuditMonitoring("provider.audit") {
		@Override
		public String getType() {
			return "provider.audit";
		}

		@Override
		public String getRegistrationName() {
			return "provider.audit";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/providermnitoringapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "provider.audit";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #PROVIDER_ACCREDITATION_NUMBER# #PROVIDER_ACCREDITATION_START_DATE# #PROVIDER_ACCREDITATION_END_DATE#";
		}
	},

	/** The Learner. 90 */
	CompanyLearner("learner.process.detail.approval") {
		@Override
		public String getType() {
			return "learner.process.detail.approval";
		}

		@Override
		public String getRegistrationName() {
			return "learner.process.detail.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerRegistrationApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.process.detail.approval";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The SDPReAccreditation. 91 */
	SDP_RE_SUBMISSION("sdp.resubmission") {
		@Override
		public String getType() {
			return "sdp.resubmission";
		}

		@Override
		public String getRegistrationName() {
			return "sdp.resubmission";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/SDPResubmissionDetails.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.resubmission.task.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/** The am. 92 */
	AM_RE_REGISTRATION("am.re-registration.config.doc") {
		@Override
		public String getType() {
			return "assessor.and.moderator.re-registration";
		}

		@Override
		public String getRegistrationName() {
			return "am.re-registration.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/assessorModaratorDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "am.re-registration.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The AM_RE_REGISTRATION_ETQA_APPROVAL. 93 */
	AM_RE_REGISTRATION_ETQA_APPROVAL("am.re-registration.etqa.config.doc") {
		@Override
		public String getType() {
			return "assessor.and.moderator.re-registration.etqa.approval";
		}

		@Override
		public String getRegistrationName() {
			return "am.re-registration.process.detail.etqa.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/assessorModaratorDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "am.re-registration.etqa.approval.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},

	/** The ARPLTradeTestApplication 94 */
	ARPLTradeTestNambApproval("arpl.trade.test.namb.approval") {
		@Override
		public String getType() {
			return "arpl.trade.test.namb.approval";
		}

		@Override
		public String getRegistrationName() {
			return "arpl.trade.test.namb.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/arpl/arpltradetestNambApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "arpl.trade.test.namb.approval";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	}

	,
	/** The SDPExtensionOfScope. 95 */
	SDPExtensionOfScopeETQAApproval("sdp.extension.of.scope.etqa.approval") {
		@Override
		public String getType() {
			return "sdp.extension.of.scope.etqa.approval";
		}

		@Override
		public String getRegistrationName() {
			return "sdp.extension.of.scope.etqa.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/SDPExtensionOfScopeDetails.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.extension.of.scope.etqa.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/** The ARPLTradeTestApplication 96 */
	ARPLTradeTestNambRejection("arpl.trade.test.namb.rejection") {
		@Override
		public String getType() {
			return "arpl.trade.test.namb.rejection";
		}

		@Override
		public String getRegistrationName() {
			return "arpl.trade.test.namb.rejection";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/arpl/arpltradetestNambRejectionWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "arpl.trade.test.namb.rejection";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The ARPLTradeTestApplication 97 */
	ARPLTradeTestDocumentUpload("arpl.trade.test.doc.upload") {
		@Override
		public String getType() {
			return "arpl.trade.test.doc.upload";
		}

		@Override
		public String getRegistrationName() {
			return "arpl.trade.test.doc.upload";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/arpl/arpltradetestDocUpload.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "arpl.trade.test.doc.upload";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The ARPLTradeTestApplication 98 */
	LearnerFileManagement("learner.file.management") {
		@Override
		public String getType() {
			return "learner";
		}

		@Override
		public String getRegistrationName() {
			return "learner.file.management";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerRegistrationApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/**
	 * 99
	 */
	WITHDRAW_WORKPLACE_APPROVAL("withdraw.workplace.approval") {
		@Override
		public String getType() {
			return "withdraw.workplace.approval";
		}

		@Override
		public String getRegistrationName() {
			return "withdraw.workplace.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/workplaceapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "withdraw.workplace.approval";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The SDPReAccreditation. 100 */
	NON_MERSETA_SCOPE_ADDITIONAL_QUALIFICATION("non.merseta.scope.additional.qualification") {
		@Override
		public String getType() {
			return "non.merseta.scope.additional.qualification";
		}

		@Override
		public String getRegistrationName() {
			return "non.merseta.scope.additional.qualification";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/nonMerSetaScopeAdditionalQualDetails.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "non.merseta.scope.additional.qualification.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},

	/**
	 * 101
	 */
	CompletionLetter("completion.letter") {
		@Override
		public String getType() {
			return "company";
		}

		@Override
		public String getRegistrationName() {
			return "completion.letter";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/completionletter.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "completion.letter";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/**
	 * 102
	 */
	SDP_COMPANY_CHANGE_APPROVAL("sdp.company.change.approval") {
		@Override
		public String getType() {
			return "sdp.company.change.approval";
		}

		@Override
		public String getRegistrationName() {
			return "sdp.company.change.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tpchanges/tpchanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.company.change.approval";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 103
	 */
	LEGACY_AM_APPLICATION("legacy.am.config.doc") {
		@Override
		public String getType() {
			return "legacy.assessor.and.moderator";
		}

		@Override
		public String getRegistrationName() {
			return "legacy.am.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/assessorModaratorDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "legacy.am.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 104
	 */
	LearnerRegistrationByNonMersetaCompanies("non.merseta.company.learner.config.doc") {
		@Override
		public String getType() {
			return "learner";
		}

		@Override
		public String getRegistrationName() {
			return "non.merseta.company.learner";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerregistrationformnonmersetaapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "completion.letter";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/**
	 * 105
	 */
	SDP_LEGACY_APPLICATION("legacy.tp.config.doc") {
		@Override
		public String getType() {
			return "legacy.training.provider.details";
		}

		@Override
		public String getRegistrationName() {
			return "legacy.tp.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingProviderDetail.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "legacy.tp.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID#";
		}
	},
	/**
	 * 106
	 */
	CompanyLearnerDetailesChange("company.learner.change.detailes.config.doc") {
		@Override
		public String getType() {
			return "learner.change.detailes";
		}

		@Override
		public String getRegistrationName() {
			return "learner.change.detailes";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/companylearnersdetailschangeapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.change.detailes";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/**
	 * 106
	 */
	NEW_SDP_CONTACT_PERSON("new.sdp.company.contact.person") {
		@Override
		public String getType() {
			return "new.sdp.company.contact.person";
		}

		@Override
		public String getRegistrationName() {
			return "new.sdp.company.contact.person";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tpchanges/tpchanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.conact.new.person.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},

	/*
	 * 107
	 */
	REMOVE_SDP_CONTACT_PERSON("remove.sdp.company.contact.person") {
		@Override
		public String getType() {
			return "remove.sdp.company.contact.person";
		}

		@Override
		public String getRegistrationName() {
			return "remove.sdp.company.contact.person";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tpchanges/tpchanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "remove.sdp.conact.person.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 108
	 */
	UPDATE_SDP_CONTACT_PERSON("new.sdp.company.contact.person") {
		@Override
		public String getType() {
			return "update.sdp.company.contact.person";
		}

		@Override
		public String getRegistrationName() {
			return "update.sdp.company.contact.person";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tpchanges/tpchanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "update.sdp.conact.person.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 109
	 */
	NEW_SDP_ASSESSOR_MODERATOR("new.sdp.company.contact.person") {
		@Override
		public String getType() {
			return "new.sdp.company.assessor.mod";
		}

		@Override
		public String getRegistrationName() {
			return "new.sdp.company.assessor.mod";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tpchanges/tpchanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.sdp.company.assessor.mod";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},

	/*
	 * 107
	 */
	REMOVE_SDP_ASSESSOR_MODERATOR("remove.sdp.company.assessor.mod") {
		@Override
		public String getType() {
			return "remove.sdp.company.assessor.mod";
		}

		@Override
		public String getRegistrationName() {
			return "remove.sdp.company.assessor.mod";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tpchanges/tpchanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "remove.sdp.company.assessor.mod";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 108
	 */
	SDP_DOC_CHANGE("sdp.doc.change") {
		@Override
		public String getType() {
			return "sdp.doc.change";
		}

		@Override
		public String getRegistrationName() {
			return "sdp.doc.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tpchanges/tpchanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdp.doc.change.task.desc";
		}

		@Override
		public String getTaskTags() {
			return "#ACCREDITATION_NUMBER# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/**
	 * 108
	 */
	ADDRESS_CHANGE("am.address.change") {
		@Override
		public String getType() {
			return "am.address.change";
		}

		@Override
		public String getRegistrationName() {
			return "am.address.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/am/addresschanges.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "am.address.change";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";

		}
	},
	/**
	 * 109
	 */
	LearnerRegistrationByMerseta("merseta.company.learner.config.doc") {
		@Override
		public String getType() {
			return "learner";
		}

		@Override
		public String getRegistrationName() {
			return "merseta.company.learner";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/employeelearnerregistrationapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "merseta.company.learner";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/**
	 * 110
	 */
	LegacyLearnerRegistration("legacy.company.learner.config.doc") {
		@Override
		public String getType() {
			return "learner";
		}

		@Override
		public String getRegistrationName() {
			return "legacy.company.learner";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/legacylearnerregistrationapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "legacy.company.learner";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The SDF DG Project Termination. 111 */
	SDF_DG_PROJECT_TERMINATION("sdf.dg.project.termination") {
		@Override
		public String getType() {
			return "sdf.dg.project.termination";
		}

		@Override
		public String getRegistrationName() {
			return "sdf.dg.project.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/sdfdgprojecttermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "sdf.dg.project.termination";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #SITE_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/**
	 * 112
	 */
	LEGACY_WORKPLACE_APPROVAL("legacy.workplace.approval") {
		@Override
		public String getType() {
			return "legacy.workplace.approval";
		}

		@Override
		public String getRegistrationName() {
			return "legacy.workplace.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/workplaceapprovallegacy.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "legacy.workplace.approval";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/**
	 * 112
	 */
	DATE_SCHEDULE("date.scheduling") {
		@Override
		public String getType() {
			return "date.scheduling";
		}

		@Override
		public String getRegistrationName() {
			return "date.scheduling";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/datescheduleapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "date.scheduling";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The TradeTestApplicationDocReupload 113 */
	TradeTestDocumentReUpload("trade.test.doc.upload") {
		@Override
		public String getType() {
			return "trade.test.doc.upload";
		}

		@Override
		public String getRegistrationName() {
			return "trade.test.doc.upload";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tradetest/tradetestDocUpload.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "trade.test.doc.upload";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The TradeTestNambApproval 114 */
	TradeTestNambApproval("trade.test.namb.approval") {
		@Override
		public String getType() {
			return "trade.test.namb.approval";
		}

		@Override
		public String getRegistrationName() {
			return "trade.test.namb.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tradetest/tradetestNambApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "trade.test.namb.approval";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The DG_CONTRACT_EXTENSION_REQUEST. 115 */
	DG_CONTRACT_EXTENSION_REQUEST("dg.contract.extension.request") {
		@Override
		public String getType() {
			return "dg.contract.extension.request";
		}

		@Override
		public String getRegistrationName() {
			return "dg.contract.extension.request";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/dgContractExtensionRequest.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.contract.extension.request";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/** The DG_CONTRACT_TERMINATION_REQUEST. 116 */
	DG_CONTRACT_TERMINATION_REQUEST("dg.contract.termination.request") {
		@Override
		public String getType() {
			return "dg.contract.termination.request";
		}

		@Override
		public String getRegistrationName() {
			return "dg.contract.termination.request";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/dgallocation/dgContractTerminationRequest.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.contract.termination.request";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #CRM_DECISION#";
		}
	},
	/** The TRADE_TEST_FAIL_LEARNER_TERMINATION. 117 */
	TRADE_TEST_FAIL_LEARNER_TERMINATION("trade.test.fail.learner.termination") {
		@Override
		public String getType() {
			return "trade.test.fail.learner.termination";
		}

		@Override
		public String getRegistrationName() {
			return "trade.test.fail.learner.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "trade.test.fail.learner.termination";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The LEARNER_ASSESSMENT_VERIFICATION. 118 */
	LEARNER_ASSESSMENT_VERIFICATION("learner.assessment.verification") {
		@Override
		public String getType() {
			return "learner.assessment.verification";
		}

		@Override
		public String getRegistrationName() {
			return "learner.assessment.verification";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/learnersassessmentverification.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.assessment.verification";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The LEARNER_ASSESSMENT_VERIFICATION. 119 */
	ASSESSMENT_VERIFICATION("assessment.verification") {
		@Override
		public String getType() {
			return "assessment.verification";
		}

		@Override
		public String getRegistrationName() {
			return "assessment.verification";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingprovidermoderationverfication.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "assessment.verification";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The ProviderVerificationModeration. 120 */
	ProviderVerificationModerationApproval("provider.verification.moderation.approval") {
		@Override
		public String getType() {
			return "provider.verification.moderation.approval";
		}

		@Override
		public String getRegistrationName() {
			return "provider.verification.moderation.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/trainingprovidermoderation.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "provider.verification.moderation.approval";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL#";
		}
	},
	/** The ProviderVerificationModeration. 121 */
	WorkplaceMonitoringSiteVisit("workplace.monitoring.site.visit") {
		@Override
		public String getType() {
			return "workplace.monitoring.site.visit";
		}

		@Override
		public String getRegistrationName() {
			return "workplace.monitoring.site.visit";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/workplaceMonitoring/workplaceMonitoringSiteVisitWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "workplace.monitoring.site.visit";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #SITE_VISIT_DATE#";
		}
	},

	/** The WorkplaceMonitoringNonComplianceApproval. 122 */
	WorkplaceMonitoringNonComplianceApproval("workplace.monitoring.non.compliance.approval") {
		@Override
		public String getType() {
			return "workplace.monitoring.non.compliance.approval";
		}

		@Override
		public String getRegistrationName() {
			return "workplace.monitoring.non.compliance.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/workplaceMonitoring/workplaceMonitoringSiteVisitNonComplianceWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "workplace.monitoring.non.compliance.approval";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #SITE_VISIT_DATE#";
		}
	},
	LearnerRegistration("company.learner.registration") {
		@Override
		public String getType() {
			return "company.learner.registration";
		}

		@Override
		public String getRegistrationName() {
			return "company.learner.registration";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerRegistrationApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "company.learner.registration";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	UnemployedBursaryLearnerRegistration("unemployed.bursary.learner.registration") {
		@Override
		public String getType() {
			return "unemployed.bursary.learner.registration";
		}

		@Override
		public String getRegistrationName() {
			return "unemployed.bursary.learner.registration";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerRegistrationApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "unemployed.bursary.learner.registration";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The ARPLTradeTestApplication 53 */
	ARPLTradeTestApplicationLegacy("legacy.arpl.trade.test.application") {
		@Override
		public String getType() {
			return "legacy.arpl.trade.test.application";
		}

		@Override
		public String getRegistrationName() {
			return "legacy.arpl.trade.test.application";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/arpl/arpltradetestlegacy.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "legacy.arpl.trade.test.application";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_FIRST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #INITIATOR_IDENTITY_NUMBER# #INITIATOR_FIRST_NAME# #INITIATOR_LAST_NAME# #INITIATOR_EMAIL# #DATE_SUBMISSION_ORIGINAL_DOC#";
		}
	},
	/** The LEARNER_ASSESSMENT_VERIFICATION. 118 */
	LEARNER_ASSESSMENT_VERIFICATION_FINAL_APPROVAL("learner.assessment.verification.final.approval") {
		@Override
		public String getType() {
			return "learner.assessment.verification.final.approval";
		}

		@Override
		public String getRegistrationName() {
			return "learner.assessment.verification.final.approval";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/learnersassessmentverification.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.assessment.verification.final.approval";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	/** The LEARNER_ASSESSMENT_VERIFICATION. 118 */
	LEARNER_ASSESSMENT_CERTIFICATE_UPLOAD("learner.assessment.verification.certificate.upload") {
		@Override
		public String getType() {
			return "learner.assessment.verification.certificate.upload";
		}

		@Override
		public String getRegistrationName() {
			return "learner.assessment.verification.certificate.upload";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/learnersassessmentverification.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.assessment.verification.certificate.upload";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	WORKPLACE_APPROVAL_MENTORS("workplace.approval.mentors") {
		@Override
		public String getType() {
			return "workplace.approval.mentors";
		}

		@Override
		public String getRegistrationName() {
			return "workplace.approval.mentors";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/workplaceapprovalmentors.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "workplace.approval.mentors";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** Legacy The ARPLTradeTestApplication */
	LegacyARPLTradeTestApplication("legacy.arpl.application") {
		@Override
		public String getType() {
			return "legacy.arpl.application";
		}

		@Override
		public String getRegistrationName() {
			return "legacy.arpl.application";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/arpl/arpltradetestlegacyregistration.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "legacy.arpl.application";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_FIRST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #INITIATOR_IDENTITY_NUMBER# #INITIATOR_FIRST_NAME# #INITIATOR_LAST_NAME# #INITIATOR_EMAIL# #DATE_SUBMISSION_ORIGINAL_DOC#";
		}
	},
	LearnerRegistrationDocuments("company.learner.registration.documents") {
		@Override
		public String getType() {
			return "company.learner.registration.documents";
		}

		@Override
		public String getRegistrationName() {
			return "company.learner.registration.documents";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerRegistrationApproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "company.learner.registration.documents";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The Learner. 1 */
	ELearner("e.learner.config.doc") {
		@Override
		public String getType() {
			return "learner";
		}

		@Override
		public String getRegistrationName() {
			return "e.learner.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/learnerregistrationotpapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "learner.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},
	DG_APPLICATION("dg.application.config.doc") {
		@Override
		public String getType() {
			return "dg.application";
		}

		@Override
		public String getRegistrationName() {
			return "dg.application.process.detail";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/externalparty/wsp/reviewapplicationdg.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "dg.application.task.description";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #DEVIATION_STATUS#";
		}
	},
	NEW_SDP_LINK("new.sdp.link") {
		@Override
		public String getType() {
			return "new.sdp.link";
		}

		@Override
		public String getRegistrationName() {
			return "new.sdp.link";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/sdpCompanyActionWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "new.sdp.link";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #TRAINING_SITE_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #REQUEST_DATE# #ACTION_REQUESTED# #SDP_USER_FIRST_NAME# #SDP_USER_LAST_NAME# #SDP_USER_EMAIL# #SDP_USER_IDENTITY_NUMBER#";
		}
	},
	REMOVE_SDP_LINK("remove.sdp.link") {
		@Override
		public String getType() {
			return "remove.sdp.link";
		}

		@Override
		public String getRegistrationName() {
			return "remove.sdp.link";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/sdpCompanyActionWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "remove.sdp.link";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #TRAINING_SITE_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #REQUEST_DATE# #ACTION_REQUESTED# #SDP_USER_FIRST_NAME# #SDP_USER_LAST_NAME# #SDP_USER_EMAIL# #SDP_USER_IDENTITY_NUMBER#";
		}
	},
	UPDATE_SDP_DESIGNATION("update.sdp.designation") {
		@Override
		public String getType() {
			return "update.sdp.designation";
		}

		@Override
		public String getRegistrationName() {
			return "update.sdp.designation";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/sdpCompanyActionWorkflow.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "update.sdp.designation";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #TRAINING_SITE_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #REQUEST_DATE# #ACTION_REQUESTED# #SDP_USER_FIRST_NAME# #SDP_USER_LAST_NAME# #SDP_USER_EMAIL# #SDP_USER_IDENTITY_NUMBER#";
		}
	},
	REQUEST_FUNDING_PAYMENT("request.funding.payment") {
		@Override
		public String getType() {
			return "request.funding.payment";
		}

		@Override
		public String getRegistrationName() {
			return "request.funding.payment";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/requestPayment.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "request.funding.payment";
		}

		@Override
		public String getTaskTags() {
			return "#COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL# #TRAINING_SITE_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #REQUEST_DATE# #ACTION_REQUESTED# #SDP_USER_FIRST_NAME# #SDP_USER_LAST_NAME# #SDP_USER_EMAIL# #SDP_USER_IDENTITY_NUMBER#";
		}
	},

	/** The ELearnerUnemployedBursary. 1 */
	ELearnerUnemployedBursary("e.unemployed.bursary.learner.registration") {
		@Override
		public String getType() {
			return "e.unemployed.bursary.learner.registration";
		}

		@Override
		public String getRegistrationName() {
			return "e.unemployed.bursary.learner.registration";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/learnerregistrationotpapproval.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "e.unemployed.bursary.learner.registration";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The ELearnerTransfer. 44 */
	ELearnerTransfer("e.learner.transfer") {
		@Override
		public String getType() {
			return "e.learner.transfer";
		}

		@Override
		public String getRegistrationName() {
			return "e.learner.transfer";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertransfer.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "e.learner.transfer";
		}

		@Override
		public String getTaskTags() {
			return "#PREVIOUS_FIRST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #INITIATOR_IDENTITY_NUMBER# #INITIATOR_FIRST_NAME# #INITIATOR_LAST_NAME# #INITIATOR_EMAIL# #TRANSFER_REQUESTED_BY# #TRANSFER_TYPE# #ORGIGINAL_COMPANY_NAME# #PROPOSSED_COMPANY_NAME#";
		}
	},

	/** E-Learner Lost Time */
	ELearnerLostTime("e.learner.lost.time") {
		@Override
		public String getType() {
			return "e.learner.lost.time";
		}

		@Override
		public String getRegistrationName() {
			return "e.learner.lost.time";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/companylearnerslosttime.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "e.learner.lost.time";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The LearnerChange. 48 */
	ELearnerChange("e.learner.change") {
		@Override
		public String getType() {
			return "e.learner.change";
		}

		@Override
		public String getRegistrationName() {
			return "e.learner.change";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnerchange.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "e.learner.change";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The MutualLearnerTermination. 46 */
	ELearnerMutualTermination("e.mutual.learner.termination") {
		@Override
		public String getType() {
			return "e.mutual.learner.termination";
		}

		@Override
		public String getRegistrationName() {
			return "e.mutual.learner.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "e.mutual.learner.termination";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	},

	/** The OneLearnerTermination. 47 */
	ELearnerOneSidedTermination("e.one.learner.termination") {
		@Override
		public String getType() {
			return "e.one.learner.termination";
		}

		@Override
		public String getRegistrationName() {
			return "e.one.learner.termination";
		}

		@Override
		public String getRedirectPage() {
			return "/pages/tp/learnertermination.jsf";
		}

		@Override
		public String getTaskDescription() {
			return "e.one.learner.termination";
		}

		@Override
		public String getTaskTags() {
			return "#FIRST_NAME# #LAST_NAME# #IDENTITY_NUMBER# #EMAIL# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER# #LEARNER_EMAIL# #COMPANY_NAME# #TRADING_NAME# #ENTITY_ID# #TEL_NUMBER# #COMPANY_EMAIL#";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new config doc process enum.
	 * @param displayNameX
	 * the display name X
	 */
	private ConfigDocProcessEnum(String displayNameX) {
		displayName = displayNameX;
	}

	/**
	 * Gets the friendly name.
	 * @return the friendly name
	 */
	public String getFriendlyName() {
		return displayName;
	}

	/**
	 * Gets the registration name.
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}

	/**
	 * Gets the redirect page.
	 * @return the redirect page
	 */
	public String getRedirectPage() {
		return displayName;
	}

	/**
	 * Gets the task description.
	 * @return the task description
	 */
	public String getTaskDescription() {
		return displayName;

	}

	public String getTaskTags() {
		return displayName;
	}

	/**
	 * Gets the type.
	 * @return the type
	 */
	public String getType() {
		return displayName;
	}

	public static final ConfigDocProcessEnum getConfigDocProcessEnumByValue(int value) {
		for (ConfigDocProcessEnum status : ConfigDocProcessEnum.values()) {
			if (status.ordinal() == value) return status;
		}
		return null;
	}
}
