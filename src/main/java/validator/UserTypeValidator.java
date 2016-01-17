package validator;

import inf.UserTypeService;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ent.UserType;

public class UserTypeValidator implements Validator {
    UserTypeService m_userTypeService;
    public UserTypeValidator(UserTypeService userTypeService) {
        m_userTypeService = userTypeService;
    }
    
    @Override
    public boolean supports(Class<?> arg0) {
        return UserType.class.equals(arg0);
    }

    @Override
    public void validate(Object arg0, Errors arg1) {
        UserType userType = (UserType) arg0;
        if (m_userTypeService.findByAttribute("name", userType.getName()) != null) {
        //if (userType.getName().equalsIgnoreCase(m_userTypeService.findByAttribute("name", userType.getName()).getName()));
            arg1.rejectValue("name", "usertype.name.exists");
        }
        
    }

}
