package nl.inholland.exam;

import model.Article;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class WelcomeMessage_DAO {
  public List<WelcomeMessage> GetAllMessages() {
    List<WelcomeMessage> allMessages = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream("src/nl.inholland.exam/WelcomeMessages.dat");
        ObjectInputStream ois = new ObjectInputStream(fis)) {

      while (true) {
        try {
          WelcomeMessage msg = (WelcomeMessage) ois.readObject();
          allMessages.add(msg);
        } catch (EOFException eofe) {
          break;
        }
      }
    } catch (IOException | ClassNotFoundException fnfe) {
      fnfe.printStackTrace();
    }
    return allMessages;
  }
}
