package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.WspCompanyAddressHistory;
import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.entity.WspCompanyHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.entity.WspSdfCompanyHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspCompanyAddressHistoryService;
import haj.com.service.WspCompanyEmployeesHistoryService;
import haj.com.service.WspCompanyHistoryService;
import haj.com.service.WspCompanyMainHistoryService;
import haj.com.service.WspCompanyTrainingComitteeHistoryService;
import haj.com.service.WspSdfCompanyHistoryService;

// TODO: Auto-generated Javadoc
@ManagedBean(name = "monitorCompanyHistoryUI")
@ViewScoped
public class MonitorCompanyHistoryUI extends AbstractUI {
	
	/* Entity Level */
	private WspCompanyMainHistory wspCompanyMainHistory;
	private WspCompanyHistory wspCompanyHistory;
	
	/* Service Level */
	private WspCompanyMainHistoryService wspCompanyMainHistoryService = new WspCompanyMainHistoryService();
	private WspCompanyAddressHistoryService wspCompanyAddressHistoryService = new WspCompanyAddressHistoryService();
	private WspCompanyEmployeesHistoryService wspCompanyEmployeesHistoryService = new WspCompanyEmployeesHistoryService();
	private WspCompanyHistoryService companyHistoryService = new WspCompanyHistoryService();
	private WspCompanyTrainingComitteeHistoryService wspCompanyTrainingComitteeHistoryService = new WspCompanyTrainingComitteeHistoryService();
	private WspSdfCompanyHistoryService wspSdfCompanyHistoryService = new WspSdfCompanyHistoryService();
	
	/* Data Models */
	private LazyDataModel<WspCompanyMainHistory> dataModelWspCompanyMainHistory;
	private LazyDataModel<WspCompanyAddressHistory> dataModelWspCompanyAddressHistory;
	private LazyDataModel<WspCompanyEmployeesHistory> dataModelWspCompanyEmployeesHistory;
	private LazyDataModel<WspCompanyHistory> dataModelWspCompanyHistory;
	private LazyDataModel<WspCompanyTrainingComitteeHistory> dataModelWspCompanyTrainingComittee;
	private LazyDataModel<WspSdfCompanyHistory> dataModelWspSdfCompanyHistory;
	
	/* booleans */
	private Boolean displayInfo = false;

