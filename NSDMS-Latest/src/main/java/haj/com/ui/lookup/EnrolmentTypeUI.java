package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.EnrolmentType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EnrolmentTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentTypeUI.
 */
@ManagedBean(name = "enrolmenttypeUI")
@ViewScoped
public class EnrolmentTypeUI extends AbstractUI {

	/** The service. */
	private EnrolmentTypeService service = new EnrolmentTypeService();
	
	/** The enrolmenttype list. */
	private List<EnrolmentType> enrolmenttypeList = null;
	
	/** The enrolmenttypefiltered list. */
	private List<EnrolmentType> enrolmenttypefilteredList = null;
	
	/** The enrolmenttype. */
	private EnrolmentType enrolmenttype = null;
	
	/** The data model. */
	private LazyDataModel<EnrolmentType> dataModel;

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
	 * Initialize method to get all EnrolmentType and prepare a for a create of
	 * a new EnrolmentType.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see EnrolmentType
	 */
	private void runInit() throws Exception {
		prepareNew();
		enrolmenttypeInfo();
	}

	/**
	 * Get all EnrolmentType for data table.
	 *
	 * @author TechFinium
	 * @see EnrolmentType
	 */
	public void enrolmenttypeInfo() {

		dataModel = new LazyDataModel<EnrolmentType>() {

			private static final long serialVersionUID = 1L;
			private List<EnrolmentType> retorno = new ArrayList<EnrolmentType>();

			@Override
			public List<EnrolmentType> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allEnrolmentType(EnrolmentType.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(EnrolmentType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(EnrolmentType obj) {
				return obj.getId();
			}

			@Override
			public EnrolmentType getRowData(String rowKey) {
				for (EnrolmentType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert EnrolmentType into database.
	 *
	 * @author TechFinium
	 * @see EnrolmentType
	 */
	public void enrolmenttypeInsert() {
		try {
			service.create(this.enrolmenttype);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			enrolmenttypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update EnrolmentType in database.
	 *
	 * @author TechFinium
	 * @see EnrolmentType
	 */
	public void enrolmenttypeUpdate() {
		try {
			service.update(this.enrolmenttype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			enrolmenttypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete EnrolmentType from database.
	 *
	 * @author TechFinium
	 * @see EnrolmentType
	 */
	public void enrolmenttypeDelete() {
		try {
			service.delete(this.enrolmenttype);
			prepareNew();
			enrolmenttypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of EnrolmentType.
	 *
	 * @author TechFinium
	 * @see EnrolmentType
	 */
	public void prepareNew() {
		enrolmenttype = new EnrolmentType();
	}

	/*
	 * public List<SelectItem> getEnrolmentTypeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * enrolmenttypeInfo(); for (EnrolmentType ug : enrolmenttypeList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<EnrolmentType> complete(String desc) {
		List<EnrolmentType> l = null;
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
	 * Gets the enrolment type list.
	 *
	 * @return the enrolment type list
	 */
	public List<EnrolmentType> getEnrolmentTypeList() {
		return enrolmenttypeList;
	}

	/**
	 * Sets the enrolment type list.
	 *
	 * @param enrolmenttypeList the new enrolment type list
	 */
	public void setEnrolmentTypeList(List<EnrolmentType> enrolmenttypeList) {
		this.enrolmenttypeList = enrolmenttypeList;
	}

	/**
	 * Gets the enrolmenttype.
	 *
	 * @return the enrolmenttype
	 */
	public EnrolmentType getEnrolmenttype() {
		return enrolmenttype;
	}

	/**
	 * Sets the enrolmenttype.
	 *
	 * @param enrolmenttype the new enrolmenttype
	 */
	public void setEnrolmenttype(EnrolmentType enrolmenttype) {
		this.enrolmenttype = enrolmenttype;
	}

	/**
	 * Gets the enrolment typefiltered list.
	 *
	 * @return the enrolment typefiltered list
	 */
	public List<EnrolmentType> getEnrolmentTypefilteredList() {
		return enrolmenttypefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param enrolmenttypefilteredList            the new enrolmenttypefilteredList list
	 * @see EnrolmentType
	 */
	public void setEnrolmentTypefilteredList(List<EnrolmentType> enrolmenttypefilteredList) {
		this.enrolmenttypefilteredList = enrolmenttypefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<EnrolmentType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<EnrolmentType> dataModel) {
		this.dataModel = dataModel;
	}

}
