package cn.pollosssss;

import cn.pollosssss.service.HelloService;

public class ConsumerMain {


  public static void main(String[] args) {
    String pollosssss = ProxyFactory.getProxy(HelloService.class).sayHello("pollosssss");

    System.out.println(pollosssss);

  }
}
