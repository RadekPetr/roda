/**
 * 
 */
package pt.gov.dgarq.roda.wui.ingest.submit.client;

import pt.gov.dgarq.roda.core.data.SimpleDescriptionObject;
import pt.gov.dgarq.roda.core.data.adapter.filter.Filter;
import pt.gov.dgarq.roda.core.data.adapter.filter.OneOfManyFilterParameter;
import pt.gov.dgarq.roda.core.data.adapter.filter.ProducerFilterParameter;
import pt.gov.dgarq.roda.core.data.eadc.DescriptionLevel;
import pt.gov.dgarq.roda.wui.common.client.ClientLogger;
import pt.gov.dgarq.roda.wui.common.client.widgets.WUIButton;
import pt.gov.dgarq.roda.wui.common.fileupload.client.FileUploadPanel;
import pt.gov.dgarq.roda.wui.dissemination.browse.client.CollectionsTreeVerticalScrollPanel;
import pt.gov.dgarq.roda.wui.dissemination.client.DescriptiveMetadataPanel;
import pt.gov.dgarq.roda.wui.ingest.client.Ingest;
import pt.gov.dgarq.roda.wui.ingest.list.client.IngestList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import config.i18n.client.IngestSubmitConstants;

/**
 * @author Luis Faria
 * 
 */
public class CreateSIP {

	private static IngestSubmitConstants constants = (IngestSubmitConstants) GWT
			.create(IngestSubmitConstants.class);

	private ClientLogger logger = new ClientLogger(getClass().getName());

	private boolean initialized;

	private VerticalPanel layout;

	private HorizontalPanel descriptiveMetadataHeaderLayout;

	private Label descriptiveMetadataHeader;

	private Label descriptiveMetadataOptionalToggle;

	private DescriptiveMetadataPanel descriptiveMetadata;

	private Label representationHeader;

	private HorizontalPanel representationLayout;

	private ContentModelSelector representationType;

	private FileUploadPanel fileUpload;

	private Label destinationHeader;

	private CollectionsTreeVerticalScrollPanel destinationChooser;

	private HorizontalPanel submitLayout;

	private WUIButton submitButton;

	private boolean submitting;

	private Image loadingImage;

	private Label submitMessage;

	private WUIButton getRodaIn;

	/**
	 * Create a new create SIP panel
	 */
	public CreateSIP() {
		layout = new VerticalPanel();
		initialized = false;
		submitting = false;
	}

