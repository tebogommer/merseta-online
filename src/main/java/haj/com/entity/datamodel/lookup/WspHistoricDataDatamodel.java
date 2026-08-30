package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.WspHistoricData;
import haj.com.service.lookup.WspHistoricDataService;

public class WspHistoricDataDatamodel extends LazyDataModel<WspHistoricData> {

	private static final long serialVersionUID = 1L;
	private List<WspHistoricData> retorno = new ArrayList<WspHistoricData>();
	private WspHistoricDataService service = new WspHistoricDataService();

	public WspHistoricDataDatamodel() {
		super();
	}

	@Override
	public List<WspHistoricData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allWspHistoricData(WspHistoricData.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(WspHistoricData.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
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
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
