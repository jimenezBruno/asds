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
      
        double h = (4.0 - 1.0) / 1000.0; // Intervalo dividido en 1000 trapecios
        double fx = Math.pow(x, 3) + 2 * x + Math.exp(x + 2);
        return h * fx;
    }
}

