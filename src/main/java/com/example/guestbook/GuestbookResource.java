package com.example.guestbook;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.example.guestbook.Greeting;
import com.example.guestbook.Guestbook;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.List;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *  * Resource which has only one representation.
 *   *
 *    */
public class GuestbookResource extends ServerResource {

    String guestbookName;
    JAXBContext jaxbContext;
    Marshaller jaxbMarshaller;

    @Override
    public void doInit(){
        this.guestbookName = "default";

        try {
            this.jaxbContext = JAXBContext.newInstance(Greeting.class);
            this.jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Get("xml")
    public String toString(){
        String finalString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<guestbook>\n";
        Key<Guestbook> theBook = Key.create(Guestbook.class, this.guestbookName);

        //LOL
//        Greeting greetingAdd = new Greeting(this.guestbookName, "Hello there");
//        ObjectifyService.ofy().save().entity(greetingAdd).now();

        List<Greeting> greetings = ObjectifyService.ofy()
            .load()
            .type(Greeting.class)
            .ancestor(theBook)
            .order("-date")
            .list();

        if (greetings.isEmpty()) {
            return "Guestbook \"" + this.guestbookName + "\" is empty";
        } else {
            for (Greeting greeting : greetings) {
                try {
                    StringWriter sw = new StringWriter();
                    jaxbMarshaller.marshal(greeting, sw);
                    String xmlString = sw.toString();
                    finalString += xmlString + "\n";
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        }
        return finalString + "</guestbook>";
    }

}
