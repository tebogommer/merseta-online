package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.CompanyLearnersAchievementStatusEISA;
import haj.com.service.CompanyLearnersAchievementStatusEISAService;
import haj.com.entity.datamodel.CompanyLearnersAchievementStatusEISADatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "companylearnersachievementstatuseisaUI")
@ViewScoped
public class CompanyLearnersAchievementStatusEISAUI extends AbstractUI {

	private CompanyLearnersAchievementStatusEISAService service = new CompanyLearnersAchievementStatusEISAService();
	private List<CompanyLearnersAchievementStatusEISA> companylearnersachievementstatuseisaList = null;
	private List<CompanyLearnersAchievementStatusEISA> companylearnersachievementstatuseisafilteredList = null;
	private CompanyLearnersAchievementStatusEISA companylearnersachievementstatuseisa = null;
	private LazyDataModel<CompanyLearnersAchievementStatusEISA> dataModel; 

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
	 * Initialize method to get all CompanyLearnersAchievementStatusEISA and prepare a for a create of a new CompanyLearnersAchievementStatusEISA
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		companylearnersachievementstatuseisaInfo();
	}

	/**
	 * Get all CompanyLearnersAchievementStatusEISA for data table
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 */
	public void companylearnersachievementstatuseisaInfo() {
//			dataModel = new CompanyLearnersAchievementStatusEISADatamodel();
	}

	/**
	 * Insert CompanyLearnersAchievementStatusEISA into database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 */
	public void companylearnersachievementstatuseisaInsert() {
		try {
				 service.create(this.companylearnersachievementstatuseisa);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersachievementstatuseisaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyLearnersAchievementStatusEISA in database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 */
    public void companylearnersachievementstatuseisaUpdate() {
		try {
				 service.update(this.companylearnersachievementstatuseisa);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersachievementstatuseisaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyLearnersAchievementStatusEISA from database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 */
	public void companylearnersachievementstatuseisaDelete() {
		try {
			 service.delete(this.companylearnersachievementstatuseisa);
			  prepareNew();
			 companylearnersachievementstatuseisaInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyLearnersAchievementStatusEISA 
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 */
	public void prepareNew() {
		companylearnersachievementstatuseisa = new CompanyLearnersAchievementStatusEISA();
	}

/*
    public List<SelectItem> getCompanyLearnersAchievementStatusEISADD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companylearnersachievementstatuseisaInfo();
    	for (CompanyLearnersAchievementStatusEISA ug : companylearnersachievementstatuseisaList) {
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
    public List<CompanyLearnersAchievementStatusEISA> complete(String desc) {
		List<CompanyLearnersAchievementStatusEISA> l = null;
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
    
    public List<CompanyLearnersAchievementStatusEISA> getCompanyLearnersAchievementStatusEISAList() {
		return companylearnersachievementstatuseisaList;
	}
	public void setCompanyLearnersAchievementStatusEISAList(List<CompanyLearnersAchievementStatusEISA> companylearnersachievementstatuseisaList) {
		this.companylearnersachievementstatuseisaList = companylearnersachievementstatuseisaList;
	}
	public CompanyLearnersAchievementStatusEISA getCompanylearnersachievementstatuseisa() {
		return companylearnersachievementstatuseisa;
	}
	public void setCompanylearnersachievementstatuseisa(CompanyLearnersAchievementStatusEISA companylearnersachievementstatuseisa) {
		this.companylearnersachievementstatuseisa = companylearnersachievementstatuseisa;
	}

    public List<CompanyLearnersAchievementStatusEISA> getCompanyLearnersAchievementStatusEISAfilteredList() {
		return companylearnersachievementstatuseisafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param companylearnersachievementstatuseisafilteredList the new companylearnersachievementstatuseisafilteredList list
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 */	
	public void setCompanyLearnersAchievementStatusEISAfilteredList(List<CompanyLearnersAchievementStatusEISA> companylearnersachievementstatuseisafilteredList) {
		this.companylearnersachievementstatuseisafilteredList = companylearnersachievementstatuseisafilteredList;
	}

	
	public LazyDataModel<CompanyLearnersAchievementStatusEISA> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersAchievementStatusEISA> dataModel) {
		this.dataModel = dataModel;
	}
	
}
