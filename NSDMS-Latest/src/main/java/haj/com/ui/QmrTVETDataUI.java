package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrTVETData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrTVETDataService;

@ManagedBean(name = "qmrTVETDataUI")

@ViewScoped
public class QmrTVETDataUI extends AbstractUI {

	private QmrTVETDataService service = new QmrTVETDataService();
	private List<QmrTVETData> qmrTVETDataList = null;
	private List<QmrTVETData> qmrTVETDatafilteredList = null;
	private QmrTVETData qmrTVETData = null;
	private LazyDataModel<QmrTVETData> dataModel; 

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
	 * Initialize method to get all QmrTVETData and prepare a for a create of a new QmrTVETData
 	 * @author TechFinium 
 	 * @see    QmrTVETData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrTVETDataInfo();
	}

	/**
	 * Get all QmrTVETData for data table
 	 * @author TechFinium 
 	 * @see    QmrTVETData
 	 */
	public void qmrTVETDataInfo() {
//			dataModel = new QmrTVETDataDatamodel();
	}

	/**
	 * Insert QmrTVETData into database
 	 * @author TechFinium 
 	 * @see    QmrTVETData
 	 */
	public void qmrTVETDataInsert() {
		try {
				 service.create(this.qmrTVETData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrTVETDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrTVETData in database
 	 * @author TechFinium 
 	 * @see    QmrTVETData
 	 */
    public void qmrTVETDataUpdate() {
		try {
				 service.update(this.qmrTVETData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrTVETDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrTVETData from database
 	 * @author TechFinium 
 	 * @see    QmrTVETData
 	 */
	public void qmrTVETDataDelete() {
		try {
			 service.delete(this.qmrTVETData);
			  prepareNew();
			 qmrTVETDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrTVETData 
 	 * @author TechFinium 
 	 * @see    QmrTVETData
 	 */
	public void prepareNew() {
		qmrTVETData = new QmrTVETData();
	}

/*
    public List<SelectItem> getQmrTVETDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrTVETDataInfo();
    	for (QmrTVETData ug : qmrTVETDataList) {
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
    public List<QmrTVETData> complete(String desc) {
		List<QmrTVETData> l = null;
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
    
    public List<QmrTVETData> getQmrTVETDataList() {
		return qmrTVETDataList;
	}
	public void setQmrTVETDataList(List<QmrTVETData> qmrTVETDataList) {
		this.qmrTVETDataList = qmrTVETDataList;
	}
	public QmrTVETData getQmrlearnershipdata() {
		return qmrTVETData;
	}
	public void setQmrlearnershipdata(QmrTVETData qmrTVETData) {
		this.qmrTVETData = qmrTVETData;
	}

    public List<QmrTVETData> getQmrTVETDatafilteredList() {
		return qmrTVETDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrTVETDatafilteredList the new qmrTVETDatafilteredList list
 	 * @see    QmrTVETData
 	 */	
	public void setQmrTVETDatafilteredList(List<QmrTVETData> qmrTVETDatafilteredList) {
		this.qmrTVETDatafilteredList = qmrTVETDatafilteredList;
	}

	
	public LazyDataModel<QmrTVETData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrTVETData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
