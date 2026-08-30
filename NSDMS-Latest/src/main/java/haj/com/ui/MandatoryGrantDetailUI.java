package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.MandatoryGrantDetail;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantDetailService;

@ManagedBean(name = "mandatorygrantdetailUI")
@ViewScoped
public class MandatoryGrantDetailUI extends AbstractUI {

	private MandatoryGrantDetailService service = new MandatoryGrantDetailService();
	private List<MandatoryGrantDetail> mandatorygrantdetailList = null;
	private List<MandatoryGrantDetail> mandatorygrantdetailfilteredList = null;
	private MandatoryGrantDetail mandatorygrantdetail = null;
	private LazyDataModel<MandatoryGrantDetail> dataModel; 

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
	 * Initialize method to get all MandatoryGrantDetail and prepare a for a create of a new MandatoryGrantDetail
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetail
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		mandatorygrantdetailInfo();
	}

	/**
	 * Get all MandatoryGrantDetail for data table
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetail
 	 */
	public void mandatorygrantdetailInfo() {
	 
			
			 dataModel = new LazyDataModel<MandatoryGrantDetail>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MandatoryGrantDetail> retorno = new  ArrayList<MandatoryGrantDetail>();
			   
			   @Override 
			   public List<MandatoryGrantDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMandatoryGrantDetail(MandatoryGrantDetail.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MandatoryGrantDetail.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MandatoryGrantDetail obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MandatoryGrantDetail getRowData(String rowKey) {
			        for(MandatoryGrantDetail obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MandatoryGrantDetail into database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetail
 	 */
	public void mandatorygrantdetailInsert() {
		try {
				 service.create(this.mandatorygrantdetail);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantdetailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MandatoryGrantDetail in database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetail
 	 */
    public void mandatorygrantdetailUpdate() {
		try {
				 service.update(this.mandatorygrantdetail);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantdetailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MandatoryGrantDetail from database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetail
 	 */
	public void mandatorygrantdetailDelete() {
		try {
			 service.delete(this.mandatorygrantdetail);
			  prepareNew();
			 mandatorygrantdetailInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MandatoryGrantDetail 
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetail
 	 */
	public void prepareNew() {
		mandatorygrantdetail = new MandatoryGrantDetail();
	}

/*
    public List<SelectItem> getMandatoryGrantDetailDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	mandatorygrantdetailInfo();
    	for (MandatoryGrantDetail ug : mandatorygrantdetailList) {
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
    public List<MandatoryGrantDetail> complete(String desc) {
		List<MandatoryGrantDetail> l = null;
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
    
    public List<MandatoryGrantDetail> getMandatoryGrantDetailList() {
		return mandatorygrantdetailList;
	}
	public void setMandatoryGrantDetailList(List<MandatoryGrantDetail> mandatorygrantdetailList) {
		this.mandatorygrantdetailList = mandatorygrantdetailList;
	}
	public MandatoryGrantDetail getMandatorygrantdetail() {
		return mandatorygrantdetail;
	}
	public void setMandatorygrantdetail(MandatoryGrantDetail mandatorygrantdetail) {
		this.mandatorygrantdetail = mandatorygrantdetail;
	}

    public List<MandatoryGrantDetail> getMandatoryGrantDetailfilteredList() {
		return mandatorygrantdetailfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param mandatorygrantdetailfilteredList the new mandatorygrantdetailfilteredList list
 	 * @see    MandatoryGrantDetail
 	 */	
	public void setMandatoryGrantDetailfilteredList(List<MandatoryGrantDetail> mandatorygrantdetailfilteredList) {
		this.mandatorygrantdetailfilteredList = mandatorygrantdetailfilteredList;
	}

	
	public LazyDataModel<MandatoryGrantDetail> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrantDetail> dataModel) {
		this.dataModel = dataModel;
	}
	
}
