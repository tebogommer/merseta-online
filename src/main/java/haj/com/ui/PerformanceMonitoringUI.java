package haj.com.ui;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.apache.commons.lang3.SystemUtils;
import org.primefaces.model.chart.MeterGaugeChartModel;

import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "performanceMonitoringUI")
@ViewScoped
public class PerformanceMonitoringUI extends AbstractUI {

	private long availableProcessors;
	private long freeMemory;
	private long maxMemory;
	private long totalMemory;
	private long totalThreadCount;

	/** JVM bean */
	private ThreadMXBean threadMXBean;

	/** machine drive */
	private File drive;

	/** Monitoring disk usage */
	private double totalDiskSpace;
	private double freeDiskSpace;
	private double freeUsableDiskSpace;

	private int BYTE_TO_GIGABYTE_METRIC = 1073741824;
	private int BYTE_TO_MEGABYTE_METRIC = 1048576;

	/** ManagementFactory class to query the memory available to the JVM */
	private MemoryMXBean memoryMXBean;

	/** monitoring memory usage */
	private double initialHeapMemoryUsage;
	private double usedHeapmemoryUsage;
	private double maxHeapMemoryUsage;
	private double comittedHeapMemoryUsage;
	
	/** monitor file system */
	private double fileSystemFreeSpace;
	private double fileSystemTotalSpace; 
	private double fileSystemUsableSpace;
	
	private MeterGaugeChartModel meterGaugeModelDiskUsage, meterGaugeModelMemoryUsage, meterGaugeModelJVM, meterGaugeModelFileSystem;
	
	private int fileSystemUsagePercent;
	private int memoryUsagePercent;
	private int diskUsagePercent; 
	private int jvmUsagePercent;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepares default for page load
	 * @throws Exception
	 */
	private void runInit() throws Exception {
		populateServerInfo();
		monitorDiskUsage();
		monitorMemoryUsage();
		monitorFileSystemUsage();
	}

	/**
	 * Populates file system information of usage
	 * @throws Exception
	 */
	private void monitorFileSystemUsage() throws Exception {
		this.fileSystemFreeSpace = 0.0; 
		this.fileSystemTotalSpace = 0.0; 
		this.fileSystemUsableSpace = 0.0;
		/* Get a list of all filesystem roots on this system */
		File[] roots = File.listRoots();

		/* For each filesystem root, print some info */
		for (File root : roots) {
			this.fileSystemFreeSpace += root.getFreeSpace();
			this.fileSystemTotalSpace += root.getTotalSpace();
			this.fileSystemUsableSpace += root.getUsableSpace();
		}
		
		this.fileSystemFreeSpace =  this.fileSystemFreeSpace / BYTE_TO_GIGABYTE_METRIC;
		this.fileSystemTotalSpace =  this.fileSystemTotalSpace / BYTE_TO_GIGABYTE_METRIC;
		this.fileSystemUsableSpace = this.fileSystemUsableSpace / BYTE_TO_GIGABYTE_METRIC;
		double obtainedValue = fileSystemTotalSpace - fileSystemUsableSpace;
		this.fileSystemUsagePercent = (int) Math.round(GenericUtility.calculatePercentage(obtainedValue, fileSystemTotalSpace));
	}

	/**
	 * Populates the server memory usage
	 * @throws Exception
	 */
	private void monitorMemoryUsage() throws Exception {
		// query heap memory
		this.memoryMXBean = ManagementFactory.getMemoryMXBean();
		// Initial: Initial memory the JVM requests from the OS during startup
		this.initialHeapMemoryUsage = (Double.valueOf(memoryMXBean.getHeapMemoryUsage().getInit()) / BYTE_TO_MEGABYTE_METRIC);
		// Used: The current amount of memory used by the JVM
		this.usedHeapmemoryUsage = (Double.valueOf(memoryMXBean.getHeapMemoryUsage().getUsed()) / BYTE_TO_MEGABYTE_METRIC);
		// Max: The maximum memory available to the JVM. If this limit is reached an
		// OutOfMemoryException may be thrown
		this.maxHeapMemoryUsage = (Double.valueOf(memoryMXBean.getHeapMemoryUsage().getMax()) / BYTE_TO_MEGABYTE_METRIC);
		// Committed: The amount of memory guaranteed to be available to the JVM
		this.comittedHeapMemoryUsage = (Double.valueOf(memoryMXBean.getHeapMemoryUsage().getCommitted()) / BYTE_TO_MEGABYTE_METRIC);
		this.memoryUsagePercent = (int) Math.round(GenericUtility.calculatePercentage(usedHeapmemoryUsage, comittedHeapMemoryUsage));
	}

