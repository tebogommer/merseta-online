package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.DgContractingBulkEntry;
import haj.com.service.DgContractingBulkEntryService;

public class DgContractingBulkEntryDatamodel extends LazyDataModel<DgContractingBulkEntry> {

	private static final long serialVersionUID = 1L;
	private List<DgContractingBulkEntry> retorno = new ArrayList<DgContractingBulkEntry>();
	private DgContractingBulkEntryService service = new DgContractingBulkEntryService();

	public DgContractingBulkEntryDatamodel() {
		super();
	}

	@Override
	public List<DgContractingBulkEntry> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allDgContractingBulkEntry(DgContractingBulkEntry.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(DgContractingBulkEntry.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(DgContractingBulkEntry obj) {
		return obj.getId();
	}

	@Override
	public DgContractingBulkEntry getRowData(String rowKey) {
		for (DgContractingBulkEntry obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
