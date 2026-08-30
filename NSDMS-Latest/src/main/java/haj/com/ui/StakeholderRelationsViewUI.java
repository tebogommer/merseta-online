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
import haj.com.entity.datamodel.CompanyCrmCloDatamodel;
import haj.com.entity.enums.RelationTypeEnum;
import haj.com.entity.lookup.StakeholderRelations;
import haj.com.entity.lookup.StakeholderRelationsSurvey;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.StakeholderRelationsService;
import haj.com.service.lookup.StakeholderRelationsSurveyService;

@ManagedBean(name = "stakeholderRelationsViewUI")
@ViewScoped
public class StakeholderRelationsViewUI extends AbstractUI {

	/** Entity Level */
	private StakeholderRelations selectedStakeholderRelations = null;
	private StakeholderRelations survey = null;

	/** Service Levels */
	private StakeholderRelationsService stakeholderRelationsService = new StakeholderRelationsService();
	private StakeholderRelationsSurveyService stakeholderRelationsSurveyService = new StakeholderRelationsSurveyService();

	/** Array Lists */
	private LazyDataModel<StakeholderRelations> dataModelNewsLetterList;
	private List<StakeholderRelations> newsLetterList = null;

	private LazyDataModel<StakeholderRelations> dataModelNoticeList;
	private List<StakeholderRelations> noticeList = null;

	private LazyDataModel<StakeholderRelations> dataModelAvalibleSurveyList;
	private LazyDataModel<StakeholderRelations> dataModelTakenSurveyList;
	private List<StakeholderRelations> avalibleSurveyList = null;
	
	private List<StakeholderRelationsSurvey> answerList = null;
	private LazyDataModel<Company> companies;
	/** display booleans */
	private boolean displayNewSurvey = false;

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
	 * @throws Exception
	 */
	private void runInit() throws Exception {
		populateInformationLists();
	}

	private void populateInformationLists() throws Exception {
		newsLetterInfo();
		noticeListInfo();
		avalibleSurveyListInfo();
		populateCompanyDataModel();
	}

	private void populateCompanyDataModel() {
		companies = new CompanyCrmCloDatamodel(getSessionUI());
	}

	/**
	 * Populates the news letter information
	 * 
	 * @throws Exception
	 */
	public void newsLetterInfo() throws Exception {
		newsLetterList = stakeholderRelationsService.findByRelationTypeEnum(RelationTypeEnum.NewsLetter);
	}

	/**
	 * Populates the news letter information
	 * 
	 * @throws Exception
	 */
	public void noticeListInfo() throws Exception {
		noticeList = stakeholderRelationsService.findByRelationTypeEnum(RelationTypeEnum.Notice);
	}

