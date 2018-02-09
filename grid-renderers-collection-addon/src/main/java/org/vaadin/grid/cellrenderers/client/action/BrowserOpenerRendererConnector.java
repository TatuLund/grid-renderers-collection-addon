package org.vaadin.grid.cellrenderers.client.action;

import java.util.Map.Entry;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.BrowserWindowOpenerState;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.util.SharedUtil;

import elemental.json.JsonObject;

@Connect(org.vaadin.grid.cellrenderers.action.BrowserOpenerRenderer.class)
public class BrowserOpenerRendererConnector extends ClickableRendererConnector<String> {

	HtmlButtonRendererServerRpc rpc = RpcProxy.create(HtmlButtonRendererServerRpc.class, this);

    public class BrowserOpenerClientRenderer extends ClickableRenderer<String, Button> {

        private boolean htmlContentAllowed = false;
        private static final String ROW_KEY_PROPERTY = "rowKey";
        private String urlString;
        
        @Override
        public Button createWidget() {
            final Button b = GWT.create(Button.class);
            
            b.addClickHandler(new ClickHandler() {
            	@Override
            	public void onClick(ClickEvent event) {
        			MouseEventDetails mouseEventDetails = MouseEventDetailsBuilder
        	                .buildMouseEventDetails(event.getNativeEvent(),
        	                        b.getElement());
        			Element e = b.getElement();
        			rpc.onClick(e.getPropertyString("rowKey"),mouseEventDetails);
            		event.stopPropagation();
            		String url = null;
            		if (getState().baseUrl == null) 
            			url = urlString;
            		else
            			url = getState().baseUrl;
                    url = addParametersAndFragment(url);
                    if (url != null) {
                        Window.open(url, getState().target, getState().features);
                    }
            	}
            });
            b.setStylePrimaryName("v-nativebutton");
            return b;
        }

        public void setHtmlContentAllowed(boolean htmlContentAllowed) {
            this.htmlContentAllowed = htmlContentAllowed;
        }

        public boolean isHtmlContentAllowed() {
            return htmlContentAllowed;
        }

        private String addParametersAndFragment(String url) {
            if (url == null) {
                return null;
            }
            
            if (!getState().parameters.isEmpty()) {
                StringBuilder params = new StringBuilder();
                for (Entry<String, String> entry : getState().parameters
                        .entrySet()) {
                    if (params.length() != 0) {
                        params.append('&');
                    }
                    params.append(URL.encodeQueryString(entry.getKey()));
                    params.append('=');

                    String value = entry.getValue();
                    if (value != null) {
                        params.append(URL.encodeQueryString(value));
                    }
                }

                url = SharedUtil.addGetParameters(url, params.toString());
            }

            if (getState().baseUrl != null) {
                // Replace previous fragment or just add to the end of the url
                url = url.replaceFirst("#.*|$", "#" + urlString);
            }            
            return url;
        }
        
        
        @Override
        public void render(RendererCellReference cell, String text, Button button) {

			Element e = button.getElement();
			urlString = text;
			
            if(e.getPropertyString(ROW_KEY_PROPERTY) != getRowKey((JsonObject) cell.getRow())) {
                e.setPropertyString(ROW_KEY_PROPERTY,
                        getRowKey((JsonObject) cell.getRow()));
            }
            String caption = getState().caption;
            if (caption == null) caption = "";
        	if (htmlContentAllowed) {
                button.setHTML(caption);
        	} else {
        		button.setText(caption);    		
            }
        	button.setEnabled(true);
        }
   	}

    @Override
    public BrowserOpenerClientRenderer getRenderer() {
        return (BrowserOpenerClientRenderer) super.getRenderer();
    }

    @Override
    protected BrowserOpenerClientRenderer createRenderer() {
    	return new BrowserOpenerClientRenderer();
    }

    @Override
    protected HandlerRegistration addClickHandler(
            RendererClickHandler<JsonObject> handler) {
        return getRenderer().addClickHandler(handler);
    }

    @Override
    public BrowserOpenerRendererState getState() {
        return (BrowserOpenerRendererState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getRenderer().setHtmlContentAllowed(getState().htmlContentAllowed);
    }
    
}
