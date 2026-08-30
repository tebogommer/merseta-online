package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.service.lookup.DesignatedTradeLevelService;

public class DesignatedTradeLevelDatamodel extends LazyDataModel<DesignatedTradeLevel> {

	private static final long serialVersionUID = 1L;
	private List<DesignatedTradeLevel> retorno = new ArrayList<DesignatedTradeLevel>();
	private DesignatedTradeLevelService service = new DesignatedTradeLevelService();

	public DesignatedTradeLevelDatamodel() {
		super();
	}

	@Override
	public List<DesignatedTradeLevel> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allDesignatedTradeLevel(DesignatedTradeLevel.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(DesignatedTradeLevel.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
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
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
