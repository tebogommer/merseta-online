package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.HostingCompanyDepartmentProcess;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.HostingCompanyProcess;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyDepartmentProcessService;
import haj.com.service.HostingCompanyDepartmentsService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentProcessUI.
 */
@ManagedBean(name = "hostingcompanydepartmentprocessUI")
@ViewScoped
public class HostingCompanyDepartmentProcessUI extends AbstractUI {

	/** The service. */
	private HostingCompanyDepartmentProcessService service = new HostingCompanyDepartmentProcessService();
	
	/** The hostingcompanydepartmentprocess list. */
	private List<HostingCompanyDepartmentProcess> hostingcompanydepartmentprocessList = null;
	
	/** The hostingcompanydepartmentprocessfiltered list. */
	private List<HostingCompanyDepartmentProcess> hostingcompanydepartmentprocessfilteredList = null;
	
	/** The hostingcompanydepartmentprocess. */
	private HostingCompanyDepartmentProcess hostingcompanydepartmentprocess = null;
    
    /** The hosting company departments. */
    private HostingCompanyDepartments hostingCompanyDepartments;
    
    /** The hosting company departments service. */
    private HostingCompanyDepartmentsService hostingCompanyDepartmentsService = new HostingCompanyDepartmentsService();
    
    /** The processes. */
    private  List<HostingCompanyProcess> processes;
    
    /** The data model. */
    private LazyDataModel<HostingCompanyDepartmentProcess> dataModel;

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
	 * Initialize method to get all HostingCompanyDepartmentProcess and prepare a for a create of a new HostingCompanyDepartmentProcess.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	private void runInit() throws Exception {
		
		if (super.getParameter("id", false) != null) {
			hostingCompanyDepartments = hostingCompanyDepartmentsService.findByKey( Long.valueOf(""+super.getParameter("id", false)) );
		}
		prepareNew();
		hostingcompanydepartmentprocessInfo();
		hostingCompanyDepartmentProcessInfo();
	}

