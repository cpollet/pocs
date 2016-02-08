package net.cpollet.pocs.tests.mappings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Christophe Pollet
 */
@Entity
@Table(name="TEST")
public class Test {
    @Id
    @Column(name="COLUMN1")
    private String column1;

    @Column(name="COLUMN2")
    private String column2;

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }
}
