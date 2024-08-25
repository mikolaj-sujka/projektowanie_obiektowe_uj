package src.main.extensions;


import src.main.edu.uj.po.simulation.interfaces.UnknownChip;
import src.main.entities.Component;

public interface IComponentFactory {
    public Component createChip(int code) throws UnknownChip;
}
