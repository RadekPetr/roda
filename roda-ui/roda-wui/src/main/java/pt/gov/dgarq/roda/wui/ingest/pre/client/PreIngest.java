/**
 * 
 */
package pt.gov.dgarq.roda.wui.ingest.pre.client;

import pt.gov.dgarq.roda.core.data.SimpleDescriptionObject;
import pt.gov.dgarq.roda.core.data.adapter.filter.Filter;
import pt.gov.dgarq.roda.core.data.adapter.filter.OneOfManyFilterParameter;
import pt.gov.dgarq.roda.core.data.adapter.filter.ProducerFilterParameter;
import pt.gov.dgarq.roda.core.data.eadc.DescriptionLevel;
import pt.gov.dgarq.roda.wui.common.client.ClientLogger;
import pt.gov.dgarq.roda.wui.common.client.HistoryResolver;
import pt.gov.dgarq.roda.wui.common.client.UserLogin;
import pt.gov.dgarq.roda.wui.common.client.widgets.HTMLWidgetWrapper;
import pt.gov.dgarq.roda.wui.dissemination.browse.client.CollectionsTreeVerticalScrollPanel;
import pt.gov.dgarq.roda.wui.dissemination.browse.client.ViewWindow;
import pt.gov.dgarq.roda.wui.ingest.client.Ingest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import config.i18n.client.PreIngestConstants;

/**
 * @author Luis Faria
 * 
 */
public class PreIngest implements HistoryResolver {

	private static PreIngest instance = null;

	/**
	 * Get instance
	 * 
	 * @return {@link PreIngest} singleton
	 */
	public static PreIngest getInstance() {
		if (instance == null) {
			instance = new PreIngest();
		}
		return instance;
	}

	private static PreIngestConstants constants = GWT
			.create(PreIngestConstants.class);

	private ClientLogger logger = new ClientLogger(getClass().getName());

	private boolean initialized;

	private VerticalPanel layout;

	private HTMLWidgetWrapper html;

	private Label classificationPlanLabel;

	private CollectionsTreeVerticalScrollPanel classificationPlan;

	private PreIngest() {
		initialized = false;
	}

	private void init() {
		if (!initialized) {
			initialized = true;
			layout = new VerticalPanel();
			html = new HTMLWidgetWrapper("PreIngest.html");

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

			classificationPlanLabel = new Label(constants
					.classificationPlanLabel());

			classificationPlan = new CollectionsTreeVerticalScrollPanel(
					classPlanFilter,
					CollectionsTreeVerticalScrollPanel.DEFAULT_SORTER, true);

			classificationPlan.addClickListener(new ClickListener() {

				public void onClick(Widget sender) {
					String pid = classificationPlan.getSelected().getPid();
					ViewWindow viewWindow = new ViewWindow(pid);
					viewWindow.show();

				}

			});

			layout.add(html);
			layout.add(classificationPlanLabel);
			layout.add(classificationPlan);

			layout.addStyleName("wui-ingest-pre");
			classificationPlanLabel.addStyleName("classificationPlan-label");
			classificationPlan.addStyleName("classificationPlan");

		} else {
			classificationPlan.clear(new AsyncCallback<Integer>() {

				public void onFailure(Throwable caught) {
					logger.error("Error updating destination chooser", caught);
				}

				public void onSuccess(Integer result) {
					// nothing to do

				}
			});
		}
	}

	public String getHistoryPath() {
		return Ingest.getInstance().getHistoryPath() + "." + getHistoryToken();
	}

	public String getHistoryToken() {
		return "pre";
	}

	public void isCurrentUserPermitted(AsyncCallback<Boolean> callback) {
		UserLogin.getInstance().checkRole(this, callback);

	}

	public void resolve(String[] historyTokens, AsyncCallback<Widget> callback) {
		if (historyTokens.length == 0) {
			init();
			callback.onSuccess(layout);
		} else {
			History.newItem(getHistoryPath());
			callback.onSuccess(null);
		}
	}

}
