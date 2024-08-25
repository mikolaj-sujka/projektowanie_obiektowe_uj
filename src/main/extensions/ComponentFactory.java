package src.main.extensions;


import src.main.edu.uj.po.simulation.interfaces.UnknownChip;
import src.main.entities.Component;
import src.main.entities.components.*;

public class ComponentFactory implements IComponentFactory{
    private static int nextComponentId = 1; // Generator unikalnych ID

    public Component createChip(int code) throws UnknownChip {
        Component component;
        switch (code) {
            case 7400: // 4x NAND
                component = new NandGateComponent(nextComponentId, 4);
                break;
            case 7402: // 4x NOR
                component = new NorGateComponent(nextComponentId, 4);
                break;
            case 7404: // 6x NOT
                component = new NotGateComponent(nextComponentId, 6);
                break;
            case 7408: // 4x AND
                component = new AndGateComponent(nextComponentId, 4);
                break;
            case 7410: // 3x NAND
                component = new NandGateComponent(nextComponentId, 3);
                break;
            case 7411: // 3x AND
                component = new AndGateComponent(nextComponentId, 3);
                break;
            case 7420: // 2x NAND
                component = new NandGateComponent(nextComponentId, 2);
                break;
            case 7431: // 6 elementów opóźniających
                component = new DelayComponent(nextComponentId, 6);
                break;
            case 7432: // 4x OR
                component = new OrGateComponent(nextComponentId, 4);
                break;
            case 7434: // 6 buforów
                component = new BufferComponent(nextComponentId, 6);
                break;
            case 7442: // dekoder BCD na kod 1 z 10
                component = new BcdDecoderComponent(nextComponentId);
                break;
            case 7444: // dekoder kodu GRAY'a na 1 z 10
                component = new GrayDecoderComponent(nextComponentId);
                break;
            case 7482: // pełny sumator dwu-bitowy
                component = new FullAdderComponent(nextComponentId);
                break;
            case 74138: // dekoder/demultiplekser 3 z 8 linii
                component = new DemultiplexerComponent(nextComponentId);
                break;
            case 74152: // multiplekser 8-kanałowy
                component = new MultiplexerComponent(nextComponentId);
                break;
            default:
                throw new UnknownChip();
        }
        component.setId(nextComponentId++);
        return component;
    }
}
