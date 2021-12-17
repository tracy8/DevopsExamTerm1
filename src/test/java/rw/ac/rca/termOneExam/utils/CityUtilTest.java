package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CityUtilTest {

    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void noCityWithWeatherMoreThan40_test() {

        assertEquals(0, cityRepository.countByWeatherGreaterThan(40));
    }

    @Test
    public void noCityWithWeatherLessThan10_test() {

        assertEquals(0, cityRepository.countByWeatherLessThan(10));
    }

    @Test
    public void citiesContainsMusanzeAndKigali_test() {
        assertTrue(cityRepository.existsByName("Musanze"));

        assertTrue(cityRepository.existsByName("Kigali"));
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class TestingSpy {

        @Spy
        private ICityRepository cityRepositoryBySpy;

        @InjectMocks
        private CityService cityService;

        @Test
        public void testSpying() {
            when(cityRepositoryBySpy.findAll()).thenReturn(Arrays.asList(new City("Lisbon", 32), new City("Marseille", 32)));

            List<City> cities = cityService.getAll();

            assertEquals(2, cities.size());
        }

    }


    @RunWith(MockitoJUnitRunner.class)
    public static class TestingMock {

        @Mock
        private ICityRepository cityRepositoryByMock;

        @InjectMocks
        private CityService cityService;

        @Test
        public void testMocking() {
            when(cityRepositoryByMock.findAll()).thenReturn(Arrays.asList(new City("Lisbon", 32), new City("Marseille", 32)));

            List<City> cities = cityService.getAll();

            assertEquals(2, cities.size());
        }

    }
}