package com.sniservices.rbs.fileuploadexample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@RestController
public class FileUploadController {

	public static final Logger logger = Logger.getLogger(FileUploadController.class.getName());

	@RequestMapping("/hello")
	public String sayHello(@RequestParam(value="name") String name)  {
		System.out.println("hello shaine");
		return "Hello " + name + "!";
	}
	@PostMapping("/upload")
	public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file ,
											@RequestParam(value = "delimiter", required = false, defaultValue = ",") String delimiter,
											@RequestParam(value = "charset", required = false, defaultValue = "UTF-8") String charset
		) throws Exception {
		if (file == null){
			throw new RuntimeException("You must provide a file for uploading");
		}
		InputStream inputStream = file.getInputStream();
		String originalName = file.getOriginalFilename();
		String name = file.getName();
		String contentType = file.getContentType();
		long size = file.getSize();

		logger.info("inputStream: " + inputStream);
		logger.info("originalName: " + originalName);
		logger.info("name: " + name);
		logger.info("contentType: " + contentType);
		logger.info("size: " + size);
		logger.info("charset" + charset);

		java.util.Scanner scanner = new java.util.Scanner(inputStream, charset).useDelimiter(delimiter);

		while (scanner.hasNext()){
			String record = scanner.next();
			//logger.info(record);
		}

//		String record = scanner.hasNext()? scanner.next():"";
//		System.out.println(record);
		scanner.close();

		return new ResponseEntity<String>(originalName, HttpStatus.OK);
	}
}
