import java.util.ArrayList;
import java.util.List;

public class strTest {

	public static void main(String[] args) {

		List<String[]> oldTest = new ArrayList<String[]>();
		List<String[]> newTest = new ArrayList<String[]>();
		
		oldTest.add(new String[]{"a1","12"});
		oldTest.add(new String[]{"c1","10"});
		oldTest.add(new String[]{"z1","10"});
		oldTest.add(new String[]{"r1","10"});
		oldTest.add(new String[]{"m1","10"});
		oldTest.add(new String[]{"b1","10"});

		newTest.add(new String[]{"a1","12"});
		newTest.add(new String[]{"b1","10"});
		newTest.add(new String[]{"z1","14"});
		newTest.add(new String[]{"x1","14"});
		newTest.add(new String[]{"m1","10"});
		newTest.add(new String[]{"n1","6"});
		
		List<String> oldTestSht = new ArrayList<String>();
		List<String> oldTestShtCopy = new ArrayList<String>();
		List<String> newTestSht = new ArrayList<String>();
		//List<String> newTestShtCopy = new ArrayList<String>();
		
		for(String[] strTest : oldTest){ oldTestSht.add(strTest[0]); }
		for(String[] strTest : oldTest){ oldTestShtCopy.add(strTest[0]); }
		for(String[] strTest1 : newTest){ newTestSht.add(strTest1[0]);	}
		//for(String[] strTest1 : newTest){ newTestShtCopy.add(strTest1[0]);	}
		
		System.out.println(oldTestSht);
		oldTestSht.removeAll(newTestSht);
		System.out.println(oldTestSht);
		
		System.out.println(newTestSht);
		newTestSht.removeAll(oldTestShtCopy);		
		System.out.println(newTestSht);

		
		for(String[] lOld:oldTest){
			if (oldTestSht.contains(lOld[0])){
				System.out.println(lOld[0] + " is Missing" );
			}
			else
			for(String[] lNew: newTest){
				
				if (newTestSht.contains(lNew[0])){
					System.out.println(lNew[0] + " is New");
					newTestSht.remove(lNew[0]);
					break;
				}
				
				if (lNew[0].equalsIgnoreCase(lOld[0])){
					if (lNew[1].equalsIgnoreCase(lOld[1])){
						System.out.println(lNew[0] + " " +
								lOld[0] + " " +
								lNew[0].equalsIgnoreCase(lOld[0])
								+ " fileSize Equal");
					}else{
						System.out.println(lNew[0] + " " +
								lOld[0] + " " +
								lNew[0].equalsIgnoreCase(lOld[0])
								+ " file Size Not Equal");
					}
					
				}/*else
					System.out.println(lNew[0] + " " +
							lOld[0] + " " +
							lNew[0].equalsIgnoreCase(lOld[0]));*/
			}

		}

	}

}
