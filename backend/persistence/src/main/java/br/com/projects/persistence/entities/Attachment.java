package br.com.projects.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_attachment")
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "binary_id")
	private Binary binary;

	private String name;
	private String mimeType;
	private String url;
	@Column(columnDefinition = "TEXT")
	private String checksum;

	@OneToMany(mappedBy = "attachment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<PostAttachment> postAttachments = new ArrayList<>();

	@OneToMany(mappedBy = "attachment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<UserAttachment> userAttachments = new ArrayList<>();

	public Attachment(Integer id) {
		this.id = id;
	}
}
