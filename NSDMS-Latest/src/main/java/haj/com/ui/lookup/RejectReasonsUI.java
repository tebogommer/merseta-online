package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RejectReasonsService;

// TODO: Auto-generated Javadoc
/**
 * The Class RejectReasonsUI.
 */
@ManagedBean(name = "rejectreasonsUI")
@ViewScoped
public class RejectReasonsUI extends AbstractUI {

	/** The service. */
	private RejectReasonsService service = new RejectReasonsService();
	
	/** The rejectreasons list. */
	private List<RejectReasons> rejectreasonsList = null;
	
	/** The rejectreasonsfiltered list. */
	private List<RejectReasons> rejectreasonsfilteredList = null;
	
	/** The rejectreasons. */
	private RejectReasons rejectreasons = null;
	
	/** The data model. */
	private LazyDataModel<RejectReasons> dataModel;

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
	 * Initialize method to get all RejectReasons and prepare a for a create of a
	 * new RejectReasons.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see RejectReasons
	 */
	private void runInit() throws Exception {
		prepareNew();
		rejectreasonsInfo();
	}

	/**
	 * Get all RejectReasons for data table.
	 *
	 * @author TechFinium
	 * @see RejectReasons
	 */
	public void rejectreasonsInfo() {

		dataModel = new LazyDataModel<RejectReasons>() {

			private static final long serialVersionUID = 1L;
			private List<RejectReasons> retorno = new ArrayList<RejectReasons>();

			@Override
			public List<RejectReasons> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allRejectReasons(RejectReasons.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(RejectReasons.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(RejectReasons obj) {
				return obj.getId();
			}

			@Override
			public RejectReasons getRowData(String rowKey) {
				for (RejectReasons obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert RejectReasons into database.
	 *
	 * @author TechFinium
	 * @see RejectReasons
	 */
	public void rejectreasonsInsert() {
		try {
			if (rejectreasons.getSoftDeleted() == null) {
				rejectreasons.setSoftDeleted(false);
			}
			service.create(this.rejectreasons);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			rejectreasonsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update RejectReasons in database.
	 *
	 * @author TechFinium
	 * @see RejectReasons
	 */
	public void rejectreasonsUpdate() {
		try {
			service.update(this.rejectreasons);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			rejectreasonsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete RejectReasons from database.
	 *
	 * @author TechFinium
	 * @see RejectReasons
	 */
	public void rejectreasonsDelete() {
		try {
			service.delete(this.rejectreasons);
			prepareNew();
			rejectreasonsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted "));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void softDeleteEntry(){
		try {
			rejectreasons.setSoftDeleted(true);
			service.update(this.rejectreasons);
			prepareNew();
			rejectreasonsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.soft.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeSoftDelete(){
		try {
			rejectreasons.setSoftDeleted(false);
			service.update(this.rejectreasons);
			prepareNew();
			rejectreasonsInfo();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of RejectReasons.
	 *
	 * @author TechFinium
	 * @see RejectReasons
	 */
	public void prepareNew() {
		rejectreasons = new RejectReasons();
		rejectreasons.setForCrm(false);
		rejectreasons.setSoftDeleted(false);
	}

	/*
	 * public List<SelectItem> getRejectReasonsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * rejectreasonsInfo(); for (RejectReasons ug : rejectreasonsList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<RejectReasons> complete(String desc) {
		List<RejectReasons> l = null;
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
	 * Gets the reject reasons list.
	 *
	 * @return the reject reasons list
	 */
	public List<RejectReasons> getRejectReasonsList() {
		return rejectreasonsList;
	}

	/**
	 * Sets the reject reasons list.
	 *
	 * @param rejectreasonsList the new reject reasons list
	 */
	public void setRejectReasonsList(List<RejectReasons> rejectreasonsList) {
		this.rejectreasonsList = rejectreasonsList;
	}

	/**
	 * Gets the rejectreasons.
	 *
	 * @return the rejectreasons
	 */
	public RejectReasons getRejectreasons() {
		return rejectreasons;
	}

	/**
	 * Sets the rejectreasons.
	 *
	 * @param rejectreasons the new rejectreasons
	 */
	public void setRejectreasons(RejectReasons rejectreasons) {
		this.rejectreasons = rejectreasons;
	}

	/**
	 * Gets the reject reasonsfiltered list.
	 *
	 * @return the reject reasonsfiltered list
	 */
	public List<RejectReasons> getRejectReasonsfilteredList() {
		return rejectreasonsfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param rejectreasonsfilteredList            the new rejectreasonsfilteredList list
	 * @see RejectReasons
	 */
	public void setRejectReasonsfilteredList(List<RejectReasons> rejectreasonsfilteredList) {
		this.rejectreasonsfilteredList = rejectreasonsfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<RejectReasons> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<RejectReasons> dataModel) {
		this.dataModel = dataModel;
	}

}
