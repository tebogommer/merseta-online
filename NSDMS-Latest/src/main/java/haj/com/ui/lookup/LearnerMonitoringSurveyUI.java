package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.LearnerMonitoringSurvey;
import haj.com.service.lookup.LearnerMonitoringSurveyService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "learnermonitoringsurveyUI")
@ViewScoped
public class LearnerMonitoringSurveyUI extends AbstractUI {

	private LearnerMonitoringSurveyService service = new LearnerMonitoringSurveyService();
	private List<LearnerMonitoringSurvey> learnermonitoringsurveyList = null;
	private List<LearnerMonitoringSurvey> learnermonitoringsurveyfilteredList = null;
	private LearnerMonitoringSurvey learnermonitoringsurvey = null;
	private LazyDataModel<LearnerMonitoringSurvey> dataModel;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all LearnerMonitoringSurvey and prepare a for a
	 * create of a new LearnerMonitoringSurvey
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		learnermonitoringsurveyInfo();
	}

	/**
	 * Get all LearnerMonitoringSurvey for data table
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 */
	public void learnermonitoringsurveyInfo() {

		dataModel = new LazyDataModel<LearnerMonitoringSurvey>() {

			private static final long serialVersionUID = 1L;
			private List<LearnerMonitoringSurvey> retorno = new ArrayList<LearnerMonitoringSurvey>();

			@Override
			public List<LearnerMonitoringSurvey> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allMainLearnerMonitoringSurvey(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countMain(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LearnerMonitoringSurvey obj) {
				return obj.getId();
			}

			@Override
			public LearnerMonitoringSurvey getRowData(String rowKey) {
				for (LearnerMonitoringSurvey obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LearnerMonitoringSurvey into database
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 */
	public void learnermonitoringsurveyInsert() {
		try {
			service.create(this.learnermonitoringsurvey);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnermonitoringsurveyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LearnerMonitoringSurvey in database
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 */
	public void learnermonitoringsurveyUpdate() {
		try {
			service.update(this.learnermonitoringsurvey);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnermonitoringsurveyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LearnerMonitoringSurvey from database
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 */
	public void learnermonitoringsurveyDelete() {
		try {
			service.delete(this.learnermonitoringsurvey);
			prepareNew();
			learnermonitoringsurveyInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LearnerMonitoringSurvey
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 */
	public void prepareNew() {
		learnermonitoringsurvey = new LearnerMonitoringSurvey();
	}

	/*
	 * public List<SelectItem> getLearnerMonitoringSurveyDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * learnermonitoringsurveyInfo(); for (LearnerMonitoringSurvey ug :
	 * learnermonitoringsurveyList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LearnerMonitoringSurvey> complete(String desc) {
		List<LearnerMonitoringSurvey> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LearnerMonitoringSurvey> getLearnerMonitoringSurveyList() {
		return learnermonitoringsurveyList;
	}

	public void setLearnerMonitoringSurveyList(List<LearnerMonitoringSurvey> learnermonitoringsurveyList) {
		this.learnermonitoringsurveyList = learnermonitoringsurveyList;
	}

	public LearnerMonitoringSurvey getLearnermonitoringsurvey() {
		return learnermonitoringsurvey;
	}

	public void setLearnermonitoringsurvey(LearnerMonitoringSurvey learnermonitoringsurvey) {
		this.learnermonitoringsurvey = learnermonitoringsurvey;
	}

	public List<LearnerMonitoringSurvey> getLearnerMonitoringSurveyfilteredList() {
		return learnermonitoringsurveyfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param learnermonitoringsurveyfilteredList
	 *            the new learnermonitoringsurveyfilteredList list
	 * @see LearnerMonitoringSurvey
	 */
	public void setLearnerMonitoringSurveyfilteredList(List<LearnerMonitoringSurvey> learnermonitoringsurveyfilteredList) {
		this.learnermonitoringsurveyfilteredList = learnermonitoringsurveyfilteredList;
	}

	public LazyDataModel<LearnerMonitoringSurvey> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LearnerMonitoringSurvey> dataModel) {
		this.dataModel = dataModel;
	}

}
