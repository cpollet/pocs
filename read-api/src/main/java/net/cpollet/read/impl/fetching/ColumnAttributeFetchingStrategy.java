package net.cpollet.read.impl.fetching;

import net.cpollet.read.impl.attributes.ColumnAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class ColumnAttributeFetchingStrategy extends BaseFetchingStrategy<ColumnAttribute> {
    private static final Map<String, String> localTable = new HashMap<String, String>() {{
        put("PERSONS.FIRSTNAME", "Christophe");
        put("PERSONS.LASTNAME", "Pollet");
    }};

    @Override
    public Map<ColumnAttribute, String> fetch() {
        Map<ColumnAttribute, String> result = new HashMap<>();

        for (ColumnAttribute attribute : getAttributesToFetch()) {
            result.put(attribute, localTable.get(getColumnFQN(attribute)));
        }

        return result;
    }

    private String getColumnFQN(ColumnAttribute attribute) {
        return attribute.getTableName() + "." + attribute.getColumnName();
    }
}
