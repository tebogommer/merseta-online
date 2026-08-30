package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrFinYears;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrFinYearsService;

@ManagedBean(name = "qmrfinyearsUI")
@ViewScoped
public class QmrFinYearsUI extends AbstractUI {

	private QmrFinYearsService service = new QmrFinYearsService();
	private List<QmrFinYears> qmrfinyearsList = null;
	private List<QmrFinYears> qmrfinyearsfilteredList = null;
	private QmrFinYears qmrfinyears = null;
	private LazyDataModel<QmrFinYears> dataModel; 

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
	 * Initialize method to get all QmrFinYears and prepare a for a create of a new QmrFinYears
 	 * @author TechFinium 
 	 * @see    QmrFinYears
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrfinyearsInfo();
	}

	/**
	 * Get all QmrFinYears for data table
 	 * @author TechFinium 
 	 * @see    QmrFinYears
 	 */
	public void qmrfinyearsInfo() {
//		dataModel = new QmrFinYearsDatamodel();
	}

	/**
	 * Insert QmrFinYears into database
 	 * @author TechFinium 
 	 * @see    QmrFinYears
 	 */
	public void qmrfinyearsInsert() {
		try {
				 service.create(this.qmrfinyears);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrfinyearsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrFinYears in database
 	 * @author TechFinium 
 	 * @see    QmrFinYears
 	 */
    public void qmrfinyearsUpdate() {
		try {
				 service.update(this.qmrfinyears);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrfinyearsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrFinYears from database
 	 * @author TechFinium 
 	 * @see    QmrFinYears
 	 */
	public void qmrfinyearsDelete() {
		try {
			 service.delete(this.qmrfinyears);
			  prepareNew();
			 qmrfinyearsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrFinYears 
 	 * @author TechFinium 
 	 * @see    QmrFinYears
 	 */
	public void prepareNew() {
		qmrfinyears = new QmrFinYears();
	}

/*
    public List<SelectItem> getQmrFinYearsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrfinyearsInfo();
    	for (QmrFinYears ug : qmrfinyearsList) {
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
    public List<QmrFinYears> complete(String desc) {
		List<QmrFinYears> l = null;
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
    
    public List<QmrFinYears> getQmrFinYearsList() {
		return qmrfinyearsList;
	}
	public void setQmrFinYearsList(List<QmrFinYears> qmrfinyearsList) {
		this.qmrfinyearsList = qmrfinyearsList;
	}
	public QmrFinYears getQmrfinyears() {
		return qmrfinyears;
	}
	public void setQmrfinyears(QmrFinYears qmrfinyears) {
		this.qmrfinyears = qmrfinyears;
	}

    public List<QmrFinYears> getQmrFinYearsfilteredList() {
		return qmrfinyearsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrfinyearsfilteredList the new qmrfinyearsfilteredList list
 	 * @see    QmrFinYears
 	 */	
	public void setQmrFinYearsfilteredList(List<QmrFinYears> qmrfinyearsfilteredList) {
		this.qmrfinyearsfilteredList = qmrfinyearsfilteredList;
	}

	
	public LazyDataModel<QmrFinYears> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrFinYears> dataModel) {
		this.dataModel = dataModel;
	}
	
}
