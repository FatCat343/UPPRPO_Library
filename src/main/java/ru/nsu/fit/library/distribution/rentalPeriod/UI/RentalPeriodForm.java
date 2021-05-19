package ru.nsu.fit.library.distribution.rentalPeriod.UI;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.nsu.fit.library.distribution.rentalPeriod.RentalPeriod;

public class RentalPeriodForm extends VerticalLayout {
    IntegerField days = new IntegerField("Rental Period Duration:");
    Button save = new Button("save");
    Button close = new Button("close");

    Binder<RentalPeriod> rentalPeriodBinder = new Binder<>(RentalPeriod.class);
    private RentalPeriod rentalPeriod;

    public RentalPeriodForm() {
        rentalPeriodBinder.bindInstanceFields(this);

        add(createFieldsLayout(), createButtonsLayout());

    }

    private HorizontalLayout createFieldsLayout() {
        rentalPeriodBinder.forField(days)
                .asRequired("Required field")
                .withValidator(min -> min >= 5, "Minimum 5 days")
                .withValidator(max -> max <= 30, "Maximum 30 days")
                .bind(RentalPeriod::getDays, RentalPeriod::setDays);
        return new HorizontalLayout(days);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        rentalPeriodBinder.addStatusChangeListener(e -> save.setEnabled(rentalPeriodBinder.isValid()));
        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {
        try {
            rentalPeriodBinder.writeBean(rentalPeriod);
            fireEvent(new saveEvent(this, rentalPeriod));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setRentalPeriod(RentalPeriod rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
        rentalPeriodBinder.readBean(rentalPeriod);
    }

    public static abstract class RentalPeriodFormEvent extends ComponentEvent<RentalPeriodForm> {
        private final RentalPeriod rentalPeriod;

        protected RentalPeriodFormEvent(RentalPeriodForm source, RentalPeriod rentalPeriod){
            super(source, false);
            this.rentalPeriod = rentalPeriod;
        }

        public RentalPeriod getRentalPeriod() {
            return rentalPeriod;
        }
    }

    public static class saveEvent extends RentalPeriodFormEvent {
        saveEvent(RentalPeriodForm source, RentalPeriod rentalPeriod) {
            super(source, rentalPeriod);
        }
    }

    public static class closeEvent extends RentalPeriodFormEvent {
        closeEvent(RentalPeriodForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
