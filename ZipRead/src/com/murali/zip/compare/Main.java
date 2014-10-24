package com.murali.zip.compare;

import java.io.File;

import com.murali.xml.XMLRead;
import com.murali.zip.ZipRead;



public class Main {
	
	private enum Args{
		help, compare, with;
	}
	
	public static void readTest(String file1, String file2) throws Exception{
		File f1 = new File(file1), f2= new File(file2);
		if (! f1.exists()){
			System.out.println("Unable to read file: " + f1.getCanonicalPath());
			System.exit(0);
		}
		if (! f2.exists()){
			System.out.println("Unable to read file: " + f2.getCanonicalPath());
			System.exit(0);
		}
	}
	
	public static void validateArguments(String[] args){
		try{
			switch (Args.valueOf(args[0].substring(1))){
				case compare:
					if (args[2].equals("-with")){
						readTest(args[1], args[3]);
						break;
					}
				case help:
				default:
					System.out.println("Usage: java -jar CompareTool.jar -compare [newfile] -with [oldfile]"
							+ "\n Ex: java -jar CompareTool.jar -compare \"Process Map_1.1.ear\" "
							+ "-with \"Process Map_1.0.ear\"");
					System.exit(0);
					break;
			}
		}catch(Exception e){
			System.out.println("Invalid Parameters!");
			System.out.println("Usage: java -jar CompareTool.jar -compare [newfile] -with [oldfile]"
					+ "\n Ex: java -jar CompareTool.jar -compare \"Process Map_1.1.ear\" "
					+ "-with \"Process Map_1.0.ear\"");
			System.exit(0);
		}
		
	}
	
	/**
	 * Creates a new directory in OS Temp directory.  
	 * 
	 * Format: "cmpr@_" + current time in number format.
	 * 
	 * @return tmpDir 
	 */
	public static File createDirInTmp(){
		
		
		//final File sysTmpDir = new File(System.getProperty("java.io.tmpdir"));
		
		//String dirName = "cmpr@_"+ new java.util.Date().getTime();
		//final File tmpDir = new File(sysTmpDir, dirName);

		final File tmpDir = new File(new File(System.getProperty("java.io.tmpdir")), 
				"cmpr@_"+ new java.util.Date().getTime());
		
		if (! tmpDir.mkdir()) System.out.println("Unable to access temp directory!!!");;
		
		return tmpDir;
	}
	
	public static boolean recursiveDelete(File fileOrDir){
		
		if(fileOrDir.isDirectory())
	    {
	        // recursively delete contents
	        for(File innerFile: fileOrDir.listFiles())
	        {
	            if(!recursiveDelete(innerFile))
	            {
	                return false;
	            }
	        }
	    }
		return fileOrDir.delete();
	}
	
	public static void main(String[] args) {
		
		try{
			validateArguments(args);
			
			System.out.println("Command Line Args validation successful!");
			
			ZipRead zRead = new ZipRead();
			XMLRead xRead = new XMLRead();
			
			File tmpDir = createDirInTmp();
			
			zRead.extractTo(args, tmpDir);
			
			xRead.input(tmpDir);
	
			//zRead.input(args[1], args[3], tmpDir);
			recursiveDelete(tmpDir);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

}