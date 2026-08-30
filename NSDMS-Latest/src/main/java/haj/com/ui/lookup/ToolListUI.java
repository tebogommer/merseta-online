package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Tool;
import haj.com.entity.lookup.ToolCategory;
import haj.com.entity.lookup.ToolList;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ToolListService;

@ManagedBean(name = "toollistUI")
@ViewScoped
public class ToolListUI extends AbstractUI {

	private ToolListService service = new ToolListService();
	private List<ToolList> toollistList = null;
	private List<ToolList> toollistfilteredList = null;
	private ToolList toollist = null;
	private LazyDataModel<ToolList> dataModel;

	private List<ToolCategory> selectedCategories;
	private ToolCategory toolCategory;

	private List<Tool> selectedTools;
	private Tool tool;

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
	 * Initialize method to get all ToolList and prepare a for a create of a new
	 * ToolList
	 * 
	 * @author TechFinium
	 * @see ToolList
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		toollistInfo();
	}

	/**
	 * Get all ToolList for data table
	 * 
	 * @author TechFinium
	 * @see ToolList
	 */
	public void toollistInfo() {

		dataModel = new LazyDataModel<ToolList>() {

			private static final long serialVersionUID = 1L;
			private List<ToolList> retorno = new ArrayList<ToolList>();

			@Override
			public List<ToolList> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allToolList(ToolList.class, first, pageSize, sortField, sortOrder, filters);
					for (ToolList toolList : retorno) {
						toolList.setToolListTool(service.allToolListTools(toolList));
					}
					dataModel.setRowCount(service.count(ToolList.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(ToolList obj) {
				return obj.getId();
			}

			@Override
			public ToolList getRowData(String rowKey) {
				for (ToolList obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert ToolList into database
	 * 
	 * @author TechFinium
	 * @see ToolList
	 */
	public void toollistInsert() {
		try {
			service.create(this.toollist, selectedTools);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			toollistInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ToolList in database
	 * 
	 * @author TechFinium
	 * @see ToolList
	 */
	public void toollistUpdate() {
		try {
			service.update(this.toollist);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			toollistInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ToolList from database
	 * 
	 * @author TechFinium
	 * @see ToolList
	 */
	public void toollistDelete() {
		try {
			service.delete(this.toollist);
			prepareNew();
			toollistInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ToolList
	 * 
	 * @author TechFinium
	 * @see ToolList
	 */
	public void prepareNew() {
		toollist = new ToolList();
		selectedTools = new ArrayList<>();
		tool = null;
	}

	public void prepToolList() {
		this.selectedTools = toollist.getToolListTool().stream().map(x -> x.getTool()).collect(Collectors.toList());
	}

	public void addToList() {
		this.selectedTools.add(tool);
		this.tool = null;
	}

	public void removeFromList() {
		this.selectedTools.remove(tool);
		this.tool = null;
	}

	/*
	 * public List<SelectItem> getToolListDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * toollistInfo(); for (ToolList ug : toollistList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ToolList> complete(String desc) {
		List<ToolList> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ToolList> getToolListList() {
		return toollistList;
	}

	public void setToolListList(List<ToolList> toollistList) {
		this.toollistList = toollistList;
	}

	public ToolList getToollist() {
		return toollist;
	}

	public void setToollist(ToolList toollist) {
		this.toollist = toollist;
	}

	public List<ToolList> getToolListfilteredList() {
		return toollistfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param toollistfilteredList
	 *            the new toollistfilteredList list
	 * @see ToolList
	 */
	public void setToolListfilteredList(List<ToolList> toollistfilteredList) {
		this.toollistfilteredList = toollistfilteredList;
	}

	public LazyDataModel<ToolList> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ToolList> dataModel) {
		this.dataModel = dataModel;
	}

	public ToolCategory getToolCategory() {
		return toolCategory;
	}

	public void setToolCategory(ToolCategory toolCategory) {
		this.toolCategory = toolCategory;
	}

	public List<ToolCategory> getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(List<ToolCategory> selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	public List<Tool> getSelectedTools() {
		return selectedTools;
	}

	public void setSelectedTools(List<Tool> selectedTools) {
		this.selectedTools = selectedTools;
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

}
