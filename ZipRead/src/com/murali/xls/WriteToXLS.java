package com.murali.xls;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.murali.ear.config.Properties;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class WriteToXLS {
	
	public void input(String file1, List<String[]> list){
		try{
		WritableWorkbook wWorkbook;
		File currentDirectory = new File(".");
		
		Date currentDate = new Date();
		
		String optFile = currentDirectory.getCanonicalFile() + "\\" +
				file1.substring(file1.lastIndexOf('\\') + 1)  + "_" 
				+ Properties.sdfExcelFileDate.format(currentDate) + "T" 
				+ Properties.sdfExcelFileTime.format(currentDate) + ".xls";
		
		wWorkbook = Workbook.createWorkbook(new File(optFile));
		
		WritableSheet wSheet = wWorkbook.createSheet("List Files", 0);
		
		WritableCellFormat wcf = new WritableCellFormat();
		
		int iColumn = 0;
		int iRow = 1;
		
		for (String[] sList : list){
			if (! sList[0].isEmpty()){
				
			}
						
			wSheet.addCell(new Label(iColumn,iRow,sList[0], wcf));
			
			wSheet.addCell(new Label(iColumn + 1, iRow,
					String.valueOf(sList[1]), wcf));
			wSheet.addCell(new Label(iColumn + 2, iRow, sList[2], wcf));
			
			iRow = iRow + 1;
		}
		
		CellView cView = new CellView();
		
		cView.setAutosize(true);
		
		wSheet.setColumnView(0,cView);
		wSheet.setColumnView(1,cView);
		wSheet.setColumnView(2,cView);
		//wSheet.setColumnView(3,cView);
		
		wWorkbook.write();
		wWorkbook.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
