package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.JasperDownloadTracker;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.JasperDownloadTrackerService;

@ManagedBean(name = "jasperdownloadtrackerUI")
@ViewScoped
public class JasperDownloadTrackerUI extends AbstractUI {

	private JasperDownloadTrackerService service = new JasperDownloadTrackerService();
	private List<JasperDownloadTracker> jasperdownloadtrackerList = null;
	private List<JasperDownloadTracker> jasperdownloadtrackerfilteredList = null;
	private JasperDownloadTracker jasperdownloadtracker = null;
	private LazyDataModel<JasperDownloadTracker> dataModel; 

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
	 * Initialize method to get all JasperDownloadTracker and prepare a for a create of a new JasperDownloadTracker
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		jasperdownloadtrackerInfo();
	}

	/**
	 * Get all JasperDownloadTracker for data table
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
 	 */
	public void jasperdownloadtrackerInfo() {
//			dataModel = new JasperDownloadTrackerDatamodel();
	}

	/**
	 * Insert JasperDownloadTracker into database
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
 	 */
	public void jasperdownloadtrackerInsert() {
		try {
				 service.create(this.jasperdownloadtracker);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 jasperdownloadtrackerInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update JasperDownloadTracker in database
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
 	 */
    public void jasperdownloadtrackerUpdate() {
		try {
				 service.update(this.jasperdownloadtracker);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 jasperdownloadtrackerInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete JasperDownloadTracker from database
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
 	 */
	public void jasperdownloadtrackerDelete() {
		try {
			 service.delete(this.jasperdownloadtracker);
			  prepareNew();
			 jasperdownloadtrackerInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of JasperDownloadTracker 
 	 * @author TechFinium 
 	 * @see    JasperDownloadTracker
 	 */
	public void prepareNew() {
		jasperdownloadtracker = new JasperDownloadTracker();
	}

/*
    public List<SelectItem> getJasperDownloadTrackerDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	jasperdownloadtrackerInfo();
    	for (JasperDownloadTracker ug : jasperdownloadtrackerList) {
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
    public List<JasperDownloadTracker> complete(String desc) {
		List<JasperDownloadTracker> l = null;
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
    
    public List<JasperDownloadTracker> getJasperDownloadTrackerList() {
		return jasperdownloadtrackerList;
	}
	public void setJasperDownloadTrackerList(List<JasperDownloadTracker> jasperdownloadtrackerList) {
		this.jasperdownloadtrackerList = jasperdownloadtrackerList;
	}
	public JasperDownloadTracker getJasperdownloadtracker() {
		return jasperdownloadtracker;
	}
	public void setJasperdownloadtracker(JasperDownloadTracker jasperdownloadtracker) {
		this.jasperdownloadtracker = jasperdownloadtracker;
	}

    public List<JasperDownloadTracker> getJasperDownloadTrackerfilteredList() {
		return jasperdownloadtrackerfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param jasperdownloadtrackerfilteredList the new jasperdownloadtrackerfilteredList list
 	 * @see    JasperDownloadTracker
 	 */	
	public void setJasperDownloadTrackerfilteredList(List<JasperDownloadTracker> jasperdownloadtrackerfilteredList) {
		this.jasperdownloadtrackerfilteredList = jasperdownloadtrackerfilteredList;
	}

	
	public LazyDataModel<JasperDownloadTracker> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<JasperDownloadTracker> dataModel) {
		this.dataModel = dataModel;
	}
	
}
