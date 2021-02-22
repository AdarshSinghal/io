
package com.adarsh.io;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

//If you move this into Controller package, it will be listed in Swagger which we dont want. 
//Another approach is to configure swagger to ignore this particular controller
@Controller
public class WelcomePageRedirectConfig {

	@GetMapping("/")
	public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
		return new RedirectView("swagger-ui.html");
	}

}
