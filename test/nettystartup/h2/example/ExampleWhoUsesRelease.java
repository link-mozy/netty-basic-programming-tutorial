package nettystartup.h2.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ExampleWhoUsesRelease {

    public static ByteBuf a(ByteBuf input) {
        System.out.println("a input.refCnt:");
        System.out.println(input.refCnt());
        return input;
    }

    public static ByteBuf b(ByteBuf input) {
        try {
            ByteBuf output = input.alloc().directBuffer(input.readableBytes() + 1);
            System.out.println("b input.refCnt:");
            System.out.println(input.refCnt());
            return output;
        } finally {
            input.release();
        }
    }

    public static void c(ByteBuf input) {
        System.out.println("c input.refCnt:");
        System.out.println(input.refCnt());
        input.release();
    }

    public static void main(String[] args) {
        String m = "test message";
        ByteBuf buf = Unpooled.wrappedBuffer(m.getBytes());
        c(b(a(buf)));
        System.out.println("main buf.refCnt:");
        System.out.println(buf.refCnt());
    }
}
