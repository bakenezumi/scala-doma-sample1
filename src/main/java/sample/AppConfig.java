package sample;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.tx.*;
import org.seasar.doma.SingletonConfig;
import org.seasar.doma.jdbc.dialect.*;


@SingletonConfig
public class AppConfig implements Config {

    private static final AppConfig CONFIG = new AppConfig();

    private final Dialect dialect;

    private final LocalTransactionDataSource dataSource;

    private final TransactionManager transactionManager;

    private AppConfig() {
        dialect = new H2Dialect();
        dataSource = new LocalTransactionDataSource(
                "jdbc:h2:mem:tutorial;DB_CLOSE_DELAY=-1", "sa", null);
        transactionManager = new LocalTransactionManager(
                dataSource.getLocalTransaction(getJdbcLogger()));
        try {
            Class.forName("org.h2.Driver");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static AppConfig singleton() {
        return CONFIG;
    }
}