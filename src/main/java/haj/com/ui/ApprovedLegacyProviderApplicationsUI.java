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
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.LegacyProviderApplicationAlterationAudit;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderLearnership;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUnitStandardService;
import haj.com.service.LegacyProviderApplicationAlterationAuditService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderLearnershipService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.TrainingProviderSkillsSetService;
import haj.com.service.UnitStandardsService;
import haj.com.service.lookup.LegacyProviderAccreditationService;
import haj.com.service.lookup.SkillsProgramUnitStandardsService;
import haj.com.service.lookup.SkillsSetUnitStandardsService;

@ManagedBean(name = "approvedLegacyProviderApplicationsUI")
@ViewScoped
public class ApprovedLegacyProviderApplicationsUI extends AbstractUI {

	/* Entity Level */
	private Company selectedCompany = null;
	private TrainingProviderApplication trainingProviderApplication = null;
	private LegacyProviderAccreditation legacyProviderAccreditation = null;
	private Qualification qualificationSelected = null;
	private SkillsSet skillsSetSelected = null;
	private SkillsProgram skillsProgramSelected = null;
	private UnitStandards unitStandardSelected = null;

	/* Service Level */
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private CompanyService companyService = new CompanyService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	private SkillsProgramUnitStandardsService skillsProgramUnitStandardsService = new SkillsProgramUnitStandardsService();
	private SkillsSetUnitStandardsService skillsSetUnitStandardsService = new SkillsSetUnitStandardsService();
	private LegacyProviderApplicationAlterationAuditService legacyProviderApplicationAlterationAuditService = new LegacyProviderApplicationAlterationAuditService();
	private LegacyProviderAccreditationService legacyProviderAccreditationService = new LegacyProviderAccreditationService();
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
	private CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();
	private TrainingProviderSkillsSetService trainingProviderSkillsSetService = new TrainingProviderSkillsSetService();
	
	/* Lazy Data Models */
	private LazyDataModel<TrainingProviderApplication> dataModelTrainingProviderApplication;
	private LazyDataModel<LegacyProviderApplicationAlterationAudit> dataModelLegacyProviderApplicationAlterationAudit;
	// allCompanyQualificationsByProviderApplicationIdAndManuallyEntered
	private LazyDataModel<CompanyQualifications> dataModelCompanyQualifications;
	// allCompanyUnitStandardByProviderApplicationIdAndManuallyEntered
	private LazyDataModel<CompanyUnitStandard> dataModelCompanyUnitStandard;
	// allTrainingProviderSkillsProgrammeByProviderApplicationIdAndManuallyEntered
	private LazyDataModel<TrainingProviderSkillsProgramme> dataModelSkillsProgramme;
	// allTrainingProviderSkillsSetByProviderApplicationIdAndManuallyEntered
	private LazyDataModel<TrainingProviderSkillsSet> dataModelSkillsSet;
	
	/* Array Lists */
	private List<UnitStandards> unitStandardList = new ArrayList<>();
	
	/* Boolean */
	private boolean hasAccess = false;
	private boolean alterationUnderway = false;
	
	/* Enums */
	private QualificationTypeSelectionEnum qualificationTypeSelectionEnum;
	
