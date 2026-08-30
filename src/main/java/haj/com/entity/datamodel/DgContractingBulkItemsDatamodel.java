package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ActiveContractDetail;
import haj.com.entity.DgContractingBulkItems;
import haj.com.service.DgContractingBulkItemsService;

public class DgContractingBulkItemsDatamodel extends LazyDataModel<DgContractingBulkItems> {

	private static final long serialVersionUID = 1L;
	private List<DgContractingBulkItems> retorno = new ArrayList<DgContractingBulkItems>();
	private DgContractingBulkItemsService service = new DgContractingBulkItemsService();

	public DgContractingBulkItemsDatamodel() {
		super();
	}

	@Override
	public List<DgContractingBulkItems> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allDgContractingBulkItems(DgContractingBulkItems.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(DgContractingBulkItems.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(DgContractingBulkItems obj) {
		return obj.getId();
	}

	@Override
	public DgContractingBulkItems getRowData(String rowKey) {
		for (DgContractingBulkItems obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
