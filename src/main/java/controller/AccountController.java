package controller;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import validator.*;
import conversion.*;
import entities.*;
import model.*;

import java.util.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "account")
public class AccountController {

	@InitBinder
	public void InitBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Date.class, new DateEditor());
		webDataBinder.registerCustomEditor(Role.class, new RoleEditor());
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(ModelMap modelMap) {
		FavModel fm = new FavModel();
		RoleModel rm = new RoleModel();
		modelMap.put("acc", new Account());
		modelMap.put("lf", fm.findAll());
		modelMap.put("lr", rm.findAll());
		return "register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(@ModelAttribute(value = "acc") @Valid Account acc,
			BindingResult bindingResult, ModelMap modelMap) {
		AccountValidator av = new AccountValidator();
		av.validate(acc, bindingResult);
		if (bindingResult.hasErrors()) {
			FavModel fm = new FavModel();
			RoleModel rm = new RoleModel();
			modelMap.put("lf", fm.findAll());
			modelMap.put("lr", rm.findAll());
			return "register";
		} else {
			modelMap.put("acc", acc);
			return "success";
		}
	}

}
