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
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "TYPE", unique = true, nullable = false)
	private String type;

	public Genre(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Genre{" +
			"id=" + id +
			", type='" + type + '\'' +
			'}';
	}
}
