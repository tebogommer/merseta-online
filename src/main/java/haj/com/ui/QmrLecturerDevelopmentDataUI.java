package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrLecturerDevelopmentData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrLecturerDevelopmentDataService;

@ManagedBean(name = "qmrLecturerDevelopmentDataUI")

@ViewScoped
public class QmrLecturerDevelopmentDataUI extends AbstractUI {

	private QmrLecturerDevelopmentDataService service = new QmrLecturerDevelopmentDataService();
	private List<QmrLecturerDevelopmentData> qmrLecturerDevelopmentDataList = null;
	private List<QmrLecturerDevelopmentData> qmrLecturerDevelopmentDatafilteredList = null;
	private QmrLecturerDevelopmentData qmrLecturerDevelopmentData = null;
	private LazyDataModel<QmrLecturerDevelopmentData> dataModel; 

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
	 * Initialize method to get all QmrLecturerDevelopmentData and prepare a for a create of a new QmrLecturerDevelopmentData
 	 * @author TechFinium 
 	 * @see    QmrLecturerDevelopmentData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrLecturerDevelopmentDataInfo();
	}

	/**
	 * Get all QmrLecturerDevelopmentData for data table
 	 * @author TechFinium 
 	 * @see    QmrLecturerDevelopmentData
 	 */
	public void qmrLecturerDevelopmentDataInfo() {
//			dataModel = new QmrLecturerDevelopmentDataDatamodel();
	}

	/**
	 * Insert QmrLecturerDevelopmentData into database
 	 * @author TechFinium 
 	 * @see    QmrLecturerDevelopmentData
 	 */
	public void qmrLecturerDevelopmentDataInsert() {
		try {
				 service.create(this.qmrLecturerDevelopmentData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrLecturerDevelopmentDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrLecturerDevelopmentData in database
 	 * @author TechFinium 
 	 * @see    QmrLecturerDevelopmentData
 	 */
    public void qmrLecturerDevelopmentDataUpdate() {
		try {
				 service.update(this.qmrLecturerDevelopmentData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrLecturerDevelopmentDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrLecturerDevelopmentData from database
 	 * @author TechFinium 
 	 * @see    QmrLecturerDevelopmentData
 	 */
	public void qmrLecturerDevelopmentDataDelete() {
		try {
			 service.delete(this.qmrLecturerDevelopmentData);
			  prepareNew();
			 qmrLecturerDevelopmentDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrLecturerDevelopmentData 
 	 * @author TechFinium 
 	 * @see    QmrLecturerDevelopmentData
 	 */
	public void prepareNew() {
		qmrLecturerDevelopmentData = new QmrLecturerDevelopmentData();
	}

/*
    public List<SelectItem> getQmrLecturerDevelopmentDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrLecturerDevelopmentDataInfo();
    	for (QmrLecturerDevelopmentData ug : qmrLecturerDevelopmentDataList) {
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
    public List<QmrLecturerDevelopmentData> complete(String desc) {
		List<QmrLecturerDevelopmentData> l = null;
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
    
    public List<QmrLecturerDevelopmentData> getQmrLecturerDevelopmentDataList() {
		return qmrLecturerDevelopmentDataList;
	}
	public void setQmrLecturerDevelopmentDataList(List<QmrLecturerDevelopmentData> qmrLecturerDevelopmentDataList) {
		this.qmrLecturerDevelopmentDataList = qmrLecturerDevelopmentDataList;
	}
	public QmrLecturerDevelopmentData getQmrlearnershipdata() {
		return qmrLecturerDevelopmentData;
	}
	public void setQmrlearnershipdata(QmrLecturerDevelopmentData qmrLecturerDevelopmentData) {
		this.qmrLecturerDevelopmentData = qmrLecturerDevelopmentData;
	}

    public List<QmrLecturerDevelopmentData> getQmrLecturerDevelopmentDatafilteredList() {
		return qmrLecturerDevelopmentDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrLecturerDevelopmentDatafilteredList the new qmrLecturerDevelopmentDatafilteredList list
 	 * @see    QmrLecturerDevelopmentData
 	 */	
	public void setQmrLecturerDevelopmentDatafilteredList(List<QmrLecturerDevelopmentData> qmrLecturerDevelopmentDatafilteredList) {
		this.qmrLecturerDevelopmentDatafilteredList = qmrLecturerDevelopmentDatafilteredList;
	}

	
	public LazyDataModel<QmrLecturerDevelopmentData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrLecturerDevelopmentData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
