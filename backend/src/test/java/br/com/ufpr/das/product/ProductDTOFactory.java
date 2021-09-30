package br.com.ufpr.das.product;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class ProductDTOFactory {
    static{
        Fixture.of(ProductDTO.class).addTemplate("default", new Rule(){{
            add("id", random(Long.class, range(1L, 200L)));
            add("description", random("produto1", "produto2", "produto3"));
        }});
        Fixture.of(ProductDTO.class).addTemplate("idNull")
            .inherits("default", new Rule() {{
                add("id", null);
            }});
        Fixture.of(ProductDTO.class).addTemplate("descriptionNull")
            .inherits("idNull", new Rule() {{
                add("description", null);
            }});

    }

    public static ProductDTO getOne(String label) {
        return Fixture.from(ProductDTO.class).gimme(label);
    }
}
