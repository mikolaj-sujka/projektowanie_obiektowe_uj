import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.services.ComponentService;
import edu.uj.po.simulation.services.SimulationService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class Component7420Tests {
    @Test
    public void test7420BasicConnection() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7420 = service.createChip(7420);

        // Test podstawowego połączenia wewnątrz układu 7420
        service.connect(component7420, 1, component7420, 2); // Połączenie 1A do 1B
        service.connect(component7420, 4, component7420, 5); // Połączenie 1C do 1D

        service.connect(component7420, 9, component7420, 10); // Połączenie 2A do 2B
        service.connect(component7420, 12, component7420, 13); // Połączenie 2C do 2D
    }

    @Test
    public void test7420OutputToInputSameComponent() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7420 = service.createChip(7420);

        // Test połączenia wyjścia jednej bramki NAND do wejścia innej w tym samym chipie
        service.connect(component7420, 6, component7420, 9); // Połączenie 1Y do 2A (dozwolone)
    }

    @Test
    public void test7420OutputToOutputSameComponent() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7420 = service.createChip(7420);

        // Test połączenia wyjścia do wyjścia w tym samym chipie - powinno rzucić wyjątek
        assertThrows(ShortCircuitException.class, () -> {
            service.connect(component7420, 6, component7420, 8); // Połączenie 1Y do 2Y (niedozwolone)
        });
    }

    @Test
    public void test7420CrossComponentConnection() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7420A = service.createChip(7420);
        int component7420B = service.createChip(7420);

        // Test połączenia między dwoma różnymi chipami 7420
        service.connect(component7420A, 6, component7420B, 9); // Połączenie 1Y z komponentu A do 2A w komponencie B (dozwolone)
    }

    @Test
    public void test7420InvalidPinConnection() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7420 = service.createChip(7420);

        // Test połączenia z nieistniejącym pinem - powinno rzucić wyjątek UnknownPin
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7420, 1, component7420, 7); // Pin 7 nie istnieje, powinno rzucić wyjątek
        });
    }
}
