package ru.nsu.fit.library.queriesUI;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.nsu.fit.library.main.UI.MainView;
import ru.nsu.fit.library.reader.ReaderService;

import java.util.Collections;
import java.util.List;

@Route(value = "Expired_distribution", layout = MainView.class)
public class Q_2 extends VerticalLayout {
    private final ReaderService readerService;
    private final Grid<Object[]> grid;

    public Q_2(ReaderService readerService) {
        this.readerService = readerService;
        this.grid = new Grid<>();
        configureGrid();
        add(grid);
        setSizeFull();
        listBooks();
    }

    private void configureGrid() {
        grid.addColumn(objects -> objects[0]).setHeader("Reader");
        grid.addColumn(objects -> objects[1]).setHeader("Book");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        setSizeFull();
    }

    private void listBooks() {
        List<Object[]> artistList = readerService.findReaderExpired();
        if (artistList.isEmpty()) {
            grid.setItems(Collections.emptyList());
        } else grid.setItems(artistList);
    }
}
