package net.cpollet.pocs.tests;

import net.cpollet.pocs.tests.support.base.BaseHibernateIntegrationTest;
import net.cpollet.pocs.tests.support.dbunit.Dataset;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Christophe Pollet
 */
@ContextConfiguration("classpath:/spring/app-context.xml")
@Dataset(value = "dbunit/dataset.xml", commit = true)
public class TestCommitTransaction extends BaseHibernateIntegrationTest {
    @Autowired
    private Service service;

    @Test
    public void testWithCommit1() {
        // GIVEN
        // in setUp

        // WHEN
        int count = service.getCount();

        // THEN
        Assert.assertThat(count, CoreMatchers.equalTo(1));
    }

    @Test
    public void testWithCommit2() {
        // GIVEN
        // in setUp

        // WHEN
        int count = service.getCount();

        // THEN
        Assert.assertThat(count, CoreMatchers.equalTo(1));
    }

    @Test
    public void dataIsCommitted() {
        // GIVEN
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        TransactionTemplate transactionTemplate = new TransactionTemplate(getTransactionManager(), transactionDefinition);

        // WHEN
        Integer count = transactionTemplate.execute(status -> service.getCount());

        // THEN
        Assert.assertThat(count, CoreMatchers.equalTo(1));
    }
}
