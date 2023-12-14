package service;

import com.example.model.Price;
import com.example.repository.PriceRepository;
import com.example.service.PriceService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @ParameterizedTest
    @CsvSource({
            "1, 35455, 2023-12-14-10.00.00, 1",
            "1, 35455, 2023-12-14-16.00.00, 1",
            "1, 35455, 2023-12-14-21.00.00, 1",
            "1, 35455, 2023-12-15-10.00.00, 1",
            "1, 35455, 2023-12-16-21.00.00, 1"
    })
    public void testGetPriceList(Long brandId, Long productId, String dateTime, int invocationTimes) {
        // Convertir la cadena de fecha y hora a LocalDateTime
        LocalDateTime applicationDate = parseStringToLocalDateTime(dateTime);

        // Configurar el comportamiento simulado del repositorio
        when(priceRepository.findByBrandIdAndProductIdAndStartDate(brandId, productId, applicationDate))
                .thenReturn(Collections.singletonList(new Price()));

        // Llamar al método del servicio bajo prueba
        Map<String, Object> params = new HashMap<>();
        params.put("brandId", brandId);
        params.put("productId", productId);
        params.put("applicationDate", dateTime);

        List<Price> result = priceService.getPriceList(params);
        // Verificar que el repositorio fue llamado con los parámetros correctos
        verify(priceRepository, times(invocationTimes)).findByBrandIdAndProductIdAndStartDate(brandId, productId, applicationDate);

        // Verificar que el resultado del servicio coincide con la respuesta simulada del repositorio
        assertEquals(Collections.singletonList(new Price()), result);
    }

    // Convierte una cadena de fecha y hora a LocalDateTime
    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));
    }
}
