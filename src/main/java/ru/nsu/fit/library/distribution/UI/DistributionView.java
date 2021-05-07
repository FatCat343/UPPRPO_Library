package ru.nsu.fit.library.distribution.UI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.nsu.fit.library.book.BookService;
import ru.nsu.fit.library.distribution.Distribution;
import ru.nsu.fit.library.distribution.DistributionService;
import ru.nsu.fit.library.main.UI.MainView;
import ru.nsu.fit.library.reader.ReaderService;

@Route(value = "distribution", layout = MainView.class)
public class DistributionView extends VerticalLayout {
    private final DistributionService distributionService;
    private final Grid<Distribution> grid = new Grid<>(Distribution.class);

    private final DistributionForm form;

    public DistributionView(DistributionService distributionService, ReaderService readerService,
                            BookService bookService) {
        this.distributionService = distributionService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new DistributionForm(readerService, bookService, distributionService);
        form.addListener(DistributionForm.saveEvent.class, this::saveDistribution);
        form.addListener(DistributionForm.deleteEvent.class, this::deleteDistribution);
        form.addListener(DistributionForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        updateList();
        closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        Button addDistributionButton = new Button("Add Distribution");
        addDistributionButton.addClickListener(click -> addDistribution());
        return new HorizontalLayout(addDistributionButton);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Distribution::getDateGive).setHeader("Give Date").setSortProperty("dateGive");
        grid.addColumn(Distribution::getDateReturn).setHeader("Return Date").setSortProperty("dateReturn");
        grid.addColumn(distribution -> {
            Distribution tmp = distributionService.findDistributionFetch(distribution);
            return tmp.getReader();
        }).setHeader("Reader").setSortProperty("reader");
        grid.addColumn(distribution -> {
            Distribution tmp = distributionService.findDistributionFetch(distribution);
            return tmp.getBook();
        }).setHeader("Book").setSortProperty("book");
        grid.asSingleSelect().addValueChangeListener(event -> editDistribution(event.getValue()));
    }

    private void addDistribution() {
        grid.asSingleSelect().clear();
        form.setDistributionNotFetched(new Distribution());
        form.setVisible(true);
    }

    private void updateList() {
        grid.setItems(distributionService.findAll());
    }

    private void saveDistribution(DistributionForm.saveEvent event) {
        Distribution distribution = distributionService.save(event.getDistribution());
        form.setBooks();
        updateList();
        closeEditor();
    }

    private void deleteDistribution(DistributionForm.deleteEvent event) {
        distributionService.delete(event.getDistribution());
        form.setBooks();
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        grid.asSingleSelect().clear();
        form.setDistribution(null);
        form.setVisible(false);
    }

    private void editDistribution(Distribution distribution) {
        if (distribution == null) closeEditor();
        else {
            form.setDistribution(distribution);
            form.setVisible(true);
        }
    }
}
