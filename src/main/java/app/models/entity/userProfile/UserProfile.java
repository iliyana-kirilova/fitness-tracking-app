package app.models.entity.userProfile;

import app.models.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer height;
    private Double weight;
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_level")
    private ActivityLevel activityLevel;

    @Enumerated(EnumType.STRING)
    private FitnessGoal goal;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
