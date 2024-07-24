package app.chat.rest;

import app.chat.auth.JwtService;
import app.chat.domain.Guild;
import app.chat.domain.vo.CreateGuildDTO;
import app.chat.service.GuildService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static app.chat.utils.UtilTest.writeValueAsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GuildController.class)
public class GuildControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private GuildService guildService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void shouldListAllAvailableGuilds() throws Exception {
        String jwtToken = "your.jwt.token.here";
        List<Guild> guilds = new ArrayList<Guild>();
        guilds.add(new Guild());
        String response = writeValueAsString(guilds);
        when(guildService.listAllAvailable()).thenReturn(guilds);
        mockMvc.perform(get("/api/guilds")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(response));
        verify(guildService, times(1)).listAllAvailable();
    }

    @Test
    public void shouldCreateGuild() throws Exception {
        String jwtToken = "your.jwt.token.here";
        CreateGuildDTO request = CreateGuildDTO.builder()
                .name("teste").build();
        String json = writeValueAsString(request);

        mockMvc.perform(post("/api/guilds")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .content(json)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(guildService, times(1)).createGuild(request);

    }
}