    /**
     * Inits the.
     */
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		dataModelWspCompanyMainHistoryInfo();
	}
	
	public void dataModelWspCompanyMainHistoryInfo() {
		dataModelWspCompanyMainHistory = new LazyDataModel<WspCompanyMainHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyMainHistory> list = new ArrayList<>();
			@Override
			public List<WspCompanyMainHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = wspCompanyMainHistoryService.allWspCompanyMainHistory(WspCompanyMainHistory.class, first, pageSize, sortField, sortOrder, filters);
					dataModelWspCompanyMainHistory.setRowCount(wspCompanyMainHistoryService.count(WspCompanyMainHistory.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(WspCompanyMainHistory object) {
				return object.getId();
			}
			@Override
			public WspCompanyMainHistory getRowData(String rowKey) {
				for (WspCompanyMainHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void selectHistory() {
		try {
			if (wspCompanyMainHistory != null && wspCompanyMainHistory.getId() != null) {
				displayInfo = true;
				dataModelWspCompanyAddressHistoryInfo();
				dataModelWspCompanyEmployeesHistoryInfo();
				dataModelWspCompanyHistoryInfo();
				dataModelWspCompanyTrainingComitteeInfo();
				dataModelWspSdfCompanyHistoryInfo();
				addInfoMessage("Select Complete");
			}else {
				addErrorMessage("Unable to locate main history link, contact support!");
				displayInfo = false;
			}
		} catch (Exception e) {
			displayInfo = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeView(){
		try {
			wspCompanyMainHistory = null;
			wspCompanyHistory = null;
			displayInfo = false;
			addInfoMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dataModelWspCompanyAddressHistoryInfo() {
		dataModelWspCompanyAddressHistory = new LazyDataModel<WspCompanyAddressHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyAddressHistory> list = new ArrayList<>();
			@Override
			public List<WspCompanyAddressHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = wspCompanyAddressHistoryService.allWspCompanyAddressHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField, sortOrder, filters, wspCompanyMainHistory.getTargetKey(), wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyAddressHistory.setRowCount(wspCompanyAddressHistoryService.countAllWspCompanyAddressHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(WspCompanyAddressHistory object) {
				return object.getId();
			}
			@Override
			public WspCompanyAddressHistory getRowData(String rowKey) {
				for (WspCompanyAddressHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void dataModelWspCompanyEmployeesHistoryInfo() {
		dataModelWspCompanyEmployeesHistory = new LazyDataModel<WspCompanyEmployeesHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyEmployeesHistory> list = new ArrayList<>();
			@Override
			public List<WspCompanyEmployeesHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = wspCompanyEmployeesHistoryService.allWspCompanyEmployeesHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField, sortOrder, filters, wspCompanyMainHistory.getTargetKey(), wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyEmployeesHistory.setRowCount(wspCompanyEmployeesHistoryService.countAllWspCompanyEmployeesHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(WspCompanyEmployeesHistory object) {
				return object.getId();
			}
			@Override
			public WspCompanyEmployeesHistory getRowData(String rowKey) {
				for (WspCompanyEmployeesHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void dataModelWspCompanyHistoryInfo() {
		try {
			if (wspCompanyMainHistory != null && wspCompanyMainHistory.getId() != null) {
				wspCompanyHistory = companyHistoryService.findByTargetClassAndKeyAndMainLinkReturnOne(wspCompanyMainHistory.getTargetKey(), wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		dataModelWspCompanyHistory = new LazyDataModel<WspCompanyHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyHistory> list = new ArrayList<>();
			@Override
			public List<WspCompanyHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = companyHistoryService.allWspCompanyHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField, sortOrder, filters, wspCompanyMainHistory.getTargetKey(), wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyHistory.setRowCount(companyHistoryService.countAllWspCompanyHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(WspCompanyHistory object) {
				return object.getId();
			}
			@Override
			public WspCompanyHistory getRowData(String rowKey) {
				for (WspCompanyHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void dataModelWspCompanyTrainingComitteeInfo() {
		dataModelWspCompanyTrainingComittee = new LazyDataModel<WspCompanyTrainingComitteeHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyTrainingComitteeHistory> list = new ArrayList<>();
			@Override
			public List<WspCompanyTrainingComitteeHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = wspCompanyTrainingComitteeHistoryService.allWspCompanyTrainingComitteeHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField, sortOrder, filters, wspCompanyMainHistory.getTargetKey(), wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyTrainingComittee.setRowCount(wspCompanyTrainingComitteeHistoryService.countAllWspCompanyTrainingComitteeHistoryHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(WspCompanyTrainingComitteeHistory object) {
				return object.getId();
			}
			@Override
			public WspCompanyTrainingComitteeHistory getRowData(String rowKey) {
				for (WspCompanyTrainingComitteeHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void dataModelWspSdfCompanyHistoryInfo() {
		dataModelWspSdfCompanyHistory = new LazyDataModel<WspSdfCompanyHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspSdfCompanyHistory> list = new ArrayList<>();
			@Override
			public List<WspSdfCompanyHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = wspSdfCompanyHistoryService.allWspSdfCompanyHistoryHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField, sortOrder, filters, wspCompanyMainHistory.getTargetKey(), wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspSdfCompanyHistory.setRowCount(wspSdfCompanyHistoryService.countAllWspSdfCompanyHistoryHistoryHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(WspSdfCompanyHistory object) {
				return object.getId();
			}
			@Override
			public WspSdfCompanyHistory getRowData(String rowKey) {
				for (WspSdfCompanyHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	/* Getters and setters */
	public WspCompanyMainHistory getWspCompanyMainHistory() {
		return wspCompanyMainHistory;
	}

	public void setWspCompanyMainHistory(WspCompanyMainHistory wspCompanyMainHistory) {
		this.wspCompanyMainHistory = wspCompanyMainHistory;
	}

	public WspCompanyHistory getWspCompanyHistory() {
		return wspCompanyHistory;
	}

	public void setWspCompanyHistory(WspCompanyHistory wspCompanyHistory) {
		this.wspCompanyHistory = wspCompanyHistory;
	}

	public LazyDataModel<WspCompanyMainHistory> getDataModelWspCompanyMainHistory() {
		return dataModelWspCompanyMainHistory;
	}

	public void setDataModelWspCompanyMainHistory(LazyDataModel<WspCompanyMainHistory> dataModelWspCompanyMainHistory) {
		this.dataModelWspCompanyMainHistory = dataModelWspCompanyMainHistory;
	}

	public LazyDataModel<WspCompanyAddressHistory> getDataModelWspCompanyAddressHistory() {
		return dataModelWspCompanyAddressHistory;
	}

	public void setDataModelWspCompanyAddressHistory(
			LazyDataModel<WspCompanyAddressHistory> dataModelWspCompanyAddressHistory) {
		this.dataModelWspCompanyAddressHistory = dataModelWspCompanyAddressHistory;
	}

	public LazyDataModel<WspCompanyEmployeesHistory> getDataModelWspCompanyEmployeesHistory() {
		return dataModelWspCompanyEmployeesHistory;
	}

	public void setDataModelWspCompanyEmployeesHistory(
			LazyDataModel<WspCompanyEmployeesHistory> dataModelWspCompanyEmployeesHistory) {
		this.dataModelWspCompanyEmployeesHistory = dataModelWspCompanyEmployeesHistory;
	}

	public LazyDataModel<WspCompanyHistory> getDataModelWspCompanyHistory() {
		return dataModelWspCompanyHistory;
	}

	public void setDataModelWspCompanyHistory(LazyDataModel<WspCompanyHistory> dataModelWspCompanyHistory) {
		this.dataModelWspCompanyHistory = dataModelWspCompanyHistory;
	}

	public LazyDataModel<WspCompanyTrainingComitteeHistory> getDataModelWspCompanyTrainingComittee() {
		return dataModelWspCompanyTrainingComittee;
	}

	public void setDataModelWspCompanyTrainingComittee(
			LazyDataModel<WspCompanyTrainingComitteeHistory> dataModelWspCompanyTrainingComittee) {
		this.dataModelWspCompanyTrainingComittee = dataModelWspCompanyTrainingComittee;
	}

	public LazyDataModel<WspSdfCompanyHistory> getDataModelWspSdfCompanyHistory() {
		return dataModelWspSdfCompanyHistory;
	}

	public void setDataModelWspSdfCompanyHistory(LazyDataModel<WspSdfCompanyHistory> dataModelWspSdfCompanyHistory) {
		this.dataModelWspSdfCompanyHistory = dataModelWspSdfCompanyHistory;
	}

	public Boolean getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(Boolean displayInfo) {
		this.displayInfo = displayInfo;
	}

	
}
