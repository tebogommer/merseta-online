package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.CompanyLearnersRatio;
import haj.com.service.CompanyLearnersRatioService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "companylearnersratioUI")
@ViewScoped
public class CompanyLearnersRatioUI extends AbstractUI {

	private CompanyLearnersRatioService service = new CompanyLearnersRatioService();
	private List<CompanyLearnersRatio> companylearnersratioList = null;
	private List<CompanyLearnersRatio> companylearnersratiofilteredList = null;
	private CompanyLearnersRatio companylearnersratio = null;
	private LazyDataModel<CompanyLearnersRatio> dataModel; 

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
	 * Initialize method to get all CompanyLearnersRatio and prepare a for a create of a new CompanyLearnersRatio
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		companylearnersratioInfo();
	}

	/**
	 * Get all CompanyLearnersRatio for data table
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
 	 */
	public void companylearnersratioInfo() {
			//dataModel = new CompanyLearnersRatioDatamodel();
	}

	/**
	 * Insert CompanyLearnersRatio into database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
 	 */
	public void companylearnersratioInsert() {
		try {
				 service.create(this.companylearnersratio);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersratioInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyLearnersRatio in database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
 	 */
    public void companylearnersratioUpdate() {
		try {
				 service.update(this.companylearnersratio);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersratioInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyLearnersRatio from database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
 	 */
	public void companylearnersratioDelete() {
		try {
			 service.delete(this.companylearnersratio);
			  prepareNew();
			 companylearnersratioInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyLearnersRatio 
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
 	 */
	public void prepareNew() {
		companylearnersratio = new CompanyLearnersRatio();
	}

/*
    public List<SelectItem> getCompanyLearnersRatioDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companylearnersratioInfo();
    	for (CompanyLearnersRatio ug : companylearnersratioList) {
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
    public List<CompanyLearnersRatio> complete(String desc) {
		List<CompanyLearnersRatio> l = null;
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
    
    public List<CompanyLearnersRatio> getCompanyLearnersRatioList() {
		return companylearnersratioList;
	}
	public void setCompanyLearnersRatioList(List<CompanyLearnersRatio> companylearnersratioList) {
		this.companylearnersratioList = companylearnersratioList;
	}
	public CompanyLearnersRatio getCompanylearnersratio() {
		return companylearnersratio;
	}
	public void setCompanylearnersratio(CompanyLearnersRatio companylearnersratio) {
		this.companylearnersratio = companylearnersratio;
	}

    public List<CompanyLearnersRatio> getCompanyLearnersRatiofilteredList() {
		return companylearnersratiofilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param companylearnersratiofilteredList the new companylearnersratiofilteredList list
 	 * @see    CompanyLearnersRatio
 	 */	
	public void setCompanyLearnersRatiofilteredList(List<CompanyLearnersRatio> companylearnersratiofilteredList) {
		this.companylearnersratiofilteredList = companylearnersratiofilteredList;
	}

	
	public LazyDataModel<CompanyLearnersRatio> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersRatio> dataModel) {
		this.dataModel = dataModel;
	}
	
}
