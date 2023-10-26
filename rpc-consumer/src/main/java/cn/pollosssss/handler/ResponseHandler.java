package cn.pollosssss.handler;

import cn.pollosssss.RpcRequestHolder;
import cn.pollosssss.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
    String requestId = rpcResponse.getRequestId();
    RpcRequestHolder.REQUEST_MAP.remove(requestId).complete(rpcResponse);
  }
}
