package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.QmrArtisanData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.QmrArtisanDataService;

@ManagedBean(name = "qmrArtisanDataUI")
@ViewScoped
public class QmrArtisanDataUI extends AbstractUI {

	private QmrArtisanDataService service = new QmrArtisanDataService();
	private List<QmrArtisanData> qmrArtisanDataList = null;
	private List<QmrArtisanData> qmrArtisanDatafilteredList = null;
	private QmrArtisanData qmrArtisanData = null;
	private LazyDataModel<QmrArtisanData> dataModel; 

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
	 * Initialize method to get all QmrArtisanData and prepare a for a create of a new QmrArtisanData
 	 * @author TechFinium 
 	 * @see    QmrArtisanData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qmrArtisanDataInfo();
	}

	/**
	 * Get all QmrArtisanData for data table
 	 * @author TechFinium 
 	 * @see    QmrArtisanData
 	 */
	public void qmrArtisanDataInfo() {
//			dataModel = new QmrArtisanDataDatamodel();
	}

	/**
	 * Insert QmrArtisanData into database
 	 * @author TechFinium 
 	 * @see    QmrArtisanData
 	 */
	public void qmrArtisanDataInsert() {
		try {
				 service.create(this.qmrArtisanData);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrArtisanDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QmrArtisanData in database
 	 * @author TechFinium 
 	 * @see    QmrArtisanData
 	 */
    public void qmrArtisanDataUpdate() {
		try {
				 service.update(this.qmrArtisanData);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qmrArtisanDataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QmrArtisanData from database
 	 * @author TechFinium 
 	 * @see    QmrArtisanData
 	 */
	public void qmrArtisanDataDelete() {
		try {
			 service.delete(this.qmrArtisanData);
			  prepareNew();
			 qmrArtisanDataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QmrArtisanData 
 	 * @author TechFinium 
 	 * @see    QmrArtisanData
 	 */
	public void prepareNew() {
		qmrArtisanData = new QmrArtisanData();
	}

/*
    public List<SelectItem> getQmrArtisanDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qmrArtisanDataInfo();
    	for (QmrArtisanData ug : qmrArtisanDataList) {
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
    public List<QmrArtisanData> complete(String desc) {
		List<QmrArtisanData> l = null;
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
    
    public List<QmrArtisanData> getQmrArtisanDataList() {
		return qmrArtisanDataList;
	}
	public void setQmrArtisanDataList(List<QmrArtisanData> qmrArtisanDataList) {
		this.qmrArtisanDataList = qmrArtisanDataList;
	}
	public QmrArtisanData getQmrlearnershipdata() {
		return qmrArtisanData;
	}
	public void setQmrlearnershipdata(QmrArtisanData qmrArtisanData) {
		this.qmrArtisanData = qmrArtisanData;
	}

    public List<QmrArtisanData> getQmrArtisanDatafilteredList() {
		return qmrArtisanDatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qmrArtisanDatafilteredList the new qmrArtisanDatafilteredList list
 	 * @see    QmrArtisanData
 	 */	
	public void setQmrArtisanDatafilteredList(List<QmrArtisanData> qmrArtisanDatafilteredList) {
		this.qmrArtisanDatafilteredList = qmrArtisanDatafilteredList;
	}

	
	public LazyDataModel<QmrArtisanData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QmrArtisanData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
