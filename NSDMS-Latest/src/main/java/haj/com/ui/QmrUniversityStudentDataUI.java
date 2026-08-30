package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrUniversityStudentData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrUniversityStudentDataService;

@ManagedBean(name = "qmrUniversityStudentDataUI")

@ViewScoped
public class QmrUniversityStudentDataUI extends AbstractUI {

	private QmrUniversityStudentDataService service = new QmrUniversityStudentDataService();
	private List<QmrUniversityStudentData> qmrUniversityStudentDataList = null;
	private List<QmrUniversityStudentData> qmrUniversityStudentDatafilteredList = null;
	private QmrUniversityStudentData qmrUniversityStudentData = null;
	private LazyDataModel<QmrUniversityStudentData> dataModel; 

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
	 * Initialize method to get all QmrUniversityStudentData and prepare a for a create of a new QmrUniversityStudentData
 	 * @author TechFinium 
 	 * @see    QmrUniversityStudentData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrUniversityStudentDataInfo();
	}

	/**
	 * Get all QmrUniversityStudentData for data table
 	 * @author TechFinium 
 	 * @see    QmrUniversityStudentData
 	 */
	public void qmrUniversityStudentDataInfo() {
//			dataModel = new QmrUniversityStudentDataDatamodel();
	}

	/**
	 * Insert QmrUniversityStudentData into database
 	 * @author TechFinium 
 	 * @see    QmrUniversityStudentData
 	 */
	public void qmrUniversityStudentDataInsert() {
		try {
				 service.create(this.qmrUniversityStudentData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrUniversityStudentDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrUniversityStudentData in database
 	 * @author TechFinium 
 	 * @see    QmrUniversityStudentData
 	 */
    public void qmrUniversityStudentDataUpdate() {
		try {
				 service.update(this.qmrUniversityStudentData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrUniversityStudentDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrUniversityStudentData from database
 	 * @author TechFinium 
 	 * @see    QmrUniversityStudentData
 	 */
	public void qmrUniversityStudentDataDelete() {
		try {
			 service.delete(this.qmrUniversityStudentData);
			  prepareNew();
			 qmrUniversityStudentDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrUniversityStudentData 
 	 * @author TechFinium 
 	 * @see    QmrUniversityStudentData
 	 */
	public void prepareNew() {
		qmrUniversityStudentData = new QmrUniversityStudentData();
	}

/*
    public List<SelectItem> getQmrUniversityStudentDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrUniversityStudentDataInfo();
    	for (QmrUniversityStudentData ug : qmrUniversityStudentDataList) {
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
    public List<QmrUniversityStudentData> complete(String desc) {
		List<QmrUniversityStudentData> l = null;
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
    
    public List<QmrUniversityStudentData> getQmrUniversityStudentDataList() {
		return qmrUniversityStudentDataList;
	}
	public void setQmrUniversityStudentDataList(List<QmrUniversityStudentData> qmrUniversityStudentDataList) {
		this.qmrUniversityStudentDataList = qmrUniversityStudentDataList;
	}
	public QmrUniversityStudentData getQmrlearnershipdata() {
		return qmrUniversityStudentData;
	}
	public void setQmrlearnershipdata(QmrUniversityStudentData qmrUniversityStudentData) {
		this.qmrUniversityStudentData = qmrUniversityStudentData;
	}

    public List<QmrUniversityStudentData> getQmrUniversityStudentDatafilteredList() {
		return qmrUniversityStudentDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrUniversityStudentDatafilteredList the new qmrUniversityStudentDatafilteredList list
 	 * @see    QmrUniversityStudentData
 	 */	
	public void setQmrUniversityStudentDatafilteredList(List<QmrUniversityStudentData> qmrUniversityStudentDatafilteredList) {
		this.qmrUniversityStudentDatafilteredList = qmrUniversityStudentDatafilteredList;
	}

	
	public LazyDataModel<QmrUniversityStudentData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrUniversityStudentData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
