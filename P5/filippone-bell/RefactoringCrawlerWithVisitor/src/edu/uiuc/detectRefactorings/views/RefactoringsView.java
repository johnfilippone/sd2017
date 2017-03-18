package edu.uiuc.detectRefactorings.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.internal.corext.util.SearchUtils;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import edu.uiuc.detectRefactorings.DetectRefactoringsPlugin;
import edu.uiuc.detectRefactorings.detection.MethodReferenceSearchRequestor;
import edu.uiuc.detectRefactorings.detection.SearchHelper;
import edu.uiuc.detectRefactorings.util.Node;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class RefactoringsView extends ViewPart {
	 static private TreeViewer viewer;

	 private Action createXmlOutputAction;

	 private Action computeRecallAndPrecision;

	 private DrillDownAdapter drillDownAdapter;

	 private Composite parent;

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	public class RefactoringsViewContentProvider implements
			IStructuredContentProvider, ITreeContentProvider {
		 private List invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			if (v != null)
				v.refresh();
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof IViewSite) {
				if (invisibleRoot == null) {
					invisibleRoot = new ArrayList();
				}
				return invisibleRoot.toArray();
			}

			return getChildren(parent);
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof Node[]) {
				return (Node[]) parent;
			}

			if (parent instanceof Node[][]) {
				return (Node[][]) parent;
			}

			if (parent instanceof RefactoringCategory) {
				RefactoringCategory new_name = (RefactoringCategory) parent;
				return new_name.getElements();
			}

			if (parent instanceof List) {
				return ((List) parent).toArray();
			}

			return new Object[0];
		}

		public Object getParent(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof Node)
				return false;
			return true;
		}

		public List getInvisibleRoot() {
			return invisibleRoot;
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object element) {
			if (element instanceof List) {
				return "TopRoot";
			}
			if (element instanceof RefactoringCategory) {
				RefactoringCategory newCategory = (RefactoringCategory) element;
				return newCategory.getName() + " ["
						+ newCategory.getRefactoringPairs().size()
						+ " refactorings]";
			}
			if (element instanceof Node[]) {
				return "Pair";
			}
			return element.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof RefactoringCategory)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return 
					PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}

	/**
	 * The constructor.
	 */
	public RefactoringsView() {
	}

	public static TreeViewer getViewer() {
		return viewer;
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.parent = parent;
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new RefactoringsViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		// enabling the alphabetical sorter will cause problems because the
		// elements
		// in the Pair will be displayed alphabetically rather than based on the
		// order
		// of the project versions
		// viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
		makeActions();
		hookContextMenu();
		contributeToActionBars();
		hookDoubleClikSupport();
	}

	 private void hookDoubleClikSupport() {
		
								 
								
								
									
									   viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.getFirstElement() instanceof Node) {
					Node node = (Node) selection.getFirstElement();
					if (node.getType().equals(Node.METHOD)
							|| node.getType().equals(Node.CLASS)) {
						MethodReferenceSearchRequestor searchRequestor = new MethodReferenceSearchRequestor();
						SearchEngine searchEngine = new SearchEngine();
						IProgressMonitor monitor = new NullProgressMonitor();
						IJavaSearchScope searchScope = SearchHelper
								.createProjectSearchScope(node.getProjectName());

						int searchConstant = 0;
						if (node.getType().equals(Node.METHOD))
							searchConstant = IJavaSearchConstants.METHOD;
						else if (node.getType().equals(Node.CLASS))
							searchConstant = IJavaSearchConstants.CLASS;
						Assert.assertFalse(searchConstant == 0);

						SearchPattern pattern = SearchPattern.createPattern(node.getFullyQualifiedName()
										+ node.getSignature(), searchConstant, IJavaSearchConstants.DECLARATIONS, SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
						try {
							searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
											.getDefaultSearchParticipant() }, searchScope, searchRequestor, monitor);
						} catch (CoreException e) {
							JavaPlugin.log(e);
						}
						if (searchRequestor.getSearchResults().size() != 0) {
							IJavaElement element = (IJavaElement) searchRequestor
									.getSearchResults().get(0);
							IType type = (IType) element
									.getAncestor(IJavaElement.TYPE);
							try {
								IEditorPart part = JavaUI.openInEditor(type);
								JavaUI.revealInEditor(part, element);
							} catch (PartInitException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JavaModelException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}

				System.out.println();
			}
		});
	}

	 private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				RefactoringsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		 getSite().registerContextMenu(menuMgr, viewer);
	}

	 private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	 private void fillLocalPullDown(IMenuManager manager) {
		manager.add(createXmlOutputAction);
		manager.add(new Separator());
		manager.add(computeRecallAndPrecision);
	}

	 private void fillContextMenu(IMenuManager manager) {
		manager.add(createXmlOutputAction);
		manager.add(computeRecallAndPrecision);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	 private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(createXmlOutputAction);
		manager.add(computeRecallAndPrecision);
	}

	 private void makeActions() {
		createXmlOutputAction = new Action() {
			public void run() {
				try {
					if (DetectRefactoringsPlugin.getDefault().getXmlOutput() != null) {
						String fileLoc = getFileNameInput(true);
						DetectRefactoringsPlugin.getDefault().getXmlOutput()
								.closeXMLOutput(fileLoc);
						if (fileLoc != null)
							showMessage("XML output exported to " + fileLoc);
					} else
						showMessage("RefactoringCrawler has not been run yet");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		createXmlOutputAction.setText("Export to XML");
		createXmlOutputAction
				.setToolTipText("Exports the detected refactorings to an XML file");
		
						createXmlOutputAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		
											
															
										computeRecallAndPrecision = new Action() {
			public void run() {
				String fileloc = getFileNameInput(false);
				File resultsFile = new File(fileloc);
				try {
					FileReader reader = new FileReader(resultsFile);
					BufferedReader bufferReader = new BufferedReader(reader);
					DetectRefactoringsPlugin plugin = DetectRefactoringsPlugin
							.getDefault();
					List<RefactoringCategory> refactorings = plugin
							.getRefactoringList();
					List expectedList = new ArrayList<String[]>();
					List falsePositives = new ArrayList();
					List falseNegatives = new ArrayList();
					List goodResults = new ArrayList();
					String line = "";
					while ((line = bufferReader.readLine()) != null) {
						StringTokenizer st = new StringTokenizer(line, ",");
						String refCategory = st.nextToken();
						String oldName = st.nextToken();
						String newName = st.nextToken();
						String[] expected = new String[] { refCategory,
								oldName, newName };
						expectedList.add(expected);
					}
					// At this point I have the expected triplets

					// First add all the false negatives that we can easily
					// identify. These are the ones that we cannot even find in
					// the RefactoringCategories. eg. If there's a bunch of
					// RenameMethods in the expected, but we cannot find any
					// rename method RefactoringCategory, we can call them FNs
					// with no checks
					ArrayList<String> refCatList = new ArrayList<String>();
					for (Iterator iter = refactorings.iterator(); iter
							.hasNext();) {
						RefactoringCategory refcat = (RefactoringCategory) iter
								.next();
						refCatList.add(refcat.getName());
					}

					// Get the false negatives
					for (Iterator iter = expectedList.iterator(); iter
							.hasNext();) {
						String[] expectedTriplet = (String[]) iter.next();

						// Refer to the above comment with refCatList
						if (!refCatList.contains(expectedTriplet[0]))
							falseNegatives.add(expectedTriplet);

						for (Iterator iterator = refactorings.iterator(); iterator
								.hasNext();) {
							RefactoringCategory cat = (RefactoringCategory) iterator
									.next();
							if (cat.getName().equals(expectedTriplet[0])) {
								boolean found = false;
								for (Iterator iterator1 = cat
										.getRefactoringPairs().iterator(); iterator1
										.hasNext();) {
									Node[] pair = (Node[]) iterator1.next();
									if (expectedTriplet[1].trim().equals(pair[0].getFullyQualifiedName())
											&& expectedTriplet[2]
													.trim()
													.equals(pair[1]
																	.getFullyQualifiedName())) {
										goodResults.add(expectedTriplet);
										found = true;
										break;
									}
								}
								if (!found)
									falseNegatives.add(expectedTriplet);
							}
						}
					}

					for (Iterator iter = refactorings.iterator(); iter
							.hasNext();) {
						RefactoringCategory refCat = (RefactoringCategory) iter
								.next();
						for (Iterator iterator = refCat.getRefactoringPairs()
								.iterator(); iterator.hasNext();) {
							Node[] pair = (Node[]) iterator.next();
							boolean found = false;
							for (Iterator iterator2 = expectedList.iterator(); iterator2
									.hasNext();) {
								String[] expectedPair = (String[]) iterator2
										.next();
								if (expectedPair[0].trim().equals(refCat.getName())
										&& pair[0].getFullyQualifiedName()
												.equals(expectedPair[1].trim())
										&& pair[1].getFullyQualifiedName()
												.equals(expectedPair[2].trim())) {
									found = true;
								}
							}
							if (!found)
								falsePositives.add(new String[] {
										refCat.getName(),
										pair[0].getFullyQualifiedName(),
										pair[1].getFullyQualifiedName() });
						}
					}

					System.out.println("Number of Good Results: "
							+ goodResults.size());
					System.out.println("Number of False Positives: "
							+ falsePositives.size());
					System.out.println("False positives are: ");
					for (Iterator iter = falsePositives.iterator(); iter
							.hasNext();) {
						String[] triple = (String[]) iter.next();
						System.out.println(triple[0] + ": " + triple[1] + ", "
								+ triple[2]);
					}
					System.out.println("Number of False Negatives: "
							+ falseNegatives.size());
					System.out.println("False negatives are: ");
					for (Iterator iter = falseNegatives.iterator(); iter
							.hasNext();) {
						String[] triple = (String[]) iter.next();
						System.out.println(triple[0] + ": " + triple[1] + ", "
								+ triple[2]);
					}
					System.out
							.println("Precision: "
									+ ((double) goodResults.size() / (double) (goodResults
											.size() + falsePositives.size())));
					System.out
							.println("Recall: "
									+ ((double) goodResults.size() / (double) (goodResults
											.size() + falseNegatives.size())));

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		computeRecallAndPrecision.setText("Compare results to expected");
		computeRecallAndPrecision.setToolTipText("Compare results to expected");
		
						computeRecallAndPrecision.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}

	 private String getFileNameInput(boolean save) {
		// JFileChooser fileChooser = new JFileChooser();
		FileDialog fileChooser = null;
		String fileName = null;
		if (save)
			fileChooser = new FileDialog(viewer.getControl().getShell(),
					SWT.SAVE);
		else
			fileChooser = new FileDialog(viewer.getControl().getShell(),
					SWT.OPEN);
		fileName = fileChooser.open();
		if (fileName != null)
			return fileName;
		else
			return null;
	}

	 private void showMessage(String message) {
		
				 MessageDialog.openInformation(viewer.getControl().getShell(), "RefactoringsView", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}