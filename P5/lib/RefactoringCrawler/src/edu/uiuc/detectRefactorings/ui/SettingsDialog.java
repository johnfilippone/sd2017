package edu.uiuc.detectRefactorings.ui;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jdt.internal.ui.util.RowLayouter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.uiuc.detectRefactorings.DetectRefactoringsPlugin;
import edu.uiuc.detectRefactorings.util.Constants;

public class SettingsDialog extends Dialog {
	String title;

	private Button detectRenMethod;

	private Button detectRenClass;

	private Button detectRenPackage;

	private Button detectMoveMethod;

	private Button detectPullUpMethod;

	private Button detectPushDownMethod;

	// private Button detectMoveField;

	private Button detectChangeMethodSignature;

	// private Button detectMoveClass;

	private Combo fOriginalVersion;

	private Combo fSubsequentVersion;

	private Text wMethod;

	private Text sMethod;

	private Text sClass;

	private Text sPack;

	private Text tRenameMethod;

	private Text tRenameClass;

	private Text tRenamePack;

	private Text tMoveMethod;

	private Text tPullUpMethod;

	private Text tPushDownMethod;

	// private Text tMoveField;

	private Text tChangeMethodSignature;

	// private Text tMoveClass;

	private Button useFeedbackLoop;

	private Button viewAdvancedSettings;

	private Control groupRefactorings;

	private Control groupFeedbackLoop;

	private Control groupSyntacticAnalysis;

	private Label labSMethod;

	private Label labSClass;

	private Label labSPack;

	private Label labWindow;

	private Label labThreshold;

	private Button useVariableShingles;

	private Text tMethod;

	private Text tClass;

	private Text tPack;

	private Label labMethodSimilarityThreshold;

	private Label labClassSimilarityThreshold;

	private Label labPackSimilarityThreshold;

	private Button useJavadocComments;

	public SettingsDialog(Shell parentShell, String title) {
		super(parentShell);
		this.title = title;
	}

	protected Control createContents(Composite parent) {
		// initialize the dialog units
		initializeDialogUnits(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN) * 3 / 2;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING) * 2;
		layout.makeColumnsEqualWidth = false;
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		dialogArea = createDialogArea(parent);

