package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrLearnershipData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrLearnershipDataService;

@ManagedBean(name = "qmrlearnershipdataUI")
@ViewScoped
public class QmrLearnershipDataUI extends AbstractUI {

	private QmrLearnershipDataService service = new QmrLearnershipDataService();
	private List<QmrLearnershipData> qmrlearnershipdataList = null;
	private List<QmrLearnershipData> qmrlearnershipdatafilteredList = null;
	private QmrLearnershipData qmrlearnershipdata = null;
	private LazyDataModel<QmrLearnershipData> dataModel; 

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
	 * Initialize method to get all QmrLearnershipData and prepare a for a create of a new QmrLearnershipData
 	 * @author TechFinium 
 	 * @see    QmrLearnershipData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrlearnershipdataInfo();
	}

	/**
	 * Get all QmrLearnershipData for data table
 	 * @author TechFinium 
 	 * @see    QmrLearnershipData
 	 */
	public void qmrlearnershipdataInfo() {
//			dataModel = new QmrLearnershipDataDatamodel();
	}

	/**
	 * Insert QmrLearnershipData into database
 	 * @author TechFinium 
 	 * @see    QmrLearnershipData
 	 */
	public void qmrlearnershipdataInsert() {
		try {
				 service.create(this.qmrlearnershipdata);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrlearnershipdataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrLearnershipData in database
 	 * @author TechFinium 
 	 * @see    QmrLearnershipData
 	 */
    public void qmrlearnershipdataUpdate() {
		try {
				 service.update(this.qmrlearnershipdata);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrlearnershipdataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrLearnershipData from database
 	 * @author TechFinium 
 	 * @see    QmrLearnershipData
 	 */
	public void qmrlearnershipdataDelete() {
		try {
			 service.delete(this.qmrlearnershipdata);
			  prepareNew();
			 qmrlearnershipdataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrLearnershipData 
 	 * @author TechFinium 
 	 * @see    QmrLearnershipData
 	 */
	public void prepareNew() {
		qmrlearnershipdata = new QmrLearnershipData();
	}

/*
    public List<SelectItem> getQmrLearnershipDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrlearnershipdataInfo();
    	for (QmrLearnershipData ug : qmrlearnershipdataList) {
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
    public List<QmrLearnershipData> complete(String desc) {
		List<QmrLearnershipData> l = null;
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
    
    public List<QmrLearnershipData> getQmrLearnershipDataList() {
		return qmrlearnershipdataList;
	}
	public void setQmrLearnershipDataList(List<QmrLearnershipData> qmrlearnershipdataList) {
		this.qmrlearnershipdataList = qmrlearnershipdataList;
	}
	public QmrLearnershipData getQmrlearnershipdata() {
		return qmrlearnershipdata;
	}
	public void setQmrlearnershipdata(QmrLearnershipData qmrlearnershipdata) {
		this.qmrlearnershipdata = qmrlearnershipdata;
	}

    public List<QmrLearnershipData> getQmrLearnershipDatafilteredList() {
		return qmrlearnershipdatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrlearnershipdatafilteredList the new qmrlearnershipdatafilteredList list
 	 * @see    QmrLearnershipData
 	 */	
	public void setQmrLearnershipDatafilteredList(List<QmrLearnershipData> qmrlearnershipdatafilteredList) {
		this.qmrlearnershipdatafilteredList = qmrlearnershipdatafilteredList;
	}

	
	public LazyDataModel<QmrLearnershipData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrLearnershipData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
