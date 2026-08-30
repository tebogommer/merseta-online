package haj.com.sars;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import haj.com.dao.HostingCompanyEmployeesDAO;
import haj.com.dao.SarsFilesDAO;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.SarsEmployerDetailService;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyDetailsService;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLoadLevies.
 */
public class SarsLoadLevies extends AbstractService {

	/** The service. */
	private SarsFilesService service = new SarsFilesService();

	/** The sars levy details service. */
	private  SarsLevyDetailsService sarsLevyDetailsService  =  new SarsLevyDetailsService();

    /** The empservice. */
    private SarsEmployerDetailService empservice  =  new SarsEmployerDetailService();

	/** The sars files. */
	private SarsFiles sarsFiles = null;

	/** The sdf. */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMMM");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM");
	
	private Date arrivalDate;

	/**
	 * Save file.
	 *
	 * @param inputstream the inputstream
	 * @param fileName the file name
	 * @param date the date
	 * @throws Exception the exception
	 */
	public void saveFile(InputStream inputstream, String fileName, Date date) throws Exception {
		this.arrivalDate = null;
		Date to = GenericUtility.getLasttDayOfMonth(date);
		String monthFolder = SARSConstants.sdf2.format(to)+"/";
		createOrReplaceFileAndDirectories(SARSConstants.ZIP_FOLDER+monthFolder);
		File targetFile = new File(SARSConstants.ZIP_FOLDER+monthFolder+fileName);
		FileUtils.copyInputStreamToFile(inputstream, targetFile);
    	unzipFile(SARSConstants.ZIP_FOLDER+monthFolder+fileName, monthFolder,GenericUtility.getFirstDayOfMonth(to));
    	if (this.arrivalDate!=null) {
    		notifyParties(this.arrivalDate);
    	}
	}

	public void notifyParties(Date adate) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SarsLevyDetails sld = sarsLevyDetailsService.find1stByArrivalDate(adate);
					if (sld!=null) {
						SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");	
						String msg = "<p>Dear #NAME#,</p>" + 
									"<p>Please be advised that skills development levies for the month of <strong>#MONTH#</strong> have been successfully uploaded on the NSDMS.</p>" + 
									"<p>The amount of the levies is <strong>R#RAND#</strong></p>" + 
									"<p>Kind regards,</p>" + 
									"<p>#SENIORMANAGERFINANCE#</p>";
						DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
						SarsFilesDAO dao = new SarsFilesDAO();
						SarsLevyDetails a = dao.monthySummary(sld.getSarsFiles().getId());
						sarsLevyDetailsService.fixSign(a);
						 
						msg = msg.replaceAll("#MONTH#", sdf.format(adate));
						msg = msg.replaceAll("#RAND#", df.format(a.getTotal()));
						
						HostingCompanyEmployeesDAO dao2 = new HostingCompanyEmployeesDAO();
						List<HostingCompanyEmployees> l = dao2.findUsersForSarsReporting();
						Users cm = null;
						List<Users> executives = new ArrayList<Users>();
						for (HostingCompanyEmployees hce : l) {
							if (hce.getJobTitle().getRoles().getId().longValue() == 4l) {
								cm = hce.getUsers();
							}
							else {
								executives.add(hce.getUsers());
							}
						}
						
						msg = msg.replaceAll("#SENIORMANAGERFINANCE#", (cm.getFirstName() + " "+cm.getLastName()));
						for (Users u : executives) {
							GenericUtility.sendMail(u.getEmail(), "NOTIFICATION OF SUCCESSFUL UPLOAD OF LEVY FILE", msg.replaceAll("#NAME#", u.getFirstName()), null);
						}
						
					}
						//  Chief Executive Officer, Chief Operations Officer, Chief Finance Officer
					
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
		/*
    
		 */
		
	}

	/**
	 * Unzip file.
	 *
	 * @param zipFile the zip file
	 * @param monthFolder the month folder
	 * @param forDate the for date
	 * @throws Exception the exception
	 */
	public void unzipFile(String zipFile, String monthFolder, Date forDate) throws Exception {
		   createOrReplaceFileAndDirectories(SARSConstants.UNZIP_FOLDER+monthFolder);
	        byte[] buffer = new byte[1024];
	        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
	        ZipEntry zipEntry = zis.getNextEntry();
	        while(zipEntry != null){
	            String fileName = zipEntry.getName();
	            if (fileName.contains("/")  && fileName.contains(".")) {
	              	fileName = fileName.substring(fileName.indexOf("/")+1);
	            }

	            try {
	            	// System.out.println(fileName);
	            	  if (!fileName.contains("__MACOSX") &&  !fileName.startsWith(".")) {
	            		File newFile = new File(SARSConstants.UNZIP_FOLDER+monthFolder + fileName);
	            		FileOutputStream fos = new FileOutputStream(newFile);
	            		int len;
	            		while ((len = zis.read(buffer)) > 0) {
	            			fos.write(buffer, 0, len);
	            		}
	            		fos.close();
	            	  }
	            } catch (java.io.FileNotFoundException fe) {
	            	   logger.info(fe);
	            }
	            		zipEntry = zis.getNextEntry();

	        }
	        zis.closeEntry();
	        zis.close();
	        checkIfDHETdidOnlyEXEFileType(SARSConstants.UNZIP_FOLDER+monthFolder);
	        deleteRedundantFiles(monthFolder);
	        loadEmployerFile(monthFolder,forDate);
	        loadLevies(SARSConstants.UNZIP_FOLDER+monthFolder);
	       // resolveFK();
	}



	private void checkIfDHETdidOnlyEXEFileType(String target) throws Exception {
	    File directory = new File(target);
	    boolean onlyexe = true;
	    if (directory.exists()) {
	        for (File file : directory.listFiles()) {
	           if (!file.getName().toLowerCase().endsWith(".exe")) {
	        	     onlyexe = false;
	           }
	        }
	        if (onlyexe) throw new Exception("Unfortuabltely this LEVY file is not in the correct format. It's a self exrtacting zip file. Please unzip the files and zip it into the corrext format.");
	    }


	}

	/**
	 * Creates the or replace file and directories.
	 *
	 * @param target the target
	 * @throws Exception the exception
	 */
	public void createOrReplaceFileAndDirectories(String target) throws Exception{
	    Path path = Paths.get(target);
	    File directory = new File(target);
	    if (directory.exists()) {
	        for (File file : directory.listFiles()) {
	            file.delete();
	        }
	        directory.delete();
	    }
	    Files.createDirectory(path);
	}

	/**
	 * Delete redundant files.
	 *
	 * @param monthFolder the month folder
	 */
	public void deleteRedundantFiles(String monthFolder) {
		deleteFilesWithExtension(SARSConstants.UNZIP_FOLDER+monthFolder,"TXT");
		deleteFilesWithExtension(SARSConstants.UNZIP_FOLDER+monthFolder,"exe");
		deleteFilesWithExtension(SARSConstants.UNZIP_FOLDER+monthFolder,"zip");
		deleteFilesWithExtension(SARSConstants.UNZIP_FOLDER+monthFolder,"TXT.SDL");
	}


	/**
	 * Delete files with extension.
	 *
	 * @param directoryName the directory name
	 * @param extension the extension
	 */
	public void deleteFilesWithExtension(final String directoryName, final String extension) {
	    final File dir = new File(directoryName);
	    final String[] allFiles = dir.list();
	    for (final String file : allFiles) {
	        if (file.endsWith(extension)) {
	            new File(directoryName + "/" + file).delete();
	        }
	    }
	}

	/**
	 * Load employer file.
	 *
	 * @param monthFolder the month folder
	 * @param forMonth the for month
	 * @throws Exception the exception
	 */
	public void loadEmployerFile(String monthFolder, Date forMonth) throws Exception {
		 try (Stream<Path> stream = Files.list(Paths.get(SARSConstants.UNZIP_FOLDER+ monthFolder))) {
			    String fileName = stream
			        .map(String::valueOf)
			        .filter(path ->  (  path.endsWith(".SDL")  && path.contains("Employers")  && !path.contains("TXT") )  )
			        .sorted()				        //.collect(Collectors.joining("; "));
			        .collect(Collectors.joining(""));

			        String td = fileName.substring(fileName.lastIndexOf('_')+1);
			        td = td.substring(0,td.indexOf('.'));
			     //   parseAndloadEmployerFile(fileName,GenericUtility.getLasttDayOfMonth(SARSConstants.sdf.parse(td)));
			        parseAndloadEmployerFile(fileName,GenericUtility.getLasttDayOfMonth(forMonth));
			}
	}


	/**
	 * Parses the andload employer file.
	 *
	 * @param file the file
	 * @param forMonth the for month
	 * @throws Exception the exception
	 */
	public void parseAndloadEmployerFile(String file,Date forMonth) throws Exception {
		logger.info("Start parseAndloadEmployerFile");
		String sdl = new String (Files.readAllBytes(new File(file).toPath()),Charset.forName("UTF-8"));

		String sdlF = SARSConstants.EMPLOYER_HEADING + "\n"+sdl;
		sdlF = sdlF.replaceAll( ";", " ");
    		sdlF = sdlF.replaceAll( "\\|", ";");
    		sdlF = sdlF.replaceAll( "\"", "'");
    		//  "
    		@SuppressWarnings("unchecked")
			List<SarsEmployerDetail> levies = (List<SarsEmployerDetail>) (List<?>) new CSVUtil().getObjects(SarsEmployerDetail.class, new ByteArrayInputStream(sdlF.getBytes()), ";");

    		logger.info("Start inserting Employer data : total must be: "+levies.size());
    		SarsFiles sf = service.findByDate(forMonth);
    		if (sf !=null) 	{
    			service.delete(sf);
    		}
    		sf = new SarsFiles();
    		sf.setCanProcessMgPayments(true);
    		sf.setForMonth(forMonth);
    		sf.setLoadedToGP(Boolean.FALSE);
    		service.create(sf);
    		this.sarsFiles = sf;
    		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
    		int i = 1, progress = 1;
    		for (SarsEmployerDetail a : levies) {
    			a.setSarsFiles(sf);
    			try {a.setNoEmployesAccordingToSARS(Integer.parseInt(a.getSarsData27().trim())); } catch (Exception e) {};
    			// post code upload
    			try {
    				if (a.getSarsData12() == null || a.getSarsData12().trim().isEmpty()) {
    					a.setEmployerPostCode("10000");
					} else {
						a.setEmployerPostCode(calculatePostCode(a.getSarsData12().trim()));
					}
    			} catch (Exception e) {
    				logger.fatal(e);
    			};
    			if (a.getTradingStatus()==null) a.setTradingStatus("0");
    			entityList.add(a);
    			// for testing and viewing all data 
//    			SarsEmployerDetailAllDataFT allData = new SarsEmployerDetailAllDataFT();
//    			org.apache.commons.beanutils.BeanUtils.copyProperties(allData, a);
//    			allData.setId(null);
//    			allData.setSarsEmployerDetail(a);
//    			allData.setSarsFiles(sf);
//    			entityList.add(allData);
    			
    			if (i==10000) {
    				service.getDao().createBatch(entityList);
    				entityList.clear();
    				i=0;
    				logger.info("Done "+progress + " of "+levies.size());
    			}
    			i++;progress++;
		}
    	    if (entityList.size() > 0) {
    	    	service.getDao().createBatch(entityList);
    	    }
    	    logger.info("Done "+progress + " of "+levies.size());
    	    // Populate the new clients from EMPLOYERS/COMPANIES
    	    empservice.populateNewFromSARSThreaded(sf.getId());

	}
	
	private String calculatePostCode(String postCodeData) throws Exception {
		if (postCodeData != null && !postCodeData.trim().isEmpty() && postCodeData.contains("/")) {
			postCodeData = postCodeData.replace("/", "");
		}
		if (postCodeData != null && !postCodeData.trim().isEmpty() && postCodeData.trim().length() > 0 && postCodeData.trim().length() < 4) {
			StringBuilder builder = new StringBuilder();
			int missing = 4 - postCodeData.trim().length();
			for (int i = 1; i <= missing; i++) {
				builder.append("0");
			}
			builder.append(postCodeData.trim());
			return builder.toString();
		} else {
			return postCodeData;
		}
	}

	   /**
   	 * Load leviesfrom file.
   	 *
   	 * @param file the file
   	 */
   	@SuppressWarnings("unchecked")
		public synchronized void   loadLeviesfromFile(String file) {
		   logger.info("loadLeviesfromFile file : "+file);
	        try {

	          	String sdl = new String (Files.readAllBytes(new File(file).toPath()),Charset.forName("UTF-8"));
	            	String sdlF = SARSConstants.LEVY_HEADING + "\n"+sdl;

	            	sdlF = sdlF.replaceAll( "\\|", ";");
	            	List<SarsLevyDetails> levies = (List<SarsLevyDetails>) (List<?>) new CSVUtil().getObjects(SarsLevyDetails.class, new ByteArrayInputStream(sdlF.getBytes()), ";");
	          	List<IDataEntity> entityList = new ArrayList<IDataEntity>();
	            boolean ft = true;	
	          	for (SarsLevyDetails a : levies) {
	          			if (ft) {
	          				this.arrivalDate = a.getArrivalDate1();
	          				ft=false;
	          			}
	            		 SarsLevyDetails b =  (SarsLevyDetails)a.clone();

	            			if ( b.getSchemeYear()==null) {

	            			       a.setTotal(b.getPenalty());
	            			       a.setPenalty(b.getTotal());
	            			}
	            		
	            		a.setSarsFiles(sarsFiles);
	            		a.setCanAppearOnMgPayments(true);
	            		entityList.add(a);
				}
	             service.getDao().createBatch(entityList);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    }



		/**
		 * Load levies.
		 *
		 * @param folder the folder
		 * @throws Exception the exception
		 */
		public  void loadLevies(String folder) throws Exception {
	 		 try (Stream<Path> stream = Files.list(Paths.get(folder))) {
				    List<String> l = stream
				        .map(String::valueOf)
				        .filter(path ->  (  path.endsWith(".SDL")  && !path.contains("Employers") && !path.contains("TXT") && !path.contains("COMBINED"))  )
				        .sorted()
				        .collect(Collectors.toList());

				    l.forEach(a-> {
				    	loadLeviesfromFile(a);
				    });
				}
	    }


		/**
		 * Resolve FK.
		 */
		public void resolveFK() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {

					logger.info("Start resolveFK");
		    	    		List<SarsLevyDetails> l2 =  sarsLevyDetailsService.allSarsLevyDetailsNoFK();
		    	    		for (SarsLevyDetails sarsLevyDetails : l2) {
		    	    			sarsLevyDetails.setSarsEmployerDetail(empservice.findByRefNo(sarsLevyDetails.getRefNo(),sarsLevyDetails.getSarsFiles()));
		    	    			sarsLevyDetailsService.update(sarsLevyDetails);
		    	    		}
		    	    		logger.info("Done resolveFK");

					} catch (Exception e) {
						logger.fatal(e);
					}
				}
			}).start();
		}


	    /**
    	 * Resolve FK.
    	 *
    	 * @param forDate the for date
    	 * @throws Exception the exception
    	 */
    	public  void resolveFK(Date forDate) throws Exception {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
				    	    List<SarsLevyDetails> l =  sarsLevyDetailsService.allSarsLevyDetailsNoFK(forDate);
				    	    for (SarsLevyDetails sarsLevyDetails : l) {
				    	      	sarsLevyDetails.setSarsEmployerDetail(empservice.findByRefNo(sarsLevyDetails.getRefNo(),sarsLevyDetails.getSarsFiles()));
				    	      	sarsLevyDetailsService.update(sarsLevyDetails);
				    	    }
					} catch (Exception e) {
						logger.fatal(e);
					}
				}
			}).start();
    }



		/**
		 * Reorg folders.
		 *
		 * @param folder the folder
		 * @throws Exception the exception
		 */
		public  void reorgFolders(String folder) throws Exception {
	 		 try (Stream<Path> stream = Files.list(Paths.get(folder))) {
				    List<String> l = stream
				        .map(String::valueOf)
				       // .filter(path ->  (  path.endsWith(".SDL")  && !path.contains("Employers") && !path.contains("TXT"))  )
				        .sorted()
				        .collect(Collectors.toList());

				    l.forEach(a-> {
				      	allFilesFolders(a);
				    });
				}
	    }

		/**
		 * All files folders.
		 *
		 * @param folder the folder
		 */
		public  void allFilesFolders(String folder) {

			 try {
				try (Stream<Path> stream = Files.list(Paths.get(folder))) {
					    List<String> l = stream
					        .map(String::valueOf)
					        .filter(path ->  (  path.endsWith(".zip")  )  )
					        .sorted()
					        .collect(Collectors.toList());

					    l.forEach(a-> {
					    	 try {
					    	   String t = a.substring(a.lastIndexOf('/')+1).toLowerCase().replaceAll(" ", "_").replaceAll("17-", "");
					    	   if (t.indexOf('.')>-1) {
					    		   t = t.substring(0, t.indexOf('.'));
					    	   }
					    	   String year = t.substring(t.indexOf('_')+1);
					    	   String fn = year +"_"+t.substring(0,t.indexOf('_'));
					    	   System.out.println(t  + " \t"+year + "\t"+fn);
					    	   Path path = Paths.get("/Users/hendrik/Desktop/demo/SARSlevyFiles/Post2013Organized/"+fn);
					    	   Files.createDirectory(path);
					    	   Files.copy(Paths.get(a), Paths.get("/Users/hendrik/Desktop/demo/SARSlevyFiles/Post2013Organized/"+fn+"/17-sdl.zip"));
					    	 } catch (Exception e) {}
					    	  // new File(a).delete();
					    });
					}
			} catch (IOException e) {
				logger.fatal(e);
			}
		}



		/**
		 * Test unzip file.
		 *
		 * @param zipFile the zip file
		 * @param monthFolder the month folder
		 * @throws Exception the exception
		 */
		public void testUnzipFile(String zipFile, String monthFolder) throws Exception {
			   createOrReplaceFileAndDirectories(SARSConstants.UNZIP_FOLDER+monthFolder);



		        byte[] buffer = new byte[1024];
		        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		        ZipEntry zipEntry = zis.getNextEntry();
		        while(zipEntry != null){
		            String fileName = zipEntry.getName();
		            if (fileName.contains("/")  && fileName.contains(".")) {
		              	fileName = fileName.substring(fileName.indexOf("/")+1);
		            }

		            try {
		            	  if (!fileName.contains("__MACOSX")) {
		            		File newFile = new File(SARSConstants.UNZIP_FOLDER+monthFolder + fileName);
		            		FileOutputStream fos = new FileOutputStream(newFile);
		            		int len;
		            		while ((len = zis.read(buffer)) > 0) {
		            			fos.write(buffer, 0, len);
		            		}
		            		fos.close();
		            	  }
		            } catch (java.io.FileNotFoundException fe) {
		            	   //logger.info(fe);
		            }
		            		zipEntry = zis.getNextEntry();

		        }
		        zis.closeEntry();
		        zis.close();

		        deleteRedundantFiles(monthFolder);
		//        loadEmployerFile(monthFolder);
		//        loadLevies(SARSConstants.UNZIP_FOLDER+monthFolder);
		//        resolveFK(forDate);
		}


		/**
		 * Zip files.
		 *
		 * @param srcFiles the src files
		 * @param monthFolder the month folder
		 * @throws Exception the exception
		 */
		public void zipFiles(List<String> srcFiles,String monthFolder) throws Exception {
			createOrReplaceFileAndDirectories(SARSConstants.ZIP_FOLDER+monthFolder);

	        FileOutputStream fos = new FileOutputStream(SARSConstants.ZIP_FOLDER+monthFolder+"17-sdl.zip");
	        ZipOutputStream zipOut = new ZipOutputStream(fos);
	        for (String srcFile : srcFiles) {
	            File fileToZip = new File(srcFile);
	            FileInputStream fis = new FileInputStream(fileToZip);
	            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
	            zipOut.putNextEntry(zipEntry);

	            byte[] bytes = new byte[1024];
	            int length;
	            while((length = fis.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            fis.close();
	        }
	        zipOut.close();
	        fos.close();
		}


		/**
		 * Prep for zip files.
		 *
		 * @param folder the folder
		 * @param monthFolder the month folder
		 * @throws Exception the exception
		 */
		public void prepForZipFiles(String folder, String monthFolder) throws Exception {
	 		 try (Stream<Path> stream = Files.list(Paths.get(folder))) {
				    List<String> l = stream
				        .map(String::valueOf)
				       // .filter(path ->  (  path.endsWith(".SDL")  && !path.contains("Employers") && !path.contains("TXT"))  )
				        .sorted()
				        .collect(Collectors.toList());



				       try { 	zipFiles(l,monthFolder); } catch (Exception e) {logger.fatal(e);}


				}
		}

		/**
		 * Zip files.
		 *
		 * @param folder the folder
		 * @throws Exception the exception
		 */
		public void zipFiles(String folder) throws Exception {
	 		 try (Stream<Path> stream = Files.list(Paths.get(folder))) {
				    List<String> l = stream
				        .map(String::valueOf)
				       // .filter(path ->  (  path.endsWith(".SDL")  && !path.contains("Employers") && !path.contains("TXT"))  )
				        .sorted()
				        .collect(Collectors.toList());

				    l.forEach(a-> {
				      	System.out.println(a + "\t" + a.substring(a.lastIndexOf(File.separatorChar)+1));
				       try { 	prepForZipFiles(a, a.substring(a.lastIndexOf(File.separatorChar)+1)+"/"); } catch (Exception e) {logger.fatal(e);}

				    });
				}
		}


		/**
		 * Test zip files.
		 *
		 * @param folder the folder
		 * @throws Exception the exception
		 */
		public  void testZipFiles(String folder) throws Exception {
	 		 try (Stream<Path> stream = Files.list(Paths.get(folder))) {
				    List<String> l = stream
				        .map(String::valueOf)
				       // .filter(path ->  (  path.endsWith(".SDL")  && !path.contains("Employers") && !path.contains("TXT"))  )
				        .sorted()
				        .collect(Collectors.toList());

				    l.forEach(a-> {
				      	System.out.println(a + "\t" + a.substring(a.lastIndexOf(File.separatorChar)+1));
				       try { 	testUnzipFile(a+"/17-sdl.zip", a.substring(a.lastIndexOf(File.separatorChar)+1)+"/"); } catch (Exception e) {logger.fatal(e);}

				    });
				}
	    }


		/**
		 * Load batch.
		 *
		 * @throws Exception the exception
		 */
		public  void loadBatch() throws Exception {
			//ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

	 		 try (Stream<Path> stream = Files.list(Paths.get(SARSConstants.ZIP_FOLDER+"/missingFiles/formatted/"))) {
				    List<String> l = stream
				        .map(String::valueOf)
				       // .filter(path ->  (  path.endsWith(".SDL")  && !path.contains("Employers") && !path.contains("TXT"))  )
				        .sorted()
				        .collect(Collectors.toList());

				    l.forEach(a-> {
				    	if (!a.contains(".DS_Store")) {
				    	  try {
				    		String month =  a.substring(a.lastIndexOf(File.separatorChar)+1);
				      	Date forDate = GenericUtility.getLasttDayOfMonth(sdf.parse(month));

				      	System.out.println(a+"/17-sdl.zip" + "\t" + month+"\t\t"+forDate);

				      	unzipFile(a+"/17-sdl.zip", month+"/", forDate);
				      /*
				      	executor.submit(() -> {
				      		try {
						      unzipFile(a+"/17-sdl.zip", month+"/", forDate);
						      System.out.println(a + "\t\t" + month+"\t"+forDate);
				      		}catch (Exception e) { logger.fatal(e);}
						    return null;
						});
				      	*/
				    	  }
				    	  catch (Exception e) {
				    		  logger.fatal(e);
				    	  }
				    	}
				    });

				}

	 	/*	  executor.shutdown();
	 	        while (!executor.isTerminated()) {
	 	        }
	 	        System.out.println("Finished all threads");
		*/


		}


		public  void formatMissing() throws Exception {
			//ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

	 		 try (Stream<Path> stream = Files.list(Paths.get(SARSConstants.ZIP_FOLDER+"/missingFiles/"))) {
				    List<String> l = stream
				        .map(String::valueOf)
				       // .filter(path ->  (  path.endsWith(".SDL")  && !path.contains("Employers") && !path.contains("TXT"))  )
				        .filter(path ->  (  path.endsWith(".zip")   ))
				        .sorted()
				        .collect(Collectors.toList());

				    l.forEach(a-> {
				    	if (!a.contains(".DS_Store")) {
				    	  try {
				    		String month =  a.substring(a.lastIndexOf(File.separatorChar)+1);
				      	Date forDate = GenericUtility.getLasttDayOfMonth(sdf2.parse(month));

				      	System.out.println(a + "\t\t" + month+"\t"+forDate + "\t"+sdf.format(forDate));
				      	createOrReplaceFileAndDirectories(SARSConstants.ZIP_FOLDER+"/missingFiles/formatted/"+sdf.format(forDate));


				      	    FileUtils.copyFile(new File(a), new File(SARSConstants.ZIP_FOLDER+"/missingFiles/formatted/"+sdf.format(forDate) +"/17-sdl.zip"));

		//		      	unzipFile(a+"/17-sdl.zip", month+"/", forDate);
				      /*
				      	executor.submit(() -> {
				      		try {
						      unzipFile(a+"/17-sdl.zip", month+"/", forDate);
						      System.out.println(a + "\t\t" + month+"\t"+forDate);
				      		}catch (Exception e) { logger.fatal(e);}
						    return null;
						});
				      	*/
				    	  }
				    	  catch (Exception e) {
				    		  logger.fatal(e);
				    	  }
				    	}
				    });

				}

	 	/*	  executor.shutdown();
	 	        while (!executor.isTerminated()) {
	 	        }
	 	        System.out.println("Finished all threads");
		*/


		}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Start");
			SarsLoadLevies service = new SarsLoadLevies();

			//  17-sdl.zip

			//service.parseAndloadEmployerFile("/Users/hendrik/Desktop/x.csv",new Date());
			//service.prepForZipFiles("/Users/hendrik/Downloads/temp/xxxx/APRIL 2015 17-sdl", "xxx");
			//Step 01
			//String folder01 = "/Users/hendrik/Desktop/demo/SARSlevyFiles/Post2013/";
			//service.reorgFolders(folder01);


			//Step 02
			//String folder02 = "/Users/hendrik/Desktop/demo/SARSlevyFiles/Post2013Organized/";
			//service.testZipFiles(folder02);

			//Step 03
			//String folder02 = "/Users/hendrik/Desktop/demo/SARSlevyFiles/Post2013Organized/";
		//	service.zipFiles(SARSConstants.UNZIP_FOLDER);



	//		String file = "/Users/hendrik/Downloads/SARS/unzip/";
	//		service.zipFiles(file);
		//	service.unzipFile(SARSConstants.ZIP_FOLDER+"September2017/"+SARSConstants.LEVY_FILE_NAME, "September2017/");
           // service.loadEmployerFile("September2017/");


			// TEST LOAD of FILE
/*			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMMM");
			Date forDate = GenericUtility.getLasttDayOfMonth(sdf.parse("2000_september"));
			String zipFile = "/Users/hendrik/Downloads/SARS/zip/2000_september/17-sdl.zip";
		    service.unzipFile(zipFile, "2000_september/", forDate);
*/


			//service.formatMissing();
			service.loadBatch();



		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		System.exit(0);
	}



}
