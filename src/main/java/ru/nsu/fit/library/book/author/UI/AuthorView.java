package ru.nsu.fit.library.book.author.UI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.nsu.fit.library.book.author.Author;
import ru.nsu.fit.library.book.author.AuthorService;
import ru.nsu.fit.library.main.UI.MainView;

@Route(value = "author", layout = MainView.class)
public class AuthorView extends VerticalLayout {
    private AuthorService authorService;
    private Grid<Author> grid = new Grid<>(Author.class);

    private AuthorForm form;

    public AuthorView(AuthorService authorService) {
        this.authorService = authorService;

        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new AuthorForm(authorService);
        form.addListener(AuthorForm.saveEvent.class, this::saveAuthor);
        form.addListener(AuthorForm.deleteEvent.class, this::deleteAuthor);
        form.addListener(AuthorForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Author::getFirstName).setHeader("FirstName").setSortProperty("firstname");
        grid.addColumn(Author::getLastName).setHeader("LastName").setSortProperty("lastname");
        grid.asSingleSelect().addValueChangeListener(event -> editAuthor(event.getValue()));
    }

    private HorizontalLayout configureToolBar() {
        Button addAuthorButton = new Button("Add Author");
        addAuthorButton.addClickListener(click -> addAuthor());
        return new HorizontalLayout(addAuthorButton);
    }

    private void addAuthor() {
        grid.asSingleSelect().clear();
        editAuthor(new Author());
    }

    private void updateList() {
        grid.setItems(authorService.findAll());
    }

    private void saveAuthor(AuthorForm.saveEvent event) {
        Author author = authorService.save(event.getAuthor());
        updateList();
        closeEditor();
    }

    private void deleteAuthor(AuthorForm.deleteEvent event) {
        authorService.delete(event.getAuthor());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        grid.asSingleSelect().clear();
        form.setAuthor(null);
        form.setVisible(false);
    }

    private void editAuthor(Author author) {
        if (author == null) closeEditor();
        else {
            form.setAuthor(author);
            form.setVisible(true);
        }
    }
}

