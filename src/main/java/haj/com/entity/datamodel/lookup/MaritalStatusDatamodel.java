package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.MaritalStatus;
import haj.com.service.lookup.MaritalStatusService;

public class MaritalStatusDatamodel extends LazyDataModel<MaritalStatus> {

	private static final long serialVersionUID = 1L;
	private List<MaritalStatus> retorno = new ArrayList<MaritalStatus>();
	private MaritalStatusService service = new MaritalStatusService();

	public MaritalStatusDatamodel() {
		super();
	}

	@Override
	public List<MaritalStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allMaritalStatus(MaritalStatus.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(MaritalStatus.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(MaritalStatus obj) {
		return obj.getId();
	}

	@Override
	public MaritalStatus getRowData(String rowKey) {
		for (MaritalStatus obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
