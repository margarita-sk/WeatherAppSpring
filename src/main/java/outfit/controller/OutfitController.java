package outfit.controller;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.apache.log4j.Logger;

import city.entity.City;
import city.service.CityService;
import outfit.dto.OutfitWithWeatherFacade;
import outfit.entity.Outfit;
import outfit.service.OutfitService;
import util.OutfitValidator;
import weather.entity.Weather;
import weather.service.WeatherService;

@Controller
@RequestMapping()
public class OutfitController {
	private static final Logger log = Logger.getLogger(OutfitController.class);

	private WeatherService weatherService;
	private CityService cityService;
	private OutfitService outfitService;
	private OutfitValidator validator;

	public OutfitController(WeatherService weatherService, CityService cityService, OutfitService outfitService,
			OutfitValidator validator) {
		this.weatherService = weatherService;
		this.outfitService = outfitService;
		this.cityService = cityService;
		this.validator = validator;
	}

	@GetMapping(value = { "/index", "/" })
	public String home() {
		return "index";
	}

	@GetMapping("/outfit/advice")
	public String showOutfitAdvice(@RequestParam(value = "city", required = false) String citySearchedName,
			Model model) {
		try {
			City city = cityService.recieveCity(citySearchedName);

			Weather weather = weatherService.recieveWeather(city.getLatitude(), city.getLongitude());
			Outfit outfit = outfitService.recieveOutfitByWeather(weather);
			OutfitWithWeatherFacade facade = outfitService.buildFacade(city, weather, outfit);

			model.addAttribute("city", facade.getCityName());
			model.addAttribute("img", facade.getIconUrl());
			model.addAttribute("temperature", facade.getTemperature());
			model.addAttribute("condition", facade.getCondition());
			model.addAttribute("outfit", facade.getOutfitName());
			model.addAttribute("accessories", facade.getAccessories());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			log.error(e);
		}
		return "outfit/advice";
	}

	@GetMapping("/outfit/list")
	public String showOutfitsList(Model model, RedirectAttributes redirectAttributes) {
		try {
			Collection<Outfit> outfits = outfitService.recieveAll();
			model.addAttribute("outfits", outfits);
			model.addAttribute("error", redirectAttributes);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			log.error(e);
		}
		return "outfit/list";
	}

	@GetMapping("/outfit/{id}")
	public String showOutfitById(@PathVariable("id") int id, Model model) {
		try {
			model.addAttribute("outfit", outfitService.recieveOufitById(id));
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			log.error(e);
		}
		return "outfit/show";
	}

	@DeleteMapping("/outfit/{id}")
	public String deleteOutfitById(@PathVariable("id") int id, @ModelAttribute("error") String error, Model model) {
		try {
			outfitService.deleteOutfit(id);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			log.error(e);
		}
		return "redirect:list";
	}

	@PostMapping("/outfit/{id}")
	public String editOutfit(@ModelAttribute("outfit") Outfit outfit, BindingResult result,
			@ModelAttribute("error") String error, Model model) {
		validator.validate(outfit, result);
		if (result.hasErrors()) {
			model.addAttribute("error", result.getFieldError().getDefaultMessage());
			return "redirect:{id}";
		}
		try {
			outfitService.editOutfit(outfit);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			log.error(e);
		}
		return "redirect:list";
	}

	@PostMapping("/outfit/add")
	public String addOutfit(@ModelAttribute("outfit") Outfit outfit, BindingResult result, Model model) {
		validator.validate(outfit, result);
		if (result.hasErrors()) {
			model.addAttribute("error", result.getFieldError().getDefaultMessage());
			return "outfit/add";
		}
		try {
			outfitService.addOutfit(outfit);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			log.error(e);
		}
		return "redirect:list";
	}

	@ModelAttribute("outfit")
	public Outfit createOutfitmodel() {
		return new Outfit();
	}

	@GetMapping("/outfit/add")
	public String showAddOutfitForm() {
		return "outfit/add";
	}

}
