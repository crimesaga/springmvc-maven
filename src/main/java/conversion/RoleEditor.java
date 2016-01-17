package conversion;

import java.beans.*;
import model.*;

public class RoleEditor extends PropertyEditorSupport{

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		try {
			RoleModel rm = new RoleModel();
			this.setValue(rm.find(arg0));
		} catch (Exception e) {
			this.setValue(null);
		}
	}

	
	
}
