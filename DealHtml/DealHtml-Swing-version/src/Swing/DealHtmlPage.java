package Swing;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DealHtmlPage {
	
	public static void CreatePage() {
		JFrame frame = new JFrame("DealHtml");
		frame.setBounds(100, 50, 1225, 639);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
        JPanel panel = new JPanel(null);
		
		// 创建一个 * 行 * 列的文本区域
		final TextArearMenu  oldText = new TextArearMenu();
		oldText.setFont(new Font(null, Font.BOLD, 17));
		// 设置自动换行
		oldText.setLineWrap(true);
		// 设置滚动
		JScrollPane scrollOld = new JScrollPane(oldText);
		// 设置大小
		scrollOld.setBounds(0, 0, 590, 600);
		// 添加到内容面板
		panel.add(scrollOld);
		
		final TextArearMenu newText = new TextArearMenu();
		newText.setFont(new Font(null, Font.BOLD, 17));
		newText.setLineWrap(true);
		JScrollPane scrollNew = new JScrollPane(newText);
		scrollNew.setBounds(610, 0, 600, 600);
		panel.add(scrollNew);
		
		//文本域监听器
		DocumentListener listener = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				newText.setText(dealHtml(oldText.getText()));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				newText.setText(dealHtml(oldText.getText()));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				newText.setText(dealHtml(oldText.getText()));
			}
		};
		oldText.getDocument().addDocumentListener(listener);

		frame.setContentPane(panel);
		frame.setVisible(true);
	}
	
	public static String dealHtml(String html) {
		InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(html.getBytes(Charset.forName("utf8"))), Charset.forName("utf8"));
		BufferedReader br = new BufferedReader(in);
		String line;
		int start = 0, end = 0, tmp = 0;
		StringBuffer sb = new StringBuffer();
		try {
			while ((line = br.readLine()) != null) {
				start = 0; 
				end = 0; 
				tmp = 0;
	        	if (!line.trim().equals("")) {
	        		//替换双引号
	        		line = line.replaceAll("\"", "\'");
					//char(9) 水平制表符，char(32) 空格
					for (int i = 0; i < line.length(); i++) {
						tmp = line.charAt(i);
						if (tmp != 9 && tmp != 32) {
							start = i;
							break;
						}
					}
					for (int i = line.length() - 1; i > 0; i--) {
						tmp = line.charAt(i);
						if (tmp != 9 && tmp != 32) {
							end = i + 1;
							break;
						}
					}
					if (end == 0) {
						line = "\"" + line + "\" +";
					} else {
						line = line.substring(0, start) + "\"" + line.substring(start, end) + "\" +";
					}
					sb.append(line + "\r\n");
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
    	return sb.toString();
	}
	
}
