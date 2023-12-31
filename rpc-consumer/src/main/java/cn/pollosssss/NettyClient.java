package cn.pollosssss;

import cn.pollosssss.handler.ResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NettyClient {


  public Object sendRequest(Invocation invocation) throws InterruptedException, ExecutionException {
    Bootstrap bootstrap = new Bootstrap();
    EventLoopGroup group = new NioEventLoopGroup();

    bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) {
            ch.pipeline()
                .addLast(new ObjectEncoder())
                .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                .addLast(new ResponseHandler());
          }
        });

    try {
      Channel channel = bootstrap.connect("127.0.0.1", 9000).sync().channel();

      RpcRequestHolder.REQUEST_MAP.put(invocation.getRequestId(), new CompletableFuture<>());
      channel.writeAndFlush(invocation).addListener(future -> {
        if (future.isSuccess()){

          System.out.println("发送成功");
        }else {
          future.cause().printStackTrace();
          System.out.println("发送失败");
        }
      });

      return RpcRequestHolder.REQUEST_MAP.get(invocation.getRequestId()).get().getResult();
    } finally {
      group.shutdownGracefully();
    }


  }
}
