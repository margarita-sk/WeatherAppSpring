package outfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthentificationController {

  @GetMapping(value = {"/index", "/"})
  public String home() {
    return "index";
  }

  @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
  public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout) {

    var model = new ModelAndView();
    if (error != null) {
      model.addObject("error", "Invalid Credentials provided.");
    }

    if (logout != null) {
      model.addObject("message", "Logged out successfully.");
    }

    model.setViewName("loginPage");
    return model;
  }

}
