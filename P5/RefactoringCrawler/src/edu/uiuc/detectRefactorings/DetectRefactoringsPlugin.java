package edu.uiuc.detectRefactorings;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org._3pq.jgrapht.graph.AbstractBaseGraph;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import edu.uiuc.detectRefactorings.detection.ChangeMethodSignatureDetection;
import edu.uiuc.detectRefactorings.detection.MoveClassDetection;
import edu.uiuc.detectRefactorings.detection.MoveFieldDetection;
import edu.uiuc.detectRefactorings.detection.MoveMethodDetection;
import edu.uiuc.detectRefactorings.detection.PullUpMethodDetection;
import edu.uiuc.detectRefactorings.detection.PushDownMethodDetection;
import edu.uiuc.detectRefactorings.detection.RefactoringDetection;
import edu.uiuc.detectRefactorings.detection.RenameClassDetection;
import edu.uiuc.detectRefactorings.detection.RenameMethodDetection;
import edu.uiuc.detectRefactorings.detection.RenamePackageDetection;
import edu.uiuc.detectRefactorings.graph.SourceNavigator;
import edu.uiuc.detectRefactorings.util.ShinglesUtil;
import edu.uiuc.detectRefactorings.views.RefactoringCategory;
import edu.uiuc.detectRefactorings.views.RefactoringsView;
import edu.uiuc.detectRefactorings.views.RefactoringsView.RefactoringsViewContentProvider;
import edu.uiuc.detectRefactorings.xml.ExportToXML;

/**
 * The main plugin class to be used in the desktop.
 */
public class DetectRefactoringsPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "edu.uiuc.detectRefactorings"; //$NON-NLS-1$

	// The shared instance.
	private static DetectRefactoringsPlugin plugin;

	// Resource bundle.
	private ResourceBundle resourceBundle;

	// The XML output class.
	private ExportToXML xmlOutput;

	private List<RefactoringCategory> refactoringList;

	private long pluginStartTime;

	private long plugInTotalTime;

	private String originalProject;

	private String subsequentProject;

//	private SourceNavigator navigator;

