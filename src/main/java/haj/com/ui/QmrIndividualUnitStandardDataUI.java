package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrIndividualUnitStandardData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrIndividualUnitStandardDataService;

@ManagedBean(name = "qmrIndividualUnitStandardDataUI")

@ViewScoped
public class QmrIndividualUnitStandardDataUI extends AbstractUI {

	private QmrIndividualUnitStandardDataService service = new QmrIndividualUnitStandardDataService();
	private List<QmrIndividualUnitStandardData> qmrIndividualUnitStandardDataList = null;
	private List<QmrIndividualUnitStandardData> qmrIndividualUnitStandardDatafilteredList = null;
	private QmrIndividualUnitStandardData qmrIndividualUnitStandardData = null;
	private LazyDataModel<QmrIndividualUnitStandardData> dataModel; 

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
	 * Initialize method to get all QmrIndividualUnitStandardData and prepare a for a create of a new QmrIndividualUnitStandardData
 	 * @author TechFinium 
 	 * @see    QmrIndividualUnitStandardData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrIndividualUnitStandardDataInfo();
	}

	/**
	 * Get all QmrIndividualUnitStandardData for data table
 	 * @author TechFinium 
 	 * @see    QmrIndividualUnitStandardData
 	 */
	public void qmrIndividualUnitStandardDataInfo() {
//			dataModel = new QmrIndividualUnitStandardDataDatamodel();
	}

	/**
	 * Insert QmrIndividualUnitStandardData into database
 	 * @author TechFinium 
 	 * @see    QmrIndividualUnitStandardData
 	 */
	public void qmrIndividualUnitStandardDataInsert() {
		try {
				 service.create(this.qmrIndividualUnitStandardData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrIndividualUnitStandardDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrIndividualUnitStandardData in database
 	 * @author TechFinium 
 	 * @see    QmrIndividualUnitStandardData
 	 */
    public void qmrIndividualUnitStandardDataUpdate() {
		try {
				 service.update(this.qmrIndividualUnitStandardData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrIndividualUnitStandardDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrIndividualUnitStandardData from database
 	 * @author TechFinium 
 	 * @see    QmrIndividualUnitStandardData
 	 */
	public void qmrIndividualUnitStandardDataDelete() {
		try {
			 service.delete(this.qmrIndividualUnitStandardData);
			  prepareNew();
			 qmrIndividualUnitStandardDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrIndividualUnitStandardData 
 	 * @author TechFinium 
 	 * @see    QmrIndividualUnitStandardData
 	 */
	public void prepareNew() {
		qmrIndividualUnitStandardData = new QmrIndividualUnitStandardData();
	}

/*
    public List<SelectItem> getQmrIndividualUnitStandardDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrIndividualUnitStandardDataInfo();
    	for (QmrIndividualUnitStandardData ug : qmrIndividualUnitStandardDataList) {
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
    public List<QmrIndividualUnitStandardData> complete(String desc) {
		List<QmrIndividualUnitStandardData> l = null;
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
    
    public List<QmrIndividualUnitStandardData> getQmrIndividualUnitStandardDataList() {
		return qmrIndividualUnitStandardDataList;
	}
	public void setQmrIndividualUnitStandardDataList(List<QmrIndividualUnitStandardData> qmrIndividualUnitStandardDataList) {
		this.qmrIndividualUnitStandardDataList = qmrIndividualUnitStandardDataList;
	}
	public QmrIndividualUnitStandardData getQmrlearnershipdata() {
		return qmrIndividualUnitStandardData;
	}
	public void setQmrlearnershipdata(QmrIndividualUnitStandardData qmrIndividualUnitStandardData) {
		this.qmrIndividualUnitStandardData = qmrIndividualUnitStandardData;
	}

    public List<QmrIndividualUnitStandardData> getQmrIndividualUnitStandardDatafilteredList() {
		return qmrIndividualUnitStandardDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrIndividualUnitStandardDatafilteredList the new qmrIndividualUnitStandardDatafilteredList list
 	 * @see    QmrIndividualUnitStandardData
 	 */	
	public void setQmrIndividualUnitStandardDatafilteredList(List<QmrIndividualUnitStandardData> qmrIndividualUnitStandardDatafilteredList) {
		this.qmrIndividualUnitStandardDatafilteredList = qmrIndividualUnitStandardDatafilteredList;
	}

	
	public LazyDataModel<QmrIndividualUnitStandardData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrIndividualUnitStandardData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
