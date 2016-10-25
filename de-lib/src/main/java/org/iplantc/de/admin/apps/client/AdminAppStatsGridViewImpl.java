package org.iplantc.de.admin.apps.client;

import org.iplantc.de.apps.client.models.AppModelKeyProvider;
import org.iplantc.de.client.models.apps.App;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by sriram on 10/21/16.
 */
public class AdminAppStatsGridViewImpl extends Composite implements AdminAppStatsGridView{

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public void addAll(List<App> apps) {
       store.addAll(apps);
    }

    interface AdminAppStatsGridViewUiBinder
            extends UiBinder<Widget, AdminAppStatsGridViewImpl> {
    }

    private static AdminAppStatsGridViewUiBinder ourUiBinder =
            GWT.create(AdminAppStatsGridViewUiBinder.class);

    @UiField
    Grid<App> grid;
    @UiField
    ColumnModel<App> cm;
    @UiField
    ListStore<App> store;

    @Inject
    public AdminAppStatsGridViewImpl() {
         initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiFactory
    ColumnModel<App> createColumnModel() {
        ColumnConfig<App, String> appName = new ColumnConfig<>(new ValueProvider<App, String>() {
            @Override
            public String getValue(App object) {
                return object.getName();
            }

            @Override
            public void setValue(App object, String value) {

            }

            @Override
            public String getPath() {
                return null;
            }
        }, 200, "Name");

        ColumnConfig<App, Integer> total = new ColumnConfig<>(new ValueProvider<App, Integer>() {
            @Override
            public Integer getValue(App object) {
                return object.getAppStats().getTotal();
            }

            @Override
            public void setValue(App object, Integer value) {

            }

            @Override
            public String getPath() {
                return null;
            }
        },100, "Total");

        ColumnConfig<App,Integer> totalCompleted = new ColumnConfig<>(new ValueProvider<App, Integer>() {
            @Override
            public Integer getValue(App object) {
                return object.getAppStats().getTotalCompleted();
            }

            @Override
            public void setValue(App object, Integer value) {

            }

            @Override
            public String getPath() {
                return null;
            }
        },100, "Completed");

        ColumnConfig<App, Integer> totalFailed = new ColumnConfig<>(new ValueProvider<App, Integer>() {
            @Override
            public Integer getValue(App object) {
                return object.getAppStats().getTotalFailed();
            }

            @Override
            public void setValue(App object, Integer value) {

            }

            @Override
            public String getPath() {
                return null;
            }
        }, 100, "Failed");

        ColumnConfig<App, Date> lastCompleted = new ColumnConfig<>(new ValueProvider<App, Date>() {
            @Override
            public Date getValue(App object) {
                return object.getAppStats().getLastCompletedDate();
            }

            @Override
            public void setValue(App object, Date value) {

            }

            @Override
            public String getPath() {
                return null;
            }
        }, 200, "Last Completed Date");

        ColumnConfig<App, Date> lastUsed = new ColumnConfig<>(new ValueProvider<App, Date>() {
            @Override
            public Date getValue(App object) {
                return object.getAppStats().getLastUsedDate();
            }

            @Override
            public void setValue(App object, Date value) {

            }

            @Override
            public String getPath() {
                return null;
            }
        },200, "Last Used");


        return new ColumnModel<>(Arrays.<ColumnConfig<App, ?>>asList(appName,total, totalCompleted, totalFailed, lastCompleted, lastUsed));
    }

    @UiFactory
    ListStore<App> createListStore() {
        return new ListStore<>(new AppModelKeyProvider());
    }
}