//	private SourceNavigator navigatorForVersion;

	/**
	 * The constructor.
	 */
	public DetectRefactoringsPlugin() {
		super();

		plugin = this;
		try {
			refactoringList = new ArrayList<RefactoringCategory>();
			resourceBundle = ResourceBundle
					.getBundle("edu.uiuc.detectRefactorings.DetectRefactoringsPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static DetectRefactoringsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = DetectRefactoringsPlugin.getDefault()
				.getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void launch(final Dictionary settings) {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		try {
			dialog.run(true, true, new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					doLaunch(settings, monitor);
				}

			});
		} catch (InterruptedException e) {

		} catch (InvocationTargetException e) {
			Throwable targer = e.getTargetException();
			ErrorDialog.openError(getShell(), "TargetError",
					"Error Occured While Running Detections", new Status(0,
							"detectRefactorings", 0, "no message", targer));
		}
	}

	private Shell getShell() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null)
			return null;
		return window.getShell();
	}

	private void doLaunch(Dictionary settings, IProgressMonitor monitor) {
		pluginStartTime = System.currentTimeMillis();
		xmlOutput = new ExportToXML();

		refactoringList.clear();
		RefactoringDetection.resetRenamingsDictionary();

		originalProject = (String) settings.get("OriginalVersion");
		subsequentProject = (String) settings.get("SubsequentVersion");
		int sMethod = Integer.parseInt((String) settings.get("sMethod"));
		int wMethod = Integer.parseInt((String) settings.get("wMethod"));
		double tMethod = Double.parseDouble((String) settings.get("tMethod"));
		int sClass = Integer.parseInt((String) settings.get("sClass"));
		double tClass = Double.parseDouble((String) settings.get("tClass"));
		int sPackage = Integer.parseInt((String) settings.get("sPackage"));
		double tPackage = Double.parseDouble((String) settings.get("tPackage"));
		double tMoveMethod = Double.parseDouble((String) settings
				.get("tMoveMethod"));
		double tPullUpMethod = Double.parseDouble((String) settings
				.get("tPullUpMethod"));
		double tPushDownMethod = Double.parseDouble((String) settings
				.get("tPushDownMethod"));
//		double tMoveField = Double.parseDouble((String) settings
//				.get("tMoveField"));
		double tChangeMethodSignature = Double.parseDouble((String) settings
				.get("tChangeMethodSignature"));
//		double tMoveClass = Double.parseDouble((String) settings
//				.get("tMoveClass"));
		
		double tRenameMethod= Double.parseDouble((String) settings.get("tRenameMethod"));
		double tRenameClass= Double.parseDouble((String) settings.get("tRenameClass"));
		double tRenamePackage= Double.parseDouble((String) settings.get("tRenamePackage"));

		boolean useJavadocComments= ((Boolean) settings.get("useJavadocComments")).booleanValue();
		SourceNavigator.useJavadocComments(useJavadocComments);
		
		ShinglesUtil shinglesUtil = new ShinglesUtil();
		shinglesUtil.setMethod_treshold(tMethod);
		shinglesUtil.setS_method(sMethod);
		shinglesUtil.setW(wMethod);
		shinglesUtil.setS_class(sClass);
		shinglesUtil.setClass_treshold(tClass);
		shinglesUtil.setS_package(sPackage);
		shinglesUtil.setPackage_treshold(tPackage);
		shinglesUtil.setPullUp_treshold(tPullUpMethod);
		shinglesUtil.setRenameClass_threshold(tRenameClass);
		shinglesUtil.setRenameMethod_threshold(tRenameMethod);
		shinglesUtil.setRenamePackage_threshold(tRenamePackage);
		shinglesUtil.setPushDown_treshold(tPushDownMethod);
		shinglesUtil.setMoveMethod_treshold(tMoveMethod);
//		shinglesUtil.setMoveField_treshold(tMoveField);
		shinglesUtil.setChangeMethodSignature_treshold(tChangeMethodSignature);
//		shinglesUtil.setMoveClass_treshold(tMoveClass);

		monitor.beginTask("Detect Refactorings", 100);

		 SourceNavigator navigator = new SourceNavigator(monitor);
		 navigator.setShinglesUtil(shinglesUtil);
		 navigator.browseProject(originalProject);
		 AbstractBaseGraph originalGraph = navigator.getGraph();
		
		 monitor.worked(3);
		
		 SourceNavigator navigatorForVersion = new SourceNavigator(monitor);
		 navigatorForVersion.setShinglesUtil(shinglesUtil);
		 navigatorForVersion.browseProject(subsequentProject);
		 AbstractBaseGraph versionGraph = navigatorForVersion.getGraph();

		// HACK done for fast testing _ REVERT
//		if (navigator == null) {
//			navigator = new SourceNavigator(monitor);
//			navigator.setShinglesUtil(shinglesUtil);
//			navigator.browseProject(originalProject);
//		}
//		AbstractBaseGraph originalGraph = navigator.getGraph();
//
//		monitor.worked(3);
//
//		if (navigatorForVersion == null) {
//			navigatorForVersion = new SourceNavigator(monitor);
//			navigatorForVersion.setShinglesUtil(shinglesUtil);
//			navigatorForVersion.browseProject(subsequentProject);
//		}
//		AbstractBaseGraph versionGraph = navigatorForVersion.getGraph();
		// END HACK

		monitor.worked(3);
		monitor.subTask("Initialize ShinglesUtil");
		shinglesUtil.initialize(originalGraph, versionGraph);

		clearRefactoringsView();

		int unitOfWorkPerRefactoring = 1;
		unitOfWorkPerRefactoring = unitOfWorkPerRefactoring(settings);

		if (((Boolean) settings.get("UseFeedbackLoop")).booleanValue()) {
			doFeedbackLoop(monitor, shinglesUtil, originalGraph, versionGraph,
					unitOfWorkPerRefactoring);
			displayAllRefactoringCategories();
			updateTimer();

			return;
		}

		if (((Boolean) settings.get("RenameMethod")).booleanValue()) {
			detectRenameMethod(monitor, tRenameMethod, shinglesUtil, originalGraph,
					versionGraph, unitOfWorkPerRefactoring);
		}

		if (((Boolean) settings.get("RenameClass")).booleanValue()) {
			detectRenameClass(monitor, tRenameClass, shinglesUtil, originalGraph,
					versionGraph, unitOfWorkPerRefactoring);
		}

		if (((Boolean) settings.get("RenamePackage")).booleanValue()) {
			detectRenamePackage(monitor, tRenamePackage, shinglesUtil, originalGraph,
					versionGraph, unitOfWorkPerRefactoring);
		}

		if (((Boolean) settings.get("MoveMethod")).booleanValue()) {
			detectMoveMethod(monitor, tMoveMethod, shinglesUtil, originalGraph,
					versionGraph, unitOfWorkPerRefactoring);
		}

		if (((Boolean) settings.get("PullUpMethod")).booleanValue()) {
			detectPullUpMethod(monitor, tPullUpMethod, shinglesUtil,
					originalGraph, versionGraph, unitOfWorkPerRefactoring);
		}

		if (((Boolean) settings.get("PushDownMethod")).booleanValue()) {
			detectPushDownMethod(monitor, tPushDownMethod, shinglesUtil,
					originalGraph, versionGraph, unitOfWorkPerRefactoring);
		}

//		if (((Boolean) settings.get("MoveField")).booleanValue()) {
//			detectMoveField(monitor, tMoveField, shinglesUtil, originalGraph,
//					versionGraph, unitOfWorkPerRefactoring);
//		}

		if (((Boolean) settings.get("ChangeMethodSignature")).booleanValue()) {
			detectChangeMethodSignature(monitor, tChangeMethodSignature,
					shinglesUtil, originalGraph, versionGraph,
					unitOfWorkPerRefactoring);
		}

//		if (((Boolean) settings.get("MoveClass")).booleanValue()) {
//			detectMoveClass(monitor, tMoveClass, shinglesUtil, originalGraph,
//					versionGraph, unitOfWorkPerRefactoring);
//		}

		displayAllRefactoringCategories();
		updateTimer();
	}

	

	/**
	 * 
	 */
	private void updateTimer() {
		plugInTotalTime = System.currentTimeMillis() - pluginStartTime;
		int plugInTotalTimeHours = (int) (plugInTotalTime / (1000 * 60 * 60));
		int plugInTotalTimeMins = (int) ((plugInTotalTime - plugInTotalTimeHours
				* (60 * 60 * 1000)) / (1000 * 60));
		int plugInTotalTimeSecs = (int) ((plugInTotalTime
				- plugInTotalTimeHours * (60 * 60 * 1000) - plugInTotalTimeMins
				* (60 * 1000)) / 1000);
		int plugInTotalTimeMsecs = (int) ((plugInTotalTime
				- plugInTotalTimeHours * (60 * 60 * 1000) - plugInTotalTimeMins
				* (60 * 1000) - plugInTotalTimeSecs * 1000));
		System.out.println("The plug in took " + plugInTotalTimeHours + ":"
				+ plugInTotalTimeMins + ":" + plugInTotalTimeSecs + ":"
				+ plugInTotalTimeMsecs + " to detect refactorings.");
	}

	public void doFeedbackLoop(IProgressMonitor monitor,
			ShinglesUtil shinglesUtil, AbstractBaseGraph originalGraph,
			AbstractBaseGraph versionGraph, int unitOfWorkPerRefactoring) {

		// clear here so for the FeedbackLoop test code (which starts executing
		// from this point)
		refactoringList.clear();
		RefactoringDetection.resetRenamingsDictionary();

		// TODO REMOVE this hardcoded hack
		// if (originalProject.indexOf("Log4J") != -1) {
		// String[][] seed = {
		// { "org.apache.log4j.Category", "org.apache.log4j.Logger" },
		// { "org.apache.log4j.Priority", "org.apache.log4j.Level" } };
		// RefactoringDetection.seedRenamingsDictionary(seed);
		//
		// }

		detectRenamePackage(monitor, shinglesUtil.getRenamePackage_treshold(),
				shinglesUtil, originalGraph, versionGraph,
				unitOfWorkPerRefactoring);
		detectRenameClass(monitor, shinglesUtil.getRenameClass_treshold(),
				shinglesUtil, originalGraph, versionGraph,
				unitOfWorkPerRefactoring);
		detectRenameMethod(monitor, shinglesUtil.getRenameMethod_treshold(),
				shinglesUtil, originalGraph, versionGraph,
				unitOfWorkPerRefactoring);

		detectPullUpMethod(monitor, shinglesUtil.getPullUp_treshold(),
				shinglesUtil, originalGraph, versionGraph,
				unitOfWorkPerRefactoring);
		detectPushDownMethod(monitor, shinglesUtil.getPushDown_treshold(),
				shinglesUtil, originalGraph, versionGraph,
				unitOfWorkPerRefactoring);
		detectMoveMethod(monitor, shinglesUtil.getMoveMethod_treshold(),
				shinglesUtil, originalGraph, versionGraph,
				unitOfWorkPerRefactoring);

		detectChangeMethodSignature(monitor, shinglesUtil
				.getChangeMethodSignature_treshold(), shinglesUtil,
				originalGraph, versionGraph, unitOfWorkPerRefactoring);

		// detectMoveField(monitor, shinglesUtil.getMoveField_treshold(),
		// shinglesUtil, originalGraph, versionGraph,
		// unitOfWorkPerRefactoring);

		// detectMoveClass(monitor, tClass, se, originalGraph, versionGraph,
		// unitOfWorkPerRefactoring);

		eliminateDuplicatesBetweenMovePushdownPullup();
	}

	private void eliminateDuplicatesBetweenMovePushdownPullup() {
		List<RefactoringCategory> refactoringCategoryList = getRefactoringList();
		RefactoringCategory pulledUpCategory;
		RefactoringCategory movedMethodsCategory;
		RefactoringCategory pushedDownCategory;
		for (Iterator iter = refactoringCategoryList.iterator(); iter.hasNext();) {
			RefactoringCategory category = (RefactoringCategory) iter.next();

		}
		//TODO implement this feature

	}

	public void detectChangeMethodSignature(IProgressMonitor monitor,
			double tChangeMethodSignature, ShinglesUtil shinglesUtil,
			AbstractBaseGraph originalGraph, AbstractBaseGraph versionGraph,
			int unitOfWorkPerRefactoring) {
		List candidateChangedMethodSignatures = shinglesUtil
				.findSimilarMethods(monitor);
		RefactoringDetection detector = new ChangeMethodSignatureDetection(
				originalGraph, versionGraph);
		detector.setThreshold(tChangeMethodSignature);
		monitor.subTask("ChangeMethodSignature Detection");
		List changedMethodSignatures = detector.detectRefactorings(
				candidateChangedMethodSignatures, monitor,
				unitOfWorkPerRefactoring);
		if (changedMethodSignatures.size() > 0) {
			RefactoringCategory changeSignatureCategory = new RefactoringCategory();
			changeSignatureCategory.setName("ChangedMethodSignatures");
			changeSignatureCategory
					.setRefactoringPairs(changedMethodSignatures);
			refactoringList.add(changeSignatureCategory);
		}
	}

	public void detectMoveField(IProgressMonitor monitor, double tMoveField,
			ShinglesUtil shinglesUtil, AbstractBaseGraph originalGraph,
			AbstractBaseGraph versionGraph, int unitOfWorkPerRefactoring) {
		List candidateMovedFields = shinglesUtil
				.findMoveFieldCandidates(monitor);
		RefactoringDetection detector = new MoveFieldDetection(originalGraph,
				versionGraph);
		detector.setThreshold(tMoveField);
		monitor.subTask("MoveField Detection");
		List moveFieldResults = detector.detectRefactorings(
				candidateMovedFields, monitor, unitOfWorkPerRefactoring);
		if (moveFieldResults.size() > 0) {
			RefactoringCategory moveFieldCategory = new RefactoringCategory();
			moveFieldCategory.setName("MovedFields");
			moveFieldCategory.setRefactoringPairs(moveFieldResults);
			refactoringList.add(moveFieldCategory);
		}
	}

	public void detectMoveMethod(IProgressMonitor monitor, double tMoveMethod,
			ShinglesUtil se, AbstractBaseGraph originalGraph,
			AbstractBaseGraph versionGraph, int unitOfWorkPerRefactoring) {
		List methodCandidates = se.findSimilarMethods(monitor);
		se.findSimilarClasses(monitor);
		RefactoringDetection detector = new MoveMethodDetection(originalGraph,
				versionGraph);
		detector.setThreshold(tMoveMethod);
		monitor.subTask("MoveMethod Detection");
		List movedMethods = detector.detectRefactorings(methodCandidates,
				monitor, unitOfWorkPerRefactoring);
		if (movedMethods.size() > 0) {
			RefactoringCategory moveMethodCategory = new RefactoringCategory();
			moveMethodCategory.setName("MovedMethods");
			moveMethodCategory.setRefactoringPairs(movedMethods);
			refactoringList.add(moveMethodCategory);
		}
	}

	public void detectMoveClass(IProgressMonitor monitor, double tMoveClass,
			ShinglesUtil shinglesUtil, AbstractBaseGraph originalGraph,
			AbstractBaseGraph versionGraph, int unitOfWorkPerRefactoring) {
		List classCandidates = shinglesUtil.findSimilarClasses(monitor);
		RefactoringDetection detector = new MoveClassDetection(originalGraph,
				versionGraph);
		detector.setThreshold(tMoveClass);
		monitor.subTask("MoveClass Detection");
		List movedClasses = detector.detectRefactorings(classCandidates,
				monitor, unitOfWorkPerRefactoring);
		if (movedClasses.size() > 0) {
			RefactoringCategory moveClassCategory = new RefactoringCategory();
			moveClassCategory.setName("MovedClasses");
			moveClassCategory.setRefactoringPairs(movedClasses);
			refactoringList.add(moveClassCategory);
		}
	}

	public void detectPushDownMethod(IProgressMonitor monitor,
			double tPushDownMethod, ShinglesUtil se,
			AbstractBaseGraph originalGraph, AbstractBaseGraph versionGraph,
			int unitOfWorkPerRefactoring) {
		List candidatePushDownMethods = se
				.findPushDownMethodCandidates(monitor);
		RefactoringDetection detector = new PushDownMethodDetection(
				originalGraph, versionGraph);
		detector.setThreshold(tPushDownMethod);
		monitor.subTask("PushDownMethod Detection");
		List pushDownMethodResults = detector.detectRefactorings(
				candidatePushDownMethods, monitor, unitOfWorkPerRefactoring);
		if (pushDownMethodResults.size() > 0) {
			RefactoringCategory pushDownCategory = new RefactoringCategory();
			pushDownCategory.setName("PushedDownMethods");
			pushDownCategory.setRefactoringPairs(pushDownMethodResults);
			refactoringList.add(pushDownCategory);
		}
	}

	public void detectPullUpMethod(IProgressMonitor monitor,
			double tPullUpMethod, ShinglesUtil se,
			AbstractBaseGraph originalGraph, AbstractBaseGraph versionGraph,
			int unitOfWorkPerRefactoring) {
		List candidatePullUpMethods = se.findPullUpMethodCandidates(monitor);
		RefactoringDetection detector = new PullUpMethodDetection(
				originalGraph, versionGraph);
		detector.setThreshold(tPullUpMethod);
		monitor.subTask("PullUpMethod Detection");
		List pullUpMethodResults = detector.detectRefactorings(
				candidatePullUpMethods, monitor, unitOfWorkPerRefactoring);
		if (pullUpMethodResults.size() > 0) {
			RefactoringCategory pullUpCategory = new RefactoringCategory();
			pullUpCategory.setName("PulledUpMethods");
			pullUpCategory.setRefactoringPairs(pullUpMethodResults);
			refactoringList.add(pullUpCategory);
		}
	}

	private void detectRenameMethod(IProgressMonitor monitor, double tMethod,
			ShinglesUtil se, AbstractBaseGraph originalGraph,
			AbstractBaseGraph versionGraph, int unitOfWorkPerRefactoring) {
		List candidateMethods = se.findSimilarMethods(monitor);
		RefactoringDetection detector = new RenameMethodDetection(
				originalGraph, versionGraph);
		detector.setThreshold(tMethod);

		// TODO REMOVE this hardcoded hack
		// if (originalProject.indexOf("Log4J") != -1) {
		// String[][] seed = {
		// { "org.apache.log4j.Category", "org.apache.log4j.Logger" },
		// { "org.apache.log4j.Priority", "org.apache.log4j.Level" } };
		// RefactoringDetection.seedRenamingsDictionary(seed);
		// }

		monitor.subTask("RenameMethod Detection");
		List renamedMethods = detector.detectRefactorings(candidateMethods,
				monitor, unitOfWorkPerRefactoring);
		if (renamedMethods.size() > 0) {
			RefactoringCategory renameMethodCategory = new RefactoringCategory();
			renameMethodCategory.setName("RenamedMethods");
			renameMethodCategory.setRefactoringPairs(renamedMethods);
			refactoringList.add(renameMethodCategory);
		}
	}

	public void detectRenameClass(IProgressMonitor monitor, double tClass,
			ShinglesUtil se, AbstractBaseGraph originalGraph,
			AbstractBaseGraph versionGraph, int unitOfWorkPerRefactoring) {
		List candidateClasses = se.findSimilarClasses(monitor);
		RefactoringDetection detector = new RenameClassDetection(originalGraph,
				versionGraph);
		detector.setThreshold(tClass);
		monitor.subTask("RenameClass Detection");
		List renamedClasses = detector.detectRefactorings(candidateClasses,
				monitor, unitOfWorkPerRefactoring);
		if (renamedClasses.size() > 0) {
			RefactoringCategory renameClassCategory = new RefactoringCategory();
			renameClassCategory.setName("RenamedClasses");
			renameClassCategory.setRefactoringPairs(renamedClasses);
			refactoringList.add(renameClassCategory);
		}
	}

	public void detectRenamePackage(IProgressMonitor monitor, double tPackage,
			ShinglesUtil se, AbstractBaseGraph originalGraph,
			AbstractBaseGraph versionGraph, int unitOfWorkPerRefactoring) {
		List candidatePackages = se.findSimilarPackages(monitor);
		RefactoringDetection detector = new RenamePackageDetection(
				originalGraph, versionGraph);
		detector.setThreshold(tPackage);
		monitor.subTask("RenamePackage Detection");
		List renamedPackages = detector.detectRefactorings(candidatePackages,
				monitor, unitOfWorkPerRefactoring);
		if (renamedPackages.size() > 0) {
			RefactoringCategory renamePackageCategory = new RefactoringCategory();
			renamePackageCategory.setName("RenamedPackages");
			renamePackageCategory.setRefactoringPairs(renamedPackages);
			refactoringList.add(renamePackageCategory);
		}
	}

	private int unitOfWorkPerRefactoring(Dictionary settings) {
		int unitOfWorkPerRefactoring;
		int refactoringsCounter = 0;
		if (((Boolean) settings.get("RenameMethod")).booleanValue())
			refactoringsCounter++;
		if (((Boolean) settings.get("RenameClass")).booleanValue())
			refactoringsCounter++;
		if (((Boolean) settings.get("RenamePackage")).booleanValue())
			refactoringsCounter++;
		if (((Boolean) settings.get("PullUpMethod")).booleanValue())
			refactoringsCounter++;
		if (((Boolean) settings.get("PushDownMethod")).booleanValue())
			refactoringsCounter++;
		if (((Boolean) settings.get("MoveMethod")).booleanValue())
			refactoringsCounter++;
//		if (((Boolean) settings.get("MoveField")).booleanValue())
//			refactoringsCounter++;
		if (((Boolean) settings.get("ChangeMethodSignature")).booleanValue())
			refactoringsCounter++;
//		if (((Boolean) settings.get("MoveClass")).booleanValue())
//			refactoringsCounter++;
		if (((Boolean) settings.get("UseFeedbackLoop")).booleanValue())
			refactoringsCounter = 7;
		unitOfWorkPerRefactoring = 94 / refactoringsCounter;

		// if (((Boolean) settings.get("UseFeedbackLoop")).booleanValue())
		// unitOfWorkPerRefactoring = 94 / 8;
		return unitOfWorkPerRefactoring;
	}

	private void clearRefactoringsView() {
		showView();
		final TreeViewer viewer = RefactoringsView.getViewer();
		if (viewer == null)
			return;
		final RefactoringsView.RefactoringsViewContentProvider provider = (RefactoringsViewContentProvider) viewer
				.getContentProvider();
		if (provider == null)
			return;
		final List invisibleRoot = provider.getInvisibleRoot();
		invisibleRoot.clear();
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				provider.inputChanged(viewer, invisibleRoot, invisibleRoot);
			}
		});
	}

	private void showView() {
		final IWorkbench workbench = PlatformUI.getWorkbench();

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				final IWorkbenchWindow window = workbench
						.getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				try {
					page
							.showView("edu.uiuc.detectRefactorings.views.RefactoringsView");
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});

	}

	void displayAllRefactoringCategories() {
		for (Iterator iter = refactoringList.iterator(); iter.hasNext();) {
			RefactoringCategory category = (RefactoringCategory) iter.next();
			displayResults(category);
		}
	}

	private void displayResults(RefactoringCategory categoryToDisplay) {
		showView();
		final TreeViewer viewer = RefactoringsView.getViewer();
		final RefactoringsView.RefactoringsViewContentProvider provider = (RefactoringsViewContentProvider) viewer
				.getContentProvider();

		final List invisibleRoot = provider.getInvisibleRoot();
		// invisibleRoot.clear();
		RefactoringCategory categoryToDelete = null;
		for (Iterator iter = invisibleRoot.iterator(); iter.hasNext();) {
			RefactoringCategory existingCategory = (RefactoringCategory) iter
					.next();
			if (existingCategory.getName().equals(categoryToDisplay.getName()))
				categoryToDelete = existingCategory;
		}

		if (categoryToDelete != null) {
			invisibleRoot.remove(categoryToDelete);
		}

		invisibleRoot.add(categoryToDisplay);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				provider.inputChanged(viewer, invisibleRoot, invisibleRoot);
			}
		});

		xmlOutput.addDetectedRefactorings(categoryToDisplay
				.getRefactoringPairs(), categoryToDisplay.getName());
	}

	public ExportToXML getXmlOutput() {
		return xmlOutput;
	}

	public List<RefactoringCategory> getRefactoringList() {
		return refactoringList;
	}

}
