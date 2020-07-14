package com.example.demo.controller;


import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class FileUploadController {
	
	@RequestMapping("/fileupload")
	public String fileupload(MultipartFile file,HttpServletRequest req) throws Exception{
		
		 
		if(null!=file) {
			
			System.out.println(file.getOriginalFilename());
			file.transferTo(new File("d:/"+file.getOriginalFilename()));
		}
		MultipartFile f=((MultipartHttpServletRequest)req).getFile("file");
		if(null!=f) {
			System.out.println("ffffff...."+f.getOriginalFilename());
			
		}
		
		return "sucess";
	}

}
