package com.murali.zip;

public class FileList {
	private String name = null;
	private String size = null;
	
	public FileList(String name, String size){
		this.name = name;
		this.size = size;
	}
	
	public void setFileName(String name){
		this.name = name;
	}
	public void setFileSize(String size){
		this.size = size;
	}
	
	public String getFileName(){
		return this.name;
	}
	public String getFileSize(){
		return this.size;
	}

}
