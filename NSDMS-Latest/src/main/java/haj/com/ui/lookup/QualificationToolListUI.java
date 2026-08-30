package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.QualificationToolList;
import haj.com.service.lookup.QualificationToolListService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "qualificationtoollistUI")
@ViewScoped
public class QualificationToolListUI extends AbstractUI {

	private QualificationToolListService service = new QualificationToolListService();
	private List<QualificationToolList> qualificationtoollistList = null;
	private List<QualificationToolList> qualificationtoollistfilteredList = null;
	private QualificationToolList qualificationtoollist = null;
	private LazyDataModel<QualificationToolList> dataModel; 

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
	 * Initialize method to get all QualificationToolList and prepare a for a create of a new QualificationToolList
 	 * @author TechFinium 
 	 * @see    QualificationToolList
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qualificationToolListInfo();
	}

	/**
	 * Get all QualificationToolList for data table
 	 * @author TechFinium 
 	 * @see    QualificationToolList
 	 */
	public void qualificationToolListInfo() {
	 
			
			 dataModel = new LazyDataModel<QualificationToolList>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<QualificationToolList> retorno = new  ArrayList<QualificationToolList>();
			   
			   @Override 
			   public List<QualificationToolList> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allQualificationToolList(QualificationToolList.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(QualificationToolList.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(QualificationToolList obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public QualificationToolList getRowData(String rowKey) {
			        for(QualificationToolList obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert QualificationToolList into database
 	 * @author TechFinium 
 	 * @see    QualificationToolList
 	 */
	public void qualificationtoollistInsert() {
		try {
				 service.create(this.qualificationtoollist);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationToolListInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QualificationToolList in database
 	 * @author TechFinium 
 	 * @see    QualificationToolList
 	 */
    public void qualificationtoollistUpdate() {
		try {
				 service.update(this.qualificationtoollist);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				  qualificationToolListInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QualificationToolList from database
 	 * @author TechFinium 
 	 * @see    QualificationToolList
 	 */
	public void qualificationtoollistDelete() {
		try {
			 service.delete(this.qualificationtoollist);
			  prepareNew();
			  qualificationToolListInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QualificationToolList 
 	 * @author TechFinium 
 	 * @see    QualificationToolList
 	 */
	public void prepareNew() {
		qualificationtoollist = new QualificationToolList();
	}

/*
    public List<SelectItem> getQualificationToolListDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qualificationtoollistInfo();
    	for (QualificationToolList ug : qualificationtoollistList) {
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
    public List<QualificationToolList> complete(String desc) {
		List<QualificationToolList> l = null;
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
    
    public List<QualificationToolList> getQualificationToolListList() {
		return qualificationtoollistList;
	}
	public void setQualificationToolListList(List<QualificationToolList> qualificationtoollistList) {
		this.qualificationtoollistList = qualificationtoollistList;
	}
	public QualificationToolList getQualificationtoollist() {
		return qualificationtoollist;
	}
	public void setQualificationtoollist(QualificationToolList qualificationtoollist) {
		this.qualificationtoollist = qualificationtoollist;
	}

    public List<QualificationToolList> getQualificationToolListfilteredList() {
		return qualificationtoollistfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qualificationtoollistfilteredList the new qualificationtoollistfilteredList list
 	 * @see    QualificationToolList
 	 */	
	public void setQualificationToolListfilteredList(List<QualificationToolList> qualificationtoollistfilteredList) {
		this.qualificationtoollistfilteredList = qualificationtoollistfilteredList;
	}

	
	public LazyDataModel<QualificationToolList> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QualificationToolList> dataModel) {
		this.dataModel = dataModel;
	}
	
}
