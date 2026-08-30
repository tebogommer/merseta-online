package  haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.enums.RelationTypeEnum;
import haj.com.entity.lookup.StakeholderRelations;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.StakeholderRelationsService;
import haj.com.service.lookup.StakeholderRelationsSurveyService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "surveyReportUI")
@ViewScoped
public class SurveyReportUI extends AbstractUI {

	/** Entity */
	private StakeholderRelations stakeholderRelations = null;
	
	/** Service Level */
	private StakeholderRelationsService stakeholderRelationsService = new StakeholderRelationsService();
	private StakeholderRelationsSurveyService stakeholderRelationsSurveyService = new StakeholderRelationsSurveyService();
	
	/** Vars */
	private int totalSurveysTaken;
	private int totalSurveysAvalible;
	
	/** Lists */
	private LazyDataModel<StakeholderRelations> dataModelAllSurveysTakenList;
	private LazyDataModel<StakeholderRelations> dataModelSurveysConfigured;
	
	/** Date selections */
	private Date fromDate;
	private Date toDate;
	
	/** Booleans */
	private boolean filterByDateRange = false;
	
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
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		populateDefaultInformation();
		populateCounts();
		allSurveysTakenInfo();
		allSurveysConfiguredInfo();
	}

	/**
	 * Populates default Information
	 */
	private void populateDefaultInformation() {
		filterByDateRange = false;
		if (fromDate == null) {
			fromDate = GenericUtility.getFirstDayOfMonth(new Date());
		}
		if (toDate == null) {
			toDate = GenericUtility.getLasttDayOfMonth(new Date());
		}
	}

	/**
	 * 
	 * Populates the count Variables of the report
	 * @throws Exception the exception
	 */
	private void populateCounts() throws Exception{
		totalSurveysTaken = stakeholderRelationsService.countTotalSurveysTakenByUsers().intValue();
		totalSurveysAvalible = stakeholderRelationsService.countTotalSurveysAvalible().intValue();
	}
	
	/**
	 * Does a search 
	 */
	public void runSearch(){
		try {
			allSurveysTakenInfo();
			if (!filterByDateRange) {
				addInfoMessage("Filter Removed");
			} else {
				addInfoMessage("Filter Applied");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void allSurveysTakenInfo() throws Exception{
		dataModelAllSurveysTakenList = new LazyDataModel<StakeholderRelations>() {
			private static final long serialVersionUID = 1L;
			private List<StakeholderRelations> retorno = new ArrayList<StakeholderRelations>();
			@Override
			public List<StakeholderRelations> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = stakeholderRelationsService.allSurveysTakenByUsers(StakeholderRelations.class, first, pageSize, sortField, sortOrder, filters, RelationTypeEnum.Survey, filterByDateRange, fromDate, toDate);
					dataModelAllSurveysTakenList.setRowCount(stakeholderRelationsService.countAllSurveysTakenByUsers(StakeholderRelations.class, filters,filterByDateRange));
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
	 * Locates Surveys Configured in admin moudle
	 * @throws Exception
	 */
	public void allSurveysConfiguredInfo() throws Exception{
		dataModelSurveysConfigured = new LazyDataModel<StakeholderRelations>() {
			private static final long serialVersionUID = 1L;
			private List<StakeholderRelations> retorno = new ArrayList<StakeholderRelations>();
			@Override
			public List<StakeholderRelations> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = stakeholderRelationsService.allSurveysConfigured(StakeholderRelations.class, first, pageSize, sortField, sortOrder, filters, RelationTypeEnum.Survey);
					dataModelSurveysConfigured.setRowCount(stakeholderRelationsService.countAllSurveysConfigured(StakeholderRelations.class, filters));
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

	/** Getters and Setters */
	public LazyDataModel<StakeholderRelations> getDataModelAllSurveysTakenList() {
		return dataModelAllSurveysTakenList;
	}

	public void setDataModelAllSurveysTakenList(LazyDataModel<StakeholderRelations> dataModelAllSurveysTakenList) {
		this.dataModelAllSurveysTakenList = dataModelAllSurveysTakenList;
	}

	public int getTotalSurveysTaken() {
		return totalSurveysTaken;
	}

	public void setTotalSurveysTaken(int totalSurveysTaken) {
		this.totalSurveysTaken = totalSurveysTaken;
	}

	public StakeholderRelations getStakeholderRelations() {
		return stakeholderRelations;
	}

	public void setStakeholderRelations(StakeholderRelations stakeholderRelations) {
		this.stakeholderRelations = stakeholderRelations;
	}

	public int getTotalSurveysAvalible() {
		return totalSurveysAvalible;
	}

	public void setTotalSurveysAvalible(int totalSurveysAvalible) {
		this.totalSurveysAvalible = totalSurveysAvalible;
	}

	public LazyDataModel<StakeholderRelations> getDataModelSurveysConfigured() {
		return dataModelSurveysConfigured;
	}

	public void setDataModelSurveysConfigured(LazyDataModel<StakeholderRelations> dataModelSurveysConfigured) {
		this.dataModelSurveysConfigured = dataModelSurveysConfigured;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isFilterByDateRange() {
		return filterByDateRange;
	}

	public void setFilterByDateRange(boolean filterByDateRange) {
		this.filterByDateRange = filterByDateRange;
	}

	
}
