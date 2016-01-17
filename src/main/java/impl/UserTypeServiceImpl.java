package impl;

import java.util.List;

import inf.UserTypeDAO2;
import inf.UserTypeService;

import org.springframework.transaction.support.TransactionTemplate;
import org.apache.log4j.Logger;

import ent.UserType;

public class UserTypeServiceImpl implements UserTypeService {
    final static Logger logger = Logger.getLogger(UserTypeService.class);
    
    private UserTypeDAO2 m_userTypeDAO;

    private TransactionTemplate m_transactionTemplate;

    public TransactionTemplate getTransactionTemplate() {
        return m_transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        m_transactionTemplate = transactionTemplate;
    }

    public UserTypeDAO2 getUserTypeDAO() {
        return m_userTypeDAO;
    }

    public void setUserTypeDAO(UserTypeDAO2 userDAO) {
        m_userTypeDAO = userDAO;
    }

    @Override
    public void addUserType(UserType userType) {
        m_userTypeDAO.makePersistent(userType);
    }

    @Override
    public List<UserType> getAllUserTypes() {
        return m_userTypeDAO.findAll();
    }

    @Override
    public UserType findByAttribute(String attribute, Object value) {
        return m_userTypeDAO.findByAttribute(attribute, value);
    }

    @Override
    public void makePersistent(List<UserType> userTypes) {
        m_userTypeDAO.makePersistent(userTypes);

    }
}
