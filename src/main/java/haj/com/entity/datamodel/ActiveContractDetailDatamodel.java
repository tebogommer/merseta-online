package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ActiveContractDetail;
import haj.com.service.ActiveContractDetailService;

public class ActiveContractDetailDatamodel extends LazyDataModel<ActiveContractDetail> {

	private static final long serialVersionUID = 1L;
	private List<ActiveContractDetail> retorno = new ArrayList<ActiveContractDetail>();
	private ActiveContractDetailService service = new ActiveContractDetailService();

	public ActiveContractDetailDatamodel() {
		super();
	}

	@Override
	public List<ActiveContractDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allActiveContractDetail(ActiveContractDetail.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(ActiveContractDetail.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(ActiveContractDetail obj) {
		return obj.getId();
	}

	@Override
	public ActiveContractDetail getRowData(String rowKey) {
		for (ActiveContractDetail obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
