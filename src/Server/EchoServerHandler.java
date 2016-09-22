package Server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.ScheduledFuture;
import jdk.internal.dynalink.beans.StaticClass;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	static int times=0;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		String inboundStr = in.readBytes(in.readableBytes()).toString(CharsetUtil.UTF_8);
		System.out.println("Server received: " + inboundStr);
		in.discardReadBytes();
		ctx.writeAndFlush(Unpooled.copiedBuffer(inboundStr.getBytes()));
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Server received finished! Flush! ");
		Channel channel = ctx.channel();
//		ScheduledFuture<?> future=channel.eventLoop().scheduleAtFixedRate(new Runnable() {
//			public void run() {
//				Date now = new Date(); 
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
//				String nowtime = dateFormat.format( now ); 
//				System.out.println(nowtime+": run...");
//				channel.writeAndFlush((Unpooled.copiedBuffer("Message: " + nowtime,CharsetUtil.UTF_8)));
//			}
//		}, 3, 2, TimeUnit.SECONDS);
		//ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		channel.writeAndFlush(Unpooled.copiedBuffer("Server received finished!".getBytes()));
		//super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable causer) {
		causer.printStackTrace();
		ctx.close();
	}
}
