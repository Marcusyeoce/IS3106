/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.MemberEntity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import ws.restful.model.CreateMemberReq;
import ws.restful.model.CreateMemberRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.MemberLoginRsp;

/**
 * REST Web Service
 *
 * @author Pham The Dzung
 */
@Path("Member")
public class MemberResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;
    
    /**
     * Creates a new instance of MemberResource
     */
    public MemberResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
    }

    @Path("memberLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response memberLogin(@QueryParam("username") String username,
                                @QueryParam("password") String password) {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.memberLogin(username, password);
            System.out.println("*********** MemberResource.memberLogin(): Member" + member.getUsername() + " login remotely via ws");
            
            member.setPassword(null);
            member.setSalt(null);
            
            return Response.status(Status.OK).entity(new MemberLoginRsp(member)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("registerMember")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(CreateMemberReq createMemberReq) {
        if (createMemberReq != null) {
            try {
                System.out.println("********** MemberResource.createMember(): Someone registers remotely via ws");
                
                CreateMemberRsp createMemberRsp = new CreateMemberRsp(memberEntitySessionBeanLocal.createNewMember(createMemberReq.getMember()));
                
                return Response.status(Response.Status.OK).entity(createMemberRsp).build();
            } catch (InputDataValidationException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid register new member request");

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

}
