package notification;
import java.awt.SystemTray;
public class Thread_runnable implements Runnable{
	private long time_len;
	private String title,text;
	public Thread_runnable(long time_len,String title,String text) {//輸入在多久後發布通知(單位為毫秒)
		this.time_len = time_len;
		this.title = title;
		this.text = text;
	}
	public void run() {
		try {
			Thread.sleep(time_len);
			//System.out.println("test, sleep for:"+time_len);
			
			if (SystemTray.isSupported()) {
				Notification_TrayIcon.displayTray(title, text);
			}else {
				System.out.println("Tray is no supported");
			}
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}		
	}	
}