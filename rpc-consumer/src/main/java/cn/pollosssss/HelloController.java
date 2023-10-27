package cn.pollosssss;

import cn.pollosssss.annotation.RpcService;
import cn.pollosssss.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @RpcService
  private HelloService helloService;

  @GetMapping("/hello")
  public String sayHello() {
    return helloService.sayHello("pollosssss");
  }
}
