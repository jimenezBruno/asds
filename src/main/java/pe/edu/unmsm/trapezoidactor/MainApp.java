package pe.edu.unmsm.trapezoidactor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainApp {
    public static void main(String[] args) {
        // Crear el sistema de actores
        ActorSystem system = ActorSystem.create("IntegralSystem");

        // Crear el actor coordinador
        ActorRef coordinator = system.actorOf(Props.create(CoordinatorActor.class), "coordinator");

        // Iniciar el cálculo
        coordinator.tell("start", ActorRef.noSender());
    }
}

