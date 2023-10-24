package cn.pollosssss;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Invocation implements Serializable {

  public String interfaceName;
  public String methodName;
  public Class[] parameterTypes;
  public Object[] parameters;
}
