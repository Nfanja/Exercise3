package firstSteps;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 *  * Resource which has only one representation.
 *   *
 *    */
public class HelloWorldResource extends ServerResource {

    @Get
    public String represent() {
        String guestbook_xml = ""
            + "<guestbook\n"
            + "xmlns=\"http://example.com\"\n"
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "xsi:schemaLocation=\"http://example.com guestbook.xsd\">\n"
            + "    <greeting>\n"
            + "        <user>Nfan</user>\n"
            + "        <message>Hello World!</message>\n"
            + "    </greeting>\n"
            + "</guestbook>\n";

        // return "hello, world (from the cloud!)";
        return guestbook_xml;
    }

}
