package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.CollectDetail;
import haj.com.service.CollectDetailService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "collectdetailUI")
@ViewScoped
public class CollectDetailUI extends AbstractUI {

	private CollectDetailService service = new CollectDetailService();
	private List<CollectDetail> collectdetailList = null;
	private List<CollectDetail> collectdetailfilteredList = null;
	private CollectDetail collectdetail = null;
	private LazyDataModel<CollectDetail> dataModel; 

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
	 * Initialize method to get all CollectDetail and prepare a for a create of a new CollectDetail
 	 * @author TechFinium 
 	 * @see    CollectDetail
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		collectdetailInfo();
	}

	/**
	 * Get all CollectDetail for data table
 	 * @author TechFinium 
 	 * @see    CollectDetail
 	 */
	public void collectdetailInfo() {
			//dataModel = new CollectDetailDatamodel();
	}

	/**
	 * Insert CollectDetail into database
 	 * @author TechFinium 
 	 * @see    CollectDetail
 	 */
	public void collectdetailInsert() {
		try {
				 service.create(this.collectdetail);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 collectdetailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CollectDetail in database
 	 * @author TechFinium 
 	 * @see    CollectDetail
 	 */
    public void collectdetailUpdate() {
		try {
				 service.update(this.collectdetail);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 collectdetailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CollectDetail from database
 	 * @author TechFinium 
 	 * @see    CollectDetail
 	 */
	public void collectdetailDelete() {
		try {
			 service.delete(this.collectdetail);
			  prepareNew();
			 collectdetailInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CollectDetail 
 	 * @author TechFinium 
 	 * @see    CollectDetail
 	 */
	public void prepareNew() {
		collectdetail = new CollectDetail();
	}

/*
    public List<SelectItem> getCollectDetailDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	collectdetailInfo();
    	for (CollectDetail ug : collectdetailList) {
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
    public List<CollectDetail> complete(String desc) {
		List<CollectDetail> l = null;
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
    
    public List<CollectDetail> getCollectDetailList() {
		return collectdetailList;
	}
	public void setCollectDetailList(List<CollectDetail> collectdetailList) {
		this.collectdetailList = collectdetailList;
	}
	public CollectDetail getCollectdetail() {
		return collectdetail;
	}
	public void setCollectdetail(CollectDetail collectdetail) {
		this.collectdetail = collectdetail;
	}

    public List<CollectDetail> getCollectDetailfilteredList() {
		return collectdetailfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param collectdetailfilteredList the new collectdetailfilteredList list
 	 * @see    CollectDetail
 	 */	
	public void setCollectDetailfilteredList(List<CollectDetail> collectdetailfilteredList) {
		this.collectdetailfilteredList = collectdetailfilteredList;
	}

	
	public LazyDataModel<CollectDetail> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CollectDetail> dataModel) {
		this.dataModel = dataModel;
	}
	
}