	/* Vars */
	private String reasonForAlteration = "";

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
		// check if user has access
		validiateAccess();
		if (hasAccess) {
			dataModelTrainingProviderApplicationInfo();
			dataModelLegacyProviderApplicationAlterationAuditInfo();
		} else {
			ajaxRedirectToDashboard();
		}
	}

	private void validiateAccess() {
		hasAccess = false;
		if (getSessionUI().isAdmin()) {
			hasAccess = true;
		} else if (getSessionUI() != null && getSessionUI().getActiveUser() != null && getSessionUI().getActiveUser().getAlterLegacyApplications() != null && getSessionUI().getActiveUser().getAlterLegacyApplications()) {
			hasAccess = true;
		}
	}

	// view of all provider applications
	public void dataModelTrainingProviderApplicationInfo(){
		dataModelTrainingProviderApplication = new LazyDataModel<TrainingProviderApplication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<>();
			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = trainingProviderApplicationService.allTrainingProviderApplicationByStatusAndLegacyLinked(first, pageSize, sortField, sortOrder, filters, ApprovalEnum.getOpenStatusForTrainingProviderApplicationsForLegacy());
					dataModelTrainingProviderApplication.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationByStatusAndLegacyLinked(filters, ApprovalEnum.getOpenStatusForTrainingProviderApplicationsForLegacy()));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(TrainingProviderApplication obj) {
				return obj.getId();
			}
			@Override
			public TrainingProviderApplication getRowData(String rowKey) {
				for (TrainingProviderApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	// view of audit info
	public void dataModelLegacyProviderApplicationAlterationAuditInfo(){
		dataModelLegacyProviderApplicationAlterationAudit = new LazyDataModel<LegacyProviderApplicationAlterationAudit>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyProviderApplicationAlterationAudit> retorno = new ArrayList<>();
			@Override
			public List<LegacyProviderApplicationAlterationAudit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = legacyProviderApplicationAlterationAuditService.allLegacyProviderApplicationAlterationAudit(LegacyProviderApplicationAlterationAudit.class,first, pageSize, sortField, sortOrder, filters);
					dataModelLegacyProviderApplicationAlterationAudit.setRowCount(legacyProviderApplicationAlterationAuditService.count(LegacyProviderApplicationAlterationAudit.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyProviderApplicationAlterationAudit obj) {
				return obj.getId();
			}
			@Override
			public LegacyProviderApplicationAlterationAudit getRowData(String rowKey) {
				for (LegacyProviderApplicationAlterationAudit obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void clearDatatables(){
		dataModelCompanyQualifications = null;
		dataModelCompanyUnitStandard = null;
		dataModelSkillsProgramme = null;
		dataModelSkillsSet = null;
	}
	
	private void clearData() {
		trainingProviderApplication = null;
		selectedCompany = null;
		legacyProviderAccreditation = null;
	}
	
	private void prepQualificationSelection(){
		reasonForAlteration = "";
		qualificationSelected  = null;
		skillsSetSelected  = null;
		skillsProgramSelected = null;
		unitStandardSelected = null;
		unitStandardList = new ArrayList<>();
	}
	
	public void prepAlteration(){
		try {
			if (trainingProviderApplication == null) {
				throw new Exception("Unable to locate provider application, contact support!");
			} else {
				// clears all data
				prepQualificationSelection();
				clearDatatables();
				// default selection
				qualificationTypeSelectionEnum = QualificationTypeSelectionEnum.Qualification;
				// populate company
				selectedCompany = companyService.findByKeyNoResolveData(trainingProviderApplication.getCompany().getId());
				companyService.resolveCompanyAddresses(selectedCompany);
				// populate legacy information
				legacyProviderAccreditation = legacyProviderAccreditationService.findByKey(trainingProviderApplication.getLegacyProviderAccreditation().getId());
				// sets the view for alteration
				alterationUnderway = true;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void changeTypeSelection(){
		try {
			prepQualificationSelection();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void qualificationSelection(){
		try {
			// populate unit standards list
			unitStandardList =  unitStandardsService.findByQualification(qualificationSelected);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void skillsProgrammeSelection(){
		try {
			// populate unit standards list
			unitStandardList =  skillsProgramUnitStandardsService.findUnitStandardsBySkillsProgrammeId(skillsProgramSelected.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void skillsSetSelection(){
		try {
			// populate unit standards list
			unitStandardList =  skillsSetUnitStandardsService.findUnitStandardsBySkillsSetId(skillsSetSelected.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addAlterationWithAudit(){
		try {
			if (reasonForAlteration == null || reasonForAlteration.trim().isEmpty()) {
				throw new Exception("");
			}
			LegacyProviderApplicationAlterationAudit audit = legacyProviderApplicationAlterationAuditService.prepNewAudit(trainingProviderApplication, selectedCompany, legacyProviderAccreditation,  getSessionUI().getActiveUser(), qualificationTypeSelectionEnum, reasonForAlteration);
			legacyProviderApplicationAlterationAuditService.createNewLinkAndAudit(audit, qualificationSelected, skillsSetSelected, skillsProgramSelected, unitStandardSelected, unitStandardList);
			dataModelLegacyProviderApplicationAlterationAuditInfo();
			clearData();
			prepQualificationSelection();
			alterationUnderway = false;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cancelAlteration(){
		try {
			clearData();
			prepQualificationSelection();
			alterationUnderway = false;
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* getters and setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public LegacyProviderAccreditation getLegacyProviderAccreditation() {
		return legacyProviderAccreditation;
	}

	public void setLegacyProviderAccreditation(LegacyProviderAccreditation legacyProviderAccreditation) {
		this.legacyProviderAccreditation = legacyProviderAccreditation;
	}

	public Qualification getQualificationSelected() {
		return qualificationSelected;
	}

	public void setQualificationSelected(Qualification qualificationSelected) {
		this.qualificationSelected = qualificationSelected;
	}

	public SkillsSet getSkillsSetSelected() {
		return skillsSetSelected;
	}

	public void setSkillsSetSelected(SkillsSet skillsSetSelected) {
		this.skillsSetSelected = skillsSetSelected;
	}

	public SkillsProgram getSkillsProgramSelected() {
		return skillsProgramSelected;
	}

	public void setSkillsProgramSelected(SkillsProgram skillsProgramSelected) {
		this.skillsProgramSelected = skillsProgramSelected;
	}

	public LazyDataModel<TrainingProviderApplication> getDataModelTrainingProviderApplication() {
		return dataModelTrainingProviderApplication;
	}

	public void setDataModelTrainingProviderApplication(
			LazyDataModel<TrainingProviderApplication> dataModelTrainingProviderApplication) {
		this.dataModelTrainingProviderApplication = dataModelTrainingProviderApplication;
	}

	public LazyDataModel<CompanyQualifications> getDataModelCompanyQualifications() {
		return dataModelCompanyQualifications;
	}

	public void setDataModelCompanyQualifications(LazyDataModel<CompanyQualifications> dataModelCompanyQualifications) {
		this.dataModelCompanyQualifications = dataModelCompanyQualifications;
	}

	public LazyDataModel<CompanyUnitStandard> getDataModelCompanyUnitStandard() {
		return dataModelCompanyUnitStandard;
	}

	public void setDataModelCompanyUnitStandard(LazyDataModel<CompanyUnitStandard> dataModelCompanyUnitStandard) {
		this.dataModelCompanyUnitStandard = dataModelCompanyUnitStandard;
	}

	public LazyDataModel<TrainingProviderSkillsProgramme> getDataModelSkillsProgramme() {
		return dataModelSkillsProgramme;
	}

	public void setDataModelSkillsProgramme(LazyDataModel<TrainingProviderSkillsProgramme> dataModelSkillsProgramme) {
		this.dataModelSkillsProgramme = dataModelSkillsProgramme;
	}

	public LazyDataModel<TrainingProviderSkillsSet> getDataModelSkillsSet() {
		return dataModelSkillsSet;
	}

	public void setDataModelSkillsSet(LazyDataModel<TrainingProviderSkillsSet> dataModelSkillsSet) {
		this.dataModelSkillsSet = dataModelSkillsSet;
	}

	public LazyDataModel<LegacyProviderApplicationAlterationAudit> getDataModelLegacyProviderApplicationAlterationAudit() {
		return dataModelLegacyProviderApplicationAlterationAudit;
	}

	public void setDataModelLegacyProviderApplicationAlterationAudit(
			LazyDataModel<LegacyProviderApplicationAlterationAudit> dataModelLegacyProviderApplicationAlterationAudit) {
		this.dataModelLegacyProviderApplicationAlterationAudit = dataModelLegacyProviderApplicationAlterationAudit;
	}

	public QualificationTypeSelectionEnum getQualificationTypeSelectionEnum() {
		return qualificationTypeSelectionEnum;
	}

	public void setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum qualificationTypeSelectionEnum) {
		this.qualificationTypeSelectionEnum = qualificationTypeSelectionEnum;
	}

	public boolean isAlterationUnderway() {
		return alterationUnderway;
	}

	public void setAlterationUnderway(boolean alterationUnderway) {
		this.alterationUnderway = alterationUnderway;
	}

	public String getReasonForAlteration() {
		return reasonForAlteration;
	}

	public void setReasonForAlteration(String reasonForAlteration) {
		this.reasonForAlteration = reasonForAlteration;
	}

	public UnitStandards getUnitStandardSelected() {
		return unitStandardSelected;
	}

	public void setUnitStandardSelected(UnitStandards unitStandardSelected) {
		this.unitStandardSelected = unitStandardSelected;
	}

	public List<UnitStandards> getUnitStandardList() {
		return unitStandardList;
	}

	public void setUnitStandardList(List<UnitStandards> unitStandardList) {
		this.unitStandardList = unitStandardList;
	}

}