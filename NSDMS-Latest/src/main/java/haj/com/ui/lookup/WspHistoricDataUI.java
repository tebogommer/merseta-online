package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.WspHistoricData;
import haj.com.service.lookup.WspHistoricDataService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import java.util.Map.Entry;

import haj.com.exceptions.ValidationException;

@ManagedBean(name = "wsphistoricdataUI")
@ViewScoped
public class WspHistoricDataUI extends AbstractUI {

	private WspHistoricDataService service = new WspHistoricDataService();
	private List<WspHistoricData> wsphistoricdataList = null;
	private List<WspHistoricData> wsphistoricdatafilteredList = null;
	private WspHistoricData wsphistoricdata = null;
	private LazyDataModel<WspHistoricData> dataModel;

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
	 * Initialize method to get all WspHistoricData and prepare a for a create
	 * of a new WspHistoricData
	 * 
	 * @author TechFinium
	 * @see WspHistoricData
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		wsphistoricdataInfo();
	}

	/**
	 * Get all WspHistoricData for data table
	 * 
	 * @author TechFinium
	 * @see WspHistoricData
	 */
	public void wsphistoricdataInfo() {

		dataModel = new LazyDataModel<WspHistoricData>() {

			private static final long serialVersionUID = 1L;
			private List<WspHistoricData> retorno = new ArrayList<WspHistoricData>();

			@Override
			public List<WspHistoricData> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					for (Entry<String, Object> entry : filters.entrySet()) {
						if (entry.getKey().equals("levyYear")) {
							int year = Integer.parseInt(String.valueOf(entry.getValue()).replace("%", ""));
							filters.put("levyYear", year);
						}
					}
					retorno = service.allWspHistoricData(WspHistoricData.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(WspHistoricData.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WspHistoricData obj) {
				return obj.getId();
			}

			@Override
			public WspHistoricData getRowData(String rowKey) {
				for (WspHistoricData obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert WspHistoricData into database
	 * 
	 * @author TechFinium
	 * @see WspHistoricData
	 */
	public void wsphistoricdataInsert() {
		try {
			service.create(this.wsphistoricdata);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wsphistoricdataInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WspHistoricData in database
	 * 
	 * @author TechFinium
	 * @see WspHistoricData
	 */
	public void wsphistoricdataUpdate() {
		try {
			service.update(this.wsphistoricdata);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wsphistoricdataInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WspHistoricData from database
	 * 
	 * @author TechFinium
	 * @see WspHistoricData
	 */
	public void wsphistoricdataDelete() {
		try {
			service.delete(this.wsphistoricdata);
			prepareNew();
			wsphistoricdataInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WspHistoricData
	 * 
	 * @author TechFinium
	 * @see WspHistoricData
	 */
	public void prepareNew() {
		wsphistoricdata = new WspHistoricData();
	}

	/*
	 * public List<SelectItem> getWspHistoricDataDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * wsphistoricdataInfo(); for (WspHistoricData ug : wsphistoricdataList) {
	 * // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WspHistoricData> complete(String desc) {
		List<WspHistoricData> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WspHistoricData> getWspHistoricDataList() {
		return wsphistoricdataList;
	}

	public void setWspHistoricDataList(List<WspHistoricData> wsphistoricdataList) {
		this.wsphistoricdataList = wsphistoricdataList;
	}

	public WspHistoricData getWsphistoricdata() {
		return wsphistoricdata;
	}

	public void setWsphistoricdata(WspHistoricData wsphistoricdata) {
		this.wsphistoricdata = wsphistoricdata;
	}

	public List<WspHistoricData> getWspHistoricDatafilteredList() {
		return wsphistoricdatafilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param wsphistoricdatafilteredList
	 *            the new wsphistoricdatafilteredList list
	 * @see WspHistoricData
	 */
	public void setWspHistoricDatafilteredList(List<WspHistoricData> wsphistoricdatafilteredList) {
		this.wsphistoricdatafilteredList = wsphistoricdatafilteredList;
	}

	public LazyDataModel<WspHistoricData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspHistoricData> dataModel) {
		this.dataModel = dataModel;
	}

}
