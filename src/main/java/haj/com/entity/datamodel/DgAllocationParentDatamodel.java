package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.DgAllocationParent;
import haj.com.service.DgAllocationParentService;

public class DgAllocationParentDatamodel extends LazyDataModel<DgAllocationParent> {

	private static final long serialVersionUID = 1L;
	private List<DgAllocationParent> retorno = new ArrayList<DgAllocationParent>();
	private DgAllocationParentService service = new DgAllocationParentService();

	public DgAllocationParentDatamodel() {
		super();
	}

	@Override
	public List<DgAllocationParent> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allDgAllocationParent(DgAllocationParent.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.countSearch(DgAllocationParent.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(DgAllocationParent obj) {
		return obj.getId();
	}

	@Override
	public DgAllocationParent getRowData(String rowKey) {
		for (DgAllocationParent obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
