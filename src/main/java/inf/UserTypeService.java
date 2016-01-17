package inf;

import java.util.List;

import ent.UserType;

public interface UserTypeService {
    public void addUserType(UserType userType);

    List<UserType> getAllUserTypes();
    public UserType findByAttribute(String name, Object value);
    public void makePersistent(List<UserType> userTypes);
}
