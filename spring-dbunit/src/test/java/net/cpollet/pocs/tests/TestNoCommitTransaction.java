package net.cpollet.pocs.tests;

import net.cpollet.pocs.tests.support.base.BaseHibernateIntegrationTest;
import net.cpollet.pocs.tests.support.database.DataSet;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Christophe Pollet
 */
@ContextConfiguration("classpath:/spring/app-context.xml")
@DataSet("dbunit/dataset.xml")
public class TestNoCommitTransaction extends BaseHibernateIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(TestNoCommitTransaction.class);

    @Autowired
    private Service service;

    @Before
    public void prepareDatabase() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionTemplate transactionTemplate = new TransactionTemplate(getTransactionManager(), transactionDefinition);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                LOG.info("Cleaning DB and committing");
                getSession().createSQLQuery("DELETE FROM TEST").executeUpdate();
            }
        });

        super.prepareDatabase();
    }

    @Test
    public void testWithoutCommit1() {
        // GIVEN
        // in setUp

        // WHEN
        int count = service.getCount();

        // THEN
        Assert.assertThat(count, CoreMatchers.equalTo(1));
    }

    @Test
    public void testWithoutCommit2() {
        // GIVEN
        // in setUp

        // WHEN
        int count = service.getCount();

        // THEN
        Assert.assertThat(count, CoreMatchers.equalTo(1));
    }

    @Test
    public void dataIsNotCommitted() {
        // GIVEN
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        TransactionTemplate transactionTemplate = new TransactionTemplate(getTransactionManager(), transactionDefinition);

        // WHEN
        Integer count = transactionTemplate.execute(status -> service.getCount());

        // THEN
        Assert.assertThat(count, CoreMatchers.equalTo(0));
    }
}
