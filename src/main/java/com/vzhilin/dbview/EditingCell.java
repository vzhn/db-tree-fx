package com.vzhilin.dbview;

import com.vzhilin.dbview.autocomplete.AutoCompletion;
import com.vzhilin.dbview.db.DbContext;
import com.vzhilin.dbview.db.QueryContext;
import com.vzhilin.dbview.db.data.Row;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;

public class EditingCell extends TreeTableCell<TreeTableNode, Row> {
    private TextField meaningTextField;

    public EditingCell() {
        itemProperty().addListener((observable, oldValue, newValue) -> setEditable(newValue != null));
    }

    @Override
    public void startEdit() {
        super.startEdit();
        Row row = getItem();
        if (row == null) {
            setText("");
            setGraphic(null);
            return;
        }
        QueryContext ctx = row.getContext();
        DbContext dbContext = ctx.getDbContext();

        if (meaningTextField == null) {
            meaningTextField = new TextField();
            meaningTextField.setOnAction(event -> cancelEdit());
            meaningTextField.setText(ctx.getTemplate(row.getTable().getName()));
            AutocompleteController.DbSuggestionProvider provider
                = new AutocompleteController.DbSuggestionProvider(dbContext.getSchema(), row.getTable());
            new AutoCompletion(provider).bind(meaningTextField);
        }

        setGraphic(meaningTextField);
        setText("");
        meaningTextField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        Row row = getItem();
        if (row != null) {
            row.getContext().setTemplate(row.getTable().getName(), meaningTextField.getText());

            setGraphic(null);
            setText(row.meaningfulValue());
        }
    }

    @Override
    public void updateItem(Row item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            if (item != null) {
                setText(item.meaningfulValue());
            }

            setGraphic(null);
        }
    }
}