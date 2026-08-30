package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.QualificationType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.QualificationTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationTypeUI.
 */
@ManagedBean(name = "qualificationtypeUI")
@ViewScoped
public class QualificationTypeUI extends AbstractUI {

	/** The service. */
	private QualificationTypeService service = new QualificationTypeService();
	
	/** The qualificationtype list. */
	private List<QualificationType> qualificationtypeList = null;
	
	/** The qualificationtypefiltered list. */
	private List<QualificationType> qualificationtypefilteredList = null;
	
	/** The qualificationtype. */
	private QualificationType qualificationtype = null;
	
	/** The data model. */
	private LazyDataModel<QualificationType> dataModel;

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
	 * Initialize method to get all QualificationType and prepare a for a create
	 * of a new QualificationType.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see QualificationType
	 */
	private void runInit() throws Exception {
		prepareNew();
		qualificationtypeInfo();
	}

	/**
	 * Get all QualificationType for data table.
	 *
	 * @author TechFinium
	 * @see QualificationType
	 */
	public void qualificationtypeInfo() {

		dataModel = new LazyDataModel<QualificationType>() {

			private static final long serialVersionUID = 1L;
			private List<QualificationType> retorno = new ArrayList<QualificationType>();

			@Override
			public List<QualificationType> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allQualificationType(QualificationType.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(QualificationType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(QualificationType obj) {
				return obj.getId();
			}

			@Override
			public QualificationType getRowData(String rowKey) {
				for (QualificationType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert QualificationType into database.
	 *
	 * @author TechFinium
	 * @see QualificationType
	 */
	public void qualificationtypeInsert() {
		try {
			service.create(this.qualificationtype);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			qualificationtypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update QualificationType in database.
	 *
	 * @author TechFinium
	 * @see QualificationType
	 */
	public void qualificationtypeUpdate() {
		try {
			service.update(this.qualificationtype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			qualificationtypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete QualificationType from database.
	 *
	 * @author TechFinium
	 * @see QualificationType
	 */
	public void qualificationtypeDelete() {
		try {
			service.delete(this.qualificationtype);
			prepareNew();
			qualificationtypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of QualificationType.
	 *
	 * @author TechFinium
	 * @see QualificationType
	 */
	public void prepareNew() {
		qualificationtype = new QualificationType();
	}

	/*
	 * public List<SelectItem> getQualificationTypeDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * qualificationtypeInfo(); for (QualificationType ug :
	 * qualificationtypeList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<QualificationType> complete(String desc) {
		List<QualificationType> l = null;
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
	 * Gets the qualification type list.
	 *
	 * @return the qualification type list
	 */
	public List<QualificationType> getQualificationTypeList() {
		return qualificationtypeList;
	}

	/**
	 * Sets the qualification type list.
	 *
	 * @param qualificationtypeList the new qualification type list
	 */
	public void setQualificationTypeList(List<QualificationType> qualificationtypeList) {
		this.qualificationtypeList = qualificationtypeList;
	}

	/**
	 * Gets the qualificationtype.
	 *
	 * @return the qualificationtype
	 */
	public QualificationType getQualificationtype() {
		return qualificationtype;
	}

	/**
	 * Sets the qualificationtype.
	 *
	 * @param qualificationtype the new qualificationtype
	 */
	public void setQualificationtype(QualificationType qualificationtype) {
		this.qualificationtype = qualificationtype;
	}

	/**
	 * Gets the qualification typefiltered list.
	 *
	 * @return the qualification typefiltered list
	 */
	public List<QualificationType> getQualificationTypefilteredList() {
		return qualificationtypefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param qualificationtypefilteredList            the new qualificationtypefilteredList list
	 * @see QualificationType
	 */
	public void setQualificationTypefilteredList(List<QualificationType> qualificationtypefilteredList) {
		this.qualificationtypefilteredList = qualificationtypefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<QualificationType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<QualificationType> dataModel) {
		this.dataModel = dataModel;
	}

}
