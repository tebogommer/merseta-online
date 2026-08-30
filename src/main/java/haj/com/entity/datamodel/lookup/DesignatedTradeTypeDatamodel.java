package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.service.lookup.DesignatedTradeTypeService;

public class DesignatedTradeTypeDatamodel extends LazyDataModel<DesignatedTradeType> {

	private static final long serialVersionUID = 1L;
	private List<DesignatedTradeType> retorno = new ArrayList<DesignatedTradeType>();
	private DesignatedTradeTypeService service = new DesignatedTradeTypeService();

	public DesignatedTradeTypeDatamodel() {
		super();
	}

	@Override
	public List<DesignatedTradeType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allDesignatedTradeType(DesignatedTradeType.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(DesignatedTradeType.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(DesignatedTradeType obj) {
		return obj.getId();
	}

	@Override
	public DesignatedTradeType getRowData(String rowKey) {
		for (DesignatedTradeType obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
