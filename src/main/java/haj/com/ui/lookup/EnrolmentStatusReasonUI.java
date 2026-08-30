package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.EnrolmentStatusReason;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EnrolmentStatusReasonService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusReasonUI.
 */
@ManagedBean(name = "enrolmentstatusreasonUI")
@ViewScoped
public class EnrolmentStatusReasonUI extends AbstractUI {

	/** The service. */
	private EnrolmentStatusReasonService service = new EnrolmentStatusReasonService();
	
	/** The enrolmentstatusreason list. */
	private List<EnrolmentStatusReason> enrolmentstatusreasonList = null;
	
	/** The enrolmentstatusreasonfiltered list. */
	private List<EnrolmentStatusReason> enrolmentstatusreasonfilteredList = null;
	
	/** The enrolmentstatusreason. */
	private EnrolmentStatusReason enrolmentstatusreason = null;
	
	/** The data model. */
	private LazyDataModel<EnrolmentStatusReason> dataModel;

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
	 * Initialize method to get all EnrolmentStatusReason and prepare a for a
	 * create of a new EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see EnrolmentStatusReason
	 */
	private void runInit() throws Exception {
		prepareNew();
		enrolmentstatusreasonInfo();
	}

	/**
	 * Get all EnrolmentStatusReason for data table.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatusReason
	 */
	public void enrolmentstatusreasonInfo() {

		dataModel = new LazyDataModel<EnrolmentStatusReason>() {

			private static final long serialVersionUID = 1L;
			private List<EnrolmentStatusReason> retorno = new ArrayList<EnrolmentStatusReason>();

			@Override
			public List<EnrolmentStatusReason> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allEnrolmentStatusReason(EnrolmentStatusReason.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(EnrolmentStatusReason.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(EnrolmentStatusReason obj) {
				return obj.getId();
			}

			@Override
			public EnrolmentStatusReason getRowData(String rowKey) {
				for (EnrolmentStatusReason obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert EnrolmentStatusReason into database.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatusReason
	 */
	public void enrolmentstatusreasonInsert() {
		try {
			service.create(this.enrolmentstatusreason);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			enrolmentstatusreasonInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update EnrolmentStatusReason in database.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatusReason
	 */
	public void enrolmentstatusreasonUpdate() {
		try {
			service.update(this.enrolmentstatusreason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			enrolmentstatusreasonInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete EnrolmentStatusReason from database.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatusReason
	 */
	public void enrolmentstatusreasonDelete() {
		try {
			service.delete(this.enrolmentstatusreason);
			prepareNew();
			enrolmentstatusreasonInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatusReason
	 */
	public void prepareNew() {
		enrolmentstatusreason = new EnrolmentStatusReason();
	}

	/*
	 * public List<SelectItem> getEnrolmentStatusReasonDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * enrolmentstatusreasonInfo(); for (EnrolmentStatusReason ug :
	 * enrolmentstatusreasonList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<EnrolmentStatusReason> complete(String desc) {
		List<EnrolmentStatusReason> l = null;
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
	 * Gets the enrolment status reason list.
	 *
	 * @return the enrolment status reason list
	 */
	public List<EnrolmentStatusReason> getEnrolmentStatusReasonList() {
		return enrolmentstatusreasonList;
	}

	/**
	 * Sets the enrolment status reason list.
	 *
	 * @param enrolmentstatusreasonList the new enrolment status reason list
	 */
	public void setEnrolmentStatusReasonList(List<EnrolmentStatusReason> enrolmentstatusreasonList) {
		this.enrolmentstatusreasonList = enrolmentstatusreasonList;
	}

	/**
	 * Gets the enrolmentstatusreason.
	 *
	 * @return the enrolmentstatusreason
	 */
	public EnrolmentStatusReason getEnrolmentstatusreason() {
		return enrolmentstatusreason;
	}

	/**
	 * Sets the enrolmentstatusreason.
	 *
	 * @param enrolmentstatusreason the new enrolmentstatusreason
	 */
	public void setEnrolmentstatusreason(EnrolmentStatusReason enrolmentstatusreason) {
		this.enrolmentstatusreason = enrolmentstatusreason;
	}

	/**
	 * Gets the enrolment status reasonfiltered list.
	 *
	 * @return the enrolment status reasonfiltered list
	 */
	public List<EnrolmentStatusReason> getEnrolmentStatusReasonfilteredList() {
		return enrolmentstatusreasonfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param enrolmentstatusreasonfilteredList            the new enrolmentstatusreasonfilteredList list
	 * @see EnrolmentStatusReason
	 */
	public void setEnrolmentStatusReasonfilteredList(List<EnrolmentStatusReason> enrolmentstatusreasonfilteredList) {
		this.enrolmentstatusreasonfilteredList = enrolmentstatusreasonfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<EnrolmentStatusReason> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<EnrolmentStatusReason> dataModel) {
		this.dataModel = dataModel;
	}

}
