package cn.pollosssss;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RpcResponse implements Serializable {

  String requestId;

  Object result;
}
