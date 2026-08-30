package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EnrolmentStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusUI.
 */
@ManagedBean(name = "enrolmentstatusUI")
@ViewScoped
public class EnrolmentStatusUI extends AbstractUI {

	/** The service. */
	private EnrolmentStatusService service = new EnrolmentStatusService();
	
	/** The enrolmentstatus list. */
	private List<EnrolmentStatus> enrolmentstatusList = null;
	
	/** The enrolmentstatusfiltered list. */
	private List<EnrolmentStatus> enrolmentstatusfilteredList = null;
	
	/** The enrolmentstatus. */
	private EnrolmentStatus enrolmentstatus = null;
	
	/** The data model. */
	private LazyDataModel<EnrolmentStatus> dataModel;

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
	 * Initialize method to get all EnrolmentStatus and prepare a for a create
	 * of a new EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see EnrolmentStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		enrolmentstatusInfo();
	}

	/**
	 * Get all EnrolmentStatus for data table.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatus
	 */
	public void enrolmentstatusInfo() {

		dataModel = new LazyDataModel<EnrolmentStatus>() {

			private static final long serialVersionUID = 1L;
			private List<EnrolmentStatus> retorno = new ArrayList<EnrolmentStatus>();

			@Override
			public List<EnrolmentStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allEnrolmentStatus(EnrolmentStatus.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(EnrolmentStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(EnrolmentStatus obj) {
				return obj.getId();
			}

			@Override
			public EnrolmentStatus getRowData(String rowKey) {
				for (EnrolmentStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert EnrolmentStatus into database.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatus
	 */
	public void enrolmentstatusInsert() {
		try {
			service.create(this.enrolmentstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			enrolmentstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update EnrolmentStatus in database.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatus
	 */
	public void enrolmentstatusUpdate() {
		try {
			service.update(this.enrolmentstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			enrolmentstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete EnrolmentStatus from database.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatus
	 */
	public void enrolmentstatusDelete() {
		try {
			service.delete(this.enrolmentstatus);
			prepareNew();
			enrolmentstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @see EnrolmentStatus
	 */
	public void prepareNew() {
		enrolmentstatus = new EnrolmentStatus();
	}

	/*
	 * public List<SelectItem> getEnrolmentStatusDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * enrolmentstatusInfo(); for (EnrolmentStatus ug : enrolmentstatusList) {
	 * // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<EnrolmentStatus> complete(String desc) {
		List<EnrolmentStatus> l = null;
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
	 * Gets the enrolment status list.
	 *
	 * @return the enrolment status list
	 */
	public List<EnrolmentStatus> getEnrolmentStatusList() {
		return enrolmentstatusList;
	}

	/**
	 * Sets the enrolment status list.
	 *
	 * @param enrolmentstatusList the new enrolment status list
	 */
	public void setEnrolmentStatusList(List<EnrolmentStatus> enrolmentstatusList) {
		this.enrolmentstatusList = enrolmentstatusList;
	}

	/**
	 * Gets the enrolmentstatus.
	 *
	 * @return the enrolmentstatus
	 */
	public EnrolmentStatus getEnrolmentstatus() {
		return enrolmentstatus;
	}

	/**
	 * Sets the enrolmentstatus.
	 *
	 * @param enrolmentstatus the new enrolmentstatus
	 */
	public void setEnrolmentstatus(EnrolmentStatus enrolmentstatus) {
		this.enrolmentstatus = enrolmentstatus;
	}

	/**
	 * Gets the enrolment statusfiltered list.
	 *
	 * @return the enrolment statusfiltered list
	 */
	public List<EnrolmentStatus> getEnrolmentStatusfilteredList() {
		return enrolmentstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param enrolmentstatusfilteredList            the new enrolmentstatusfilteredList list
	 * @see EnrolmentStatus
	 */
	public void setEnrolmentStatusfilteredList(List<EnrolmentStatus> enrolmentstatusfilteredList) {
		this.enrolmentstatusfilteredList = enrolmentstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<EnrolmentStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<EnrolmentStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
