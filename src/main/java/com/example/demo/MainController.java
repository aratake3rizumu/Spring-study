package com.example.demo;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import net.bytebuddy.asm.Advice.Return;
import org.aspectj.weaver.loadtime.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Controller
public class MainController {
	@RequestMapping(value="/day22")
	public ModelAndView day22Post(ModelAndView mv){
	ArrayList<String[]> customers = new ArrayList<String[]>();
	customers.add(new String[] {"佐藤HTML太郎","35歳","男性", "東京都中央区"});
	customers.add(new String[] {"鈴木Java五郎","24歳","男性", "千葉県柏市"});
	customers.add(new String[] {"高橋CSS子","29歳","女性", "群馬県群馬市"});
	customers.add(new String[] {"群馬沙知代","45歳","女性", "群馬県群馬市"});
	customers.add(new String[] {"太田券","22歳","男性", "東京都大田区"});
	mv.addObject("customers", customers);
	mv.setViewName("day22");
	return mv;
	}
	
	@RequestMapping(value="/day21", method=RequestMethod.GET)
	public ModelAndView day21Get(ModelAndView mv){
	mv.addObject("suzuki", "no");
	mv.setViewName("day21");
	return mv;
	}
	@RequestMapping(value="/day21", method=RequestMethod.POST)
	public ModelAndView day21Post(ModelAndView mv){
	mv.addObject("suzuki", "aaa");
	mv.setViewName("day21");
	return mv;
	}
	@RequestMapping("/day21/{name}")
	public ModelAndView day21(@PathVariable String name, ModelAndView mv){
	mv.addObject("name", name);
	mv.setViewName("day21");
	return mv;
	}
	
	@RequestMapping("/sosu/{num}")
	public ModelAndView sosu(@PathVariable int num, ModelAndView mv){
	String result;
	
	if(num == 1){
		result = num + "は素数ではありません...";
		mv.addObject("result" , result);
	} else if(num == 2){
		result = num + "は素数です！";
		mv.addObject("result" , result);
	}
		
	for(int i = 2; i < num; i++) {
		if(num % i== 0) {
			result = num + "は素数ではありません...";
			mv.addObject("result" , result);
			break;
		} else {
			result = num + "は素数です！";
			mv.addObject("result" , result);
		}
	}
	mv.setViewName("sosu");
	return mv;
	}
	
	@Autowired
	UserDataRepository repository;
	
	@RequestMapping(value = "/day20" , method=RequestMethod.GET)
	public ModelAndView day20Get(ModelAndView mv) {
	mv.addObject("name", "名前がここに入ります");
	mv.addObject("age", "年齢がここに入ります");
	mv.addObject("height", "身長がここに入ります");
	mv.addObject("weight", "体重がここに入ります");
	mv.addObject("from", "出身地がここに入ります");
	mv.setViewName("day20");
	return mv;
	}
	@RequestMapping(value="/day20", method=RequestMethod.POST)
	public ModelAndView indexPost(ModelAndView mv, 
	@RequestParam("nameVal")String name,
	@RequestParam("ageVal")String age, @RequestParam("heightVal")String height, 
	@RequestParam("weightVal")int weight, @RequestParam("fromVal")String from) {
	mv.addObject("name", name);
	mv.addObject("age", age + "歳");
	mv.addObject("height", height + "cm");
	mv.addObject("weight", weight * 100 + "g");
	mv.addObject("from", from);
	mv.setViewName("day20");
	return mv;
	}
	
	
	@RequestMapping(value = "/cul" , method=RequestMethod.GET)
	public ModelAndView culGet(ModelAndView mv) {
	mv.addObject("number","階乗の計算をします。");
	mv.setViewName("cul");
	return mv;
	}
	@RequestMapping(value="/cul", method=RequestMethod.POST)
	public ModelAndView culPost(ModelAndView mv, 
	@RequestParam("numberVal")int number) {
		int answer = 0; int n = number;
		while(n >= 0) {
			answer += n;
			n--;
		}
	mv.addObject("number", number + "の階乗は" + answer + "です！");
	mv.setViewName("cul");
	return mv;
	}
	
	
	@RequestMapping(value = "/bmi" , method=RequestMethod.GET)
	public ModelAndView bmiGet(ModelAndView mv) {
	mv.addObject("bmi","BMIの計算をします。");
	mv.setViewName("bmi");
	return mv;
	}
	@RequestMapping(value="/bmi", method=RequestMethod.POST)
	public ModelAndView bmiPost(ModelAndView mv, 
	@RequestParam("heightVal")double height, 
	@RequestParam("weightVal")double weight) {
	height = height * 0.01;
	double bmi = weight / (height * height);
	mv.addObject("bmi", "BMI" + "は" + bmi + "です！");
	String result;
	if (bmi > 25) {
		result = "肥満です";
	} else if (bmi >= 18.5) {
		result = "標準です";
	} else {
		result = "低体重です";
	}
	mv.addObject("result" , result);
	mv.setViewName("bmi");
	return mv;
	}
	

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView indexGet(ModelAndView mv){
		List<UserData> customers = repository.findAll();
		mv.addObject("customers", customers);
		mv.setViewName("index");
		return mv;
	}
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ModelAndView indexPost(@ModelAttribute("formModel") UserData userData, ModelAndView mv){
		repository.saveAndFlush(userData);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/mypage/{id}", method = RequestMethod.GET)
	public ModelAndView mypageGet(@ModelAttribute UserData userData, ModelAndView mv,
	@PathVariable long id){
	Optional<UserData> user = repository.findById(id);
	mv.addObject("userData", user.get());
	mv.setViewName("mypage");
	return mv;
	}
	@RequestMapping(value="/mypage/", method = RequestMethod.POST)
	public ModelAndView mypagePost(@ModelAttribute UserData userData, ModelAndView mv){
	repository.saveAndFlush(userData);
	return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/delete/", method =
	RequestMethod.POST)
	public ModelAndView delete(@RequestParam("id")
	long id, ModelAndView mv){
	repository.deleteById(id);
	return new ModelAndView("redirect:/");
	}
}