	/**
	 * Initialize the create SIP panel
	 */
	public void init() {
		if (!initialized) {
			initialized = true;
			descriptiveMetadataHeaderLayout = new HorizontalPanel();
			descriptiveMetadataHeader = new Label(constants
					.createMetadataHeader());
			descriptiveMetadataOptionalToggle = new Label();
			descriptiveMetadata = new DescriptiveMetadataPanel();
			descriptiveMetadata.setReadonly(false);
			descriptiveMetadata.setOptionalVisible(false);

			updateOptionalMetadataToggle();
			descriptiveMetadataOptionalToggle
					.addClickListener(new ClickListener() {

						public void onClick(Widget sender) {
							descriptiveMetadata
									.setOptionalVisible(!descriptiveMetadata
											.isOptionalVisible());
							updateOptionalMetadataToggle();
						}

					});

			representationHeader = new Label(constants
					.createRepresentationHeader());
			representationLayout = new HorizontalPanel();
			representationType = new ContentModelSelector();
			fileUpload = new FileUploadPanel(representationType.getSelected()
					.getFilenameConstraints());
			representationType.addChangeListener(new ChangeListener() {

				public void onChange(Widget sender) {
					fileUpload.setConstraints(representationType.getSelected()
							.getFilenameConstraints());
					updateVisibles();

				}

			});

			destinationHeader = new Label(constants.createDestinationHeader());
			Filter classPlanFilter = new Filter();
			classPlanFilter.add(new ProducerFilterParameter());
			classPlanFilter.add(new OneOfManyFilterParameter(
					SimpleDescriptionObject.LEVEL, new String[] {
							DescriptionLevel.FONDS.getLevel(),
							DescriptionLevel.SUBFONDS.getLevel(),
							DescriptionLevel.CLASS.getLevel(),
							DescriptionLevel.SUBCLASS.getLevel(),
							DescriptionLevel.SERIES.getLevel(),
							DescriptionLevel.SUBSERIES.getLevel() }));
			destinationChooser = new CollectionsTreeVerticalScrollPanel(
					classPlanFilter,
					CollectionsTreeVerticalScrollPanel.DEFAULT_SORTER, true);

			submitLayout = new HorizontalPanel();
			submitButton = new WUIButton(constants.createSubmitButton(),
					WUIButton.Left.ROUND, WUIButton.Right.ARROW_FORWARD);

			submitButton.addClickListener(new ClickListener() {

				public void onClick(Widget sender) {
					submit();
				}

			});

			loadingImage = new Image("images/loadingSmall.gif");
			submitMessage = new Label();

			getRodaIn = new WUIButton(constants.createSipGetRodaIn(),
					WUIButton.Left.ROUND, WUIButton.Right.ARROW_DOWN);

			getRodaIn.addClickListener(new ClickListener() {

				public void onClick(Widget sender) {
					Ingest.downloadRodaIn(null, null);
				}

			});

			descriptiveMetadataHeaderLayout.add(descriptiveMetadataHeader);
			descriptiveMetadataHeaderLayout
					.add(descriptiveMetadataOptionalToggle);

			representationLayout.add(representationType.getWidget());
			representationLayout.add(fileUpload.getWidget());

			submitLayout.add(submitButton);
			submitLayout.add(loadingImage);
			submitLayout.add(submitMessage);
			submitLayout.add(getRodaIn);

			submitButton.setEnabled(false);
			loadingImage.setVisible(false);

			updateVisibles();

			ChangeListener listener = new ChangeListener() {

				public void onChange(Widget sender) {
					if (!submitting) {
						updateVisibles();
					}
				}

			};

			descriptiveMetadata.addChangeListener(listener);
			fileUpload.addChangeListener(listener);
			destinationChooser.addClickListener(new ClickListener() {

				public void onClick(Widget sender) {
					updateVisibles();
				}

			});

			layout.add(descriptiveMetadataHeaderLayout);
			layout.add(descriptiveMetadata);
			layout.add(representationHeader);
			layout.add(representationLayout);
			layout.add(destinationHeader);
			layout.add(destinationChooser);
			layout.add(submitLayout);

			layout.addStyleName("wui-ingest-submit-create");
			descriptiveMetadataHeader.addStyleName("create-title");
			representationHeader.addStyleName("create-title");
			destinationHeader.addStyleName("create-title");
			descriptiveMetadataHeaderLayout
					.addStyleName("create-metadata-header");
			descriptiveMetadataOptionalToggle
					.addStyleName("create-metadata-toggle");
			descriptiveMetadata.addStyleName("create-metadata");
			representationLayout.addStyleName("create-representation");
			fileUpload.getWidget().addStyleName("create-representation-file");
			destinationChooser.addStyleName("create-destination-chooser");
			submitLayout.addStyleName("create-submit");
			submitButton.addStyleName("create-submit-button");
			loadingImage.addStyleName("create-submit-loading");
			submitMessage.setStylePrimaryName("create-submit-message");
			getRodaIn.addStyleName("create-submit-get-roda-in");
			submitLayout.setCellVerticalAlignment(loadingImage,
					HasAlignment.ALIGN_MIDDLE);
			submitLayout.setCellVerticalAlignment(submitMessage,
					HasAlignment.ALIGN_MIDDLE);
			submitLayout.setCellWidth(getRodaIn, "100%");
			submitLayout.setCellHorizontalAlignment(getRodaIn,
					HasHorizontalAlignment.ALIGN_RIGHT);
		} else {
			destinationChooser.clear(new AsyncCallback<Integer>() {

				public void onFailure(Throwable caught) {
					logger.error("Error updating destination chooser", caught);
				}

				public void onSuccess(Integer result) {
					// nothing to do

				}
			});
		}
	}

