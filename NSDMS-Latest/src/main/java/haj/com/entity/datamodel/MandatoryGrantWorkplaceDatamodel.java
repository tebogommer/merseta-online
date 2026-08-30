package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.MandatoryGrantWorkplace;
import haj.com.service.MandatoryGrantWorkplaceService;

public class MandatoryGrantWorkplaceDatamodel extends LazyDataModel<MandatoryGrantWorkplace> {

	private static final long serialVersionUID = 1L;
	private List<MandatoryGrantWorkplace> retorno = new ArrayList<MandatoryGrantWorkplace>();
	private MandatoryGrantWorkplaceService service = new MandatoryGrantWorkplaceService();

	public MandatoryGrantWorkplaceDatamodel() {
		super();
	}

	@Override
	public List<MandatoryGrantWorkplace> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allMandatoryGrantWorkplace(MandatoryGrantWorkplace.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(MandatoryGrantWorkplace.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(MandatoryGrantWorkplace obj) {
		return obj.getId();
	}

	@Override
	public MandatoryGrantWorkplace getRowData(String rowKey) {
		for (MandatoryGrantWorkplace obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
