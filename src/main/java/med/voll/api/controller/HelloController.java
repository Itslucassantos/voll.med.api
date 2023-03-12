package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
	//Get usamos para leitura, para receber dados da API, quando queremos enviar dados para API, 
	//usamos POST
	@GetMapping
	public String olaMundo() {
		return "Hello World!";
	}

}
