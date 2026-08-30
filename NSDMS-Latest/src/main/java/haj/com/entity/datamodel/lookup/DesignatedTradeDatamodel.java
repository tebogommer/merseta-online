package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DesignatedTrade;
import haj.com.service.lookup.DesignatedTradeService;

public class DesignatedTradeDatamodel extends LazyDataModel<DesignatedTrade> {

	private static final long serialVersionUID = 1L;
	private List<DesignatedTrade> retorno = new ArrayList<DesignatedTrade>();
	private DesignatedTradeService service = new DesignatedTradeService();

	public DesignatedTradeDatamodel() {
		super();
	}

	@Override
	public List<DesignatedTrade> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allDesignatedTrade(DesignatedTrade.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(DesignatedTrade.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(DesignatedTrade obj) {
		return obj.getId();
	}

	@Override
	public DesignatedTrade getRowData(String rowKey) {
		for (DesignatedTrade obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
