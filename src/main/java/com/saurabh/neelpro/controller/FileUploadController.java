package com.saurabh.neelpro.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

import com.saurabh.neelpro.entity.FileDetails;
import com.saurabh.neelpro.repository.FileDetailRepository;
import com.saurabh.neelpro.service.FileDetailsService;
import com.saurabh.neelpro.storage.StorageFileNotFoundException;
import com.saurabh.neelpro.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@Autowired
	private FileDetailRepository fileDetailRepository;

	@Autowired
	private FileDetailsService fileDetailsService;

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile",
						path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList()));
		return "index";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
				+ file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded "
				+ file.getOriginalFilename() + "!");
		FileDetails fileDetails = new FileDetails();
		fileDetails.setNameOfPerson(file.getOriginalFilename());
		fileDetails.setLocalDate(LocalDate.now());
		fileDetails.setLocalTime(LocalTime.now());
//		System.out.println(fileDetails);
		fileDetailRepository.save(fileDetails);
		return "redirect:/";
	}

	@GetMapping("/generate")
	public String generateExcel(Model model) {
		fileDetailsService.generateExcel();
		model.addAttribute("message", "Excel file has been generated successfully.");
		return "generate"; // This should match the name of your Thymeleaf template
	}


	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}