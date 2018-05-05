package com.vzhilin.dbview.db.mean.exp;

import com.vzhilin.dbview.db.data.Row;
import com.vzhilin.dbview.db.schema.Table;

public final class ParsedTemplate {
    private final boolean valid;
    private final Table table;
    private final String line;
    private final Expression expression;
    private final String errorMessage;

    public ParsedTemplate(Table table, String line, Expression expression) {
        this.table = table;
        this.line = line;
        this.expression = expression;
        this.errorMessage = "";
        this.valid = true;
    }

    public ParsedTemplate(Table table, String line, String errorMessage) {
        this.table = table;
        this.line = line;
        this.errorMessage = errorMessage;
        this.expression = null;
        this.valid = false;
    }

    public ExpressionValue render(Row row) {
        if (expression != null) {
            return expression.render(row);
        } else {
            return new ExpressionValue(errorMessage);
        }
    }

    public String getError() {
        return errorMessage;
    }

    public boolean isValid() {
        return valid;
    }
}