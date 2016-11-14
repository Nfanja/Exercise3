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
public class GreetingResource extends ServerResource {

    String guestbookName;
    String greetingId;
    JAXBContext jaxbContext;
    Marshaller jaxbMarshaller;

    @Override
    public void doInit(){
        this.guestbookName = "default";
        this.greetingId = getAttribute("greeting");

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
        String finalString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        Key<Guestbook> theBook = Key.create(Guestbook.class, this.guestbookName);

        Greeting greeting = ObjectifyService.ofy()
            .load()
            .type(Greeting.class)
            .parent(theBook)
            .id(Long.parseLong(this.greetingId, 10))
            .now();

        try {
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(greeting, sw);
            String xmlString = sw.toString();
            finalString += xmlString + "\n";
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return finalString;
    }

}
