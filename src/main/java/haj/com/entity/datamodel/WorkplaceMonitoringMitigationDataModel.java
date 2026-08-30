package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringMitigation;
import haj.com.service.WorkplaceMonitoringMitigationService;

public class WorkplaceMonitoringMitigationDataModel extends LazyDataModel<WorkplaceMonitoringMitigation> {

	private static final long serialVersionUID = 1L;
	private List<WorkplaceMonitoringMitigation> retorno = new ArrayList<WorkplaceMonitoringMitigation>();
	private WorkplaceMonitoringMitigationService workplaceMonitoringMitigationService = new WorkplaceMonitoringMitigationService();
	private WorkplaceMonitoring workplacemonitoring = null;

	public WorkplaceMonitoringMitigationDataModel(WorkplaceMonitoring workplacemonitoring) {
		super();
		this.workplacemonitoring = workplacemonitoring;
	}

	@Override
	public List<WorkplaceMonitoringMitigation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = workplaceMonitoringMitigationService.sortAndFilter(first, pageSize, sortField, sortOrder, filters, workplacemonitoring);
			setRowCount(workplaceMonitoringMitigationService.count(filters, workplacemonitoring));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(WorkplaceMonitoringMitigation obj) {
		return obj.getId();
	}

	@Override
	public WorkplaceMonitoringMitigation getRowData(String rowKey) {
		for (WorkplaceMonitoringMitigation obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
