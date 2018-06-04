package donation.client.utils.mailer;

import java.awt.*;
import java.net.URI;

public class Mailer {

    private static Desktop desktop = Desktop.getDesktop();

    public static void openGmail() {
        String uri = "https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
        try {
            desktop.browse(new URI(uri));
        } catch (Exception e) {
            System.out.println("Mailer->" + e.getMessage());
        }
    }

    public static void openYahoo() {

        String uri = "https://login.yahoo.com/?.src=ym&lang=&done=https%3A%2F%2Fmail.yahoo.com%2F";

        try {
            desktop.browse(new URI(uri));
        } catch (Exception e) {
            System.out.println("Mailer->" + e.getMessage());
        }
    }

}
