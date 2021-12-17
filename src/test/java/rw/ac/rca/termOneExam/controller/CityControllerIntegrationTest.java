package rw.ac.rca.termOneExam.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityServiceMvc;

    @MockBean
    private ICityRepository cityRepository;

    @Test
    public void getOne_success() throws Exception {

        when(cityServiceMvc.getById(104))
                .thenReturn(Optional.of(new City(104, "Nyagatare", 28.0,0.0)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/cities/id/104")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28.0,\"fahrenheit\":0.0}"))
                .andReturn();
    }

    @Test
    public void getOne_not_Found() throws Exception{
        when(cityServiceMvc.getById(200))
                .thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cities/id/200")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void create_success() throws Exception{
        City createCityDTO = new City("Huye", 23.0);
        when(cityRepository.existsByName(createCityDTO.getName()))
                .thenReturn(false);
        when(cityRepository.save(createCityDTO))
                .thenReturn(createCityDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cities/add")
                .content("{\"name\":\"Nyagatare\",\"weather\":28.0}")
                .contentType("application/json");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

    }
    @Test
    public void create_fail_exists() throws Exception{
        when(cityServiceMvc.existsByName("Huye"))
                .thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cities/add")
                .content("{\"name\":\"Huye\",\"weather\":28.0}")
                .contentType("application/json");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getAll_success() throws Exception {

        when(cityServiceMvc.getAll())
                .thenReturn(Arrays.asList(new City("Huye", 23.0), new City("Karongi", 23.0)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/cities/all")
                .accept(MediaType.APPLICATION_JSON);


        mockMvc.perform(request).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAll_fail() throws Exception{
        when(cityServiceMvc.getAll())
                .thenReturn(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/cities/all")
                .accept(MediaType.APPLICATION_JSON);


        mockMvc.perform(request).andExpect(status().isNotFound())
                .andReturn();
    }


}