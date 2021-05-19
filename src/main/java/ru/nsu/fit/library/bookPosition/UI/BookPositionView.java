package ru.nsu.fit.library.bookPosition.UI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.nsu.fit.library.bookPosition.BookPosition;
import ru.nsu.fit.library.bookPosition.BookPositionService;
import ru.nsu.fit.library.main.UI.MainView;
import ru.nsu.fit.library.storage.Storage;
import ru.nsu.fit.library.storage.StorageService;

@Route(value = "bookPosition", layout = MainView.class)
public class BookPositionView extends VerticalLayout {
    private final BookPositionService bookPositionService;
    private final Grid<BookPosition> grid = new Grid<>(BookPosition.class);

    private final BookPositionForm form;
    public BookPositionView(BookPositionService bookPositionService, StorageService storageService) {
        this.bookPositionService = bookPositionService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new BookPositionForm(bookPositionService, storageService);
        form.addListener(BookPositionForm.saveEvent.class, this::saveBookPosition);
        form.addListener(BookPositionForm.deleteEvent.class, this::deleteBookPosition);
        form.addListener(BookPositionForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        updateList();
        closeEditor();
    }

    private void configureGrid(){
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(BookPosition::getShelfNumber).setHeader("Shelf Number").setSortProperty("shelf");
        grid.addColumn(BookPosition::getRackNumber).setHeader("Rack Number").setSortProperty("rack");

        grid.addColumn(bookPosition -> {
            Storage s = bookPosition.getStorage();
            return s.toString();
        }).setHeader("Storage").setSortProperty("storage");

        grid.asSingleSelect().addValueChangeListener(event -> editBookPosition(event.getValue()));
    }

    private HorizontalLayout configureToolBar() {
        Button addBookPositionButton = new Button("Add BookPosition");
        addBookPositionButton.addClickListener(click -> addBookPosition());
        return new HorizontalLayout(addBookPositionButton);
    }

    private void addBookPosition() {
        System.out.println(grid.asSingleSelect().getValue());
        editBookPosition(new BookPosition());
    }

    private void saveBookPosition(BookPositionForm.saveEvent event) {
        BookPosition bookPosition = bookPositionService.save(event.getBookPosition());
        updateList();
        closeEditor();
    }

    private void deleteBookPosition(BookPositionForm.deleteEvent event) {
        bookPositionService.delete(event.getBookPosition());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(bookPositionService.findAllFetch());
    }

    private void closeEditor() {
        grid.asSingleSelect().clear();
        form.setBookPosition(null);
        form.setVisible(false);
    }

    private void editBookPosition(BookPosition bookPosition) {
        if (bookPosition == null) closeEditor();
        else {
            form.setBookPosition(bookPosition);
            form.setVisible(true);
        }
    }

}