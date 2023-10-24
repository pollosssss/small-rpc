package cn.pollosssss;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args)
      throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    ServerSocket serverSocket = new ServerSocket(8888);
    while (true) {
      Socket socket = serverSocket.accept();
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      Invocation invocation = (Invocation) in.readObject();
      String interfaceName = invocation.getInterfaceName();
      String methodName = invocation.getMethodName();
      Object[] parameters = invocation.getParameters();
      Class[] parameterTypes = invocation.getParameterTypes();

      Class<?> clazz = Class.forName(interfaceName);
      Method method = clazz.getMethod(methodName, parameterTypes);
      Object response = method.invoke(clazz.newInstance(), parameters);
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      out.writeObject(response);
      out.flush();
      out.close();
      in.close();
      socket.close();
    }

  }
}
