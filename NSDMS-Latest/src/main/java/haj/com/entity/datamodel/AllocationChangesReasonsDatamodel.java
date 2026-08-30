package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AllocationChangesReasons;
import haj.com.service.AllocationChangesReasonsService;

public class AllocationChangesReasonsDatamodel extends LazyDataModel<AllocationChangesReasons> {

	private static final long serialVersionUID = 1L;
	private List<AllocationChangesReasons> retorno = new ArrayList<AllocationChangesReasons>();
	private AllocationChangesReasonsService service = new AllocationChangesReasonsService();

	public AllocationChangesReasonsDatamodel() {
		super();
	}

	@Override
	public List<AllocationChangesReasons> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allAllocationChangesReasons(AllocationChangesReasons.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(AllocationChangesReasons.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(AllocationChangesReasons obj) {
		return obj.getId();
	}

	@Override
	public AllocationChangesReasons getRowData(String rowKey) {
		for (AllocationChangesReasons obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
