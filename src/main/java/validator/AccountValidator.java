package validator;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import entities.*;

public class AccountValidator implements Validator {

	public boolean supports(Class<?> arg0) {
		return Account.class.equals(arg0);
	}
	public void validate(Object arg0, Errors arg1) {
		Account acc = (Account) arg0;
		if (acc.getUsername().equalsIgnoreCase("abc"))
			arg1.rejectValue("username", "account.username.exists");
		
	}

}
