package CyPirates.avere.domain.item.entity;

import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import CyPirates.avere.domain.user.entity.User;
import CyPirates.avere.global.entity.BaseEntity;
import CyPirates.avere.global.image.entity.ImageEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "item")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity extends BaseEntity {
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_description", nullable = false)
    private String itemDescription;

    @Column(name = "item_location", nullable = false)
    private String itemLocation;

    @Column(name = "item_time", nullable = false)
    private String itemTime;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
