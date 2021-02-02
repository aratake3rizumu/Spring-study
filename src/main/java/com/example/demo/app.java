package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class app {
	@RequestMapping(value="/app", method=RequestMethod.GET)
	public ModelAndView appGet(ModelAndView mv){
	mv.addObject("total", false);
	mv.setViewName("app");
	return mv;
	}
	@RequestMapping(value="/app/total", method=RequestMethod.POST)
	public ModelAndView totalPost(
	@RequestParam("fish")String fish, 
	@RequestParam("shrimp")String shrimp, 
	@RequestParam("katsu")String katsu,
	@RequestParam("beer")String beer,
	ModelAndView mv){
//	System.out.println(fish);
//	System.out.println(katsu);
//	System.out.println(shrimp);
//	System.out.println(beer);
		int total = 0;
		if(fish !="") {
			mv.addObject("fish", "・" + "焼き魚定食");
			total += 700;
		} 
		
		if(shrimp !="") {
			mv.addObject("shrimp",  "・" + "エビフライ定食");
			total += 800;
		} 
		
		if(katsu !="") {
			mv.addObject("katsu",  "・" + "味噌カツ定食");
			total += 900;
		} 
		
		if(beer !="") {
			mv.addObject("beer",  "・" + "ビール");
			total += 500;
		} 
	mv.addObject("total", "・" +  total + "円");
	
	mv.setViewName("total");
	return mv;
	}
	@RequestMapping(value="/app/account", method=RequestMethod.POST)
	public ModelAndView AccountPost(
	@RequestParam("account")String account,ModelAndView mv){
	mv.addObject("account", "┗" + account);
	mv.setViewName("Account");
	return mv;
	}
	@RequestMapping(value="/app/pay", method=RequestMethod.POST)
	public ModelAndView payPost(ModelAndView mv){
	mv.setViewName("Pay");
	return mv;
	}
}