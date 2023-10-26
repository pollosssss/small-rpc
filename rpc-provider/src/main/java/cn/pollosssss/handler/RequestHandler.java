package cn.pollosssss.handler;

import cn.pollosssss.Invocation;
import cn.pollosssss.RpcResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.lang.reflect.Method;

public class RequestHandler extends SimpleChannelInboundHandler<Invocation> {

  private ObjectMapper objectMapper = new ObjectMapper();

//  @Override
//  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//    Invocation invocation = (Invocation) msg;
////    Invocation invocation = objectMapper.readValue(msgString, Invocation.class);
//    String interfaceName = invocation.getInterfaceName();
//    String methodName = invocation.getMethodName();
//    Object[] parameters = invocation.getParameters();
//    Class[] parameterTypes = invocation.getParameterTypes();
//
//    Class<?> clazz = Class.forName(interfaceName);
//    Method method = clazz.getMethod(methodName, parameterTypes);
//    Object response = method.invoke(clazz.newInstance(), parameters);
//    RpcResponse rpcResponse = RpcResponse.builder().requestId(invocation.getRequestId()).result(response).build();
//    ctx.writeAndFlush(rpcResponse);
//  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Invocation invocation) throws Exception {
    String interfaceName = invocation.getInterfaceName();
    String methodName = invocation.getMethodName();
    Object[] parameters = invocation.getParameters();
    Class[] parameterTypes = invocation.getParameterTypes();

    Class<?> clazz = Class.forName(interfaceName);
    Method method = clazz.getMethod(methodName, parameterTypes);
    Object response = method.invoke(clazz.newInstance(), parameters);
    RpcResponse rpcResponse = RpcResponse.builder().requestId(invocation.getRequestId()).result(response).build();
    ctx.writeAndFlush(rpcResponse).addListener(future ->
        {
          if (future.isSuccess()) {
            System.out.println("发送成功");
          } else {
            future.cause().printStackTrace();
            System.out.println("发送失败");
          }
        }
    );
  }

  @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
