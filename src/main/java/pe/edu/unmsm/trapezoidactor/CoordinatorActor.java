package pe.edu.unmsm.trapezoidactor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class CoordinatorActor extends AbstractActor {
    private final ActorRef[] trapezoidActors;
    private final double intervalStart = 1.0;
    private final double intervalEnd = 4.0;
    private final int numActors = 10;
    private final int numTrapezoids = 1000;

    private double integralResult = 0.0;
    private int trapecesCalculados = 0;

    public CoordinatorActor() {
        trapezoidActors = new ActorRef[numActors];
        for (int i = 0; i < numActors; i++) {
            trapezoidActors[i] = getContext().actorOf(Props.create(TrapezoidActor.class));
            System.out.println("Actor creado: " + trapezoidActors[i]);
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("start", msg -> {
                    // Iniciar el cálculo distribuyendo el trabajo entre los actores del trapecio
                    System.out.println("Iniciando cálculo...");
                    calculateIntegral();
                })
                .match(Double.class, result -> {
                    // Recibir el resultado de un actor del trapecio y acumularlo
                    integralResult += result;
                    trapecesCalculados++;

                    // Verificar si todos los trapecios han sido calculados
                    if (trapecesCalculados == numTrapezoids) {
                        System.out.println("Resultado de la integral: " + integralResult);
                        getContext().getSystem().terminate();
                    }
                })
                .build();
    }

    private void calculateIntegral() {
        double intervalSize = (intervalEnd - intervalStart) / numTrapezoids;

        for (int i = 0; i < numTrapezoids; i++) {
            double x = intervalStart + i * intervalSize;
            int actorIndex = i % numActors;
            trapezoidActors[actorIndex].tell(x, getSelf());
        }
    }
}