	/**
	 * Get all HostingCompanyDepartmentProcess for data table.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	public void hostingcompanydepartmentprocessInfo() throws Exception {
	       
       this.hostingcompanydepartmentprocessList = service.findByDepartment(hostingCompanyDepartments);
	
	}

	/**
	 * Insert HostingCompanyDepartmentProcess into database.
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentProcess
	 */
	public void hostingcompanydepartmentprocessInsert() {
		try {
				 service.create(this.hostingcompanydepartmentprocess);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 hostingcompanydepartmentprocessInfo();
				 hostingCompanyDepartmentProcessInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update HostingCompanyDepartmentProcess in database.
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentProcess
	 */
    public void hostingcompanydepartmentprocessUpdate() {
		try {
				 service.update(this.hostingcompanydepartmentprocess);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 hostingcompanydepartmentprocessInfo();
				 hostingCompanyDepartmentProcessInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete HostingCompanyDepartmentProcess from database.
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentProcess
	 */
	public void hostingcompanydepartmentprocessDelete() {
		try {
			 service.delete(this.hostingcompanydepartmentprocess);
			  prepareNew();
			 hostingcompanydepartmentprocessInfo();
			 hostingCompanyDepartmentProcessInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of HostingCompanyDepartmentProcess .
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	public void prepareNew() throws Exception {
		hostingcompanydepartmentprocess = new HostingCompanyDepartmentProcess();
		hostingcompanydepartmentprocess.setHostingCompanyDepartments(hostingCompanyDepartments);
		this.processes = service.findNotUsed(hostingCompanyDepartments);
	}

/*
    public List<SelectItem> getHostingCompanyDepartmentProcessDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	hostingcompanydepartmentprocessInfo();
    	for (HostingCompanyDepartmentProcess ug : hostingcompanydepartmentprocessList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<HostingCompanyDepartmentProcess> complete(String desc) {
		List<HostingCompanyDepartmentProcess> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the hostingcompanydepartmentprocess list.
	 *
	 * @return the hostingcompanydepartmentprocess list
	 */
	public List<HostingCompanyDepartmentProcess> getHostingcompanydepartmentprocessList() {
		return hostingcompanydepartmentprocessList;
	}

	/**
	 * Sets the hostingcompanydepartmentprocess list.
	 *
	 * @param hostingcompanydepartmentprocessList the new hostingcompanydepartmentprocess list
	 */
	public void setHostingcompanydepartmentprocessList(
			List<HostingCompanyDepartmentProcess> hostingcompanydepartmentprocessList) {
		this.hostingcompanydepartmentprocessList = hostingcompanydepartmentprocessList;
	}

	/**
	 * Gets the hostingcompanydepartmentprocessfiltered list.
	 *
	 * @return the hostingcompanydepartmentprocessfiltered list
	 */
	public List<HostingCompanyDepartmentProcess> getHostingcompanydepartmentprocessfilteredList() {
		return hostingcompanydepartmentprocessfilteredList;
	}

	/**
	 * Sets the hostingcompanydepartmentprocessfiltered list.
	 *
	 * @param hostingcompanydepartmentprocessfilteredList the new hostingcompanydepartmentprocessfiltered list
	 */
	public void setHostingcompanydepartmentprocessfilteredList(
			List<HostingCompanyDepartmentProcess> hostingcompanydepartmentprocessfilteredList) {
		this.hostingcompanydepartmentprocessfilteredList = hostingcompanydepartmentprocessfilteredList;
	}

	/**
	 * Gets the hostingcompanydepartmentprocess.
	 *
	 * @return the hostingcompanydepartmentprocess
	 */
	public HostingCompanyDepartmentProcess getHostingcompanydepartmentprocess() {
		return hostingcompanydepartmentprocess;
	}

	/**
	 * Sets the hostingcompanydepartmentprocess.
	 *
	 * @param hostingcompanydepartmentprocess the new hostingcompanydepartmentprocess
	 */
	public void setHostingcompanydepartmentprocess(HostingCompanyDepartmentProcess hostingcompanydepartmentprocess) {
		this.hostingcompanydepartmentprocess = hostingcompanydepartmentprocess;
	}

	/**
	 * Gets the hosting company departments.
	 *
	 * @return the hosting company departments
	 */
	public HostingCompanyDepartments getHostingCompanyDepartments() {
		return hostingCompanyDepartments;
	}

	/**
	 * Sets the hosting company departments.
	 *
	 * @param hostingCompanyDepartments the new hosting company departments
	 */
	public void setHostingCompanyDepartments(HostingCompanyDepartments hostingCompanyDepartments) {
		this.hostingCompanyDepartments = hostingCompanyDepartments;
	}

	/**
	 * Gets the processes.
	 *
	 * @return the processes
	 */
	public List<HostingCompanyProcess> getProcesses() {
		return processes;
	}

	/**
	 * Sets the processes.
	 *
	 * @param processes the new processes
	 */
	public void setProcesses(List<HostingCompanyProcess> processes) {
		this.processes = processes;
	}
	
	/**
	 * Hosting company department process info.
	 */
	public void hostingCompanyDepartmentProcessInfo() {

		dataModel = new LazyDataModel<HostingCompanyDepartmentProcess>() {

			private static final long serialVersionUID = 1L;
			private List<HostingCompanyDepartmentProcess> retorno = new ArrayList<HostingCompanyDepartmentProcess>();

			@Override
			public List<HostingCompanyDepartmentProcess> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allHostingCompanyDepartmentProcess(HostingCompanyDepartmentProcess.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(HostingCompanyDepartmentProcess.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HostingCompanyDepartmentProcess obj) {
				return obj.getId();
			}

			@Override
			public HostingCompanyDepartmentProcess getRowData(String rowKey) {
				for (HostingCompanyDepartmentProcess obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}


	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<HostingCompanyDepartmentProcess> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<HostingCompanyDepartmentProcess> dataModel) {
		this.dataModel = dataModel;
	}
    
}
