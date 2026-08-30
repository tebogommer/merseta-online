package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrCandidacyProgrammeData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrCandidacyProgrammeDataService;

@ManagedBean(name = "qmrCandidacyProgrammeDataUI")

@ViewScoped
public class QmrCandidacyProgrammeDataUI extends AbstractUI {

	private QmrCandidacyProgrammeDataService service = new QmrCandidacyProgrammeDataService();
	private List<QmrCandidacyProgrammeData> qmrCandicacyProgrammeDataList = null;
	private List<QmrCandidacyProgrammeData> qmrCandicacyProgrammeDatafilteredList = null;
	private QmrCandidacyProgrammeData qmrCandicacyProgrammeData = null;
	private LazyDataModel<QmrCandidacyProgrammeData> dataModel; 

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
	 * Initialize method to get all QmrCandidacyProgrammeData and prepare a for a create of a new QmrCandidacyProgrammeData
 	 * @author TechFinium 
 	 * @see    QmrCandidacyProgrammeData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrCandicacyProgrammeDataInfo();
	}

	/**
	 * Get all QmrCandidacyProgrammeData for data table
 	 * @author TechFinium 
 	 * @see    QmrCandidacyProgrammeData
 	 */
	public void qmrCandicacyProgrammeDataInfo() {
//			dataModel = new QmrCandidacyProgrammeDataDatamodel();
	}

	/**
	 * Insert QmrCandidacyProgrammeData into database
 	 * @author TechFinium 
 	 * @see    QmrCandidacyProgrammeData
 	 */
	public void qmrCandicacyProgrammeDataInsert() {
		try {
				 service.create(this.qmrCandicacyProgrammeData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrCandicacyProgrammeDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrCandidacyProgrammeData in database
 	 * @author TechFinium 
 	 * @see    QmrCandidacyProgrammeData
 	 */
    public void qmrCandicacyProgrammeDataUpdate() {
		try {
				 service.update(this.qmrCandicacyProgrammeData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrCandicacyProgrammeDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrCandidacyProgrammeData from database
 	 * @author TechFinium 
 	 * @see    QmrCandidacyProgrammeData
 	 */
	public void qmrCandicacyProgrammeDataDelete() {
		try {
			 service.delete(this.qmrCandicacyProgrammeData);
			  prepareNew();
			 qmrCandicacyProgrammeDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrCandidacyProgrammeData 
 	 * @author TechFinium 
 	 * @see    QmrCandidacyProgrammeData
 	 */
	public void prepareNew() {
		qmrCandicacyProgrammeData = new QmrCandidacyProgrammeData();
	}

/*
    public List<SelectItem> getQmrCandidacyProgrammeDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrCandicacyProgrammeDataInfo();
    	for (QmrCandidacyProgrammeData ug : qmrCandicacyProgrammeDataList) {
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
    public List<QmrCandidacyProgrammeData> complete(String desc) {
		List<QmrCandidacyProgrammeData> l = null;
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
    
    public List<QmrCandidacyProgrammeData> getQmrCandidacyProgrammeDataList() {
		return qmrCandicacyProgrammeDataList;
	}
	public void setQmrCandidacyProgrammeDataList(List<QmrCandidacyProgrammeData> qmrCandicacyProgrammeDataList) {
		this.qmrCandicacyProgrammeDataList = qmrCandicacyProgrammeDataList;
	}
	public QmrCandidacyProgrammeData getQmrlearnershipdata() {
		return qmrCandicacyProgrammeData;
	}
	public void setQmrlearnershipdata(QmrCandidacyProgrammeData qmrCandicacyProgrammeData) {
		this.qmrCandicacyProgrammeData = qmrCandicacyProgrammeData;
	}

    public List<QmrCandidacyProgrammeData> getQmrCandidacyProgrammeDatafilteredList() {
		return qmrCandicacyProgrammeDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrCandicacyProgrammeDatafilteredList the new qmrCandicacyProgrammeDatafilteredList list
 	 * @see    QmrCandidacyProgrammeData
 	 */	
	public void setQmrCandidacyProgrammeDatafilteredList(List<QmrCandidacyProgrammeData> qmrCandicacyProgrammeDatafilteredList) {
		this.qmrCandicacyProgrammeDatafilteredList = qmrCandicacyProgrammeDatafilteredList;
	}

	
	public LazyDataModel<QmrCandidacyProgrammeData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrCandidacyProgrammeData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
