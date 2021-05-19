package ru.nsu.fit.library.distribution.UI;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.nsu.fit.library.distribution.Distribution;
import ru.nsu.fit.library.distribution.DistributionService;
import ru.nsu.fit.library.main.UI.MainView;
import ru.nsu.fit.library.reader.Reader;
import ru.nsu.fit.library.reader.ReaderService;

import java.time.LocalDate;

@Route(value = "distribution_by_reader", layout = MainView.class)
public class DistributionByReaderView extends VerticalLayout {
    private final DistributionService distributionService;
    private final ReaderService readerService;
    private final Grid<Distribution> grid = new Grid<>(Distribution.class);

    private final ComboBox<Reader> reader = new ComboBox<>("Choose reader:");

    public DistributionByReaderView(DistributionService distributionService, ReaderService readerService) {
        this.distributionService = distributionService;
        this.readerService = readerService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        add(toolbar, grid);

        updateList();
    }

    private HorizontalLayout configureToolBar() {
        reader.setItems(readerService.findAll());
        reader.addValueChangeListener(value -> updateList());
        HorizontalLayout toolBar = new HorizontalLayout(reader);
        toolBar.setAlignItems(Alignment.BASELINE);
        return toolBar;
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(distribution -> {
            Distribution tmp = distributionService.findDistributionFetch(distribution);
            return tmp.getBook();
        }).setHeader("Book").setSortProperty("book");
        grid.addColumn(distribution -> {
            Distribution tmp = distributionService.findDistributionFetch(distribution);
            if (tmp.getDateReturn() != null) return "Returned, date: " + tmp.getDateReturn().toString();
            else {
                LocalDate rental =  tmp.getDateGive().plusDays(tmp.getRentalPeriod().getDays());
                return "Not returned, deadline: " + rental;
            }
        }).setHeader("Return status").setSortProperty("return_status");
        grid.addColumn(Distribution::getDateGive).setHeader("Give Date").setSortProperty("dateGive");
        grid.addColumn(Distribution::getDateReturn).setHeader("Return Date").setSortProperty("dateReturn");
    }

    private void updateList() {
        grid.setItems(distributionService.findAllByReader(reader.getValue()));
    }
}
