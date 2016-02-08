package net.cpollet.pocs.tests.support.dbunit;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author Christophe Pollet
 */
public class SpringDatabaseDataSourceConnection extends DatabaseDataSourceConnection {
    private final DataSource dataSource;

    public SpringDatabaseDataSourceConnection(DataSource dataSource, String schema) throws SQLException {
        super(dataSource, schema);
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        return new NoCommitConnection(dataSource, conn);
    }
}
