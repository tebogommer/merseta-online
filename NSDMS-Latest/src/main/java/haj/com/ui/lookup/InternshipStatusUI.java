package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.InternshipStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.InternshipStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class InternshipStatusUI.
 */
@ManagedBean(name = "internshipstatusUI")
@ViewScoped
public class InternshipStatusUI extends AbstractUI {

	/** The service. */
	private InternshipStatusService service = new InternshipStatusService();
	
	/** The internshipstatus list. */
	private List<InternshipStatus> internshipstatusList = null;
	
	/** The internshipstatusfiltered list. */
	private List<InternshipStatus> internshipstatusfilteredList = null;
	
	/** The internshipstatus. */
	private InternshipStatus internshipstatus = null;
	
	/** The data model. */
	private LazyDataModel<InternshipStatus> dataModel;

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
	 * Initialize method to get all InternshipStatus and prepare a for a create
	 * of a new InternshipStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see InternshipStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		internshipstatusInfo();
	}

	/**
	 * Get all InternshipStatus for data table.
	 *
	 * @author TechFinium
	 * @see InternshipStatus
	 */
	public void internshipstatusInfo() {

		dataModel = new LazyDataModel<InternshipStatus>() {

			private static final long serialVersionUID = 1L;
			private List<InternshipStatus> retorno = new ArrayList<InternshipStatus>();

			@Override
			public List<InternshipStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allInternshipStatus(InternshipStatus.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(InternshipStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(InternshipStatus obj) {
				return obj.getId();
			}

			@Override
			public InternshipStatus getRowData(String rowKey) {
				for (InternshipStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert InternshipStatus into database.
	 *
	 * @author TechFinium
	 * @see InternshipStatus
	 */
	public void internshipstatusInsert() {
		try {
			service.create(this.internshipstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			internshipstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update InternshipStatus in database.
	 *
	 * @author TechFinium
	 * @see InternshipStatus
	 */
	public void internshipstatusUpdate() {
		try {
			service.update(this.internshipstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			internshipstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete InternshipStatus from database.
	 *
	 * @author TechFinium
	 * @see InternshipStatus
	 */
	public void internshipstatusDelete() {
		try {
			service.delete(this.internshipstatus);
			prepareNew();
			internshipstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of InternshipStatus.
	 *
	 * @author TechFinium
	 * @see InternshipStatus
	 */
	public void prepareNew() {
		internshipstatus = new InternshipStatus();
	}

	/*
	 * public List<SelectItem> getInternshipStatusDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * internshipstatusInfo(); for (InternshipStatus ug : internshipstatusList)
	 * { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<InternshipStatus> complete(String desc) {
		List<InternshipStatus> l = null;
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
	 * Gets the internship status list.
	 *
	 * @return the internship status list
	 */
	public List<InternshipStatus> getInternshipStatusList() {
		return internshipstatusList;
	}

	/**
	 * Sets the internship status list.
	 *
	 * @param internshipstatusList the new internship status list
	 */
	public void setInternshipStatusList(List<InternshipStatus> internshipstatusList) {
		this.internshipstatusList = internshipstatusList;
	}

	/**
	 * Gets the internshipstatus.
	 *
	 * @return the internshipstatus
	 */
	public InternshipStatus getInternshipstatus() {
		return internshipstatus;
	}

	/**
	 * Sets the internshipstatus.
	 *
	 * @param internshipstatus the new internshipstatus
	 */
	public void setInternshipstatus(InternshipStatus internshipstatus) {
		this.internshipstatus = internshipstatus;
	}

	/**
	 * Gets the internship statusfiltered list.
	 *
	 * @return the internship statusfiltered list
	 */
	public List<InternshipStatus> getInternshipStatusfilteredList() {
		return internshipstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param internshipstatusfilteredList            the new internshipstatusfilteredList list
	 * @see InternshipStatus
	 */
	public void setInternshipStatusfilteredList(List<InternshipStatus> internshipstatusfilteredList) {
		this.internshipstatusfilteredList = internshipstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<InternshipStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<InternshipStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
