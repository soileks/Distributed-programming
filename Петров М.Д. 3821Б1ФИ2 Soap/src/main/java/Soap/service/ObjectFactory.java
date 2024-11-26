
package Soap.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the Soap.service package. 
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

    private final static QName _DrawLine_QNAME = new QName("http://Soap/", "drawLine");
    private final static QName _DrawLineResponse_QNAME = new QName("http://Soap/", "drawLineResponse");
    private final static QName _GetGameState_QNAME = new QName("http://Soap/", "getGameState");
    private final static QName _GetGameStateResponse_QNAME = new QName("http://Soap/", "getGameStateResponse");
    private final static QName _GetID_QNAME = new QName("http://Soap/", "getID");
    private final static QName _GetIDResponse_QNAME = new QName("http://Soap/", "getIDResponse");
    private final static QName _GetScore_QNAME = new QName("http://Soap/", "getScore");
    private final static QName _GetScoreResponse_QNAME = new QName("http://Soap/", "getScoreResponse");
    private final static QName _IsGameOver_QNAME = new QName("http://Soap/", "isGameOver");
    private final static QName _IsGameOverResponse_QNAME = new QName("http://Soap/", "isGameOverResponse");
    private final static QName _IsItMyMove_QNAME = new QName("http://Soap/", "isItMyMove");
    private final static QName _IsItMyMoveResponse_QNAME = new QName("http://Soap/", "isItMyMoveResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: Soap.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DrawLine }
     * 
     */
    public DrawLine createDrawLine() {
        return new DrawLine();
    }

    /**
     * Create an instance of {@link DrawLineResponse }
     * 
     */
    public DrawLineResponse createDrawLineResponse() {
        return new DrawLineResponse();
    }

    /**
     * Create an instance of {@link GetGameState }
     * 
     */
    public GetGameState createGetGameState() {
        return new GetGameState();
    }

    /**
     * Create an instance of {@link GetGameStateResponse }
     * 
     */
    public GetGameStateResponse createGetGameStateResponse() {
        return new GetGameStateResponse();
    }

    /**
     * Create an instance of {@link GetID }
     * 
     */
    public GetID createGetID() {
        return new GetID();
    }

    /**
     * Create an instance of {@link GetIDResponse }
     * 
     */
    public GetIDResponse createGetIDResponse() {
        return new GetIDResponse();
    }

    /**
     * Create an instance of {@link GetScore }
     * 
     */
    public GetScore createGetScore() {
        return new GetScore();
    }

    /**
     * Create an instance of {@link GetScoreResponse }
     * 
     */
    public GetScoreResponse createGetScoreResponse() {
        return new GetScoreResponse();
    }

    /**
     * Create an instance of {@link IsGameOver }
     * 
     */
    public IsGameOver createIsGameOver() {
        return new IsGameOver();
    }

    /**
     * Create an instance of {@link IsGameOverResponse }
     * 
     */
    public IsGameOverResponse createIsGameOverResponse() {
        return new IsGameOverResponse();
    }

    /**
     * Create an instance of {@link IsItMyMove }
     * 
     */
    public IsItMyMove createIsItMyMove() {
        return new IsItMyMove();
    }

    /**
     * Create an instance of {@link IsItMyMoveResponse }
     * 
     */
    public IsItMyMoveResponse createIsItMyMoveResponse() {
        return new IsItMyMoveResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DrawLine }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DrawLine }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "drawLine")
    public JAXBElement<DrawLine> createDrawLine(DrawLine value) {
        return new JAXBElement<DrawLine>(_DrawLine_QNAME, DrawLine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DrawLineResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DrawLineResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "drawLineResponse")
    public JAXBElement<DrawLineResponse> createDrawLineResponse(DrawLineResponse value) {
        return new JAXBElement<DrawLineResponse>(_DrawLineResponse_QNAME, DrawLineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGameState }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetGameState }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "getGameState")
    public JAXBElement<GetGameState> createGetGameState(GetGameState value) {
        return new JAXBElement<GetGameState>(_GetGameState_QNAME, GetGameState.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGameStateResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetGameStateResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "getGameStateResponse")
    public JAXBElement<GetGameStateResponse> createGetGameStateResponse(GetGameStateResponse value) {
        return new JAXBElement<GetGameStateResponse>(_GetGameStateResponse_QNAME, GetGameStateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetID }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetID }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "getID")
    public JAXBElement<GetID> createGetID(GetID value) {
        return new JAXBElement<GetID>(_GetID_QNAME, GetID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIDResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetIDResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "getIDResponse")
    public JAXBElement<GetIDResponse> createGetIDResponse(GetIDResponse value) {
        return new JAXBElement<GetIDResponse>(_GetIDResponse_QNAME, GetIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScore }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetScore }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "getScore")
    public JAXBElement<GetScore> createGetScore(GetScore value) {
        return new JAXBElement<GetScore>(_GetScore_QNAME, GetScore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScoreResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetScoreResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "getScoreResponse")
    public JAXBElement<GetScoreResponse> createGetScoreResponse(GetScoreResponse value) {
        return new JAXBElement<GetScoreResponse>(_GetScoreResponse_QNAME, GetScoreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsGameOver }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IsGameOver }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "isGameOver")
    public JAXBElement<IsGameOver> createIsGameOver(IsGameOver value) {
        return new JAXBElement<IsGameOver>(_IsGameOver_QNAME, IsGameOver.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsGameOverResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IsGameOverResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "isGameOverResponse")
    public JAXBElement<IsGameOverResponse> createIsGameOverResponse(IsGameOverResponse value) {
        return new JAXBElement<IsGameOverResponse>(_IsGameOverResponse_QNAME, IsGameOverResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsItMyMove }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IsItMyMove }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "isItMyMove")
    public JAXBElement<IsItMyMove> createIsItMyMove(IsItMyMove value) {
        return new JAXBElement<IsItMyMove>(_IsItMyMove_QNAME, IsItMyMove.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsItMyMoveResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IsItMyMoveResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://Soap/", name = "isItMyMoveResponse")
    public JAXBElement<IsItMyMoveResponse> createIsItMyMoveResponse(IsItMyMoveResponse value) {
        return new JAXBElement<IsItMyMoveResponse>(_IsItMyMoveResponse_QNAME, IsItMyMoveResponse.class, null, value);
    }

}
