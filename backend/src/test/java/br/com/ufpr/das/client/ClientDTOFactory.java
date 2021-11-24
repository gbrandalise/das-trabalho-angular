package br.com.ufpr.das.client;

import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class ClientDTOFactory {

    static {
        Fixture.of(ClientDTO.class).addTemplate("default", new Rule() {{
            add("id", random(Long.class, range(1L, 200L)));
            add("cpf", random("92153389267", "22203961260", "87542557831"));
            add("firstName", random("client1", "client2", "client3"));
            add("lastName", "${firstName}");
        }});
        Fixture.of(ClientDTO.class).addTemplate("idNull")
            .inherits("default", new Rule() {{
                add("id", null);
            }});
        Fixture.of(ClientDTO.class).addTemplate("cpfNull")
            .inherits("idNull", new Rule() {{
                add("cpf", null);
            }});
    }

    public static ClientDTO getOne(String label) {
        return Fixture.from(ClientDTO.class).gimme(label);
    }

    public static List<ClientDTO> getList(int quantity, String label) {
        return Fixture.from(ClientDTO.class).gimme(quantity, label);
    }
    
}
