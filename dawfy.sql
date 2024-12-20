CREATE TABLE Usuario (
    id BIGSERIAL PRIMARY KEY ,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    pais CHAR(2) NOT NULL
);

CREATE TABLE Cliente (
    idCliente INT PRIMARY KEY,
    FOREIGN KEY (idCliente) REFERENCES Usuario(id)
);

CREATE TABLE Artista (
    idArtista INT PRIMARY KEY,
    FOREIGN KEY (idArtista) REFERENCES Usuario(id)
);

CREATE TABLE Album (
    idAlbum BIGSERIAL PRIMARY KEY ,
    nombre VARCHAR(100) NOT NULL,
    fechaLanzamiento DATE NOT NULL,
    artista INT NOT NULL,
    FOREIGN KEY (artista) REFERENCES Artista(idArtista)
);

CREATE TABLE Cancion (
    idCancion BIGSERIAL PRIMARY KEY ,
    url TEXT NOT NULL,
    duracion INT NOT NULL,
    album INT NOT NULL,
    FOREIGN KEY (album) REFERENCES Album(idAlbum)
);

CREATE TABLE Categoria (
    idCategoria BIGSERIAL PRIMARY KEY ,
    nombre VARCHAR(20) NOT NULL
);

CREATE TABLE CancionCategoria (
    categoria INT ,
    cancion INT ,
    FOREIGN KEY (cancion) REFERENCES Cancion(idCancion),
    FOREIGN KEY (categoria) REFERENCES Categoria(idCategoria),
    PRIMARY KEY (categoria, cancion)
);

CREATE TABLE Colaboracion (
    cancion INT,
    artista INT,
    PRIMARY KEY (cancion, artista),
    FOREIGN KEY (cancion) REFERENCES Cancion(idCancion),
    FOREIGN KEY (artista) REFERENCES Artista(idArtista)
);