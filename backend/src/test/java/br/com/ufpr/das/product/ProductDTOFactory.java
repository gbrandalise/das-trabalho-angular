package br.com.ufpr.das.product;

import java.util.List;

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
        Fixture.of(ProductDTO.class).addTemplate("descriptionBlank")
            .inherits("idNull", new Rule() {{
                add("description", "");
        }});

    }

    public static ProductDTO getOne(String label) {
        return Fixture.from(ProductDTO.class).gimme(label);
    }

    public static List<ProductDTO> getList(int quantity, String label) {
        return Fixture.from(ProductDTO.class).gimme(quantity, label);
    }
}