	private void updateOptionalMetadataToggle() {
		descriptiveMetadataOptionalToggle.setText(descriptiveMetadata
				.isOptionalVisible() ? constants.createHideOptionalMetadata()
				: constants.createShowOptionalMetadata());
	}

	/**
	 * Get widget
	 * 
	 * @return widget
	 */
	public Widget getWidget() {
		return layout;
	}

	/**
	 * Update visible and enabled state of all widgets
	 */
	public void updateVisibles() {
		descriptiveMetadata.isValid(new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				logger.error("Error validating descriptive metadata", caught);
			}

			public void onSuccess(Boolean isValid) {
				if (!isValid) {
					submitButton.setEnabled(false);
					submitMessage.setText(constants
							.createMetadataInvalidWarning());
					submitMessage.addStyleDependentName("error");
				} else if (fileUpload.isEmpty()) {
					submitButton.setEnabled(false);
					submitMessage.setText(constants.createNoFilesWarning());
					submitMessage.addStyleDependentName("error");
				} else if (!fileUpload.isValid()) {
					submitButton.setEnabled(false);
					submitMessage
							.setText(constants.createInvalidFilesWarning());
					submitMessage.addStyleDependentName("error");
				} else if (destinationChooser.getSelected() == null) {
					submitButton.setEnabled(false);
					submitMessage.setText(constants
							.createNoDestinationWarning());
					submitMessage.addStyleDependentName("error");
				} else if (destinationChooser.getSelected().getSDO().getLevel()
						.compareTo(DescriptionLevel.FILE) > 0) {
					submitButton.setEnabled(false);
					submitMessage.setText(constants
							.createInvalidDestinationWarning());
					submitMessage.addStyleDependentName("error");
				} else {
					submitButton.setEnabled(true);
					submitMessage.setText("");
					submitMessage.removeStyleDependentName("error");
				}
			}

		});

	}

	/**
	 * Submit SIP
	 */
	public void submit() {
		submitting = true;
		loadingImage.setVisible(true);
		submitMessage.setText(constants.createSubmitUploadingMessage());
		submitButton.setEnabled(false);
		fileUpload.submit(new AsyncCallback<String[]>() {

			public void onFailure(Throwable caught) {
				if (caught != null) {
					logger.error("Error uploading files", caught);
				}

				submitting = false;
				loadingImage.setVisible(false);
				submitMessage.setText("");
				submitButton.setEnabled(true);

			}

			public void onSuccess(String[] fileCodes) {
				submitMessage.setText(constants.createSubmitSubmitingMessage());
				descriptiveMetadata.save();
				String contentModel = representationType.getSelected()
						.getContentModel();
				IngestSubmitService.Util.getInstance().createSIP(contentModel,
						descriptiveMetadata.getDescriptionObject(), fileCodes,
						destinationChooser.getSelected().getPid(),
						new AsyncCallback<Boolean>() {

							public void onFailure(Throwable caught) {
								logger.error("Error while submiting", caught);
								submitting = false;
								
								loadingImage.setVisible(false);
								submitMessage.setText("");
								submitButton.setEnabled(true);
							}

							public void onSuccess(Boolean success) {
								if (success.booleanValue()) {
									loadingImage.setVisible(false);
									submitMessage.setText("");
									submitButton.setEnabled(true);
									descriptiveMetadata.clear();
									fileUpload.clear();

									// Initialize ingest list
									IngestList.getInstance().init();

									// Set processing state filter
									IngestList.getInstance().setStateFilter(
											IngestList.StateFilter.PROCESSING);

									// Update ingest list
									IngestList.getInstance().update();

									// Show ingest list
									History.newItem(IngestList.getInstance()
											.getHistoryPath());

								} else {
									loadingImage.setVisible(false);
									submitMessage.setText(constants
											.createSubmitFailureMessage());
									submitMessage
											.addStyleDependentName("error");
									submitButton.setEnabled(true);
								}
								submitting = false;
								updateVisibles();

							}

						});

			}

		});
	}

}
