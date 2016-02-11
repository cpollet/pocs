package net.cpollet.pocs.read.api;

/**
 * @author Christophe Pollet
 */
public final class Attributes {
    /**
     * Person's first name
     */
    public static final String FIRST_NAME = "FIRST_NAME";
    /**
     * Person's last name
     */
    public static final String LAST_NAME = "LAST_NAME";
    /**
     * Person's cat name
     */
    public static final String CAT_NAME = "CAT_NAME";
    /**
     * Person's age
     */
    public static final String AGE = "AGE";

    /**
     * Some deprecated value
     *
     * @deprecated not supported anymore, use FIRST_NAME instead.
     */
    @Deprecated
    public static final String DEPRECATED = "DEPRECATED";

    private Attributes() {
        // we don't want to instantiate this class
    }
}
