package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ActiveContracts;
import haj.com.entity.Addenda;
import haj.com.service.AddendaService;

public class AddendaDatamodel extends LazyDataModel<Addenda> {

	private static final long serialVersionUID = 1L;
	private List<Addenda>     retorno          = new ArrayList<Addenda>();
	private AddendaService    service          = new AddendaService();
	private ActiveContracts   activeContracts;

	public AddendaDatamodel(ActiveContracts activeContracts) {
		super();
		this.activeContracts = activeContracts;
	}

	public AddendaDatamodel() {
		super();
	}

	@Override
	public List<Addenda> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			if (activeContracts == null) {
				retorno = service.allAddenda(Addenda.class, first, pageSize, sortField, sortOrder, filters);
				setRowCount(service.count(Addenda.class, filters));
			} else {
				if (filters == null) filters = new HashMap<String, Object>();
				filters.put("activeContractsID", activeContracts.getId());
				retorno = service.allAddenda(first, pageSize, sortField, sortOrder, filters);
				setRowCount(service.count(filters));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(Addenda obj) {
		return obj.getId();
	}

	@Override
	public Addenda getRowData(String rowKey) {
		for (Addenda obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