	/**
	 * Populates drive usage information
	 * @throws Exception
	 */
	private void monitorDiskUsage() throws Exception {
		if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC_OSX) {
			drive = new File("/");
		} else if (SystemUtils.IS_OS_WINDOWS) {
			drive = new File("C:");
		}
		// converts the bytes to Gigs
		this.totalDiskSpace = (Double.valueOf(drive.getTotalSpace()) / BYTE_TO_GIGABYTE_METRIC);
		this.freeDiskSpace = (Double.valueOf(drive.getFreeSpace()) / BYTE_TO_GIGABYTE_METRIC);
		this.freeUsableDiskSpace = (Double.valueOf(drive.getUsableSpace()) / BYTE_TO_GIGABYTE_METRIC);
		double obtainedValue = totalDiskSpace - freeUsableDiskSpace;
		this.diskUsagePercent =  (int) Math.round(GenericUtility.calculatePercentage(obtainedValue, totalDiskSpace));
	}

	/**
	 * Populates server information
	 * @throws Exception
	 */
	private void populateServerInfo() throws Exception {
		this.availableProcessors = Runtime.getRuntime().availableProcessors();
		this.totalMemory = (Runtime.getRuntime().totalMemory() / 1024) / 1024;
		this.threadMXBean = ManagementFactory.getThreadMXBean();
		this.totalThreadCount = threadMXBean.getThreadCount();
		/* This will return Long.MAX_VALUE if there is no preset limit */
		this.maxMemory = (Runtime.getRuntime().maxMemory() / 1024) / 1024;
		this.freeMemory = (Runtime.getRuntime().freeMemory() / 1024) / 1024;
		long obtainedValue = totalMemory - freeMemory;
		this.jvmUsagePercent = (int) Math.round(GenericUtility.calculatePercentage(obtainedValue, totalMemory));
	}
	
	public void populateServerStats() {
		try {
			populateServerInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void monitorDiskStatus() {
		try {
			monitorDiskUsage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void monitorMemoryStats() {
		try {
			monitorMemoryUsage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void monitorFileSystemStats() {
		try {
			monitorFileSystemUsage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Builds chart model of information
	 * @param gauge The model to build
	 * @param maxInterval The maximum number in range
	 * @param usedInterval The actual number in range
	 * @param title The model title
	 * @param label The model label
	 * @param marker The marker of the model
	 * @param useMarker Indicator if the marker needs to be used
	 * @return MeterGaugeChartModel The built model
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private MeterGaugeChartModel initMeterGaugeModel(MeterGaugeChartModel gauge, double maxInterval, double usedInterval, String title, String label, Double marker, boolean useMarker) throws Exception {
		List<Number> intervals = new ArrayList<>();
		int interval = (int) (maxInterval / 5);
		int tempInterval = 0;
		intervals.add(tempInterval + interval);
		intervals.add(tempInterval += interval);
		intervals.add(tempInterval += interval);
		intervals.add(tempInterval += interval);
		intervals.add(tempInterval += interval);
		intervals.add(tempInterval += interval);
		gauge = new MeterGaugeChartModel(useMarker ? marker : usedInterval, intervals);
		gauge.setTitle(title);
		gauge.setGaugeLabel(label);
		gauge.setGaugeLabelPosition("bottom");
		gauge.setShowTickLabels(true);
		gauge.setLabelHeightAdjust(110);
		gauge.setIntervalOuterRadius(100);
		gauge.setSeriesColors("66cc66,e7a526,93b75f,E7E658,cc6666");
		return gauge;
	}

	// getters and setters
	public long getAvailableProcessors() {
		return availableProcessors;
	}

	public void setAvailableProcessors(long availableProcessors) {
		this.availableProcessors = availableProcessors;
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getTotalThreadCount() {
		return totalThreadCount;
	}

	public void setTotalThreadCount(long totalThreadCount) {
		this.totalThreadCount = totalThreadCount;
	}

	public ThreadMXBean getThreadMXBean() {
		return threadMXBean;
	}

	public void setThreadMXBean(ThreadMXBean threadMXBean) {
		this.threadMXBean = threadMXBean;
	}

	public File getDrive() {
		return drive;
	}

	public void setDrive(File drive) {
		this.drive = drive;
	}

	public double getTotalDiskSpace() {
		return totalDiskSpace;
	}

	public void setTotalDiskSpace(double totalDiskSpace) {
		this.totalDiskSpace = totalDiskSpace;
	}

	public double getFreeDiskSpace() {
		return freeDiskSpace;
	}

	public void setFreeDiskSpace(double freeDiskSpace) {
		this.freeDiskSpace = freeDiskSpace;
	}

	public double getFreeUsableDiskSpace() {
		return freeUsableDiskSpace;
	}

	public void setFreeUsableDiskSpace(double freeUsableDiskSpace) {
		this.freeUsableDiskSpace = freeUsableDiskSpace;
	}

	public int getBYTE_TO_GIGABYTE_METRIC() {
		return BYTE_TO_GIGABYTE_METRIC;
	}

	public int getBYTE_TO_MEGABYTE_METRIC() {
		return BYTE_TO_MEGABYTE_METRIC;
	}

	public MemoryMXBean getMemoryMXBean() {
		return memoryMXBean;
	}

	public void setMemoryMXBean(MemoryMXBean memoryMXBean) {
		this.memoryMXBean = memoryMXBean;
	}

	public double getInitialHeapMemoryUsage() {
		return initialHeapMemoryUsage;
	}

	public void setInitialHeapMemoryUsage(double initialHeapMemoryUsage) {
		this.initialHeapMemoryUsage = initialHeapMemoryUsage;
	}

	public double getUsedHeapmemoryUsage() {
		return usedHeapmemoryUsage;
	}

	public void setUsedHeapmemoryUsage(double usedHeapmemoryUsage) {
		this.usedHeapmemoryUsage = usedHeapmemoryUsage;
	}

	public double getMaxHeapMemoryUsage() {
		return maxHeapMemoryUsage;
	}

	public void setMaxHeapMemoryUsage(double maxHeapMemoryUsage) {
		this.maxHeapMemoryUsage = maxHeapMemoryUsage;
	}

	public double getComittedHeapMemoryUsage() {
		return comittedHeapMemoryUsage;
	}

	public void setComittedHeapMemoryUsage(double comittedHeapMemoryUsage) {
		this.comittedHeapMemoryUsage = comittedHeapMemoryUsage;
	}

	public double getFileSystemFreeSpace() {
		return fileSystemFreeSpace;
	}

	public void setFileSystemFreeSpace(double fileSystemFreeSpace) {
		this.fileSystemFreeSpace = fileSystemFreeSpace;
	}

	public double getFileSystemTotalSpace() {
		return fileSystemTotalSpace;
	}

	public void setFileSystemTotalSpace(double fileSystemTotalSpace) {
		this.fileSystemTotalSpace = fileSystemTotalSpace;
	}

	public double getFileSystemUsableSpace() {
		return fileSystemUsableSpace;
	}

	public void setFileSystemUsableSpace(double fileSystemUsableSpace) {
		this.fileSystemUsableSpace = fileSystemUsableSpace;
	}

	public MeterGaugeChartModel getMeterGaugeModelDiskUsage() {
		return meterGaugeModelDiskUsage;
	}

	public void setMeterGaugeModelDiskUsage(MeterGaugeChartModel meterGaugeModelDiskUsage) {
		this.meterGaugeModelDiskUsage = meterGaugeModelDiskUsage;
	}

	public MeterGaugeChartModel getMeterGaugeModelMemoryUsage() {
		return meterGaugeModelMemoryUsage;
	}

	public void setMeterGaugeModelMemoryUsage(MeterGaugeChartModel meterGaugeModelMemoryUsage) {
		this.meterGaugeModelMemoryUsage = meterGaugeModelMemoryUsage;
	}

	public MeterGaugeChartModel getMeterGaugeModelJVM() {
		return meterGaugeModelJVM;
	}

	public void setMeterGaugeModelJVM(MeterGaugeChartModel meterGaugeModelJVM) {
		this.meterGaugeModelJVM = meterGaugeModelJVM;
	}

	public MeterGaugeChartModel getMeterGaugeModelFileSystem() {
		return meterGaugeModelFileSystem;
	}

	public void setMeterGaugeModelFileSystem(MeterGaugeChartModel meterGaugeModelFileSystem) {
		this.meterGaugeModelFileSystem = meterGaugeModelFileSystem;
	}

	public int getFileSystemUsagePercent() {
		return fileSystemUsagePercent;
	}

	public void setFileSystemUsagePercent(int fileSystemUsagePercent) {
		this.fileSystemUsagePercent = fileSystemUsagePercent;
	}

	public int getMemoryUsagePercent() {
		return memoryUsagePercent;
	}

	public void setMemoryUsagePercent(int memoryUsagePercent) {
		this.memoryUsagePercent = memoryUsagePercent;
	}

	public int getDiskUsagePercent() {
		return diskUsagePercent;
	}

	public void setDiskUsagePercent(int diskUsagePercent) {
		this.diskUsagePercent = diskUsagePercent;
	}

	public int getJvmUsagePercent() {
		return jvmUsagePercent;
	}

	public void setJvmUsagePercent(int jvmUsagePercent) {
		this.jvmUsagePercent = jvmUsagePercent;
	}

	public void setBYTE_TO_GIGABYTE_METRIC(int bYTE_TO_GIGABYTE_METRIC) {
		BYTE_TO_GIGABYTE_METRIC = bYTE_TO_GIGABYTE_METRIC;
	}

	public void setBYTE_TO_MEGABYTE_METRIC(int bYTE_TO_MEGABYTE_METRIC) {
		BYTE_TO_MEGABYTE_METRIC = bYTE_TO_MEGABYTE_METRIC;
	}

}
