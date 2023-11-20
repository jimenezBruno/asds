package pe.edu.unmsm.trapezoidactor;

import akka.actor.AbstractActor;

public class TrapezoidActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Double.class, x -> {
                    // Calcular la contribución del trapecio para el valor de x y enviar el resultado al remitente
                    double result = calculateTrapezoid(x);
                    getSender().tell(result, getSelf());
                })
                .build();
    }

    private double calculateTrapezoid(double x) {
        // Implementa la fórmula del trapecio para calcular la contribución del trapecio en el punto x
        // Puedes usar la función f(x) = x^3 + 2x + Math.exp(x + 2)
        double fx = Math.pow(x, 3) + 2 * x + Math.exp(x + 2);
        return fx;
    }
}


