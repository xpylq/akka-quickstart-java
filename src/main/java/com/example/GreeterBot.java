package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

/**
 *  receives the reply from the Greeter and sends a number of additional greeting messages and collect the replies until a given max number of messages have been reached.
 */
public class GreeterBot extends AbstractBehavior<Greeter.Greeted> {

    public static Behavior<Greeter.Greeted> create(int max) {
        return Behaviors.setup(context -> new GreeterBot(context, max));
    }

    private final int max;
    private int greetingCounter;

    private GreeterBot(ActorContext<Greeter.Greeted> context, int max) {
        super(context);
        this.max = max;
    }

    @Override
    public Receive<Greeter.Greeted> createReceive() {
        return newReceiveBuilder().onMessage(Greeter.Greeted.class, this::onGreeted).build();
    }

    private Behavior<Greeter.Greeted> onGreeted(Greeter.Greeted message) {
        greetingCounter++;
        getContext().getLog().info("Greeting {} for {}", greetingCounter, message.whom);
        if (greetingCounter == max) {
            return Behaviors.stopped();
        } else {
            message.from.tell(new Greeter.Greet(message.whom, getContext().getSelf()));
            return this;
        }
    }
}
