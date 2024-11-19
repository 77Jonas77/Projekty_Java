public interface LayerCommunicator {
    void passMessage(byte[] pdu) throws NumberNotFoundException;
}
