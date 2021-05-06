package ru.nsu.fit.library.main.UI;

import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
public class DefaultView extends VerticalLayout {

    private final Header header = new Header();
    public DefaultView() {
        header.setText("Library");
    }
}
