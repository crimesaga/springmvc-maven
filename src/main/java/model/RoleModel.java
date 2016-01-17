package model;

import entities.*;
import java.util.*;

public class RoleModel {

	public List<Role> findAll() {
		List<Role> lr = new ArrayList<Role>();
		lr.add(new Role("r1", "Role 1"));
		lr.add(new Role("r2", "Role 2"));
		lr.add(new Role("r3", "Role 3"));
		lr.add(new Role("r4", "Role 4"));
		lr.add(new Role("r5", "Role 5"));
		return lr;
	}

	public Role find(String id) {
		for (Role r : findAll())
			if (r.getId().equalsIgnoreCase(id))
				return r;
		return null;
	}

}
