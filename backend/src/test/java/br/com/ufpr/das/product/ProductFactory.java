package br.com.ufpr.das.product;

import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class ProductFactory {

    static{
        Fixture.of(Product.class).addTemplate("default", new Rule(){{
            add("id", random(Long.class, range(1L, 200L)));
            add("description", random("produto1", "produto2", "produto3"));
        }});
        Fixture.of(Product.class).addTemplate("idNull")
            .inherits("default", new Rule() {{
                add("id", null);
            }});
        Fixture.of(Product.class).addTemplate("descriptionNull")
            .inherits("idNull", new Rule() {{
                add("description", null);
            }});

    }

    public static Product getOne(String label) {
        return Fixture.from(Product.class).gimme(label);
    }

    public static List<Product> getList(int quantity, String label) {
        return Fixture.from(Product.class).gimme(quantity, label);
    }
    
}
