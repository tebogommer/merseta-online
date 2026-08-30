package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacySectionTwentyEightTradeTest;
import haj.com.service.lookup.LegacySectionTwentyEightTradeTestService;

public class LegacySectionTwentyEightTradeTestDatamodel extends LazyDataModel<LegacySectionTwentyEightTradeTest> {

	private static final long serialVersionUID = 1L;
	private List<LegacySectionTwentyEightTradeTest> retorno = new ArrayList<LegacySectionTwentyEightTradeTest>();
	private LegacySectionTwentyEightTradeTestService service = new LegacySectionTwentyEightTradeTestService();

	public LegacySectionTwentyEightTradeTestDatamodel() {
		super();
	}

	@Override
	public List<LegacySectionTwentyEightTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacySectionTwentyEightTradeTest(LegacySectionTwentyEightTradeTest.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacySectionTwentyEightTradeTest.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacySectionTwentyEightTradeTest obj) {
		return obj.getId();
	}

	@Override
	public LegacySectionTwentyEightTradeTest getRowData(String rowKey) {
		for (LegacySectionTwentyEightTradeTest obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
