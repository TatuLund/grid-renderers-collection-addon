[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/grid-renderers-collection-for-vaadin7)
[![Stars on Vaadin Directory](https://img.shields.io/vaadin-directory/star/grid-renderers-collection-for-vaadin7.svg)](https://vaadin.com/directory/component/grid-renderers-collection-for-vaadin7)

# Grid Renderers Add-on for Vaadin 8

Grid Renderers collection is a set of renderers for Vaadin 8 Grid. It is possible to use Table as editable
canvas with components in Table cells. With help of this collection it is possible to achieve similar
functionality with Grid component. In addition to Editable renderers this collection has couple of renderers
for advanced data presentation too.

## Online demo

Try the add-on demo at *TBD*

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to https://vaadin.com/directory/component/grid-renderers-collection-for-vaadin7

## Building and running demo

git clone https://github.com/vaadin/grid-renderers-collection-addon.git
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for grid-renderers-collection-addon-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your grid-renderers-collection-addon-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the grid-renderers-collection-addon-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/grid-renderers-collection-addon-demo/ to see the application.

### Debugging client-side

The most common way of debugging and making changes to the client-side code is dev-mode. To create debug configuration for it, open grid-renderers-collection-addon-demo project properties and click "Create Development Mode Launch" button on the Vaadin tab. Right-click newly added "GWT development mode for grid-renderers-collection-addon-demo.launch" and choose Debug As > Debug Configurations... Open up Classpath tab for the development mode configuration and choose User Entries. Click Advanced... and select Add Folders. Choose Java and Resources under grid-renderers-collection-addon/src/main and click ok. Now you are ready to start debugging the client-side code by clicking debug. Click Launch Default Browser button in the GWT Development Mode in the launched application. Now you can modify and breakpoints to client-side classes and see changes by reloading the web page. 

Another way of debugging client-side is superdev mode. To enable it, uncomment devModeRedirectEnabled line from the end of DemoWidgetSet.gwt.xml located under grid-renderers-collection-addon-demo resources folder and compile the widgetset once by running vaadin:compile Maven target for grid-renderers-collection-addon-demo. Refresh grid-renderers-collection-addon-demo project resources by right clicking the project and choosing Refresh. Click "Create SuperDevMode Launch" button on the Vaadin tab of the grid-renderers-collection-addon-demo project properties panel to create superder mode code server launch configuration and modify the class path as instructed above. After starting the code server by running SuperDevMode launch as Java application, you can navigate to http://localhost:8080/grid-renderers-collection-addon-demo/?superdevmode. Now all code changes you do to your client side will get compiled as soon as you reload the web page. You can also access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings. 

 
## Release notes

### Version 2.6.0
* Removed setIsEnabledProvider(..,boolean) option as it was not working as intended
* Replaced the above with caching option setIsEnabledProvider(..,int)

### Version 2.5.0
* Added setIsEnabledProvider(..,true) alternate method to EditableRenderer
* Fixed issues with some Editable renderers triggering double events
* Fixed client side exception in DateFieldRenderer

### Version 2.4.0
* Added support for Resource in column for BrowserOpenerRenderer

### Version 2.3.3
* Fixing bug EditableRenderers did not honor grid.setEnabled(false), see issue #53

### Version 2.3.2
* Fixing timing issue in BooleanSwitchRenderer

### Version 2.3.1
* Fixed TextFieldRenderer to have proper style when not editable
* Added more configuration options to DeleteButtonRenderer

### Version 2.3.0
* Added setIsEnabledProvider(..) method to EditableRenderer and implemented it in its subclasses, See issue #46

### Version 2.2.10
* Fixing issue #36, BrowserOpenerRenderer url fetching was not made correctly

### Version 2.2.9
* Added tooltip support to HtmlButtonRenderer and BrowserOpenerRenderer.

### Version 2.2.8
* Added feature to BrowserOpenerRenderer. With alternative constructor open UI with URI fragment given in cell.

### Version 2.2.7
* Added BrowserOpenerRenderer. This is a button that opens a new browser window when clicked.

### Version 2.2.6
* Added setOffset to RowIndexRenderer, so that row index count can start from defined position

### Version 2.2.5
* Minor change for Vaadin 8.2+ compatibility

### Version 2.2.4
* Added support of ordinals to RowIndexRenderer

### Version 2.2.3
* Added RowIndexRenderer

### Version 2.2.2
* Fixing issue #24, SimpleSelectRenderer conversion logic was flawed since Vaadin 8 migration was halfbaked

### Version 2.2.1
* Fixing issue #22, BooleanSwitchRenderer.setReadOnly(..) did not work
* Fix, The blurChangeMode did not work properly

### Version 2.2.0
* Added blurChangeMode to TextFieldRenderer and DateFieldRenderer to tackle issue #18.
* Added eagerChangeMode to TextFieldRenderer to tackle issue #18.
* Added support for different DateResolutions to DateFieldRenderer
* Added HtmlButtonRenderer to collection

### Version 2.1.1
* Added isReadOnly() and setReadOnly(..) to Editable Renderers.

### Version 2.1.0
* Added ConverterRenderer

### Version 2.0.0
* First version for Vaadin 8, based on featureset of version 1.1.2 for Vaadin 7
* There are API changes
* Updated demo

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

Grid Renderers collection is initially written by Ilia Motornyi 

SimpleSelectRenderer, BooleanSwitchRenderer, BlobImageRenderer, DateFieldRenderer, TextFieldRenderer, SparklineRenderer,  RatingStarsRenderer, HtmlButtonRenderer, DeleteButtonRenderer, ConverterRenderer, RowIndexRenderer, BrowserOpenerRenderer
contributed by Tatu Lund (tatu@vaadin.com corresponding author)

ItemEditEvent, GridNavigationExtension contributed by Mikael Granqvist

Major pieces of development of this add-on has been sponsored by multiple Support and Prime customers of Vaadin. See vaadin.com/support and Development on Demand for more details. 

# Developer Guide

## Getting started

Here is a simple example on how to try out the add-on component:


    TextFieldRenderer<MyPojo,String> renderer = new TextFieldRenderer<>(MyPojo::setText);
    grid.addColumn(MyPojo::getText, renderer);

For a more comprehensive examples, see org.vaadin.grid.cellrenderers.demo.DemoUI

## Documentation

See JavaDoc online here

https://vaadin.com/directory/component/grid-renderers-collection-for-vaadin7/api

## Features

### BrowserOpenerRenderer
A button which opens a new browser window with url in the Grid's cell. 

### RowIndexRenderer
A simple Renderer that renders the row index as a number. Not affected by sorting. 

### HtmlButtonRenderer
Alternative to the ButtonRenderer included to Vaadin framework. This one supports also HTML content and
does not propagate click event when used.

### DeleteButtonRenderer
Two stage (Delete & Confirm) Delete action button. Delete and Confirm texts can be configured e.g. for
localization. There is style name for Confirm state in order to add accent in custom theme if needed.
Vaadin 8 version of this renderer can be found in https://vaadin.com/directory#!addon/gridfastnavigation-add-on

### SimpleSelectRenderer
Select a value with popup selector from small set of values. This Renderer is like ComboBox, but much
simpler. With Converter it is possible to map non String values to String. See demo.

### BlobImageRenderer
Render small images directly from byte[] of the bean. Useful when your read image as Blob field
from SQL database. Note, use only small images to avoid excess overhead.

### CheckboxRenderer
Single-click editor for boolean columns - Editor aware. Most suitable for unbuffered Grid Label options
can be configured for localization.

### BooleanSwitchRenderer
Alternative version of CheckboxRenderer similar to editable renderers.

### DateFieldRenderer
Inline Dates editor.

### TextFieldRenderer
Multipurpose inline Text editor. Supports various types of data using Converter that can be set
via TextFieldRenderer.setConverter() API. TextFieldRenderer and DateFieldRenderer are suitable when
you need to edit few columns only. Tab key jumps between editable fields by default. The input is
directly stored in the container. For backend commits it is recommend to have "save" button in the
UI.

### SparklineRenderer
Configurable SparklineRenderer. Renders collection of Numbers as a simple chart. The renderer uses
Sparklines add-on by Marc Englund (which inturn uses gwt-graphics add-on). SparklineRenderer has
SparklineConfiguration class inside, which controls various Sparkline configuration options thru
shared state. Most of the settings have immediate effect, see the demo.

### RatingStarsRenderer

RatingStarsRenderer is based on Widget in RatingStars add-on by Teemu Pöntelin. You can use 

RatingStarsRenderer both as a view only or editable field renderer. The max number of stars can
be also configured.

### GridNavigationExtension

GridNavigationExtension makes possible to quickly navigate Grid with keyboard and input data
with editable Renderers. See the demo. GridNavigation extension was originally written by Mikael
Granqvist. 

Note, there is another similar type of extension available also for use with Grid's unbuffered
editor, which is called GridFastNavigation https://vaadin.com/directory#!addon/gridfastnavigation-add-on
