package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImagenesUtils {

	public static String crearImagen(File archivoImagen) throws IOException {
		if (archivoImagen == null) {
			return null;
		}

		String rutaRelativa = "imagenes/" + archivoImagen.getName();
		Path origen = archivoImagen.toPath();
		Path destino = Paths.get(rutaRelativa);

		Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

		return rutaRelativa;
	}

}
