package cn.pollosssss;

import cn.pollosssss.service.HelloService;
import cn.pollosssss.service.HelloServiceImpl;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Socket socket = new Socket("localhost", 8888);
    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    out.writeObject(Invocation.builder()
        .interfaceName(HelloServiceImpl.class.getName())
        .methodName("sayHello")
        .parameterTypes(new Class[] {String.class})
        .parameters(new Object[] {"pollosssss"})
        .build());
    out.flush();

    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    String obj = (String) in.readObject();
    out.close();
    in.close();
    socket.close();
    System.out.println(obj);
  }
}
