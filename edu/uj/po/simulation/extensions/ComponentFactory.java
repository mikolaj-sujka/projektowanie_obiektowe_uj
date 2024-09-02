package edu.uj.po.simulation.extensions;


import edu.uj.po.simulation.interfaces.UnknownChip;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.components.*;

public class ComponentFactory implements IComponentFactory{

    public Component createChip(int code) throws UnknownChip {
        Component component;
        int componentId = UniqueIdGenerator.getNextId();
        switch (code) {
            case 7400: // 4x NAND
                component = new NandGateComponent(componentId, 4);
                break;
            case 7402: // 4x NOR
                component = new NorGateComponent(componentId, 4);
                break;
            case 7404: // 6x NOT
                component = new NotGateComponent(componentId, 6);
                break;
            case 7408: // 4x AND
                component = new AndGateComponent(componentId, 4);
                break;
            case 7410: // 3x NAND
                component = new NandGateComponent(componentId, 3);
                break;
            case 7411: // 3x AND
                component = new AndGateComponent(componentId, 3);
                break;
            case 7420: // 2x NAND
                component = new NandGateComponent(componentId, 2);
                break;
            case 7431: // 6 elementów opóźniających
                component = new DelayComponent(componentId, 6);
                break;
            case 7432: // 4x OR
                component = new OrGateComponent(componentId, 4);
                break;
            case 7434: // 6 buforów
                component = new BufferComponent(componentId, 6);
                break;
            case 7442: // dekoder BCD na kod 1 z 10
                component = new BcdDecoderComponent(componentId);
                break;
            case 7444: // dekoder kodu GRAY'a na 1 z 10
                component = new GrayDecoderComponent(componentId);
                break;
            case 7482: // pełny sumator dwu-bitowy
                component = new FullAdderComponent(componentId);
                break;
            case 74138: // dekoder/demultiplekser 3 z 8 linii
                component = new DemultiplexerComponent(componentId);
                break;
            case 74152: // multiplekser 8-kanałowy
                component = new MultiplexerComponent(componentId);
                break;
            default:
                throw new UnknownChip();
        }
        component.setId(componentId);
        return component;
    }
}
