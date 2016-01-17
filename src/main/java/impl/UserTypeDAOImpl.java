package impl;

import inf.UserTypeDAO2;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import ent.UserType;

public class UserTypeDAOImpl extends GenericHibernateDAO<UserType, Long> implements UserTypeDAO2 {
    public UserTypeDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private TransactionTemplate m_transactionTemplate;

    public TransactionTemplate getTransactionTemplate() {
        return m_transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        m_transactionTemplate = transactionTemplate;
    }
//
//    @Override
//    public Integer addUserType(UserType userType) {
//        return (Integer) getSession().save(userType);
//    }
//
//    @Override
//    public void update(UserType userType) {
//        getSession().update(userType);
//    }
}
