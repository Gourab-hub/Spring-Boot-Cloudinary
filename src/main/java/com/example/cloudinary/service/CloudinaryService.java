package com.example.cloudinary.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	@Autowired
	private Cloudinary cloudinary;

	public String uploadFile(MultipartFile file) {
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
					ObjectUtils.asMap("transformation", "w_300,h_300,c_fill"));
			return uploadResult.get("url").toString(); // Get the uploaded file URL
		} catch (IOException e) {
			throw new RuntimeException("Error uploading file to Cloudinary", e);
		}
	}

	public String uploadFile(MultipartFile file, String resourceType) {
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
//	                    		"effect", "make_transparent:50", // Remove background (if applicable)
//	                            "background", "pink",
					"resource_type", resourceType // Set resource type dynamically
			));
			System.out.println(uploadResult);
			return uploadResult.get("url").toString(); // Get the uploaded file URL
		} catch (IOException e) {
			throw new RuntimeException("Error uploading file to Cloudinary", e);
		}
	}

	public List<String> uploadMultipleImages(MultipartFile[] files) {
		List<String> urls = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
						ObjectUtils.asMap("resource_type", "image"));
				urls.add(uploadResult.get("url").toString()); // Store uploaded image URL
			} catch (IOException e) {
				throw new RuntimeException("Error uploading file to Cloudinary", e);
			}
		}
		return urls;
	}

}
