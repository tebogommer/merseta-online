package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantRecommendationService;

@ManagedBean(name = "mandatorygrantrecommendationUI")
@ViewScoped
public class MandatoryGrantRecommendationUI extends AbstractUI {

	private MandatoryGrantRecommendationService service = new MandatoryGrantRecommendationService();
	private List<MandatoryGrantRecommendation> mandatorygrantrecommendationList = null;
	private List<MandatoryGrantRecommendation> mandatorygrantrecommendationfilteredList = null;
	private MandatoryGrantRecommendation mandatorygrantrecommendation = null;
	private LazyDataModel<MandatoryGrantRecommendation> dataModel; 

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
	 * Initialize method to get all MandatoryGrantRecommendation and prepare a for a create of a new MandatoryGrantRecommendation
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		mandatorygrantrecommendationInfo();
	}

	/**
	 * Get all MandatoryGrantRecommendation for data table
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
 	 */
	public void mandatorygrantrecommendationInfo() {
	 
			
			 dataModel = new LazyDataModel<MandatoryGrantRecommendation>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MandatoryGrantRecommendation> retorno = new  ArrayList<MandatoryGrantRecommendation>();
			   
			   @Override 
			   public List<MandatoryGrantRecommendation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMandatoryGrantRecommendation(MandatoryGrantRecommendation.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MandatoryGrantRecommendation.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MandatoryGrantRecommendation obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MandatoryGrantRecommendation getRowData(String rowKey) {
			        for(MandatoryGrantRecommendation obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MandatoryGrantRecommendation into database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
 	 */
	public void mandatorygrantrecommendationInsert() {
		try {
				 service.create(this.mandatorygrantrecommendation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantrecommendationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MandatoryGrantRecommendation in database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
 	 */
    public void mandatorygrantrecommendationUpdate() {
		try {
				 service.update(this.mandatorygrantrecommendation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantrecommendationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MandatoryGrantRecommendation from database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
 	 */
	public void mandatorygrantrecommendationDelete() {
		try {
			 service.delete(this.mandatorygrantrecommendation);
			  prepareNew();
			 mandatorygrantrecommendationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MandatoryGrantRecommendation 
 	 * @author TechFinium 
 	 * @see    MandatoryGrantRecommendation
 	 */
	public void prepareNew() {
		mandatorygrantrecommendation = new MandatoryGrantRecommendation();
	}

/*
    public List<SelectItem> getMandatoryGrantRecommendationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	mandatorygrantrecommendationInfo();
    	for (MandatoryGrantRecommendation ug : mandatorygrantrecommendationList) {
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
    public List<MandatoryGrantRecommendation> complete(String desc) {
		List<MandatoryGrantRecommendation> l = null;
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
    
    public List<MandatoryGrantRecommendation> getMandatoryGrantRecommendationList() {
		return mandatorygrantrecommendationList;
	}
	public void setMandatoryGrantRecommendationList(List<MandatoryGrantRecommendation> mandatorygrantrecommendationList) {
		this.mandatorygrantrecommendationList = mandatorygrantrecommendationList;
	}
	public MandatoryGrantRecommendation getMandatorygrantrecommendation() {
		return mandatorygrantrecommendation;
	}
	public void setMandatorygrantrecommendation(MandatoryGrantRecommendation mandatorygrantrecommendation) {
		this.mandatorygrantrecommendation = mandatorygrantrecommendation;
	}

    public List<MandatoryGrantRecommendation> getMandatoryGrantRecommendationfilteredList() {
		return mandatorygrantrecommendationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param mandatorygrantrecommendationfilteredList the new mandatorygrantrecommendationfilteredList list
 	 * @see    MandatoryGrantRecommendation
 	 */	
	public void setMandatoryGrantRecommendationfilteredList(List<MandatoryGrantRecommendation> mandatorygrantrecommendationfilteredList) {
		this.mandatorygrantrecommendationfilteredList = mandatorygrantrecommendationfilteredList;
	}

	
	public LazyDataModel<MandatoryGrantRecommendation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrantRecommendation> dataModel) {
		this.dataModel = dataModel;
	}
	
}
