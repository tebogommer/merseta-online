package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Wsp;
import haj.com.entity.WspStrategicPriorities;
import haj.com.service.WspStrategicPrioritiesService;

public class WspStrategicPrioritiesDatamodel extends LazyDataModel<WspStrategicPriorities> {

	private static final long             serialVersionUID = 1L;
	private List<WspStrategicPriorities>  retorno          = new ArrayList<WspStrategicPriorities>();
	private WspStrategicPrioritiesService service          = new WspStrategicPrioritiesService();
	private Wsp                           wsp;

	public WspStrategicPrioritiesDatamodel() {
		super();
	}

	public WspStrategicPrioritiesDatamodel(Wsp wsp) {
		super();
		this.wsp = wsp;
	}

	@Override
	public List<WspStrategicPriorities> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			if (wsp != null) {
				if (filters == null) filters = new HashMap<String, Object>();
				filters.put("wspID", wsp.getId());
				retorno = service.allWspStrategicPriorities(first, pageSize, sortField, sortOrder, filters);
				setRowCount(service.count(filters));
			} else {
				retorno = service.allWspStrategicPriorities(WspStrategicPriorities.class, first, pageSize, sortField, sortOrder, filters);
				setRowCount(service.count(WspStrategicPriorities.class, filters));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(WspStrategicPriorities obj) {
		return obj.getId();
	}

	@Override
	public WspStrategicPriorities getRowData(String rowKey) {
		for (WspStrategicPriorities obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
