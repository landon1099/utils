import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class DealHtml {
	/**
	 * 
	 * 0、输入输出流操作文件（需进行每行操作）
	 * 1、替换双引号
	 * 2、判断每行左右两端是否为  水平制表符char=9 和 空格char=32
	 * 3、用双引号封装每行数据
	 * 注意：使用批处理命令运行时，deal_html.txt、deal_html_result.txt 必须以ANSI格式保存，否则会有乱码
	 */
	public static void main(String[] args) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
	    try {
			fis = new FileInputStream("deal_html.txt");
			br = new BufferedReader(new InputStreamReader(fis));
			fos = new FileOutputStream("deal_html_result.txt");
			bw = new BufferedWriter(new OutputStreamWriter(fos));
	    	String str = "";
	    	while ((str = br.readLine()) != null) {
	        	if (str.trim().length() != 0) {
	        		//替换双引号
	        		str = str.replaceAll("\"", "\'");
					int start = 0;
					int end = 0;
					int tmp = 0;
					for (int i = 0; i < str.length(); i++) {
						tmp = str.charAt(i);
						if (tmp != 9 && tmp != 32) {
							start = i;
							break;
						}
					}
					for (int i = str.length() - 1; i > 0; i--) {
						tmp = str.charAt(i);
						if (tmp != 9 && tmp != 32) {
							end = i + 1;
							break;
						}
					}
					String tmpStr = str.substring(0, start) + "\"" + str.substring(start, end) + "\" +";
					bw.write(tmpStr);
					bw.newLine();
					bw.flush();
				}
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } finally {
	    	if (bw != null) {
	        	bw.close();
	        }
	        if (br != null) {
	        	br.close();
	        }
	        if (fos != null) {
	        	fos.close();
	        }
	        if (fis != null) {
	        	fis.close();
	        }
	    }
	}
}
