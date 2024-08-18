package extensions;

import edu.uj.po.simulation.interfaces.UnknownChip;
import entities.Component;
import entities.components.*;

public class ComponentFactory implements IComponentFactory{
    private static int nextComponentId = 1; // Generator unikalnych ID

    public Component createChip(int code) throws UnknownChip {
        Component component;
        switch (code) {
            case 7400: // 4x NAND
                component = new NandGateComponent(nextComponentId, 4); // Przykładowy komponent - 4 bramki NAND
                break;
            case 7402: // 4x NOR
                component = new NorGateComponent(nextComponentId, 4); // Przykładowy komponent - 4 bramki NOR
                break;
            case 7404: // 6x NOT
                component = new NotGateComponent(nextComponentId, 6); // Przykładowy komponent - 6 bramek NOT
                break;
            case 7408: // 4x AND
                component = new AndGateComponent(nextComponentId, 4); // Przykładowy komponent - 4 bramki AND
                break;
            case 7410: // 3x NAND
                component = new NandGateComponent(nextComponentId, 3); // Przykładowy komponent - 3 bramki NAND
                break;
            case 7411: // 3x AND
                component = new AndGateComponent(nextComponentId, 3); // Przykładowy komponent - 3 bramki AND
                break;
            case 7420: // 2x NAND
                component = new NandGateComponent(nextComponentId, 2); // Przykładowy komponent - 2 bramki NAND
                break;
            case 7431: // 6 elementów opóźniających
                component = new DelayComponent(nextComponentId, 6); // Przykładowy komponent - 6 elementów opóźniających
                break;
            case 7432: // 4x OR
                component = new OrGateComponent(nextComponentId, 4); // Przykładowy komponent - 4 bramki OR
                break;
            case 7434: // 6 buforów
                component = new BufferComponent(nextComponentId, 6); // Przykładowy komponent - 6 buforów
                break;
            case 7442: // dekoder BCD na kod 1 z 10
                component = new BcdDecoderComponent(nextComponentId); // Przykładowy komponent - dekoder BCD
                break;
            case 7444: // dekoder kodu GRAY'a na 1 z 10
                component = new GrayDecoderComponent(nextComponentId); // Przykładowy komponent - dekoder kodu GRAY'a
                break;
            case 7482: // pełny sumator dwu-bitowy
                component = new FullAdderComponent(nextComponentId); // Przykładowy komponent - pełny sumator dwu-bitowy
                break;
            case 74138: // dekoder/demultiplekser 3 z 8 linii
                component = new DemultiplexerComponent(nextComponentId); // Przykładowy komponent - dekoder/demultiplekser
                break;
            case 74152: // multiplekser 8-kanałowy
                component = new MultiplexerComponent(nextComponentId); // Przykładowy komponent - multiplekser 8-kanałowy
                break;
            default:
                throw new UnknownChip();
        }
        component.setId(nextComponentId++);
        return component;
    }
}
