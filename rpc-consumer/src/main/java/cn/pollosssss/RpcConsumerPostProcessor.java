package cn.pollosssss;

import cn.pollosssss.annotation.RpcService;
import java.lang.reflect.Field;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class RpcConsumerPostProcessor implements BeanPostProcessor {

  @SneakyThrows
  @Override
  public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {

    Field[] fields = bean.getClass().getDeclaredFields();
    for (Field field : fields) {
      RpcService annotation = field.getAnnotation(RpcService.class);
      if (annotation != null) {
        Class<?> fieldType = field.getType();
        field.setAccessible(true);
        field.set(bean, ProxyFactory.getProxy(fieldType));
      }
    }
    return bean;
  }
}
