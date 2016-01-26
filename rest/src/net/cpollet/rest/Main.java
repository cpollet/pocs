package net.cpollet.rest;

import net.cpollet.rest.api.attributes.CustomAttribute;
import net.cpollet.rest.api.attributes.PredefinedAttribute;
import net.cpollet.rest.api.filters.DefaultAttributesList;
import net.cpollet.rest.api.restrictions.Restriction;
import net.cpollet.rest.api.restrictions.Restrictions;

/**
 * @author Christophe Pollet
 */
public class Main {
    public static void main(String[] args) {
        Restriction restriction = Restrictions.equals(PredefinedAttribute.FIRST_NAME, "Christophe")
                .and(Restrictions.contains(PredefinedAttribute.LAST_NAME, "Pollet").not());

        System.out.println(restriction.toQuery());

        DefaultAttributesList attributesList = new DefaultAttributesList();
        attributesList.add(PredefinedAttribute.LAST_NAME);
        attributesList.add(new CustomAttribute("persons", "birthDate"));
        attributesList.add(new CustomAttribute("persons", "sales", "persons_sales", "personId"));

        System.out.println(attributesList.toQuery());
    }
}
