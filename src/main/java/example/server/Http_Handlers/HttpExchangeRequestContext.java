package example.server.Http_Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.fileupload.RequestContext;

import java.io.IOException;
import java.io.InputStream;

public class HttpExchangeRequestContext implements RequestContext {
    private HttpExchange exchange;

    public HttpExchangeRequestContext(HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getCharacterEncoding() {
        return "UTF-8";
    }

    @Override
    public String getContentType() {
        return exchange.getRequestHeaders().getFirst("Content-Type");
    }

    @Override
    public int getContentLength() {
        return 0; // Not used
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return exchange.getRequestBody();
    }
}
