/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ArticleEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.ArticleNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author oimun
 */
@Local
public interface ArticleEntitySessionBeanLocal {

    public Long createNewArticle(Long staffId, ArticleEntity newArticleEntity) throws StaffNotFoundException, InputDataValidationException;

    public ArticleEntity retrieveArticleByArticleId(Long atricleId) throws ArticleNotFoundException;

    public List<ArticleEntity> retrieveAllArticles();

    public List<ArticleEntity> retrieveAllArticlesByStaffId(Long staffId);

    public void updateArticle(ArticleEntity article) throws ArticleNotFoundException, StaffNotFoundException, InputDataValidationException;

    public void deleteArticle(Long articleId) throws ArticleNotFoundException, StaffNotFoundException;
    
}
