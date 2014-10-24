/**
 * @author Muralidhar Tatiparthi
 *
 */
package com.murali.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.murali.ear.config.Properties;
import com.murali.xls.WriteToXLS;


public class ZipRead {
	
	public boolean extractZipToDir(String file1, File tmpDir){
		
		boolean rBool = false;
		ZipFile zFile = null;
		
		try{
			
			zFile = new ZipFile(new File(file1),ZipFile.OPEN_READ);
			
			Enumeration<? extends ZipEntry> zEntries = zFile.entries();
			
			ZipEntry zEntry = null;
			
			while(zEntries.hasMoreElements()){
				zEntry = (ZipEntry)zEntries.nextElement();

				int extn = zEntry.getName().lastIndexOf('.');
				
				String absPath = tmpDir.getAbsolutePath() + "\\" + zEntry.getName();
				
				new File(new File(absPath).getParent()).mkdirs();
				
				if (Properties.listTokens.contains(zEntry.getName().substring(extn+1))){
					
					File tmpZip = new File(tmpDir.getAbsolutePath() + "\\tmp" + zEntry.getName());
					
					BufferedInputStream bis = new BufferedInputStream(zFile.getInputStream(zEntry));
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpZip));
					
					while (bis.available() > 0){ bos.write(bis.read()); }
					
					bis.close();
					bos.close();

					extractZipToDir(tmpZip.getAbsolutePath(), new File(absPath));

					tmpZip.delete();
				}
				else{
					BufferedInputStream bis = new BufferedInputStream(zFile.getInputStream(zEntry));
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(absPath));
					
					while (bis.available() > 0){ bos.write(bis.read()); }
					
					bis.close();
					bos.close();
					
				}
			
			}
			rBool = true;
		}
		catch (Exception e){ e.printStackTrace();}
		finally {
			try{ 
				if (zFile != null)
					zFile.close(); 
				
			} catch (IOException ioe)
				{System.out.println("Failed to read/close file : " + file1
						+ " Error Message : " + ioe.getMessage());}
		}
		return rBool;
	}
	
	public void extractTo(String[] args, File tmpDir){
		
		System.out.println("Extracting file :" + new File(args[1]).getAbsolutePath());

		extractZipToDir(args[1], 
				new File(tmpDir.getAbsolutePath() + "\\newCompare_" + new File(args[1]).getName()));

		System.out.println("Extracting file :" + new File(args[3]).getAbsolutePath());
		
		extractZipToDir(args[3], 
				new File(tmpDir.getAbsolutePath() + "\\oldWith_" + new File(args[3]).getName()));
		
	}
	
	public void input(String file1, String file2, File tmpDir){
		
		System.out.println("Reading file :" + file1);
		System.out.println("Reading file :" + file2);
		
		ZipFile newZipFile = null;
		ZipFile oldZipFile = null;
				
		List<String[]> newListZipFile = null;
		List<String[]> oldListZipFile = null;
		
		ZipEntry zipEntry = null;
		
		try{
			newZipFile = new ZipFile(new File(file1),ZipFile.OPEN_READ);
			Enumeration<? extends ZipEntry> newZipEntries = newZipFile.entries();
			
			newListZipFile = new ArrayList<String[]>();
			
			while(newZipEntries.hasMoreElements()){
				zipEntry = (ZipEntry)newZipEntries.nextElement();

				int extn = zipEntry.getName().lastIndexOf('.');
				
				if (Properties.listTokens.contains(zipEntry.getName().substring(extn+1))){
					
					BufferedInputStream bis = new BufferedInputStream(newZipFile.getInputStream(zipEntry));
					
					String newFile = "new" + zipEntry.getName();
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
					
					while (bis.available() > 0){ bos.write(bis.read()); }
					
					bis.close();
					bos.close();
					
					ZipFile innerZip = new ZipFile(new File(newFile),ZipFile.OPEN_READ);
					
					innerZip(innerZip, newListZipFile, zipEntry.getName());
					
					if (innerZip != null) innerZip.close();
					
					(new File(newFile)).delete();
					
					
				}
				else
					newListZipFile.add(new String[]{zipEntry.getName(),
							String.valueOf(zipEntry.getSize()), ""});
				
			}
			WriteToXLS wtx = new WriteToXLS();
			wtx.input(file1, newListZipFile);
			
			
			/*oldZipFile = new ZipFile(file2);
			Enumeration<? extends ZipEntry> oldZipEntries = oldZipFile.entries();
			
			oldListZipFile = new ArrayList<Object[]>();
			
			while(oldZipEntries.hasMoreElements()){
				zipEntry = (ZipEntry)oldZipEntries.nextElement();
				
				oldListZipFile.add(new String[]{zipEntry.getName(),
						String.valueOf(zipEntry.getSize())});
			}*/
			
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try{ 
				if (newZipFile != null)
					newZipFile.close(); 
				
			} catch (IOException ioe)
				{System.out.println("Failed to read/close file : " + file1
						+ " Error Message : " + ioe.getMessage());}
			try{ 
			
				if (oldZipFile != null)
					oldZipFile.close(); 
				
			} catch (IOException ioe)
				{System.out.println("Failed to read/close file : " + file2
						+ " Error Message : " + ioe.getMessage());}
			
		}
		
	}
	
	public void innerZip(ZipFile innerZip, 
			List<String[]> listZipFile, String zipName) {
		try{
			
			Enumeration<? extends ZipEntry> innerZipEntries = innerZip.entries();
			
			while (innerZipEntries.hasMoreElements()){
				ZipEntry zE = (ZipEntry)innerZipEntries.nextElement();

				listZipFile.add(new String[]{zE.getName(),
						String.valueOf(zE.getSize()),
						zipName});
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// Current directory output.....
		File directory = new File(args[0]);

				
		//Zip Read

		try{
			System.out.println("Trying to read " + directory.getName() + " at " +
					directory.getParent());
			
			ZipFile zipFile = new ZipFile(args[0]);
			
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			
						
			Date date = new Date();
			
			
			WritableWorkbook wWorkbook;
			File currentDirectory = new File(".");
			
			String optFile = currentDirectory.getCanonicalFile() + "\\" +
					zipFile.getName().substring(zipFile.getName().lastIndexOf('\\') + 1) + "_" 
					+ Properties.sdfExcelFileDate.format(date) + "T" 
					+ Properties.sdfExcelFileTime.format(date) + ".xls";
			
			wWorkbook = Workbook.createWorkbook(new File(optFile));
			System.out.println(optFile);
			
			WritableSheet wSheet = wWorkbook.createSheet("List Files", 0);
			
			WritableCellFormat wcf = new WritableCellFormat();
			//wcf.setShrinkToFit(false);
			//wcf.setWrap(false);
			
			//List<FileList> fileListOld = new ArrayList<FileList>();
			//List<String[]> fileListOld1 = new ArrayList<String[]>();
			
			int iColumn = 0;
			int iRow = 0;
			while(entries.hasMoreElements()){
				ZipEntry zipEntry = entries.nextElement();
				System.out.println(zipEntry.getName() + "  " 
						+ Properties.sdfDateTime.format(new Date(zipEntry.getTime())) + " "
						+ zipEntry.getSize());
				
				wSheet.addCell(new Label(iColumn,iRow,zipEntry.getName(), wcf));
				
				wSheet.addCell(new Label(iColumn + 1, iRow,
						String.valueOf(zipEntry.getSize()), wcf));
				wSheet.addCell(new Label(iColumn + 2, iRow, 
						Properties.sdfDateTime.format(new Date(zipEntry.getTime())), wcf));
				
				//fileListOld.add(new FileList(zipEntry.getName(),));
				
				for (String token : Properties.strToken){
					
					if (zipEntry.getName().endsWith(token))
						wSheet.addCell(new Label(iColumn+3,iRow,"Compressed File", wcf));
				}
				
				iRow = iRow + 1;
			}
			
			CellView cView = new CellView();
			
			cView.setAutosize(true);
			wSheet.setColumnView(0,cView );
			wSheet.setColumnView(1,cView);
			wSheet.setColumnView(2,cView);
			wSheet.setColumnView(3,cView);
			
			wWorkbook.write();
			wWorkbook.close();

			zipFile.close();
		} catch (Exception e){
			System.out.println("Exception is =" + e.getMessage());
		}
	}

}
