/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.ArticleEntity;

/**
 *
 * @author Pham The Dzung
 */
public class RetrieveArticleRsp {
    private ArticleEntity article;

    public RetrieveArticleRsp() {
    }

    public RetrieveArticleRsp(ArticleEntity article) {
        this.article = article;
    }

    public ArticleEntity getArticle() {
        return article;
    }

    public void setArticle(ArticleEntity article) {
        this.article = article;
    }
    
    
}
