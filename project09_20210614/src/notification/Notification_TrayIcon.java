package notification;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class Notification_TrayIcon {
    public static void displayTray(String title,String text) throws AWTException {//跳出windows的通知，且包含標題和內容
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image,"Event");
        tray.add(trayIcon);
        trayIcon.displayMessage(title, text, MessageType.NONE);
    }
}
