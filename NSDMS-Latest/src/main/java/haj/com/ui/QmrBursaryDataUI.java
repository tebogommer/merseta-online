package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrBursaryData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrBursaryDataService;

@ManagedBean(name = "qmrBursaryDataUI")

@ViewScoped
public class QmrBursaryDataUI extends AbstractUI {

	private QmrBursaryDataService service = new QmrBursaryDataService();
	private List<QmrBursaryData> qmrBursaryDataList = null;
	private List<QmrBursaryData> qmrBursaryDatafilteredList = null;
	private QmrBursaryData qmrBursaryData = null;
	private LazyDataModel<QmrBursaryData> dataModel; 

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
	 * Initialize method to get all QmrBursaryData and prepare a for a create of a new QmrBursaryData
 	 * @author TechFinium 
 	 * @see    QmrBursaryData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrBursaryDataInfo();
	}

	/**
	 * Get all QmrBursaryData for data table
 	 * @author TechFinium 
 	 * @see    QmrBursaryData
 	 */
	public void qmrBursaryDataInfo() {
//			dataModel = new QmrBursaryDataDatamodel();
	}

	/**
	 * Insert QmrBursaryData into database
 	 * @author TechFinium 
 	 * @see    QmrBursaryData
 	 */
	public void qmrBursaryDataInsert() {
		try {
				 service.create(this.qmrBursaryData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrBursaryDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrBursaryData in database
 	 * @author TechFinium 
 	 * @see    QmrBursaryData
 	 */
    public void qmrBursaryDataUpdate() {
		try {
				 service.update(this.qmrBursaryData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrBursaryDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrBursaryData from database
 	 * @author TechFinium 
 	 * @see    QmrBursaryData
 	 */
	public void qmrBursaryDataDelete() {
		try {
			 service.delete(this.qmrBursaryData);
			  prepareNew();
			 qmrBursaryDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrBursaryData 
 	 * @author TechFinium 
 	 * @see    QmrBursaryData
 	 */
	public void prepareNew() {
		qmrBursaryData = new QmrBursaryData();
	}

/*
    public List<SelectItem> getQmrBursaryDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrBursaryDataInfo();
    	for (QmrBursaryData ug : qmrBursaryDataList) {
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
    public List<QmrBursaryData> complete(String desc) {
		List<QmrBursaryData> l = null;
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
    
    public List<QmrBursaryData> getQmrBursaryDataList() {
		return qmrBursaryDataList;
	}
	public void setQmrBursaryDataList(List<QmrBursaryData> qmrBursaryDataList) {
		this.qmrBursaryDataList = qmrBursaryDataList;
	}
	public QmrBursaryData getQmrlearnershipdata() {
		return qmrBursaryData;
	}
	public void setQmrlearnershipdata(QmrBursaryData qmrBursaryData) {
		this.qmrBursaryData = qmrBursaryData;
	}

    public List<QmrBursaryData> getQmrBursaryDatafilteredList() {
		return qmrBursaryDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrBursaryDatafilteredList the new qmrBursaryDatafilteredList list
 	 * @see    QmrBursaryData
 	 */	
	public void setQmrBursaryDatafilteredList(List<QmrBursaryData> qmrBursaryDatafilteredList) {
		this.qmrBursaryDatafilteredList = qmrBursaryDatafilteredList;
	}

	
	public LazyDataModel<QmrBursaryData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrBursaryData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
