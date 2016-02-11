package net.cpollet.rest.api.restrictions;

/**
 * @author Christophe Pollet
 */
public abstract class BaseRestriction implements Restriction {
    @Override
    public Restriction and(Restriction right) {
        return Restrictions.and(this, right);
    }

    @Override
    public Restriction or(Restriction right) {
        return Restrictions.or(this,right);
    }

    @Override
    public Restriction not() {
        return Restrictions.not(this);
    }
}
