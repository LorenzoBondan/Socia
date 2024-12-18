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
@Table(name = "tb_binary")
public class Binary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private byte[] bytes;

	@OneToMany(mappedBy = "binary", fetch = FetchType.LAZY)
	private List<Attachment> attachments = new ArrayList<>();

	public Binary(Integer id) {
		this.id = id;
	}
}
