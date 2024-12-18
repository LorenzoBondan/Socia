package br.com.projects.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_follow")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_follower_id")
	private User userFollower;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_following_id")
	private User userFollowing;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime date;

	public Follow(Integer id) {
		this.id = id;
	}
}
