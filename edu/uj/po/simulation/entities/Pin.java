package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;

public class Pin {
    private final int pinNumber;         // Numer pinu w komponencie
    private PinState state;        // Stan pinu (HIGH, LOW, UNKNOWN)
    private final boolean isOutput;      // Czy pin jest wyjściowy
    private Pin connectedPin;      // Pin, do którego ten pin jest podłączony (jeśli istnieje)
    private boolean isUpdatingState; // Flaga zapobiegająca rekurencji

    public Pin(int pinNumber, boolean isOutput) {
        this.pinNumber = pinNumber;
        this.isOutput = isOutput;
        this.state = PinState.UNKNOWN; // Domyślny stan to UNKNOWN
        this.isUpdatingState = false;  // Flaga początkowa dla unikania rekurencji
    }

    // Pobranie numeru pinu
    public int getPinNumber() {
        return pinNumber;
    }

    // Pobranie stanu pinu
    public PinState getState() {
        return state;
    }

    // Ustawienie stanu pinu
    public void setState(PinState newState) {
        // Sprawdź, czy pin nie jest w trakcie aktualizacji, aby uniknąć rekurencji
        if (!isUpdatingState) {
            isUpdatingState = true;  // Ustaw flagę na true, aby zapobiec rekurencji

            System.out.println("Pin " + pinNumber + " setting state to " + newState);
            this.state = newState;

            // Jeśli pin jest połączony z innym pinem i nie jest w trakcie aktualizacji, ustaw stan tego pinu
            if (connectedPin != null && !connectedPin.isUpdatingState) {
                connectedPin.setState(newState);
            }

            isUpdatingState = false;  // Ustaw flagę z powrotem na false po zakończeniu
        }
    }

    // Sprawdzenie, czy pin jest wyjściowy
    public boolean isOutput() {
        return isOutput;
    }

    // Połączenie tego pinu z innym pinem
    public void connect(Pin otherPin) {
        this.connectedPin = otherPin;
        otherPin.connectedPin = this;
    }

    // Rozłączenie pinu od innego pinu (jeśli połączenie istnieje)
    public void disconnect() {
        if (connectedPin != null) {
            connectedPin.connectedPin = null;
            this.connectedPin = null;
        }
    }

    // Sprawdzenie, czy pin jest połączony z innym pinem
    public boolean isConnected() {
        return connectedPin != null;
    }

    // Pobranie połączonego pinu (jeśli istnieje)
    public Pin getConnectedPin() {
        return connectedPin;
    }
}
