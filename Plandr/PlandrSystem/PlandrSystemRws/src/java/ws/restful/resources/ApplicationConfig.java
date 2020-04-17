/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Pham The Dzung
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.resources.ArticleResource.class);
        resources.add(ws.restful.resources.AttractionResource.class);
        resources.add(ws.restful.resources.BookingResource.class);
        resources.add(ws.restful.resources.MemberResource.class);
        resources.add(ws.restful.resources.PromotionResource.class);
        resources.add(ws.restful.resources.ReviewResource.class);
        resources.add(ws.restful.resources.TagResource.class);
    }
    
}
