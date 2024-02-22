package com.ms.loginapp.loginapp.demo;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {
	
	@PostMapping(value = "demo")
	public ResponseEntity<String> register(){
		
		return new ResponseEntity<String>("Succesful demo",HttpStatusCode.valueOf(200));
	}

}
