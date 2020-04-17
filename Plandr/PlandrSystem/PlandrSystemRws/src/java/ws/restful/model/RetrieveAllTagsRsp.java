/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.TagEntity;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class RetrieveAllTagsRsp {
    private List<TagEntity> tags;

    public RetrieveAllTagsRsp() {
    }

    public RetrieveAllTagsRsp(List<TagEntity> tags) {
        this.tags = tags;
    }

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }
    
    
}
