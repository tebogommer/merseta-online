package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.QualificationEntryRequirement;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.QualificationEntryRequirementService;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationEntryRequirementUI.
 */
@ManagedBean(name = "qualificationentryrequirementUI")
@ViewScoped
public class QualificationEntryRequirementUI extends AbstractUI {

	/** The service. */
	private QualificationEntryRequirementService service = new QualificationEntryRequirementService();
	
	/** The qualificationentryrequirement list. */
	private List<QualificationEntryRequirement> qualificationentryrequirementList = null;
	
	/** The qualificationentryrequirementfiltered list. */
	private List<QualificationEntryRequirement> qualificationentryrequirementfilteredList = null;
	
	/** The qualificationentryrequirement. */
	private QualificationEntryRequirement qualificationentryrequirement = null;
	
	/** The data model. */
	private LazyDataModel<QualificationEntryRequirement> dataModel; 

    /**
     * Inits the.
     */
    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all QualificationEntryRequirement and prepare a for a create of a new QualificationEntryRequirement.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    QualificationEntryRequirement
	 */
	private void runInit() throws Exception {
		prepareNew();
		qualificationentryrequirementInfo();
	}

	/**
	 * Get all QualificationEntryRequirement for data table.
	 *
	 * @author TechFinium
	 * @see    QualificationEntryRequirement
	 */
	public void qualificationentryrequirementInfo() {
	 
			
			 dataModel = new LazyDataModel<QualificationEntryRequirement>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<QualificationEntryRequirement> retorno = new  ArrayList<QualificationEntryRequirement>();
			   
			   @Override 
			   public List<QualificationEntryRequirement> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allQualificationEntryRequirement(QualificationEntryRequirement.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(QualificationEntryRequirement.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(QualificationEntryRequirement obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public QualificationEntryRequirement getRowData(String rowKey) {
			        for(QualificationEntryRequirement obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert QualificationEntryRequirement into database.
	 *
	 * @author TechFinium
	 * @see    QualificationEntryRequirement
	 */
	public void qualificationentryrequirementInsert() {
		try {
				 service.create(this.qualificationentryrequirement);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationentryrequirementInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QualificationEntryRequirement in database.
	 *
	 * @author TechFinium
	 * @see    QualificationEntryRequirement
	 */
    public void qualificationentryrequirementUpdate() {
		try {
				 service.update(this.qualificationentryrequirement);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationentryrequirementInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QualificationEntryRequirement from database.
	 *
	 * @author TechFinium
	 * @see    QualificationEntryRequirement
	 */
	public void qualificationentryrequirementDelete() {
		try {
			 service.delete(this.qualificationentryrequirement);
			  prepareNew();
			 qualificationentryrequirementInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QualificationEntryRequirement .
	 *
	 * @author TechFinium
	 * @see    QualificationEntryRequirement
	 */
	public void prepareNew() {
		qualificationentryrequirement = new QualificationEntryRequirement();
	}

/*
    public List<SelectItem> getQualificationEntryRequirementDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qualificationentryrequirementInfo();
    	for (QualificationEntryRequirement ug : qualificationentryrequirementList) {
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
    public List<QualificationEntryRequirement> complete(String desc) {
		List<QualificationEntryRequirement> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    /**
     * Gets the qualification entry requirement list.
     *
     * @return the qualification entry requirement list
     */
    public List<QualificationEntryRequirement> getQualificationEntryRequirementList() {
		return qualificationentryrequirementList;
	}
	
	/**
	 * Sets the qualification entry requirement list.
	 *
	 * @param qualificationentryrequirementList the new qualification entry requirement list
	 */
	public void setQualificationEntryRequirementList(List<QualificationEntryRequirement> qualificationentryrequirementList) {
		this.qualificationentryrequirementList = qualificationentryrequirementList;
	}
	
	/**
	 * Gets the qualificationentryrequirement.
	 *
	 * @return the qualificationentryrequirement
	 */
	public QualificationEntryRequirement getQualificationentryrequirement() {
		return qualificationentryrequirement;
	}
	
	/**
	 * Sets the qualificationentryrequirement.
	 *
	 * @param qualificationentryrequirement the new qualificationentryrequirement
	 */
	public void setQualificationentryrequirement(QualificationEntryRequirement qualificationentryrequirement) {
		this.qualificationentryrequirement = qualificationentryrequirement;
	}

    /**
     * Gets the qualification entry requirementfiltered list.
     *
     * @return the qualification entry requirementfiltered list
     */
    public List<QualificationEntryRequirement> getQualificationEntryRequirementfilteredList() {
		return qualificationentryrequirementfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param qualificationentryrequirementfilteredList the new qualificationentryrequirementfilteredList list
	 * @see    QualificationEntryRequirement
	 */	
	public void setQualificationEntryRequirementfilteredList(List<QualificationEntryRequirement> qualificationentryrequirementfilteredList) {
		this.qualificationentryrequirementfilteredList = qualificationentryrequirementfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<QualificationEntryRequirement> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<QualificationEntryRequirement> dataModel) {
		this.dataModel = dataModel;
	}
	
}
