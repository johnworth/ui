package org.iplantc.de.diskResource.client.gin;

import org.iplantc.de.client.models.diskResources.Folder;
import org.iplantc.de.client.models.search.DiskResourceQueryTemplate;

import static com.google.gwt.dom.client.BrowserEvents.CLICK;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeNode;
import com.sencha.gxt.widget.core.client.tree.TreeSelectionModel;

public final class TreeCell extends AbstractCell<Folder> {

    private final TreeCellAppearance appearance;
    private final Tree<Folder, Folder> tree;

    public interface TreeCellAppearance {

        void render(Cell.Context context, Folder value, SafeHtmlBuilder sb);

        void onBrowserEvent(Cell.Context context, Element parent, Folder value, NativeEvent event, ValueUpdater<Folder> valueUpdater);

        void setHasHandlers(HasHandlers hasHandlers);

        void setSelectionModel(TreeSelectionModel<Folder> selectionModel);
    }

    public TreeCell(TreeCellAppearance appearance, final Tree<Folder, Folder> tree) {
        super(CLICK);
        this.appearance = appearance;
        this.tree = tree;
    }

    public TreeCell(Tree<Folder, Folder> tree) {
        this(GWT.<TreeCellAppearance> create(TreeCellAppearance.class), tree);
    }

    @Override
    public void render(Cell.Context context, Folder value, SafeHtmlBuilder sb) {
        appearance.render(context, value, sb);
    }

    public void setHasHandlers(HasHandlers hasHandlers) {
        appearance.setHasHandlers(hasHandlers);
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, Folder value, NativeEvent event, ValueUpdater<Folder> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);

        TreeNode<Folder> node = tree.findNode(event.getEventTarget().<Element> cast());
        if (!(value instanceof DiskResourceQueryTemplate) && !node.isLoaded()) {
            return;
        }
        appearance.onBrowserEvent(context, parent, value, event, valueUpdater);
    }

    public void setSelectionModel(TreeSelectionModel<Folder> selectionModel) {
        appearance.setSelectionModel(selectionModel);
    }
}