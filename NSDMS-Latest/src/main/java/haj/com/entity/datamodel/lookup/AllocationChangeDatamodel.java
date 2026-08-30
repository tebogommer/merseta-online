package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.AllocationChange;
import haj.com.service.lookup.AllocationChangeService;

public class AllocationChangeDatamodel extends LazyDataModel<AllocationChange> {

	private static final long serialVersionUID = 1L;
	private List<AllocationChange> retorno = new ArrayList<AllocationChange>();
	private AllocationChangeService service = new AllocationChangeService();

	public AllocationChangeDatamodel() {
		super();
	}

	@Override
	public List<AllocationChange> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allAllocationChange(AllocationChange.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(AllocationChange.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(AllocationChange obj) {
		return obj.getId();
	}

	@Override
	public AllocationChange getRowData(String rowKey) {
		for (AllocationChange obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
