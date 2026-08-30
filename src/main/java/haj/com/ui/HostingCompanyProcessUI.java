package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.HostingCompanyProcess;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyProcessService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyProcessUI.
 */
@ManagedBean(name = "hostingcompanyprocessUI")
@ViewScoped
public class HostingCompanyProcessUI extends AbstractUI {

	/** The service. */
	private HostingCompanyProcessService service = new HostingCompanyProcessService();
	
	/** The hostingcompanyprocess list. */
	private List<HostingCompanyProcess> hostingcompanyprocessList = null;
	
	/** The hostingcompanyprocessfiltered list. */
	private List<HostingCompanyProcess> hostingcompanyprocessfilteredList = null;
	
	/** The hostingcompanyprocess. */
	private HostingCompanyProcess hostingcompanyprocess = null;
	
	/** The data model. */
	private LazyDataModel<HostingCompanyProcess> dataModel;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all HostingCompanyProcess and prepare a for a
	 * create of a new HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see HostingCompanyProcess
	 */
	private void runInit() throws Exception {
		prepareNew();
		hostingcompanyprocessInfo();
	}

	/**
	 * Get all HostingCompanyProcess for data table.
	 *
	 * @author TechFinium
	 * @see HostingCompanyProcess
	 */
	public void hostingcompanyprocessInfo() {

		dataModel = new LazyDataModel<HostingCompanyProcess>() {

			private static final long serialVersionUID = 1L;
			private List<HostingCompanyProcess> retorno = new ArrayList<HostingCompanyProcess>();

			@Override
			public List<HostingCompanyProcess> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allHostingCompanyProcess(HostingCompanyProcess.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(HostingCompanyProcess.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HostingCompanyProcess obj) {
				return obj.getId();
			}

			@Override
			public HostingCompanyProcess getRowData(String rowKey) {
				for (HostingCompanyProcess obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert HostingCompanyProcess into database.
	 *
	 * @author TechFinium
	 * @see HostingCompanyProcess
	 */
	public void hostingcompanyprocessInsert() {
		try {
			service.create(this.hostingcompanyprocess);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			hostingcompanyprocessInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update HostingCompanyProcess in database.
	 *
	 * @author TechFinium
	 * @see HostingCompanyProcess
	 */
	public void hostingcompanyprocessUpdate() {
		try {
			service.update(this.hostingcompanyprocess);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			hostingcompanyprocessInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete HostingCompanyProcess from database.
	 *
	 * @author TechFinium
	 * @see HostingCompanyProcess
	 */
	public void hostingcompanyprocessDelete() {
		try {
			service.delete(this.hostingcompanyprocess);
			prepareNew();
			hostingcompanyprocessInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @see HostingCompanyProcess
	 */
	public void prepareNew() {
		hostingcompanyprocess = new HostingCompanyProcess();
	}

	/*
	 * public List<SelectItem> getHostingCompanyProcessDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * hostingcompanyprocessInfo(); for (HostingCompanyProcess ug :
	 * hostingcompanyprocessList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<HostingCompanyProcess> complete(String desc) {
		List<HostingCompanyProcess> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the hosting company process list.
	 *
	 * @return the hosting company process list
	 */
	public List<HostingCompanyProcess> getHostingCompanyProcessList() {
		return hostingcompanyprocessList;
	}

	/**
	 * Sets the hosting company process list.
	 *
	 * @param hostingcompanyprocessList the new hosting company process list
	 */
	public void setHostingCompanyProcessList(List<HostingCompanyProcess> hostingcompanyprocessList) {
		this.hostingcompanyprocessList = hostingcompanyprocessList;
	}

	/**
	 * Gets the hostingcompanyprocess.
	 *
	 * @return the hostingcompanyprocess
	 */
	public HostingCompanyProcess getHostingcompanyprocess() {
		return hostingcompanyprocess;
	}

	/**
	 * Sets the hostingcompanyprocess.
	 *
	 * @param hostingcompanyprocess the new hostingcompanyprocess
	 */
	public void setHostingcompanyprocess(HostingCompanyProcess hostingcompanyprocess) {
		this.hostingcompanyprocess = hostingcompanyprocess;
	}

	/**
	 * Gets the hosting company processfiltered list.
	 *
	 * @return the hosting company processfiltered list
	 */
	public List<HostingCompanyProcess> getHostingCompanyProcessfilteredList() {
		return hostingcompanyprocessfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param hostingcompanyprocessfilteredList            the new hostingcompanyprocessfilteredList list
	 * @see HostingCompanyProcess
	 */
	public void setHostingCompanyProcessfilteredList(List<HostingCompanyProcess> hostingcompanyprocessfilteredList) {
		this.hostingcompanyprocessfilteredList = hostingcompanyprocessfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<HostingCompanyProcess> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<HostingCompanyProcess> dataModel) {
		this.dataModel = dataModel;
	}

}
