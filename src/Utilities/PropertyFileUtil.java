package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
public static String getValueForKey(String Key)throws Throwable
{
	Properties configproperties=new Properties();
	FileInputStream fi=new FileInputStream("D:\\Selenium_Evening\\ERP_Stock\\PropertyFile\\Environment.properties");
	configproperties.load(fi);
	return configproperties.getProperty(Key);
}
}
