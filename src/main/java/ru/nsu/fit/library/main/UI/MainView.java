package ru.nsu.fit.library.main.UI;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;
import ru.nsu.fit.library.book.UI.BookView;
import ru.nsu.fit.library.book.author.UI.AuthorView;

public class MainView extends AppLayout {
    private MenuBar menuBar = new MenuBar();

    public MainView() {
        MenuItem books = menuBar.addItem("Books");
        MenuItem author = menuBar.addItem("Authors");

        books.getSubMenu().addItem(new RouterLink("Books", BookView.class));
        author.getSubMenu().addItem(new RouterLink("Authors", AuthorView.class));

        addToNavbar(menuBar);
    }
}
