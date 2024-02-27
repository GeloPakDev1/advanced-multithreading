package service;

import com.gpd.esm.fjp.Fibonacci;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ForkJoinPool;

@ExtendWith(MockitoExtension.class)
public class FibonacciTest {

    @Test
    void testSequentialProcessing() {
        assertEquals(1134903170L, new ForkJoinPool().invoke(new Fibonacci(45)).longValue());
    }
}
