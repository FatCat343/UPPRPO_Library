package ru.nsu.fit.library.reader;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import ru.nsu.fit.library.main.UI.MainView;

@Route(value = "readers", layout = MainView.class)
public class ReaderView extends VerticalLayout {
    private final ReaderService readerService;
    private final Grid<Reader> grid = new Grid<>(Reader.class);
    private final ReaderForm form;

    public ReaderView(ReaderService readerService) {
        this.readerService = readerService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new ReaderForm(readerService);
        form.addListener(ReaderForm.saveEvent.class, this::saveReader);
        form.addListener(ReaderForm.deleteEvent.class, this::deleteReader);
        form.addListener(ReaderForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);
        updateList();
        closeEditor();
    }
    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Reader::getFirstName).setHeader("FirstName").setSortProperty("firstname");
        grid.addColumn(Reader::getLastName).setHeader("LastName").setSortProperty("lastname");
        grid.asSingleSelect().addValueChangeListener(event -> editReader(event.getValue()));
//        grid.asSingleSelect().addValueChangeListener(event -> editStaff(event.getValue()));
//        grid.setItemDetailsRenderer(TemplateRenderer.<Reader>of(
//                "<div class='custom-details' style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
//                        + "<div>Assigned to storage : <b>[[item.storage]]!</b></div>"
//                        + "</div>")
//                .withProperty("storage", staff -> {
//                    return staffService.findStaffByIdFetch(staff).getStorage().toString();
//                })
//                // This is now how we open the details
//                .withEventHandler("handleClick", staff -> {
//                    grid.getDataProvider().refreshItem(staff);
//                }));
//        grid.setDetailsVisibleOnClick(false);
//
//        grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid
//                .setDetailsVisible(item, !grid.isDetailsVisible(item))));
    }

    private HorizontalLayout configureToolBar() {
        Button addStaffButton = new Button("Add Reader");
        addStaffButton.addClickListener(click -> addReader());
        return new HorizontalLayout(addStaffButton);
    }

    private void addReader() {
        grid.asSingleSelect().clear();
        form.setReader(new Reader());
        form.setVisible(true);
    }

    private void updateList(){
        grid.setItems(readerService.findAll());
    }

    private void saveReader(ReaderForm.saveEvent event) {
        readerService.save(event.getReader());
        updateList();
        closeEditor();
    }

    private void deleteReader(ReaderForm.deleteEvent event) {
        readerService.delete(event.getReader());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        grid.asSingleSelect().clear();
        form.setReader(null);
        form.setVisible(false);

    }

    private void editReader(Reader reader) {
        if (reader == null) closeEditor();
        else {
            form.setReader(reader);
            form.setVisible(true);
        }
    }


}
