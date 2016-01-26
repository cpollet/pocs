package net.cpollet.rest.api.attributes;

/**
 * @author Christophe Pollet
 */
@Deprecated
public class CustomAttribute implements Attribute {
    private final String table;
    private final String column;
    private final String foreignTable;
    private final String foreignColumn;

    @Deprecated
    public CustomAttribute(String table, String column) {
        this.table = table;
        this.column = column;
        this.foreignTable = null;
        this.foreignColumn = null;
    }

    @Deprecated
    public CustomAttribute(String table, String column, String foreignTable, String foreignColumn) {
        this.table = table;
        this.column = column;
        this.foreignTable = foreignTable;
        this.foreignColumn = foreignColumn;
    }

    public String toKey() {
        return table + "." + column;
    }

    @Override
    public String toQuery() {
        String foreignRef = null;

        if (foreignTable != null && foreignColumn != null) {
            foreignRef = foreignTable + "." + foreignColumn;
        }

        return "attr(" + table + "." + column + (foreignRef != null ? ":" + foreignRef : "") + ")";
    }
}
