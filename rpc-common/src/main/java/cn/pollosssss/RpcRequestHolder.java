package cn.pollosssss;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class RpcRequestHolder {

  public static final Map<String, CompletableFuture<RpcResponse>> REQUEST_MAP = new ConcurrentHashMap<>();

}
