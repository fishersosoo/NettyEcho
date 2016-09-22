package Server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	private final int port;
	
	public EchoServer(int port){
		//构造函数，设置server的端口
		this.port = port;
	}
	
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		if(args.length != 1){
			//参数不正常
			System.err.println("Usage: "+ EchoServer.class.getSimpleName() + " <port>");
			return ;
		}
		//接受参数作为端口
		int port =Integer.parseInt(args[0]);
		new EchoServer(port).start();	//启动Server
	}


	private void start() throws Exception{
		// TODO Auto-generated method stub
		final EchoServerOutHandler outHandler=new EchoServerOutHandler();
		final EchoServerHandler serverHandler = new EchoServerHandler();	//创建handler实例
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(port))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel channel) throws Exception{
					
					channel.pipeline().addLast("indhandler",serverHandler);
				channel.pipeline().addLast("outhandler",outHandler);
				channel.pipeline().addLast("out2",new EchoServerOutHandler2());
				}
				
			});
			ChannelFuture future = bootstrap.bind().sync();
			future.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully().sync();
		}
	}

}
