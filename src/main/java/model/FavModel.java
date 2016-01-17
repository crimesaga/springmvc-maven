package model;

import entities.*;
import java.util.*;

public class FavModel {

	public List<Fav> findAll() {
		List<Fav> lf = new ArrayList<Fav>();
		lf.add(new Fav("f1", "Fav 1"));
		lf.add(new Fav("f2", "Fav 2"));
		lf.add(new Fav("f3", "Fav 3"));
		lf.add(new Fav("f4", "Fav 4"));
		lf.add(new Fav("f5", "Fav 5"));
		return lf;
	}

}
