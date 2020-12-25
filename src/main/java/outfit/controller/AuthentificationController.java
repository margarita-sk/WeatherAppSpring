package outfit.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authorization")
public class AuthentificationController {

	@GetMapping("/logout")
	public String logOut(SecurityContextHolder sch, HttpServletRequest request) throws ServletException {
		request.logout();
		return "index";
	}
}
