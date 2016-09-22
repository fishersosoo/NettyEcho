package Server;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

public class EchoServerOutHandler2 extends ChannelOutboundHandlerAdapter {
	static int counter = 1;

	@Override
	public void write(ChannelHandlerContext channelHandlerContext, Object msg, ChannelPromise promise)
			throws Exception {
		ByteBuf in = (ByteBuf) msg;
		String outboundStr = in.readBytes(in.readableBytes()).toString(CharsetUtil.UTF_8);
		System.out.println(counter + " out: " + outboundStr);
		msg=(("" + counter + " : ").toString() + outboundStr);
		 super.write(channelHandlerContext, msg, promise);
	}

}
