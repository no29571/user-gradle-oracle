package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.LocalUser;
import com.example.service.LocalUserService;
import com.example.validation.LocalUserForm;

@Controller
public class LocalUserController {
	@Autowired
	private  LocalUserService userService;
	
	//一覧
	@GetMapping("/listUser")
	public String listUser(Model model) {
		List<LocalUser> list = userService.findAll();
		model.addAttribute("userList", list);
		return "/user/list";
	}

	//searchフォームsubmit時
	@PostMapping("/searchUser")
	public String search(@RequestParam("keyword") String keyword, Model model) {
		List<LocalUser> list = userService.findUser(keyword);
		model.addAttribute("userList", list);
		model.addAttribute("keyword", keyword);
		return "/user/list";
	}
	
	//createリンク押下時
	@GetMapping("/createUser")
	public String createUser(Model model) {
		LocalUserForm form = new LocalUserForm();
		form.setEnabled(true);
		model.addAttribute("localUserForm", form);
		return "/user/create";
	}
	
	//createフォームsubmit時
	@PostMapping("/createUser")
	public String createUser(@Validated LocalUserForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/user/create";
		}
		userService.insert(form);
		return "redirect:/listUser";
	}
	
	//edit(& delete)リンク押下時
	@GetMapping("/editUser/{id}")
	public String editUser(@PathVariable(value = "id") int id, Model model) {
		//LocalUser user = userService.findById(id).orElse(new LocalUser());
		Optional<LocalUser> opt = userService.findById(id);
		if (!opt.isPresent()) {
			return "redirect:/createUser";
		}
		LocalUser user = opt.get();
		LocalUserForm form = new LocalUserForm();
		BeanUtils.copyProperties(user, form);
		model.addAttribute("localUserForm", form);
		return "/user/edit";
	}
	
	//editフォームsubmit時
	@PostMapping("/editUser")
	public String editUser(@Validated LocalUserForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/user/edit";
		}
		try {
			userService.update(form);
		//} catch (org.hibernate.StaleObjectStateException ex) {
		} catch (ObjectOptimisticLockingFailureException ex) {//@Versionによる排他制御
			result.rejectValue("ver", "com.example.controller.LocalUserController.editUser.ObjectOptimisticLockingFailureException");//messages.properties
			return "/user/edit";
		}
		return "redirect:/listUser";
	}
	
	//作成者・更新者の参照用に、削除せず有効・無効で管理に変更
	//deleteフォームsubmit時
	/*@PostMapping("/deleteUser")
	public String deleteUser(@ModelAttribute("localUserForm") LocalUserForm form) {
		userService.delete(form.getId());
		return "redirect:/listUser";
	}*/
}
