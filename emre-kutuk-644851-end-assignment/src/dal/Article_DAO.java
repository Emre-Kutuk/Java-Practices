package dal;

import model.Article;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Article_DAO {
  public List<Article> GetAllArticles() {
    List<Article> allArticles = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream("src/data/articles.dat");
        ObjectInputStream ois = new ObjectInputStream(fis)) {

      while (true) {
        try {
          Article article = (Article) ois.readObject();
          allArticles.add(article);
        } catch (EOFException eofe) {
          break;
        }
      }
    } catch (IOException | ClassNotFoundException fnfe) {
      fnfe.printStackTrace();
    }
    return allArticles;
  }

  public void AddArticles(List<Article> articles) {
    {
      try (FileOutputStream fos = new FileOutputStream(new File("src/data/articles.dat"));
          ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        for (Article a : articles) oos.writeObject(a);

      } catch (IOException fnfe) {
        fnfe.printStackTrace();
      }
    }
  }

  public void ChangeArticleQuantity(Article article, int quantity, Boolean add) {
    List<Article> articlesTemp = new ArrayList<>();

    for (Article a : GetAllArticles()) {
      if (a.equals(article)) {
        a.setQuantity(quantity, add);
      }
      articlesTemp.add(a);
    }
    AddArticles(articlesTemp);
  }
}
