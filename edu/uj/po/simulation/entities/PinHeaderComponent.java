package edu.uj.po.simulation.entities;

public class PinHeaderComponent extends Component {
    public PinHeaderComponent(int id, int size, boolean isOutput) {
        super(id);
        // Dodaj piny w zależności od rozmiaru
        for (int i = 1; i <= size; i++) {
            addPin(i, new Pin(i, isOutput));
        }
    }

    @Override
    public void performLogic() {
        // Pin header nie wykonuje żadnej logiki
    }
}
