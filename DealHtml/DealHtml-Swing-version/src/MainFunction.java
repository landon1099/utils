import java.awt.EventQueue;

import javax.swing.UIManager;
import freeseawind.lf.LittleLuckLookAndFeel;
import Swing.DealHtmlPage;

public class MainFunction {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
            public void run(){
                try{
                	UIManager.setLookAndFeel(LittleLuckLookAndFeel.class.getName());
                	DealHtmlPage.CreatePage();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
	}
}
