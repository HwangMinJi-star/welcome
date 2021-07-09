package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@GetMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("name", "홍길동");
		//hong이 주석달았습니다.
		return "hello"; //=> templates/hello.html 
	}
	
	@GetMapping("/apiTest1")
	@ResponseBody
	public String api_string(@RequestParam String name) {
		return "hello! "+name;
		
		//http://localhost:9091/apiTest1?name=hong
		//=> 결과 : hello! hong
	}
	
	@GetMapping("/apiTest2")
	@ResponseBody
	public Person api_object(@RequestParam String name, @RequestParam int age) {
		Person p=new Person();
		p.setName(name);
		p.setAge(age);
		
		return p;
		
		//http://localhost:9091/apiTest2?name=hong&age=20
		//=> 결과 - json : {"name":"hong","age":20}
		
	}
}//

class Person {
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
