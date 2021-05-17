package ru.nsu.fit.library.distribution.UI;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.nsu.fit.library.book.Book;
import ru.nsu.fit.library.book.BookService;
import ru.nsu.fit.library.distribution.Distribution;
import ru.nsu.fit.library.distribution.DistributionService;
import ru.nsu.fit.library.distribution.rentalPeriod.RentalPeriod;
import ru.nsu.fit.library.distribution.rentalPeriod.RentalPeriodService;
import ru.nsu.fit.library.distribution.rentalPeriod.UI.RentalPeriodForm;
import ru.nsu.fit.library.reader.Reader;
import ru.nsu.fit.library.reader.ReaderService;

import java.time.LocalDate;

public class DistributionForm extends VerticalLayout {
    private final DatePicker dateGive = new DatePicker("Give Date");
    private final DatePicker dateReturn = new DatePicker("Return Date");

    private final ComboBox<Reader> reader = new ComboBox<>("Reader");
    private final ComboBox<Book> book = new ComboBox<>("Book");
    private final ComboBox<RentalPeriod> rentalPeriod = new ComboBox<>("Rental Period:");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private final Binder<Distribution> distributionBinder = new Binder<>(Distribution.class);
    private final DistributionService distributionService;
    private final BookService bookService;
    private Distribution distribution;
    private final RentalPeriodService rentalPeriodService;
    private final RentalPeriodForm rentalPeriodForm;

    public DistributionForm(ReaderService readerService, BookService bookService,
                            RentalPeriodService rentalPeriodService, DistributionService distributionService) {
        this.distributionService = distributionService;
        this.rentalPeriodService = rentalPeriodService;
        this.bookService = bookService;
        distributionBinder.bindInstanceFields(this);
        reader.setItems(readerService.findAll());
        reader.setRequired(true);
        book.setItems(bookService.findNotGiven());
        book.setRequired(true);
        rentalPeriod.setItems(rentalPeriodService.findAll());
        rentalPeriod.setRequired(true);
        dateGive.setRequired(true);

        rentalPeriodForm = new RentalPeriodForm();
        rentalPeriodForm.addListener(RentalPeriodForm.saveEvent.class,  this::saveRentalPeriod);
        rentalPeriodForm.addListener(RentalPeriodForm.closeEvent.class, e -> closeEditor());

        add(createFieldsLayout(), createButtonsLayout(), rentalPeriodForm);

        closeEditor();
    }

    private HorizontalLayout createFieldsLayout() {
        Button addRentalPeriod = new Button("Add Rental Period");
        addRentalPeriod.addClickListener(click -> addRentalPeriod());
        dateGive.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            if (selected != null) {
                dateReturn.setMin(selected.plusDays(1));
            } else {
                dateReturn.setMin(null);
            }
        });
        dateReturn.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            if (selected != null) {
                dateGive.setMax(selected.minusDays(1));
            } else {
                dateGive.setMax(null);
            }
        });
        HorizontalLayout fields = new HorizontalLayout(book, reader, rentalPeriod, dateGive, dateReturn, addRentalPeriod);
        fields.setAlignItems(Alignment.BASELINE);
        return fields;
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, distribution)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        distributionBinder.addStatusChangeListener(e -> save.setEnabled(distributionBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try{
            distributionBinder.writeBean(distribution);
            fireEvent(new saveEvent(this, distribution));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setBooks(){
        book.setItems(bookService.findNotGiven());
    }

    private void closeEditor() {
        rentalPeriodForm.setRentalPeriod(null);
        rentalPeriodForm.setVisible(false);
    }

    private void addRentalPeriod() {
        rentalPeriodForm.setRentalPeriod(new RentalPeriod());
        rentalPeriodForm.setVisible(true);
    }

    private void saveRentalPeriod(RentalPeriodForm.saveEvent event) {
        rentalPeriodService.save(event.getRentalPeriod());
        this.rentalPeriod.setItems(rentalPeriodService.findAll());
        closeEditor();
    }

    public void setDistributionNotFetched(Distribution distribution) {
        this.distribution = distribution;
        distributionBinder.readBean(distribution);
    }

    public void setDistribution(Distribution distribution) {
        Distribution fetchedDistribution = distributionService.findDistributionFetch(distribution);
        this.distribution = fetchedDistribution;
        distributionBinder.readBean(fetchedDistribution);
    }

    public static abstract class DistributionFormEvent extends ComponentEvent<DistributionForm> {
        private final Distribution distribution;

        protected DistributionFormEvent(DistributionForm source, Distribution distribution){
            super(source, false);
            this.distribution = distribution;
        }

        public Distribution getDistribution() {
            return distribution;
        }
    }

    public static class saveEvent extends DistributionFormEvent {
        saveEvent(DistributionForm source, Distribution distribution) {
            super(source, distribution);
        }
    }

    public static class deleteEvent extends DistributionFormEvent {
        deleteEvent(DistributionForm source, Distribution distribution) {
            super(source, distribution);
        }
    }

    public static class closeEvent extends DistributionFormEvent {
        closeEvent(DistributionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
