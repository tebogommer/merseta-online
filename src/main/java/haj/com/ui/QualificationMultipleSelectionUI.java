package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QualificationMultipleSelection;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QualificationMultipleSelectionService;

@ManagedBean(name = "qualificationmultipleselectionUI")
@ViewScoped
public class QualificationMultipleSelectionUI extends AbstractUI {

	private QualificationMultipleSelectionService service = new QualificationMultipleSelectionService();
	private List<QualificationMultipleSelection> qualificationmultipleselectionList = null;
	private List<QualificationMultipleSelection> qualificationmultipleselectionfilteredList = null;
	private QualificationMultipleSelection qualificationmultipleselection = null;
	private LazyDataModel<QualificationMultipleSelection> dataModel; 

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
	 * Initialize method to get all QualificationMultipleSelection and prepare a for a create of a new QualificationMultipleSelection
 	 * @author TechFinium 
 	 * @see    QualificationMultipleSelection
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qualificationmultipleselectionInfo();
	}

	/**
	 * Get all QualificationMultipleSelection for data table
 	 * @author TechFinium 
 	 * @see    QualificationMultipleSelection
 	 */
	public void qualificationmultipleselectionInfo() {
//			dataModel = new QualificationMultipleSelectionDatamodel();
	}

	/**
	 * Insert QualificationMultipleSelection into database
 	 * @author TechFinium 
 	 * @see    QualificationMultipleSelection
 	 */
	public void qualificationmultipleselectionInsert() {
		try {
				 service.create(this.qualificationmultipleselection);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationmultipleselectionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QualificationMultipleSelection in database
 	 * @author TechFinium 
 	 * @see    QualificationMultipleSelection
 	 */
    public void qualificationmultipleselectionUpdate() {
		try {
				 service.update(this.qualificationmultipleselection);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationmultipleselectionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QualificationMultipleSelection from database
 	 * @author TechFinium 
 	 * @see    QualificationMultipleSelection
 	 */
	public void qualificationmultipleselectionDelete() {
		try {
			 service.delete(this.qualificationmultipleselection);
			  prepareNew();
			 qualificationmultipleselectionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QualificationMultipleSelection 
 	 * @author TechFinium 
 	 * @see    QualificationMultipleSelection
 	 */
	public void prepareNew() {
		qualificationmultipleselection = new QualificationMultipleSelection();
	}

/*
    public List<SelectItem> getQualificationMultipleSelectionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qualificationmultipleselectionInfo();
    	for (QualificationMultipleSelection ug : qualificationmultipleselectionList) {
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
    public List<QualificationMultipleSelection> complete(String desc) {
		List<QualificationMultipleSelection> l = null;
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
    
    public List<QualificationMultipleSelection> getQualificationMultipleSelectionList() {
		return qualificationmultipleselectionList;
	}
	public void setQualificationMultipleSelectionList(List<QualificationMultipleSelection> qualificationmultipleselectionList) {
		this.qualificationmultipleselectionList = qualificationmultipleselectionList;
	}
	public QualificationMultipleSelection getQualificationmultipleselection() {
		return qualificationmultipleselection;
	}
	public void setQualificationmultipleselection(QualificationMultipleSelection qualificationmultipleselection) {
		this.qualificationmultipleselection = qualificationmultipleselection;
	}

    public List<QualificationMultipleSelection> getQualificationMultipleSelectionfilteredList() {
		return qualificationmultipleselectionfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qualificationmultipleselectionfilteredList the new qualificationmultipleselectionfilteredList list
 	 * @see    QualificationMultipleSelection
 	 */	
	public void setQualificationMultipleSelectionfilteredList(List<QualificationMultipleSelection> qualificationmultipleselectionfilteredList) {
		this.qualificationmultipleselectionfilteredList = qualificationmultipleselectionfilteredList;
	}

	
	public LazyDataModel<QualificationMultipleSelection> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QualificationMultipleSelection> dataModel) {
		this.dataModel = dataModel;
	}
	
}
