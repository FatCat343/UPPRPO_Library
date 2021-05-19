package ru.nsu.fit.library.bookPosition.UI;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.nsu.fit.library.bookPosition.BookPosition;
import ru.nsu.fit.library.bookPosition.BookPositionService;
import ru.nsu.fit.library.storage.Storage;
import ru.nsu.fit.library.storage.StorageService;

public class BookPositionForm extends VerticalLayout {
    IntegerField rackNumber = new IntegerField("Rack Number");
    IntegerField shelfNumber = new IntegerField("Shelf Number");
    ComboBox<Storage> storage = new ComboBox<>("Storage");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<BookPosition> bookPositionBinder = new Binder<>(BookPosition.class);
    BookPositionService bookPositionService;
    private BookPosition bookPosition;

    public BookPositionForm(BookPositionService bookPositionService, StorageService storageService) {
        this.bookPositionService = bookPositionService;
        bookPositionBinder.bindInstanceFields(this);
        storage.setItems(storageService.findAll());
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout(){
        shelfNumber.setRequiredIndicatorVisible(true);
        rackNumber.setRequiredIndicatorVisible(true);
        storage.setRequired(true);
        return new HorizontalLayout(shelfNumber, rackNumber, storage);
    }

    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, bookPosition)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        bookPositionBinder.addStatusChangeListener(e -> save.setEnabled(bookPositionBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try{
            bookPositionBinder.writeBean(bookPosition);
            if (!bookPositionService.exists(bookPosition)) {
                fireEvent(new saveEvent(this, bookPosition));
            }
            else {
                Notification.show("Save error: "+ "This item already exists").
                        setPosition(Notification.Position.TOP_START);
            }
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setBookPosition(BookPosition bookPosition) {
        this.bookPosition = new BookPosition(bookPosition);
        bookPositionBinder.readBean(bookPosition);
    }

    public static abstract class BookPositionFormEvent extends ComponentEvent<BookPositionForm> {
        private final BookPosition bookPosition;

        protected BookPositionFormEvent(BookPositionForm source, BookPosition bookPosition){
            super(source, false);
            this.bookPosition = bookPosition;
        }

        public BookPosition getBookPosition() {
            return bookPosition;
        }
    }

    public static class saveEvent extends BookPositionFormEvent {
        saveEvent(BookPositionForm source, BookPosition bookPosition) {
            super(source, bookPosition);
        }
    }

    public static class deleteEvent extends BookPositionFormEvent {
        deleteEvent(BookPositionForm source, BookPosition bookPosition) {
            super(source, bookPosition);
        }
    }

    public static class closeEvent extends BookPositionFormEvent {
        closeEvent(BookPositionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
