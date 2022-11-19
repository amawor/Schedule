package Crawer;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.logging.LogFactory;
import javax.swing.*;
public class Crawer {
    final static int FRAME_WIDTH=480;
    final static int FRAME_HEIGHT=400;
    public static String main(String[] args,String userID,String password) throws IOException {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        final String u="https://selectcourse.nccu.edu.tw/regcourse/";
        System.out.println("Loading page now-----------------------------------------------: "+u);
        java.net.URL url=new java.net.URL(u);
        WebClient webClient = new WebClient();
        WebRequest webRequest=new WebRequest(url);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(30 * 1000);
        HtmlPage page = webClient.getPage(webRequest);
        HtmlForm form = page.getFormByName("form1");
        HtmlSubmitInput button = form.getInputByValue("µn¤J");
        HtmlTextInput userIDField = form.getInputByName("LDAPTB");
        HtmlPasswordInput passwordField = form.getInputByName("passTB");
        String pageAsXml;
	    userIDField.setValueAttribute(userID);
	    passwordField.setValueAttribute(password);
	    button.click();
        HtmlPage nextPage=webClient.getPage("https://selectcourse.nccu.edu.tw/regcourse/RegSub.aspx");
        pageAsXml = nextPage.asXml();
	    webClient.close();
	    return pageAsXml;
    }
}
