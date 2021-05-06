package ru.nsu.fit.library.book.UI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.gatanaso.MultiselectComboBox;
import org.vaadin.klaudeta.PaginatedGrid;
import ru.nsu.fit.library.book.Book;
import ru.nsu.fit.library.book.BookService;
import ru.nsu.fit.library.book.author.Author;
import ru.nsu.fit.library.book.author.AuthorService;
import ru.nsu.fit.library.main.UI.MainView;

import java.util.List;

@Route(value = "books", layout = MainView.class)
public class BookView extends VerticalLayout {
    private BookService bookService;
    private PaginatedGrid<Book> grid = new PaginatedGrid<>(Book.class);

    DataProvider<Book, Void> dataProvider;

    private BookForm form;

    public BookView(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        addClassName("book-view");
        setSizeFull();
        dataProvider = createDataProvider(bookService);
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new BookForm(authorService, bookService);
        form.addListener(BookForm.saveEvent.class, this::saveBook);
        form.addListener(BookForm.deleteEvent.class, this::deleteBook);
        form.addListener(BookForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        Button addBookButton = new Button("Add Book");
        addBookButton.addClickListener(click -> addBook());

        return new HorizontalLayout(addBookButton);
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
        grid.setPageSize(10);
        grid.setDataProvider(dataProvider);

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
        dataProvider.refreshItem(book);
        closeEditor();
    }

    private void deleteBook(BookForm.deleteEvent event) {
        bookService.delete(event.getBook());
        dataProvider.refreshAll();
        closeEditor();
    }


    public void editBook(Book book) {
        if (book == null) closeEditor();
        else {
            form.setBook(book);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private DataProvider<Book, Void> createDataProvider(BookService bookService) {
        DataProvider<Book, Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    int page = offset/limit;
                    List<Book> books = getBookService().fetch(page, limit);
                    return books.stream();
                },

                query -> {
                    return (int) getBookService().getCount(query.getLimit());
                }
        );
        return dataProvider;
    }

    private BookService getBookService() {
        return bookService;
    }
}

