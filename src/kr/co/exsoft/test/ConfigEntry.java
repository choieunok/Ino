package kr.co.exsoft.test;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @(#)ConfigEntry.java
 *
 * @author	mgbang (guntop83@exsoft.co.kr)
 * @date	2011. 07. 19.
 */
public class ConfigEntry {
	
	public String basePath = "";
	
	public String startDate = "";
	public String endDate = "";
	public String testItem = "";
	public String fileExt = "";

	public String pcName = "";
	ArrayList<String> testItemList = null;
	
	
	/**
	 *  eXconfig.xml의 	<Extension><LogType>에 설정된 
	 *  파일 확장자들을 파싱한다.
	 *  
	 * @return
	 */
	public ArrayList<String> getItemList(){
		ArrayList<String> itemList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(this.testItem,",");
		while(st.hasMoreTokens()) {
//			logExtList.add(st.nextToken().toUpperCase());
			itemList.add(st.nextToken());
		}		
		return itemList;
	}
	
	/**
	 * Debug Print
	 */
    public void debug() {
    	
    	System.out.println("startDate");
    	System.out.println("   startDate : " + startDate);
    	System.out.println("");	
//    	System.out.println("PcName");
//    	System.out.println("   PcName : " + pcName);
//    	System.out.println("");
    	
    }
    
}