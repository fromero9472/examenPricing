package service;

import com.example.model.Price;
import com.example.repository.PriceRepository;
import com.example.service.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Extiende con la anotación MockitoExtension para utilizar Mockito en las pruebas
@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    // Mock del repositorio para simular su comportamiento
    @Mock
    private PriceRepository priceRepository;

    // Instancia del servicio a probar, con los mocks inyectados automáticamente
    @InjectMocks
    private PriceService priceService;

    // Prueba parametrizada con diferentes conjuntos de datos
    @ParameterizedTest
    @CsvSource({
            "1, 35455, 2023-12-14-10.00.00",
            "1, 35455, 2023-12-14-16.00.00",
            "1, 35455, 2023-12-14-21.00.00",
            "1, 35455, 2023-12-15-10.00.00",
            "1, 35455, 2023-12-16-21.00.00"
    })
    public void testGetPriceList(Long brandId, Long productId, String dateTime) {
        // Llama al método de prueba con los parámetros de entrada
        executeTest(brandId, productId, dateTime, () -> Collections.singletonList(new Price()));
    }

    // Método que realiza la ejecución de la prueba
    private void executeTest(Long brandId, Long productId, String dateTime, Supplier<List<Price>> serviceResponse) {
        // Convierte la cadena de fecha y hora a LocalDateTime
        LocalDateTime applicationDate = parseStringToLocalDateTime(dateTime);

        // Configura el comportamiento simulado del repositorio
        when(priceRepository.findByBrandIdAndProductIdAndStartDate(brandId, productId, applicationDate))
                .thenReturn(serviceResponse.get());

        // Llama al método del servicio bajo prueba
        List<Price> result = priceService.getPriceList(brandId, productId, applicationDate);

        // Verifica que el repositorio fue llamado con los parámetros correctos
        verify(priceRepository, times(1)).findByBrandIdAndProductIdAndStartDate(brandId, productId, applicationDate);

        // Verifica que el resultado del servicio coincide con la respuesta simulada del repositorio
        assertEquals(serviceResponse.get(), result);
    }

    // Convierte una cadena de fecha y hora a LocalDateTime
    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));
    }
}
