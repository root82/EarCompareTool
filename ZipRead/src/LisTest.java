import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.murali.zip.FileList;


public class LisTest {

	public static void main(String[] args) {

		List<FileList> fileListOld = new ArrayList<FileList>();
		List<FileList> fileListNew = new ArrayList<FileList>();
		
		fileListOld.add(new FileList("a1","12"));
		fileListOld.add(new FileList("c1","10"));
		fileListOld.add(new FileList("z1","10"));
		
		fileListNew.add(new FileList("a1","12"));
		fileListNew.add(new FileList("b1","12"));
		fileListNew.add(new FileList("z1","14"));

		
		Iterator<FileList> flio = fileListOld.iterator();
		Iterator<FileList> flin = fileListNew.iterator();
		
		
		for(FileList objList1:fileListOld){
			//System.out.println("old value:"+objList1.getFileName());
			
			for(FileList objListNew: fileListNew){
				
				
				if (objListNew.getFileName().equalsIgnoreCase(objList1.getFileName())){
					if (objListNew.getFileSize().equalsIgnoreCase(objList1.getFileSize())){
						System.out.println(objListNew.getFileName() + " " +
								objList1.getFileName() + " " +
								objListNew.getFileName().equalsIgnoreCase(objList1.getFileName())
								+ " fileSize Equal");
					}else{
						System.out.println(objListNew.getFileName() + " " +
								objList1.getFileName() + " " +
								objListNew.getFileName().equalsIgnoreCase(objList1.getFileName())
								+ " file Size Not Equal");
					}
					
				}else
					System.out.println(objListNew.getFileName() + " " +
							objList1.getFileName() + " " +
							objListNew.getFileName().equalsIgnoreCase(objList1.getFileName()));
			}
/*			if(fileListNew.get(0)[0].contains(objList1[0])){
				//al2.remove(objList1);
				System.out.println("new value:"+fileListNew.get(0)[0].contains(objList1[0]));
			}*/
		}
			
	}

}
