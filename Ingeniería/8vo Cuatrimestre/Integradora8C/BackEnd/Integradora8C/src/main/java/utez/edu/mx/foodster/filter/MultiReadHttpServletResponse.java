package utez.edu.mx.foodster.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.*;

public class MultiReadHttpServletResponse extends HttpServletResponseWrapper {

    private ByteArrayOutputStream cachedContent;
    private ServletOutputStream output;
    private PrintWriter writer;

    public MultiReadHttpServletResponse(HttpServletResponse response) throws UnsupportedEncodingException {
        super(response);
        cachedContent = new ByteArrayOutputStream();
        output = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                cachedContent.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                // No es necesario implementar

            }
        };
        writer = new PrintWriter(new OutputStreamWriter(cachedContent, this.getCharacterEncoding()));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return output;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        output.flush();
        writer.flush();
    }

    public byte[] getContentAsByteArray() {
        return cachedContent.toByteArray();
    }

    public void copyBodyToResponse() throws IOException {
        super.getOutputStream().write(cachedContent.toByteArray());
    }
}