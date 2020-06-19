package ru.otus.krivonos.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENRE")
public class GenreJPA {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "TYPE", unique = true, nullable = false)
	private String type;

	public GenreMongo convert() {
		return new GenreMongo(String.valueOf(this.id), this.type);
	}
}
