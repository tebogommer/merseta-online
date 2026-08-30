package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.AllocationList;
import haj.com.service.lookup.AllocationListService;

public class AllocationListDatamodel extends LazyDataModel<AllocationList> {

	private static final long serialVersionUID = 1L;
	private List<AllocationList> retorno = new ArrayList<AllocationList>();
	private AllocationListService service = new AllocationListService();
	private boolean findTotals;
	private Integer finYear = null;

	public AllocationListDatamodel() {
		super();
	}

	public AllocationListDatamodel(boolean findTotals) {
		super();
		this.findTotals = findTotals;
	}
	
	public AllocationListDatamodel(boolean findTotals, Integer finYear) {
		super();
		this.findTotals = findTotals;
		this.finYear = finYear;
	}

	@Override
	public List<AllocationList> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			// change request
//			if (finYear != null) {
//				retorno = service.allAllocationListByFinYear(first, pageSize, sortField, sortOrder, filters, finYear);
//				if (findTotals) {
//					for (AllocationList allocationList : retorno) {
//						if (allocationList.getFinYear() != null) {
//							allocationList.setNumberAllocated(service.findTotalsByFinYear(allocationList.getOfoCodes(), allocationList.getFinYear()));
//						} else {
//							allocationList.setNumberAllocated(service.findTotals(allocationList.getOfoCodes()));
//						}
//					}
//				}
//				setRowCount(service.countAllAllocationListByFinYear(filters));
//			} else {
//				retorno = service.allAllocationList(AllocationList.class, first, pageSize, sortField, sortOrder, filters);
//				if (findTotals) {
//					for (AllocationList allocationList : retorno) {
//						if (allocationList.getFinYear() != null) {
//							allocationList.setNumberAllocated(service.findTotalsByFinYear(allocationList.getOfoCodes(), allocationList.getFinYear()));
//						} else {
//							allocationList.setNumberAllocated(service.findTotals(allocationList.getOfoCodes()));
//						}
//					}
//				}
//				setRowCount(service.count(AllocationList.class, filters));
//			}
			
			retorno = service.allAllocationList(AllocationList.class, first, pageSize, sortField, sortOrder, filters);
			if (findTotals) {
				for (AllocationList allocationList : retorno) {
					if (finYear != null) {
						allocationList.setNumberAllocated(service.findTotalsByFinYear(allocationList.getOfoCodes(), finYear));
					} else {
						allocationList.setNumberAllocated(service.findTotals(allocationList.getOfoCodes()));
					}
					
				}
			}
			setRowCount(service.count(AllocationList.class, filters));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(AllocationList obj) {
		return obj.getId();
	}

	@Override
	public AllocationList getRowData(String rowKey) {
		for (AllocationList obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