	/**
	 * Populates the news letter information
	 * 
	 * @throws Exception
	 */
	public void avalibleSurveyListInfo() throws Exception {
//		avalibleSurveyList = stakeholderRelationsService.findByRelationTypeEnum(RelationTypeEnum.Survey);
		// Surveys taken by session user
		dataModelTakenSurveyList = new LazyDataModel<StakeholderRelations>() {
			private static final long serialVersionUID = 1L;
			private List<StakeholderRelations> retorno = new ArrayList<StakeholderRelations>();
			@Override
			public List<StakeholderRelations> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = stakeholderRelationsService.allSurveysTakenByUser(StakeholderRelations.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser(), RelationTypeEnum.Survey);
					dataModelTakenSurveyList.setRowCount(stakeholderRelationsService.countAllSurveysTakenByUser(StakeholderRelations.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(StakeholderRelations obj) {
				return obj.getId();
			}

			@Override
			public StakeholderRelations getRowData(String rowKey) {
				for (StakeholderRelations obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};
		
		
		// Surveys available for session user to take
		dataModelAvalibleSurveyList = new LazyDataModel<StakeholderRelations>() {
			private static final long serialVersionUID = 1L;
			private List<StakeholderRelations> retorno = new ArrayList<StakeholderRelations>();
			@Override
			public List<StakeholderRelations> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = stakeholderRelationsService.allSurveysNotTakenByUsers(StakeholderRelations.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser(), RelationTypeEnum.Survey);
					dataModelAvalibleSurveyList.setRowCount(stakeholderRelationsService.countAllSurveysNotTakenByUsers(StakeholderRelations.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(StakeholderRelations obj) {
				return obj.getId();
			}

			@Override
			public StakeholderRelations getRowData(String rowKey) {
				for (StakeholderRelations obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};
	}

	/**
	 * Preps New Survey Entry
	 */
	public void prepNewSurveyEntry() {
		try {
			displayNewSurvey = true;
			survey = new StakeholderRelations();
			survey.setDescription(selectedStakeholderRelations.getDescription());
			survey.setRelationTypeEnum(selectedStakeholderRelations.getRelationTypeEnum());
			survey.setUser(getSessionUI().getActiveUser());
			survey.setParentLink(selectedStakeholderRelations.getId());
			answerList = stakeholderRelationsSurveyService.findByStakeholderRelationsUserNotAssigned(selectedStakeholderRelations);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Saves the Survey
	 */
	public void saveSurvey() {
		try {
			stakeholderRelationsService.submitAndCreateSurvey(survey, answerList, getSessionUI().getActiveUser());
			avalibleSurveyListInfo();
			addInfoMessage("Survey Complete");
			displayNewSurvey = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Cancels Survey Entry
	 */
	public void cancelSurvey() {
		try {
			survey = null;
			answerList = null;
			avalibleSurveyListInfo();
			displayNewSurvey = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/** getters and setters */
	public List<StakeholderRelations> getNewsLetterList() {
		return newsLetterList;
	}

	public void setNewsLetterList(List<StakeholderRelations> newsLetterList) {
		this.newsLetterList = newsLetterList;
	}

	public List<StakeholderRelations> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<StakeholderRelations> noticeList) {
		this.noticeList = noticeList;
	}

	public List<StakeholderRelations> getAvalibleSurveyList() {
		return avalibleSurveyList;
	}

	public void setAvalibleSurveyList(List<StakeholderRelations> avalibleSurveyList) {
		this.avalibleSurveyList = avalibleSurveyList;
	}

	public StakeholderRelations getSelectedStakeholderRelations() {
		return selectedStakeholderRelations;
	}

	public void setSelectedStakeholderRelations(StakeholderRelations selectedStakeholderRelations) {
		this.selectedStakeholderRelations = selectedStakeholderRelations;
	}

	public boolean isDisplayNewSurvey() {
		return displayNewSurvey;
	}

	public void setDisplayNewSurvey(boolean displayNewSurvey) {
		this.displayNewSurvey = displayNewSurvey;
	}

	public StakeholderRelations getSurvey() {
		return survey;
	}

	public void setSurvey(StakeholderRelations survey) {
		this.survey = survey;
	}

	public List<StakeholderRelationsSurvey> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<StakeholderRelationsSurvey> answerList) {
		this.answerList = answerList;
	}

	public LazyDataModel<StakeholderRelations> getDataModelAvalibleSurveyList() {
		return dataModelAvalibleSurveyList;
	}

	public void setDataModelAvalibleSurveyList(LazyDataModel<StakeholderRelations> dataModelAvalibleSurveyList) {
		this.dataModelAvalibleSurveyList = dataModelAvalibleSurveyList;
	}

	public LazyDataModel<StakeholderRelations> getDataModelTakenSurveyList() {
		return dataModelTakenSurveyList;
	}

	public void setDataModelTakenSurveyList(LazyDataModel<StakeholderRelations> dataModelTakenSurveyList) {
		this.dataModelTakenSurveyList = dataModelTakenSurveyList;
	}

	public LazyDataModel<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(LazyDataModel<Company> companies) {
		this.companies = companies;
	}

}
