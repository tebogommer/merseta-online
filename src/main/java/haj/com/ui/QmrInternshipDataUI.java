package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrInternshipData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrInternshipDataService;

@ManagedBean(name = "qmrInternshipDataUI")

@ViewScoped
public class QmrInternshipDataUI extends AbstractUI {

	private QmrInternshipDataService service = new QmrInternshipDataService();
	private List<QmrInternshipData> qmrInternshipDataList = null;
	private List<QmrInternshipData> qmrInternshipDatafilteredList = null;
	private QmrInternshipData qmrInternshipData = null;
	private LazyDataModel<QmrInternshipData> dataModel; 

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
	 * Initialize method to get all QmrInternshipData and prepare a for a create of a new QmrInternshipData
 	 * @author TechFinium 
 	 * @see    QmrInternshipData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrInternshipDataInfo();
	}

	/**
	 * Get all QmrInternshipData for data table
 	 * @author TechFinium 
 	 * @see    QmrInternshipData
 	 */
	public void qmrInternshipDataInfo() {
//			dataModel = new QmrInternshipDataDatamodel();
	}

	/**
	 * Insert QmrInternshipData into database
 	 * @author TechFinium 
 	 * @see    QmrInternshipData
 	 */
	public void qmrInternshipDataInsert() {
		try {
				 service.create(this.qmrInternshipData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrInternshipDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrInternshipData in database
 	 * @author TechFinium 
 	 * @see    QmrInternshipData
 	 */
    public void qmrInternshipDataUpdate() {
		try {
				 service.update(this.qmrInternshipData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrInternshipDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrInternshipData from database
 	 * @author TechFinium 
 	 * @see    QmrInternshipData
 	 */
	public void qmrInternshipDataDelete() {
		try {
			 service.delete(this.qmrInternshipData);
			  prepareNew();
			 qmrInternshipDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrInternshipData 
 	 * @author TechFinium 
 	 * @see    QmrInternshipData
 	 */
	public void prepareNew() {
		qmrInternshipData = new QmrInternshipData();
	}

/*
    public List<SelectItem> getQmrInternshipDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrInternshipDataInfo();
    	for (QmrInternshipData ug : qmrInternshipDataList) {
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
    public List<QmrInternshipData> complete(String desc) {
		List<QmrInternshipData> l = null;
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
    
    public List<QmrInternshipData> getQmrInternshipDataList() {
		return qmrInternshipDataList;
	}
	public void setQmrInternshipDataList(List<QmrInternshipData> qmrInternshipDataList) {
		this.qmrInternshipDataList = qmrInternshipDataList;
	}
	public QmrInternshipData getQmrlearnershipdata() {
		return qmrInternshipData;
	}
	public void setQmrlearnershipdata(QmrInternshipData qmrInternshipData) {
		this.qmrInternshipData = qmrInternshipData;
	}

    public List<QmrInternshipData> getQmrInternshipDatafilteredList() {
		return qmrInternshipDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrInternshipDatafilteredList the new qmrInternshipDatafilteredList list
 	 * @see    QmrInternshipData
 	 */	
	public void setQmrInternshipDatafilteredList(List<QmrInternshipData> qmrInternshipDatafilteredList) {
		this.qmrInternshipDatafilteredList = qmrInternshipDatafilteredList;
	}

	
	public LazyDataModel<QmrInternshipData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrInternshipData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
