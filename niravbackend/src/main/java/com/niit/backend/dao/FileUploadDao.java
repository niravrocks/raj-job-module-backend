package com.niit.backend.dao;

import com.niit.backend.model.UploadFile;

public interface FileUploadDao {
	void save(UploadFile uploadFile);
	UploadFile getFile(String username);
	UploadFile update(int fileuploadid, UploadFile uploadfile);
}

