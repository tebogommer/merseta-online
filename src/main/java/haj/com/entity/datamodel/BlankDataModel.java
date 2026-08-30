package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.service.BlankService;

public class BlankDataModel extends LazyDataModel<Blank> {

	private static final long serialVersionUID = 1L;
	private List<Blank> retorno = new ArrayList<Blank>();
	private BlankService blankService = new BlankService();

	public BlankDataModel() {
		super();
	}

	@Override
	public List<Blank> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = blankService.allBlank(Blank.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(blankService.count(Blank.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(Blank obj) {
		return obj.getId();
	}

	@Override
	public Blank getRowData(String rowKey) {
		for (Blank obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
