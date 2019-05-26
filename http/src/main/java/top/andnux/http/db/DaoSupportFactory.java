package top.andnux.http.db;

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    private DaoSupportFactory() {
    }

    public static DaoSupportFactory getFactory() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupport<T>();
        daoSupport.init(clazz);
        return daoSupport;
    }
}
