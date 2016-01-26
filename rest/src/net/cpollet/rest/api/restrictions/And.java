package net.cpollet.rest.api.restrictions;

/**
 * @author Christophe Pollet
 */
public class And extends BaseRestriction {
    private final Restriction left;
    private final Restriction right;

    public And(Restriction left, Restriction right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toQuery() {
        return "and(" + left.toQuery() + "," + right.toQuery() + ")";
    }
}
