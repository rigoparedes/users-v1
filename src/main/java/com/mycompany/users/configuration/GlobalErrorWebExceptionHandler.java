package com.mycompany.users.configuration;

import static java.util.Optional.ofNullable;
import static org.springframework.boot.web.error.ErrorAttributeOptions.of;

import com.mycompany.users.domain.util.UserApiException;
import java.util.Map;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

// https://www.baeldung.com/spring-webflux-errors
@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

  public GlobalErrorWebExceptionHandler(
      ErrorAttributes errorAttributes,
      Resources resources,
      ApplicationContext applicationContext,
      ServerCodecConfigurer serverCodecConfigurer) {
    super(errorAttributes, resources, applicationContext);
    super.setMessageWriters(serverCodecConfigurer.getWriters());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  protected int getHttpStatus(Map<String, Object> errorAttributes) {
    return (int) errorAttributes.get("status");
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    Map<String, Object> errors = getErrorAttributes(request, of(Include.MESSAGE));
    Throwable error = super.getError(request);
    int httpStatus = getHttpStatus(errors);
    Object message = ofNullable(errors.get("message")).orElse("Error inesperado.");
    if (UserApiException.class.isInstance(error)) {
      UserApiException userApiException = (UserApiException) error;
      httpStatus = userApiException.getStatus();
      message = userApiException.getMessage();
    }
    return ServerResponse.status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(Map.of("message", message)));
  }
}

