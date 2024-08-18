package extensions;

import edu.uj.po.simulation.interfaces.UnknownChip;
import entities.Component;

public interface IComponentFactory {
    public Component createChip(int code) throws UnknownChip;
}
