package org.vaadin.grid.cellrenderers.editable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.client.editable.SimpleSelectRendererClientRpc;
import org.vaadin.grid.cellrenderers.client.editable.SimpleSelectRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.editable.SimpleSelectRendererState;
import org.vaadin.grid.cellrenderers.client.editable.TextFieldRendererClientRpc;

import com.vaadin.data.Converter;
import com.vaadin.data.ValueContext;
import com.vaadin.server.Setter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;

import elemental.json.JsonValue;

/**
 * Simple selection renderer to be used when the set of values to be selected is small. The renderer
 * has type parameter. The renderer uses ListBox GWT widget, which accepts only String as value.
 * Since there is many times need for select to be used with discrete numbers, enum, etc. other 
 * types than String, this renderer can be used with Converter that takes care of converting values
 * to Strings used in selection popup and real type in the Container. The renderer fires edit
 * event when value has been modified.
 *
 * @see Grid#addColumn(String, com.vaadin.ui.renderers.AbstractRenderer)
 * @see Grid#addColumn(com.vaadin.data.ValueProvider, com.vaadin.ui.renderers.AbstractRenderer)
 * @see Grid#addColumn(com.vaadin.data.ValueProvider, com.vaadin.data.ValueProvider, com.vaadin.ui.renderers.AbstractRenderer)
 * 
 * @param <T> Bean type of the Grid where this Renderer is being used
 * 
 * @author Tatu Lund
 */
public class SimpleSelectRenderer<T,A> extends EditableRenderer<T,A> {

	private Map<String,A> items;
	private Converter<String,A> converter;
	private List<A> dropDownList;
	
	/**
	 * Set A Converter to be used in conversion between value and caption label of the listing.
	 * 
	 * @param converter The converter to be used
	 */
	public void setConverter(Converter<String,A> converter) {
		this.converter = converter;
		setItems(dropDownList);
	}
	
	/**
	 * Constructor for SimpleSelectRenderer. 
	 * 
	 * @param setter Method reference to right setter of T 
	 * @param dropDownList List of the of the selection options to be shown in drop down menu
	 */
	public SimpleSelectRenderer(Setter<T,A> setter, List<A> dropDownList) {
        super((Class<A>) Object.class);
 
        this.dropDownList = dropDownList;
        
        setItems(dropDownList);
        
    	setupSimpleSelectRenderer(setter);
    }

	private void setItems(List<A> dropDownList) {
	    items = new HashMap<String,A>();
        List<String> captions = new ArrayList<String>();
		
        for (A value : dropDownList) {
        	String key = "";
        	if (converter != null) {
        		key = converter.convertToPresentation(value, new ValueContext());
        	} else {
        		key = value.toString();
        	}
        	if (items.putIfAbsent(key, value) == null) captions.add(key);
        }
        getState().dropDownList = captions;
	}
 
	/**
	 * Constructor for SimpleSelectRenderer.
	 * 
	 * @param setter Method reference to right setter of T 
	 * @param dropDownList List of the of the selection options to be shown in drop down menu
     * @param title Tooltip text for the select
	 */
	public SimpleSelectRenderer(Setter<T,A> setter, List<A> dropDownList, String title) {
        super((Class<A>) Object.class);

        this.dropDownList = dropDownList;

        setItems(dropDownList);

        getState().title = title;

    	setupSimpleSelectRenderer(setter);
    }

	private void setupSimpleSelectRenderer(final Setter<T,A> setter) {
        registerRpc(new SimpleSelectRendererServerRpc() {

            public void onChange(String rowKey, String newKey) {

            	Grid<T> grid = getParentGrid();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T,A> column = getParent();
            	Map<String,A> dropDownItems = items;
            	A newValue = dropDownItems.get(newKey);
             	setter.accept(item,newValue);
            	grid.getDataProvider().refreshItem(item);

            	fireItemEditEvent(item, column, newValue);
             }

			@Override
			public void applyIsEnabledCheck(String rowKey) {
            	Grid<T> grid = getParentGrid();
            	T item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	if (item != null) {
            		boolean result = isEnabledProvider.apply(item);
    				getRPC().setEnabled(result,rowKey);				
            	}
			}
        });
    }
	
	/**
	 * Used internally
	 */
    @Override
    public JsonValue encode(A value) {
        if (converter == null) {
            return encode((String) value, String.class);
        } else {
            return encode(converter.convertToPresentation(value, new ValueContext()), String.class);
        }
    }	
	
    @Override
    protected SimpleSelectRendererState getState()  {
        return (SimpleSelectRendererState) super.getState();
    }
    
    private SimpleSelectRendererClientRpc getRPC() {
        return getRpcProxy(SimpleSelectRendererClientRpc.class);
    }
}
