package ru.nsu.fit.library.reader;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ReaderForm extends VerticalLayout {
    private final TextField firstName = new TextField("FirstName");
    private final TextField lastName = new TextField("LastName");

    private final Button save = new Button("save");
    private final Button delete = new Button("delete");
    private final Button close = new Button("close");

    private final Binder<Reader> readerBinder = new Binder<>(Reader.class);
    private final ReaderService readerService;
    private Reader reader;

    public ReaderForm(ReaderService readerService) {
        this.readerService = readerService;
        readerBinder.bindInstanceFields(this);
        readerBinder.forField(firstName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Reader::getFirstName, Reader::setFirstName);
        readerBinder.forField(lastName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Reader::getLastName, Reader::setLastName);
        add(createFieldsLayout(), createButtonsLayout());
    }
    private HorizontalLayout createFieldsLayout(){
        lastName.setRequired(true);
        firstName.setRequired(true);
        return new HorizontalLayout(firstName, lastName);
    }
    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, reader)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        readerBinder.addStatusChangeListener(e -> save.setEnabled(readerBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try{
            readerBinder.writeBean(reader);
            if (!readerService.exist(reader)){
                fireEvent(new saveEvent(this, reader));
            }
            else {
                Notification.show("Save error: "+ "This reader already exists").
                        setPosition(Notification.Position.TOP_START);
            }
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setReader(Reader reader) {
        this.reader = new Reader(reader);
        readerBinder.readBean(reader);
    }

    public static abstract class ReaderFormEvent extends ComponentEvent<ReaderForm> {
        private final Reader reader;

        protected ReaderFormEvent(ReaderForm source, Reader reader){
            super(source, false);
            this.reader = reader;
        }

        public Reader getReader() {
            return reader;
        }
    }

    public static class saveEvent extends ReaderFormEvent {
        saveEvent(ReaderForm source, Reader reader) {
            super(source, reader);
        }
    }

    public static class deleteEvent extends ReaderFormEvent {
        deleteEvent(ReaderForm source, Reader reader) {
            super(source, reader);
        }
    }

    public static class closeEvent extends ReaderFormEvent {
        closeEvent(ReaderForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
