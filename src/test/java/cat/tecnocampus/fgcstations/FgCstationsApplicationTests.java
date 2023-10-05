package cat.tecnocampus.fgcstations;

import cat.tecnocampus.fgcstations.domain.FavoriteJourney;
import cat.tecnocampus.fgcstations.domain.Journey;
import cat.tecnocampus.fgcstations.domain.Station;
import cat.tecnocampus.fgcstations.domain.User;
import cat.tecnocampus.fgcstations.domain.exceptions.SameOriginDestinationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FgCstationsApplicationTests {

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
