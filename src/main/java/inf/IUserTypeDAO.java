package inf;

import ent.UserType;

public interface IUserTypeDAO {
    public boolean insert(String name);
    public boolean update(int id);
    public boolean delete(int id);
    public UserType[] load();
    public boolean insert(UserType[] insert);
    public boolean update(UserType[] update);
    public boolean delete(int[] delete);
    public UserType findID(int id);
    public UserType[] findID1(int id);
    public UserType[] findName(String name);
    public boolean update(UserType afAu);
    public int getMaxID();
}
