package extensions;

public interface IPinHeaderFactory {
    int createOutputPinHeader(int size);
    int createInputPinHeader(int size);
}
