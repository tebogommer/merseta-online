package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrAETProgrammeData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrAETProgrammeDataService;

@ManagedBean(name = "qmrAetProgrammeDataUI")
@ViewScoped
public class QmrAETProgrammeDataUI extends AbstractUI {

	private QmrAETProgrammeDataService service = new QmrAETProgrammeDataService();
	private List<QmrAETProgrammeData> qmrAETProgrammeDataList = null;
	private List<QmrAETProgrammeData> qmrAETProgrammeDatafilteredList = null;
	private QmrAETProgrammeData qmrAETProgrammeData = null;
	private LazyDataModel<QmrAETProgrammeData> dataModel; 

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
	 * Initialize method to get all QmrAETProgrammeData and prepare a for a create of a new QmrAETProgrammeData
 	 * @author TechFinium 
 	 * @see    QmrAETProgrammeData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrAETProgrammeDataInfo();
	}

	/**
	 * Get all QmrAETProgrammeData for data table
 	 * @author TechFinium 
 	 * @see    QmrAETProgrammeData
 	 */
	public void qmrAETProgrammeDataInfo() {
//			dataModel = new QmrAETProgrammeDataDatamodel();
	}

	/**
	 * Insert QmrAETProgrammeData into database
 	 * @author TechFinium 
 	 * @see    QmrAETProgrammeData
 	 */
	public void qmrAETProgrammeDataInsert() {
		try {
				 service.create(this.qmrAETProgrammeData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrAETProgrammeDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrAETProgrammeData in database
 	 * @author TechFinium 
 	 * @see    QmrAETProgrammeData
 	 */
    public void qmrAETProgrammeDataUpdate() {
		try {
				 service.update(this.qmrAETProgrammeData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrAETProgrammeDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrAETProgrammeData from database
 	 * @author TechFinium 
 	 * @see    QmrAETProgrammeData
 	 */
	public void qmrAETProgrammeDataDelete() {
		try {
			 service.delete(this.qmrAETProgrammeData);
			  prepareNew();
			 qmrAETProgrammeDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrAETProgrammeData 
 	 * @author TechFinium 
 	 * @see    QmrAETProgrammeData
 	 */
	public void prepareNew() {
		qmrAETProgrammeData = new QmrAETProgrammeData();
	}

/*
    public List<SelectItem> getQmrAETProgrammeDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrAETProgrammeDataInfo();
    	for (QmrAETProgrammeData ug : qmrAETProgrammeDataList) {
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
    public List<QmrAETProgrammeData> complete(String desc) {
		List<QmrAETProgrammeData> l = null;
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
    
    public List<QmrAETProgrammeData> getQmrAETProgrammeDataList() {
		return qmrAETProgrammeDataList;
	}
	public void setQmrAETProgrammeDataList(List<QmrAETProgrammeData> qmrAETProgrammeDataList) {
		this.qmrAETProgrammeDataList = qmrAETProgrammeDataList;
	}
	public QmrAETProgrammeData getQmrlearnershipdata() {
		return qmrAETProgrammeData;
	}
	public void setQmrlearnershipdata(QmrAETProgrammeData qmrAETProgrammeData) {
		this.qmrAETProgrammeData = qmrAETProgrammeData;
	}

    public List<QmrAETProgrammeData> getQmrAETProgrammeDatafilteredList() {
		return qmrAETProgrammeDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrAETProgrammeDatafilteredList the new qmrAETProgrammeDatafilteredList list
 	 * @see    QmrAETProgrammeData
 	 */	
	public void setQmrAETProgrammeDatafilteredList(List<QmrAETProgrammeData> qmrAETProgrammeDatafilteredList) {
		this.qmrAETProgrammeDatafilteredList = qmrAETProgrammeDatafilteredList;
	}

	
	public LazyDataModel<QmrAETProgrammeData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrAETProgrammeData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
