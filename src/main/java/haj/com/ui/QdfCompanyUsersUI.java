package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.QdfCompany;
import haj.com.entity.QdfCompanyUsers;
import haj.com.service.QdfCompanyUsersService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "qdfcompanyusersUI")
@ViewScoped
public class QdfCompanyUsersUI extends AbstractUI {

	private QdfCompanyUsersService service = new QdfCompanyUsersService();
	private List<QdfCompanyUsers> qdfcompanyusersList = null;
	private List<QdfCompanyUsers> qdfcompanyusersfilteredList = null;
	private QdfCompanyUsers qdfcompanyusers = null;
	private LazyDataModel<QdfCompanyUsers> dataModel; 

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
	 * Initialize method to get all QdfCompanyUsers and prepare a for a create of a new QdfCompanyUsers
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qdfcompanyusersInfo();
	}

	/**
	 * Get all QdfCompanyUsers for data table
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
 	 */
	public void qdfcompanyusersInfo() {
			//dataModel = new QdfCompanyUsersDatamodel();
			dataModel = new LazyDataModel<QdfCompanyUsers>() {
				private static final long serialVersionUID = 1L;
				private List<QdfCompanyUsers> retorno = new ArrayList<QdfCompanyUsers>();
				@Override
				public List<QdfCompanyUsers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
					try {
						filters.put("user", getSessionUI().getActiveUser());
						retorno = service.allQdfCompanyUsers(QdfCompanyUsers.class, first, pageSize, sortField, sortOrder, filters);
						for(QdfCompanyUsers qdfCompany : retorno) {
							//populateDate(qdfCompany);
						}
						dataModel.setRowCount(service.count(QdfCompanyUsers.class, filters));
					} catch (Exception e) {
						logger.fatal(e);
					}
					return retorno;
				}
				
				@Override
				public Object getRowKey(QdfCompanyUsers obj) {
					return obj.getId();
				}
				
				@Override
				public QdfCompanyUsers getRowData(String rowKey) {
					for (QdfCompanyUsers obj : retorno) {
						if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
					}
					return null;
				}
			};
	}

	/**
	 * Insert QdfCompanyUsers into database
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
 	 */
	public void qdfcompanyusersInsert() {
		try {
				 service.create(this.qdfcompanyusers);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qdfcompanyusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QdfCompanyUsers in database
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
 	 */
    public void qdfcompanyusersUpdate() {
		try {
				 service.update(this.qdfcompanyusers);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qdfcompanyusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QdfCompanyUsers from database
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
 	 */
	public void qdfcompanyusersDelete() {
		try {
			 service.delete(this.qdfcompanyusers);
			  prepareNew();
			 qdfcompanyusersInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QdfCompanyUsers 
 	 * @author TechFinium 
 	 * @see    QdfCompanyUsers
 	 */
	public void prepareNew() {
		qdfcompanyusers = new QdfCompanyUsers();
	}

/*
    public List<SelectItem> getQdfCompanyUsersDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qdfcompanyusersInfo();
    	for (QdfCompanyUsers ug : qdfcompanyusersList) {
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
    public List<QdfCompanyUsers> complete(String desc) {
		List<QdfCompanyUsers> l = null;
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
    
    public List<QdfCompanyUsers> getQdfCompanyUsersList() {
		return qdfcompanyusersList;
	}
	public void setQdfCompanyUsersList(List<QdfCompanyUsers> qdfcompanyusersList) {
		this.qdfcompanyusersList = qdfcompanyusersList;
	}
	public QdfCompanyUsers getQdfcompanyusers() {
		return qdfcompanyusers;
	}
	public void setQdfcompanyusers(QdfCompanyUsers qdfcompanyusers) {
		this.qdfcompanyusers = qdfcompanyusers;
	}

    public List<QdfCompanyUsers> getQdfCompanyUsersfilteredList() {
		return qdfcompanyusersfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qdfcompanyusersfilteredList the new qdfcompanyusersfilteredList list
 	 * @see    QdfCompanyUsers
 	 */	
	public void setQdfCompanyUsersfilteredList(List<QdfCompanyUsers> qdfcompanyusersfilteredList) {
		this.qdfcompanyusersfilteredList = qdfcompanyusersfilteredList;
	}

	
	public LazyDataModel<QdfCompanyUsers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QdfCompanyUsers> dataModel) {
		this.dataModel = dataModel;
	}
	
}
