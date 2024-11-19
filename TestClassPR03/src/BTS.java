public
class BTS
        extends TransmissionStation {

    public BTS(String name, LayerCommunicator nextLayer) {
        super(name, nextLayer);
    }

    @Override
    public void run() {
        super.run();
        while (running) {
            if (getWaitingSMSCounter() != 0) {
//                System.out.println("tu");
                pollWaitingSMS();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    nextLayer.passMessage(this.pdu);
                } catch (NumberNotFoundException e) {
                    System.out.println("\033[31mThere is no reveiver with given number!\033[0m");
                }

                try {
                    endMessageKeeping();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
