package com.alfish.arealmvp.model;

import com.alfish.arealmvp.model.composite.CloudId;
import com.alfish.arealmvp.util.InstantConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "cloud", schema = "public")
@Getter
@Setter
@ToString
public class Cloud {

    @EmbeddedId
    private CloudId cloudId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "submission_time")
    @Convert(converter = InstantConverter.class)
    private Instant submissionTime;

}
