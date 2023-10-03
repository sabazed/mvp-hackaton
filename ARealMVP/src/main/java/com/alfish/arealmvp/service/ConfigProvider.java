package com.alfish.arealmvp.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ConfigProvider {

	@Value("${jwt.header}") private String JWT_HEADER;
	@Value("${jwt.prefix}") private String JWT_PREFIX;

	private final Environment environment;

	private String JWT_SECRET;
	private String S3_ACCESS_KEY;
	private String S3_SECRET_KEY;
	private String S3_BUCKET;

	@Autowired
	public ConfigProvider(Environment environment) {
		this.environment = environment;
		JWT_SECRET = environment.getProperty("jwtSecret");
		S3_ACCESS_KEY = environment.getProperty("s3AccessKey");
		S3_SECRET_KEY = environment.getProperty("s3SecretKey");
		S3_BUCKET = environment.getProperty("s3Bucket");
	}

	public String JWT_HEADER() {
		return JWT_HEADER;
	}

	public String JWT_PREFIX() {
		return JWT_PREFIX;
	}

	public String JWT_SECRET() {
		return JWT_SECRET;
	}

	public String S3_ACCESS_KEY() {
		return S3_ACCESS_KEY;
	}

	public String S3_SECRET_KEY() {
		return S3_SECRET_KEY;
	}

	public String S3_BUCKET() {
		return S3_BUCKET;
	}

}
