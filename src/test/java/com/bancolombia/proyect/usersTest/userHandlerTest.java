package com.bancolombia.proyect.usersTest;

import com.bancolombia.proyect.domain.model.user.Dtos.UserDto;
import com.bancolombia.proyect.domain.model.user.Dtos.balanceDto;
import com.bancolombia.proyect.domain.usecase.userUseCase.UserUseCase;
import com.bancolombia.proyect.infrastructure.entry_points.Routes.RouterRestUser;
import com.bancolombia.proyect.infrastructure.entry_points.handler.CashoutsHandler;
import com.bancolombia.proyect.infrastructure.entry_points.handler.UserHandler;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRestUser.class, UserHandler.class, CashoutsHandler.class})
@AutoConfigureWebTestClient
public class userHandlerTest {
    @Mock
    private UserUseCase useCase;

    @Mock
    private ServerRequest request;

    @InjectMocks
    private UserHandler userHandler;
    private CashoutsHandler cashoutsHandler;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userHandler = new UserHandler(useCase);
        webTestClient = WebTestClient.bindToRouterFunction(new RouterRestUser().routerFunction(userHandler)).build();
    }

    @Test
    public void testGetUserHandler_UserFound() {
        Long userId = 1L;
        BigDecimal balance = new BigDecimal(100);
        UserDto userDto = new UserDto("John Doe",balance,userId);
        when(request.pathVariable("id")).thenReturn(String.valueOf(userId));
        when(useCase.findUserById(anyLong())).thenReturn(Mono.just(userDto));

        Mono<ServerResponse> response = userHandler.getUserHandler(request);

        ServerResponse result = response.block();
        assert result != null;
        assertEquals(200, result.statusCode().value());
        assertEquals(MediaType.APPLICATION_JSON, result.headers().getContentType());

    }

    @Test
    public void testGetUserHandler_UserNotFound() {
        Long userId = 1L;
        when(request.pathVariable("id")).thenReturn(String.valueOf(userId));
        when(useCase.findUserById(anyLong())).thenReturn(Mono.empty());

        Mono<ServerResponse> response = userHandler.getUserHandler(request);

        ServerResponse result = response.block();
        assert result != null;
        assertEquals(404, result.statusCode().value());
    }

    @Test
    public void testSaveUserHandler_UserSavedSuccessfully() {
        BigDecimal balance = new BigDecimal(100);
        UserDto userDto = new UserDto("John Doe", balance,1L);
        UserDto savedUserDto = new UserDto( "John Doe",balance,1L);
        when(useCase.saveUser(any(UserDto.class))).thenReturn(Mono.just(savedUserDto));
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()  // Verificar que el estado sea 200 OK
                .expectHeader().contentType(MediaType.APPLICATION_JSON)  // Verificar que el tipo de contenido sea JSON
                .expectBody(UserDto.class)
                .value(userResponse -> {
                    // Assert: Verificar que los datos del usuario guardado sean correctos
                    assertEquals(1L, userResponse.getId());
                    assertEquals("John Doe", userResponse.getName());
                    assertEquals(balance, userResponse.getBalance());
                });





    }

    @Test
    public void testSaveUserHandler_UserSaveFailed() {
        BigDecimal balance = new BigDecimal(100);
        UserDto userDto = new UserDto("John Doe", balance,1L);
        when(request.bodyToMono(UserDto.class)).thenReturn(Mono.just(userDto));
        when(useCase.saveUser(any(UserDto.class))).thenReturn(Mono.error(new RuntimeException("Database error")));
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class)
                .value(errorMessage -> {
                    assertEquals("FAILED TO SAVE USER: Database error", errorMessage);
                });
    }

    @Test
    public void testUpdateBalanceHandler_Success() {
        Long userId = 1L;
        BigDecimal newBalance = BigDecimal.valueOf(100);

        balanceDto balanceDto = new balanceDto(newBalance);
        UserDto updatedUserDto = new UserDto("John Doe", newBalance, userId);

        when(useCase.updateBalance(userId, newBalance)).thenReturn(Mono.just(updatedUserDto));

        webTestClient.put()
                .uri("/users/{id}/balance", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(balanceDto)  // Aquí envías el objeto balanceDto
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserDto.class)
                .value(userResponse -> {
                    assertEquals(userId, userResponse.getId());
                    assertEquals("John Doe", userResponse.getName());
                    assertEquals(newBalance, userResponse.getBalance());
                });
    }

    @Test
    public void testUpdateBalanceHandler_AmountIsNull() {
        Long userId = 1L;
        balanceDto balanceDto = new balanceDto(null);

        webTestClient.put()
                .uri("/users/{id}/balance", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(balanceDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .value(responseBody -> {
                    assertEquals("Error updating balance: Amount cannot be null", responseBody);
                });
    }

    @Test
    public void testUpdateBalanceHandler_UserNotFound() {
        Long userId = 1L;
        BigDecimal newBalance = new BigDecimal(100);
        JSONObject balanceDto = new JSONObject();
        balanceDto.put("amount", 100);

        when(useCase.updateBalance(userId, newBalance)).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/users/{id}/balance", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(balanceDto)
                .exchange()
                .expectStatus().is5xxServerError()
        ;
    }


}

