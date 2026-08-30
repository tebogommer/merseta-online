package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.entity.lookup.InterventionType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringLearnerSurveyAnswersService;

@ManagedBean(name = "workplacemonitoringlearnersurveyanswersUI")
@ViewScoped
public class WorkplaceMonitoringLearnerSurveyAnswersUI extends AbstractUI {

	private WorkplaceMonitoringLearnerSurveyAnswersService service = new WorkplaceMonitoringLearnerSurveyAnswersService();
	private List<WorkplaceMonitoringLearnerSurveyAnswers> workplacemonitoringlearnersurveyanswersList = null;
	private List<WorkplaceMonitoringLearnerSurveyAnswers> workplacemonitoringlearnersurveyanswersfilteredList = null;
	private WorkplaceMonitoringLearnerSurveyAnswers workplacemonitoringlearnersurveyanswers = null;
	private LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> dataModel;
	
	private InterventionType selectedInterventionTypeAnswers;
	private InterventionType toCopyInterventionTypeAnswers;

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
	 * Initialize method to get all WorkplaceMonitoringLearnerSurveyAnswers and
	 * prepare a for a create of a new WorkplaceMonitoringLearnerSurveyAnswers
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringLearnerSurveyAnswers
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringlearnersurveyanswersInfo();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerSurveyAnswers for data table where no
	 * traget class and key assigned
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void workplacemonitoringlearnersurveyanswersInfo() {
		dataModel = new LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringLearnerSurveyAnswers> retorno = new ArrayList<>();

			@Override
			public List<WorkplaceMonitoringLearnerSurveyAnswers> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allWorkplaceMonitoringLearnerSurveyAnswersWhereNoTragetClassAssigned(first,
							pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(
							service.countAllWorkplaceMonitoringLearnerSurveyAnswersWhereNoTragetClassAssigned(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkplaceMonitoringLearnerSurveyAnswers obj) {
				return obj.getId();
			}

			@Override
			public WorkplaceMonitoringLearnerSurveyAnswers getRowData(String rowKey) {
				for (WorkplaceMonitoringLearnerSurveyAnswers obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert WorkplaceMonitoringLearnerSurveyAnswers into database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void workplacemonitoringlearnersurveyanswersInsert() {
		try {
			service.createLookUpEntry(this.workplacemonitoringlearnersurveyanswers);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringlearnersurveyanswersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WorkplaceMonitoringLearnerSurveyAnswers in database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void workplacemonitoringlearnersurveyanswersUpdate() {
		try {
			service.update(this.workplacemonitoringlearnersurveyanswers);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringlearnersurveyanswersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WorkplaceMonitoringLearnerSurveyAnswers from database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void workplacemonitoringlearnersurveyanswersDelete() {
		try {
			service.delete(this.workplacemonitoringlearnersurveyanswers);
			prepareNew();
			workplacemonitoringlearnersurveyanswersInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepQuestionCopyByInterventionType(){
		try {
			selectedInterventionTypeAnswers = null;
			toCopyInterventionTypeAnswers = null;
			runClientSideExecute("PF('internetionTypeQuestionCopyDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void copyInterventionTypeQuestions(){
		try {
			if (selectedInterventionTypeAnswers == null) {
				throw new Exception("Provide intervention type for configured questions");
			}
			if (toCopyInterventionTypeAnswers == null) {
				throw new Exception("Provide intervention type for configured questions");
			}
			if (selectedInterventionTypeAnswers.getId().equals(toCopyInterventionTypeAnswers.getId())) {
				throw new Exception("Intervention types can not be the same. Select a different Intervention type for one of the selection.");
			}
			
			service.copyLookUpAnswersByInterventionTypeToAnother(selectedInterventionTypeAnswers, toCopyInterventionTypeAnswers);
			runClientSideExecute("PF('internetionTypeQuestionCopyDlg').hide()");
			prepareNew();
			workplacemonitoringlearnersurveyanswersInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepInterventionTypeQuestionsDelete(){
		try {
			selectedInterventionTypeAnswers = null;
			runClientSideExecute("PF('internetionTypeQuestionDeleteDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void deleteQuestionsByInterventionType(){
		try {
			if (selectedInterventionTypeAnswers == null) {
				throw new Exception("Provide intervention type for questions delete");
			}
			service.deleteAllQuestionsByInterventionType(selectedInterventionTypeAnswers);
			runClientSideExecute("PF('internetionTypeQuestionDeleteDlg').hide()");
			prepareNew();
			workplacemonitoringlearnersurveyanswersInfo();
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WorkplaceMonitoringLearnerSurveyAnswers
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void prepareNew() {
		workplacemonitoringlearnersurveyanswers = new WorkplaceMonitoringLearnerSurveyAnswers();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WorkplaceMonitoringLearnerSurveyAnswers> complete(String desc) {
		List<WorkplaceMonitoringLearnerSurveyAnswers> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WorkplaceMonitoringLearnerSurveyAnswers> getWorkplaceMonitoringLearnerSurveyAnswersList() {
		return workplacemonitoringlearnersurveyanswersList;
	}

	public void setWorkplaceMonitoringLearnerSurveyAnswersList(
			List<WorkplaceMonitoringLearnerSurveyAnswers> workplacemonitoringlearnersurveyanswersList) {
		this.workplacemonitoringlearnersurveyanswersList = workplacemonitoringlearnersurveyanswersList;
	}

	public WorkplaceMonitoringLearnerSurveyAnswers getWorkplacemonitoringlearnersurveyanswers() {
		return workplacemonitoringlearnersurveyanswers;
	}

	public void setWorkplacemonitoringlearnersurveyanswers(
			WorkplaceMonitoringLearnerSurveyAnswers workplacemonitoringlearnersurveyanswers) {
		this.workplacemonitoringlearnersurveyanswers = workplacemonitoringlearnersurveyanswers;
	}

	public List<WorkplaceMonitoringLearnerSurveyAnswers> getWorkplaceMonitoringLearnerSurveyAnswersfilteredList() {
		return workplacemonitoringlearnersurveyanswersfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param workplacemonitoringlearnersurveyanswersfilteredList
	 *            the new workplacemonitoringlearnersurveyanswersfilteredList
	 *            list
	 * @see WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void setWorkplaceMonitoringLearnerSurveyAnswersfilteredList(
			List<WorkplaceMonitoringLearnerSurveyAnswers> workplacemonitoringlearnersurveyanswersfilteredList) {
		this.workplacemonitoringlearnersurveyanswersfilteredList = workplacemonitoringlearnersurveyanswersfilteredList;
	}

	public LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringLearnerSurveyAnswers> dataModel) {
		this.dataModel = dataModel;
	}

	public InterventionType getSelectedInterventionTypeAnswers() {
		return selectedInterventionTypeAnswers;
	}

	public void setSelectedInterventionTypeAnswers(InterventionType selectedInterventionTypeAnswers) {
		this.selectedInterventionTypeAnswers = selectedInterventionTypeAnswers;
	}

	public InterventionType getToCopyInterventionTypeAnswers() {
		return toCopyInterventionTypeAnswers;
	}

	public void setToCopyInterventionTypeAnswers(InterventionType toCopyInterventionTypeAnswers) {
		this.toCopyInterventionTypeAnswers = toCopyInterventionTypeAnswers;
	}

}
