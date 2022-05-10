package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.Color;

import java.io.*;

public class ObjectSenderReceiver {

    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;

    public ObjectSenderReceiver(InputStream is, OutputStream os) throws IOException {
        oos = new ObjectOutputStream(os);
        oos.flush();
        ois = new ObjectInputStream(is);
    }

    public <T> void sendObject(T obj) throws IOException {
        oos.reset();
        oos.writeObject(obj);
        oos.flush();
    }

    public <T> T receiveObject() throws IOException, ClassNotFoundException {
        return (T) ois.readObject();
    }


}
