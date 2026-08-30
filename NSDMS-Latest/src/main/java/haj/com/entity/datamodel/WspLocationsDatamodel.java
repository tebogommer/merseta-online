package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Wsp;
import haj.com.entity.WspLocations;
import haj.com.entity.WspStrategicPriorities;
import haj.com.service.WspLocationsService;

public class WspLocationsDatamodel extends LazyDataModel<WspLocations> {

	private static final long   serialVersionUID = 1L;
	private List<WspLocations>  retorno          = new ArrayList<WspLocations>();
	private WspLocationsService service          = new WspLocationsService();
	private Wsp                 wsp;

	public WspLocationsDatamodel() {
		super();
	}

	public WspLocationsDatamodel(Wsp wsp) {
		super();
		this.wsp = wsp;
	}

	@Override
	public List<WspLocations> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			if (wsp != null) {
				if (filters == null) filters = new HashMap<String, Object>();
				filters.put("wspID", wsp.getId());
				retorno = service.allWspLocations(first, pageSize, sortField, sortOrder, filters);
				setRowCount(service.count(filters));
			} else {
				retorno = service.allWspLocations(WspLocations.class, first, pageSize, sortField, sortOrder, filters);
				setRowCount(service.count(WspLocations.class, filters));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(WspLocations obj) {
		return obj.getId();
	}

	@Override
	public WspLocations getRowData(String rowKey) {
		for (WspLocations obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

}
