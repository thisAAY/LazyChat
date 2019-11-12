package connection.listener;

import data.model.Device;

public interface MessageListener {

    void onMessageArriveToServer(Device device, String msg);
    void onMessageArriveToClient(Device device,String msg);

}
