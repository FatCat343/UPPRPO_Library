package ru.nsu.fit.library.main.UI;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;
import ru.nsu.fit.library.book.UI.BookView;
import ru.nsu.fit.library.book.author.UI.AuthorView;
import ru.nsu.fit.library.bookPosition.UI.BookPositionView;
import ru.nsu.fit.library.distribution.UI.DistributionView;
import ru.nsu.fit.library.library.UI.LibraryView;
import ru.nsu.fit.library.queriesUI.Q_1;
import ru.nsu.fit.library.queriesUI.Q_2;
import ru.nsu.fit.library.reader.ReaderView;
import ru.nsu.fit.library.storage.UI.StorageView;

public class MainView extends AppLayout {

    public MainView() {
        MenuBar menuBar = new MenuBar();

        MenuItem books = menuBar.addItem("Books");
        MenuItem author = menuBar.addItem("Authors");
		MenuItem reader = menuBar.addItem("Readers");
        MenuItem distribution = menuBar.addItem("Distributions");
        MenuItem bookPosition = menuBar.addItem("Book position");
        MenuItem storage = menuBar.addItem("Storage");
        MenuItem library = menuBar.addItem("Library");
        MenuItem specialTasks = menuBar.addItem("Special tasks");

        books.getSubMenu().addItem(new RouterLink("Books", BookView.class));
        author.getSubMenu().addItem(new RouterLink("Authors", AuthorView.class));
		reader.getSubMenu().addItem(new RouterLink("Readers", ReaderView.class));
        distribution.getSubMenu().addItem(new RouterLink("Distributions", DistributionView.class));
        bookPosition.getSubMenu().addItem(new RouterLink("Book position", BookPositionView.class));
        storage.getSubMenu().addItem(new RouterLink("Storage", StorageView.class));
        library.getSubMenu().addItem(new RouterLink("Library", LibraryView.class));
        specialTasks.getSubMenu().addItem(new RouterLink("Get the most popular books among readers", Q_1.class));
        specialTasks.getSubMenu().addItem(new RouterLink("Get readers with expired books", Q_2.class));

        addToNavbar(menuBar);
    }
}
