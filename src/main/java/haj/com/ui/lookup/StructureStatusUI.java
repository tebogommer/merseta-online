package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.StructureStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.StructureStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureStatusUI.
 */
@ManagedBean(name = "structurestatusUI")
@ViewScoped
public class StructureStatusUI extends AbstractUI {

	/** The service. */
	private StructureStatusService service = new StructureStatusService();
	
	/** The structurestatus list. */
	private List<StructureStatus> structurestatusList = null;
	
	/** The structurestatusfiltered list. */
	private List<StructureStatus> structurestatusfilteredList = null;
	
	/** The structurestatus. */
	private StructureStatus structurestatus = null;
	
	/** The data model. */
	private LazyDataModel<StructureStatus> dataModel;

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
	 * Initialize method to get all StructureStatus and prepare a for a create
	 * of a new StructureStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see StructureStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		structurestatusInfo();
	}

	/**
	 * Get all StructureStatus for data table.
	 *
	 * @author TechFinium
	 * @see StructureStatus
	 */
	public void structurestatusInfo() {

		dataModel = new LazyDataModel<StructureStatus>() {

			private static final long serialVersionUID = 1L;
			private List<StructureStatus> retorno = new ArrayList<StructureStatus>();

			@Override
			public List<StructureStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allStructureStatus(StructureStatus.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(StructureStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(StructureStatus obj) {
				return obj.getId();
			}

			@Override
			public StructureStatus getRowData(String rowKey) {
				for (StructureStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert StructureStatus into database.
	 *
	 * @author TechFinium
	 * @see StructureStatus
	 */
	public void structurestatusInsert() {
		try {
			service.create(this.structurestatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			structurestatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update StructureStatus in database.
	 *
	 * @author TechFinium
	 * @see StructureStatus
	 */
	public void structurestatusUpdate() {
		try {
			service.update(this.structurestatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			structurestatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete StructureStatus from database.
	 *
	 * @author TechFinium
	 * @see StructureStatus
	 */
	public void structurestatusDelete() {
		try {
			service.delete(this.structurestatus);
			prepareNew();
			structurestatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of StructureStatus.
	 *
	 * @author TechFinium
	 * @see StructureStatus
	 */
	public void prepareNew() {
		structurestatus = new StructureStatus();
	}

	/*
	 * public List<SelectItem> getStructureStatusDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * structurestatusInfo(); for (StructureStatus ug : structurestatusList) {
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
	public List<StructureStatus> complete(String desc) {
		List<StructureStatus> l = null;
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
	 * Gets the structure status list.
	 *
	 * @return the structure status list
	 */
	public List<StructureStatus> getStructureStatusList() {
		return structurestatusList;
	}

	/**
	 * Sets the structure status list.
	 *
	 * @param structurestatusList the new structure status list
	 */
	public void setStructureStatusList(List<StructureStatus> structurestatusList) {
		this.structurestatusList = structurestatusList;
	}

	/**
	 * Gets the structurestatus.
	 *
	 * @return the structurestatus
	 */
	public StructureStatus getStructurestatus() {
		return structurestatus;
	}

	/**
	 * Sets the structurestatus.
	 *
	 * @param structurestatus the new structurestatus
	 */
	public void setStructurestatus(StructureStatus structurestatus) {
		this.structurestatus = structurestatus;
	}

	/**
	 * Gets the structure statusfiltered list.
	 *
	 * @return the structure statusfiltered list
	 */
	public List<StructureStatus> getStructureStatusfilteredList() {
		return structurestatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param structurestatusfilteredList            the new structurestatusfilteredList list
	 * @see StructureStatus
	 */
	public void setStructureStatusfilteredList(List<StructureStatus> structurestatusfilteredList) {
		this.structurestatusfilteredList = structurestatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<StructureStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<StructureStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
