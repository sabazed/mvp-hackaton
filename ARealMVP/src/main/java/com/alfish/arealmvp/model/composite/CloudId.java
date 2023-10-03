package com.alfish.arealmvp.model.composite;

import com.alfish.arealmvp.model.Statue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
public class CloudId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "statue_id") private Statue statue;

    @Column(name = "cloud_id") private Integer cloudId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloudId cloud = (CloudId) o;
        return cloud.cloudId.equals(cloudId) && cloud.statue.getStatueId().equals(statue.getStatueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statue.getStatueId(), cloudId);
    }

}