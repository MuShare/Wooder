package org.mushare.wooder.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "wooder_text")
public class Text implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    @Column(nullable = false)
    private String identifer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean android;

    @Column(nullable = false)
    private Boolean ios;

    @ManyToOne
    @JoinColumn(nullable = false, name = "text_foler_id")
    private TextFolder folder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return Objects.equals(id, text.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
