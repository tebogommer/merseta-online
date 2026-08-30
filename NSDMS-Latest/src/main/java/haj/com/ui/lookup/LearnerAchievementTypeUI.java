package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LearnerAchievementType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LearnerAchievementTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerAchievementTypeUI.
 */
@ManagedBean(name = "learnerachievementtypeUI")
@ViewScoped
public class LearnerAchievementTypeUI extends AbstractUI {

	/** The service. */
	private LearnerAchievementTypeService service = new LearnerAchievementTypeService();
	
	/** The learnerachievementtype list. */
	private List<LearnerAchievementType> learnerachievementtypeList = null;
	
	/** The learnerachievementtypefiltered list. */
	private List<LearnerAchievementType> learnerachievementtypefilteredList = null;
	
	/** The learnerachievementtype. */
	private LearnerAchievementType learnerachievementtype = null;
	
	/** The data model. */
	private LazyDataModel<LearnerAchievementType> dataModel;

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
	 * Initialize method to get all LearnerAchievementType and prepare a for a
	 * create of a new LearnerAchievementType.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see LearnerAchievementType
	 */
	private void runInit() throws Exception {
		prepareNew();
		learnerachievementtypeInfo();
	}

	/**
	 * Get all LearnerAchievementType for data table.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementType
	 */
	public void learnerachievementtypeInfo() {

		dataModel = new LazyDataModel<LearnerAchievementType>() {

			private static final long serialVersionUID = 1L;
			private List<LearnerAchievementType> retorno = new ArrayList<LearnerAchievementType>();

			@Override
			public List<LearnerAchievementType> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLearnerAchievementType(LearnerAchievementType.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LearnerAchievementType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LearnerAchievementType obj) {
				return obj.getId();
			}

			@Override
			public LearnerAchievementType getRowData(String rowKey) {
				for (LearnerAchievementType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LearnerAchievementType into database.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementType
	 */
	public void learnerachievementtypeInsert() {
		try {
			service.create(this.learnerachievementtype);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnerachievementtypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LearnerAchievementType in database.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementType
	 */
	public void learnerachievementtypeUpdate() {
		try {
			service.update(this.learnerachievementtype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnerachievementtypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LearnerAchievementType from database.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementType
	 */
	public void learnerachievementtypeDelete() {
		try {
			service.delete(this.learnerachievementtype);
			prepareNew();
			learnerachievementtypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LearnerAchievementType.
	 *
	 * @author TechFinium
	 * @see LearnerAchievementType
	 */
	public void prepareNew() {
		learnerachievementtype = new LearnerAchievementType();
	}

	/*
	 * public List<SelectItem> getLearnerAchievementTypeDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * learnerachievementtypeInfo(); for (LearnerAchievementType ug :
	 * learnerachievementtypeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LearnerAchievementType> complete(String desc) {
		List<LearnerAchievementType> l = null;
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
	 * Gets the learner achievement type list.
	 *
	 * @return the learner achievement type list
	 */
	public List<LearnerAchievementType> getLearnerAchievementTypeList() {
		return learnerachievementtypeList;
	}

	/**
	 * Sets the learner achievement type list.
	 *
	 * @param learnerachievementtypeList the new learner achievement type list
	 */
	public void setLearnerAchievementTypeList(List<LearnerAchievementType> learnerachievementtypeList) {
		this.learnerachievementtypeList = learnerachievementtypeList;
	}

	/**
	 * Gets the learnerachievementtype.
	 *
	 * @return the learnerachievementtype
	 */
	public LearnerAchievementType getLearnerachievementtype() {
		return learnerachievementtype;
	}

	/**
	 * Sets the learnerachievementtype.
	 *
	 * @param learnerachievementtype the new learnerachievementtype
	 */
	public void setLearnerachievementtype(LearnerAchievementType learnerachievementtype) {
		this.learnerachievementtype = learnerachievementtype;
	}

	/**
	 * Gets the learner achievement typefiltered list.
	 *
	 * @return the learner achievement typefiltered list
	 */
	public List<LearnerAchievementType> getLearnerAchievementTypefilteredList() {
		return learnerachievementtypefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param learnerachievementtypefilteredList            the new learnerachievementtypefilteredList list
	 * @see LearnerAchievementType
	 */
	public void setLearnerAchievementTypefilteredList(List<LearnerAchievementType> learnerachievementtypefilteredList) {
		this.learnerachievementtypefilteredList = learnerachievementtypefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<LearnerAchievementType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<LearnerAchievementType> dataModel) {
		this.dataModel = dataModel;
	}

}
