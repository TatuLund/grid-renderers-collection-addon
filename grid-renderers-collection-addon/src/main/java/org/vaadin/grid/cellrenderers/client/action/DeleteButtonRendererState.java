package org.vaadin.grid.cellrenderers.client.action;

import com.vaadin.shared.ui.grid.renderers.ClickableRendererState;

public class DeleteButtonRendererState extends ClickableRendererState {
    public boolean htmlContentAllowed = false;
    public String delete = "Delete";
    public String confirm = "Confirm";
    public String normalStyle = "v-deletebutton";
    public String deleteStyle = "delete-confirm";
    public int timeOut = 10;
}
