package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DesignatedTradeLevelItems;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.DesignatedTradeLevelItemsService;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.DesignatedTradeService;
import haj.com.service.lookup.DesignatedTradeTypeService;

@ManagedBean(name = "designatedtradelevelUI")
@ViewScoped
public class DesignatedTradeLevelUI extends AbstractUI {

	private DesignatedTradeLevel designatedtradelevel = null;
	private DesignatedTradeLevel designatedtradelevelForItems = null;
	private DesignatedTradeLevelItems designatedTradeLevelItems = null;
	
	private DesignatedTradeLevelService service = new DesignatedTradeLevelService();
	private DesignatedTradeService designatedTradeService = new DesignatedTradeService();
	private DesignatedTradeTypeService designatedTradeTypeService = new DesignatedTradeTypeService();
	private DesignatedTradeLevelItemsService designatedTradeLevelItemsService = new DesignatedTradeLevelItemsService();
	
	private LazyDataModel<DesignatedTradeLevel> dataModel;

	private List<DesignatedTradeLevel> designatedtradelevelList = null;
	private List<DesignatedTradeLevel> designatedtradelevelfilteredList = null;
	
	private List<DesignatedTrade> designatedTradeList = new ArrayList<>();
	private List<DesignatedTradeType> designatedTradeTypeList = new ArrayList<>();


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
	 * Initialize method to get all DesignatedTradeLevel and prepare a for a
	 * create of a new DesignatedTradeLevel
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		designatedtradelevelInfo();
		populateLists();
	}

	/**
	 * Get all DesignatedTradeLevel for data table
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 */
	public void designatedtradelevelInfo() {
		dataModel = new LazyDataModel<DesignatedTradeLevel>() {
			private static final long serialVersionUID = 1L;
			private List<DesignatedTradeLevel> retorno = new ArrayList<DesignatedTradeLevel>();

			@Override
			public List<DesignatedTradeLevel> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allDesignatedTradeLevel(DesignatedTradeLevel.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(DesignatedTradeLevel.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DesignatedTradeLevel obj) {
				return obj.getId();
			}

			@Override
			public DesignatedTradeLevel getRowData(String rowKey) {
				for (DesignatedTradeLevel obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	private void populateLists() throws Exception {
		designatedTradeList = designatedTradeService.allDesignatedTrade();
		designatedTradeTypeList = designatedTradeTypeService.allDesignatedTradeType();
	}

	/**
	 * Insert DesignatedTradeLevel into database
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 */
	public void designatedtradelevelInsert() {
		try {
			service.create(this.designatedtradelevel);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			designatedtradelevelInfo();
			populateLists();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DesignatedTradeLevel in database
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 */
	public void designatedtradelevelUpdate() {
		try {
			service.update(this.designatedtradelevel);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			designatedtradelevelInfo();
			populateLists();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DesignatedTradeLevel from database
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 */
	public void designatedtradelevelDelete() {
		try {
			service.delete(this.designatedtradelevel);
			prepareNew();
			designatedtradelevelInfo();
			populateLists();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DesignatedTradeLevel
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 */
	public void prepareNew() {
		designatedtradelevel = new DesignatedTradeLevel();
		designatedtradelevel.setExtension(false);
		designatedtradelevel.setNoOrder(false);
	}

	/*
	 * public List<SelectItem> getDesignatedTradeLevelDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * designatedtradelevelInfo(); for (DesignatedTradeLevel ug :
	 * designatedtradelevelList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DesignatedTradeLevel> complete(String desc) {
		List<DesignatedTradeLevel> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
//	private DesignatedTradeLevel designatedtradelevel = null;
//	private DesignatedTradeLevel designatedtradelevelForItems = null;
//	private DesignatedTradeLevelItems designatedTradeLevelItems = null;
	
	public void prepNewItem(){
		try {
			designatedTradeLevelItems = new DesignatedTradeLevelItems();
			designatedTradeLevelItems.setDesignatedTradeLevel(designatedtradelevelForItems);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void createUpdateItem(){
		try {
			designatedTradeLevelItemsService.create(designatedTradeLevelItems);
			designatedTradeLevelItems = null;
			runClientSideExecute("PF('addItemDlg').hide()");
			addInfoMessage("Action Complete");
			prepareNew();
			designatedtradelevelInfo();
			populateLists();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void deleteItem(){
		try {
			designatedTradeLevelItemsService.delete(designatedTradeLevelItems);
			runClientSideExecute("PF('addItemDlg').hide()");
			addWarningMessage("Action Complete");
			prepareNew();
			designatedtradelevelInfo();
			populateLists();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeItemView(){
		try {
			designatedTradeLevelItems = null;
			runClientSideExecute("PF('addItemDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* getters and Setters */
	public List<DesignatedTradeLevel> getDesignatedTradeLevelList() {
		return designatedtradelevelList;
	}

	public void setDesignatedTradeLevelList(List<DesignatedTradeLevel> designatedtradelevelList) {
		this.designatedtradelevelList = designatedtradelevelList;
	}

	public DesignatedTradeLevel getDesignatedtradelevel() {
		return designatedtradelevel;
	}

	public void setDesignatedtradelevel(DesignatedTradeLevel designatedtradelevel) {
		this.designatedtradelevel = designatedtradelevel;
	}

	public List<DesignatedTradeLevel> getDesignatedTradeLevelfilteredList() {
		return designatedtradelevelfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param designatedtradelevelfilteredList
	 *            the new designatedtradelevelfilteredList list
	 * @see DesignatedTradeLevel
	 */
	public void setDesignatedTradeLevelfilteredList(List<DesignatedTradeLevel> designatedtradelevelfilteredList) {
		this.designatedtradelevelfilteredList = designatedtradelevelfilteredList;
	}

	public LazyDataModel<DesignatedTradeLevel> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DesignatedTradeLevel> dataModel) {
		this.dataModel = dataModel;
	}

	public List<DesignatedTradeLevel> getDesignatedtradelevelList() {
		return designatedtradelevelList;
	}

	public void setDesignatedtradelevelList(List<DesignatedTradeLevel> designatedtradelevelList) {
		this.designatedtradelevelList = designatedtradelevelList;
	}

	public List<DesignatedTradeLevel> getDesignatedtradelevelfilteredList() {
		return designatedtradelevelfilteredList;
	}

	public void setDesignatedtradelevelfilteredList(List<DesignatedTradeLevel> designatedtradelevelfilteredList) {
		this.designatedtradelevelfilteredList = designatedtradelevelfilteredList;
	}

	public List<DesignatedTrade> getDesignatedTradeList() {
		return designatedTradeList;
	}

	public void setDesignatedTradeList(List<DesignatedTrade> designatedTradeList) {
		this.designatedTradeList = designatedTradeList;
	}

	public List<DesignatedTradeType> getDesignatedTradeTypeList() {
		return designatedTradeTypeList;
	}

	public void setDesignatedTradeTypeList(List<DesignatedTradeType> designatedTradeTypeList) {
		this.designatedTradeTypeList = designatedTradeTypeList;
	}

	public DesignatedTradeLevelItems getDesignatedTradeLevelItems() {
		return designatedTradeLevelItems;
	}

	public void setDesignatedTradeLevelItems(DesignatedTradeLevelItems designatedTradeLevelItems) {
		this.designatedTradeLevelItems = designatedTradeLevelItems;
	}

	public DesignatedTradeLevel getDesignatedtradelevelForItems() {
		return designatedtradelevelForItems;
	}

	public void setDesignatedtradelevelForItems(DesignatedTradeLevel designatedtradelevelForItems) {
		this.designatedtradelevelForItems = designatedtradelevelForItems;
	}

}
