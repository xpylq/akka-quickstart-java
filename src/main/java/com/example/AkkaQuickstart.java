package com.example;

import akka.actor.typed.ActorSystem;

import java.io.IOException;

public class AkkaQuickstart {

    public static void main(String[] args) {
        /**
         * ActorSystem是akka初始化的入口，通常一个应用只创建一个ActorSystem
         * ActorSystem的入参通常一个名称，和一个守护actor
         * 典型的应用程序的初始化是在守护actor中完成的
         */
        final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");
        //#actor-system

        //#main-send-messages
        greeterMain.tell(new GreeterMain.SayHello("Charles"));
        //#main-send-messages

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            greeterMain.terminate();
        }
    }
}
