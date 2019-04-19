import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.ws.util.StringUtils;

public class NumTransfer {
	
	public static void main(String[] args) {
		PropertiesUtil pUtil = new PropertiesUtil();
		String rootPath = pUtil.getValue("address");
		ArrayList<String> files = getFiles(rootPath);
		
		for (String name : files) {
			try {
				modifyName(name);
			} catch (Exception e) {
				System.out.println("------can not transter fileName：" + name);
				RuntimeException exception  =  new RuntimeException(e);
		        throw  exception;
			}
		}
	}
	
	/**
	 * 修改文件名称
	 */
	public static Boolean modifyName(String path) {
		
		Boolean isOver = true;
		
		String file_addr = path;
		file_addr = file_addr.replaceAll("\\\\", "/").replaceAll("//", "/");
		
		File fileTarget = new File(file_addr);
		String name = fileTarget.getName();
		if (!name.contains("第") || !name.contains("集")) {
			System.out.println("------can not transfer file name :" + name);
			return isOver;
		}
		
		int start = name.indexOf("第");
		int end = name.indexOf("集");
		String numString = name.substring(start + 1, end);
		if (!isNumStr(numString)) {
			Integer numTran = chineseNumber2Int(numString);
			
			String newName = name.replace(numString, numTran + "");
			
			fileTarget.renameTo(new File(fileTarget.getParent() + "/" + newName));
		}
		
		return isOver;
	}
	
	public static ArrayList<String> getFiles(String path) {
	    ArrayList<String> fileUrls = new ArrayList<String>();
	    File file = new File(path);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	        	fileUrls.add(tempList[i].toString());
	        }
	        if (tempList[i].isDirectory()) {
	        	ArrayList<String> files = getFiles(tempList[i].toString());
	        	fileUrls.addAll(files);
	        }
	    }
	    
	    return fileUrls;
	}
	
	
	/**
     * 中文數字转阿拉伯数组【十万九千零六十  --> 109060】
     * @author 雪见烟寒
     * @param chineseNumber
     * @return
     */
    private static int chineseNumber2Int(String chineseNumber){
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                        case 0:
                            temp *= 10;
                            break;
                        case 1:
                            temp *= 100;
                            break;
                        case 2:
                            temp *= 1000;
                            break;
                        case 3:
                            temp *= 10000;
                            break;
                        case 4:
                            temp *= 100000000;
                            break;
                        default:
                            break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }
	
    //判断字符串是否为数字
    public static boolean isNumStr(String str){
        for (int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    
}
