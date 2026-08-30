package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.service.lookup.LegacySECTTwentyEightService;

public class LegacySECTTwentyEightDatamodel extends LazyDataModel<LegacySECTTwentyEight> {

	private static final long serialVersionUID = 1L;
	private List<LegacySECTTwentyEight> retorno = new ArrayList<LegacySECTTwentyEight>();
	private LegacySECTTwentyEightService service = new LegacySECTTwentyEightService();

	public LegacySECTTwentyEightDatamodel() {
		super();
	}

	@Override
	public List<LegacySECTTwentyEight> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacySECTTwentyEight(LegacySECTTwentyEight.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacySECTTwentyEight.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacySECTTwentyEight obj) {
		return obj.getId();
	}

	@Override
	public LegacySECTTwentyEight getRowData(String rowKey) {
		for (LegacySECTTwentyEight obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
