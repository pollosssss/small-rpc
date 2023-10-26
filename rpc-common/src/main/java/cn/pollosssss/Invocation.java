package cn.pollosssss;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Invocation implements Serializable {

  private String requestId;
  private String interfaceName;
  private String methodName;
  private Class[] parameterTypes;
  private Object[] parameters;
}
