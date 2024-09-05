package edu.uj.po.simulation.extensions;


import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.UnknownChip;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.components.*;

import java.util.HashMap;
import java.util.Map;

public class ComponentFactory implements IComponentFactory{

    public Component createChip(int code, int componentId) throws UnknownChip {
        Component component;
        switch (code) {
            case 7400: // 4x NAND
                component = new Component7400(componentId);
                break;
            case 7402: // 4x NOR
                component = new Component7402(componentId);
                break;
            case 7404: // 6x NOT
                component = new Component7404(componentId);
                break;
            case 7408: // 4x AND
                component = new Component7408(componentId);
                break;
            case 7410: // 3x NAND
                component = new Component7410(componentId);
                break;
            case 7411: // 3x AND
                component = new Component7411(componentId);
                break;
            case 7420: // 2x NAND
                component = new Component7420(componentId);
                break;
            case 7431: // 6 elementów opóźniających
                component = new Component7431(componentId);
                break;
            case 7432: // 4x OR
                component = new Component7432(componentId);
                break;
            case 7434: // 6 buforów
                component = new Component7434(componentId);
                break;
            case 7442: // dekoder BCD na kod 1 z 10
                component = new Component7442(componentId);
                break;
            case 7444: // dekoder kodu GRAY'a na 1 z 10
                component = new Component7444(componentId);
                break;
            case 7482: // pełny sumator dwu-bitowy
                component = new Component7482(componentId);
                break;
            case 74138: // dekoder/demultiplekser 3 z 8 linii
                component = new Component74138(componentId);
                break;
            case 74152: // multiplekser 8-kanałowy
                component = new Component74152(componentId);
                break;
            default:
                throw new UnknownChip();
        }
        component.setId(componentId);
        return component;
    }

    public Component createOutputPinHeader(int size, int componentId) {
        Component pinHeader = new Component(componentId, createPins(size, true), true);
        pinHeader.setId(componentId);
        return pinHeader;
    }

    public Component createInputPinHeader(int size, int componentId) {
        Component pinHeader = new Component(componentId, createPins(size, false), true);
        pinHeader.setId(componentId);
        return pinHeader;
    }

    private static Map<Integer, Pin> createPins(int size, boolean isOutput) {
        Map<Integer, Pin> pins = new HashMap<>();

        for (int i = 1; i <= size; i++) {
            pins.put(i, new Pin(i, isOutput));
        }

        return pins;
    }
}
