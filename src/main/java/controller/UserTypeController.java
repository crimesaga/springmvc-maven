package controller;

import java.util.Arrays;
import java.util.List;

import inf.UserTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import validator.UserTypeValidator;

import ent.UserType;

@Controller
@RequestMapping(value = "usertype")
public class UserTypeController {
    @Autowired@Qualifier("userTypeService")
    private UserTypeService m_userTypeService;
//    public UserTypeService getUserTypeService() {
//        return m_userTypeService;
//    }
//    public void setUserTypeService(UserTypeService userTypeService) {
//        m_userTypeService = userTypeService;
//    }
    @RequestMapping(value = "all")
    public String getAll(ModelMap modelMap) {
        modelMap.put("usertypes", m_userTypeService.getAllUserTypes());
        return "all";
    }
    
    @RequestMapping(value = "onewithjson/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody UserType getOneWithJson(@PathVariable int id) {
        UserType userType = m_userTypeService.findByAttribute("id", id);
        return userType;
    }
    
    @RequestMapping(value = "allwithjson", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<UserType> getAllWithJson() {
        return  m_userTypeService.getAllUserTypes();
    }
    
    @RequestMapping(value = "allwithxml", method = RequestMethod.GET, produces = "application/xml")
    public @ResponseBody List<UserType> getAllWithXML() {
        return  m_userTypeService.getAllUserTypes();
    }
    
    @RequestMapping(value = "detail")
    public String getDetail(@RequestParam("id") int id, ModelMap modelMap) {
        UserType userType = m_userTypeService.findByAttribute("id", id);
        modelMap.put("usertype", userType);
        return "detail";
    }
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@ModelAttribute("usertype") UserType userType, BindingResult bindingResult, ModelMap modelMap) {
        UserTypeValidator validator = new UserTypeValidator(m_userTypeService);
        validator.validate(userType, bindingResult);
        if (userType != null && !bindingResult.hasErrors()) {
            m_userTypeService.makePersistent(Arrays.asList(userType));
            modelMap.put("usertype", userType);
            return "success1";
        }
        return "detail";
    }
    
//    @RequestMapping(value = "login", method = RequestMethod.GET)
//    public String login(ModelMap model) {
//        return "login";
//    }
// 
//    @RequestMapping(value = "accessdenied", method = RequestMethod.GET)
//    public String loginerror(ModelMap model) {
//        model.addAttribute("error", "true");
//        return "denied";
//    }
// 
//    @RequestMapping(value = "logout", method = RequestMethod.GET)
//    public String logout(ModelMap model) {
//        return "logout";
//    }
}
