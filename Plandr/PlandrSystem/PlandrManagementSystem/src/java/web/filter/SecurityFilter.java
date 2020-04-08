package web.filter;

import entity.StaffEntity;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.enumeration.AccessRightEnum;

/**
 *
 * @author oimun
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    
    private static final String CONTEXT_ROOT = "/PlandrManagementSystem";
    
    FilterConfig filterConfig;
    
    public SecurityFilter() {
    }    
    
     public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();        
        
         if(httpSession.getAttribute("isLogin") == null)
        {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean)httpSession.getAttribute("isLogin");
        
        if(!excludeLoginCheck(requestServletPath))
        {
            if(isLogin == true)
            {
                StaffEntity currentStaffEntity = (StaffEntity)httpSession.getAttribute("currentStaffEntity");
                
                if(checkAccessRight(requestServletPath, currentStaffEntity.getAccessRightEnum()))
                {
                    chain.doFilter(request, response);
                }
                else
                {
                    httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
                }
            }
            else
            {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
            }
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {        
    }
    
    private Boolean excludeLoginCheck(String path)
    {
        if(path.equals("/index.xhtml") ||
            path.equals("/accessRightError.xhtml") ||
            path.startsWith("/javax.faces.resource"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
     private Boolean checkAccessRight(String path, AccessRightEnum accessRight)
    {        
        if(accessRight.equals(AccessRightEnum.ADMIN))
        {            
            if(path.equals("/systemAdministration/staffManagement.xhtml"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(accessRight.equals(AccessRightEnum.EMPLOYEE))
        {
            if(path.equals("/employeeOperation/companyManagement.xhtml") ||
                    path.equals("/employeeOperation/articleManagement.xhtml") ||
                    path.equals("/employeeOperation/attractionManagement.xhtml") ||
                    path.equals("/employeeOperation/createNewAttraction.xhtml"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        return false;
    }
    
}
