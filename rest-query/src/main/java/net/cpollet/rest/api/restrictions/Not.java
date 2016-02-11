package net.cpollet.rest.api.restrictions;

/**
 * @author Christophe Pollet
 */
public class Not extends BaseRestriction {
    private final Restriction restriction;

    public Not(Restriction restriction) {
        this.restriction = restriction;
    }

    @Override
    public String toQuery() {
        return "not(" + restriction.toQuery() + ")";
    }
}
