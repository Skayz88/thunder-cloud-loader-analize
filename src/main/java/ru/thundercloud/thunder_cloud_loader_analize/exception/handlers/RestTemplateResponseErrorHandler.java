package ru.thundercloud.thunder_cloud_loader_analize.exception.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import ru.thundercloud.thunder_cloud_loader_analize.exception.ForbiddenException;
import ru.thundercloud.thunder_cloud_loader_analize.exception.dto.HandleError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 *
 * @author DRakovskiy
 */

@Slf4j
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        throw new ForbiddenException(response.getStatusCode().toString(), getBodyOrDefault(response));
    }

    private HandleError getBodyOrDefault(ClientHttpResponse response) {
        if (Objects.nonNull(response)) {
            String body = getBodyToString(response);
            HandleError handleException = getPaymentExceptionFromBody(body);
            if (Objects.nonNull(handleException)) {
                return handleException;
            }
            return new HandleError(body, "CommonError");
        }
        return new HandleError("Возникла ошибка при обращении к сервису", "CommonError");
    }

    private String getBodyToString(ClientHttpResponse response) {
        if (Objects.nonNull(response)) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                response.getBody().transferTo(buffer);
                return buffer.toString(StandardCharsets.UTF_8);
            } catch (Exception e) {
                log.error("При получении Body ответа возникла ошибка: {}", e.getMessage());
            }
        }
        return null;
    }

    private HandleError getPaymentExceptionFromBody(String body) {
        try {
            return objectMapper.readValue(body, HandleError.class);
        } catch (Exception e) {
            log.error("При маппинге objectMapper возникла ошибка- {}", e.getMessage());
        }
        return null;
    }
}
