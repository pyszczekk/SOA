package pl.edu.agh.soa.soap;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@Stateless
@WebService(name="securityDomain", portName = "SecurityPort", targetNamespace = "https://soap.soa.pl/soa/ws")
@SecurityDomain("my-security-domain")
@DeclareRoles({"MyRole"})
@WebContext(contextRoot = "soa", urlPattern = "/helloSecurity", authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL)
public class HelloSecurity {
    @RolesAllowed("MyRole")
    @WebMethod(action = "helloSecurity")
    @WebResult(name="hiSecurity")
    public String SecurityHello(){
            return "Hello security domain!";
    }

}
