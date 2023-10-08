package cat.tecnocampus.fgcstations;

import cat.tecnocampus.fgcstations.api.FGCRestController;
import cat.tecnocampus.fgcstations.application.DTOs.FavoriteJourneyDTO;
import cat.tecnocampus.fgcstations.application.DTOs.FriendsDTO;
import cat.tecnocampus.fgcstations.application.FgcController;
import cat.tecnocampus.fgcstations.domain.*;
import cat.tecnocampus.fgcstations.domain.exceptions.SameOriginDestinationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FGCRestController.class)
class FgCstationsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FgcController fgcController;

    @Test
    void whenValidInput_thenReturns200() throws Exception{
        User user = new User();
        Station station = new Station();
        FavoriteJourneyDTO favJourney = new FavoriteJourneyDTO();
        FriendsDTO friends = new FriendsDTO();

        mockMvc.perform(get("/users").contentType("aplication/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{username}").contentType("aplication/json")
                        .param("username", "true")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stations").contentType("aplication/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stations/{nom}").contentType("aplication/json")
                        .param("nom", "true")
                        .content(objectMapper.writeValueAsString(station)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/users/{userName}/favoriteJourney").contentType("aplication/json")
                    .param("FavoriteJourneyDTO","userName", "true")
                    .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{userName}/friends").contentType("aplication/json")
                        .param("username", "true")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/friends").contentType("aplication/json"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/users/friends").contentType("aplication/json")
                .param("FriendsDTO", "true")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }




    @Test
    void createUser() {
        User user = new User();
        assertTrue(user.getFavoriteJourneyList() instanceof ArrayList<FavoriteJourney>);
        assertTrue(user.getName()==null && user.getEmail()==null && user.getSecondName()==null && user.getUsername()==null);
    }

    @Test
    void checkSameOriginDestinationException(){
        Station origin = new Station();
        Station destination = new Station();
        origin.setNom("Mataro");
        destination.setNom("Mataro");
        assertThrows(SameOriginDestinationException.class, () -> new Journey(origin, destination, "123"));
    }
}
