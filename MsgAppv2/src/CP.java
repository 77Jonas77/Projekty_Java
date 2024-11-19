public class CP implements ServerCommunicationInterface,ClientCommunicationInterface{
    //Communication Panel
    //Czy da sie to jakos lepiej zaimplementowac? tzn jedna instancje klasy do serwera i klienta?
    private final String reset = "\u001B[0m";
    private final String red = "\u001B[31m";

    @Override
    public void serverNotifyMsg(String msg) {
        System.out.println(red + "SERVER: " + msg + reset);
    }

    @Override
    public void clientNotifyMsg(String msg, String username) {
        System.out.println(username + " " + msg);
    }
}
