package net.cpollet.rest.api.restrictions;

/**
 * @author Christophe Pollet
 */
public interface Restriction {
    Restriction and(Restriction right);

    Restriction or(Restriction right);

    Restriction not();

    String toQuery();
}
