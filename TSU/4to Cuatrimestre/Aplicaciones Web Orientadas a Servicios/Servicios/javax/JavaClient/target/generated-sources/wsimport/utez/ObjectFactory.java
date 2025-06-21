
package utez;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the utez package. 
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

    private final static QName _GuessNumber_QNAME = new QName("utez", "guessNumber");
    private final static QName _GuessNumberResponse_QNAME = new QName("utez", "guessNumberResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: utez
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GuessNumber }
     * 
     */
    public GuessNumber createGuessNumber() {
        return new GuessNumber();
    }

    /**
     * Create an instance of {@link GuessNumberResponse }
     * 
     */
    public GuessNumberResponse createGuessNumberResponse() {
        return new GuessNumberResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuessNumber }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GuessNumber }{@code >}
     */
    @XmlElementDecl(namespace = "utez", name = "guessNumber")
    public JAXBElement<GuessNumber> createGuessNumber(GuessNumber value) {
        return new JAXBElement<GuessNumber>(_GuessNumber_QNAME, GuessNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuessNumberResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GuessNumberResponse }{@code >}
     */
    @XmlElementDecl(namespace = "utez", name = "guessNumberResponse")
    public JAXBElement<GuessNumberResponse> createGuessNumberResponse(GuessNumberResponse value) {
        return new JAXBElement<GuessNumberResponse>(_GuessNumberResponse_QNAME, GuessNumberResponse.class, null, value);
    }

}
