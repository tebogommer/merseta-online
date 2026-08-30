package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Wsp;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.service.WspDGService;

public class WspDatamodel extends LazyDataModel<Wsp> {

	private static final long serialVersionUID = 1L;
	private List<Wsp>         retorno          = new ArrayList<Wsp>();
	private WspDGService      wspService       = new WspDGService();

	public WspDatamodel() {
		super();
	}

	@Override
	public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			if (filters == null) filters = new HashMap<String, Object>();
			filters.put("pendingSignOff", WspStatusEnum.ProjectReview);
			retorno = wspService.allWspByStatus(first, pageSize, sortField, sortOrder, filters);
			setRowCount(wspService.countWspByStatus(filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(Wsp obj) {
		return obj.getId();
	}

	@Override
	public Wsp getRowData(String rowKey) {
		for (Wsp obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
