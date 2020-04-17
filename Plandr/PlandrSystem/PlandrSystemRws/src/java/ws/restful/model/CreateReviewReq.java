/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.ReviewEntity;

/**
 *
 * @author Pham The Dzung
 */
public class CreateReviewReq {
    private String username;
    private String password;
    private ReviewEntity review;
    private Long attractionId;

    public CreateReviewReq() {
    }

    public CreateReviewReq(String username, String password, ReviewEntity review, Long attractionId) {
        this.username = username;
        this.password = password;
        this.review = review;
        this.attractionId = attractionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ReviewEntity getReview() {
        return review;
    }

    public void setReview(ReviewEntity review) {
        this.review = review;
    }

    public Long getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
    }
    
    
}
