package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LearnerAchievementStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LearnerAchievementStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerAchievementStatusUI.
 */
@ManagedBean(name = "learnerachievementstatusUI")
@ViewScoped
public class LearnerAchievementStatusUI extends AbstractUI {

	/** The service. */
	private LearnerAchievementStatusService service = new LearnerAchievementStatusService();
	
	/** The learnerachievementstatus list. */
	private List<LearnerAchievementStatus> learnerachievementstatusList = null;
	
	/** The learnerachievementstatusfiltered list. */
	private List<LearnerAchievementStatus> learnerachievementstatusfilteredList = null;
	
	/** The learnerachievementstatus. */
	private LearnerAchievementStatus learnerachievementstatus = null;
	
	/** The data model. */
	private LazyDataModel<LearnerAchievementStatus> dataModel;

	/**
	 * Inits the.
	 */
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
	 * Initialize method to get all LearnerAchievementStatus and prepare a for a
	 * create of a new LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see LearnerAchievementStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		learnerachievementstatusInfo();
	}

	/**
	 * Get all LearnerAchievementStatus for data table.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementStatus
	 */
	public void learnerachievementstatusInfo() {

		dataModel = new LazyDataModel<LearnerAchievementStatus>() {

			private static final long serialVersionUID = 1L;
			private List<LearnerAchievementStatus> retorno = new ArrayList<LearnerAchievementStatus>();

			@Override
			public List<LearnerAchievementStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLearnerAchievementStatus(LearnerAchievementStatus.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LearnerAchievementStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LearnerAchievementStatus obj) {
				return obj.getId();
			}

			@Override
			public LearnerAchievementStatus getRowData(String rowKey) {
				for (LearnerAchievementStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LearnerAchievementStatus into database.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementStatus
	 */
	public void learnerachievementstatusInsert() {
		try {
			service.create(this.learnerachievementstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnerachievementstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LearnerAchievementStatus in database.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementStatus
	 */
	public void learnerachievementstatusUpdate() {
		try {
			service.update(this.learnerachievementstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnerachievementstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LearnerAchievementStatus from database.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementStatus
	 */
	public void learnerachievementstatusDelete() {
		try {
			service.delete(this.learnerachievementstatus);
			prepareNew();
			learnerachievementstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementStatus
	 */
	public void prepareNew() {
		learnerachievementstatus = new LearnerAchievementStatus();
	}

	/*
	 * public List<SelectItem> getLearnerAchievementStatusDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * learnerachievementstatusInfo(); for (LearnerAchievementStatus ug :
	 * learnerachievementstatusList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LearnerAchievementStatus> complete(String desc) {
		List<LearnerAchievementStatus> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the learner achievement status list.
	 *
	 * @return the learner achievement status list
	 */
	public List<LearnerAchievementStatus> getLearnerAchievementStatusList() {
		return learnerachievementstatusList;
	}

	/**
	 * Sets the learner achievement status list.
	 *
	 * @param learnerachievementstatusList the new learner achievement status list
	 */
	public void setLearnerAchievementStatusList(List<LearnerAchievementStatus> learnerachievementstatusList) {
		this.learnerachievementstatusList = learnerachievementstatusList;
	}

	/**
	 * Gets the learnerachievementstatus.
	 *
	 * @return the learnerachievementstatus
	 */
	public LearnerAchievementStatus getLearnerachievementstatus() {
		return learnerachievementstatus;
	}

	/**
	 * Sets the learnerachievementstatus.
	 *
	 * @param learnerachievementstatus the new learnerachievementstatus
	 */
	public void setLearnerachievementstatus(LearnerAchievementStatus learnerachievementstatus) {
		this.learnerachievementstatus = learnerachievementstatus;
	}

	/**
	 * Gets the learner achievement statusfiltered list.
	 *
	 * @return the learner achievement statusfiltered list
	 */
	public List<LearnerAchievementStatus> getLearnerAchievementStatusfilteredList() {
		return learnerachievementstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param learnerachievementstatusfilteredList            the new learnerachievementstatusfilteredList list
	 * @see LearnerAchievementStatus
	 */
	public void setLearnerAchievementStatusfilteredList(
			List<LearnerAchievementStatus> learnerachievementstatusfilteredList) {
		this.learnerachievementstatusfilteredList = learnerachievementstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<LearnerAchievementStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<LearnerAchievementStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
