package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DesignationStructureStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.DesignationStructureStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationStructureStatusUI.
 */
@ManagedBean(name = "designationstructurestatusUI")
@ViewScoped
public class DesignationStructureStatusUI extends AbstractUI {

	/** The service. */
	private DesignationStructureStatusService service = new DesignationStructureStatusService();
	
	/** The designationstructurestatus list. */
	private List<DesignationStructureStatus> designationstructurestatusList = null;
	
	/** The designationstructurestatusfiltered list. */
	private List<DesignationStructureStatus> designationstructurestatusfilteredList = null;
	
	/** The designationstructurestatus. */
	private DesignationStructureStatus designationstructurestatus = null;
	
	/** The data model. */
	private LazyDataModel<DesignationStructureStatus> dataModel;

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
	 * Initialize method to get all DesignationStructureStatus and prepare a for
	 * a create of a new DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see DesignationStructureStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		designationstructurestatusInfo();
	}

	/**
	 * Get all DesignationStructureStatus for data table.
	 *
	 * @author TechFinium
	 * @see DesignationStructureStatus
	 */
	public void designationstructurestatusInfo() {

		dataModel = new LazyDataModel<DesignationStructureStatus>() {

			private static final long serialVersionUID = 1L;
			private List<DesignationStructureStatus> retorno = new ArrayList<DesignationStructureStatus>();

			@Override
			public List<DesignationStructureStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allDesignationStructureStatus(DesignationStructureStatus.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(DesignationStructureStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DesignationStructureStatus obj) {
				return obj.getId();
			}

			@Override
			public DesignationStructureStatus getRowData(String rowKey) {
				for (DesignationStructureStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert DesignationStructureStatus into database.
	 *
	 * @author TechFinium
	 * @see DesignationStructureStatus
	 */
	public void designationstructurestatusInsert() {
		try {
			service.create(this.designationstructurestatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			designationstructurestatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DesignationStructureStatus in database.
	 *
	 * @author TechFinium
	 * @see DesignationStructureStatus
	 */
	public void designationstructurestatusUpdate() {
		try {
			service.update(this.designationstructurestatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			designationstructurestatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DesignationStructureStatus from database.
	 *
	 * @author TechFinium
	 * @see DesignationStructureStatus
	 */
	public void designationstructurestatusDelete() {
		try {
			service.delete(this.designationstructurestatus);
			prepareNew();
			designationstructurestatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @see DesignationStructureStatus
	 */
	public void prepareNew() {
		designationstructurestatus = new DesignationStructureStatus();
	}

	/*
	 * public List<SelectItem> getDesignationStructureStatusDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * designationstructurestatusInfo(); for (DesignationStructureStatus ug :
	 * designationstructurestatusList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DesignationStructureStatus> complete(String desc) {
		List<DesignationStructureStatus> l = null;
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
	 * Gets the designation structure status list.
	 *
	 * @return the designation structure status list
	 */
	public List<DesignationStructureStatus> getDesignationStructureStatusList() {
		return designationstructurestatusList;
	}

	/**
	 * Sets the designation structure status list.
	 *
	 * @param designationstructurestatusList the new designation structure status list
	 */
	public void setDesignationStructureStatusList(List<DesignationStructureStatus> designationstructurestatusList) {
		this.designationstructurestatusList = designationstructurestatusList;
	}

	/**
	 * Gets the designationstructurestatus.
	 *
	 * @return the designationstructurestatus
	 */
	public DesignationStructureStatus getDesignationstructurestatus() {
		return designationstructurestatus;
	}

	/**
	 * Sets the designationstructurestatus.
	 *
	 * @param designationstructurestatus the new designationstructurestatus
	 */
	public void setDesignationstructurestatus(DesignationStructureStatus designationstructurestatus) {
		this.designationstructurestatus = designationstructurestatus;
	}

	/**
	 * Gets the designation structure statusfiltered list.
	 *
	 * @return the designation structure statusfiltered list
	 */
	public List<DesignationStructureStatus> getDesignationStructureStatusfilteredList() {
		return designationstructurestatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param designationstructurestatusfilteredList            the new designationstructurestatusfilteredList list
	 * @see DesignationStructureStatus
	 */
	public void setDesignationStructureStatusfilteredList(
			List<DesignationStructureStatus> designationstructurestatusfilteredList) {
		this.designationstructurestatusfilteredList = designationstructurestatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<DesignationStructureStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<DesignationStructureStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
