package net.cpollet.pocs.read.service.attributes;

/**
 * @author Christophe Pollet
 */
public class ColumnAttribute extends BaseAttribute {
    private final String tableName;
    private final String columnName;

    public ColumnAttribute(String attributeName, String tableName, String columnName) {
        super(attributeName);
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTableName() {
        return tableName;
    }
}
