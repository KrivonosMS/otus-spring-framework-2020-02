package ru.otus.krivonos.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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
