package backup;

import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URISyntaxException;

public class Backup {

    private String path;

    private static Backup instance = new Backup();

    private Backup() {
        try {
            path = new File(Backup.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent() + "\\backup.dat";
//            path = Backup.class.getResource("/backup").toURI().getPath().substring(1)+"/backup.dat";
//            path = "./backup/backup.dat";
        } catch (URISyntaxException e) {
            System.out.println("Backup->" + e.getMessage());
        }
    }

    /**
     * @return a pair formed by username and password of database owner
     */

    private Pair<String, String> getUsernameAndPassword() {

        try {

            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(
                            new File(
                                    Backup
                                            .class
                                            .getResource(
                                                    "/hibernate.cfg.xml"
                                            )
                                            .toURI()
                                            .getPath()
                            )
                    );

            NodeList list = document.getElementsByTagName("property");

            String password = null, username = null;

            for (int i = 0; i < list.getLength(); ++i) {

                Node node = list.item(i);

                String nodeValue = node.getAttributes().getNamedItem("name").getNodeValue();

                if (!nodeValue.equals("hibernate.connection.username") && !nodeValue.equals("hibernate.connection.password"))
                    continue;

                if (nodeValue.equals("hibernate.connection.username")) {
                    username = node.getTextContent();
                    continue;
                }

                password = node.getTextContent();
            }

            return new Pair<>(username, password);

        } catch (Exception e) {
            System.out.println("Backup->getUsernameAndPassword->" + e.getMessage());
        }

        return null;
    }

    public static Backup getInstance() {
        return instance;
    }

    /**
     * Saves the content of database in a folder withname backup.dat
     */

    public void doBackup() {

        new Thread(() -> {

//            Pair<String, String> userPass = getUsernameAndPassword();

            String mySqlDumpLocation = null;

            try {
//                mySqlDumpLocation = getClass().getResource("/Processes/mysqldump.exe").toURI().getPath().substring(1);
                mySqlDumpLocation = new File(Backup.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent() + "\\mysqldump.exe";
            } catch (Exception e) {
                e.printStackTrace();
            }

//            String cmd = mySqlDumpLocation + " -u" + userPass.getKey() + " -p" + userPass.getValue() + " --database " + "iss" + " -r " + "\"" + path + "\"";
            String cmd = "\"" + mySqlDumpLocation +"\" -uroot -proot --database iss -r \"" + path + "\"";

            try {
                System.out.println(mySqlDumpLocation);
                System.out.println(path);
                System.out.println(cmd);
                int error = Runtime.getRuntime().exec(cmd).waitFor();
                System.out.println("M-am terminat " + error);
                if (error == 0) {
                    System.out.println("Backup complete!");
                    return;
                }

                System.out.println("Backup failed!");

            } catch (Exception e) {
                System.out.println("DoBackup->" + e.getMessage());
            }

        }).start();
    }
}
