
package pl.edu.agh.soa.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pl.edu.agh.soa.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SecurityHello_QNAME = new QName("https://soap.soa.pl/soa/ws", "SecurityHello");
    private final static QName _SecurityHelloResponse_QNAME = new QName("https://soap.soa.pl/soa/ws", "SecurityHelloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pl.edu.agh.soa.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SecurityHello }
     * 
     */
    public SecurityHello createSecurityHello() {
        return new SecurityHello();
    }

    /**
     * Create an instance of {@link SecurityHelloResponse }
     * 
     */
    public SecurityHelloResponse createSecurityHelloResponse() {
        return new SecurityHelloResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SecurityHello }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SecurityHello }{@code >}
     */
    @XmlElementDecl(namespace = "https://soap.soa.pl/soa/ws", name = "SecurityHello")
    public JAXBElement<SecurityHello> createSecurityHello(SecurityHello value) {
        return new JAXBElement<SecurityHello>(_SecurityHello_QNAME, SecurityHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SecurityHelloResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SecurityHelloResponse }{@code >}
     */
    @XmlElementDecl(namespace = "https://soap.soa.pl/soa/ws", name = "SecurityHelloResponse")
    public JAXBElement<SecurityHelloResponse> createSecurityHelloResponse(SecurityHelloResponse value) {
        return new JAXBElement<SecurityHelloResponse>(_SecurityHelloResponse_QNAME, SecurityHelloResponse.class, null, value);
    }

}
