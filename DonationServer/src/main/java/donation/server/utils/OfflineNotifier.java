package donation.server.utils;


import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class OfflineNotifier {

    private HashMap<String, Set<String>> undeliveredMessages = new HashMap<>();
    private final int REGISTERTIME = 1000;

    public synchronized void sendUndeliveredMessages(String username, BiConsumer<String, String> consumer) {

        if (undeliveredMessages.get(username) == null || undeliveredMessages.get(username).size() == 0) return;

        undeliveredMessages.forEach((x, y) -> {

            if (y.size() == 0) return;
            new Thread(() -> {

                try {
                    Thread.sleep(REGISTERTIME);
                } catch (InterruptedException e) {
                    System.out.println("DeliverMessages->" + e.getMessage());
                }

                y.forEach(undeliveredMessage -> {
                    consumer.accept(x, undeliveredMessage);
                });

            }).start();

        });
    }

    public synchronized void addMessage(String username, String message) {

        if (undeliveredMessages.get(username) == null) {
            Set<String> set = new HashSet<>();
            set.add(message);
            undeliveredMessages.put(username, set);
            return;
        }

        undeliveredMessages.get(username).add(message);
    }

    public synchronized boolean hasUndeliveredMessages(String username) {

        return !(undeliveredMessages.get(username) == null || undeliveredMessages.get(username).isEmpty());
    }

    public synchronized void removeNotificationFromDonor(String username, String message) {

        if (undeliveredMessages.get(username) == null || undeliveredMessages.get(username).size() == 0) return;

        Set<String> notificationsFromDonor = undeliveredMessages.get(username);
        Set<String> notificationsToSend = new HashSet<>(notificationsFromDonor.stream().filter(x -> !x.equals(message)).collect(Collectors.toList()));

        undeliveredMessages.put(username, notificationsToSend);
    }

    public int getNrNotifications(String username) {
        return undeliveredMessages.get(username) == null ? 0 : undeliveredMessages.get(username).size();
    }
}
