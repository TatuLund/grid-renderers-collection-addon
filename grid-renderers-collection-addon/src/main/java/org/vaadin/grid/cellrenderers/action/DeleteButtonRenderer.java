package org.vaadin.grid.cellrenderers.action;

import java.lang.reflect.Method;

import org.vaadin.grid.cellrenderers.client.action.DeleteButtonRendererServerRpc;
import org.vaadin.grid.cellrenderers.client.action.DeleteButtonRendererState;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.util.ReflectTools;

/**
 * DeleteButtonRenderer creates two stage Delete button. Clicking the delete button
 * will turn it to confirm mode, where it stays next XX seconds (default = 10) before
 * going back to normal mode. If button is clicked second time in confrim mode
 * the action {@link DeleteRendererClickEvent} is triggered.  
 * 
 * @see Grid#addColumn(String, com.vaadin.ui.renderers.AbstractRenderer)
 * @see Grid#addColumn(com.vaadin.data.ValueProvider, com.vaadin.ui.renderers.AbstractRenderer)
 * @see Grid#addColumn(com.vaadin.data.ValueProvider, com.vaadin.data.ValueProvider, com.vaadin.ui.renderers.AbstractRenderer)
 * 
 * @author Tatu Lund
 *
 * @param <T> Bean type of the Grid where the renderer is being used
 */
public class DeleteButtonRenderer<T> extends ClickableRenderer<T,Boolean> {

    /**
     * An interface for listening to {@link DeleteRendererClickEvent renderer click
     * events}.
     *
     */
    public interface DeleteRendererClickListener extends ConnectorEventListener {

        static final Method CLICK_METHOD = ReflectTools.findMethod(
                DeleteRendererClickListener.class, "click", DeleteRendererClickEvent.class);

        /**
         * Called when a rendered button is clicked.
         *
         * @param event
         *            the event representing the click
         */
        void click(DeleteRendererClickEvent event);
    }

    /**
     * An event fired when a clickable widget rendered by a DeleteButtonRenderer is
     * clicked in confirm state.
     *
     */
    public static class DeleteRendererClickEvent extends ClickEvent {

        private final Object item;
        private final Column column;

        protected DeleteRendererClickEvent(Grid source, Object item,
                Column column, MouseEventDetails mouseEventDetails) {
            super(source, mouseEventDetails);
            this.item = item;
            this.column = column;
        }

        /**
         * Returns the item of the row where the click event originated.
         *
         * @return the item of the clicked row
         */
        public Object getItem() {
            return item;
        }

        /**
         * Returns the {@link Column} where the click event originated.
         *
         * @return the column of the click event
         */
        public Column getColumn() {
            return column;
        }
    }


    /**
     * Creates a new button renderer
     * and e.g. localized Strings for meaning delete and confirm
     *
     * @param delete
     *            text meaning delete
     * @param confirm
     *            text meaning confirm
     */
    public DeleteButtonRenderer(String delete, String confirm) {
        super(Boolean.class, "");
        getState().delete = delete;
        getState().confirm = confirm;
        setupRpc();
    }

    /**
     * Creates a new delete button renderer and adds the given click listener to it
     * and e.g. localized Strings for meaning delete and confirm
     *
     * @param listener
     *            the click listener to register
     * @param delete
     *            text meaning delete
     * @param confirm
     *            text meaning confirm
     */
    public DeleteButtonRenderer(DeleteRendererClickListener listener,
            String delete, String confirm) {
        this(delete, confirm);
        addClickListener(listener);
    }

    /**
     * Creates a new delete button renderer.
     * 
     * Delete button renderer creates two stage Delete - Confirm button
     * When in confirm state "delete-confirm" stylename is set.
     */
    public DeleteButtonRenderer() {
        this("Delete","Confirm");
    }

    /**
     * Creates a new button renderer and adds the given click listener to it.
     *
     * @param listener
     *            the click listener to register
     */
    public DeleteButtonRenderer(DeleteRendererClickListener listener) {
        this(listener, "Delete", "Confirm");
    }

    private void setupRpc() {
    	registerRpc(new DeleteButtonRendererServerRpc() {
            public void onClick(String rowKey, MouseEventDetails mouseDetails) {
            	Grid<T> grid = getParentGrid();
            	Object item = grid.getDataCommunicator().getKeyMapper().get(rowKey);
            	Column<T, Boolean> column = getParent();
            	fireEvent(new DeleteRendererClickEvent(grid, item, column, mouseDetails));
    		}
    	});
    }
    
    /**
     *  Get null presentation string
     */
    @Override
    public String getNullRepresentation() {
        return super.getNullRepresentation();
    }

    @Override
    protected DeleteButtonRendererState getState() {
        return (DeleteButtonRendererState) super.getState();
    }

    @Override
    protected DeleteButtonRendererState getState(boolean markAsDirty) {
        return (DeleteButtonRendererState) super.getState(markAsDirty);
    }

    /**
     * Sets whether the data should be rendered as HTML (instead of text).
     * <p>
     * By default everything is rendered as text.
     *
     * @param htmlContentAllowed
     *            <code>true</code> to render as HTML, <code>false</code> to
     *            render as text
     */
    public void setHtmlContentAllowed(boolean htmlContentAllowed) {
        getState().htmlContentAllowed = htmlContentAllowed;
    }

    /**
     * Gets whether the data should be rendered as HTML (instead of text).
     * <p>
     * By default everything is rendered as text.
     *
     * @return <code>true</code> if the renderer renders a HTML,
     *         <code>false</code> if the content is rendered as text
     */
    public boolean isHtmlContentAllowed() {
        return getState(false).htmlContentAllowed;
    }

    /**
     * Adds a click listener to this button renderer. The listener is invoked
     * every time one of the buttons rendered by this renderer is clicked.
     *
     * @param listener
     *            the click listener to be added
	 * @return Returns Registration object that can be used for listener removal 
     */
    public Registration addClickListener(DeleteRendererClickListener listener) {
        return addListener(DeleteRendererClickEvent.class, listener,
        		DeleteRendererClickListener.CLICK_METHOD);
    }

    /**
     * Set style name for Delete button when in normal state, default "v-deletebutton" 
     * 
     * @param styleName A style name String 
     */
    public void setStyleName(String styleName) {
    	if (styleName == null) throw new IllegalArgumentException("Style cannot be null");
    	getState().normalStyle = styleName;
    }
    
    /**
     * Set style name for Delete button when in confirm state, default "delete-confirm" 
     * 
     * @param styleName A style name String
     */
    public void setDeleteStyleName(String styleName) {
    	if (styleName == null) throw new IllegalArgumentException("Delete style cannot be null");
    	getState().deleteStyle = styleName;
    }
    
    /**
     * Set the time out before button goes back to normal mode (default 10 seconds) 
     * 
     * @param seconds Time out in seconds
     */
    public void setTimeout(int seconds) {
    	getState().timeOut = seconds;
    }
    
    /**
     * Get the current timeout
     * 
     * @return The current timeout
     */
    public int getTimeout() {
    	return getState().timeOut;
    }
    
}
