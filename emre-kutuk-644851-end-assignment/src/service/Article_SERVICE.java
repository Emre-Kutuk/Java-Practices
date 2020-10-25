package service;

import dal.Article_DAO;
import model.Article;

import java.util.List;

public class Article_SERVICE {
  Article_DAO article_db = new Article_DAO();

  public void AddArticles(List<Article> articles) {
    try {
      article_db.AddArticles(articles);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Article> GetAllArticles() {
    try {
      return article_db.GetAllArticles();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void ChangeArticleQuantity(Article article, int quantity, Boolean add) {
    try {
      article_db.ChangeArticleQuantity(article, quantity, add);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
