package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.QualificationToolKit;
import haj.com.service.lookup.QualificationToolKitService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "qualificationtoolkitUI")
@ViewScoped
public class QualificationToolKitUI extends AbstractUI {

	private QualificationToolKitService service = new QualificationToolKitService();
	private List<QualificationToolKit> qualificationtoolkitList = null;
	private List<QualificationToolKit> qualificationtoolkitfilteredList = null;
	private QualificationToolKit qualificationtoolkit = null;
	private LazyDataModel<QualificationToolKit> dataModel; 

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
	 * Initialize method to get all QualificationToolKit and prepare a for a create of a new QualificationToolKit
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qualificationtoolkitInfo();
	}

	/**
	 * Get all QualificationToolKit for data table
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
 	 */
	public void qualificationtoolkitInfo() {
	 
			
			 dataModel = new LazyDataModel<QualificationToolKit>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<QualificationToolKit> retorno = new  ArrayList<QualificationToolKit>();
			   
			   @Override 
			   public List<QualificationToolKit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allQualificationToolKit(QualificationToolKit.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(QualificationToolKit.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(QualificationToolKit obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public QualificationToolKit getRowData(String rowKey) {
			        for(QualificationToolKit obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert QualificationToolKit into database
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
 	 */
	public void qualificationtoolkitInsert() {
		try {
				 service.createWithCheck(this.qualificationtoolkit);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationtoolkitInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QualificationToolKit in database
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
 	 */
    public void qualificationtoolkitUpdate() {
		try {
				 service.update(this.qualificationtoolkit);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationtoolkitInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QualificationToolKit from database
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
 	 */
	public void qualificationtoolkitDelete() {
		try {
			 service.delete(this.qualificationtoolkit);
			  prepareNew();
			 qualificationtoolkitInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QualificationToolKit 
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
 	 */
	public void prepareNew() {
		qualificationtoolkit = new QualificationToolKit();
	}

/*
    public List<SelectItem> getQualificationToolKitDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qualificationtoolkitInfo();
    	for (QualificationToolKit ug : qualificationtoolkitList) {
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
    public List<QualificationToolKit> complete(String desc) {
		List<QualificationToolKit> l = null;
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
    
    public List<QualificationToolKit> getQualificationToolKitList() {
		return qualificationtoolkitList;
	}
	public void setQualificationToolKitList(List<QualificationToolKit> qualificationtoolkitList) {
		this.qualificationtoolkitList = qualificationtoolkitList;
	}
	public QualificationToolKit getQualificationtoolkit() {
		return qualificationtoolkit;
	}
	public void setQualificationtoolkit(QualificationToolKit qualificationtoolkit) {
		this.qualificationtoolkit = qualificationtoolkit;
	}

    public List<QualificationToolKit> getQualificationToolKitfilteredList() {
		return qualificationtoolkitfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qualificationtoolkitfilteredList the new qualificationtoolkitfilteredList list
 	 * @see    QualificationToolKit
 	 */	
	public void setQualificationToolKitfilteredList(List<QualificationToolKit> qualificationtoolkitfilteredList) {
		this.qualificationtoolkitfilteredList = qualificationtoolkitfilteredList;
	}

	
	public LazyDataModel<QualificationToolKit> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QualificationToolKit> dataModel) {
		this.dataModel = dataModel;
	}
	
}
