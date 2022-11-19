package notification;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class Notification_TrayIcon {
    public static void displayTray(String title,String text) throws AWTException {//���Xwindows���q���A�B�]�t���D�M���e
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image,"Event");
        tray.add(trayIcon);
        trayIcon.displayMessage(title, text, MessageType.NONE);
    }
}
