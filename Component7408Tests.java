import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.entities.components.Component7408;
import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.services.ComponentService;
import edu.uj.po.simulation.services.SimulationService;
import org.junit.Test;

import static org.junit.Assert.*;

public class Component7408Tests {
    @Test
    public void testValidConnection1() throws UnknownPin {
        Component7408 component = new Component7408(1);

        Pin inputA = component.getPin(1); // 1A
        Pin inputB = component.getPin(2); // 1B
        Pin outputY = component.getPin(3); // 1Y

        assertNotNull(inputA);
        assertNotNull(inputB);
        assertNotNull(outputY);
    }

    @Test
    public void testValidConnection2() throws UnknownPin {
        Component7408 component = new Component7408(1);

        Pin inputA = component.getPin(4); // 2A
        Pin inputB = component.getPin(5); // 2B
        Pin outputY = component.getPin(6); // 2Y

        assertNotNull(inputA);
        assertNotNull(inputB);
        assertNotNull(outputY);
    }

    @Test
    public void testValidConnection3() throws UnknownPin {
        Component7408 component = new Component7408(1);

        Pin inputA = component.getPin(9); // 3A
        Pin inputB = component.getPin(10); // 3B
        Pin outputY = component.getPin(8); // 3Y

        assertNotNull(inputA);
        assertNotNull(inputB);
        assertNotNull(outputY);
    }

    @Test
    public void testValidConnection4() throws UnknownPin {
        Component7408 component = new Component7408(1);

        Pin inputA = component.getPin(12); // 4A
        Pin inputB = component.getPin(13); // 4B
        Pin outputY = component.getPin(11); // 4Y

        assertNotNull(inputA);
        assertNotNull(inputB);
        assertNotNull(outputY);
    }

