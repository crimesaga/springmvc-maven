package conversion;

import java.beans.*;
import java.text.*;

public class DateEditor extends PropertyEditorSupport{

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.setValue(sdf.parse(arg0));
		} catch (Exception e) {
			this.setValue(null);
		}
	}

	
	
}
