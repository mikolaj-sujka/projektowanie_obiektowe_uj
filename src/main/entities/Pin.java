package src.main.entities;


import src.main.edu.uj.po.simulation.interfaces.PinState;

public class Pin {
    private final int pinNumber;         // Numer pinu w komponencie
    private PinState state;        // Stan pinu (HIGH, LOW, UNKNOWN)
    private final boolean isOutput;      // Czy pin jest wyjściowy
    private Pin connectedPin;      // Pin, do którego ten pin jest podłączony (jeśli istnieje)

    public Pin(int pinNumber, boolean isOutput) {
        this.pinNumber = pinNumber;
        this.isOutput = isOutput;
        this.state = PinState.UNKNOWN; // Domyślny stan to UNKNOWN
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
    public void setState(PinState state) {
        this.state = state;
        // Jeśli pin jest połączony z innym pinem, ustaw stan tego pinu
        if (connectedPin != null) {
            connectedPin.setState(state);
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