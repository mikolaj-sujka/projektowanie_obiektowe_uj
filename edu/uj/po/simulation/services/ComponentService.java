package edu.uj.po.simulation.services;


import edu.uj.po.simulation.entities.PinHeaderComponent;
import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownChip;
import edu.uj.po.simulation.interfaces.UnknownComponent;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.extensions.ComponentFactory;
import edu.uj.po.simulation.extensions.PinHeaderFactory;

import java.util.HashMap;
import java.util.Set;

public class ComponentService {
    ComponentFactory componentFactory = new ComponentFactory();
    PinHeaderFactory pinHeaderFactory = new PinHeaderFactory();
    HashMap<Integer, Component> components = new HashMap<>();
    private final SimulationService simulationService;

    public ComponentService(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    public int createChip(int code) throws UnknownChip {
        Component component = componentFactory.createChip(code);
        int componentId = getComponentId(component);
        components.put(componentId, component);
        simulationService.addComponent(component);
        return componentId;
    }

    public int createOutputPinHeader(int size) {
        Component pinHeader = pinHeaderFactory.createOutputPinHeader(size);
        components.put(getComponentId(pinHeader), pinHeader);
        simulationService.addComponent(pinHeader);
        return pinHeader.getId();
    }

    public int createInputPinHeader(int size) {
        Component pinHeader = pinHeaderFactory.createInputPinHeader(size);
        components.put(getComponentId(pinHeader), pinHeader);
        simulationService.addComponent(pinHeader);
        return pinHeader.getId();
    }

    public void connect(int component1Id, int pin1, int component2Id, int pin2) throws UnknownComponent, UnknownPin, ShortCircuitException {
        Component component1 = components.get(component1Id);

        if (component1 == null) {
            throw new UnknownComponent(component1Id);
        }

        Pin pin1Obj = component1.getPin(pin1);

        Component component2 = components.get(component2Id);
        if (component2 == null) {
            throw new UnknownComponent(component2Id);
        }

        Pin pin2Obj = component2.getPin(pin2);

        component1.validateConnection(pin1Obj, pin2Obj, component1.getId(), component2.getId());
        component2.validateConnection(pin2Obj, pin1Obj, component1.getId(), component2.getId());

        if (pin1Obj.isConnected() && pin1Obj.getConnectedPins().contains(pin2Obj)) {
            return;
        }


        if (pin1Obj.isOutput() && pin2Obj.isOutput()) {
            throw new ShortCircuitException();
        }

        if (pin1Obj.isConnected()) {
            Set<Pin> connectedPins = pin1Obj.getConnectedPins();
            boolean hasConnectedOutput = connectedPins.stream().anyMatch(Pin::isOutput);

            if (hasConnectedOutput && pin2Obj.isOutput()) {
                throw new ShortCircuitException();
            }
        }

        if (pin2Obj.isConnected()) {
            Set<Pin> connectedPins = pin2Obj.getConnectedPins();

            boolean hasConnectedOutput = connectedPins.stream().anyMatch(Pin::isOutput);

            if (hasConnectedOutput && pin1Obj.isOutput()) {
                throw new ShortCircuitException();
            }
        }

        if (isUserInputHeader(component1Id) && pin1Obj.isOutput() && !pin2Obj.isOutput()) {
            throw new ShortCircuitException();
        }
        if (isUserInputHeader(component2Id) && pin2Obj.isOutput() && !pin1Obj.isOutput()) {
            throw new ShortCircuitException();
        }

        pin1Obj.connect(pin2Obj);
    }

    public int getComponentId(Component component) {
        return component.getId();
    }

    private boolean isUserInputHeader(int componentId) {
        Component component = components.get(componentId);
        return component instanceof PinHeaderComponent && !((PinHeaderComponent) component).isOutput();
    }
}
