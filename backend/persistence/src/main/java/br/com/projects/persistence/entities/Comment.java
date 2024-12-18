package br.com.projects.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	private String description;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime date;
	private Boolean isEdited;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<CommentLike> likes = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_comment_comments",
			joinColumns = @JoinColumn(name = "comment_parent_id"),
			inverseJoinColumns = @JoinColumn(name = "comment_children_id")
	)
	private Set<Comment> comments = new HashSet<>();

	public Comment(Integer id) {
		this.id = id;
	}
}
