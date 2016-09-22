package Server;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

public class EchoServerOutHandler extends ChannelOutboundHandlerAdapter {
	static int counter = 0;

	@Override
	public void write(ChannelHandlerContext channelHandlerContext, Object msg, ChannelPromise promise)
			throws Exception {
//		System.out.println("!!!!!!");
		String outboundStr = (String) msg;
		System.out.println(counter + " out: " + outboundStr);
		msg=Unpooled.copiedBuffer((("" + counter + " : ").toString() + outboundStr), CharsetUtil.UTF_8);;
		channelHandlerContext.flush();
	 super.write(channelHandlerContext, msg, promise);
	}

}
