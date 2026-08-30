import haj.com.dao.CompanyDAO;
import haj.com.entity.Company;
import haj.com.entity.Users;
import haj.com.framework.IDataEntity;

public class TestHibernate {

	public static void main(String[] args) {
//		System.out.println(AgeGroupEnum.EIGHTEEN_TWENTYFOUR.getMiddleAge());
		System.out.println("START");
//		try {
//			//2006/11/17
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//			HistoricalIntersetaTransfersDAO dao = new HistoricalIntersetaTransfersDAO();
//			List<IDataEntity> e = new ArrayList<IDataEntity>();
//
//			List<HistoricalIntersetaTransfers> l = dao.allHistoricalIntersetaTransfers();
//			for (HistoricalIntersetaTransfers ih : l) {
//				ih.setTransactionDateD(sdf.parse(ih.getTransactionDate().trim()));
//				e.add(ih);
//			}
//			dao.updateBatch(e);
			
			
			
			/**
			 * Stanley Testing code
			 */
		CompanyDAO dao = new CompanyDAO();
		try {
			Company a = dao.findByKey((long) 27472);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Long count = dao.count(Company.class);
			Company c =new Company();
			Long cKey = (long) 27472;
			c = (Company) dao.getByClassAndKey(c.getClass() , cKey);
			
			
			Users u = new Users();
			Long uKey = (long) 24;
			u = (Users) dao.getByClassAndKey(u.getClass(), uKey);
			
			IDataEntity gen = dao.getByClassAndKey(u.getClass(), uKey);
			
			gen.getClass();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
			
			
			
			
			
			
			
//			UsersService service = new UsersService();
//			List<Users> list = service.allUsers();
//			List<EmployeesImport> emp = (List<EmployeesImport>) ListUtils.mapToClass(list, EmployeesImport.class);
//			System.out.println(haj.com.utils.CSVUtil.writeCSV(emp, ","));

//			SoftDeleteExampleDAO dao = new SoftDeleteExampleDAO();
/*
	//		dao.create(new SoftDeleteExample("A"));
	//		dao.create(new SoftDeleteExample("B"));
	//		dao.create(new SoftDeleteExample("C"));

			SoftDeleteExample a =  dao.findByKey(1l);
			dao.delete(a);
			if (a==null) {
				System.out.println("1l Not found");
			}

			List<SoftDeleteExample> l = dao.allSoftDeleteExample();
			l.forEach(x -> {
				System.out.println(x.getDescription());
			});

	*/
	//		SoftDeleteExample b =  dao.findByKey(2l);



/*			SoftDeleteChildExample b1 = new SoftDeleteChildExample("B1",b);
			b.addSoftDeleteChildExample(b1);
			SoftDeleteChildExample b2 = new SoftDeleteChildExample("B2",b);
			b.addSoftDeleteChildExample(b2);

			dao.update(b);

			System.out.println(b.getDescription());
			b.getSoftDeleteChildExamples().forEach(v-> {
				System.out.println(v.getChildDescription());
			});
	*/
		//	dao.delete(b);

		//	SoftDeleteExample b1 =  dao.findByKey(2l);
		//	if (b1==null) {
		//		System.out.println("2l Not found");
		//	}

	/*		SoftDeleteExample d = new SoftDeleteExample("D");
			dao.create(d);

			SoftDeleteChildExample d1 = new SoftDeleteChildExample("D1",d);
			d.addSoftDeleteChildExample(d1);
			SoftDeleteChildExample d2 = new SoftDeleteChildExample("D2",d);
			d.addSoftDeleteChildExample(d2);

			dao.update(d);
	*/
		//new ConfigDocService().usedConfigDocProcessEnumInTasks();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		System.out.println("END");
		System.exit(0);
	}
}
