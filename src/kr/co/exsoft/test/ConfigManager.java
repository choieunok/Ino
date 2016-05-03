package kr.co.exsoft.test;

import java.util.ArrayList;

import kr.co.exsoft.fw.xml.XmlUtil;

/**
 * @(#)ConfigManager.java
 * 
 * @author 	mgbang (guntop83@exsoft.co.kr)
 * @date	2011. 07. 19.
 */
public class ConfigManager {
	
	private static ConfigManager instance = null;
	
	private XmlUtil xmlUtil = null;
	private ConfigEntry configEntry = null;
	
	
	/**
	 * ConfigManager Singleton
	 * 
	 * @return
	 */
	public static ConfigManager getInstance() {
		
		if (instance == null) {
			synchronized (ConfigManager.class) {
				if (instance == null) {
					instance = new ConfigManager();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * Constructor
	 */
	ConfigManager() {
		
		try {
			this.configEntry = new ConfigEntry();
//			this.xmlUtil = new XmlUtil(this.getBasePath() + "/eXconfig.xml");
			this.xmlUtil = new XmlUtil("C:/eXconfig.xml");
			
			this.initConfig();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * GET - Properties ConfigEntity
	 * 
	 * @return
	 */
	public ConfigEntry getConfig() {
		
		this.configEntry = new ConfigEntry();
//		this.xmlUtil = new XmlUtil(this.getBasePath() + "/eXconfig.xml");
		this.xmlUtil = new XmlUtil("C:/eXconfig.xml");
		this.initConfig();
		
        return configEntry;
    }
	
	/**
	 * GET - Classpath Folder Path
	 * 
	 * @return
	 */
	public String getBasePath() {
		
		String TOKEN_STR = "/eXrep";
        String basePath = getClass().getResource("ConfigManager.class").getPath();
        
        int idx = basePath.indexOf(TOKEN_STR);
        if(idx > 0)
        {
            basePath = basePath.substring(0, idx + TOKEN_STR.length());
        }
        
        idx = basePath.lastIndexOf(":");
        
        if(idx > 0)
        {
            String tmp = basePath.substring(0, idx); 
            basePath = basePath.substring(idx, basePath.length());
            
            idx = tmp.lastIndexOf("/");
            if(idx > 0 || (idx == 0 && tmp.subSequence(0, 1).equals("/")))
            {
                idx++;
                basePath = tmp.substring(idx, tmp.length()) + basePath;
            }
        }
        
        if((basePath.length() - 1) == basePath.lastIndexOf("/"))
        {
            basePath = basePath.substring(0, (basePath.length() - 1));
        }
        
        return basePath;
	}
	
	/**
	 * xml Value Entity Setting
	 */
	private void initConfig() {
		

		configEntry.testItem = xmlUtil.getValue("TestItem.Name");
		configEntry.testItemList = configEntry.getItemList();
		
		configEntry.startDate = xmlUtil.getValue("Period.Start");
		configEntry.endDate = xmlUtil.getValue("Period.End");

		configEntry.basePath = xmlUtil.getValue("TestPath.BasePath");
		configEntry.pcName = xmlUtil.getValue("TestPath.PCName");
        
      
	}
}
