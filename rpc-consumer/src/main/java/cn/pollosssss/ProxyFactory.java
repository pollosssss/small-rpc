package cn.pollosssss;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class ProxyFactory {

  private static InvocationHandler handler = new InvocationHandler() {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Invocation invocation = Invocation.builder()
          .interfaceName(method.getDeclaringClass().getName())
          .methodName(method.getName())
          .parameterTypes(method.getParameterTypes())
          .parameters(args)
          .requestId(UUID.randomUUID().toString())
          .build();

      return new NettyClient().sendRequest(invocation);
    }
  };

    public static <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
    }
}
