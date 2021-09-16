package br.com.ufpr.das.client;

import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class ClientFactory {

    static {
        Fixture.of(Client.class).addTemplate("default", new Rule() {{
            add("id", random(Long.class, range(1L, 200L)));
            add("cpf", random("60083903003", "17662152030", "31598104020"));
            add("firstName", random("client1", "client2", "client3"));
            add("lastName", "${firstName}");
        }});
    }

    public static Client getOne(String label) {
        return Fixture.from(Client.class).gimme(label);
    }

    public static List<Client> getList(int quantity, String label) {
        return Fixture.from(Client.class).gimme(quantity, label);
    }
    
}
