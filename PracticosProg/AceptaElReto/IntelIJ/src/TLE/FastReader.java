package TLE;

import java.io.DataInputStream;
import java.io.IOException;

public class FastReader {
    final private int BUFFER_SIZE = 1 << 16; // 65,536 bytes
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public FastReader() {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    // Lee una línea completa de entrada.
    public String nextLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        byte c = read();
        // Lee hasta el fin de línea o EOF
        while (c != -1 && c != '\n') {
            if (c != '\r')
                sb.append((char)c);
            c = read();
        }
        return sb.toString();
    }

    public int nextInt() throws IOException {
        int ret = 0;
        byte c = read();
        while(c <= ' ') c = read();  // omite espacios en blanco
        boolean neg = (c == '-');
        if (neg) c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        return neg ? -ret : ret;
    }

    private byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    private void fillBuffer() throws IOException {
        bytesRead = din.read(buffer, 0, BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
        bufferPointer = 0;
    }
}