		buttonBar = createButtonBar(parent);
		return parent;
	}

	protected Control createDialogArea(Composite parent) {
		Composite superComposite = new Composite(parent, SWT.NONE);
		// setControl(superComposite);
		initializeDialogUnits(superComposite);

		superComposite.setLayout(new GridLayout());

		Composite composite = new Composite(superComposite, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 8;
		composite.setLayout(layout);
		// RowLayouter layouter = new RowLayouter(2);

		createProjectSelection(composite);

		createSyntacticAndSemanticGroups(composite);

		createFeedbackGroup(composite);

		enableAdvancedSettings(false);
		Dialog.applyDialogFont(superComposite);

		return superComposite;

	}

	/**
	 * @param composite
	 */
	private void createFeedbackGroup(Composite composite) {
		groupFeedbackLoop = createFeedbackLoopGroup(composite);
		groupFeedbackLoop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));
	}

	/**
	 * @param composite
	 */
	private void createSyntacticAndSemanticGroups(Composite composite) {
		final SashForm sashForm = new SashForm(composite, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				2, 1));

		groupSyntacticAnalysis = createSyntacticAnalysisGroup(sashForm);
		groupSyntacticAnalysis.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));

		groupRefactorings = createDetectRefactoringsGroup(sashForm);
		groupRefactorings.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));

		sashForm.setWeights(new int[] { 5, 5 });
	}

	private Control createSyntacticAnalysisGroup(Composite composite) {
		RowLayouter rowLayouter = new RowLayouter(2);
		Group group = new Group(composite, SWT.NONE);
		group.setText("Shingles Encoding");
		group.setLayout(new GridLayout(2, false));

		Label filler = new Label(group, SWT.NONE);
		filler.setVisible(false);

		Label filler2 = new Label(group, SWT.NONE);
		filler2.setVisible(false);

		labMethodSimilarityThreshold = new Label(group, SWT.NONE);
		labMethodSimilarityThreshold.setText("MethodSimilarity");

		tMethod = createTextInputField(group);
		tMethod.setText("   .5");

		labClassSimilarityThreshold = new Label(group, SWT.NONE);
		labClassSimilarityThreshold.setText("ClassSimilarity");

		tClass = createTextInputField(group);
		tClass.setText("   .7");

		labPackSimilarityThreshold = new Label(group, SWT.NONE);
		labPackSimilarityThreshold.setText("PackageSimilarity");

		tPack = createTextInputField(group);
		tPack.setText("   .7");

		labWindow = new Label(group, SWT.NONE);
		labWindow.setText("windowSize");

		wMethod = createTextInputField(group);
		wMethod.setEditable(true);
		wMethod.setText("    1");

		labSMethod = new Label(group, SWT.NONE);
		labSMethod.setText("shingles/Method");

		sMethod = createTextInputField(group);
		sMethod.setText("    7");

		labSClass = new Label(group, SWT.NONE);
		labSClass.setText("shingles/Class");

		sClass = createTextInputField(group);
		sClass.setText("  35");

		labSPack = new Label(group, SWT.NONE);
		labSPack.setText("shingles/Package");

		sPack = createTextInputField(group);
		sPack.setText(" 120");

		useVariableShingles = createCheckbox(group, "UseVariableShingles",
				true, rowLayouter);
		useVariableShingles
				.setToolTipText("Uses a number of shingles direct proportional with "
						+ "the length of the entity"
						+ " (e.g., "
						+ sMethod.getText().trim()
						+ " + 2*LOC shingles per method)");

		useJavadocComments = createCheckbox(group, "Use Javadoc Comments",
				false, rowLayouter);
		useJavadocComments
				.setToolTipText("Counts Javadoc comments as part of the method body. This"
						+ " can be useful for interface methods or very short methods.");
		return group;
	}

	/**
	 * @param composite
	 * @param layouter
	 */
	private void createProjectSelection(Composite composite) {

		RowLayouter rowLayouter = new RowLayouter(2);
		Label label = new Label(composite, SWT.NONE);
		label.setText("OriginalVersion");

		DetectRefactoringsPlugin plugin = DetectRefactoringsPlugin.getDefault();
		Preferences preferences = plugin.getPluginPreferences();
		String defaultString = preferences
				.getDefaultString(Constants.initialVersion);
		fOriginalVersion = createProjectSelectionCombo(composite);

		rowLayouter.perform(label, fOriginalVersion, 1);

		label = new Label(composite, SWT.NONE);
		label.setText("SubsequentVersion");

		fSubsequentVersion = createProjectSelectionCombo(composite);
		String defaultStringForSecond = preferences
				.getDefaultString(Constants.subsequentVersion);
		if (!"".equals(defaultString))
			fOriginalVersion.setText(defaultString);
		if (!"".equals(defaultStringForSecond))
			fSubsequentVersion.setText(defaultStringForSecond);

		rowLayouter.perform(label, fSubsequentVersion, 1);

		String title = "Change Default Settings";
		boolean defaultValue = false;
		viewAdvancedSettings = createCheckbox(composite, title, defaultValue,
				rowLayouter);
		viewAdvancedSettings.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean selected = viewAdvancedSettings.getSelection();
				if (selected) {
					enableAdvancedSettings(true);
				} else {
					enableAdvancedSettings(false);
				}
			}
		});
		viewAdvancedSettings
				.setToolTipText("Enables one to change the default settings "
						+ "(e.g. syntactic analysis settings, thresholds, etc.)");
	}

	protected void enableAdvancedSettings(boolean enabled) {
		groupFeedbackLoop.setEnabled(enabled);
		groupRefactorings.setEnabled(enabled);
		groupSyntacticAnalysis.setEnabled(enabled);

		detectChangeMethodSignature.setEnabled(enabled);
		// detectMoveClass.setEnabled(enabled);
		// detectMoveField.setEnabled(enabled);
		detectMoveMethod.setEnabled(enabled);
		detectPullUpMethod.setEnabled(enabled);
		detectPushDownMethod.setEnabled(enabled);
		detectRenClass.setEnabled(enabled);
		detectRenMethod.setEnabled(enabled);
		detectRenPackage.setEnabled(enabled);

		tRenameMethod.setEnabled(enabled);
		tRenameClass.setEnabled(enabled);
		tRenamePack.setEnabled(enabled);

		tChangeMethodSignature.setEnabled(enabled);
		// tMoveClass.setEnabled(enabled);
		// tMoveField.setEnabled(enabled);
		tMoveMethod.setEnabled(enabled);
		tPullUpMethod.setEnabled(enabled);
		tPushDownMethod.setEnabled(enabled);

		wMethod.setEnabled(enabled);
		sMethod.setEnabled(enabled);
		sClass.setEnabled(enabled);
		sPack.setEnabled(enabled);
		tMethod.setEnabled(enabled);
		tClass.setEnabled(enabled);
		tPack.setEnabled(enabled);

		labSClass.setEnabled(enabled);
		labSMethod.setEnabled(enabled);
		labSPack.setEnabled(enabled);
		labWindow.setEnabled(enabled);

		labClassSimilarityThreshold.setEnabled(enabled);
		labMethodSimilarityThreshold.setEnabled(enabled);
		labPackSimilarityThreshold.setEnabled(enabled);

		labThreshold.setEnabled(enabled);

		useFeedbackLoop.setEnabled(enabled);
		useVariableShingles.setEnabled(enabled);
		useJavadocComments.setEnabled(enabled);
	}

	private Control createFeedbackLoopGroup(Composite composite) {
		RowLayouter layouter = new RowLayouter(2);
		Group group = new Group(composite, SWT.NONE);
		group.setText("Feedback Loop");
		group.setLayout(new GridLayout(3, false));

		Label filler = new Label(group, SWT.NONE);
		filler.setText("Detect Refactorings Type");
		filler.setVisible(false);

		String title = "Use Feedback Loop";
		boolean defaultValue = true;
		useFeedbackLoop = createCheckbox(group, title, defaultValue, layouter);
		useFeedbackLoop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				useFeedbackLoop.getSelection();
			}
		});

		useFeedbackLoop
				.setToolTipText("Runs all the refactoring detection strategies above \n"
						+ "until no more new refactorings are found. The order in which refactorings are run \n"
						+ "is picked by us (e.g., RenamePackage, then RenameClass then RenameMethod)");
		return group;
	}

	private Control createDetectRefactoringsGroup(Composite composite) {
		RowLayouter layouter = new RowLayouter(2);
		Group group = new Group(composite, SWT.NONE);
		group.setText("ReferenceGraphSimilarity");
		group.setLayout(new GridLayout(3, false));

		Label filler = new Label(group, SWT.NONE);
		filler.setText("Detect");
		filler.setVisible(false);

		Label filler2 = new Label(group, SWT.NONE);
		filler2.setText("D");
		filler2.setVisible(false);

		labThreshold = new Label(group, SWT.NONE);
		labThreshold.setText("threshold");

		String title = "RenameMethod";
		boolean defaultValue = false;
		detectRenMethod = createCheckbox(group, title, defaultValue, layouter);
		detectRenMethod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				detectRenMethod.getSelection();
			}
		});
		detectRenMethod.setSelection(false);

		tRenameMethod = createTextInputField(group);
		tRenameMethod.setText("    .5");

		title = "RenameClass";
		defaultValue = false;
		detectRenClass = createCheckbox(group, title, defaultValue, layouter);
		detectRenClass.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				detectRenClass.getSelection();
			}
		});

		tRenameClass = createTextInputField(group);
		tRenameClass.setText("    .7");

		title = "RenamePackage";
		defaultValue = false;
		detectRenPackage = createCheckbox(group, title, defaultValue, layouter);
		detectRenPackage.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				detectRenPackage.getSelection();
			}
		});

		tRenamePack = createTextInputField(group);
		tRenamePack.setText("    .7");

		title = "MoveMethod";
		defaultValue = false;
		detectMoveMethod = createCheckbox(group, title, defaultValue, layouter);
		detectMoveMethod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				detectMoveMethod.getSelection();
			}
		});

		tMoveMethod = createTextInputField(group);
		tMoveMethod.setText("    .5");

		title = "PullUpMethod";
		defaultValue = false;
		detectPullUpMethod = createCheckbox(group, title, defaultValue,
				layouter);
		detectPullUpMethod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				detectPullUpMethod.getSelection();
			}
		});

		tPullUpMethod = createTextInputField(group);
		tPullUpMethod.setText("    .6");

		title = "PushDownMethod";
		defaultValue = false;
		detectPushDownMethod = createCheckbox(group, title, defaultValue,
				layouter);
		detectPushDownMethod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				detectPushDownMethod.getSelection();
			}
		});

		tPushDownMethod = createTextInputField(group);
		tPushDownMethod.setText("    .6");

		// title = "MoveField";
		// defaultValue = false;
		// detectMoveField = createCheckbox(group, title, defaultValue,
		// layouter);
		// detectMoveField.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e) {
		// detectMoveField.getSelection();
		// }
		// });
		//
		// tMoveField = createTextInputField(group);
		// tMoveField.setText(" .6");

		title = "ChangeMethodSignature";
		defaultValue = false;
		detectChangeMethodSignature = createCheckbox(group, title,
				defaultValue, layouter);
		detectChangeMethodSignature
				.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						detectChangeMethodSignature.getSelection();
					}
				});

		tChangeMethodSignature = createTextInputField(group);
		tChangeMethodSignature.setText("    .5");

		// title = "MoveClass";
		// defaultValue = false;
		// detectMoveClass = createCheckbox(group, title, defaultValue,
		// layouter);
		// detectMoveClass.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e) {
		// detectMoveClass.getSelection();
		// }
		// });
		//
		// tMoveClass = createTextInputField(group);
		// tMoveClass.setText(" .6");

		return group;
	}

	private Combo createProjectSelectionCombo(Composite composite) {
		Combo version = new Combo(composite, SWT.SINGLE | SWT.BORDER);
		populateCombo(version);
		version.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateOKStatus();
			}
		});
		version.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateOKStatus();

			}
		});
		GridData data = new GridData(GridData.FILL, GridData.FILL, true, false,
				1, 1);
		data.widthHint = convertWidthInCharsToPixels(50);
		version.setLayoutData(data);
		return version;
	}

	private void populateCombo(Combo combo) {
		combo.add("select project");
		combo.select(0);
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] allProjects = root.getProjects();
		for (int i = 0; i < allProjects.length; i++) {
			IProject project = allProjects[i];
			combo.add(project.getName(), i);
		}
	}

	protected boolean updateOKStatus() {
		String originalVersion = fOriginalVersion.getText();
		String subsequentVersion = fSubsequentVersion.getText();
		return ((originalVersion != null) && (subsequentVersion != null)
				&& (!originalVersion.equals(subsequentVersion))
				&& (checkProjectExists(originalVersion)) && (checkProjectExists(subsequentVersion)));
	}

	private boolean checkProjectExists(String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject project = root.getProject(projectName);
		return project.exists();
	}

	private Button createCheckbox(Composite composite, String title2,
			boolean defaultValue, RowLayouter layouter) {
		Button checkBox = new Button(composite, SWT.CHECK);
		checkBox.setText(title2);
		checkBox.setSelection(defaultValue);
		layouter.perform(checkBox);
		return checkBox;
	}

	private Button createRadioButton(Composite composite, String title2,
			boolean defaultValue, RowLayouter layouter) {
		Button radioButton = new Button(composite, SWT.RADIO);
		radioButton.setText(title2);
		radioButton.setSelection(defaultValue);
		layouter.perform(radioButton);
		return radioButton;
	}

	private Text createTextInputField(Composite composite) {
		Text textField = new Text(composite, SWT.BORDER);
		return textField;
	}

	@SuppressWarnings( { "unchecked" })
	protected void okPressed() {

		Dictionary settings = new Hashtable();
		// project settings
		settings.put("OriginalVersion", fOriginalVersion.getText().trim());
		settings.put("SubsequentVersion", fSubsequentVersion.getText().trim());

		// Shingles settings
		settings.put("wMethod", wMethod.getText().trim());
		settings.put("sMethod", sMethod.getText().trim());
		settings.put("tMethod", tMethod.getText().trim());

		settings.put("sClass", sClass.getText().trim());
		settings.put("tClass", tClass.getText().trim());

		settings.put("sPackage", sPack.getText().trim());
		settings.put("tPackage", tPack.getText().trim());

		settings.put("useJavadocComments", new Boolean(useJavadocComments
				.getSelection()));
		settings.put("useVariableShingles", new Boolean(useVariableShingles
				.getSelection()));

		// refactoring settings
		settings.put("RenameMethod",
				new Boolean(detectRenMethod.getSelection()));
		settings.put("RenameClass", new Boolean(detectRenClass.getSelection()));
		settings.put("RenamePackage", new Boolean(detectRenPackage
				.getSelection()));
		settings
				.put("MoveMethod", new Boolean(detectMoveMethod.getSelection()));
		settings.put("PullUpMethod", new Boolean(detectPullUpMethod
				.getSelection()));
		settings.put("PushDownMethod", new Boolean(detectPushDownMethod
				.getSelection()));
		// settings.put("MoveField", new
		// Boolean(detectMoveField.getSelection()));
		settings.put("ChangeMethodSignature", new Boolean(
				detectChangeMethodSignature.getSelection()));
		// settings.put("MoveClass", new
		// Boolean(detectMoveClass.getSelection()));

		settings.put("tRenameMethod", tRenameMethod.getText().trim());
		settings.put("tRenameClass", tRenameClass.getText().trim());
		settings.put("tRenamePackage", tRenamePack.getText().trim());

		settings.put("tMoveMethod", tMoveMethod.getText().trim());

		settings.put("tPullUpMethod", tPullUpMethod.getText().trim());

		settings.put("tPushDownMethod", tPushDownMethod.getText().trim());

		// settings.put("tMoveField", tMoveField.getText().trim());

		settings.put("tChangeMethodSignature", tChangeMethodSignature.getText()
				.trim());

		// settings.put("tMoveClass", tMoveClass.getText().trim());

		// feedback loop Settings
		settings.put("UseFeedbackLoop", new Boolean(useFeedbackLoop
				.getSelection()));

		DetectRefactoringsPlugin plugin = DetectRefactoringsPlugin.getDefault();
		Preferences preferences = plugin.getPluginPreferences();
		preferences.setDefault(Constants.initialVersion, fOriginalVersion
				.getText());
		preferences.setDefault(Constants.subsequentVersion, fSubsequentVersion
				.getText());
		plugin.savePluginPreferences();

		super.okPressed();
		// launch project
		plugin.launch(settings);

	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null)
			shell.setText(title);
		// if (titleImage != null)
		// shell.setImage(titleImage);
	}

}
