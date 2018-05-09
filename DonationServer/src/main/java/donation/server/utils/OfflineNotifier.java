package donation.server.utils;

import donation.services.IMainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class OfflineNotifier {

    private HashMap <String,Vector<String> > undeliveredMessages = new HashMap<>();
    private final int REGISTERTIME = 1000;

    public void sendUndeliveredMessages(String username, BiConsumer<String,String> consumer){

        if(undeliveredMessages.get(username).size() == 0)return;

        undeliveredMessages.forEach((x,y)->{//todo optimization(make this to run on multiple threads (hint use executorService) )

            if(y.size() == 0)return;
            new Thread(()-> {
                try {
                    Thread.sleep(REGISTERTIME);
                } catch (InterruptedException e) {
                    System.out.println("DeliverMessages->" + e.getMessage());
                }
                y.forEach(undeliveredMessage -> consumer.accept(x, undeliveredMessage));
            }).start();
            //y.clear();
        });
    }

    public synchronized  void addMessage(String username,String message){

        if(undeliveredMessages.get(username) == null){
            Vector <String> vector = new Vector<>();vector.add(message);
            undeliveredMessages.put(username,vector);
            return;
        }

        undeliveredMessages.get(username).add(message);
    }

    public boolean hasUndeliveredMessages(String username){
        return !(undeliveredMessages.get(username) == null || undeliveredMessages.get(username).isEmpty());
    }

    public synchronized void removeNotificationFromDonor(String username,String message){

        if(undeliveredMessages.get(username) == null || undeliveredMessages.get(username).size() == 0)return;

        Vector <String> notificationsFromDonor = undeliveredMessages.get(username);

        System.out.println("before->" + notificationsFromDonor);
        Vector <String> notificationsToSend = new Vector<>(notificationsFromDonor.stream().filter(x->!x.equals(message)).collect(Collectors.toList()));

        System.out.println("after->" + notificationsToSend);

        undeliveredMessages.put(username,notificationsToSend);
    }
}
