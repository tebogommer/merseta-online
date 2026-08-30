package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrRPLData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrRPLDataService;

@ManagedBean(name = "qmrRPLDataUI")

@ViewScoped
public class QmrRPLDataUI extends AbstractUI {

	private QmrRPLDataService service = new QmrRPLDataService();
	private List<QmrRPLData> qmrRPLDataList = null;
	private List<QmrRPLData> qmrRPLDatafilteredList = null;
	private QmrRPLData qmrRPLData = null;
	private LazyDataModel<QmrRPLData> dataModel; 

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
	 * Initialize method to get all QmrRPLData and prepare a for a create of a new QmrRPLData
 	 * @author TechFinium 
 	 * @see    QmrRPLData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrRPLDataInfo();
	}

	/**
	 * Get all QmrRPLData for data table
 	 * @author TechFinium 
 	 * @see    QmrRPLData
 	 */
	public void qmrRPLDataInfo() {
//			dataModel = new QmrRPLDataDatamodel();
	}

	/**
	 * Insert QmrRPLData into database
 	 * @author TechFinium 
 	 * @see    QmrRPLData
 	 */
	public void qmrRPLDataInsert() {
		try {
				 service.create(this.qmrRPLData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrRPLDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrRPLData in database
 	 * @author TechFinium 
 	 * @see    QmrRPLData
 	 */
    public void qmrRPLDataUpdate() {
		try {
				 service.update(this.qmrRPLData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrRPLDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrRPLData from database
 	 * @author TechFinium 
 	 * @see    QmrRPLData
 	 */
	public void qmrRPLDataDelete() {
		try {
			 service.delete(this.qmrRPLData);
			  prepareNew();
			 qmrRPLDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrRPLData 
 	 * @author TechFinium 
 	 * @see    QmrRPLData
 	 */
	public void prepareNew() {
		qmrRPLData = new QmrRPLData();
	}

/*
    public List<SelectItem> getQmrRPLDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrRPLDataInfo();
    	for (QmrRPLData ug : qmrRPLDataList) {
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
    public List<QmrRPLData> complete(String desc) {
		List<QmrRPLData> l = null;
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
    
    public List<QmrRPLData> getQmrRPLDataList() {
		return qmrRPLDataList;
	}
	public void setQmrRPLDataList(List<QmrRPLData> qmrRPLDataList) {
		this.qmrRPLDataList = qmrRPLDataList;
	}
	public QmrRPLData getQmrlearnershipdata() {
		return qmrRPLData;
	}
	public void setQmrlearnershipdata(QmrRPLData qmrRPLData) {
		this.qmrRPLData = qmrRPLData;
	}

    public List<QmrRPLData> getQmrRPLDatafilteredList() {
		return qmrRPLDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrRPLDatafilteredList the new qmrRPLDatafilteredList list
 	 * @see    QmrRPLData
 	 */	
	public void setQmrRPLDatafilteredList(List<QmrRPLData> qmrRPLDatafilteredList) {
		this.qmrRPLDatafilteredList = qmrRPLDatafilteredList;
	}

	
	public LazyDataModel<QmrRPLData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrRPLData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
