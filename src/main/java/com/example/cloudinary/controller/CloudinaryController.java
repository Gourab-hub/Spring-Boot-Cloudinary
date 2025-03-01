package com.example.cloudinary.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.cloudinary.service.CloudinaryService;

@RestController
public class CloudinaryController {

	@Autowired
	private CloudinaryService cloudinaryService;

	@PostMapping("/imageUpload")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
		String imageUrl = cloudinaryService.uploadFile(file);
		return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
	}

	@PostMapping("/videoUpload")
	public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
		String videoUrl = cloudinaryService.uploadFile(file, "video");
		return ResponseEntity.ok("Video uploaded successfully: " + videoUrl);
	}

	@PostMapping("/multiImages")
	public ResponseEntity<List<String>> uploadMultipleImages(@RequestParam("files") MultipartFile[] files) {
		List<String> imageUrls = cloudinaryService.uploadMultipleImages(files);
		return ResponseEntity.ok(imageUrls);
	}

	@DeleteMapping("/delete/{publicId}")
	public ResponseEntity<Map> deleteImage(@PathVariable String publicId) {
		try {
			Map result = cloudinaryService.deleteImage(publicId);
			return ResponseEntity.ok(result);
		} catch (IOException e) {
			return ResponseEntity.status(500).body(null);
		}
	}

}
