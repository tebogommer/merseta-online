package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrSkillsProgrammeData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrSkillsProgrammeDataService;

@ManagedBean(name = "qmrSkillsProgrammeDataUI")

@ViewScoped
public class QmrSkillsProgrammeDataUI extends AbstractUI {

	private QmrSkillsProgrammeDataService service = new QmrSkillsProgrammeDataService();
	private List<QmrSkillsProgrammeData> qmrSkillsProgrammeDataList = null;
	private List<QmrSkillsProgrammeData> qmrSkillsProgrammeDatafilteredList = null;
	private QmrSkillsProgrammeData qmrSkillsProgrammeData = null;
	private LazyDataModel<QmrSkillsProgrammeData> dataModel; 

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
	 * Initialize method to get all QmrSkillsProgrammeData and prepare a for a create of a new QmrSkillsProgrammeData
 	 * @author TechFinium 
 	 * @see    QmrSkillsProgrammeData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrSkillsProgrammeDataInfo();
	}

	/**
	 * Get all QmrSkillsProgrammeData for data table
 	 * @author TechFinium 
 	 * @see    QmrSkillsProgrammeData
 	 */
	public void qmrSkillsProgrammeDataInfo() {
//			dataModel = new QmrSkillsProgrammeDataDatamodel();
	}

	/**
	 * Insert QmrSkillsProgrammeData into database
 	 * @author TechFinium 
 	 * @see    QmrSkillsProgrammeData
 	 */
	public void qmrSkillsProgrammeDataInsert() {
		try {
				 service.create(this.qmrSkillsProgrammeData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrSkillsProgrammeDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrSkillsProgrammeData in database
 	 * @author TechFinium 
 	 * @see    QmrSkillsProgrammeData
 	 */
    public void qmrSkillsProgrammeDataUpdate() {
		try {
				 service.update(this.qmrSkillsProgrammeData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrSkillsProgrammeDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrSkillsProgrammeData from database
 	 * @author TechFinium 
 	 * @see    QmrSkillsProgrammeData
 	 */
	public void qmrSkillsProgrammeDataDelete() {
		try {
			 service.delete(this.qmrSkillsProgrammeData);
			  prepareNew();
			 qmrSkillsProgrammeDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrSkillsProgrammeData 
 	 * @author TechFinium 
 	 * @see    QmrSkillsProgrammeData
 	 */
	public void prepareNew() {
		qmrSkillsProgrammeData = new QmrSkillsProgrammeData();
	}

/*
    public List<SelectItem> getQmrSkillsProgrammeDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrSkillsProgrammeDataInfo();
    	for (QmrSkillsProgrammeData ug : qmrSkillsProgrammeDataList) {
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
    public List<QmrSkillsProgrammeData> complete(String desc) {
		List<QmrSkillsProgrammeData> l = null;
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
    
    public List<QmrSkillsProgrammeData> getQmrSkillsProgrammeDataList() {
		return qmrSkillsProgrammeDataList;
	}
	public void setQmrSkillsProgrammeDataList(List<QmrSkillsProgrammeData> qmrSkillsProgrammeDataList) {
		this.qmrSkillsProgrammeDataList = qmrSkillsProgrammeDataList;
	}
	public QmrSkillsProgrammeData getQmrlearnershipdata() {
		return qmrSkillsProgrammeData;
	}
	public void setQmrlearnershipdata(QmrSkillsProgrammeData qmrSkillsProgrammeData) {
		this.qmrSkillsProgrammeData = qmrSkillsProgrammeData;
	}

    public List<QmrSkillsProgrammeData> getQmrSkillsProgrammeDatafilteredList() {
		return qmrSkillsProgrammeDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrSkillsProgrammeDatafilteredList the new qmrSkillsProgrammeDatafilteredList list
 	 * @see    QmrSkillsProgrammeData
 	 */	
	public void setQmrSkillsProgrammeDatafilteredList(List<QmrSkillsProgrammeData> qmrSkillsProgrammeDatafilteredList) {
		this.qmrSkillsProgrammeDatafilteredList = qmrSkillsProgrammeDatafilteredList;
	}

	
	public LazyDataModel<QmrSkillsProgrammeData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrSkillsProgrammeData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
