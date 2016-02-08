package net.cpollet.pocs.tests.support.base;

import net.cpollet.pocs.tests.support.dbunit.SpringDatabaseDataSourceConnection;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * @author Christophe Pollet
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public abstract class BaseHibernateIntegrationTest extends BaseIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseHibernateIntegrationTest.class);

    private final static TransactionDefinition COMMIT = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    private final static TransactionDefinition NO_COMMIT = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);

    @Autowired
    private HibernateTransactionManager transactionManager;

    @Before
    public void prepareDatabase() {
        TransactionTemplate transactionTemplate = prepareTransactionTemplate(needCommittedData());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                LOG.info("Loading data from " + data());
                try {
                    IDatabaseConnection dbUnitConnection = new SpringDatabaseDataSourceConnection(transactionManager.getDataSource(), "SYSTEM");

                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    IDataSet dataSet = new FlatXmlDataSetBuilder().build(classLoader.getResourceAsStream(data()));
                    DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
                }
                catch (SQLException | DatabaseUnitException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private TransactionTemplate prepareTransactionTemplate(boolean mustCommitData) {
        return new TransactionTemplate(transactionManager, needCommittedData() ? COMMIT : NO_COMMIT);
    }


    protected abstract String data();

    protected boolean needCommittedData() {
        return false;
    }

    protected HibernateTransactionManager getTransactionManager() {
        return transactionManager;
    }

    protected Session getSession() {
        return transactionManager.getSessionFactory().getCurrentSession();
    }
}
