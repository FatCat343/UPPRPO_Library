package ru.nsu.fit.library.book.UI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;
import ru.nsu.fit.library.book.Book;
import ru.nsu.fit.library.book.BookService;
import ru.nsu.fit.library.book.author.Author;
import ru.nsu.fit.library.book.author.AuthorService;
import ru.nsu.fit.library.bookPosition.BookPositionService;
import ru.nsu.fit.library.main.UI.MainView;
import java.util.List;

@Route(value = "books", layout = MainView.class)
public class BookView extends VerticalLayout {
    private final TextField filterText = new TextField();
    private final Grid<Book> grid = new Grid<>(Book.class);

    private final BookService bookService;

    private final BookForm form;

    public BookView(BookService bookService, AuthorService authorService, BookPositionService bookPositionService) {
        this.bookService = bookService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new BookForm(authorService, bookService,bookPositionService);
        form.addListener(BookForm.saveEvent.class, this::saveBook);
        form.addListener(BookForm.deleteEvent.class, this::deleteBook);
        form.addListener(BookForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        listBooks();
        closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> listBooks());

        Button addBookButton = new Button("Add Book");
        addBookButton.addClickListener(click -> addBook());

        return new HorizontalLayout(filterText, addBookButton);
    }

    private void listBooks() {
        if ((filterText.getValue().equals("")) || (filterText.getValue() == null)) {
            grid.setItems(bookService.findAll());
        }
        else {
            grid.setItems(bookService.findAll(filterText.getValue()));
        }
    }

    void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
    }

    private void configureGrid(){
        grid.addClassName("student-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Book::getTitle).setHeader("Title").setSortProperty("title");
        grid.addColumn(book -> {
            Author author = book.getAuthor();
            return author.toString();
        }).setHeader("Author").setSortProperty("author");
        grid.addColumn(Book::getBookLocation).setHeader("Location").setSortProperty("location");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setPageSize(10);
        grid.asSingleSelect().addValueChangeListener(event -> editBook(event.getValue()));
    }

    private void closeEditor() {
        grid.asSingleSelect().clear();
        form.setBook(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveBook(BookForm.saveEvent event) {
        Book book = bookService.save(event.getBook());
        listBooks();
        closeEditor();
    }

    private void deleteBook(BookForm.deleteEvent event) {
        bookService.delete(event.getBook());
        listBooks();
        closeEditor();
    }

    public void editBook(Book book) {
        if (book == null) closeEditor();
        else {
            form.setBook(book);
            form.setVisible(true);
        }
    }
}

