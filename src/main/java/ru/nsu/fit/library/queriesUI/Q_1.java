package ru.nsu.fit.library.queriesUI;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.nsu.fit.library.book.BookService;
import ru.nsu.fit.library.main.UI.MainView;

import java.util.Collections;
import java.util.List;

@Route(value = "Special_Tasks", layout = MainView.class)
public class Q_1 extends VerticalLayout {
    private final BookService bookService;
    private final Grid<Object[]> grid;

    public Q_1(BookService bookService) {
        this.bookService = bookService;
        this.grid = new Grid<>();
        configureGrid();
        add(grid);
        setSizeFull();
        listBooks();
    }

    private void configureGrid() {
        grid.addColumn(objects -> objects[0]).setHeader("Title");
        grid.addColumn(objects -> objects[1]).setHeader("Author");
        grid.addColumn(objects -> objects[2]).setHeader("Pick count");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        setSizeFull();
    }

    private void listBooks() {
        List<Object[]> artistList = bookService.findBooksByPopularity();
        if (artistList.isEmpty()) {
            grid.setItems(Collections.emptyList());
        } else grid.setItems(artistList);
    }
}
