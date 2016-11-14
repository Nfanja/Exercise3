package com.example.guestbook;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class GuestBookApplication extends Application {

/**
* Creates a root Restlet that will receive all incoming calls.
*/
    @Override
    public Restlet createInboundRoot() {
    // Create a router Restlet that routes each call to a
    //new instance of HelloWorldResource.
        Router router = new Router(getContext());

    // Defines only one route
        router.attachDefault(HelloWorldResource.class);
        router.attach("/rest/guestbook", GuestbookResource.class);
        router.attach("/rest/guestbook/{greeting}", GreetingResource.class);

        ObjectifyService.register(Guestbook.class);
        ObjectifyService.register(Greeting.class);

        return router;
    }
}
