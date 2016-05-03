package kr.co.exsoft.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;

public class SearchFileEverage {
	//TODO:XML variable (추후 XML 설정값에서 rootPath설정)
	public static String rootPath = "C:"+File.separator+"log"+File.separator+"test"+File.separator;
//	public static String rootPath = File.separator+"log"+File.separator+"logdata";


    private static long totalFileSize = 0L;
    private static int totalFileCnt = 0;
    
    public static void main(String[] args) throws IOException {

		ConfigManager configManager = ConfigManager.getInstance();
		ConfigEntry eXconfig = configManager.getConfig();
//		String modelName = "Y574"; //설비명
		ArrayList<String> testItemName = eXconfig.testItemList;
		rootPath = eXconfig.basePath + File.separator + eXconfig.pcName + File.separator;

		String startDate = eXconfig.startDate; //검색 시작날짜
		String endDate = eXconfig.endDate; //검색 마지막날짜
		
		long startTime = System.nanoTime(); //public long startTime = System.currentTimeMillis();
		long endTime = 0L;

    	try {
//    		doProcess(modelName, testItemName);
    		doProcess( testItemName, startDate, endDate);
    		endTime = System.nanoTime() - startTime;
    		System.out.println("Total Processing Time : " + getFormatNumber(endTime) + " nsec");
    		endTime = endTime / 1000000;
    		System.out.println("Total Processing Time : " + getFormatNumber(endTime) + " msec");
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void doProcess( ArrayList<String> testItemList, String Sdate, String Edate) throws Exception{
		//대상 공정 선택
		StringBuffer sb = null; //  "KS" + modelName+"*"+testItemName+"*"; 
		for(String item:testItemList){
				sb = new StringBuffer();
			if("txt".equals(item)){
				sb.append("*.txt");
			}else{
				sb.append("KS");
				sb.append("*");
				sb.append(item);
				sb.append("*");			
			}

			String glob = sb.toString();
			System.out.println("#####################################");
			System.out.println("TestItem = "+ item);
			System.out.println("#####################################");
		    Walker walk = new Walker(glob);
		    
		    EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
	    	for(Path fileTree : getSearchRange(Sdate,Edate) ){ 
	    		Files.walkFileTree(fileTree, opts, Integer.MAX_VALUE, walk);
	    		totalFileSize += walk.getTotalFileSize();
	    		totalFileCnt += walk.getTotalFileCnt();
	    		
	    		showResult(fileTree, totalFileSize,totalFileCnt);
	    		//txt 파일일때 초기화 금지
	    		if(!"txt".equals(item)){
	    			initVal();
				}
	    		
	    	}
		}
		
	}
	
	public static void showResult(Path fileTree, long totalFileSize, int totalFileCnt) throws Exception{
        System.out.println("=====================================");
        System.out.println("totalFileSize = "+ getFormatNumber(totalFileSize / 1024)+" Kbytes")   ; //KB환산
        System.out.println("totalFileCnt = "+ getFormatNumber((long)totalFileCnt) + "개")     ;
        
        //평균치 구하기
        if((totalFileSize / 1024)>0){
        	System.out.println("Everage File Size = "+ getFormatNumber(totalFileSize / (long)totalFileCnt) + " bytes" ) ;
        }else{
        	System.out.println("totalEverage = 0") ;
        }
        
        System.out.println("=====================================");
        System.out.println("\n");
	}

	public static void initVal(){
		totalFileSize = 0;
		totalFileCnt = 0;
	}
	
	
	public static String getFormatNumber(long no){
		String original = Long.toString(no);
		String convert = "";
		int count = 1;
		for(int k=original.length()-1; k>-1; k--){   
			if( (count%3) == 0 && k < original.length()-1 && k > 0)
				convert = "," +original.charAt(k)  + convert;
			else
				convert = original.charAt(k) + convert;

			count++;
		}
		return convert;
	}
	
	public static ArrayList<Path> getSearchRange(String Sdate, String Edate){
		ArrayList<Path> pathList = new ArrayList<Path>();
		ArrayList<String> pagtIdList = new ArrayList<String>();
		try{
			int iSdate = Integer.parseInt(Sdate);
			int iEdate = Integer.parseInt(Edate);
			// 시작날짜와 끝날짜의 사이값
			if(iSdate <= iEdate){
				 for(int i=iSdate; i <= iEdate ; i++) {
					 pagtIdList.add(String.valueOf(i));
				 }
			}

			for(String pagtIdFolder : pagtIdList){
				pathList.add(Paths.get(rootPath + File.separator + pagtIdFolder));
			}
		}catch(Exception e){
			StackTraceElement[] ste = e.getStackTrace();
			for(StackTraceElement s : ste)
				System.err.println(s);
		}
		return pathList;
	}
}
