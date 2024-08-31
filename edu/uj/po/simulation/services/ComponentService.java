package edu.uj.po.simulation.services;


import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownChip;
import edu.uj.po.simulation.interfaces.UnknownComponent;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.extensions.ComponentFactory;
import edu.uj.po.simulation.extensions.PinHeaderFactory;

import java.util.HashMap;

public class ComponentService {
    ComponentFactory componentFactory = new ComponentFactory();
    PinHeaderFactory pinHeaderFactory = new PinHeaderFactory();
    HashMap<Integer, Component> components = new HashMap<>();
    private final SimulationService simulationService;

    public ComponentService(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    public int createChip(int code) throws UnknownChip {
        Component component = componentFactory.createChip(code); // Pobieramy ID
        int componentId = getComponentId(component);
        components.put(componentId, component); // Przechowujemy komponent z jego unikalnym ID
        simulationService.addComponent(component);
        return componentId;
    }

    public int createOutputPinHeader(int size) {
        int componentId = pinHeaderFactory.createOutputPinHeader(size);
        components.compute(componentId, (_, component) -> component);
        return componentId;
    }

    public int createInputPinHeader(int size) {
        int componentId = pinHeaderFactory.createInputPinHeader(size);
        components.compute(componentId, (_, component) -> component);
        return componentId;
    }

    public void connect(int component1Id, int pin1, int component2Id, int pin2) throws UnknownComponent, UnknownPin, ShortCircuitException {
        Component component1 = components.get(component1Id);
        Component component2 = components.get(component2Id);

        if (component1 == null) {
            throw new UnknownComponent(component1Id);
        }
        if (component2 == null) {
            throw new UnknownComponent(component2Id);
        }

        Pin pin1Obj = component1.getPin(pin1);
        Pin pin2Obj = component2.getPin(pin2);

        if (pin1Obj == null) {
            throw new UnknownPin(component1Id, pin1);
        }
        if (pin2Obj == null) {
            throw new UnknownPin(component2Id, pin2);
        }

        // Sprawdzenie połączeń czerwonych (złe połączenia)
        if (pin1Obj.isOutput() && pin2Obj.isOutput()) {
            throw new ShortCircuitException();
        }

        // Sprawdzenie, czy pin1 jest już połączony z innym pinem (zielone połączenia)
        if (pin1Obj.isConnected() && !pin1Obj.isOutput() && pin2Obj.isOutput()) {
            if (pin1Obj.getConnectedPin().isOutput()) {
                throw new ShortCircuitException();
            }
        }

        // Sprawdzenie, czy pin2 jest połączony i czy jest wejściem oraz pin1 jest wyjściem
        if (pin2Obj.isConnected() && !pin2Obj.isOutput() && pin1Obj.isOutput()) {
            if (pin2Obj.getConnectedPin().isOutput()) {
                throw new ShortCircuitException();
            }
        }

        // Połączenie pinów (tylko jeśli poprzednie warunki nie zostały spełnione)
        pin1Obj.connect(pin2Obj);
    }

    public int getComponentId(Component component) {
        return component.getId();
    }
}
