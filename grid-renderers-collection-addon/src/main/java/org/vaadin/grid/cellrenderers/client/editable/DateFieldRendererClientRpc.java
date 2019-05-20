package org.vaadin.grid.cellrenderers.client.editable;

import com.vaadin.shared.communication.ClientRpc;

public interface DateFieldRendererClientRpc extends ClientRpc {

	void setEnabled(boolean enabled, String rowKey);

	void switchEnabled(String rowKey);
}
