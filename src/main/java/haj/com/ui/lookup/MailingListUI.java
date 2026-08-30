package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.MailingList;
import haj.com.service.lookup.MailingListService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "mailinglistUI")
@ViewScoped
public class MailingListUI extends AbstractUI {

	private MailingListService service = new MailingListService();
	private List<MailingList> mailinglistList = null;
	private List<MailingList> mailinglistfilteredList = null;
	private MailingList mailinglist = null;
	private LazyDataModel<MailingList> dataModel;

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
	 * Initialize method to get all MailingList and prepare a for a create of a
	 * new MailingList
	 * 
	 * @author TechFinium
	 * @see MailingList
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		mailinglistInfo();
	}

	/**
	 * Get all MailingList for data table
	 * 
	 * @author TechFinium
	 * @see MailingList
	 */
	public void mailinglistInfo() {

		dataModel = new LazyDataModel<MailingList>() {

			private static final long serialVersionUID = 1L;
			private List<MailingList> retorno = new ArrayList<MailingList>();

			@Override
			public List<MailingList> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allMailingList(MailingList.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(MailingList.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MailingList obj) {
				return obj.getId();
			}

			@Override
			public MailingList getRowData(String rowKey) {
				for (MailingList obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert MailingList into database
	 * 
	 * @author TechFinium
	 * @see MailingList
	 */
	public void mailinglistInsert() {
		try {
			service.create(this.mailinglist);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mailinglistInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update MailingList in database
	 * 
	 * @author TechFinium
	 * @see MailingList
	 */
	public void mailinglistUpdate() {
		try {
			service.update(this.mailinglist);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mailinglistInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete MailingList from database
	 * 
	 * @author TechFinium
	 * @see MailingList
	 */
	public void mailinglistDelete() {
		try {
			service.delete(this.mailinglist);
			prepareNew();
			mailinglistInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testEmailNotification(){
		try {
			service.testNotification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of MailingList
	 * 
	 * @author TechFinium
	 * @see MailingList
	 */
	public void prepareNew() {
		mailinglist = new MailingList();
	}

	/*
	 * public List<SelectItem> getMailingListDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * mailinglistInfo(); for (MailingList ug : mailinglistList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<MailingList> complete(String desc) {
		List<MailingList> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<MailingList> getMailingListList() {
		return mailinglistList;
	}

	public void setMailingListList(List<MailingList> mailinglistList) {
		this.mailinglistList = mailinglistList;
	}

	public MailingList getMailinglist() {
		return mailinglist;
	}

	public void setMailinglist(MailingList mailinglist) {
		this.mailinglist = mailinglist;
	}

	public List<MailingList> getMailingListfilteredList() {
		return mailinglistfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param mailinglistfilteredList
	 *            the new mailinglistfilteredList list
	 * @see MailingList
	 */
	public void setMailingListfilteredList(List<MailingList> mailinglistfilteredList) {
		this.mailinglistfilteredList = mailinglistfilteredList;
	}

	public LazyDataModel<MailingList> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MailingList> dataModel) {
		this.dataModel = dataModel;
	}

}
