package net.cpollet.rest.api.restrictions;

/**
 * @author Christophe Pollet
 */
public class Or extends BaseRestriction {
    private final Restriction left;
    private final Restriction right;

    public Or(Restriction left, Restriction right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toQuery() {
        return "or(" + left.toQuery() + "," + right.toQuery() + ")";
    }
}
