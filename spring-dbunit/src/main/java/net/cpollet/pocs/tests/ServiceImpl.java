package net.cpollet.pocs.tests;

import net.cpollet.pocs.tests.mappings.Test;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

/**
 * @author Christophe Pollet
 */
public class ServiceImpl implements Service {
    private final static Logger LOG = LoggerFactory.getLogger(ServiceImpl.class);
    private final HibernateTransactionManager transactionManager;

    public ServiceImpl(HibernateTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

//        session.doWork(new Work() {
//            @Override
//            public void execute(Connection connection) throws SQLException {
//                try {
//                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//                    IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection, "SYSTEM");
//                    IDataSet dataSet = new FlatXmlDataSetBuilder().build(classLoader.getResourceAsStream("dataset.xml"));
//
//                    DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
//                    LOG.info("done");
//                }
//                catch (DatabaseUnitException e) {
//                    LOG.error(e.getMessage(), e);
//                }
//            }
//        });


    @Override
    public int getCount() {
        return getSession()
                .createCriteria(Test.class)
                .list()
                .size();
    }

    private Session getSession() {
        return transactionManager.getSessionFactory().getCurrentSession();
    }

}
