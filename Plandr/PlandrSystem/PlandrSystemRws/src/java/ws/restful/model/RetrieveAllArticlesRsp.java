/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.ArticleEntity;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class RetrieveAllArticlesRsp {
    
    private List<ArticleEntity> articles;

    public RetrieveAllArticlesRsp() {
    }

    public RetrieveAllArticlesRsp(List<ArticleEntity> articles) {
        this.articles = articles;
    }

    public List<ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleEntity> articles) {
        this.articles = articles;
    }
    
    
}