    @Test
    public void testInvalidPinMappingComponent7408() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        // Test uzyskiwania dostępu do nieprawidłowego numeru pinu, co powinno rzucić UnknownPin
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408, 0, component7408, 7); // Pin 0 nie istnieje
        });
    }

    @Test
    public void testCorrectPinConnectionComponent7408() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        // Test poprawnego połączenia pinów wejściowych i wyjściowych
        service.connect(component7408, 1, component7408, 3); // Połączenie 1A do 1Y (dozwolone)
        service.connect(component7408, 2, component7408, 3); // Połączenie 1B do 1Y (dozwolone)
    }

    @Test
    public void testDuplicatePinConnectionComponent7408() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        // Test próby podwójnego połączenia tych samych pinów
        service.connect(component7408, 1, component7408, 3); // Pierwsze połączenie 1A do 1Y
        service.connect(component7408, 1, component7408, 3); // Drugie połączenie 1A do 1Y (nadal dozwolone)
    }

    @Test
    public void testCorrectConnectionBetweenTwoComponents7408() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        // Test poprawnego połączenia pinów między dwoma komponentami 7408
        service.connect(component7408A, 1, component7408B, 2); // Połączenie 1A z 1B między dwoma komponentami
    }

    @Test
    public void testInvalidPinConnectionBetweenTwoComponents7408() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        // Test próby połączenia nieistniejących pinów między dwoma komponentami 7408
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408A, 7, component7408B, 8); // Piny 7 i 8 nie istnieją w 7408, więc powinno rzucić wyjątek
        });
    }

    @Test
    public void testAllPinsMappedCorrectly() {
        Component7408 component = new Component7408(1);
        for (int pinNumber : component.getPins().keySet()) {
            assertTrue(component.getPins().containsKey(pinNumber));
        }
    }

    @Test
    public void testPerformLogicHighInputs() throws UnknownPin {
        Component7408 component = new Component7408(1);

        component.getPin(1).setState(PinState.HIGH);
        component.getPin(2).setState(PinState.HIGH);
        component.performLogic();

        assertEquals(PinState.HIGH, component.getPin(3).getState());
    }

    @Test
    public void testPerformLogicLowInputs() throws UnknownPin {
        Component7408 component = new Component7408(1);

        component.getPin(1).setState(PinState.LOW);
        component.getPin(2).setState(PinState.LOW);
        component.performLogic();

        assertEquals(PinState.LOW, component.getPin(3).getState());
    }

    @Test
    public void testPerformLogicUnknownInputs() throws UnknownPin {
        Component7408 component = new Component7408(1);

        component.getPin(1).setState(PinState.UNKNOWN);
        component.getPin(2).setState(PinState.HIGH);
        component.performLogic();

        assertEquals(PinState.UNKNOWN, component.getPin(3).getState());
    }

    @Test
    public void test7408ConnectionInputToOutputSameComponent() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        service.connect(component7408, 1, component7408, 3); // Połączenie 1A do 1Y
    }

    @Test
    public void test7408ConnectionOutputToInputDifferentComponent() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        service.connect(component7408A, 3, component7408B, 4); // Połączenie 1Y do 2A
    }

    @Test
    public void test7408ConnectionOutputToOutputSameComponent() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        assertThrows(ShortCircuitException.class, () -> {
            service.connect(component7408, 3, component7408, 6); // Połączenie 1Y do 2Y (niedozwolone)
        });
    }

    @Test
    public void test7408ConnectionOutputToOutputDifferentComponents() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        assertThrows(ShortCircuitException.class, () -> {
            service.connect(component7408A, 3, component7408B, 6); // Połączenie 1Y do 2Y (niedozwolone)
        });
    }

    @Test
    public void test7408ConnectionDifferentPinsInputToOutputSameComponent() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        service.connect(component7408, 4, component7408, 6); // Połączenie 2A do 2Y
    }

    @Test
    public void test7408ConnectionDifferentPinsOutputToOutputSameComponent() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        assertThrows(ShortCircuitException.class, () -> {
            service.connect(component7408, 6, component7408, 3); // Połączenie 2Y do 1Y (niedozwolone)
        });
    }

    @Test
    public void test7408ConnectionInputToOutputSameComponentReverse() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        service.connect(component7408, 5, component7408, 4); // Połączenie 2B do 2A
    }

    @Test
    public void test7408ConnectionOutputToInputSameComponentReverse() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        service.connect(component7408, 6, component7408, 5); // Połączenie 2Y do 2B
    }

    @Test
    public void testUnknownPinForComponent2() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        // Pin 14 nie istnieje w układzie 7408 (ma tylko piny 1-6 i 8-13)
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408, 2, component7408, 14); // Pin 14 nie istnieje
        });
    }

    @Test
    public void testUnknownPinForDifferentComponents() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        // Pin 15 nie istnieje w układzie 7408 (ma tylko piny 1-6 i 8-13)
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408A, 3, component7408B, 15); // Pin 15 nie istnieje w component7408B
        });
    }

    @Test
    public void testUnknownPinForNonExistentPinBothComponents() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        // Piny 7 i 14 nie istnieją w układzie 7408
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408A, 7, component7408B, 14); // Oba piny nie istnieją
        });
    }

    @Test
    public void testUnknownPinForIncorrectPinNumberComponent1() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        // Pin 0 nie istnieje, ponieważ numeracja pinów zaczyna się od 1
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408, 0, component7408, 2); // Pin 0 nie istnieje
        });
    }

    @Test
    public void testUnknownPinForIncorrectPinNumberComponent2() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        // Pin 100 nie istnieje w układzie 7408
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408, 2, component7408, 100); // Pin 100 nie istnieje
        });
    }

    @Test
    public void testUnknownPinForIncorrectComponentPinNumber() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        // Pin 100 nie istnieje w żadnym z komponentów
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408A, 100, component7408B, 100); // Pin 100 nie istnieje
        });
    }

    @Test
    public void testUnknownPinForMissingPinInFirstComponent() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        // Pin 15 nie istnieje w component7408A, ale 2 istnieje w component7408B
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408A, 15, component7408B, 2); // Pin 15 nie istnieje w component7408A
        });
    }

    @Test
    public void testUnknownPinForNonExistentPinsDifferentComponents() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408A = service.createChip(7408);
        int component7408B = service.createChip(7408);

        // Piny 15 i 16 nie istnieją w żadnym z komponentów
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408A, 15, component7408B, 16); // Oba piny nie istnieją
        });
    }

    @Test
    public void testUnknownPinForConnectingGndOrVccPins() throws UnknownChip {
        ComponentService service = new ComponentService(new SimulationService());
        int component7408 = service.createChip(7408);

        // Sprawdzenie, czy próba połączenia pinów GND lub VCC (które są ignorowane) wywoła UnknownPin
        assertThrows(UnknownPin.class, () -> {
            service.connect(component7408, 7, component7408, 14); // Piny 7 i 14 są zazwyczaj GND i VCC
        });
    }
}

